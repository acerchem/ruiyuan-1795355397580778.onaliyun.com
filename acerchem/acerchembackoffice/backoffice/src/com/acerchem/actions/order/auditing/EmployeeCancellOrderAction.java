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

import com.acerchem.core.service.AcerchemStockService;
import com.acerchem.service.customercreditaccount.DefaultCustomerCreditAccountService;
import com.hybris.cockpitng.actions.ActionContext;
import com.hybris.cockpitng.actions.ActionResult;
import com.hybris.cockpitng.actions.CockpitAction;
import com.hybris.cockpitng.engine.impl.AbstractComponentWidgetAdapterAware;

import de.hybris.platform.commerceservices.event.OrderCancelledEvent;
import de.hybris.platform.core.enums.OrderStatus;
import de.hybris.platform.core.model.order.OrderModel;
import de.hybris.platform.processengine.BusinessProcessService;
import de.hybris.platform.servicelayer.event.EventService;
import de.hybris.platform.servicelayer.model.ModelService;

public class EmployeeCancellOrderAction extends AbstractComponentWidgetAdapterAware implements CockpitAction<OrderModel, Object>
{
	private static final Logger LOG = Logger.getLogger(EmployeeCancellOrderAction.class);
	private static final String CREDIT_PAYMENT_INFO = "CreditPayment";
	
	@Resource
	BusinessProcessService businessProcessService;
	
	@Resource
	private ModelService modelService;
	
	@Resource
	private AcerchemStockService acerchemStockService;
	
	@Resource
	private EventService eventService;
	
	@Resource
	private DefaultCustomerCreditAccountService defaultCustomerCreditAccountService;
	
	public EventService getEventService() {
		return eventService;
	}

	public void setEventService(final EventService eventService) {
		this.eventService = eventService;
	
	}
	public AcerchemStockService getAcerchemStockService() {
		return acerchemStockService;
	}

	public void setAcerchemStockService(final AcerchemStockService acerchemStockService) {
		this.acerchemStockService = acerchemStockService;
	}

	public BusinessProcessService getBusinessProcessService() {
		return businessProcessService;
	}

	public void setBusinessProcessService(final BusinessProcessService businessProcessService) {
		this.businessProcessService = businessProcessService;
	}


	@Override
	public ActionResult<Object> perform(final ActionContext<OrderModel> ctx) {
		// TODO Auto-generated method stub
		LOG.info("--------------------start-------------------");
		final OrderModel order = ctx.getData();
		LOG.info("---------------------------------------"+order.getOrderProcess().iterator().next().getCode());
		
		if(order != null){
//			if (OrderStatus.COMPLETED.equals(order.getStatus()) || OrderStatus.DELIVERED.equals(order.getStatus()) || OrderStatus.UNCONFIRMDELIVERY.equals(order.getStatus()))
//			{
//				return new ActionResult("failed");
//			}else{
				acerchemStockService.releaseStock(order);
				if(order.getPaymentMode().getCode().equals(CREDIT_PAYMENT_INFO)){
					defaultCustomerCreditAccountService.updateCreditAccountRepaymentByOrder(order,true);
				}
				getEventService().publishEvent(new OrderCancelledEvent(order.getOrderProcess().iterator().next()));
				LOG.info("--------------------------------end CancelOrderStatusAction----------------------");
				setOrderStatus(order, OrderStatus.CANCELLED);
				return new ActionResult("success");
			//}
		}
		LOG.info("--------------------end-------------------");
		return new ActionResult("failed");
	}
	
	private void setOrderStatus(final OrderModel order, final OrderStatus orderStatus) {
		       order.setStatus(orderStatus);
		      this.modelService.save(order);
	}


	public boolean canPerform(ActionContext<OrderModel> ctx) {
		OrderModel order = (OrderModel) ctx.getData();
		if(order==null) return false;
		if(order.getEmployeeConfirm()!=null && order.getEmployeeConfirm()){
			return false;
		}
		if(order.getStatus()!=null&&OrderStatus.CANCELLED.equals(order.getStatus())){
			return false;
		}
		return true;
	}
}
