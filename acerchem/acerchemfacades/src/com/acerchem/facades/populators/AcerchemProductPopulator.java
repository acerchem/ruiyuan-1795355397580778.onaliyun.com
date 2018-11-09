package com.acerchem.facades.populators;

import de.hybris.platform.commercefacades.product.converters.populator.ProductPopulator;
import de.hybris.platform.commercefacades.product.data.ProductData;
import de.hybris.platform.core.model.product.ProductModel;
import com.acerchem.core.model.UserLevelModel;
import de.hybris.platform.commercefacades.product.PriceDataFactory;
import de.hybris.platform.commercefacades.product.data.PriceData;
import de.hybris.platform.commercefacades.product.data.PriceDataType;
import de.hybris.platform.commerceservices.price.CommercePriceService;
import de.hybris.platform.core.model.user.UserModel;
import de.hybris.platform.jalo.order.price.PriceInformation;
import de.hybris.platform.servicelayer.dto.converter.ConversionException;
import de.hybris.platform.servicelayer.user.UserService;
import java.math.BigDecimal;
import javax.annotation.Resource;
import org.apache.commons.collections.CollectionUtils;


public class AcerchemProductPopulator extends ProductPopulator{
	
	@Resource
	private UserService userService;
	@Resource
	private CommercePriceService commercePriceService;
	@Resource
	private PriceDataFactory priceDataFactory;
	
	@Override
	public void populate(final ProductModel productModel, final ProductData productData) throws ConversionException
	{
		super.populate(productModel, productData);
		productData.setMinOrderQuantity(productModel.getMinOrderQuantity());
		productData.setNetWeight(productModel.getNetWeight());
		productData.setUnitCalculateRato(productModel.getUnitCalculateRato());
	    productData.setDiscontinued(productModel.isDiscontinued());
	    
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
		
		final Boolean displayPrice = ((ProductModel)productModel).isAnonymousDisplayPrice();
		final UserModel user = userService.getCurrentUser();
		final boolean isAnonymousUser = userService.isAnonymousUser(user);
		if (info != null&&(!isAnonymousUser||displayPrice))
		{
			final PriceData priceData = priceDataFactory.create(priceType, BigDecimal.valueOf(info.getPriceValue().getValue()),
					info.getPriceValue().getCurrencyIso());
			productData.setPrice(priceData);
			
            UserLevelModel userLevel = user.getUserLevel();
            if(userLevel!=null){
                Double discount = userLevel.getDiscount();

                if(discount!=null&&userLevel!=null){
                    BigDecimal promotion = BigDecimal.valueOf(info.getPriceValue().getValue()).multiply(BigDecimal.valueOf(discount));
                    final PriceData promotionPrice = priceDataFactory.create(priceType, promotion,info.getPriceValue().getCurrencyIso());

                    productData.setPromotionPrice(promotionPrice);
                }
            }
		}
		else
		{
			productData.setPurchasable(Boolean.FALSE);
		}
	}
}



