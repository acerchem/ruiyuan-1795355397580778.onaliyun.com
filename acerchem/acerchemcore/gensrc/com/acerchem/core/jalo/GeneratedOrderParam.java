/*
 * ----------------------------------------------------------------
 * --- WARNING: THIS FILE IS GENERATED AND WILL BE OVERWRITTEN! ---
 * --- Generated at 2018-5-6 22:26:27                           ---
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
 * Generated class for type {@link de.hybris.platform.jalo.GenericItem OrderParam}.
 */
@SuppressWarnings({"deprecation","unused","cast","PMD"})
public abstract class GeneratedOrderParam extends GenericItem
{
	/** Qualifier of the <code>OrderParam.daysForCancel</code> attribute **/
	public static final String DAYSFORCANCEL = "daysForCancel";
	/** Qualifier of the <code>OrderParam.daysForPickUp</code> attribute **/
	public static final String DAYSFORPICKUP = "daysForPickUp";
	protected static final Map<String, AttributeMode> DEFAULT_INITIAL_ATTRIBUTES;
	static
	{
		final Map<String, AttributeMode> tmp = new HashMap<String, AttributeMode>();
		tmp.put(DAYSFORCANCEL, AttributeMode.INITIAL);
		tmp.put(DAYSFORPICKUP, AttributeMode.INITIAL);
		DEFAULT_INITIAL_ATTRIBUTES = Collections.unmodifiableMap(tmp);
	}
	@Override
	protected Map<String, AttributeMode> getDefaultAttributeModes()
	{
		return DEFAULT_INITIAL_ATTRIBUTES;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>OrderParam.daysForCancel</code> attribute.
	 * @return the daysForCancel
	 */
	public Integer getDaysForCancel(final SessionContext ctx)
	{
		return (Integer)getProperty( ctx, DAYSFORCANCEL);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>OrderParam.daysForCancel</code> attribute.
	 * @return the daysForCancel
	 */
	public Integer getDaysForCancel()
	{
		return getDaysForCancel( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>OrderParam.daysForCancel</code> attribute. 
	 * @return the daysForCancel
	 */
	public int getDaysForCancelAsPrimitive(final SessionContext ctx)
	{
		Integer value = getDaysForCancel( ctx );
		return value != null ? value.intValue() : 0;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>OrderParam.daysForCancel</code> attribute. 
	 * @return the daysForCancel
	 */
	public int getDaysForCancelAsPrimitive()
	{
		return getDaysForCancelAsPrimitive( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>OrderParam.daysForCancel</code> attribute. 
	 * @param value the daysForCancel
	 */
	public void setDaysForCancel(final SessionContext ctx, final Integer value)
	{
		setProperty(ctx, DAYSFORCANCEL,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>OrderParam.daysForCancel</code> attribute. 
	 * @param value the daysForCancel
	 */
	public void setDaysForCancel(final Integer value)
	{
		setDaysForCancel( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>OrderParam.daysForCancel</code> attribute. 
	 * @param value the daysForCancel
	 */
	public void setDaysForCancel(final SessionContext ctx, final int value)
	{
		setDaysForCancel( ctx,Integer.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>OrderParam.daysForCancel</code> attribute. 
	 * @param value the daysForCancel
	 */
	public void setDaysForCancel(final int value)
	{
		setDaysForCancel( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>OrderParam.daysForPickUp</code> attribute.
	 * @return the daysForPickUp
	 */
	public Integer getDaysForPickUp(final SessionContext ctx)
	{
		return (Integer)getProperty( ctx, DAYSFORPICKUP);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>OrderParam.daysForPickUp</code> attribute.
	 * @return the daysForPickUp
	 */
	public Integer getDaysForPickUp()
	{
		return getDaysForPickUp( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>OrderParam.daysForPickUp</code> attribute. 
	 * @return the daysForPickUp
	 */
	public int getDaysForPickUpAsPrimitive(final SessionContext ctx)
	{
		Integer value = getDaysForPickUp( ctx );
		return value != null ? value.intValue() : 0;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>OrderParam.daysForPickUp</code> attribute. 
	 * @return the daysForPickUp
	 */
	public int getDaysForPickUpAsPrimitive()
	{
		return getDaysForPickUpAsPrimitive( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>OrderParam.daysForPickUp</code> attribute. 
	 * @param value the daysForPickUp
	 */
	public void setDaysForPickUp(final SessionContext ctx, final Integer value)
	{
		setProperty(ctx, DAYSFORPICKUP,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>OrderParam.daysForPickUp</code> attribute. 
	 * @param value the daysForPickUp
	 */
	public void setDaysForPickUp(final Integer value)
	{
		setDaysForPickUp( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>OrderParam.daysForPickUp</code> attribute. 
	 * @param value the daysForPickUp
	 */
	public void setDaysForPickUp(final SessionContext ctx, final int value)
	{
		setDaysForPickUp( ctx,Integer.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>OrderParam.daysForPickUp</code> attribute. 
	 * @param value the daysForPickUp
	 */
	public void setDaysForPickUp(final int value)
	{
		setDaysForPickUp( getSession().getSessionContext(), value );
	}
	
}
