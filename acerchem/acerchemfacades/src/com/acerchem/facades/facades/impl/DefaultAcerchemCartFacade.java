package com.acerchem.facades.facades.impl;

import com.acerchem.core.service.AcerchemCommerCartService;
import com.acerchem.facades.facades.AcerchemCartFacade;

import de.hybris.platform.commercefacades.order.EntryGroupData;
import de.hybris.platform.commercefacades.order.data.AddToCartParams;
import de.hybris.platform.commercefacades.order.data.CartData;
import de.hybris.platform.commercefacades.order.data.CartModificationData;
import de.hybris.platform.commercefacades.order.data.OrderEntryData;
import de.hybris.platform.commercefacades.order.impl.DefaultCartFacade;
import de.hybris.platform.commercefacades.product.ProductOption;
import de.hybris.platform.commercefacades.product.data.PriceData;
import de.hybris.platform.commercefacades.product.data.PriceDataType;
import de.hybris.platform.commercefacades.product.data.ProductData;
import de.hybris.platform.commerceservices.order.CommerceCartModification;
import de.hybris.platform.commerceservices.order.CommerceCartModificationException;
import de.hybris.platform.commerceservices.service.data.CommerceCartParameter;
import de.hybris.platform.core.model.order.AbstractOrderEntryModel;
import de.hybris.platform.core.model.order.CartEntryModel;
import de.hybris.platform.core.model.order.CartModel;
import de.hybris.platform.core.model.product.ProductModel;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Jacob.Ji on 2018/3/20.
 */
public class DefaultAcerchemCartFacade extends DefaultCartFacade implements AcerchemCartFacade {

    private AcerchemCommerCartService acerchemCommerCartService;

    @Override
    public String  acerchemValidateCart(String productCode,boolean isUseFutureStock,String storeId) {
        if (ObjectUtils.isEmpty(storeId)){
            return "basket.error.storeId.empty";
        }
        if (hasSessionCart()){
            CartModel cartModel = getCartService().getSessionCart();
            /** 2019-08-09 disable store check*/
//            if(!acerchemValidatePointOfService(storeId,cartModel)){
//                return "basket.error.storeId.different";
//            }
            if (!acerchemValidateProduct(isUseFutureStock,cartModel,productCode)){
                return "basket.error.product.stock.different";
            }

        }
        return null;
    }

    @Override
    public String acerchemValidateCartData(String productCode, long quantity, boolean isUseFutureStock,String storeId) {
        String validateCart=acerchemValidateCart(productCode, isUseFutureStock, storeId);
        if (!StringUtils.isEmpty(validateCart)) {
            return validateCart;
        }

        CartModel cartModel = getCartService().getSessionCart();
        List<AbstractOrderEntryModel> orderEntryModels = cartModel.getEntries();
        if (orderEntryModels != null && !orderEntryModels.isEmpty()) {
            for (AbstractOrderEntryModel orderEntryModel : orderEntryModels) {
                ProductModel product=orderEntryModel.getProduct();
                if (productCode.equalsIgnoreCase(product.getCode()) && quantity < product.getMinOrderQuantity()) {
                    return "basket.error.product.stockthreshold.different";
                }
            }
        }
        return null;
    }

    @Override
    public CartModificationData addToCart(String code, long quantity, boolean isUseFutureStock,String storeId,String availableDate) throws CommerceCartModificationException{
        final AddToCartParams params = new AddToCartParams();
        params.setProductCode(code);
        params.setQuantity(quantity);
        params.setIsUseFutureStock(isUseFutureStock);
        params.setStoreId(storeId);

        return addToCart(params);
    }

    @Override
    public CartModificationData addToCart(final AddToCartParams addToCartParams) throws CommerceCartModificationException
    {
        final CommerceCartParameter parameter = getCommerceCartParameterConverter().convert(addToCartParams);
        final CommerceCartModification modification = acerchemCommerCartService.addToCart(parameter);

        return getCartModificationConverter().convert(modification);
    }

    @Override
    public CartModificationData updateCartEntry(long entryNumber, String storeId, boolean isUseFutureStock) throws CommerceCartModificationException {
        final AddToCartParams dto = new AddToCartParams();
        dto.setStoreId(storeId);
        dto.setIsUseFutureStock(isUseFutureStock);
        final CommerceCartParameter parameter = getCommerceCartParameterConverter().convert(dto);
        parameter.setEnableHooks(true);
        parameter.setEntryNumber(entryNumber);
        CommerceCartModification commerceCartModification = null;
        if (parameter.getPointOfService() == null)
        {
//            commerceCartModification = getCommerceCartService().updateToShippingModeForCartEntry(parameter);
        }
        else
        {
        commerceCartModification = acerchemCommerCartService.updatePointOfServiceForCartEntry(parameter);
        }
        return getCartModificationConverter().convert(commerceCartModification);
    }

