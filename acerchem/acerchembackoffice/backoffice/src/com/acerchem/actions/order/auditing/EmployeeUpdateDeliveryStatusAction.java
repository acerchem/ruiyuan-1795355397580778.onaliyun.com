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
package com.acerchem.actions.order.auditing;

import javax.annotation.Resource;

import org.apache.log4j.Logger;

import com.hybris.cockpitng.actions.ActionContext;
import com.hybris.cockpitng.actions.ActionResult;
import com.hybris.cockpitng.actions.CockpitAction;
import com.hybris.cockpitng.engine.impl.AbstractComponentWidgetAdapterAware;

import de.hybris.platform.core.enums.OrderStatus;
import de.hybris.platform.core.model.order.OrderModel;
import de.hybris.platform.processengine.BusinessProcessEvent;
import de.hybris.platform.processengine.BusinessProcessService;
import de.hybris.platform.servicelayer.model.ModelService;

public class EmployeeUpdateDeliveryStatusAction extends AbstractComponentWidgetAdapterAware implements CockpitAction<OrderModel, Object>
{
	private static final Logger LOG = Logger.getLogger(EmployeeUpdateDeliveryStatusAction.class);
	private static final String CURRENT_OBJECT = "currentObject";
	
	@Resource
	BusinessProcessService businessProcessService;
	
	@Resource
	private ModelService modelService;
	
	public BusinessProcessService getBusinessProcessService() {
		return businessProcessService;
	}

	public void setBusinessProcessService(BusinessProcessService businessProcessService) {
		this.businessProcessService = businessProcessService;
	}


	@Override
	public ActionResult<Object> perform(ActionContext<OrderModel> ctx) {
		// TODO Auto-generated method stub
		LOG.info("--------------------start EmployeeUpdateDeliveryStatusAction-------------------");
		OrderModel order = (OrderModel) ctx.getData();
		//setOrderStatus(order, OrderStatus.DELIVERED);
		final String processCode = order.getOrderProcess().iterator().next().getCode();
		final String eventID = new StringBuilder()//
		          .append(processCode)//
		          .append("_")//
		    .append("ConfirmConsignmentStatusActionEvent")//
		    .toString();
		final BusinessProcessEvent event = BusinessProcessEvent.builder(eventID)
			    .withChoice("waitForEmployeeConfirmConsignmentStatus").build();
			  getBusinessProcessService().triggerEvent(event);
		LOG.info("--------------------end EmployeeUpdateDeliveryStatusAction-------------------");
		return new ActionResult("success");
	}
	
	private void setOrderStatus(OrderModel order, OrderStatus orderStatus) {
		order.setStatus(orderStatus);
		this.modelService.save(order);
	}
	
}
