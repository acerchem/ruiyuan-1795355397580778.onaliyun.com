package com.acerchem.facades.populators;

import de.hybris.platform.commercefacades.order.converters.populator.OrderPopulator;
import de.hybris.platform.commercefacades.order.data.OrderData;
import de.hybris.platform.commercefacades.product.PriceDataFactory;
import de.hybris.platform.commercefacades.product.data.PriceDataType;
import de.hybris.platform.core.model.order.OrderModel;
import javax.annotation.Resource;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;

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
        if (source.getStorageCost()!=null){
            target.setStorageCost(priceDataFactory.create(PriceDataType.BUY,
                    BigDecimal.valueOf(source.getStorageCost().doubleValue()), source.getCurrency().getIsocode()));
        }
        if (source.getOperateCost()!=null){
            target.setOperateCost(priceDataFactory.create(PriceDataType.BUY,
                    BigDecimal.valueOf(source.getOperateCost().doubleValue()), source.getCurrency().getIsocode()));
        }
        if(source.getPickUpDate()!=null){
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-DD");
            target.setPickUpdate(sdf.format(source.getPickUpDate()));
        }

    }
}

