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
package com.acerchem.fulfilmentprocess.actions.consignment;

import de.hybris.platform.core.enums.OrderStatus;
import de.hybris.platform.core.model.order.OrderModel;
import de.hybris.platform.orderprocessing.model.OrderProcessModel;
import de.hybris.platform.processengine.action.AbstractAction;
import de.hybris.platform.processengine.action.AbstractSimpleDecisionAction;
import de.hybris.platform.task.RetryLaterException;

import com.acerchem.fulfilmentprocess.CheckOrderService;
import com.acerchem.fulfilmentprocess.actions.order.WaitForCustomerConfirmAction.Transition;

import java.util.HashSet;
import java.util.Set;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Required;


/**
 * This example action checks the order for required data in the business process. Skipping this action may result in
 * failure in one of the subsequent steps of the process. The relation between the order and the business process is
 * defined in basecommerce extension through item OrderProcess. Therefore if your business process has to access the
 * order (a typical case), it is recommended to use the OrderProcess as a parentClass instead of the plain
 * BusinessProcess.
 */
public class EmployeeConsigmentConfirmAction  extends AbstractAction<OrderProcessModel>
{
	private static final Logger LOG = Logger.getLogger(EmployeeConsigmentConfirmAction.class);


	public enum Transition
	{
		OK, NOK, WAIT;

		public static Set<String> getStringValues()
		{
			final Set<String> res = new HashSet<String>();
			for (final Transition transitions : Transition.values())
			{
				res.add(transitions.toString());
			}
			return res;
		}
	}
	


	@Override
	public Set<String> getTransitions() {
		// TODO Auto-generated method stub
		return Transition.getStringValues();
	}


	@Override
	public String execute(OrderProcessModel process) throws RetryLaterException, Exception {
		return executeAction(process).toString();
	}

	protected Transition executeAction(final OrderProcessModel process)
	{
		final OrderModel order = process.getOrder();
		if (order != null)
		{
			if(order.getEmployeeConfirmDelivery()){
				return Transition.OK;
			}else{
				return Transition.WAIT;
			}
		}else{
			return Transition.NOK;
		}
	}

}
