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

			final double totalDiscounts = calculateDiscountValues(order, recalculate);
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
		double entrySubtotal = 0.0;
		for (final AbstractOrderEntryModel e : order.getEntries())
		{
			recalculateOrderEntryIfNeeded(e, forceRecalculate);
			subtotal += (e.getBasePrice() * Double.parseDouble(e.getProduct().getNetWeight())* e.getQuantity());
			entrySubtotal = (e.getBasePrice() * Double.parseDouble(e.getProduct().getNetWeight())* e.getQuantity());
			e.setTotalPrice(entrySubtotal);
			modelService.save(e);
		}
		order.setTotalPrice(Double.valueOf(subtotal));

	}
}
