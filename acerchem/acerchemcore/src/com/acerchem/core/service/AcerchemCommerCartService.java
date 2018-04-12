package com.acerchem.core.service;


import de.hybris.platform.commerceservices.order.CommerceCartModification;
import de.hybris.platform.commerceservices.order.CommerceCartModificationException;
import de.hybris.platform.commerceservices.service.data.CommerceCartParameter;
import de.hybris.platform.core.model.order.CartModel;
import de.hybris.platform.core.model.product.ProductModel;
import de.hybris.platform.core.model.product.UnitModel;

import java.util.List;

/**
 * Created by Jacob.Ji on 2018/3/21.
 */
public interface AcerchemCommerCartService {

    CommerceCartModification addToCart(final CommerceCartParameter parameter) throws CommerceCartModificationException;

    CommerceCartModification updatePointOfServiceForCartEntry(final CommerceCartParameter parameters) throws CommerceCartModificationException;
}
