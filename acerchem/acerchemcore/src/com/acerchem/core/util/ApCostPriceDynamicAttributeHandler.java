package com.acerchem.core.util;

import de.hybris.platform.europe1.model.PriceRowModel;
import de.hybris.platform.servicelayer.model.attribute.DynamicAttributeHandler;
import de.hybris.platform.util.DiscountValue;
import org.apache.commons.lang.StringUtils;

import java.math.BigDecimal;
import java.util.List;


public class ApCostPriceDynamicAttributeHandler implements DynamicAttributeHandler<String, PriceRowModel>
{
	public ApCostPriceDynamicAttributeHandler() {
	}

	public String get(PriceRowModel model) {
		BigDecimal defaultPriceBig = ConvertToBig(model.getPrice());

		BigDecimal costPrice = ConvertToBig(model.getPrice());
		BigDecimal customerCostPrice = ConvertToBig(model.getApCustomCost());
		BigDecimal customerDutyPrice = ConvertToBig(model.getApCustomDuty());
		BigDecimal midgroundMarginRate = ConvertToBig(model.getApMidgroundMarginRate());
		BigDecimal midgroundCostRate = ConvertToBig(model.getApMidgroudCostRate());
		BigDecimal capitalCostRate = ConvertToBig(model.getApCapitalCostRate());
		BigDecimal costRatio =  customerDutyPrice.add(midgroundMarginRate).add(midgroundCostRate).add(capitalCostRate);
		costPrice = costPrice.multiply(costRatio).add(customerCostPrice);
		costPrice = costPrice.setScale(2, BigDecimal.ROUND_HALF_UP);
		costPrice = defaultPriceBig.add(costPrice);
		return costPrice.toString();
	}

	private BigDecimal ConvertToBig(Object price){
		Double convertPrice = 0d;
		try
		{
			if(price instanceof Double){
				convertPrice = (Double) price;
			}else if(price instanceof String)
			{
				if (StringUtils.isNotBlank((String) price))
				{
					convertPrice = Double.valueOf((String) price);
				}
			}

			BigDecimal costPrice = new BigDecimal(convertPrice);
			costPrice = costPrice.setScale(2, BigDecimal.ROUND_HALF_UP);
			return costPrice;
		}catch (Exception e){
			// convert fail
		}
		return new BigDecimal(0d);
	}

	public void set(PriceRowModel model, String xx) {
	}
}
