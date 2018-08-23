package com.acerchem.facades.populators;

import de.hybris.platform.commercefacades.product.data.ProductData;
import de.hybris.platform.core.model.product.ProductModel;
import de.hybris.platform.core.model.user.UserModel;
import de.hybris.platform.servicelayer.dto.converter.ConversionException;
import de.hybris.platform.servicelayer.user.UserService;

import javax.annotation.Resource;

import de.hybris.platform.commercefacades.product.converters.populator.ProductSummaryPopulator;

public class AcerchemProductSummaryPopulator<SOURCE extends ProductModel, TARGET extends ProductData> extends ProductSummaryPopulator<SOURCE, TARGET>{
	
	@Resource
	private UserService userService;
	
	@Override
	public void populate(final SOURCE productModel, final TARGET productData) throws ConversionException
	{
		final UserModel user = userService.getCurrentUser();
		final boolean isAnonymousUser = userService.isAnonymousUser(user);
		productData.setSummary(isAnonymousUser?null:safeToString(getProductAttribute(productModel, ProductModel.SUMMARY)));
	}
}
