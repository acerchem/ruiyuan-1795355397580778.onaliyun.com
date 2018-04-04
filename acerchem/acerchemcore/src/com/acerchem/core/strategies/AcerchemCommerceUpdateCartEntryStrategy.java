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
package com.acerchem.core.strategies;

import de.hybris.platform.commerceservices.order.CommerceCartModification;
import de.hybris.platform.commerceservices.order.CommerceCartModificationException;
import de.hybris.platform.commerceservices.service.data.CommerceCartParameter;

public interface AcerchemCommerceUpdateCartEntryStrategy
{
	CommerceCartModification updateQuantityForCartEntry(final CommerceCartParameter parameters) throws CommerceCartModificationException;

	CommerceCartModification updatePointOfServiceForCartEntry(final CommerceCartParameter parameters) throws CommerceCartModificationException;
}
