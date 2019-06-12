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
    public void populate(final OrderModel source, final OrderData target){
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
		target.setOrderRemark(source.getOrderRemark());
		
		/*final Double totalDiscounts = source.getTotalDiscounts()==null?0:source.getTotalDiscounts();
		target.setTotalDiscounts(priceDataFactory.create(PriceDataType.BUY, BigDecimal.valueOf(totalDiscounts.doubleValue()), source.getCurrency().getIsocode()));
		*/
        if (source.getStorageCost()!=null){
            target.setStorageCost(priceDataFactory.create(PriceDataType.BUY,
                    BigDecimal.valueOf(source.getStorageCost().doubleValue()), source.getCurrency().getIsocode()));
        }
        if (source.getOperateCost()!=null){
            target.setOperateCost(priceDataFactory.create(PriceDataType.BUY,
                    BigDecimal.valueOf(source.getOperateCost().doubleValue()), source.getCurrency().getIsocode()));
        }
        // modified by Jayson.wang
        final SimpleDateFormat sdf = new SimpleDateFormat("d/M/yyyy");
        if(source.getPickUpDate()!=null){
            target.setPickUpdate(sdf.format(source.getPickUpDate()));
        }
        
        if(source.getDeliveryDate()!=null){
        	target.setDeliveyDate(sdf.format(source.getDeliveryDate()));
        }
        
        if(source.getWaitDeliveriedDate()!=null){
        	target.setWaitDeliveiedDate(sdf.format(source.getWaitDeliveriedDate()));
        }
        
        //add orderEntry's baseRealPrice and totalRealPrice by Jayson.wang
        for(final AbstractOrderEntryModel entry: source.getEntries()) {
        	
        	for (int i=0 ;i<target.getEntries().size();i++){
        		final OrderEntryData targetEntry = target.getEntries().get(i);
        		if (targetEntry.getEntryNumber().equals(entry.getEntryNumber())){
        			final Double baseReal = entry.getBaseRealPrice()==null?0:entry.getBaseRealPrice();
        			final Double totalReal = entry.getTotalRealPrice()==null?0:entry.getTotalRealPrice();
        			//final Double baseMemberPrice = entry.getTotalRealPrice()==null?0:entry.getBaseMemberlPrice();
        			targetEntry.setBaseRealPrice(priceDataFactory.create(PriceDataType.BUY, BigDecimal.valueOf(baseReal.doubleValue()), source.getCurrency().getIsocode()));
        			targetEntry.setTotalRealPrice(priceDataFactory.create(PriceDataType.BUY, BigDecimal.valueOf(totalReal.doubleValue()), source.getCurrency().getIsocode()));
        			targetEntry.setTotalWeight(entry.getTotalWeight());
        			
        			if (entry.getBaseMemberlPrice() != null){
        			    targetEntry.setPromotionBasePrice(priceDataFactory.create(PriceDataType.BUY, BigDecimal.valueOf(entry.getBaseMemberlPrice().doubleValue()), source.getCurrency().getIsocode()));
        			}
        			
        			target.getEntries().remove(i);
        			target.getEntries().add(i,targetEntry);

        		}
        		
        	}
        }

    }
}

