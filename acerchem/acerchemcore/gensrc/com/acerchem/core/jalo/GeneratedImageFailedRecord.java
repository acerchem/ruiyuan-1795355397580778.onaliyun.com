/*
 * ----------------------------------------------------------------
 * --- WARNING: THIS FILE IS GENERATED AND WILL BE OVERWRITTEN! ---
 * --- Generated at 2018-4-2 22:42:54                           ---
 * ----------------------------------------------------------------
 */
package com.acerchem.core.jalo;

import com.acerchem.core.constants.AcerchemCoreConstants;
import de.hybris.platform.jalo.GenericItem;
import de.hybris.platform.jalo.Item.AttributeMode;
import de.hybris.platform.jalo.SessionContext;
import de.hybris.platform.jalo.enumeration.EnumerationValue;
import de.hybris.platform.jalo.media.Media;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Generated class for type {@link de.hybris.platform.jalo.GenericItem ImageFailedRecord}.
 */
@SuppressWarnings({"deprecation","unused","cast","PMD"})
public abstract class GeneratedImageFailedRecord extends GenericItem
{
	/** Qualifier of the <code>ImageFailedRecord.fileName</code> attribute **/
	public static final String FILENAME = "fileName";
	/** Qualifier of the <code>ImageFailedRecord.actionType</code> attribute **/
	public static final String ACTIONTYPE = "actionType";
	/** Qualifier of the <code>ImageFailedRecord.aliyunUrl</code> attribute **/
	public static final String ALIYUNURL = "aliyunUrl";
	/** Qualifier of the <code>ImageFailedRecord.location</code> attribute **/
	public static final String LOCATION = "location";
	/** Qualifier of the <code>ImageFailedRecord.status</code> attribute **/
	public static final String STATUS = "status";
	/** Qualifier of the <code>ImageFailedRecord.mediaData</code> attribute **/
	public static final String MEDIADATA = "mediaData";
	/** Qualifier of the <code>ImageFailedRecord.mediaPK</code> attribute **/
	public static final String MEDIAPK = "mediaPK";
	protected static final Map<String, AttributeMode> DEFAULT_INITIAL_ATTRIBUTES;
	static
	{
		final Map<String, AttributeMode> tmp = new HashMap<String, AttributeMode>();
		tmp.put(FILENAME, AttributeMode.INITIAL);
		tmp.put(ACTIONTYPE, AttributeMode.INITIAL);
		tmp.put(ALIYUNURL, AttributeMode.INITIAL);
		tmp.put(LOCATION, AttributeMode.INITIAL);
		tmp.put(STATUS, AttributeMode.INITIAL);
		tmp.put(MEDIADATA, AttributeMode.INITIAL);
		tmp.put(MEDIAPK, AttributeMode.INITIAL);
		DEFAULT_INITIAL_ATTRIBUTES = Collections.unmodifiableMap(tmp);
	}
	@Override
	protected Map<String, AttributeMode> getDefaultAttributeModes()
	{
		return DEFAULT_INITIAL_ATTRIBUTES;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ImageFailedRecord.actionType</code> attribute.
	 * @return the actionType - 操作类型
	 */
	public EnumerationValue getActionType(final SessionContext ctx)
	{
		return (EnumerationValue)getProperty( ctx, ACTIONTYPE);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ImageFailedRecord.actionType</code> attribute.
	 * @return the actionType - 操作类型
	 */
	public EnumerationValue getActionType()
	{
		return getActionType( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ImageFailedRecord.actionType</code> attribute. 
	 * @param value the actionType - 操作类型
	 */
	public void setActionType(final SessionContext ctx, final EnumerationValue value)
	{
		setProperty(ctx, ACTIONTYPE,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ImageFailedRecord.actionType</code> attribute. 
	 * @param value the actionType - 操作类型
	 */
	public void setActionType(final EnumerationValue value)
	{
		setActionType( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ImageFailedRecord.aliyunUrl</code> attribute.
	 * @return the aliyunUrl - 阿里云Url
	 */
	public String getAliyunUrl(final SessionContext ctx)
	{
		return (String)getProperty( ctx, ALIYUNURL);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ImageFailedRecord.aliyunUrl</code> attribute.
	 * @return the aliyunUrl - 阿里云Url
	 */
	public String getAliyunUrl()
	{
		return getAliyunUrl( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ImageFailedRecord.aliyunUrl</code> attribute. 
	 * @param value the aliyunUrl - 阿里云Url
	 */
	public void setAliyunUrl(final SessionContext ctx, final String value)
	{
		setProperty(ctx, ALIYUNURL,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ImageFailedRecord.aliyunUrl</code> attribute. 
	 * @param value the aliyunUrl - 阿里云Url
	 */
	public void setAliyunUrl(final String value)
	{
		setAliyunUrl( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ImageFailedRecord.fileName</code> attribute.
	 * @return the fileName - 文件名
	 */
	public String getFileName(final SessionContext ctx)
	{
		return (String)getProperty( ctx, FILENAME);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ImageFailedRecord.fileName</code> attribute.
	 * @return the fileName - 文件名
	 */
	public String getFileName()
	{
		return getFileName( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ImageFailedRecord.fileName</code> attribute. 
	 * @param value the fileName - 文件名
	 */
	public void setFileName(final SessionContext ctx, final String value)
	{
		setProperty(ctx, FILENAME,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ImageFailedRecord.fileName</code> attribute. 
	 * @param value the fileName - 文件名
	 */
	public void setFileName(final String value)
	{
		setFileName( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ImageFailedRecord.location</code> attribute.
	 * @return the location - 本地路径
	 */
	public String getLocation(final SessionContext ctx)
	{
		return (String)getProperty( ctx, LOCATION);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ImageFailedRecord.location</code> attribute.
	 * @return the location - 本地路径
	 */
	public String getLocation()
	{
		return getLocation( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ImageFailedRecord.location</code> attribute. 
	 * @param value the location - 本地路径
	 */
	public void setLocation(final SessionContext ctx, final String value)
	{
		setProperty(ctx, LOCATION,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ImageFailedRecord.location</code> attribute. 
	 * @param value the location - 本地路径
	 */
	public void setLocation(final String value)
	{
		setLocation( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ImageFailedRecord.mediaData</code> attribute.
	 * @return the mediaData - mediaData
	 */
	public Media getMediaData(final SessionContext ctx)
	{
		return (Media)getProperty( ctx, MEDIADATA);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ImageFailedRecord.mediaData</code> attribute.
	 * @return the mediaData - mediaData
	 */
	public Media getMediaData()
	{
		return getMediaData( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ImageFailedRecord.mediaData</code> attribute. 
	 * @param value the mediaData - mediaData
	 */
	public void setMediaData(final SessionContext ctx, final Media value)
	{
		setProperty(ctx, MEDIADATA,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ImageFailedRecord.mediaData</code> attribute. 
	 * @param value the mediaData - mediaData
	 */
	public void setMediaData(final Media value)
	{
		setMediaData( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ImageFailedRecord.mediaPK</code> attribute.
	 * @return the mediaPK - mediaPk
	 */
	public String getMediaPK(final SessionContext ctx)
	{
		return (String)getProperty( ctx, MEDIAPK);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ImageFailedRecord.mediaPK</code> attribute.
	 * @return the mediaPK - mediaPk
	 */
	public String getMediaPK()
	{
		return getMediaPK( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ImageFailedRecord.mediaPK</code> attribute. 
	 * @param value the mediaPK - mediaPk
	 */
	public void setMediaPK(final SessionContext ctx, final String value)
	{
		setProperty(ctx, MEDIAPK,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ImageFailedRecord.mediaPK</code> attribute. 
	 * @param value the mediaPK - mediaPk
	 */
	public void setMediaPK(final String value)
	{
		setMediaPK( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ImageFailedRecord.status</code> attribute.
	 * @return the status - 状态(初始状态为0，其他为失败次数+1)
	 */
	public String getStatus(final SessionContext ctx)
	{
		return (String)getProperty( ctx, STATUS);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ImageFailedRecord.status</code> attribute.
	 * @return the status - 状态(初始状态为0，其他为失败次数+1)
	 */
	public String getStatus()
	{
		return getStatus( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ImageFailedRecord.status</code> attribute. 
	 * @param value the status - 状态(初始状态为0，其他为失败次数+1)
	 */
	public void setStatus(final SessionContext ctx, final String value)
	{
		setProperty(ctx, STATUS,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ImageFailedRecord.status</code> attribute. 
	 * @param value the status - 状态(初始状态为0，其他为失败次数+1)
	 */
	public void setStatus(final String value)
	{
		setStatus( getSession().getSessionContext(), value );
	}
	
}
