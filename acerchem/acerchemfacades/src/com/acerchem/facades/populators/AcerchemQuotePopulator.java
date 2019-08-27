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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DateFormat;
import java.text.SimpleDateFormat;


public class AcerchemQuotePopulator extends QuotePopulator implements Populator<QuoteModel, QuoteData>
{
	private static final Logger LOG = LoggerFactory.getLogger(AcerchemQuotePopulator.class);

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
			PriceData entryTotalPrice = entryData.getTotalPrice();
			BigDecimal totalPriceValue = entryTotalPrice.getValue();
			PriceData subtotal = target.getSubTotal();
			BigDecimal subtotalPriceValue = subtotal.getValue();
			BigDecimal bilv = totalPriceValue.divide(subtotalPriceValue);
			LOG.info(" entry info bilv"+bilv);

			PriceData totalPrice = target.getTotalPrice();
			BigDecimal orderTotalPrice = totalPrice.getValue();
			BigDecimal newEntryTotalPrice = orderTotalPrice.multiply(bilv).setScale(2, BigDecimal.ROUND_HALF_UP);
			LOG.info(" entry info new Total price"+newEntryTotalPrice);
			PriceData newEntryTotalPriceData = priceDataFactory.create(PriceDataType.BUY, newEntryTotalPrice, source.getCurrency().getIsocode());
			entryData.setTotalPrice(newEntryTotalPriceData);

			BigDecimal totalWeightBig = new BigDecimal(totalWeight.doubleValue());
			BigDecimal newBasePrice = newEntryTotalPrice.divide(totalWeightBig, RoundingMode.HALF_UP);
			PriceData newBasePriceData = priceDataFactory.create(PriceDataType.BUY, newBasePrice, source.getCurrency().getIsocode());
			LOG.info(" entry info new base price"+newBasePrice);
			entryData.setBasePrice(newBasePriceData);
		}
	}
}
