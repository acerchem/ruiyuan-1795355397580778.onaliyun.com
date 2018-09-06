package com.acerchem.facades.storefinder.populators;

import de.hybris.platform.commercefacades.product.data.PriceData;
import de.hybris.platform.commercefacades.product.data.PriceDataType;
import de.hybris.platform.commercefacades.product.data.ProductData;
import de.hybris.platform.commercefacades.search.converters.populator.SearchResultVariantProductPopulator;
import de.hybris.platform.commerceservices.price.CommercePriceService;
import de.hybris.platform.commerceservices.search.resultdata.SearchResultValueData;
import de.hybris.platform.core.model.product.ProductModel;
import de.hybris.platform.core.model.user.UserModel;
import de.hybris.platform.jalo.order.price.PriceInformation;
import de.hybris.platform.product.ProductService;
import de.hybris.platform.servicelayer.user.UserService;
import java.math.BigDecimal;
import javax.annotation.Resource;
import org.apache.commons.collections.CollectionUtils;
import com.acerchem.core.model.UserLevelModel;

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
	
	@Resource
	private CommercePriceService commercePriceService;

	@Override
	public void populate(final SearchResultValueData source, final ProductData target)
	{
        super.populate(source, target);
        
        final ProductModel productModel = productService.getProductForCode((String)source.getValues().get("code"));
        final UserModel user = userService.getCurrentUser();
		final boolean isAnonymousUser = userService.isAnonymousUser(user);
		final Boolean displayPrice = productModel.isAnonymousDisplayPrice();
		
		final PriceDataType priceType;
		final PriceInformation info;
		if (CollectionUtils.isEmpty(productModel.getVariants()))
		{
			priceType = PriceDataType.BUY;
			info = commercePriceService.getWebPriceForProduct(productModel);
		}
		else
		{
			priceType = PriceDataType.FROM;
			info = commercePriceService.getFromPriceForProduct(productModel);
		}
		
		if (info != null&&(!isAnonymousUser||displayPrice))
		{
			final PriceData priceData = getPriceDataFactory().create(priceType, BigDecimal.valueOf(info.getPriceValue().getValue()),
					info.getPriceValue().getCurrencyIso());
			target.setPrice(priceData);		
            UserLevelModel userLevel = user.getUserLevel();
            if(userLevel!=null){
                Double discount = userLevel.getDiscount();

                if(discount!=null&&userLevel!=null){
                    BigDecimal promotion = BigDecimal.valueOf(info.getPriceValue().getValue()).multiply(BigDecimal.valueOf(discount));
                    final PriceData promotionPrice = getPriceDataFactory().create(priceType, promotion,info.getPriceValue().getCurrencyIso());

                    target.setPromotionPrice(promotionPrice);
                }
            }
		}
		else
		{
			target.setPrice(null);
			target.setPromotionPrice(null);
			
		}
		
		if(isAnonymousUser){
			target.setManufacturer(null);
		}
	}

}
