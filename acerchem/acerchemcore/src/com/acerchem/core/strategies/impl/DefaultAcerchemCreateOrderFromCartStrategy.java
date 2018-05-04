/*
 * [y] hybris Platform
 *
 * Copyright (c) 2017 SAP SE or an SAP affiliate company.  All rights reserved.
 *
 * This software is the confidential and proprietary information of SAP
 * ("Confidential Information"). You shall not disclose such Confidential
 * Information and shall use it only in accordance with the terms of the
 * license agreement you entered into with SAP.
 */
package com.acerchem.core.strategies.impl;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;

import de.hybris.platform.core.model.order.CartModel;
import de.hybris.platform.core.model.order.OrderEntryModel;
import de.hybris.platform.core.model.order.OrderModel;
import de.hybris.platform.order.InvalidCartException;
import de.hybris.platform.order.strategies.CartValidator;
import de.hybris.platform.order.strategies.CreateOrderFromCartStrategy;
import de.hybris.platform.order.strategies.impl.DefaultCreateOrderFromCartStrategy;
import de.hybris.platform.order.strategies.ordercloning.CloneAbstractOrderStrategy;
import de.hybris.platform.servicelayer.keygenerator.KeyGenerator;




public class DefaultAcerchemCreateOrderFromCartStrategy extends DefaultCreateOrderFromCartStrategy implements CreateOrderFromCartStrategy
{
	private static final Logger LOG = Logger.getLogger(DefaultAcerchemCreateOrderFromCartStrategy.class);
	
	private CartValidator cartValidator;
	private CloneAbstractOrderStrategy cloneAbstractOrderStrategy;
	private KeyGenerator keyGenerator;
	public CartValidator getCartValidator() {
		return cartValidator;
	}
	public void setCartValidator(CartValidator cartValidator) {
		this.cartValidator = cartValidator;
	}
	public CloneAbstractOrderStrategy getCloneAbstractOrderStrategy() {
		return cloneAbstractOrderStrategy;
	}
	public void setCloneAbstractOrderStrategy(CloneAbstractOrderStrategy cloneAbstractOrderStrategy) {
		this.cloneAbstractOrderStrategy = cloneAbstractOrderStrategy;
	}
	public KeyGenerator getKeyGenerator() {
		return keyGenerator;
	}
	public void setKeyGenerator(KeyGenerator keyGenerator) {
		this.keyGenerator = keyGenerator;
	}
	
	@Override
	public OrderModel createOrderFromCart(final CartModel cart) throws InvalidCartException
	{
		if (cartValidator != null)
		{
			cartValidator.validateCart(cart);
		}
		final OrderModel res = cloneAbstractOrderStrategy.clone(null, null, cart, generateOrderCode(cart), OrderModel.class,
				OrderEntryModel.class);

		return res;
	}
	
	protected String generateOrderCode(final CartModel cart)
	{
		final Object generatedValue = keyGenerator.generate();
		if (generatedValue instanceof String)
		{
			return "H"+new SimpleDateFormat("yyyyMMdd").format(new Date())+(String)generatedValue;
		}
		else
		{
			return "H"+new SimpleDateFormat("yyyyMMdd").format(new Date())+String.valueOf(generatedValue);
		}
	}
	

}
