package com.acerchem.facades.populators;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;

import javax.annotation.Resource;

import de.hybris.platform.commercefacades.order.converters.populator.OrderPopulator;
import de.hybris.platform.commercefacades.order.data.OrderData;
import de.hybris.platform.commercefacades.order.data.OrderEntryData;
import de.hybris.platform.commercefacades.product.PriceDataFactory;
import de.hybris.platform.commercefacades.product.data.PriceDataType;
import de.hybris.platform.core.model.order.AbstractOrderEntryModel;
import de.hybris.platform.core.model.order.OrderModel;

/**
 * Created by Jacob.Ji on 2018/4/19.
 */
public class AcerchemOrderPopulator extends OrderPopulator {

    @Resource
    private PriceDataFactory priceDataFactory;

    @Override
    public void populate(OrderModel source, OrderData target){
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
		target.setPickupDateOfExtended(source.getPickupDateOfExtended());
		
        if (source.getStorageCost()!=null){
            target.setStorageCost(priceDataFactory.create(PriceDataType.BUY,
                    BigDecimal.valueOf(source.getStorageCost().doubleValue()), source.getCurrency().getIsocode()));
        }
        if (source.getOperateCost()!=null){
            target.setOperateCost(priceDataFactory.create(PriceDataType.BUY,
                    BigDecimal.valueOf(source.getOperateCost().doubleValue()), source.getCurrency().getIsocode()));
        }
        // modified by Jayson.wang
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        if(source.getPickUpDate()!=null){
            target.setPickUpdate(sdf.format(source.getPickUpDate()));
        }
        
        if(source.getDeliveyDate()!=null){
        	target.setDeliveyDate(sdf.format(source.getDeliveyDate()));
        }
        
        if(source.getWaitDeliveiedDate()!=null){
        	target.setWaitDeliveiedDate(sdf.format(source.getWaitDeliveiedDate()));
        }
        
        //add orderEntry's baseRealPrice and totalRealPrice by Jayson.wang
        for(AbstractOrderEntryModel entry: source.getEntries()) {
        	
        	for (int i=0 ;i<target.getEntries().size();i++){
        		OrderEntryData targetEntry = target.getEntries().get(i);
        		if (targetEntry.getEntryNumber().equals(entry.getEntryNumber())){
        			targetEntry.setBaseRealPrice(priceDataFactory.create(PriceDataType.BUY, BigDecimal.valueOf(entry.getBaseRealPrice().doubleValue()), source.getCurrency().getIsocode()));
        			targetEntry.setTotalRealPrice(priceDataFactory.create(PriceDataType.BUY, BigDecimal.valueOf(entry.getTotalRealPrice().doubleValue()), source.getCurrency().getIsocode()));
        			
        			target.getEntries().remove(i);
        			target.getEntries().add(i,targetEntry);

        		}
        		
        	}
        }

    }
}

