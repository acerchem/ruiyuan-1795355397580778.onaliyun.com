package com.acerchem.facades.populators;

import com.acerchem.core.model.CreditTransactionModel;
import com.acerchem.core.model.CustomerCreditAccountModel;

import de.hybris.platform.commercefacades.user.data.CreditTransactionData;
import de.hybris.platform.commercefacades.user.data.CustomerCreditAccountData;
import de.hybris.platform.converters.Populator;
import de.hybris.platform.servicelayer.dto.converter.ConversionException;
import de.hybris.platform.servicelayer.dto.converter.Converter;
import org.springframework.util.Assert;

public class CreditTransactionPopulator implements Populator<CreditTransactionModel, CreditTransactionData> {

	private Converter<CustomerCreditAccountModel,CustomerCreditAccountData> creditAccount;

	@Override
	public void populate(CreditTransactionModel source, CreditTransactionData target)
			throws ConversionException {
		Assert.notNull(source, "Parameter source cannot be null.");
		Assert.notNull(target, "Parameter target cannot be null.");

		if (source.getCreaditUsedAmount() != null)
		{
			target.setCreaditUsedAmount(source.getCreaditUsedAmount());
		}
		if (source.getPaybackAmount() != null)
		{
			target.setPaybackAmount(source.getPaybackAmount());
		}
		if (source.getIsPayback() != null)
		{
			target.setIsPayback(source.getIsPayback());
		}
		if (source.getPaybackTime() != null)
		{
			target.setPaybackTime(source.getPaybackTime());
		}
		if (source.getShouldPaybackTime() != null)
		{
			target.setShouldPaybackTime(source.getShouldPaybackTime());
		}
		if (source.getCransactionId() != null)
		{
			target.setCransactionId(source.getCransactionId());
		}
		if (source.getCreditAccount() != null)
		{
			target.setCreditAccount(creditAccount.convert(source.getCreditAccount()));
		}
	}
}
