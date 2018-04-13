/*
 * ----------------------------------------------------------------
 * --- WARNING: THIS FILE IS GENERATED AND WILL BE OVERWRITTEN! ---
 * --- Generated at 2018-4-13 20:17:00                          ---
 * ----------------------------------------------------------------
 */
package com.acerchem.core.jalo;

import com.acerchem.core.constants.AcerchemCoreConstants;
import de.hybris.platform.jalo.GenericItem;
import de.hybris.platform.jalo.Item.AttributeMode;
import de.hybris.platform.jalo.SessionContext;
import de.hybris.platform.jalo.c2l.Country;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Generated class for type {@link com.acerchem.core.jalo.CountryTrayFareConf CountryTrayFareConf}.
 */
@SuppressWarnings({"deprecation","unused","cast","PMD"})
public abstract class GeneratedCountryTrayFareConf extends GenericItem
{
	/** Qualifier of the <code>CountryTrayFareConf.country</code> attribute **/
	public static final String COUNTRY = "country";
	/** Qualifier of the <code>CountryTrayFareConf.trayAmount</code> attribute **/
	public static final String TRAYAMOUNT = "trayAmount";
	/** Qualifier of the <code>CountryTrayFareConf.price</code> attribute **/
	public static final String PRICE = "price";
	protected static final Map<String, AttributeMode> DEFAULT_INITIAL_ATTRIBUTES;
	static
	{
		final Map<String, AttributeMode> tmp = new HashMap<String, AttributeMode>();
		tmp.put(COUNTRY, AttributeMode.INITIAL);
		tmp.put(TRAYAMOUNT, AttributeMode.INITIAL);
		tmp.put(PRICE, AttributeMode.INITIAL);
		DEFAULT_INITIAL_ATTRIBUTES = Collections.unmodifiableMap(tmp);
	}
	@Override
	protected Map<String, AttributeMode> getDefaultAttributeModes()
	{
		return DEFAULT_INITIAL_ATTRIBUTES;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>CountryTrayFareConf.country</code> attribute.
	 * @return the country
	 */
	public Country getCountry(final SessionContext ctx)
	{
		return (Country)getProperty( ctx, COUNTRY);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>CountryTrayFareConf.country</code> attribute.
	 * @return the country
	 */
	public Country getCountry()
	{
		return getCountry( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>CountryTrayFareConf.country</code> attribute. 
	 * @param value the country
	 */
	public void setCountry(final SessionContext ctx, final Country value)
	{
		setProperty(ctx, COUNTRY,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>CountryTrayFareConf.country</code> attribute. 
	 * @param value the country
	 */
	public void setCountry(final Country value)
	{
		setCountry( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>CountryTrayFareConf.price</code> attribute.
	 * @return the price
	 */
	public Double getPrice(final SessionContext ctx)
	{
		return (Double)getProperty( ctx, PRICE);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>CountryTrayFareConf.price</code> attribute.
	 * @return the price
	 */
	public Double getPrice()
	{
		return getPrice( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>CountryTrayFareConf.price</code> attribute. 
	 * @return the price
	 */
	public double getPriceAsPrimitive(final SessionContext ctx)
	{
		Double value = getPrice( ctx );
		return value != null ? value.doubleValue() : 0.0d;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>CountryTrayFareConf.price</code> attribute. 
	 * @return the price
	 */
	public double getPriceAsPrimitive()
	{
		return getPriceAsPrimitive( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>CountryTrayFareConf.price</code> attribute. 
	 * @param value the price
	 */
	public void setPrice(final SessionContext ctx, final Double value)
	{
		setProperty(ctx, PRICE,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>CountryTrayFareConf.price</code> attribute. 
	 * @param value the price
	 */
	public void setPrice(final Double value)
	{
		setPrice( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>CountryTrayFareConf.price</code> attribute. 
	 * @param value the price
	 */
	public void setPrice(final SessionContext ctx, final double value)
	{
		setPrice( ctx,Double.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>CountryTrayFareConf.price</code> attribute. 
	 * @param value the price
	 */
	public void setPrice(final double value)
	{
		setPrice( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>CountryTrayFareConf.trayAmount</code> attribute.
	 * @return the trayAmount
	 */
	public Integer getTrayAmount(final SessionContext ctx)
	{
		return (Integer)getProperty( ctx, TRAYAMOUNT);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>CountryTrayFareConf.trayAmount</code> attribute.
	 * @return the trayAmount
	 */
	public Integer getTrayAmount()
	{
		return getTrayAmount( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>CountryTrayFareConf.trayAmount</code> attribute. 
	 * @return the trayAmount
	 */
	public int getTrayAmountAsPrimitive(final SessionContext ctx)
	{
		Integer value = getTrayAmount( ctx );
		return value != null ? value.intValue() : 0;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>CountryTrayFareConf.trayAmount</code> attribute. 
	 * @return the trayAmount
	 */
	public int getTrayAmountAsPrimitive()
	{
		return getTrayAmountAsPrimitive( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>CountryTrayFareConf.trayAmount</code> attribute. 
	 * @param value the trayAmount
	 */
	public void setTrayAmount(final SessionContext ctx, final Integer value)
	{
		setProperty(ctx, TRAYAMOUNT,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>CountryTrayFareConf.trayAmount</code> attribute. 
	 * @param value the trayAmount
	 */
	public void setTrayAmount(final Integer value)
	{
		setTrayAmount( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>CountryTrayFareConf.trayAmount</code> attribute. 
	 * @param value the trayAmount
	 */
	public void setTrayAmount(final SessionContext ctx, final int value)
	{
		setTrayAmount( ctx,Integer.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>CountryTrayFareConf.trayAmount</code> attribute. 
	 * @param value the trayAmount
	 */
	public void setTrayAmount(final int value)
	{
		setTrayAmount( getSession().getSessionContext(), value );
	}
	
}
