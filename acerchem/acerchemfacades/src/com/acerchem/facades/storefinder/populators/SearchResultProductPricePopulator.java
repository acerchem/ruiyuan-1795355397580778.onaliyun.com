package com.acerchem.facades.storefinder.populators;

import de.hybris.platform.commercefacades.product.data.ImageData;
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
import java.util.List;
import javax.annotation.Resource;
import org.apache.commons.collections.CollectionUtils;
import com.acerchem.core.model.UserLevelModel;
import org.springframework.util.Assert;


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

	public void superPopulate(final SearchResultValueData source, final ProductData target)
	{
		Assert.notNull(source, "Parameter source cannot be null.");
		Assert.notNull(target, "Parameter target cannot be null.");
		// Pull the values directly from the SearchResult object
		target.setCode(this.<String> getValue(source, "code"));
		target.setName(this.<String> getValue(source, "name"));
		List<String> arrayList = this.<List> getValue(source, "manufacturerName");
		if(arrayList!=null&&CollectionUtils.isNotEmpty(arrayList))
		{
			target.setManufacturer(arrayList.get(0));
		}
		target.setDescription(this.<String> getValue(source, "description"));
		target.setSummary(this.<String> getValue(source, "summary"));
		target.setAverageRating(this.<Double> getValue(source, "reviewAvgRating"));
		target.setConfigurable(this.<Boolean> getValue(source, "configurable"));
		target.setBaseProduct(this.<String> getValue(source, "baseProductCode"));

		populatePrices(source, target);

		// Populate product's classification features
		getProductFeatureListPopulator().populate(getFeaturesList(source), target);

		final List<ImageData> images = createImageData(source);
		if (CollectionUtils.isNotEmpty(images))
		{
			target.setImages(images);
		}

		populateUrl(source, target);
		populatePromotions(source, target);
		populateStock(source, target);
	}

	@Override
	public void populate(final SearchResultValueData source, final ProductData target)
	{
		superPopulate(source, target);
		if (source.getValues() != null)
		{
			target.setMultidimensional((Boolean) source.getValues().get(MULTIDIMENSIONAL));
			setPriceRange(source, target);
			setFirstCategoryNameList(source, target);
		}
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
