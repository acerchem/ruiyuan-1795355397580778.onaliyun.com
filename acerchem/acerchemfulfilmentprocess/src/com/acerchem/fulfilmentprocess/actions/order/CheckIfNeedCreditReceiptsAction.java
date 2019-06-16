package com.acerchem.fulfilmentprocess.actions.order;

import de.hybris.platform.core.model.order.OrderModel;
import de.hybris.platform.orderprocessing.model.OrderProcessModel;
import de.hybris.platform.processengine.action.AbstractSimpleDecisionAction;
import org.apache.log4j.Logger;


public class CheckIfNeedCreditReceiptsAction extends AbstractSimpleDecisionAction<OrderProcessModel>
{
	private static final Logger LOG = Logger.getLogger(CheckIfNeedCreditReceiptsAction.class);

	@Override
	public Transition executeAction(final OrderProcessModel process)
	{
		final OrderModel order = process.getOrder();
		//setOrderStatus(order, OrderStatus.UNCONFIRMDELIVERY);
		if (order == null)
		{
			LOG.error("Missing the order, exiting the process");
			return Transition.NOK;
		}

		if (order.getPaymentMode().getCode().equals("CreditPayment"))
		{
			return Transition.OK;
		}else{
			return Transition.NOK;
		}
	}
}
