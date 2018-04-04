package com.acerchem.facades.populators;

import com.acerchem.core.model.CreditTransactionModel;
import com.acerchem.core.model.CustomerCreditAccountModel;
import de.hybris.platform.commercefacades.user.data.CreditTransactionData;
import de.hybris.platform.commercefacades.user.data.CustomerCreditAccountData;
import de.hybris.platform.converters.Populator;
import de.hybris.platform.servicelayer.dto.converter.ConversionException;
import de.hybris.platform.servicelayer.dto.converter.Converter;
import org.springframework.util.Assert;

/*alice*/
public class CustomerCreditAccountPopulator implements Populator<CustomerCreditAccountModel, CustomerCreditAccountData> {

	private Converter<CreditTransactionModel,CreditTransactionData> transactionsConverter;
	
	@Override
	public void populate(CustomerCreditAccountModel source, CustomerCreditAccountData target)
			throws ConversionException {
		
		Assert.notNull(source, "Parameter source cannot be null.");
		Assert.notNull(target, "Parameter target cannot be null.");

		if (source.getBillingInterval() != null)
		{
			target.setBillingInterval(source.getBillingInterval());
		}
		if (source.getCreaditRemainedAmount() != null)
		{
			target.setCreaditRemainedAmount(source.getCreaditRemainedAmount());
		}
		if (source.getCreditTotalAmount() != null)
		{
			target.setCreditTotalAmount(source.getCreditTotalAmount());
		}
		if (source.getStatus() != null)
		{
			target.setStatus(source.getStatus().getCode());
		}
		
		if (source.getTransactions() != null)
		{
			target.setTransactions(transactionsConverter.convertAll(source.getTransactions()));
		}
		
	}
	

}
