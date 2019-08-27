package com.acerchem.facades.populators;

import com.acerchem.facades.product.data.PaymentModeData;
import de.hybris.platform.commercefacades.order.converters.populator.QuotePopulator;
import de.hybris.platform.commercefacades.order.data.OrderEntryData;
import de.hybris.platform.commercefacades.product.PriceDataFactory;
import de.hybris.platform.commercefacades.product.data.PriceData;
import de.hybris.platform.commercefacades.product.data.PriceDataType;
import de.hybris.platform.commercefacades.quote.data.QuoteData;
import de.hybris.platform.converters.Populator;
import de.hybris.platform.core.model.order.QuoteModel;
import de.hybris.platform.core.model.user.CustomerModel;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DateFormat;
import java.text.SimpleDateFormat;


public class AcerchemQuotePopulator extends QuotePopulator implements Populator<QuoteModel, QuoteData>
{
	@Resource
	private PriceDataFactory priceDataFactory;

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
			SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
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
		for(OrderEntryData entryData : target.getEntries()){
			Long totalWeight = (entryData.getQuantity())*(Long.parseLong(entryData.getProduct().getNetWeight()));
			entryData.setTotalWeight(totalWeight.intValue());
			PriceData priceData = entryData.getBasePrice();
			BigDecimal bigDecimal = priceData.getValue();
			BigDecimal totalWeightBig = new BigDecimal(totalWeight.doubleValue());
			bigDecimal = bigDecimal.divide(totalWeightBig, RoundingMode.HALF_UP);
			PriceData priceData1 = priceDataFactory.create(PriceDataType.BUY, bigDecimal, source.getCurrency().getIsocode());
			entryData.setBasePrice(priceData1);
		}
	}
}
