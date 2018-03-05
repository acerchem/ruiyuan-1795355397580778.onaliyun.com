package com.acerchem.facades.storefinder.populators;

import de.hybris.platform.commercefacades.product.PriceDataFactory;
import de.hybris.platform.commercefacades.product.converters.populator.AbstractProductPopulator;
import de.hybris.platform.commercefacades.product.converters.populator.ProductPricePopulator;
import de.hybris.platform.commercefacades.product.data.PriceData;
import de.hybris.platform.commercefacades.product.data.PriceDataType;
import de.hybris.platform.commercefacades.product.data.ProductData;
import de.hybris.platform.commerceservices.price.CommercePriceService;
import de.hybris.platform.core.model.product.ProductModel;
import de.hybris.platform.core.model.user.UserModel;
import de.hybris.platform.jalo.order.price.PriceInformation;
import de.hybris.platform.servicelayer.dto.converter.ConversionException;
import de.hybris.platform.servicelayer.user.UserService;
import java.math.BigDecimal;
import javax.annotation.Resource;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Required;

public class ProductPricePopulator1<SOURCE extends ProductModel, TARGET extends ProductData> 
		extends ProductPricePopulator<SOURCE, TARGET>
{
	private CommercePriceService commercePriceService;
	private PriceDataFactory priceDataFactory;
	
	/*Alice*/
	@Resource
	private UserService userService;
	/*End*/

	protected CommercePriceService getCommercePriceService()
	{
		return commercePriceService;
	}

	@Required
	public void setCommercePriceService(final CommercePriceService commercePriceService)
	{
		this.commercePriceService = commercePriceService;
	}

	protected PriceDataFactory getPriceDataFactory()
	{
		return priceDataFactory;
	}

	@Required
	public void setPriceDataFactory(final PriceDataFactory priceDataFactory)
	{
		this.priceDataFactory = priceDataFactory;
	}


	@Override
	public void populate(final SOURCE productModel, final TARGET productData) throws ConversionException
	{
		final PriceDataType priceType;
		final PriceInformation info;
		if (CollectionUtils.isEmpty(productModel.getVariants()))
		{
			priceType = PriceDataType.BUY;
			info = getCommercePriceService().getWebPriceForProduct(productModel);
		}
		else
		{
			priceType = PriceDataType.FROM;
			info = getCommercePriceService().getFromPriceForProduct(productModel);
		}
		
		/*Alice*/
		final Boolean displayPrice = ((ProductModel)productModel).isAnonymousDisplayPrice();
		final UserModel user = userService.getCurrentUser();
		final boolean isAnonymousUser = userService.isAnonymousUser(user);
		if (info != null&&(!isAnonymousUser||displayPrice))
		{
			final PriceData priceData = getPriceDataFactory().create(priceType, BigDecimal.valueOf(info.getPriceValue().getValue()),
					info.getPriceValue().getCurrencyIso());
			productData.setPrice(priceData);
		}
		else
		{
			productData.setPurchasable(Boolean.FALSE);
		}
		/*End*/
	}
}
