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

import javax.annotation.Resource;

import org.apache.log4j.Logger;

import com.acerchem.core.service.AcerchemStockService;

import de.hybris.platform.core.enums.OrderStatus;
import de.hybris.platform.core.model.order.OrderModel;
import de.hybris.platform.orderprocessing.model.OrderProcessModel;
import de.hybris.platform.processengine.action.AbstractSimpleDecisionAction;


/**
 * This example action checks the order for required data in the business process. Skipping this action may result in
 * failure in one of the subsequent steps of the process. The relation between the order and the business process is
 * defined in basecommerce extension through item OrderProcess. Therefore if your business process has to access the
 * order (a typical case), it is recommended to use the OrderProcess as a parentClass instead of the plain
 * BusinessProcess.
 */
public class CancelOrderStatusAction extends AbstractSimpleDecisionAction<OrderProcessModel>
{
	private static final Logger LOG = Logger.getLogger(CancelOrderStatusAction.class);
	
	@Resource
	private AcerchemStockService acerchemStockService;

	@Override
	public Transition executeAction(final OrderProcessModel process) {
		// TODO Auto-generated method stub
		
		LOG.info("--------------------------------start CancelOrderStatusAction----------------------");
		final OrderModel order = process.getOrder();
		if(order != null){
			if (OrderStatus.COMPLETED.equals(order.getStatus()) || OrderStatus.DELIVERED.equals(order.getStatus()))
			{
				return Transition.NOK;
			}else{
				setOrderStatus(order, OrderStatus.CANCELLED);
				acerchemStockService.releaseStock(order);
				LOG.info("--------------------------------end CancelOrderStatusAction----------------------");
				return Transition.OK;
			}
		}
		return Transition.NOK;
	}
}
