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
package com.acerchem.core.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Required;

import de.hybris.platform.core.model.c2l.CurrencyModel;
import de.hybris.platform.core.model.order.AbstractOrderEntryModel;
import de.hybris.platform.core.model.order.AbstractOrderModel;
import de.hybris.platform.order.exceptions.CalculationException;
import de.hybris.platform.order.impl.DefaultCalculationService;
import de.hybris.platform.order.strategies.calculation.FindDiscountValuesStrategy;
import de.hybris.platform.order.strategies.calculation.OrderRequiresCalculationStrategy;
import de.hybris.platform.servicelayer.i18n.CommonI18NService;
import de.hybris.platform.servicelayer.model.ModelService;
import de.hybris.platform.util.DiscountValue;
import de.hybris.platform.util.PriceValue;
import de.hybris.platform.util.TaxValue;


/**
 *
 */
public class DefaultAcerchemCalculationService extends DefaultCalculationService
{

	private static final Logger LOG = Logger.getLogger(DefaultAcerchemCalculationService.class);

	@Resource
	private OrderRequiresCalculationStrategy orderRequiresCalculationStrategy;
	
	@Resource
	private ModelService modelService;

	@Resource
	private CommonI18NService commonI18NService;

	protected void calculateTotals(final AbstractOrderModel order, final boolean recalculate,
			final Map<TaxValue, Map<Set<TaxValue>, Double>> taxValueMap) throws CalculationException
	{
		if (recalculate || orderRequiresCalculationStrategy.requiresCalculation(order))
		{
			final CurrencyModel curr = order.getCurrency();
			final int digits = curr.getDigits().intValue();
			// subtotal
			final double subtotal = order.getSubtotal().doubleValue();
			// discounts
			
			double totalDiscounts = calculateDiscountValues(order, recalculate);
			totalDiscounts += getProductsDiscountsAmount(order);
			final double roundedTotalDiscounts = commonI18NService.roundCurrency(totalDiscounts, digits);
			order.setTotalDiscounts(Double.valueOf(roundedTotalDiscounts));
			// set total
			double total =0.0d;
			total = subtotal + total;
//			if (order.getDeliveryCost()!=null){
//				total = subtotal + total + order.getDeliveryCost().doubleValue();
//			}
			if (order.getPaymentCost()!=null){
				total = total + order.getPaymentCost().doubleValue();
			}
//			if(order.getOperateCost()!=null){
//				total = total +order.getOperateCost().doubleValue();
//			}
//			if (order.getStorageCost()!=null){
//				total = total +order.getStorageCost().doubleValue();
//			}
			total = total - roundedTotalDiscounts;

			final double totalRounded = commonI18NService.roundCurrency(total, digits);
			order.setTotalPrice(Double.valueOf(totalRounded));
			// taxes
			final double totalTaxes = calculateTotalTaxValues(//
					order, recalculate, //
					digits, //
					getTaxCorrectionFactor(taxValueMap, subtotal, total, order), //
					taxValueMap);//
			final double totalRoundedTaxes = commonI18NService.roundCurrency(totalTaxes, digits);
			order.setTotalTax(Double.valueOf(totalRoundedTaxes));
			setCalculatedStatus(order);
			saveOrder(order);
		}
	}

	@Override
	public void calculate(final AbstractOrderModel order) throws CalculationException
	{
		if (orderRequiresCalculationStrategy.requiresCalculation(order))
		{
			// -----------------------------
			// first calc all entries
			calculateEntries(order, false);
			// -----------------------------
			// reset own values
			final Map taxValueMap = resetAllValues(order);
			// -----------------------------
			// now calculate all totals
			calculateTotals(order, false, taxValueMap);
			// notify manual discouns - needed?
			//notifyDiscountsAboutCalculation();
		}
	}
	
	public void calculateEntries(final AbstractOrderModel order, final boolean forceRecalculate) throws CalculationException
	{
		double subtotal = 0.0;
		for (final AbstractOrderEntryModel e : order.getEntries())
		{
			recalculateOrderEntryIfNeeded(e, forceRecalculate);
			subtotal += (e.getTotalPrice());
		}
		order.setTotalPrice(Double.valueOf(subtotal));

	}
	
	protected void resetAllValues(final AbstractOrderEntryModel entry) throws CalculationException
	{
		// taxes
		final Collection<TaxValue> entryTaxes = findTaxValues(entry);
		entry.setTaxValues(entryTaxes);
		final PriceValue pv = findBasePrice(entry);
		final AbstractOrderModel order = entry.getOrder();
		final PriceValue basePrice = convertPriceIfNecessary(pv, order.getNet().booleanValue(), order.getCurrency(), entryTaxes);
		//entry.setBasePrice(Double.valueOf(basePrice.getValue()));
		if(order.getUser().getUserLevel() != null && order.getUser().getUserLevel().getDiscount() != null &&  !"".equals(order.getUser().getUserLevel().getDiscount())){
			double userLevelPrice = (Double.valueOf(basePrice.getValue())* order.getUser().getUserLevel().getDiscount());
			entry.setBasePrice(userLevelPrice * Double.valueOf(entry.getProduct().getNetWeight()));
		}else{
			entry.setBasePrice(Double.valueOf(basePrice.getValue()) * Double.valueOf(entry.getProduct().getNetWeight()));
		}
		//modelService.save(entry);
		final List<DiscountValue> entryDiscounts = findDiscountValues(entry);
		entry.setDiscountValues(entryDiscounts);
	}
	
	
	protected double calculateDiscountValues(final AbstractOrderModel order, final boolean recalculate)
	{
		if (recalculate || orderRequiresCalculationStrategy.requiresCalculation(order))
		{
			
			
			final List<DiscountValue> discountValues = order.getGlobalDiscountValues();
			if (discountValues != null && !discountValues.isEmpty())
			{
				// clean discount value list -- do we still need it?
				//				removeAllGlobalDiscountValues();
				//
				final CurrencyModel curr = order.getCurrency();
				final String iso = curr.getIsocode();

				final int digits = curr.getDigits().intValue();
				final double discountablePrice = order.getSubtotal().doubleValue()
						+ (order.isDiscountsIncludePaymentCost() ? order.getPaymentCost().doubleValue() : 0.0);
				/*
				 * apply discounts to this order's total
				 */
				final List appliedDiscounts = DiscountValue.apply(1.0, discountablePrice, digits,
						convertDiscountValues(order, discountValues), iso);
				// store discount values
				order.setGlobalDiscountValues(appliedDiscounts);
				return DiscountValue.sumAppliedValues(appliedDiscounts);
			}
			return 0.0;
		}
		else
		{
			return DiscountValue.sumAppliedValues(order.getGlobalDiscountValues());
		}
	}
	
	protected double getProductsDiscountsAmount(final AbstractOrderModel source)
	{
		double discounts = 0.0d;

		final List<AbstractOrderEntryModel> entries = source.getEntries();
		if (entries != null)
		{
			for (final AbstractOrderEntryModel entry : entries)
			{
				final List<DiscountValue> discountValues = entry.getDiscountValues();
				if (discountValues != null)
				{
					for (final DiscountValue dValue : discountValues)
					{
						discounts += dValue.getAppliedValue();
					}
				}
			}
		}
		return discounts;
	}
	
	
}
