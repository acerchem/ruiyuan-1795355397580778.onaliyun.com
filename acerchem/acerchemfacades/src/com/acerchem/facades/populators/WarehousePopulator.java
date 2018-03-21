package com.acerchem.facades.populators;

import com.acerchem.facades.product.data.WarehouseData;
import de.hybris.platform.converters.Populator;
import de.hybris.platform.ordersplitting.model.WarehouseModel;
import de.hybris.platform.servicelayer.dto.converter.ConversionException;

public class WarehousePopulator implements Populator<WarehouseModel, WarehouseData>
{
	@Override
	public void populate(WarehouseModel source, WarehouseData target) throws ConversionException {
		target.setCode(source.getCode());
		target.setName(source.getName());
	}
}
