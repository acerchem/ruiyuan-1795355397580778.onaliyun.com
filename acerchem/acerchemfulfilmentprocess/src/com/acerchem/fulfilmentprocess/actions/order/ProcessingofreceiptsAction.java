package com.acerchem.fulfilmentprocess.actions.order;

import com.acerchem.core.model.CreditTransactionModel;
import com.acerchem.core.model.CustomerCreditAccountModel;
import de.hybris.platform.core.enums.OrderStatus;
import de.hybris.platform.core.model.order.OrderModel;
import de.hybris.platform.core.model.user.CustomerModel;
import de.hybris.platform.orderprocessing.model.OrderProcessModel;
import de.hybris.platform.processengine.action.AbstractSimpleDecisionAction;
import de.hybris.platform.servicelayer.model.ModelService;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.log4j.Logger;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;


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
				if(creditPointPaid!=null)
				{
					creaditRemainedAmount = creaditRemainedAmount.add(BigDecimal.valueOf(creditPointPaid));
					BigDecimal creaditTotalAmount = customerCreditAccount.getCreditTotalAmount();
					if (creaditRemainedAmount.compareTo(creaditTotalAmount) < 1)
					{
						customerCreditAccount.setCreaditRemainedAmount(creaditRemainedAmount);
						order.setStatus(OrderStatus.COMPLETED);
						if(CollectionUtils.isNotEmpty(customerCreditAccount.getTransactions())){
							Optional<CreditTransactionModel> transactionModelOptional = new ArrayList<>(customerCreditAccount.getTransactions()).stream()
									.filter(creditTransactionModel -> creditTransactionModel.getOrderCode().equals(order.getCode()))
									.findAny();
							if(transactionModelOptional.isPresent()){
								CreditTransactionModel creditTransactionModel = transactionModelOptional.get();
								creditTransactionModel.setIsPayback(true);
								creditTransactionModel.setPaybackAmount(BigDecimal.valueOf(creditPointPaid));
								creditTransactionModel.setPaybackTime(new Date());
								modelService.save(creditTransactionModel);
							}
						}
						modelService.save(order);
						modelService.save(customerCreditAccount);
					}
				}
			}
			return Transition.OK;
		}else{
			return Transition.NOK;
		}
	}
}
