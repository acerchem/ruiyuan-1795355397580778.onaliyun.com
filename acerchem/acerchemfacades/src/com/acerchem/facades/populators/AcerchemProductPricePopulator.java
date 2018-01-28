/**
 *
 */
package com.acerchem.facades.populators;

import de.hybris.platform.commercefacades.product.PriceDataFactory;
import de.hybris.platform.commercefacades.product.data.PriceData;
import de.hybris.platform.commercefacades.product.data.PriceDataType;
import de.hybris.platform.commercefacades.product.data.ProductData;
import de.hybris.platform.converters.Populator;
import de.hybris.platform.core.model.product.ProductModel;
import de.hybris.platform.europe1.model.PriceRowModel;
import de.hybris.platform.servicelayer.dto.converter.ConversionException;

import java.math.BigDecimal;

import javax.annotation.Resource;


/**
 * @author luke
 *
 */
public class AcerchemProductPricePopulator implements Populator<ProductModel, ProductData>
{

	@Resource
	private PriceDataFactory priceDataFactory;

	/*
	 * (non-Javadoc)
	 *
	 * @see de.hybris.platform.converters.Populator#populate(java.lang.Object, java.lang.Object)
	 */
	@Override
	public void populate(final ProductModel source, final ProductData target) throws ConversionException
	{
		final PriceRowModel promotionPriceModel = source.getPromotionPrice();
		if (promotionPriceModel != null && promotionPriceModel.getPrice() != null)
		{
			final PriceData priceData = priceDataFactory.create(PriceDataType.BUY,
					BigDecimal.valueOf(promotionPriceModel.getPrice().doubleValue()), promotionPriceModel.getCurrency());
			target.setPromotionPrice(priceData);
		}


	}

}
