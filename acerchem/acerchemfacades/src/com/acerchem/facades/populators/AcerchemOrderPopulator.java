package com.acerchem.facades.populators;

import de.hybris.platform.converters.Populator;
import de.hybris.platform.core.model.order.OrderModel;
import de.hybris.platform.commercefacades.order.converters.populator.OrderPopulator;
import de.hybris.platform.commercefacades.order.data.OrderData;
import de.hybris.platform.servicelayer.dto.converter.ConversionException;

public class AcerchemOrderPopulator extends OrderPopulator implements Populator<OrderModel, OrderData>
{
	@Override
	public void populate(OrderModel source, OrderData target) throws ConversionException {
		super.populate(source,target);
		target.setCustomerConfirm(source.getCustomerConfirm());
	}

}

