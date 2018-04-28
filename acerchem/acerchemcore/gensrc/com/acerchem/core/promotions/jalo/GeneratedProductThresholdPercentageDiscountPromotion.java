/*
 * ----------------------------------------------------------------
 * --- WARNING: THIS FILE IS GENERATED AND WILL BE OVERWRITTEN! ---
 * --- Generated at 2018-4-28 19:14:12                          ---
 * ----------------------------------------------------------------
 */
package com.acerchem.core.promotions.jalo;

import com.acerchem.core.constants.AcerchemCoreConstants;
import com.acerchem.core.jalo.PromotionThresholdDiscount;
import de.hybris.platform.jalo.Item.AttributeMode;
import de.hybris.platform.jalo.JaloInvalidParameterException;
import de.hybris.platform.jalo.SessionContext;
import de.hybris.platform.jalo.c2l.C2LManager;
import de.hybris.platform.jalo.c2l.Language;
import de.hybris.platform.promotions.jalo.ProductPromotion;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Generated class for type {@link com.acerchem.core.promotions.jalo.ProductThresholdPercentageDiscountPromotion ProductThresholdPercentageDiscountPromotion}.
 */
@SuppressWarnings({"deprecation","unused","cast","PMD"})
public abstract class GeneratedProductThresholdPercentageDiscountPromotion extends ProductPromotion
{
	/** Qualifier of the <code>ProductThresholdPercentageDiscountPromotion.thresholdDiscounts</code> attribute **/
	public static final String THRESHOLDDISCOUNTS = "thresholdDiscounts";
	/** Qualifier of the <code>ProductThresholdPercentageDiscountPromotion.messageFired</code> attribute **/
	public static final String MESSAGEFIRED = "messageFired";
	protected static final Map<String, AttributeMode> DEFAULT_INITIAL_ATTRIBUTES;
	static
	{
		final Map<String, AttributeMode> tmp = new HashMap<String, AttributeMode>(ProductPromotion.DEFAULT_INITIAL_ATTRIBUTES);
		tmp.put(THRESHOLDDISCOUNTS, AttributeMode.INITIAL);
		tmp.put(MESSAGEFIRED, AttributeMode.INITIAL);
		DEFAULT_INITIAL_ATTRIBUTES = Collections.unmodifiableMap(tmp);
	}
	@Override
	protected Map<String, AttributeMode> getDefaultAttributeModes()
	{
		return DEFAULT_INITIAL_ATTRIBUTES;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ProductThresholdPercentageDiscountPromotion.messageFired</code> attribute.
	 * @return the messageFired - The message to show when the promotion has fired.
	 */
	public String getMessageFired(final SessionContext ctx)
	{
		if( ctx == null || ctx.getLanguage() == null )
		{
			throw new JaloInvalidParameterException("GeneratedProductThresholdPercentageDiscountPromotion.getMessageFired requires a session language", 0 );
		}
		return (String)getLocalizedProperty( ctx, MESSAGEFIRED);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ProductThresholdPercentageDiscountPromotion.messageFired</code> attribute.
	 * @return the messageFired - The message to show when the promotion has fired.
	 */
	public String getMessageFired()
	{
		return getMessageFired( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ProductThresholdPercentageDiscountPromotion.messageFired</code> attribute. 
	 * @return the localized messageFired - The message to show when the promotion has fired.
	 */
	public Map<Language,String> getAllMessageFired(final SessionContext ctx)
	{
		return (Map<Language,String>)getAllLocalizedProperties(ctx,MESSAGEFIRED,C2LManager.getInstance().getAllLanguages());
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ProductThresholdPercentageDiscountPromotion.messageFired</code> attribute. 
	 * @return the localized messageFired - The message to show when the promotion has fired.
	 */
	public Map<Language,String> getAllMessageFired()
	{
		return getAllMessageFired( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ProductThresholdPercentageDiscountPromotion.messageFired</code> attribute. 
	 * @param value the messageFired - The message to show when the promotion has fired.
	 */
	public void setMessageFired(final SessionContext ctx, final String value)
	{
		if( ctx == null || ctx.getLanguage() == null )
		{
			throw new JaloInvalidParameterException("GeneratedProductThresholdPercentageDiscountPromotion.setMessageFired requires a session language", 0 );
		}
		setLocalizedProperty(ctx, MESSAGEFIRED,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ProductThresholdPercentageDiscountPromotion.messageFired</code> attribute. 
	 * @param value the messageFired - The message to show when the promotion has fired.
	 */
	public void setMessageFired(final String value)
	{
		setMessageFired( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ProductThresholdPercentageDiscountPromotion.messageFired</code> attribute. 
	 * @param value the messageFired - The message to show when the promotion has fired.
	 */
	public void setAllMessageFired(final SessionContext ctx, final Map<Language,String> value)
	{
		setAllLocalizedProperties(ctx,MESSAGEFIRED,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ProductThresholdPercentageDiscountPromotion.messageFired</code> attribute. 
	 * @param value the messageFired - The message to show when the promotion has fired.
	 */
	public void setAllMessageFired(final Map<Language,String> value)
	{
		setAllMessageFired( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ProductThresholdPercentageDiscountPromotion.thresholdDiscounts</code> attribute.
	 * @return the thresholdDiscounts - threshold Percentage discount.
	 */
	public List<PromotionThresholdDiscount> getThresholdDiscounts(final SessionContext ctx)
	{
		List<PromotionThresholdDiscount> coll = (List<PromotionThresholdDiscount>)getProperty( ctx, THRESHOLDDISCOUNTS);
		return coll != null ? coll : Collections.EMPTY_LIST;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ProductThresholdPercentageDiscountPromotion.thresholdDiscounts</code> attribute.
	 * @return the thresholdDiscounts - threshold Percentage discount.
	 */
	public List<PromotionThresholdDiscount> getThresholdDiscounts()
	{
		return getThresholdDiscounts( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ProductThresholdPercentageDiscountPromotion.thresholdDiscounts</code> attribute. 
	 * @param value the thresholdDiscounts - threshold Percentage discount.
	 */
	public void setThresholdDiscounts(final SessionContext ctx, final List<PromotionThresholdDiscount> value)
	{
		setProperty(ctx, THRESHOLDDISCOUNTS,value == null || !value.isEmpty() ? value : null );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ProductThresholdPercentageDiscountPromotion.thresholdDiscounts</code> attribute. 
	 * @param value the thresholdDiscounts - threshold Percentage discount.
	 */
	public void setThresholdDiscounts(final List<PromotionThresholdDiscount> value)
	{
		setThresholdDiscounts( getSession().getSessionContext(), value );
	}
	
}
