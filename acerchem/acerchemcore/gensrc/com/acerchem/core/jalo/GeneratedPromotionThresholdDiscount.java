/*
 * ----------------------------------------------------------------
 * --- WARNING: THIS FILE IS GENERATED AND WILL BE OVERWRITTEN! ---
 * --- Generated at 2018-4-25 8:00:37                           ---
 * ----------------------------------------------------------------
 */
package com.acerchem.core.jalo;

import com.acerchem.core.constants.AcerchemCoreConstants;
import de.hybris.platform.jalo.GenericItem;
import de.hybris.platform.jalo.Item.AttributeMode;
import de.hybris.platform.jalo.SessionContext;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Generated class for type {@link de.hybris.platform.jalo.GenericItem PromotionThresholdDiscount}.
 */
@SuppressWarnings({"deprecation","unused","cast","PMD"})
public abstract class GeneratedPromotionThresholdDiscount extends GenericItem
{
	/** Qualifier of the <code>PromotionThresholdDiscount.minQuantity</code> attribute **/
	public static final String MINQUANTITY = "minQuantity";
	/** Qualifier of the <code>PromotionThresholdDiscount.maxQuantity</code> attribute **/
	public static final String MAXQUANTITY = "maxQuantity";
	/** Qualifier of the <code>PromotionThresholdDiscount.percentageDiscount</code> attribute **/
	public static final String PERCENTAGEDISCOUNT = "percentageDiscount";
	protected static final Map<String, AttributeMode> DEFAULT_INITIAL_ATTRIBUTES;
	static
	{
		final Map<String, AttributeMode> tmp = new HashMap<String, AttributeMode>();
		tmp.put(MINQUANTITY, AttributeMode.INITIAL);
		tmp.put(MAXQUANTITY, AttributeMode.INITIAL);
		tmp.put(PERCENTAGEDISCOUNT, AttributeMode.INITIAL);
		DEFAULT_INITIAL_ATTRIBUTES = Collections.unmodifiableMap(tmp);
	}
	@Override
	protected Map<String, AttributeMode> getDefaultAttributeModes()
	{
		return DEFAULT_INITIAL_ATTRIBUTES;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>PromotionThresholdDiscount.maxQuantity</code> attribute.
	 * @return the maxQuantity
	 */
	public Integer getMaxQuantity(final SessionContext ctx)
	{
		return (Integer)getProperty( ctx, MAXQUANTITY);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>PromotionThresholdDiscount.maxQuantity</code> attribute.
	 * @return the maxQuantity
	 */
	public Integer getMaxQuantity()
	{
		return getMaxQuantity( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>PromotionThresholdDiscount.maxQuantity</code> attribute. 
	 * @return the maxQuantity
	 */
	public int getMaxQuantityAsPrimitive(final SessionContext ctx)
	{
		Integer value = getMaxQuantity( ctx );
		return value != null ? value.intValue() : 0;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>PromotionThresholdDiscount.maxQuantity</code> attribute. 
	 * @return the maxQuantity
	 */
	public int getMaxQuantityAsPrimitive()
	{
		return getMaxQuantityAsPrimitive( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>PromotionThresholdDiscount.maxQuantity</code> attribute. 
	 * @param value the maxQuantity
	 */
	public void setMaxQuantity(final SessionContext ctx, final Integer value)
	{
		setProperty(ctx, MAXQUANTITY,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>PromotionThresholdDiscount.maxQuantity</code> attribute. 
	 * @param value the maxQuantity
	 */
	public void setMaxQuantity(final Integer value)
	{
		setMaxQuantity( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>PromotionThresholdDiscount.maxQuantity</code> attribute. 
	 * @param value the maxQuantity
	 */
	public void setMaxQuantity(final SessionContext ctx, final int value)
	{
		setMaxQuantity( ctx,Integer.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>PromotionThresholdDiscount.maxQuantity</code> attribute. 
	 * @param value the maxQuantity
	 */
	public void setMaxQuantity(final int value)
	{
		setMaxQuantity( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>PromotionThresholdDiscount.minQuantity</code> attribute.
	 * @return the minQuantity
	 */
	public Integer getMinQuantity(final SessionContext ctx)
	{
		return (Integer)getProperty( ctx, MINQUANTITY);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>PromotionThresholdDiscount.minQuantity</code> attribute.
	 * @return the minQuantity
	 */
	public Integer getMinQuantity()
	{
		return getMinQuantity( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>PromotionThresholdDiscount.minQuantity</code> attribute. 
	 * @return the minQuantity
	 */
	public int getMinQuantityAsPrimitive(final SessionContext ctx)
	{
		Integer value = getMinQuantity( ctx );
		return value != null ? value.intValue() : 0;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>PromotionThresholdDiscount.minQuantity</code> attribute. 
	 * @return the minQuantity
	 */
	public int getMinQuantityAsPrimitive()
	{
		return getMinQuantityAsPrimitive( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>PromotionThresholdDiscount.minQuantity</code> attribute. 
	 * @param value the minQuantity
	 */
	public void setMinQuantity(final SessionContext ctx, final Integer value)
	{
		setProperty(ctx, MINQUANTITY,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>PromotionThresholdDiscount.minQuantity</code> attribute. 
	 * @param value the minQuantity
	 */
	public void setMinQuantity(final Integer value)
	{
		setMinQuantity( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>PromotionThresholdDiscount.minQuantity</code> attribute. 
	 * @param value the minQuantity
	 */
	public void setMinQuantity(final SessionContext ctx, final int value)
	{
		setMinQuantity( ctx,Integer.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>PromotionThresholdDiscount.minQuantity</code> attribute. 
	 * @param value the minQuantity
	 */
	public void setMinQuantity(final int value)
	{
		setMinQuantity( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>PromotionThresholdDiscount.percentageDiscount</code> attribute.
	 * @return the percentageDiscount
	 */
	public Double getPercentageDiscount(final SessionContext ctx)
	{
		return (Double)getProperty( ctx, PERCENTAGEDISCOUNT);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>PromotionThresholdDiscount.percentageDiscount</code> attribute.
	 * @return the percentageDiscount
	 */
	public Double getPercentageDiscount()
	{
		return getPercentageDiscount( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>PromotionThresholdDiscount.percentageDiscount</code> attribute. 
	 * @return the percentageDiscount
	 */
	public double getPercentageDiscountAsPrimitive(final SessionContext ctx)
	{
		Double value = getPercentageDiscount( ctx );
		return value != null ? value.doubleValue() : 0.0d;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>PromotionThresholdDiscount.percentageDiscount</code> attribute. 
	 * @return the percentageDiscount
	 */
	public double getPercentageDiscountAsPrimitive()
	{
		return getPercentageDiscountAsPrimitive( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>PromotionThresholdDiscount.percentageDiscount</code> attribute. 
	 * @param value the percentageDiscount
	 */
	public void setPercentageDiscount(final SessionContext ctx, final Double value)
	{
		setProperty(ctx, PERCENTAGEDISCOUNT,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>PromotionThresholdDiscount.percentageDiscount</code> attribute. 
	 * @param value the percentageDiscount
	 */
	public void setPercentageDiscount(final Double value)
	{
		setPercentageDiscount( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>PromotionThresholdDiscount.percentageDiscount</code> attribute. 
	 * @param value the percentageDiscount
	 */
	public void setPercentageDiscount(final SessionContext ctx, final double value)
	{
		setPercentageDiscount( ctx,Double.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>PromotionThresholdDiscount.percentageDiscount</code> attribute. 
	 * @param value the percentageDiscount
	 */
	public void setPercentageDiscount(final double value)
	{
		setPercentageDiscount( getSession().getSessionContext(), value );
	}
	
}
