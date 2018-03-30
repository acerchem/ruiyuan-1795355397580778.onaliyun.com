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
package com.acerchem.facades.populators;

import de.hybris.platform.commercefacades.order.data.AddToCartParams;
import de.hybris.platform.commerceservices.service.data.CommerceCartParameter;
import de.hybris.platform.converters.Populator;
import de.hybris.platform.servicelayer.dto.converter.ConversionException;


public class AcerchemCartParameterPopulator implements Populator<AddToCartParams, CommerceCartParameter>
{
	@Override
	public void populate(final AddToCartParams source, final CommerceCartParameter target) throws ConversionException
	{
		target.setWarehouseCode(source.getWarehouseCode());
		target.setIsUseFutureStock(source.getIsUseFutureStock());
		target.setAvailableDate(source.getAvailableDate());
	}

}
