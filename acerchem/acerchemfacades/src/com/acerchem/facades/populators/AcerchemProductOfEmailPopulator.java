package com.acerchem.facades.populators;

import de.hybris.platform.commercefacades.product.data.ProductData;
import de.hybris.platform.converters.Populator;
import de.hybris.platform.core.model.product.ProductModel;
import de.hybris.platform.core.model.product.UnitModel;
import de.hybris.platform.servicelayer.dto.converter.ConversionException;

public class AcerchemProductOfEmailPopulator implements Populator<ProductModel, ProductData> {

	@Override
	public void populate(ProductModel source, ProductData target) throws ConversionException {
		// TODO Auto-generated method stub
		
		target.setPackageType(source.getPackageType());
		target.setPackageWeight(source.getPackageWeight());
		target.setNetWeight(source.getNetWeight());
		target.setGrossWeight(source.getGrossWeight());
		
		UnitModel uModel = source.getUnit();
		if(uModel!=null){
			target.setUnitName(uModel.getCode());
		}
	}
	
	

}
