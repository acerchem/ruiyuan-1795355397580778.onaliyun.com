package com.acerchem.actions.creditTransaction;

import javax.annotation.Resource;
import org.apache.log4j.Logger;
import com.acerchem.core.model.CreditTransactionModel;
import com.acerchem.core.model.CustomerCreditAccountModel;
import com.hybris.cockpitng.actions.ActionContext;
import com.hybris.cockpitng.actions.ActionResult;
import com.hybris.cockpitng.actions.CockpitAction;
import com.hybris.cockpitng.engine.impl.AbstractComponentWidgetAdapterAware;
import com.acerchem.service.customercreditaccount.DefaultCustomerCreditAccountService;

public class CofirmCreditRepaymentAction extends AbstractComponentWidgetAdapterAware implements CockpitAction<CreditTransactionModel, Object>
{
	private static final Logger LOG = Logger.getLogger(CofirmCreditRepaymentAction.class);
	@Resource
	private DefaultCustomerCreditAccountService defaultCustomerCreditAccountService;

	@Override
	public ActionResult<Object> perform(ActionContext<CreditTransactionModel> ctx) {
		LOG.info("--------------------start-------------------");
		CreditTransactionModel CreditTransaction = (CreditTransactionModel) ctx.getData();
		LOG.info("---------------------------------------"+CreditTransaction.getOrderCode());
		
		if(CreditTransaction != null){
				
			CustomerCreditAccountModel creditAccount=defaultCustomerCreditAccountService.updateCustomerCreditAccountRepayment(CreditTransaction.getOrderCode(),CreditTransaction.getCreaditUsedAmount());
			if(creditAccount!=null)
			{
				LOG.info("--------------------------------end CofirmCreditRepaymentAction----------------------");
				return new ActionResult("success");
			}
			else
			{
				return new ActionResult("failed");
			}
		}
		LOG.info("--------------------end-------------------");
		return new ActionResult("failed");
	}
	
	
}
