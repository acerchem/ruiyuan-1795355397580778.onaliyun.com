package com.acerchem.facades.populators;

import de.hybris.platform.commercefacades.user.data.CreditTransactionData;
import de.hybris.platform.converters.Populator;
import org.springframework.util.Assert;
import com.acerchem.core.model.CreditTransactionModel;

/**
 * Converter implementation for {@link de.hybris.platform.core.model.user.UserModel} as source and
 * {@link de.hybris.platform.commercefacades.user.data.UserLevelData} as target type.
 */
public class AcerchemCreditTransactionPopulator implements Populator<CreditTransactionModel,CreditTransactionData>
{
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
		target.setRemark(source.getRemark());
		target.setOrderCode(source.getOrderCode());
		target.setProductNumber(source.getProductNumber());
		target.setCreationtime(source.getCreationtime());
		
	}
	
}

