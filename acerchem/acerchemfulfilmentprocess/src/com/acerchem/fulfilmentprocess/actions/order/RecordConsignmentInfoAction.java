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

import org.apache.log4j.Logger;

import de.hybris.platform.orderprocessing.model.OrderProcessModel;
import de.hybris.platform.processengine.action.AbstractProceduralAction;


public class RecordConsignmentInfoAction extends AbstractProceduralAction<OrderProcessModel>
{
	private static final Logger LOG = Logger.getLogger(RecordConsignmentInfoAction.class);


	@Override
	public void executeAction(final OrderProcessModel process)
	{
		LOG.info("=================RecordConsignmentInfoAction================");
	}

}
