package com.acerchem.facades.populators;

import de.hybris.platform.commercefacades.product.converters.populator.ProductPopulator;
import de.hybris.platform.commercefacades.product.data.ProductData;
import de.hybris.platform.core.model.product.ProductModel;

public class AcerchemProductPopulator extends ProductPopulator{
	
	@Override
	public void populate(final ProductModel source, final ProductData target)
	{
		super.populate(source, target);
		target.setNetWeight(source.getNetWeight());
	}
	
}


