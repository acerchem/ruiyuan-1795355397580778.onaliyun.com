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
package com.acerchem.core.strategies.impl;

import com.acerchem.core.model.CountryTrayFareConfModel;
import com.acerchem.core.service.AcerchemTrayService;
import com.acerchem.core.strategies.AcerchemFindDeliveryCostStrategy;
import de.hybris.platform.commercefacades.order.data.DeliveryModeData;
import de.hybris.platform.commercefacades.order.data.ZoneDeliveryModeData;
import de.hybris.platform.commercefacades.product.data.PriceDataType;
import de.hybris.platform.core.model.c2l.CountryModel;
import de.hybris.platform.core.model.order.AbstractOrderEntryModel;
import de.hybris.platform.core.model.order.AbstractOrderModel;
import de.hybris.platform.core.model.order.CartModel;
import de.hybris.platform.core.model.order.delivery.DeliveryModeModel;
import de.hybris.platform.core.model.product.ProductModel;
import de.hybris.platform.deliveryzone.model.ZoneDeliveryModeModel;
import de.hybris.platform.jalo.order.AbstractOrder;
import de.hybris.platform.jalo.order.delivery.DeliveryMode;
import de.hybris.platform.order.CartService;
import de.hybris.platform.order.strategies.calculation.FindDeliveryCostStrategy;
import de.hybris.platform.order.strategies.calculation.impl.DefaultFindDeliveryCostStrategy;
import de.hybris.platform.servicelayer.config.ConfigurationService;
import de.hybris.platform.servicelayer.dto.converter.Converter;
import de.hybris.platform.servicelayer.util.ServicesUtil;
import de.hybris.platform.util.PriceValue;
import org.apache.log4j.Logger;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.math.BigDecimal;

public class DefaultAcerchemFindDeliveryCostStrategy extends DefaultFindDeliveryCostStrategy implements AcerchemFindDeliveryCostStrategy
{

	private static final Logger LOG = Logger.getLogger(DefaultAcerchemFindDeliveryCostStrategy.class);

	//自提
	private final String DELIVERY_MENTION = "DELIVERY_MENTION";
	//送货
	private final String DELIVERY_GROSS = "DELIVERY_GROSS";
	private final String DELIVERY_MENTION_FEE ="delivery.mention.storage.fee";
	private final String ORDER_OPERATION_FEE ="order.operation.fee";
	private final String ORDER_STANDARD_CRITICAL_FEE ="order.standard.critical.price";
	//默认存储费
	private final String defaultStorageFee = "30";
	private final String defaultOrderOperationFee = "100";
	private final String defaultOrderStandardFee = "10000";

	@Resource
	private ConfigurationService configurationService;
	@Resource
	private CartService cartService;
	@Resource
	private AcerchemTrayService acerchemTrayService;


	@Override
	public PriceValue getDeliveryCost(final AbstractOrderModel order)
	{
		ServicesUtil.validateParameterNotNullStandardMessage("order", order);
		try
		{
			DeliveryModeModel deliveryMode = order.getDeliveryMode();
			if( deliveryMode != null )
			{
				getModelService().save(order);
				if (deliveryMode instanceof ZoneDeliveryModeModel) {
					final AbstractOrder orderItem = getModelService().getSource(order);
					final DeliveryMode dModeJalo = getModelService().getSource(deliveryMode);
					return dModeJalo.getCost(orderItem);
				}else {
					return convert(order);
				}
			}
			else
			{
				return new PriceValue(order.getCurrency().getIsocode(), 0.0, order.getNet().booleanValue()); 
			}
		}
		catch (final Exception e)
		{
			LOG.warn("Could not find deliveryCost for order [" + order.getCode() + "] due to : " + e + "... skipping!");
			return new PriceValue(order.getCurrency().getIsocode(), 0.0, order.getNet().booleanValue());
		}
	}

	protected PriceValue convert(final AbstractOrderModel order){

		final DeliveryModeModel deliveryModeModel = order.getDeliveryMode();
		PriceValue deliveryCost = null;
			if (order != null)
			{
				double orderTotalPrice  = order.getTotalPrice();
				String orderStandardFee = configurationService.getConfiguration().getString(ORDER_STANDARD_CRITICAL_FEE,defaultOrderStandardFee);
				BigDecimal operationFee = BigDecimal.ZERO;
				//add operate fee
				if (orderTotalPrice <= Double.valueOf(orderStandardFee)){
					String orderOperationFee = configurationService.getConfiguration().getString(ORDER_OPERATION_FEE,defaultOrderOperationFee);
					operationFee = operationFee.add(BigDecimal.valueOf(Double.valueOf(orderOperationFee)));
					order.setOperateCost(operationFee.doubleValue());
				}


				//自提运费和存储费用改造
				if (DELIVERY_MENTION.equals(deliveryModeModel.getCode())){
					//存储费
					String deliveryMetionPrice = configurationService.getConfiguration().getString(DELIVERY_MENTION_FEE,defaultStorageFee);
					order.setStorageCost(Double.valueOf(deliveryMetionPrice));
					order.setDeliveryCost(0.0d);
					//运费为0
					deliveryCost = new PriceValue(order.getCurrency().getIsocode(), 0.0d , true);

				}else if (DELIVERY_GROSS.equals(deliveryModeModel.getCode())){
					BigDecimal fee = BigDecimal.valueOf(0.0d);
					//托盘运输费
					fee =  BigDecimal.valueOf(getTotalPriceForCart());
					order.setDeliveryCost(fee.doubleValue());
					order.setStorageCost(0.0d);

					deliveryCost = new PriceValue(order.getCurrency().getIsocode(), fee.doubleValue(), true);
				}
			}
			getModelService().save(order);
		return deliveryCost;
	}


	private  double getTotalPriceForCart(){
		double totalTrayPrice = 0.0d;
		CountryModel countryModel = null;
		//托盘数量
		BigDecimal totalTrayAmount = BigDecimal.ZERO;
		if (cartService.hasSessionCart()){
			CartModel cartModel = cartService.getSessionCart();

			for (AbstractOrderEntryModel aoe : cartModel.getEntries()){

				if (aoe.getDeliveryPointOfService().getAddress()!=null) {
					countryModel = aoe.getDeliveryPointOfService().getAddress().getCountry();
				}
				ProductModel productModel = aoe.getProduct();
				//先获取托盘比例，在计算数量
				String unitCalculateRato = productModel.getUnitCalculateRato();
				if (ObjectUtils.isEmpty(unitCalculateRato)){
					LOG.error("当前商品未配置托盘比例,产品编号："+productModel.getCode());
				}
				Long quantity = aoe.getQuantity();

				//托盘数量
				BigDecimal entryTrayAmount = BigDecimal.valueOf(quantity).divide(new BigDecimal(unitCalculateRato),BigDecimal.ROUND_HALF_UP,BigDecimal.ROUND_DOWN);

				totalTrayAmount =totalTrayAmount.add(entryTrayAmount);
			}
		}

		CountryTrayFareConfModel countryTrayFareConf = acerchemTrayService.getPriceByCountryAndTray(countryModel, (int) Math.ceil(totalTrayAmount.doubleValue()));
		if (countryTrayFareConf!=null){
			totalTrayPrice = countryTrayFareConf.getPrice();
		}
		return totalTrayPrice;
	}



}
