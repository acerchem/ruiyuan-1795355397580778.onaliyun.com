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

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.log4j.Logger;

import de.hybris.platform.core.model.c2l.CurrencyModel;
import de.hybris.platform.core.model.order.AbstractOrderEntryModel;
import de.hybris.platform.core.model.order.AbstractOrderModel;
import de.hybris.platform.order.exceptions.CalculationException;
import de.hybris.platform.order.impl.DefaultCalculationService;
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
			double remainDiscountPrice = 0.0;
			final CurrencyModel curr = order.getCurrency();
			final int digits = curr.getDigits().intValue();
			// subtotal
			final double subtotal = order.getSubtotal().doubleValue();
			// discounts
			if(order.getUser().getUserLevel() != null && order.getUser().getUserLevel().getDiscount() != null && !"".equals(order.getUser().getUserLevel().getDiscount())){
				remainDiscountPrice = (order.getUser().getUserLevel().getDiscount())*(order.getSubtotal());
			}
			double totalDiscounts = calculateDiscountValues(order, recalculate);
			totalDiscounts =subtotal - remainDiscountPrice;
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
		entry.setBasePrice(Double.valueOf(basePrice.getValue()) * Double.valueOf(entry.getProduct().getNetWeight()));
		modelService.save(entry);
		final List<DiscountValue> entryDiscounts = findDiscountValues(entry);
		entry.setDiscountValues(entryDiscounts);
	}
}
