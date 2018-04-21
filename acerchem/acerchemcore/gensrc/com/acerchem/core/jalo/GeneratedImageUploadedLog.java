/*
 * ----------------------------------------------------------------
 * --- WARNING: THIS FILE IS GENERATED AND WILL BE OVERWRITTEN! ---
 * --- Generated at 2018-4-22 2:10:34                           ---
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
 * Generated class for type {@link de.hybris.platform.jalo.GenericItem ImageUploadedLog}.
 */
@SuppressWarnings({"deprecation","unused","cast","PMD"})
public abstract class GeneratedImageUploadedLog extends GenericItem
{
	/** Qualifier of the <code>ImageUploadedLog.imagePK</code> attribute **/
	public static final String IMAGEPK = "imagePK";
	/** Qualifier of the <code>ImageUploadedLog.aliyunUrl</code> attribute **/
	public static final String ALIYUNURL = "aliyunUrl";
	/** Qualifier of the <code>ImageUploadedLog.location</code> attribute **/
	public static final String LOCATION = "location";
	protected static final Map<String, AttributeMode> DEFAULT_INITIAL_ATTRIBUTES;
	static
	{
		final Map<String, AttributeMode> tmp = new HashMap<String, AttributeMode>();
		tmp.put(IMAGEPK, AttributeMode.INITIAL);
		tmp.put(ALIYUNURL, AttributeMode.INITIAL);
		tmp.put(LOCATION, AttributeMode.INITIAL);
		DEFAULT_INITIAL_ATTRIBUTES = Collections.unmodifiableMap(tmp);
	}
	@Override
	protected Map<String, AttributeMode> getDefaultAttributeModes()
	{
		return DEFAULT_INITIAL_ATTRIBUTES;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ImageUploadedLog.aliyunUrl</code> attribute.
	 * @return the aliyunUrl - 阿里云URL
	 */
	public String getAliyunUrl(final SessionContext ctx)
	{
		return (String)getProperty( ctx, ALIYUNURL);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ImageUploadedLog.aliyunUrl</code> attribute.
	 * @return the aliyunUrl - 阿里云URL
	 */
	public String getAliyunUrl()
	{
		return getAliyunUrl( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ImageUploadedLog.aliyunUrl</code> attribute. 
	 * @param value the aliyunUrl - 阿里云URL
	 */
	public void setAliyunUrl(final SessionContext ctx, final String value)
	{
		setProperty(ctx, ALIYUNURL,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ImageUploadedLog.aliyunUrl</code> attribute. 
	 * @param value the aliyunUrl - 阿里云URL
	 */
	public void setAliyunUrl(final String value)
	{
		setAliyunUrl( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ImageUploadedLog.imagePK</code> attribute.
	 * @return the imagePK - 图片PK
	 */
	public String getImagePK(final SessionContext ctx)
	{
		return (String)getProperty( ctx, IMAGEPK);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ImageUploadedLog.imagePK</code> attribute.
	 * @return the imagePK - 图片PK
	 */
	public String getImagePK()
	{
		return getImagePK( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ImageUploadedLog.imagePK</code> attribute. 
	 * @param value the imagePK - 图片PK
	 */
	public void setImagePK(final SessionContext ctx, final String value)
	{
		setProperty(ctx, IMAGEPK,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ImageUploadedLog.imagePK</code> attribute. 
	 * @param value the imagePK - 图片PK
	 */
	public void setImagePK(final String value)
	{
		setImagePK( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ImageUploadedLog.location</code> attribute.
	 * @return the location - 本地路径
	 */
	public String getLocation(final SessionContext ctx)
	{
		return (String)getProperty( ctx, LOCATION);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ImageUploadedLog.location</code> attribute.
	 * @return the location - 本地路径
	 */
	public String getLocation()
	{
		return getLocation( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ImageUploadedLog.location</code> attribute. 
	 * @param value the location - 本地路径
	 */
	public void setLocation(final SessionContext ctx, final String value)
	{
		setProperty(ctx, LOCATION,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ImageUploadedLog.location</code> attribute. 
	 * @param value the location - 本地路径
	 */
	public void setLocation(final String value)
	{
		setLocation( getSession().getSessionContext(), value );
	}
	
}
