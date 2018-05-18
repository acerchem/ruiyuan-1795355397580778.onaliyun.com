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

import de.hybris.platform.core.model.order.OrderModel;
import de.hybris.platform.core.model.user.AddressModel;
import de.hybris.platform.orderprocessing.model.OrderProcessModel;
import de.hybris.platform.processengine.action.AbstractAction;
import de.hybris.platform.task.RetryLaterException;
import java.util.HashSet;
import java.util.Set;
import org.apache.log4j.Logger;


/**
 * This action check if authorization has review status
 */
public class ChooseInvoiceTempAction extends AbstractAction<OrderProcessModel>
{
	private static final Logger LOG = Logger.getLogger(ChooseInvoiceTempAction.class);

	public enum Transition
	{
		//A , B , C , D , E , F
		TEMP1, TEMP2 , TEMP3, TEMP4 , TEMP5, TEMP6 , NOK;

		public static Set<String> getStringValues()
		{
			final Set<String> res = new HashSet<String>();
			for (final Transition transitions : Transition.values())
			{
				res.add(transitions.toString());
			}
			return res;
		}
	}

	@Override
	public Set<String> getTransitions()
	{
		return Transition.getStringValues();
	}

	@Override
	public final String execute(final OrderProcessModel process) throws RetryLaterException, Exception
	{
		return executeAction(process).toString();
	}

	protected Transition executeAction(final OrderProcessModel process)
	{
		final OrderModel order = process.getOrder();
		LOG.info("-------------------------------------"+order.getEntries().get(0).getDeliveryPointOfService().getDeliveryZone().getCode());
		
		AddressModel addressModel = null;
		if(order.getDeliveryMode().getCode().equals("DELIVERY_MENTION"))
		{//自提
			for(AddressModel address:order.getUser().getAddresses())
			{
				if(address.getContactAddress())
				{
					addressModel=address;
				}
			}
		}
		else
		{//送货
			addressModel=order.getDeliveryAddress();
		}
		
		if("Neele-vat".equals(order.getEntries().get(0).getDeliveryPointOfService().getWarehouses().get(0).getCode())){
			LOG.info("-------------------Neele-vat +  TEMP4------------------");
			return Transition.TEMP4;
		}else if("UK".equals(order.getEntries().get(0).getDeliveryPointOfService().getWarehouses().get(0).getCode())){
			if("GB".equals(addressModel.getCountry().getIsocode())){
				LOG.info("-------------------GB +  TEMP2------------------"+addressModel.getCountry().getIsocode());
				return Transition.TEMP2;
			}
			else
			{
				LOG.info("------------------- TEMP3------------------"+addressModel.getCountry().getIsocode());
				return Transition.TEMP3;
			}
		}
		return Transition.NOK; 
	}
}
