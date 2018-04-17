package com.acerchem.facades.facades;


import de.hybris.platform.commercefacades.order.data.CartModificationData;
import de.hybris.platform.commerceservices.order.CommerceCartModificationException;

import java.text.ParseException;
import java.util.Date;

/**
 * Created by Jacob.Ji on 2018/3/22.
 */
public interface AcerchemCartFacade {

    String acerchemValidateCart(String productCode,boolean isUseFutureStock,String storeId);

    /**
     * Method for adding a product to cart.
     *
     * @param code
     *           code of product to add
     * @param quantity
     *           the quantity of the product
     * @param isUseFutureStock
     *           the warehouseCode of the product
     * @return the cart modification data that includes a statusCode and the actual quantity added to the cart
     * @throws CommerceCartModificationException
     *            if the cart cannot be modified
     */
    CartModificationData addToCart(String code, long quantity, boolean isUseFutureStock, String storeId, String availableDate) throws CommerceCartModificationException;

    CartModificationData updateCartEntry(long entryNumber, String storeId ,boolean isUseFutureStock) throws CommerceCartModificationException;

    double getAddToCartPrice(String productCode,Long qty);
}
