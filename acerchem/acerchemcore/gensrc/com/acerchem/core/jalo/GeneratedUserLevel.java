/*
 * ----------------------------------------------------------------
 * --- WARNING: THIS FILE IS GENERATED AND WILL BE OVERWRITTEN! ---
 * --- Generated at 2018-4-28 18:44:38                          ---
 * ----------------------------------------------------------------
 */
package com.acerchem.core.jalo;

import com.acerchem.core.constants.AcerchemCoreConstants;
import de.hybris.platform.constants.CoreConstants;
import de.hybris.platform.jalo.Item.AttributeMode;
import de.hybris.platform.jalo.SessionContext;
import de.hybris.platform.jalo.enumeration.EnumerationValue;
import de.hybris.platform.jalo.type.CollectionType;
import de.hybris.platform.jalo.user.User;
import de.hybris.platform.jalo.user.UserGroup;
import de.hybris.platform.util.OneToManyHandler;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Generated class for type {@link de.hybris.platform.jalo.user.UserGroup UserLevel}.
 */
@SuppressWarnings({"deprecation","unused","cast","PMD"})
public abstract class GeneratedUserLevel extends UserGroup
{
	/** Qualifier of the <code>UserLevel.levelName</code> attribute **/
	public static final String LEVELNAME = "levelName";
	/** Qualifier of the <code>UserLevel.levelCode</code> attribute **/
	public static final String LEVELCODE = "levelCode";
	/** Qualifier of the <code>UserLevel.discount</code> attribute **/
	public static final String DISCOUNT = "discount";
	/** Qualifier of the <code>UserLevel.customers</code> attribute **/
	public static final String CUSTOMERS = "customers";
	/**
	* {@link OneToManyHandler} for handling 1:n CUSTOMERS's relation attributes from 'many' side.
	**/
	protected static final OneToManyHandler<User> CUSTOMERSHANDLER = new OneToManyHandler<User>(
	CoreConstants.TC.USER,
	false,
	"userLevel",
	"userLevelPOS",
	true,
	true,
	CollectionType.LIST
	);
	protected static final Map<String, AttributeMode> DEFAULT_INITIAL_ATTRIBUTES;
	static
	{
		final Map<String, AttributeMode> tmp = new HashMap<String, AttributeMode>(UserGroup.DEFAULT_INITIAL_ATTRIBUTES);
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
	 * <i>Generated method</i> - Getter of the <code>UserLevel.customers</code> attribute.
	 * @return the customers
	 */
	public List<User> getCustomers(final SessionContext ctx)
	{
		return (List<User>)CUSTOMERSHANDLER.getValues( ctx, this );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>UserLevel.customers</code> attribute.
	 * @return the customers
	 */
	public List<User> getCustomers()
	{
		return getCustomers( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>UserLevel.customers</code> attribute. 
	 * @param value the customers
	 */
	public void setCustomers(final SessionContext ctx, final List<User> value)
	{
		CUSTOMERSHANDLER.setValues( ctx, this, value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>UserLevel.customers</code> attribute. 
	 * @param value the customers
	 */
	public void setCustomers(final List<User> value)
	{
		setCustomers( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Adds <code>value</code> to customers. 
	 * @param value the item to add to customers
	 */
	public void addToCustomers(final SessionContext ctx, final User value)
	{
		CUSTOMERSHANDLER.addValue( ctx, this, value );
	}
	
	/**
	 * <i>Generated method</i> - Adds <code>value</code> to customers. 
	 * @param value the item to add to customers
	 */
	public void addToCustomers(final User value)
	{
		addToCustomers( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Removes <code>value</code> from customers. 
	 * @param value the item to remove from customers
	 */
	public void removeFromCustomers(final SessionContext ctx, final User value)
	{
		CUSTOMERSHANDLER.removeValue( ctx, this, value );
	}
	
	/**
	 * <i>Generated method</i> - Removes <code>value</code> from customers. 
	 * @param value the item to remove from customers
	 */
	public void removeFromCustomers(final User value)
	{
		removeFromCustomers( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>UserLevel.discount</code> attribute.
	 * @return the discount - 折扣
	 */
	public Double getDiscount(final SessionContext ctx)
	{
		return (Double)getProperty( ctx, DISCOUNT);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>UserLevel.discount</code> attribute.
	 * @return the discount - 折扣
	 */
	public Double getDiscount()
	{
		return getDiscount( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>UserLevel.discount</code> attribute. 
	 * @return the discount - 折扣
	 */
	public double getDiscountAsPrimitive(final SessionContext ctx)
	{
		Double value = getDiscount( ctx );
		return value != null ? value.doubleValue() : 0.0d;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>UserLevel.discount</code> attribute. 
	 * @return the discount - 折扣
	 */
	public double getDiscountAsPrimitive()
	{
		return getDiscountAsPrimitive( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>UserLevel.discount</code> attribute. 
	 * @param value the discount - 折扣
	 */
	public void setDiscount(final SessionContext ctx, final Double value)
	{
		setProperty(ctx, DISCOUNT,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>UserLevel.discount</code> attribute. 
	 * @param value the discount - 折扣
	 */
	public void setDiscount(final Double value)
	{
		setDiscount( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>UserLevel.discount</code> attribute. 
	 * @param value the discount - 折扣
	 */
	public void setDiscount(final SessionContext ctx, final double value)
	{
		setDiscount( ctx,Double.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>UserLevel.discount</code> attribute. 
	 * @param value the discount - 折扣
	 */
	public void setDiscount(final double value)
	{
		setDiscount( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>UserLevel.levelCode</code> attribute.
	 * @return the levelCode - 等级代码
	 */
	public EnumerationValue getLevelCode(final SessionContext ctx)
	{
		return (EnumerationValue)getProperty( ctx, LEVELCODE);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>UserLevel.levelCode</code> attribute.
	 * @return the levelCode - 等级代码
	 */
	public EnumerationValue getLevelCode()
	{
		return getLevelCode( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>UserLevel.levelCode</code> attribute. 
	 * @param value the levelCode - 等级代码
	 */
	public void setLevelCode(final SessionContext ctx, final EnumerationValue value)
	{
		setProperty(ctx, LEVELCODE,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>UserLevel.levelCode</code> attribute. 
	 * @param value the levelCode - 等级代码
	 */
	public void setLevelCode(final EnumerationValue value)
	{
		setLevelCode( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>UserLevel.levelName</code> attribute.
	 * @return the levelName - 等级名称
	 */
	public String getLevelName(final SessionContext ctx)
	{
		return (String)getProperty( ctx, LEVELNAME);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>UserLevel.levelName</code> attribute.
	 * @return the levelName - 等级名称
	 */
	public String getLevelName()
	{
		return getLevelName( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>UserLevel.levelName</code> attribute. 
	 * @param value the levelName - 等级名称
	 */
	public void setLevelName(final SessionContext ctx, final String value)
	{
		setProperty(ctx, LEVELNAME,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>UserLevel.levelName</code> attribute. 
	 * @param value the levelName - 等级名称
	 */
	public void setLevelName(final String value)
	{
		setLevelName( getSession().getSessionContext(), value );
	}
	
}
