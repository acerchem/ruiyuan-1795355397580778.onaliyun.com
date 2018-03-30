package com.acerchem.facades.facades.impl;

import com.acerchem.core.service.AcerchemCommerCartService;
import com.acerchem.facades.facades.AcerchemCartFacade;
import de.hybris.platform.commercefacades.order.data.AddToCartParams;
import de.hybris.platform.commercefacades.order.data.CartModificationData;
import de.hybris.platform.commercefacades.order.impl.DefaultCartFacade;
import de.hybris.platform.commerceservices.order.CommerceCartModification;
import de.hybris.platform.commerceservices.order.CommerceCartModificationException;
import de.hybris.platform.commerceservices.service.data.CommerceCartParameter;
import de.hybris.platform.core.model.order.AbstractOrderEntryModel;
import de.hybris.platform.core.model.order.CartModel;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.util.ObjectUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.stream.Collectors;

/**
 * Created by Jacob.Ji on 2018/3/20.
 */
public class DefaultAcerchemCartFacade extends DefaultCartFacade implements AcerchemCartFacade {

    private AcerchemCommerCartService acerchemCommerCartService;

    @Override
    public String  acerchemValidateCart(String productCode,boolean isUseFutureStock,String storeId) {
        if (hasSessionCart()){
            CartModel cartModel = getCartService().getSessionCart();
            if (ObjectUtils.isEmpty(storeId)){
                return "basket.error.storeId.empty";
            }
            if(!acerchemValidatePointOfService(storeId,cartModel)){
                return "basket.error.storeId.different";
            }
            if (!acerchemValidateProduct(isUseFutureStock,cartModel,productCode)){
                return "basket.error.product.stock.different";
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
        if (availableDate!=null) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-DD");
            try {
                params.setAvailableDate(sdf.parse(availableDate));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        return addToCart(params);
    }

    @Override
    public CartModificationData addToCart(final AddToCartParams addToCartParams) throws CommerceCartModificationException
    {
        final CommerceCartParameter parameter = getCommerceCartParameterConverter().convert(addToCartParams);
        final CommerceCartModification modification = acerchemCommerCartService.addToCart(parameter);

        return getCartModificationConverter().convert(modification);
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
}

