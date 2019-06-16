package com.acerchem.fulfilmentprocess.actions.order;

import com.acerchem.core.model.CustomerCreditAccountModel;
import de.hybris.platform.core.enums.OrderStatus;
import de.hybris.platform.core.model.order.OrderModel;
import de.hybris.platform.core.model.user.CustomerModel;
import de.hybris.platform.orderprocessing.model.OrderProcessModel;
import de.hybris.platform.processengine.action.AbstractSimpleDecisionAction;
import de.hybris.platform.servicelayer.model.ModelService;
import org.apache.log4j.Logger;

import javax.annotation.Resource;
import java.math.BigDecimal;


public class ProcessingofreceiptsAction extends AbstractSimpleDecisionAction<OrderProcessModel>
{
	private static final Logger LOG = Logger.getLogger(ProcessingofreceiptsAction.class);

	@Resource
	private ModelService modelService;

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
			Double creditPointPaid = order.getCreditPointsPaid();
			if(order.getUser() instanceof CustomerModel){
				CustomerModel customer = (CustomerModel)order.getUser();
				CustomerCreditAccountModel customerCreditAccount= customer.getCreditAccount();
				modelService.refresh(customerCreditAccount);
				BigDecimal creaditRemainedAmount = customerCreditAccount.getCreaditRemainedAmount();
				creaditRemainedAmount = creaditRemainedAmount.add(BigDecimal.valueOf(creditPointPaid));
				BigDecimal creaditTotalAmount = customerCreditAccount.getCreditTotalAmount();
				if(creaditRemainedAmount.compareTo(creaditTotalAmount) < 1){
					customerCreditAccount.setCreaditRemainedAmount(creaditRemainedAmount);
					order.setStatus(OrderStatus.COMPLETED);
					modelService.save(order);
					modelService.save(customerCreditAccount);
				}
			}
			return Transition.OK;
		}else{
			return Transition.NOK;
		}
	}
}
