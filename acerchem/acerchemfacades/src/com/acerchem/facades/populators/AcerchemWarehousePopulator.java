package com.acerchem.facades.populators;

import de.hybris.platform.commercefacades.user.data.AcerchemWarehouseData;
import de.hybris.platform.converters.Populator;
import de.hybris.platform.ordersplitting.model.WarehouseModel;
import de.hybris.platform.servicelayer.dto.converter.ConversionException;

/**
 * @author lizhen
 */
public class AcerchemWarehousePopulator implements Populator<WarehouseModel, AcerchemWarehouseData>
{
	@Override
	public void populate(WarehouseModel source, AcerchemWarehouseData target) throws ConversionException {
		target.setCode(source.getCode());
		target.setName(source.getName());
	}
}

