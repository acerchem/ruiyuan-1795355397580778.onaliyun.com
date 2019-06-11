package com.acerchem.facades.populators;

import com.acerchem.facades.product.data.PaymentModeData;
import de.hybris.platform.commercefacades.order.converters.populator.QuotePopulator;
import de.hybris.platform.commercefacades.quote.data.QuoteData;
import de.hybris.platform.converters.Populator;
import de.hybris.platform.core.model.order.QuoteModel;


public class AcerchemQuotePopulator extends QuotePopulator implements Populator<QuoteModel, QuoteData>
{
	@Override
	public void populate(final QuoteModel source, final QuoteData target)
	{
		if(source.getDeliveryAddress()!=null) target.setDeliveryAddress(getAddressConverter().convert(source.getDeliveryAddress()));
		if(source.getDeliveryMode()!=null) target.setDeliveryMode(getDeliveryModeConverter().convert(source.getDeliveryMode()));
		if(source.getPaymentMode()!=null)
		{
			PaymentModeData paymentModeData = new PaymentModeData();
			paymentModeData.setCode(source.getPaymentMode().getCode());
			target.setPaymentModeData(paymentModeData);
		}
		addPrincipalInformation(source,target);
		super.populate(source, target);
	}
}
