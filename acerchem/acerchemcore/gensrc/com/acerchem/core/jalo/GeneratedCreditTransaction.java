/*
 * ----------------------------------------------------------------
 * --- WARNING: THIS FILE IS GENERATED AND WILL BE OVERWRITTEN! ---
 * --- Generated at 2018-4-14 9:51:32                           ---
 * ----------------------------------------------------------------
 */
package com.acerchem.core.jalo;

import com.acerchem.core.constants.AcerchemCoreConstants;
import com.acerchem.core.jalo.CustomerCreditAccount;
import de.hybris.platform.jalo.GenericItem;
import de.hybris.platform.jalo.Item;
import de.hybris.platform.jalo.Item.AttributeMode;
import de.hybris.platform.jalo.JaloBusinessException;
import de.hybris.platform.jalo.SessionContext;
import de.hybris.platform.jalo.type.CollectionType;
import de.hybris.platform.jalo.type.ComposedType;
import de.hybris.platform.util.BidirectionalOneToManyHandler;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Generated class for type {@link de.hybris.platform.jalo.GenericItem CreditTransaction}.
 */
@SuppressWarnings({"deprecation","unused","cast","PMD"})
public abstract class GeneratedCreditTransaction extends GenericItem
{
	/** Qualifier of the <code>CreditTransaction.creaditUsedAmount</code> attribute **/
	public static final String CREADITUSEDAMOUNT = "creaditUsedAmount";
	/** Qualifier of the <code>CreditTransaction.paybackAmount</code> attribute **/
	public static final String PAYBACKAMOUNT = "paybackAmount";
	/** Qualifier of the <code>CreditTransaction.isPayback</code> attribute **/
	public static final String ISPAYBACK = "isPayback";
	/** Qualifier of the <code>CreditTransaction.paybackTime</code> attribute **/
	public static final String PAYBACKTIME = "paybackTime";
	/** Qualifier of the <code>CreditTransaction.shouldPaybackTime</code> attribute **/
	public static final String SHOULDPAYBACKTIME = "shouldPaybackTime";
	/** Qualifier of the <code>CreditTransaction.cransactionId</code> attribute **/
	public static final String CRANSACTIONID = "cransactionId";
	/** Qualifier of the <code>CreditTransaction.creditAccountPOS</code> attribute **/
	public static final String CREDITACCOUNTPOS = "creditAccountPOS";
	/** Qualifier of the <code>CreditTransaction.creditAccount</code> attribute **/
	public static final String CREDITACCOUNT = "creditAccount";
	/**
	* {@link BidirectionalOneToManyHandler} for handling 1:n CREDITACCOUNT's relation attributes from 'one' side.
	**/
	protected static final BidirectionalOneToManyHandler<GeneratedCreditTransaction> CREDITACCOUNTHANDLER = new BidirectionalOneToManyHandler<GeneratedCreditTransaction>(
	AcerchemCoreConstants.TC.CREDITTRANSACTION,
	false,
	"creditAccount",
	"creditAccountPOS",
	true,
	true,
	CollectionType.LIST
	);
	protected static final Map<String, AttributeMode> DEFAULT_INITIAL_ATTRIBUTES;
	static
	{
		final Map<String, AttributeMode> tmp = new HashMap<String, AttributeMode>();
		tmp.put(CREADITUSEDAMOUNT, AttributeMode.INITIAL);
		tmp.put(PAYBACKAMOUNT, AttributeMode.INITIAL);
		tmp.put(ISPAYBACK, AttributeMode.INITIAL);
		tmp.put(PAYBACKTIME, AttributeMode.INITIAL);
		tmp.put(SHOULDPAYBACKTIME, AttributeMode.INITIAL);
		tmp.put(CRANSACTIONID, AttributeMode.INITIAL);
		tmp.put(CREDITACCOUNTPOS, AttributeMode.INITIAL);
		tmp.put(CREDITACCOUNT, AttributeMode.INITIAL);
		DEFAULT_INITIAL_ATTRIBUTES = Collections.unmodifiableMap(tmp);
	}
	@Override
	protected Map<String, AttributeMode> getDefaultAttributeModes()
	{
		return DEFAULT_INITIAL_ATTRIBUTES;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>CreditTransaction.cransactionId</code> attribute.
	 * @return the cransactionId
	 */
	public String getCransactionId(final SessionContext ctx)
	{
		return (String)getProperty( ctx, CRANSACTIONID);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>CreditTransaction.cransactionId</code> attribute.
	 * @return the cransactionId
	 */
	public String getCransactionId()
	{
		return getCransactionId( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>CreditTransaction.cransactionId</code> attribute. 
	 * @param value the cransactionId
	 */
	public void setCransactionId(final SessionContext ctx, final String value)
	{
		setProperty(ctx, CRANSACTIONID,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>CreditTransaction.cransactionId</code> attribute. 
	 * @param value the cransactionId
	 */
	public void setCransactionId(final String value)
	{
		setCransactionId( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>CreditTransaction.creaditUsedAmount</code> attribute.
	 * @return the creaditUsedAmount - 消费金额
	 */
	public BigDecimal getCreaditUsedAmount(final SessionContext ctx)
	{
		return (BigDecimal)getProperty( ctx, CREADITUSEDAMOUNT);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>CreditTransaction.creaditUsedAmount</code> attribute.
	 * @return the creaditUsedAmount - 消费金额
	 */
	public BigDecimal getCreaditUsedAmount()
	{
		return getCreaditUsedAmount( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>CreditTransaction.creaditUsedAmount</code> attribute. 
	 * @param value the creaditUsedAmount - 消费金额
	 */
	public void setCreaditUsedAmount(final SessionContext ctx, final BigDecimal value)
	{
		setProperty(ctx, CREADITUSEDAMOUNT,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>CreditTransaction.creaditUsedAmount</code> attribute. 
	 * @param value the creaditUsedAmount - 消费金额
	 */
	public void setCreaditUsedAmount(final BigDecimal value)
	{
		setCreaditUsedAmount( getSession().getSessionContext(), value );
	}
	
	@Override
	protected Item createItem(final SessionContext ctx, final ComposedType type, final ItemAttributeMap allAttributes) throws JaloBusinessException
	{
		CREDITACCOUNTHANDLER.newInstance(ctx, allAttributes);
		return super.createItem( ctx, type, allAttributes );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>CreditTransaction.creditAccount</code> attribute.
	 * @return the creditAccount
	 */
	public CustomerCreditAccount getCreditAccount(final SessionContext ctx)
	{
		return (CustomerCreditAccount)getProperty( ctx, CREDITACCOUNT);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>CreditTransaction.creditAccount</code> attribute.
	 * @return the creditAccount
	 */
	public CustomerCreditAccount getCreditAccount()
	{
		return getCreditAccount( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>CreditTransaction.creditAccount</code> attribute. 
	 * @param value the creditAccount
	 */
	public void setCreditAccount(final SessionContext ctx, final CustomerCreditAccount value)
	{
		CREDITACCOUNTHANDLER.addValue( ctx, value, this  );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>CreditTransaction.creditAccount</code> attribute. 
	 * @param value the creditAccount
	 */
	public void setCreditAccount(final CustomerCreditAccount value)
	{
		setCreditAccount( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>CreditTransaction.creditAccountPOS</code> attribute.
	 * @return the creditAccountPOS
	 */
	 Integer getCreditAccountPOS(final SessionContext ctx)
	{
		return (Integer)getProperty( ctx, CREDITACCOUNTPOS);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>CreditTransaction.creditAccountPOS</code> attribute.
	 * @return the creditAccountPOS
	 */
	 Integer getCreditAccountPOS()
	{
		return getCreditAccountPOS( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>CreditTransaction.creditAccountPOS</code> attribute. 
	 * @return the creditAccountPOS
	 */
	 int getCreditAccountPOSAsPrimitive(final SessionContext ctx)
	{
		Integer value = getCreditAccountPOS( ctx );
		return value != null ? value.intValue() : 0;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>CreditTransaction.creditAccountPOS</code> attribute. 
	 * @return the creditAccountPOS
	 */
	 int getCreditAccountPOSAsPrimitive()
	{
		return getCreditAccountPOSAsPrimitive( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>CreditTransaction.creditAccountPOS</code> attribute. 
	 * @param value the creditAccountPOS
	 */
	 void setCreditAccountPOS(final SessionContext ctx, final Integer value)
	{
		setProperty(ctx, CREDITACCOUNTPOS,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>CreditTransaction.creditAccountPOS</code> attribute. 
	 * @param value the creditAccountPOS
	 */
	 void setCreditAccountPOS(final Integer value)
	{
		setCreditAccountPOS( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>CreditTransaction.creditAccountPOS</code> attribute. 
	 * @param value the creditAccountPOS
	 */
	 void setCreditAccountPOS(final SessionContext ctx, final int value)
	{
		setCreditAccountPOS( ctx,Integer.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>CreditTransaction.creditAccountPOS</code> attribute. 
	 * @param value the creditAccountPOS
	 */
	 void setCreditAccountPOS(final int value)
	{
		setCreditAccountPOS( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>CreditTransaction.isPayback</code> attribute.
	 * @return the isPayback - 是否已还款
	 */
	public Boolean isIsPayback(final SessionContext ctx)
	{
		return (Boolean)getProperty( ctx, ISPAYBACK);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>CreditTransaction.isPayback</code> attribute.
	 * @return the isPayback - 是否已还款
	 */
	public Boolean isIsPayback()
	{
		return isIsPayback( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>CreditTransaction.isPayback</code> attribute. 
	 * @return the isPayback - 是否已还款
	 */
	public boolean isIsPaybackAsPrimitive(final SessionContext ctx)
	{
		Boolean value = isIsPayback( ctx );
		return value != null ? value.booleanValue() : false;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>CreditTransaction.isPayback</code> attribute. 
	 * @return the isPayback - 是否已还款
	 */
	public boolean isIsPaybackAsPrimitive()
	{
		return isIsPaybackAsPrimitive( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>CreditTransaction.isPayback</code> attribute. 
	 * @param value the isPayback - 是否已还款
	 */
	public void setIsPayback(final SessionContext ctx, final Boolean value)
	{
		setProperty(ctx, ISPAYBACK,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>CreditTransaction.isPayback</code> attribute. 
	 * @param value the isPayback - 是否已还款
	 */
	public void setIsPayback(final Boolean value)
	{
		setIsPayback( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>CreditTransaction.isPayback</code> attribute. 
	 * @param value the isPayback - 是否已还款
	 */
	public void setIsPayback(final SessionContext ctx, final boolean value)
	{
		setIsPayback( ctx,Boolean.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>CreditTransaction.isPayback</code> attribute. 
	 * @param value the isPayback - 是否已还款
	 */
	public void setIsPayback(final boolean value)
	{
		setIsPayback( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>CreditTransaction.paybackAmount</code> attribute.
	 * @return the paybackAmount - 还款金额
	 */
	public BigDecimal getPaybackAmount(final SessionContext ctx)
	{
		return (BigDecimal)getProperty( ctx, PAYBACKAMOUNT);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>CreditTransaction.paybackAmount</code> attribute.
	 * @return the paybackAmount - 还款金额
	 */
	public BigDecimal getPaybackAmount()
	{
		return getPaybackAmount( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>CreditTransaction.paybackAmount</code> attribute. 
	 * @param value the paybackAmount - 还款金额
	 */
	public void setPaybackAmount(final SessionContext ctx, final BigDecimal value)
	{
		setProperty(ctx, PAYBACKAMOUNT,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>CreditTransaction.paybackAmount</code> attribute. 
	 * @param value the paybackAmount - 还款金额
	 */
	public void setPaybackAmount(final BigDecimal value)
	{
		setPaybackAmount( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>CreditTransaction.paybackTime</code> attribute.
	 * @return the paybackTime - 实际还款日期
	 */
	public Long getPaybackTime(final SessionContext ctx)
	{
		return (Long)getProperty( ctx, PAYBACKTIME);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>CreditTransaction.paybackTime</code> attribute.
	 * @return the paybackTime - 实际还款日期
	 */
	public Long getPaybackTime()
	{
		return getPaybackTime( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>CreditTransaction.paybackTime</code> attribute. 
	 * @return the paybackTime - 实际还款日期
	 */
	public long getPaybackTimeAsPrimitive(final SessionContext ctx)
	{
		Long value = getPaybackTime( ctx );
		return value != null ? value.longValue() : 0;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>CreditTransaction.paybackTime</code> attribute. 
	 * @return the paybackTime - 实际还款日期
	 */
	public long getPaybackTimeAsPrimitive()
	{
		return getPaybackTimeAsPrimitive( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>CreditTransaction.paybackTime</code> attribute. 
	 * @param value the paybackTime - 实际还款日期
	 */
	public void setPaybackTime(final SessionContext ctx, final Long value)
	{
		setProperty(ctx, PAYBACKTIME,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>CreditTransaction.paybackTime</code> attribute. 
	 * @param value the paybackTime - 实际还款日期
	 */
	public void setPaybackTime(final Long value)
	{
		setPaybackTime( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>CreditTransaction.paybackTime</code> attribute. 
	 * @param value the paybackTime - 实际还款日期
	 */
	public void setPaybackTime(final SessionContext ctx, final long value)
	{
		setPaybackTime( ctx,Long.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>CreditTransaction.paybackTime</code> attribute. 
	 * @param value the paybackTime - 实际还款日期
	 */
	public void setPaybackTime(final long value)
	{
		setPaybackTime( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>CreditTransaction.shouldPaybackTime</code> attribute.
	 * @return the shouldPaybackTime - 还款日
	 */
	public Long getShouldPaybackTime(final SessionContext ctx)
	{
		return (Long)getProperty( ctx, SHOULDPAYBACKTIME);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>CreditTransaction.shouldPaybackTime</code> attribute.
	 * @return the shouldPaybackTime - 还款日
	 */
	public Long getShouldPaybackTime()
	{
		return getShouldPaybackTime( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>CreditTransaction.shouldPaybackTime</code> attribute. 
	 * @return the shouldPaybackTime - 还款日
	 */
	public long getShouldPaybackTimeAsPrimitive(final SessionContext ctx)
	{
		Long value = getShouldPaybackTime( ctx );
		return value != null ? value.longValue() : 0;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>CreditTransaction.shouldPaybackTime</code> attribute. 
	 * @return the shouldPaybackTime - 还款日
	 */
	public long getShouldPaybackTimeAsPrimitive()
	{
		return getShouldPaybackTimeAsPrimitive( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>CreditTransaction.shouldPaybackTime</code> attribute. 
	 * @param value the shouldPaybackTime - 还款日
	 */
	public void setShouldPaybackTime(final SessionContext ctx, final Long value)
	{
		setProperty(ctx, SHOULDPAYBACKTIME,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>CreditTransaction.shouldPaybackTime</code> attribute. 
	 * @param value the shouldPaybackTime - 还款日
	 */
	public void setShouldPaybackTime(final Long value)
	{
		setShouldPaybackTime( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>CreditTransaction.shouldPaybackTime</code> attribute. 
	 * @param value the shouldPaybackTime - 还款日
	 */
	public void setShouldPaybackTime(final SessionContext ctx, final long value)
	{
		setShouldPaybackTime( ctx,Long.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>CreditTransaction.shouldPaybackTime</code> attribute. 
	 * @param value the shouldPaybackTime - 还款日
	 */
	public void setShouldPaybackTime(final long value)
	{
		setShouldPaybackTime( getSession().getSessionContext(), value );
	}
	
}
