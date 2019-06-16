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

import com.hybris.backoffice.widgets.notificationarea.event.NotificationEvent;
import com.hybris.backoffice.widgets.notificationarea.event.NotificationUtils;
import org.apache.log4j.Logger;

import com.hybris.cockpitng.actions.ActionContext;
import com.hybris.cockpitng.actions.ActionResult;
import com.hybris.cockpitng.actions.CockpitAction;
import com.hybris.cockpitng.engine.impl.AbstractComponentWidgetAdapterAware;

import de.hybris.platform.core.model.order.OrderModel;
import de.hybris.platform.processengine.BusinessProcessEvent;
import de.hybris.platform.processengine.BusinessProcessService;
import de.hybris.platform.servicelayer.model.ModelService;

import java.util.EnumSet;


public class OrderForEmployeeCofirmPayAction extends AbstractComponentWidgetAdapterAware implements CockpitAction<OrderModel, Object>
{
	private static final Logger LOG = Logger.getLogger(OrderForEmployeeCofirmPayAction.class);
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
		LOG.info("--------------------start-------------------");
		OrderModel order = (OrderModel) ctx.getData();
		LOG.info("---------------------------------------"+order.getOrderProcess().iterator().next().getCode());
		if(order.getCustomerConfirmPay()==null || !order.getCustomerConfirmPay()){
			ActionResult actionResult = new ActionResult("success");
			actionResult.setStatusFlags(EnumSet.of(ActionResult.StatusFlag.OBJECT_MODIFIED));
			NotificationUtils.notifyUser(this.getNotificationSource(ctx), "MissCustomerConfirm", NotificationEvent.Level.FAILURE, new Object[0]);
			return actionResult;
		}
		final String eventID = new StringBuilder()//
		          .append(order.getOrderProcess().iterator().next().getCode())//
		          .append("_")//
		    .append("ConfirmPayActionEvent")//
		    .toString();
		final BusinessProcessEvent event = BusinessProcessEvent.builder(eventID)
			    .withChoice("waitForEmployeeConfirmPay").build();
			  getBusinessProcessService().triggerEvent(event);
			  order.setEmployeeConfirmPay(true);
			  this.modelService.save(order);
		LOG.info("--------------------end-------------------"+order.getEmployeeConfirmPay());
		ActionResult actionResult = new ActionResult("success");
		actionResult.setStatusFlags(EnumSet.of(ActionResult.StatusFlag.OBJECT_MODIFIED));
		NotificationUtils.notifyUser(this.getNotificationSource(ctx), "SUCCESS", NotificationEvent.Level.SUCCESS, new Object[0]);
		return actionResult;
	}

	protected String getNotificationSource(ActionContext<?> actionContext) {
		return NotificationUtils.getWidgetNotificationSource(actionContext);
	}

	public boolean canPerform(ActionContext<OrderModel> ctx) {
		OrderModel order = (OrderModel) ctx.getData();
		if(order==null) return false;
		if(order.getEmployeeConfirm()==null || !order.getEmployeeConfirm()){
			return false;
		}
		if((order.getEmployeeConfirmPay()!=null && order.getEmployeeConfirmPay())
				&& order.getCustomerConfirmPay()!=null && order.getCustomerConfirmPay()){
			return false;
		}
		return true;
	}
}