    @Override
    public double getAddToCartPrice(OrderEntryData orderEntryData, Long qty) {

        String netWeight = orderEntryData.getProduct().getNetWeight();
        		
        PriceData priceData = orderEntryData.getBasePrice();
        double totalPrice=0;
        if (priceData!=null){
            BigDecimal basePrice = priceData.getValue();
            
           // long totalWeight = qty* Long.valueOf(netWeight);
        	//Double totalPrice = Long.valueOf(basePrice)*totalWeight;
        	
        	 totalPrice =basePrice.doubleValue()*qty;
            //cartEntryTotalPrice = basePrice.multiply(BigDecimal.valueOf(qty));
        }
        return totalPrice;
    }

    private boolean acerchemValidatePointOfService(String storeId, CartModel cartModel){
        boolean isSamePOS = false;
        if (CollectionUtils.isNotEmpty(cartModel.getEntries())){
            isSamePOS = storeId.equals(cartModel.getEntries().stream().findFirst().get().getDeliveryPointOfService().getName());
        }else{
            isSamePOS = true;
        }
        return isSamePOS;
    }

    private boolean acerchemValidateProduct(boolean isUseFutureStock, CartModel cartModel,String productCode){
        boolean isCanAddProduct = true;
        if (CollectionUtils.isNotEmpty(cartModel.getEntries())){
            for (AbstractOrderEntryModel aoe : cartModel.getEntries()){
                if (productCode.equals(aoe.getProduct().getCode())&& isUseFutureStock !=aoe.getIsUseFutureStock()){
                    return false;
                }
            }
        }
        return isCanAddProduct;
    }

    @Required
    public void setAcerchemCommerCartService(AcerchemCommerCartService acerchemCommerCartService) {
        this.acerchemCommerCartService = acerchemCommerCartService;
    }
    
    @Override
	public CartData getSessionCartWithEntryOrdering(final boolean recentlyAddedFirst)
	{
		if (hasSessionCart())
		{
			final CartData data = getSessionCart();
			
			String netWeight = null;
			
			
			Double subTotal=(double) 0;
			if (data.getEntries()!= null){
				
				for(OrderEntryData entry:data.getEntries()){
					ProductData productData = entry.getProduct();
				    netWeight =productData.getNetWeight();
					
					// set the basePrice
					
					BigDecimal priceValue=entry.getBasePrice().getValue();
					
					Double basePrice = priceValue.doubleValue()/ Double.valueOf(netWeight);
					
					String currentyIso = entry.getBasePrice().getCurrencyIso();
					
					PriceData priceData = getPriceDataFactory().create(PriceDataType.BUY, BigDecimal.valueOf(basePrice),
							currentyIso);
					
					entry.setBasePrice(priceData);
					
					//-----------------set the total Price
					
					BigDecimal totalPrice=entry.getTotalPrice().getValue();
					
					Double price = totalPrice.doubleValue()/ Double.valueOf(netWeight);
					
					PriceData priceData1 = getPriceDataFactory().create(PriceDataType.BUY, BigDecimal.valueOf(price),
							currentyIso);
					entry.setTotalPrice(priceData1);
					
					//-----------------set the sub Price
				    // BigDecimal subPrice=data.getSubTotal().getValue();
			
					  subTotal += price;
						
					
					
				}
				
				PriceData priceData2 = getPriceDataFactory().create(PriceDataType.BUY, BigDecimal.valueOf(subTotal),
						data.getSubTotal().getCurrencyIso());
			    data.setSubTotal(priceData2);
				
			}

			if (recentlyAddedFirst)
			{
				final List<OrderEntryData> recentlyAddedListEntries = new ArrayList<>(data.getEntries());
				Collections.reverse(recentlyAddedListEntries);
				data.setEntries(Collections.unmodifiableList(recentlyAddedListEntries));
				final List<EntryGroupData> recentlyChangedEntryGroups = new ArrayList<>(data.getRootGroups());
				Collections.reverse(recentlyChangedEntryGroups);
				data.setRootGroups(Collections.unmodifiableList(recentlyChangedEntryGroups));
			}

			return data;
		}
		return createEmptyCart();
	}


	@Override
	public PriceData createPrice(PriceDataType priceType, BigDecimal value, String currencyIso) {
		
		return  getPriceDataFactory().create(PriceDataType.BUY, value, currencyIso);
	}
}

