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
package com.acerchem.fulfilmentprocess.actions.order;

import org.apache.log4j.Logger;

import de.hybris.platform.core.model.order.AbstractOrderEntryModel;
import de.hybris.platform.core.model.order.OrderModel;
import de.hybris.platform.orderprocessing.model.OrderProcessModel;
import de.hybris.platform.processengine.action.AbstractSimpleDecisionAction;
import de.hybris.platform.task.RetryLaterException;


public class RecordConsignmentInfoAction extends AbstractSimpleDecisionAction<OrderProcessModel>
{
	private static final Logger LOG = Logger.getLogger(RecordConsignmentInfoAction.class);

	@Override
	public de.hybris.platform.processengine.action.AbstractSimpleDecisionAction.Transition executeAction(
			OrderProcessModel process) throws RetryLaterException, Exception {
		// TODO Auto-generated method stub
		LOG.info("=================RecordConsignmentInfoAction================");
		final OrderModel order = process.getOrder();
		for(AbstractOrderEntryModel orderEntry :order.getEntries()){
			if(orderEntry.getConsignmentEntries().size()>0){
				return Transition.OK;
			}else{
				return Transition.NOK;
			}
		}
		return Transition.OK;
	}
}
