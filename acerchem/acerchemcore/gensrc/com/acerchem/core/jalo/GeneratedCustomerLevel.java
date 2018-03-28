/*
 * ----------------------------------------------------------------
 * --- WARNING: THIS FILE IS GENERATED AND WILL BE OVERWRITTEN! ---
 * --- Generated at 2018-3-28 14:38:31                          ---
 * ----------------------------------------------------------------
 */
package com.acerchem.core.jalo;

import com.acerchem.core.constants.AcerchemCoreConstants;
import de.hybris.platform.jalo.GenericItem;
import de.hybris.platform.jalo.Item.AttributeMode;
import de.hybris.platform.jalo.SessionContext;
import de.hybris.platform.jalo.enumeration.EnumerationValue;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Generated class for type {@link de.hybris.platform.jalo.GenericItem CustomerLevel}.
 */
@SuppressWarnings({"deprecation","unused","cast","PMD"})
public abstract class GeneratedCustomerLevel extends GenericItem
{
	/** Qualifier of the <code>CustomerLevel.levelName</code> attribute **/
	public static final String LEVELNAME = "levelName";
	/** Qualifier of the <code>CustomerLevel.levelCode</code> attribute **/
	public static final String LEVELCODE = "levelCode";
	/** Qualifier of the <code>CustomerLevel.discount</code> attribute **/
	public static final String DISCOUNT = "discount";
	protected static final Map<String, AttributeMode> DEFAULT_INITIAL_ATTRIBUTES;
	static
	{
		final Map<String, AttributeMode> tmp = new HashMap<String, AttributeMode>();
		tmp.put(LEVELNAME, AttributeMode.INITIAL);
		tmp.put(LEVELCODE, AttributeMode.INITIAL);
		tmp.put(DISCOUNT, AttributeMode.INITIAL);
		DEFAULT_INITIAL_ATTRIBUTES = Collections.unmodifiableMap(tmp);
	}
	@Override
	protected Map<String, AttributeMode> getDefaultAttributeModes()
	{
		return DEFAULT_INITIAL_ATTRIBUTES;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>CustomerLevel.discount</code> attribute.
	 * @return the discount - ææ£
	 */
	public Double getDiscount(final SessionContext ctx)
	{
		return (Double)getProperty( ctx, DISCOUNT);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>CustomerLevel.discount</code> attribute.
	 * @return the discount - ææ£
	 */
	public Double getDiscount()
	{
		return getDiscount( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>CustomerLevel.discount</code> attribute. 
	 * @return the discount - ææ£
	 */
	public double getDiscountAsPrimitive(final SessionContext ctx)
	{
		Double value = getDiscount( ctx );
		return value != null ? value.doubleValue() : 0.0d;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>CustomerLevel.discount</code> attribute. 
	 * @return the discount - ææ£
	 */
	public double getDiscountAsPrimitive()
	{
		return getDiscountAsPrimitive( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>CustomerLevel.discount</code> attribute. 
	 * @param value the discount - ææ£
	 */
	public void setDiscount(final SessionContext ctx, final Double value)
	{
		setProperty(ctx, DISCOUNT,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>CustomerLevel.discount</code> attribute. 
	 * @param value the discount - ææ£
	 */
	public void setDiscount(final Double value)
	{
		setDiscount( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>CustomerLevel.discount</code> attribute. 
	 * @param value the discount - ææ£
	 */
	public void setDiscount(final SessionContext ctx, final double value)
	{
		setDiscount( ctx,Double.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>CustomerLevel.discount</code> attribute. 
	 * @param value the discount - ææ£
	 */
	public void setDiscount(final double value)
	{
		setDiscount( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>CustomerLevel.levelCode</code> attribute.
	 * @return the levelCode - ç­çº§ä»£ç 
	 */
	public EnumerationValue getLevelCode(final SessionContext ctx)
	{
		return (EnumerationValue)getProperty( ctx, LEVELCODE);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>CustomerLevel.levelCode</code> attribute.
	 * @return the levelCode - ç­çº§ä»£ç 
	 */
	public EnumerationValue getLevelCode()
	{
		return getLevelCode( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>CustomerLevel.levelCode</code> attribute. 
	 * @param value the levelCode - ç­çº§ä»£ç 
	 */
	public void setLevelCode(final SessionContext ctx, final EnumerationValue value)
	{
		setProperty(ctx, LEVELCODE,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>CustomerLevel.levelCode</code> attribute. 
	 * @param value the levelCode - ç­çº§ä»£ç 
	 */
	public void setLevelCode(final EnumerationValue value)
	{
		setLevelCode( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>CustomerLevel.levelName</code> attribute.
	 * @return the levelName - ç­çº§åç§°
	 */
	public String getLevelName(final SessionContext ctx)
	{
		return (String)getProperty( ctx, LEVELNAME);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>CustomerLevel.levelName</code> attribute.
	 * @return the levelName - ç­çº§åç§°
	 */
	public String getLevelName()
	{
		return getLevelName( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>CustomerLevel.levelName</code> attribute. 
	 * @param value the levelName - ç­çº§åç§°
	 */
	public void setLevelName(final SessionContext ctx, final String value)
	{
		setProperty(ctx, LEVELNAME,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>CustomerLevel.levelName</code> attribute. 
	 * @param value the levelName - ç­çº§åç§°
	 */
	public void setLevelName(final String value)
	{
		setLevelName( getSession().getSessionContext(), value );
	}
	
}
