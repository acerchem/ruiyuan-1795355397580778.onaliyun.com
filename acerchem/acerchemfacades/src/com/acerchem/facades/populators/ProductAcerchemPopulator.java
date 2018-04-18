/*
 * [y] hybris Platform
 *
 * Copyright (c) 2017 SAP SE or an SAP affiliate company.  All rights reserved.
 *
 * This software is the confidential and proprietary information of SAP
 * ("Confidential Information"). You shall not disclose such Confidential
 * Information and shall use it only in accordance with the terms of the
 * license agreement you entered into with SAP.
 */
package com.acerchem.facades.populators;

import de.hybris.platform.commercefacades.product.converters.populator.AbstractProductPopulator;
import de.hybris.platform.commercefacades.product.data.ProductData;
import de.hybris.platform.core.model.product.ProductModel;
import de.hybris.platform.core.model.product.UnitModel;
import de.hybris.platform.servicelayer.dto.converter.ConversionException;

/**
 * Populate the product data with the product's summary description
 */
public class ProductAcerchemPopulator<SOURCE extends ProductModel, TARGET extends ProductData> extends AbstractProductPopulator<SOURCE, TARGET>
{
	@Override
	public void populate(final SOURCE productModel, final TARGET productData) throws ConversionException
	{
		productData.setChemicalInfo(safeToString(getProductAttribute(productModel, ProductModel.CHEMICALINFO)));
		productData.setUnitCalculateRato(safeToString(getProductAttribute(productModel, ProductModel.CHEMICALINFO)));
		
		productData.setPackageType(safeToString(getProductAttribute(productModel, ProductModel.PACKAGETYPE)));
		productData.setPackageWeight(safeToString(getProductAttribute(productModel, ProductModel.PACKAGEWEIGHT)));
		productData.setNetWeight(safeToString(getProductAttribute(productModel, ProductModel.NETWEIGHT)));
		productData.setGrossWeight(safeToString(getProductAttribute(productModel, ProductModel.GROSSWEIGHT)));
		
		
		UnitModel uModel = productModel.getUnit();
		if (uModel != null){
			productData.setUnitName(uModel.getName());
		}
		
	}
}
