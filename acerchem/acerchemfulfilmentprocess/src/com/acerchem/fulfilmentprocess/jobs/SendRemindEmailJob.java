/*
 * [y] hybris Platform
 *
 * Copyright (c) 2017 SAP SE or an SAP affiliate company.  All rights reserved.
 *
 * This software is the confidential and proprietary information of SAP
 * ("Confidential Information"). You shall not disclose such Confidential
 * Information and shall use it only in accordance with the terms of the
 * license agreement you entered into with SAP.
 */
package com.acerchem.fulfilmentprocess.jobs;

import de.hybris.platform.core.enums.OrderStatus;
import de.hybris.platform.core.model.order.OrderModel;
import de.hybris.platform.cronjob.enums.CronJobResult;
import de.hybris.platform.cronjob.enums.CronJobStatus;
import de.hybris.platform.cronjob.model.CronJobModel;
import de.hybris.platform.orderprocessing.model.OrderProcessModel;
import de.hybris.platform.processengine.BusinessProcessService;
import de.hybris.platform.servicelayer.cronjob.AbstractJobPerformable;
import de.hybris.platform.servicelayer.cronjob.PerformResult;
import de.hybris.platform.servicelayer.search.FlexibleSearchQuery;
import de.hybris.platform.servicelayer.search.SearchResult;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Required;


/**
 * 此为最后一次提醒job
 */
public class SendRemindEmailJob extends AbstractJobPerformable<CronJobModel>
{ private static final Logger LOG = Logger.getLogger(SendRemindEmailJob.class);

	private BusinessProcessService businessProcessService;

	protected BusinessProcessService getBusinessProcessService()
	{
		return businessProcessService;
	}

	@Required
	public void setBusinessProcessService(final BusinessProcessService businessProcessService)
	{
		this.businessProcessService = businessProcessService;
	}

	public static final String SERVER_REMIND_PROCESS = "order-serverRemind-process";

	public OrderProcessModel createProcess(final OrderModel order)
	{
		Random random = new Random();
		final OrderProcessModel process = (OrderProcessModel) businessProcessService.createProcess(
				"severRemind" + System.currentTimeMillis()+random.nextInt(1000),SERVER_REMIND_PROCESS);
		process.setOrder(order);
		businessProcessService.startProcess(process);
		return process;
	}


	@Override
	public PerformResult perform(CronJobModel cronJobModel) {

		Date now = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String format = sdf.format(now);
		String query = "select {l.PK} from {order AS l} where {l.status} = ?status" +
				" and {l.lastTimeRemindDate} >= ?startTime  and {l.lastTimeRemindDate} < ?endTime";
		final FlexibleSearchQuery searchQuery = new FlexibleSearchQuery(query);
		searchQuery.addQueryParameter("status", OrderStatus.DELIVERED);
		searchQuery.addQueryParameter("startTime", format + " 00:00:00");
		searchQuery.addQueryParameter("endTime", format + " 23:59:59");
		final SearchResult<OrderModel> search = flexibleSearchService.search(searchQuery);
		List<OrderModel> result = search.getResult();
		if (CollectionUtils.isNotEmpty(result)){
			for (OrderModel order:result){
				createProcess(order);
			}
		}
		return new PerformResult(CronJobResult.SUCCESS, CronJobStatus.FINISHED);
	}
}
