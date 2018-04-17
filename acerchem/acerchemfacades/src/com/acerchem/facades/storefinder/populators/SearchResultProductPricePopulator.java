package com.acerchem.facades.storefinder.populators;

import de.hybris.platform.commercefacades.product.data.ProductData;
import de.hybris.platform.commercefacades.search.converters.populator.SearchResultVariantProductPopulator;
import de.hybris.platform.commerceservices.search.resultdata.SearchResultValueData;
import de.hybris.platform.core.model.product.ProductModel;
import de.hybris.platform.core.model.user.UserModel;
import de.hybris.platform.product.ProductService;
import de.hybris.platform.servicelayer.user.UserService;
import javax.annotation.Resource;

/**
 * Converter implementation for {@link de.hybris.platform.commerceservices.search.resultdata.SearchResultValueData} as
 * source and {@link de.hybris.platform.commercefacades.product.data.ProductData} as target type.
 */
public class SearchResultProductPricePopulator extends SearchResultVariantProductPopulator
{
	
	@Resource
	private UserService userService;
	
	@Resource(name = "productService")
	private ProductService productService;

	@Override
	public void populate(final SearchResultValueData source, final ProductData target)
	{
        super.populate(source, target);
        
        final UserModel user = userService.getCurrentUser();
		final boolean isAnonymousUser = userService.isAnonymousUser(user);
		
		String code=(String)source.getValues().get("code");
		final ProductModel productModel = productService.getProductForCode(code);
		
		
		final Boolean displayPrice = productModel.isAnonymousDisplayPrice();
		
		if (source.getValues() != null&&isAnonymousUser&&!displayPrice)
		{
			target.setPrice(null);
		}
	}

}
