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

import java.util.stream.Collectors;

/**
 * Created by Jacob.Ji on 2018/3/20.
 */
public class DefaultAcerchemCartFacade extends DefaultCartFacade implements AcerchemCartFacade {

    private AcerchemCommerCartService acerchemCommerCartService;

    @Override
    public String acerchemValidateCart(String warehouseCode,String productCode,boolean isUseFutureStock) {
        if (hasSessionCart()){
            CartModel cartModel = getCartService().getSessionCart();
            if(acerchemValidateWarehouse(warehouseCode,cartModel)){
                return "basket.error.warehouse.different";
            }
            if (!acerchemValidateProduct(isUseFutureStock,cartModel,productCode)){
                return "basket.error.product.stock.different";
            }

        }
        return null;
    }


    @Override
    public CartModificationData addToCart(String code, long quantity, String warehouseCode, boolean isUseFutureStock,String storeId) throws CommerceCartModificationException {
        final AddToCartParams params = new AddToCartParams();
        params.setProductCode(code);
        params.setQuantity(quantity);
        params.setWarehouseCode(warehouseCode);
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

    private boolean acerchemValidateWarehouse(String warehouseCode, CartModel cartModel){
        boolean isSameWarehouse = true;
        if (CollectionUtils.isNotEmpty(cartModel.getEntries())){
            isSameWarehouse = warehouseCode .equals(cartModel.getEntries().stream().findFirst().get().getWarehouseCode());
        }
        return isSameWarehouse;
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

