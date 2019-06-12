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

import static de.hybris.platform.servicelayer.util.ServicesUtil.validateParameterNotNull;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import com.acerchem.core.model.CountryTrayFareConfModel;
import com.acerchem.core.service.AcerchemStockService;
import com.acerchem.core.service.AcerchemTrayService;
import com.acerchem.core.strategies.AcerchemCommercePlaceOrderStrategy;

import de.hybris.platform.commerceservices.order.impl.DefaultCommercePlaceOrderStrategy;
import de.hybris.platform.commerceservices.service.data.CommerceCheckoutParameter;
import de.hybris.platform.commerceservices.service.data.CommerceOrderResult;
import de.hybris.platform.core.model.c2l.RegionModel;
import de.hybris.platform.core.model.order.AbstractOrderEntryModel;
import de.hybris.platform.core.model.order.AbstractOrderModel;
import de.hybris.platform.core.model.order.CartModel;
import de.hybris.platform.core.model.order.OrderModel;
import de.hybris.platform.core.model.product.ProductModel;
import de.hybris.platform.core.model.user.AddressModel;
import de.hybris.platform.core.model.user.CustomerModel;
import de.hybris.platform.order.InvalidCartException;
import de.hybris.platform.order.exceptions.CalculationException;
import de.hybris.platform.ordersplitting.model.WarehouseModel;
import de.hybris.platform.promotions.model.PromotionResultModel;
import de.hybris.platform.stock.exception.InsufficientStockLevelException;


public class DefaultAcerchemCommercePlaceOrderStrategy extends DefaultCommercePlaceOrderStrategy implements AcerchemCommercePlaceOrderStrategy
{
	private static final Logger LOG = Logger.getLogger(DefaultAcerchemCommercePlaceOrderStrategy.class);

	private AcerchemStockService acerchemStockService;
	
	@Resource
	private AcerchemTrayService acerchemTrayService;
	

