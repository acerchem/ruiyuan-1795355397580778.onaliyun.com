package com.acerchem.facades.populators;

import de.hybris.platform.commercefacades.user.data.CreditTransactionData;
import de.hybris.platform.commercefacades.user.data.CustomerCreditAccountData;
import de.hybris.platform.converters.Populator;
import de.hybris.platform.servicelayer.dto.converter.Converter;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.util.Assert;
import com.acerchem.core.model.CreditTransactionModel;
import com.acerchem.core.model.CustomerCreditAccountModel;

/**
 * Converter implementation for {@link de.hybris.platform.core.model.user.UserModel} as source and
 * {@link de.hybris.platform.commercefacades.user.data.UserLevelData} as target type.
 */
public class AcerchemCreditAccountPopulator implements Populator<CustomerCreditAccountModel,CustomerCreditAccountData>
{
	private Converter<CreditTransactionModel,CreditTransactionData> creditTransactionConverter;
	
	@Override
	public void populate(final CustomerCreditAccountModel source, final CustomerCreditAccountData target)
	{
		
		Assert.notNull(source, "Parameter source cannot be null.");
		Assert.notNull(target, "Parameter target cannot be null.");
		
		
		target.setBillingInterval(source.getBillingInterval());
		target.setCreaditRemainedAmount(source.getCreaditRemainedAmount());
		target.setCreditTotalAmount(source.getCreditTotalAmount());
		target.setStatus(source.getStatus().getCode());
		target.setTransactions(creditTransactionConverter.convertAll(source.getTransactions()));
		
	}

	@Required
	public void setCreditTransactionConverter(Converter<CreditTransactionModel,CreditTransactionData> creditTransactionConverter) {
		this.creditTransactionConverter = creditTransactionConverter;
	}
	
}

