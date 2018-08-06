package com.acerchem.facades.populators;

import java.text.SimpleDateFormat;

import de.hybris.platform.commercefacades.order.converters.populator.OrderHistoryPopulator;
import de.hybris.platform.commercefacades.order.data.OrderHistoryData;
import de.hybris.platform.converters.Populator;
import de.hybris.platform.core.model.order.OrderModel;
import de.hybris.platform.servicelayer.dto.converter.ConversionException;

public class AcerchemOrderHistoryPopulator extends OrderHistoryPopulator implements Populator<OrderModel, OrderHistoryData>
{
	@Override
	public void populate(OrderModel source, OrderHistoryData target) throws ConversionException {
		super.populate(source,target);
		target.setCustomerConfirm(source.getCustomerConfirm());
		target.setEmployeeConfirm(source.getEmployeeConfirm());
		target.setDeliveryConfirm(source.getDeliveryConfirm());
		target.setPayConfirm(source.getPayConfirm());
		target.setCustomerConfirmPay(source.getCustomerConfirmPay());
		target.setEmployeeConfirmPay(source.getEmployeeConfirmPay());
		target.setCustomerConfirmDelivery(source.getCustomerConfirmDelivery());
		target.setEmployeeConfirmDelivery(source.getEmployeeConfirmDelivery());
		target.setPickUpDate(source.getPickUpDate());
		target.setPlacedStr(new SimpleDateFormat("d/M/yyyy hh:mm:ss").format(source.getCreationtime()));
		 // added by Jayson.wang
        SimpleDateFormat sdf = new SimpleDateFormat("d/M/yyyy");
        if(source.getDeliveyDate()!=null){
        	target.setDeliveyDate(sdf.format(source.getDeliveyDate()));
        }
        
        if(source.getWaitDeliveiedDate()!=null){
        	target.setWaitDeliveiedDate(sdf.format(source.getWaitDeliveiedDate()));
        }

	}

}

