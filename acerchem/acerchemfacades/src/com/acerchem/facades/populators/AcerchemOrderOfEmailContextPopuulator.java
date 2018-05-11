package com.acerchem.facades.populators;


import org.apache.commons.lang3.StringUtils;
import org.springframework.util.Assert;

import de.hybris.platform.commercefacades.order.data.OrderData;
import de.hybris.platform.converters.Populator;
import de.hybris.platform.core.model.c2l.CurrencyModel;
import de.hybris.platform.core.model.order.OrderModel;
import de.hybris.platform.core.model.order.payment.PaymentModeModel;
import de.hybris.platform.servicelayer.dto.converter.ConversionException;

public class AcerchemOrderOfEmailContextPopuulator implements Populator<OrderModel, OrderData>{

	/* (non-Javadoc)
	 * @see de.hybris.platform.converters.Populator#populate(java.lang.Object, java.lang.Object)
	 */
	@Override
	public void populate(OrderModel source, OrderData target) throws ConversionException {
		// TODO Auto-generated method stub
		
		Assert.notNull(source, "Parameter source cannot be null.");
		Assert.notNull(target, "Parameter target cannot be null.");
		
		CurrencyModel curModel = source.getCurrency();
		if(curModel != null){
			if(StringUtils.isNotBlank(curModel.getName())){
				target.setCurrency(curModel.getIsocode());
			}
		}
		
		PaymentModeModel payModel = source.getPaymentMode();
		
		if(payModel != null){
			target.setPaymentMode(payModel.getName());
			
		}
		
		
	}

	
}
