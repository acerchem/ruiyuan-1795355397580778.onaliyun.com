/*
 * ----------------------------------------------------------------
 * --- WARNING: THIS FILE IS GENERATED AND WILL BE OVERWRITTEN! ---
 * --- Generated at 2018-3-21 14:43:28                          ---
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
	protected static final Map<String, AttributeMode> DEFAULT_INITIAL_ATTRIBUTES;
	static
	{
		final Map<String, AttributeMode> tmp = new HashMap<String, AttributeMode>();
		tmp.put(IMAGEPK, AttributeMode.INITIAL);
		tmp.put(ALIYUNURL, AttributeMode.INITIAL);
		DEFAULT_INITIAL_ATTRIBUTES = Collections.unmodifiableMap(tmp);
	}
	@Override
	protected Map<String, AttributeMode> getDefaultAttributeModes()
	{
		return DEFAULT_INITIAL_ATTRIBUTES;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ImageUploadedLog.aliyunUrl</code> attribute.
	 * @return the aliyunUrl - é¿éäºURL
	 */
	public String getAliyunUrl(final SessionContext ctx)
	{
		return (String)getProperty( ctx, ALIYUNURL);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ImageUploadedLog.aliyunUrl</code> attribute.
	 * @return the aliyunUrl - é¿éäºURL
	 */
	public String getAliyunUrl()
	{
		return getAliyunUrl( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ImageUploadedLog.aliyunUrl</code> attribute. 
	 * @param value the aliyunUrl - é¿éäºURL
	 */
	public void setAliyunUrl(final SessionContext ctx, final String value)
	{
		setProperty(ctx, ALIYUNURL,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ImageUploadedLog.aliyunUrl</code> attribute. 
	 * @param value the aliyunUrl - é¿éäºURL
	 */
	public void setAliyunUrl(final String value)
	{
		setAliyunUrl( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ImageUploadedLog.imagePK</code> attribute.
	 * @return the imagePK - å¾çPK
	 */
	public String getImagePK(final SessionContext ctx)
	{
		return (String)getProperty( ctx, IMAGEPK);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ImageUploadedLog.imagePK</code> attribute.
	 * @return the imagePK - å¾çPK
	 */
	public String getImagePK()
	{
		return getImagePK( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ImageUploadedLog.imagePK</code> attribute. 
	 * @param value the imagePK - å¾çPK
	 */
	public void setImagePK(final SessionContext ctx, final String value)
	{
		setProperty(ctx, IMAGEPK,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ImageUploadedLog.imagePK</code> attribute. 
	 * @param value the imagePK - å¾çPK
	 */
	public void setImagePK(final String value)
	{
		setImagePK( getSession().getSessionContext(), value );
	}
	
}
