package com.acerchem.facades.populators;

import de.hybris.platform.commercefacades.user.data.CreditTransactionData;
import de.hybris.platform.commercefacades.user.data.CustomerCreditAccountData;
import de.hybris.platform.converters.Populator;
import de.hybris.platform.servicelayer.dto.converter.Converter;

import org.springframework.util.Assert;
import com.acerchem.core.model.CreditTransactionModel;
import com.acerchem.core.model.CustomerCreditAccountModel;

/**
 * Converter implementation for {@link de.hybris.platform.core.model.user.UserModel} as source and
 * {@link de.hybris.platform.commercefacades.user.data.UserLevelData} as target type.
 */
public class AcerchemCreditTransactionPopulator implements Populator<CreditTransactionModel,CreditTransactionData>
{
	//private Converter<CustomerCreditAccountModel,CustomerCreditAccountData> creditAccount;
	
	@Override
	public void populate(final CreditTransactionModel source, final CreditTransactionData target)
	{
		Assert.notNull(source, "Parameter source cannot be null.");
		Assert.notNull(target, "Parameter target cannot be null.");
		
		target.setCreaditUsedAmount(source.getCreaditUsedAmount());
		target.setPaybackAmount(source.getPaybackAmount());
		target.setIsPayback(source.getIsPayback());
		if(source.getPaybackTime()!=null)
		{
			target.setPaybackTime(source.getPaybackTime());
		}
		target.setShouldPaybackTime(source.getShouldPaybackTime());
		target.setCransactionId(source.getCransactionId());
		
		target.setOrderCode(source.getOrderCode());
		target.setProductNumber(source.getProductNumber());
		
		/*if (source.getCreditAccount() != null)
		{
			target.setCreditAccount(creditAccount.convert(source.getCreditAccount()));
		}*/
	}
	
}

