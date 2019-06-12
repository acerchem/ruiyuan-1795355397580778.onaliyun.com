package com.acerchem.facades.populators;

import com.acerchem.facades.product.data.PaymentModeData;
import de.hybris.platform.commercefacades.order.converters.populator.QuotePopulator;
import de.hybris.platform.commercefacades.quote.data.QuoteData;
import de.hybris.platform.converters.Populator;
import de.hybris.platform.core.model.order.QuoteModel;
import de.hybris.platform.core.model.user.CustomerModel;

import java.text.DateFormat;
import java.text.SimpleDateFormat;


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
		if(source.getWaitDeliveriedDate()!=null)
		{
			SimpleDateFormat dateFormat = new SimpleDateFormat("MMM dd'th',YYYY");
			target.setWaitDeliveiedDate(dateFormat.format(source.getWaitDeliveriedDate()));
		}else{
			target.setWaitDeliveiedDate("");
		}
		if(source.getUser()!=null)
		{
			target.setUserCompanyName(source.getUser().getCompanyName());
			if(source.getUser() instanceof CustomerModel){
				CustomerModel customerModel = (CustomerModel)source.getUser();
				if(customerModel.getCreditAccount().getBillingInterval()!=null) target.setBillingInterval(customerModel.getCreditAccount().getBillingInterval());
			}
		}
		super.populate(source, target);
	}
}