	@Transactional
	@Override
	public CommerceOrderResult placeOrder(final CommerceCheckoutParameter parameter) throws InvalidCartException
	{
		final CartModel cartModel = parameter.getCart();
		validateParameterNotNull(cartModel, "Cart model cannot be null");
		final CommerceOrderResult result = new CommerceOrderResult();
		try
		{
			beforePlaceOrder(parameter);
			if (getCalculationService().requiresCalculation(cartModel))
			{
				// does not make sense to fail here especially since we don't fail below when we calculate order.
				// throw new IllegalArgumentException(String.format("Cart [%s] must be calculated", cartModel.getCode()));
				LOG.error(String.format("CartModel's [%s] calculated flag was false", cartModel.getCode()));
			}

			final CustomerModel customer = (CustomerModel) cartModel.getUser();
			validateParameterNotNull(customer, "Customer model cannot be null");

			final OrderModel orderModel = getOrderService().createOrderFromCart(cartModel);

			if (orderModel != null)
			{
				// Reset the Date attribute for use in determining when the order was placed
				orderModel.setDate(new Date());

				// Store the current site and store on the order
				orderModel.setSite(getBaseSiteService().getCurrentBaseSite());
				orderModel.setStore(getBaseStoreService().getCurrentBaseStore());
				orderModel.setLanguage(getCommonI18NService().getCurrentLanguage());

				if (parameter.getSalesApplication() != null)
				{
					orderModel.setSalesApplication(parameter.getSalesApplication());
				}

				// clear the promotionResults that where cloned from cart PromotionService.transferPromotionsToOrder will copy them over bellow.
				orderModel.setAllPromotionResults(Collections.<PromotionResultModel> emptySet());


				getModelService().saveAll(customer, orderModel);
				
				if (cartModel.getPaymentInfo() != null && cartModel.getPaymentInfo().getBillingAddress() != null)
				{
					final AddressModel billingAddress = cartModel.getPaymentInfo().getBillingAddress();
					orderModel.setPaymentAddress(billingAddress);
					orderModel.getPaymentInfo().setBillingAddress(getModelService().clone(billingAddress));
					getModelService().save(orderModel.getPaymentInfo());
				}
				
				if(cartModel.getDeliveryMode() != null){
					orderModel.setDeliveryMode(cartModel.getDeliveryMode());
				}
				if(cartModel.getDeliveryMode() != null && cartModel.getDeliveryMode().getCode().equalsIgnoreCase("DELIVERY_GROSS")){
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					String waitDelivereyDate = sdf.format(orderModel.getPickUpDate());
					Calendar ca = Calendar.getInstance();
					try {
						ca.setTime(sdf.parse(waitDelivereyDate));
						ca.add(Calendar.DATE, getTotalPriceForCart(orderModel));// 
						waitDelivereyDate = sdf.format(ca.getTime());
						Date endDate = sdf.parse(waitDelivereyDate);
						orderModel.setWaitDeliveriedDate(endDate);
					} catch (ParseException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
				
				if(cartModel.getDeliveryMode() != null && cartModel.getDeliveryMode().getCode().equalsIgnoreCase("DELIVERY_MENTION")){
					orderModel.setDeliveryAddress(cartModel.getDeliveryAddress());
					
				}
				List<AbstractOrderEntryModel> orderEntries = new ArrayList<AbstractOrderEntryModel>();
				for (AbstractOrderEntryModel aoe: orderModel.getEntries()){
					
					Long totalWeight = (aoe.getQuantity())*(Long.parseLong(aoe.getProduct().getNetWeight()));
					aoe.setTotalWeight(new Long(totalWeight).intValue());
					orderEntries.add(aoe);
				}
				
				orderModel.setEntries(orderEntries);
				
				getModelService().save(orderModel);
				// Transfer promotions to the order
				getPromotionsService().transferPromotionsToOrder(cartModel, orderModel, false);

				getModelService().refresh(orderModel);
				getModelService().refresh(customer);

				result.setOrder(orderModel);

				this.beforeSubmitOrder(parameter, result);

				try {
					calculateStockForCart(orderModel);
				} catch (InsufficientStockLevelException e) {
					LOG.error("calculateStock error :"+e.getMessage());
				}
				getOrderService().submitOrder(orderModel);
			}
			else
			{
				throw new IllegalArgumentException(String.format("Order was not properly created from cart %s", cartModel.getCode()));
			}
		}
		finally
		{
			getExternalTaxesService().clearSessionTaxDocument();
		}

		this.afterPlaceOrder(parameter, result);
		return result;
	}

	private void calculateStockForCart(OrderModel orderModel) throws InsufficientStockLevelException {
		for (AbstractOrderEntryModel aoe: orderModel.getEntries()){
			ProductModel productModel = aoe.getProduct();
			WarehouseModel warehouseModel = aoe.getDeliveryPointOfService().getWarehouses().get(0);
			Long quantity = aoe.getQuantity();
			boolean isUseFutureStock = aoe.getIsUseFutureStock();

			acerchemStockService.calculateStock(productModel,warehouseModel,quantity.intValue(),null,isUseFutureStock);
		}
	}

	@Required
	public void setAcerchemStockService(AcerchemStockService acerchemStockService) {
		this.acerchemStockService = acerchemStockService;
	}
	
	 private  Integer getTotalPriceForCart(AbstractOrderModel order){
		 	RegionModel regionModel = null;
			CountryTrayFareConfModel countryTrayFareConf  = null;
			//��������
			BigDecimal totalTrayAmount = BigDecimal.ZERO;
			if (order!=null){

				for (AbstractOrderEntryModel aoe : order.getEntries()){

					if (aoe.getDeliveryPointOfService().getAddress()!=null) {
						regionModel = aoe.getOrder().getDeliveryAddress().getRegion();
					}
					ProductModel productModel = aoe.getProduct();
					//�Ȼ�ȡ���̱������ڼ�������
					String unitCalculateRato = productModel.getUnitCalculateRato();
					if (ObjectUtils.isEmpty(unitCalculateRato)){
						
					}
					Long quantity = (aoe.getQuantity())*(Long.parseLong(aoe.getProduct().getNetWeight()));

					//��������
					BigDecimal entryTrayAmount = BigDecimal.valueOf(quantity).divide(new BigDecimal(unitCalculateRato),BigDecimal.ROUND_HALF_UP,BigDecimal.ROUND_DOWN);

					totalTrayAmount =totalTrayAmount.add(entryTrayAmount);
				}
			}
			if(regionModel != null){
				countryTrayFareConf = acerchemTrayService.getPriceByCountryAndTray(regionModel, (int) Math.ceil(totalTrayAmount.doubleValue()));
			}
			if(countryTrayFareConf != null){
				return countryTrayFareConf.getDeliveriedDay();
			}
			return 0;
		}
}
