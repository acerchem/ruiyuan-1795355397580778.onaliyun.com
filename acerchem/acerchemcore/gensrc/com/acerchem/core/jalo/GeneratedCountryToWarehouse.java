/*
 * ----------------------------------------------------------------
 * --- WARNING: THIS FILE IS GENERATED AND WILL BE OVERWRITTEN! ---
 * --- Generated at 2018-3-26 21:31:42                          ---
 * ----------------------------------------------------------------
 */
package com.acerchem.core.jalo;

import com.acerchem.core.constants.AcerchemCoreConstants;
import de.hybris.platform.jalo.GenericItem;
import de.hybris.platform.jalo.Item.AttributeMode;
import de.hybris.platform.jalo.SessionContext;
import de.hybris.platform.jalo.c2l.Country;
import de.hybris.platform.ordersplitting.jalo.Warehouse;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Generated class for type {@link de.hybris.platform.jalo.GenericItem CountryToWarehouse}.
 */
@SuppressWarnings({"deprecation","unused","cast","PMD"})
public abstract class GeneratedCountryToWarehouse extends GenericItem
{
	/** Qualifier of the <code>CountryToWarehouse.country</code> attribute **/
	public static final String COUNTRY = "country";
	/** Qualifier of the <code>CountryToWarehouse.warehouseList</code> attribute **/
	public static final String WAREHOUSELIST = "warehouseList";
	protected static final Map<String, AttributeMode> DEFAULT_INITIAL_ATTRIBUTES;
	static
	{
		final Map<String, AttributeMode> tmp = new HashMap<String, AttributeMode>();
		tmp.put(COUNTRY, AttributeMode.INITIAL);
		tmp.put(WAREHOUSELIST, AttributeMode.INITIAL);
		DEFAULT_INITIAL_ATTRIBUTES = Collections.unmodifiableMap(tmp);
	}
	@Override
	protected Map<String, AttributeMode> getDefaultAttributeModes()
	{
		return DEFAULT_INITIAL_ATTRIBUTES;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>CountryToWarehouse.country</code> attribute.
	 * @return the country
	 */
	public Country getCountry(final SessionContext ctx)
	{
		return (Country)getProperty( ctx, COUNTRY);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>CountryToWarehouse.country</code> attribute.
	 * @return the country
	 */
	public Country getCountry()
	{
		return getCountry( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>CountryToWarehouse.country</code> attribute. 
	 * @param value the country
	 */
	public void setCountry(final SessionContext ctx, final Country value)
	{
		setProperty(ctx, COUNTRY,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>CountryToWarehouse.country</code> attribute. 
	 * @param value the country
	 */
	public void setCountry(final Country value)
	{
		setCountry( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>CountryToWarehouse.warehouseList</code> attribute.
	 * @return the warehouseList
	 */
	public List<Warehouse> getWarehouseList(final SessionContext ctx)
	{
		List<Warehouse> coll = (List<Warehouse>)getProperty( ctx, WAREHOUSELIST);
		return coll != null ? coll : Collections.EMPTY_LIST;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>CountryToWarehouse.warehouseList</code> attribute.
	 * @return the warehouseList
	 */
	public List<Warehouse> getWarehouseList()
	{
		return getWarehouseList( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>CountryToWarehouse.warehouseList</code> attribute. 
	 * @param value the warehouseList
	 */
	public void setWarehouseList(final SessionContext ctx, final List<Warehouse> value)
	{
		setProperty(ctx, WAREHOUSELIST,value == null || !value.isEmpty() ? value : null );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>CountryToWarehouse.warehouseList</code> attribute. 
	 * @param value the warehouseList
	 */
	public void setWarehouseList(final List<Warehouse> value)
	{
		setWarehouseList( getSession().getSessionContext(), value );
	}
	
}
