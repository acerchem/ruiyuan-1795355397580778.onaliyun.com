package com.acerchem.core.service.impl;

import com.acerchem.core.service.AcerchemCommerCartService;
import com.acerchem.core.strategies.AcerchemCommerceAddToCartStrategy;
import de.hybris.platform.commerceservices.order.CommerceCartModification;
import de.hybris.platform.commerceservices.order.CommerceCartModificationException;
import de.hybris.platform.commerceservices.order.impl.DefaultCommerceCartService;
import de.hybris.platform.commerceservices.service.data.CommerceCartParameter;
import org.springframework.beans.factory.annotation.Required;

/**
 * Created by Jacob.Ji on 2018/3/21.
 */
public class DefaultAcerchemCommerCartService extends DefaultCommerceCartService implements AcerchemCommerCartService {

    private AcerchemCommerceAddToCartStrategy acerchemCommerceAddToCartStrategy;
    @Override
    public CommerceCartModification addToCart(final CommerceCartParameter parameter) throws CommerceCartModificationException
    {
        return acerchemCommerceAddToCartStrategy.addToCart(parameter);
    }

    @Required
    public void setAcerchemCommerceAddToCartStrategy(AcerchemCommerceAddToCartStrategy acerchemCommerceAddToCartStrategy) {
        this.acerchemCommerceAddToCartStrategy = acerchemCommerceAddToCartStrategy;
    }
}
