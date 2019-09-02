package com.acerchem.facades.populators;

import de.hybris.platform.commercefacades.order.converters.populator.CartPopulator;
import de.hybris.platform.commercefacades.order.converters.populator.OrderPopulator;
import de.hybris.platform.commercefacades.order.data.AbstractOrderData;
import de.hybris.platform.commercefacades.order.data.CartData;
import de.hybris.platform.commercefacades.order.data.OrderData;
import de.hybris.platform.commercefacades.order.data.OrderEntryData;
import de.hybris.platform.commercefacades.product.PriceDataFactory;
import de.hybris.platform.commercefacades.product.data.PriceData;
import de.hybris.platform.commercefacades.product.data.PriceDataType;
import de.hybris.platform.core.model.order.AbstractOrderEntryModel;
import de.hybris.platform.core.model.order.AbstractOrderModel;
import de.hybris.platform.core.model.order.CartModel;
import de.hybris.platform.core.model.order.OrderModel;
import de.hybris.platform.ordersplitting.model.StockLevelModel;
import de.hybris.platform.stock.StockService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Jacob.Ji on 2018/4/19.
 */
public class AcerchemCartPopulator extends CartPopulator {

	private static final Logger LOG = LoggerFactory.getLogger(AcerchemCartPopulator.class);
    @Resource
    private PriceDataFactory priceDataFactory;
    @Resource
    private StockService stockService;

    @Override
    public void populate(CartModel source, CartData target){
        super.populate(source,target);
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
        if (source.getEntries()!=null && source.getEntries().size()>0){
            boolean isUsefutureStock = source.getEntries().get(0).getIsUseFutureStock();
            target.setIsUseFutureStock(isUsefutureStock);
            for (AbstractOrderEntryModel aoe: source.getEntries()){
                if (isUsefutureStock){
                    StockLevelModel stockLevelModel = stockService.getStockLevel(aoe.getProduct(),aoe.getDeliveryPointOfService().getWarehouses().get(0));
                    target.setDeliveryDays(stockLevelModel.getPreOrderReleaseDay());
                }else{
                    StockLevelModel stockLevelModel = stockService.getStockLevel(aoe.getProduct(),aoe.getDeliveryPointOfService().getWarehouses().get(0));
                    target.setDeliveryDays(stockLevelModel.getAvaPreOrderReleaseDay());
                }
            }
            
            Double subTotal = (double) 0;
            for (OrderEntryData orderEntryData : target.getEntries()) {
            	
            	String netWeight = orderEntryData.getProduct().getNetWeight();
            	
            	long items = orderEntryData.getQuantity();
            	
            	BigDecimal basePrice = orderEntryData.getBasePrice().getValue();
            	long totalWeight = items* Long.valueOf(netWeight);
            	//Double totalPrice = Long.valueOf(basePrice)*totalWeight;
            	
            	Double totalPrice =basePrice.doubleValue()*totalWeight;
            	
            	subTotal += totalPrice;
            	
            	orderEntryData.setTotalPrice(createPrice(source, totalPrice));
            }
            
            target.setSubTotal(createPrice(source, subTotal));
        }
        if (source.getAllPromotionResults()!=null) {
            for (OrderEntryData orderEntryData : target.getEntries()) {
            	 Long quantity = orderEntryData.getQuantity();
            	 String code = orderEntryData.getProduct()!=null?orderEntryData.getProduct().getCode():"";
					 LOG.info("entry code:"+code+" quantity:"+quantity);
					 BigDecimal basePrice = BigDecimal.valueOf(0d);
            	 if(quantity!=null&&quantity>0){
						PriceData totalPriceData = orderEntryData.getTotalPrice();
            	 	if(totalPriceData!=null&&totalPriceData.getValue()!=null && totalPriceData.getValue().doubleValue()>0)
						{
							BigDecimal totalValue = totalPriceData.getValue();
							totalValue = totalValue.setScale(4, BigDecimal.ROUND_HALF_UP);
							LOG.info("entry code:"+code+" tprice value:"+totalValue);
							basePrice = totalValue.divide(BigDecimal.valueOf(quantity)).setScale(2, BigDecimal.ROUND_HALF_UP);
						}
					 }

                PriceData promotionBasePrice = priceDataFactory.create(PriceDataType.BUY,
                        BigDecimal.valueOf(basePrice.doubleValue()), source.getCurrency().getIsocode());
                orderEntryData.setPromotionBasePrice(promotionBasePrice);

            }
        }
    }
    ///orderEntry.getOrder().getCurrency()
    
    protected PriceData createPrice(final CartModel cardModel, final Double val)
	{
		return getPriceDataFactory().create(PriceDataType.BUY, BigDecimal.valueOf(val.doubleValue()),
				cardModel.getCurrency());
	}
}
