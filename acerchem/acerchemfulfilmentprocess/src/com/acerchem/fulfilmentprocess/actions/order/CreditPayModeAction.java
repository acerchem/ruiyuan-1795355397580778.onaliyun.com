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

import java.math.BigDecimal;

import javax.annotation.Resource;

import org.apache.log4j.Logger;

import com.acerchem.core.model.CustomerCreditAccountModel;
import com.acerchem.service.customercreditaccount.DefaultCustomerCreditAccountService;

import de.hybris.platform.core.enums.OrderStatus;
import de.hybris.platform.core.model.order.OrderModel;
import de.hybris.platform.orderprocessing.model.OrderProcessModel;
import de.hybris.platform.processengine.action.AbstractProceduralAction;
import de.hybris.platform.processengine.action.AbstractSimpleDecisionAction;
import de.hybris.platform.processengine.action.AbstractSimpleDecisionAction.Transition;
import de.hybris.platform.task.RetryLaterException;


/**
 * This example action checks the order for required data in the business process. Skipping this action may result in
 * failure in one of the subsequent steps of the process. The relation between the order and the business process is
 * defined in basecommerce extension through item OrderProcess. Therefore if your business process has to access the
 * order (a typical case), it is recommended to use the OrderProcess as a parentClass instead of the plain
 * BusinessProcess.
 */
public class CreditPayModeAction extends AbstractSimpleDecisionAction<OrderProcessModel>
{
	private static final Logger LOG = Logger.getLogger(CreditPayModeAction.class);
	
	@Resource
	private DefaultCustomerCreditAccountService defaultCustomerCreditAccountService;
	
	
	public DefaultCustomerCreditAccountService getDefaultCustomerCreditAccountService() {
		return defaultCustomerCreditAccountService;
	}



	public void setDefaultCustomerCreditAccountService(
			DefaultCustomerCreditAccountService defaultCustomerCreditAccountService) {
		this.defaultCustomerCreditAccountService = defaultCustomerCreditAccountService;
	}



	@Override
	public Transition executeAction(final OrderProcessModel process){
		// TODO Auto-generated method stub
		
		final OrderModel order = process.getOrder();
		
		if(order != null){
			if (order.getPaymentMode().getCode().equals("CreditPayment"))
			{
				
				try {
					
					CustomerCreditAccountModel customerCreditAccountModel = defaultCustomerCreditAccountService.updateCustomerCreditAccountConsume(BigDecimal.valueOf(order.getTotalPrice()));
					if(customerCreditAccountModel != null){
						//支付成功后,改变orderStatus
						setOrderStatus(order, OrderStatus.PAIED);
						return Transition.OK;
					}else{
						return Transition.NOK;
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					LOG.error("更新信用账户失败!");
					return Transition.NOK;
				}
			}
		}
		return Transition.OK;
	}


}
