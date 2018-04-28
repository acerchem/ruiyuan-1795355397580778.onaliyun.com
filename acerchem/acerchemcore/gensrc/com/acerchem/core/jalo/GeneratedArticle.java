/*
 * ----------------------------------------------------------------
 * --- WARNING: THIS FILE IS GENERATED AND WILL BE OVERWRITTEN! ---
 * --- Generated at 2018-4-28 18:44:38                          ---
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
 * Generated class for type {@link de.hybris.platform.jalo.GenericItem Article}.
 */
@SuppressWarnings({"deprecation","unused","cast","PMD"})
public abstract class GeneratedArticle extends GenericItem
{
	/** Qualifier of the <code>Article.articleTitle</code> attribute **/
	public static final String ARTICLETITLE = "articleTitle";
	/** Qualifier of the <code>Article.articleUrl</code> attribute **/
	public static final String ARTICLEURL = "articleUrl";
	protected static final Map<String, AttributeMode> DEFAULT_INITIAL_ATTRIBUTES;
	static
	{
		final Map<String, AttributeMode> tmp = new HashMap<String, AttributeMode>();
		tmp.put(ARTICLETITLE, AttributeMode.INITIAL);
		tmp.put(ARTICLEURL, AttributeMode.INITIAL);
		DEFAULT_INITIAL_ATTRIBUTES = Collections.unmodifiableMap(tmp);
	}
	@Override
	protected Map<String, AttributeMode> getDefaultAttributeModes()
	{
		return DEFAULT_INITIAL_ATTRIBUTES;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Article.articleTitle</code> attribute.
	 * @return the articleTitle - 文章标题
	 */
	public String getArticleTitle(final SessionContext ctx)
	{
		return (String)getProperty( ctx, ARTICLETITLE);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Article.articleTitle</code> attribute.
	 * @return the articleTitle - 文章标题
	 */
	public String getArticleTitle()
	{
		return getArticleTitle( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Article.articleTitle</code> attribute. 
	 * @param value the articleTitle - 文章标题
	 */
	public void setArticleTitle(final SessionContext ctx, final String value)
	{
		setProperty(ctx, ARTICLETITLE,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Article.articleTitle</code> attribute. 
	 * @param value the articleTitle - 文章标题
	 */
	public void setArticleTitle(final String value)
	{
		setArticleTitle( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Article.articleUrl</code> attribute.
	 * @return the articleUrl - 文件url
	 */
	public String getArticleUrl(final SessionContext ctx)
	{
		return (String)getProperty( ctx, ARTICLEURL);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Article.articleUrl</code> attribute.
	 * @return the articleUrl - 文件url
	 */
	public String getArticleUrl()
	{
		return getArticleUrl( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Article.articleUrl</code> attribute. 
	 * @param value the articleUrl - 文件url
	 */
	public void setArticleUrl(final SessionContext ctx, final String value)
	{
		setProperty(ctx, ARTICLEURL,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Article.articleUrl</code> attribute. 
	 * @param value the articleUrl - 文件url
	 */
	public void setArticleUrl(final String value)
	{
		setArticleUrl( getSession().getSessionContext(), value );
	}
	
}
