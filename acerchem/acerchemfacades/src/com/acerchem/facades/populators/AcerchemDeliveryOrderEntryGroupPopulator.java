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


import de.hybris.platform.commercefacades.order.converters.populator.DeliveryOrderEntryGroupPopulator;
import de.hybris.platform.commercefacades.order.converters.populator.OrderEntryGroupPopulator;
import de.hybris.platform.commercefacades.order.data.AbstractOrderData;
import de.hybris.platform.commercefacades.order.data.DeliveryOrderEntryGroupData;
import de.hybris.platform.commercefacades.order.data.OrderEntryData;
import de.hybris.platform.core.model.order.AbstractOrderEntryModel;
import de.hybris.platform.core.model.order.AbstractOrderModel;
import de.hybris.platform.servicelayer.dto.converter.ConversionException;
import org.apache.commons.collections.CollectionUtils;

import java.util.ArrayList;


/**
 * Updates {@link AbstractOrderData} with order group data. Order entry data must be already populated in
 * {@link AbstractOrderData}.
 * 
 */
public class AcerchemDeliveryOrderEntryGroupPopulator extends DeliveryOrderEntryGroupPopulator
{

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.hybris.platform.converters.Populator#populate(java.lang.Object, java.lang.Object)
	 */
	@Override
	public void populate(final AbstractOrderModel source, final AbstractOrderData target) throws ConversionException
	{
		super.populate(source, target);

		if (target.getDeliveryOrderGroups() == null)
		{
			target.setDeliveryOrderGroups(new ArrayList<DeliveryOrderEntryGroupData>());
		}


		for (final AbstractOrderEntryModel entryModel : source.getEntries())
		{
			createUpdateShipGroupData(entryModel, target);
		}
		target.setDeliveryItemsQuantity(Long.valueOf(this.sumDeliveryItemsQuantity(source)));
	}



	protected void createUpdateShipGroupData(final AbstractOrderEntryModel entryModel, final AbstractOrderData target)
	{
		if (entryModel.getDeliveryPointOfService() == null)
		{
			DeliveryOrderEntryGroupData groupData = null;
			if (CollectionUtils.isNotEmpty(target.getDeliveryOrderGroups()))
			{
				groupData = target.getDeliveryOrderGroups().iterator().next();
			}
			if (groupData == null)
			{
				groupData = new DeliveryOrderEntryGroupData();
				groupData.setEntries(new ArrayList<OrderEntryData>());
				target.getDeliveryOrderGroups().add(groupData);
			}

			updateGroupTotalPriceWithTax(entryModel, groupData);
			groupData.getEntries().add(getOrderEntryData(target, entryModel.getEntryNumber()));
		}
	}

	public long sumDeliveryItemsQuantity(final AbstractOrderModel source)
	{
		long sum = 0;
		for (final AbstractOrderEntryModel entryModel : source.getEntries())
		{
//			if (entryModel.getDeliveryPointOfService() == null)
//			{
				sum += entryModel.getQuantity().longValue();
//			}
		}
		return sum;
	}

}
