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
import de.hybris.platform.servicelayer.dto.converter.ConversionException;



/**
 * Populate the product data with stock information
 */
public class ProductNetWeightPopulator<SOURCE extends ProductModel, TARGET extends ProductData> extends
		AbstractProductPopulator<SOURCE, TARGET>
{
/*	private Converter<ProductModel, StockData> stockConverter;

	protected Converter<ProductModel, StockData> getStockConverter()
	{
		return stockConverter;
	}

	@Required
	public void setStockConverter(final Converter<ProductModel, StockData> stockConverter)
	{
		this.stockConverter = stockConverter;
	}*/

	@Override
	public void populate(final SOURCE source, final TARGET target) throws ConversionException
	{
		target.setNetWeight(source.getNetWeight());
	}
}
