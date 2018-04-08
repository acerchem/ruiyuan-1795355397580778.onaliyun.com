/*
 * ----------------------------------------------------------------
 * --- WARNING: THIS FILE IS GENERATED AND WILL BE OVERWRITTEN! ---
 * --- Generated at 2018-4-3 10:38:15                           ---
 * ----------------------------------------------------------------
 *  
 * [y] hybris Platform
 *  
 * Copyright (c) 2000-2016 SAP SE
 * All rights reserved.
 *  
 * This software is the confidential and proprietary information of SAP
 * Hybris ("Confidential Information"). You shall not disclose such
 * Confidential Information and shall use it only in accordance with the
 * terms of the license agreement you entered into with SAP Hybris.
 *  
 */
package de.hybris.platform.cms2lib.components;

import com.acerchem.core.constants.AcerchemCoreConstants;
import de.hybris.platform.cms2lib.components.AbstractMultipleBannerComponent;
import de.hybris.platform.jalo.Item.AttributeMode;
import de.hybris.platform.jalo.SessionContext;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Generated class for type {@link de.hybris.platform.cms2lib.components.MultipleBannerComponent MultipleBannerComponent}.
 */
@SuppressWarnings({"deprecation","unused","cast","PMD"})
public abstract class GeneratedMultipleBannerComponent extends AbstractMultipleBannerComponent
{
	/** Qualifier of the <code>MultipleBannerComponent.loop</code> attribute **/
	public static final String LOOP = "loop";
	/** Qualifier of the <code>MultipleBannerComponent.displayLevel</code> attribute **/
	public static final String DISPLAYLEVEL = "displayLevel";
	/** Qualifier of the <code>MultipleBannerComponent.classfication</code> attribute **/
	public static final String CLASSFICATION = "classfication";
	protected static final Map<String, AttributeMode> DEFAULT_INITIAL_ATTRIBUTES;
	static
	{
		final Map<String, AttributeMode> tmp = new HashMap<String, AttributeMode>(AbstractMultipleBannerComponent.DEFAULT_INITIAL_ATTRIBUTES);
		tmp.put(LOOP, AttributeMode.INITIAL);
		tmp.put(DISPLAYLEVEL, AttributeMode.INITIAL);
		tmp.put(CLASSFICATION, AttributeMode.INITIAL);
		DEFAULT_INITIAL_ATTRIBUTES = Collections.unmodifiableMap(tmp);
	}
	@Override
	protected Map<String, AttributeMode> getDefaultAttributeModes()
	{
		return DEFAULT_INITIAL_ATTRIBUTES;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>MultipleBannerComponent.classfication</code> attribute.
	 * @return the classfication
	 */
	public String getClassfication(final SessionContext ctx)
	{
		return (String)getProperty( ctx, CLASSFICATION);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>MultipleBannerComponent.classfication</code> attribute.
	 * @return the classfication
	 */
	public String getClassfication()
	{
		return getClassfication( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>MultipleBannerComponent.classfication</code> attribute. 
	 * @param value the classfication
	 */
	public void setClassfication(final SessionContext ctx, final String value)
	{
		setProperty(ctx, CLASSFICATION,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>MultipleBannerComponent.classfication</code> attribute. 
	 * @param value the classfication
	 */
	public void setClassfication(final String value)
	{
		setClassfication( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>MultipleBannerComponent.displayLevel</code> attribute.
	 * @return the displayLevel
	 */
	public Integer getDisplayLevel(final SessionContext ctx)
	{
		return (Integer)getProperty( ctx, DISPLAYLEVEL);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>MultipleBannerComponent.displayLevel</code> attribute.
	 * @return the displayLevel
	 */
	public Integer getDisplayLevel()
	{
		return getDisplayLevel( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>MultipleBannerComponent.displayLevel</code> attribute. 
	 * @return the displayLevel
	 */
	public int getDisplayLevelAsPrimitive(final SessionContext ctx)
	{
		Integer value = getDisplayLevel( ctx );
		return value != null ? value.intValue() : 0;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>MultipleBannerComponent.displayLevel</code> attribute. 
	 * @return the displayLevel
	 */
	public int getDisplayLevelAsPrimitive()
	{
		return getDisplayLevelAsPrimitive( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>MultipleBannerComponent.displayLevel</code> attribute. 
	 * @param value the displayLevel
	 */
	public void setDisplayLevel(final SessionContext ctx, final Integer value)
	{
		setProperty(ctx, DISPLAYLEVEL,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>MultipleBannerComponent.displayLevel</code> attribute. 
	 * @param value the displayLevel
	 */
	public void setDisplayLevel(final Integer value)
	{
		setDisplayLevel( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>MultipleBannerComponent.displayLevel</code> attribute. 
	 * @param value the displayLevel
	 */
	public void setDisplayLevel(final SessionContext ctx, final int value)
	{
		setDisplayLevel( ctx,Integer.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>MultipleBannerComponent.displayLevel</code> attribute. 
	 * @param value the displayLevel
	 */
	public void setDisplayLevel(final int value)
	{
		setDisplayLevel( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>MultipleBannerComponent.loop</code> attribute.
	 * @return the loop
	 */
	public Boolean isLoop(final SessionContext ctx)
	{
		return (Boolean)getProperty( ctx, LOOP);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>MultipleBannerComponent.loop</code> attribute.
	 * @return the loop
	 */
	public Boolean isLoop()
	{
		return isLoop( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>MultipleBannerComponent.loop</code> attribute. 
	 * @return the loop
	 */
	public boolean isLoopAsPrimitive(final SessionContext ctx)
	{
		Boolean value = isLoop( ctx );
		return value != null ? value.booleanValue() : false;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>MultipleBannerComponent.loop</code> attribute. 
	 * @return the loop
	 */
	public boolean isLoopAsPrimitive()
	{
		return isLoopAsPrimitive( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>MultipleBannerComponent.loop</code> attribute. 
	 * @param value the loop
	 */
	public void setLoop(final SessionContext ctx, final Boolean value)
	{
		setProperty(ctx, LOOP,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>MultipleBannerComponent.loop</code> attribute. 
	 * @param value the loop
	 */
	public void setLoop(final Boolean value)
	{
		setLoop( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>MultipleBannerComponent.loop</code> attribute. 
	 * @param value the loop
	 */
	public void setLoop(final SessionContext ctx, final boolean value)
	{
		setLoop( ctx,Boolean.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>MultipleBannerComponent.loop</code> attribute. 
	 * @param value the loop
	 */
	public void setLoop(final boolean value)
	{
		setLoop( getSession().getSessionContext(), value );
	}
	
}
