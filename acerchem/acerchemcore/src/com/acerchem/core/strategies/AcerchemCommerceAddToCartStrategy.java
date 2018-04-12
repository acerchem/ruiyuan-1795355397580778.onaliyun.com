package com.acerchem.core.strategies;

import de.hybris.platform.commerceservices.order.CommerceCartModification;
import de.hybris.platform.commerceservices.order.CommerceCartModificationException;
import de.hybris.platform.commerceservices.service.data.CommerceCartParameter;

/**
 * Created by Jacob.Ji on 2018/3/23.
 */
public interface AcerchemCommerceAddToCartStrategy {

    CommerceCartModification addToCart(CommerceCartParameter parameter) throws CommerceCartModificationException;
}
