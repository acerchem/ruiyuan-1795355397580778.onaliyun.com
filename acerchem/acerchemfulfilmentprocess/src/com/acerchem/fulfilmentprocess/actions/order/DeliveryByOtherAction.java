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
package com.acerchem.fulfilmentprocess.actions.order;

import de.hybris.platform.ordersplitting.model.ConsignmentModel;
import de.hybris.platform.ordersplitting.model.ConsignmentProcessModel;
import de.hybris.platform.processengine.action.AbstractProceduralAction;
import de.hybris.platform.warehouse.Process2WarehouseAdapter;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Required;


public class DeliveryByOtherAction extends AbstractProceduralAction<ConsignmentProcessModel>
{
	private static final Logger LOG = Logger.getLogger(DeliveryByOtherAction.class);


	@Override
	public void executeAction(final ConsignmentProcessModel process)
	{
		final ConsignmentModel consignment = process.getConsignment();
		
		if(consignment != null){
				
		}
	}	
}
