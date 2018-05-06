/*
 * ----------------------------------------------------------------
 * --- WARNING: THIS FILE IS GENERATED AND WILL BE OVERWRITTEN! ---
 * --- Generated at 2018-5-6 23:04:14                           ---
 * ----------------------------------------------------------------
 */
package com.acerchem.core.jalo;

import com.acerchem.core.constants.AcerchemCoreConstants;
import com.acerchem.core.jalo.CreditTransaction;
import de.hybris.platform.constants.CoreConstants;
import de.hybris.platform.jalo.GenericItem;
import de.hybris.platform.jalo.Item.AttributeMode;
import de.hybris.platform.jalo.SessionContext;
import de.hybris.platform.jalo.enumeration.EnumerationValue;
import de.hybris.platform.jalo.type.CollectionType;
import de.hybris.platform.jalo.user.Customer;
import de.hybris.platform.util.OneToManyHandler;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Generated class for type {@link de.hybris.platform.jalo.GenericItem CustomerCreditAccount}.
 */
@SuppressWarnings({"deprecation","unused","cast","PMD"})
public abstract class GeneratedCustomerCreditAccount extends GenericItem
{
	/** Qualifier of the <code>CustomerCreditAccount.billingInterval</code> attribute **/
	public static final String BILLINGINTERVAL = "billingInterval";
	/** Qualifier of the <code>CustomerCreditAccount.creaditRemainedAmount</code> attribute **/
	public static final String CREADITREMAINEDAMOUNT = "creaditRemainedAmount";
	/** Qualifier of the <code>CustomerCreditAccount.creditTotalAmount</code> attribute **/
	public static final String CREDITTOTALAMOUNT = "creditTotalAmount";
	/** Qualifier of the <code>CustomerCreditAccount.status</code> attribute **/
	public static final String STATUS = "status";
	/** Qualifier of the <code>CustomerCreditAccount.transactions</code> attribute **/
	public static final String TRANSACTIONS = "transactions";
	/** Qualifier of the <code>CustomerCreditAccount.customer</code> attribute **/
	public static final String CUSTOMER = "customer";
	/**
	* {@link OneToManyHandler} for handling 1:n TRANSACTIONS's relation attributes from 'many' side.
	**/
	protected static final OneToManyHandler<CreditTransaction> TRANSACTIONSHANDLER = new OneToManyHandler<CreditTransaction>(
	AcerchemCoreConstants.TC.CREDITTRANSACTION,
	false,
	"creditAccount",
	"creditAccountPOS",
	true,
	true,
	CollectionType.LIST
	);
	/**
	* {@link OneToManyHandler} for handling 1:n CUSTOMER's relation attributes from 'many' side.
	**/
	protected static final OneToManyHandler<Customer> CUSTOMERHANDLER = new OneToManyHandler<Customer>(
	CoreConstants.TC.CUSTOMER,
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
		tmp.put(BILLINGINTERVAL, AttributeMode.INITIAL);
		tmp.put(CREADITREMAINEDAMOUNT, AttributeMode.INITIAL);
		tmp.put(CREDITTOTALAMOUNT, AttributeMode.INITIAL);
		tmp.put(STATUS, AttributeMode.INITIAL);
		DEFAULT_INITIAL_ATTRIBUTES = Collections.unmodifiableMap(tmp);
	}
	@Override
	protected Map<String, AttributeMode> getDefaultAttributeModes()
	{
		return DEFAULT_INITIAL_ATTRIBUTES;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>CustomerCreditAccount.billingInterval</code> attribute.
	 * @return the billingInterval - 账期时间
	 */
	public Integer getBillingInterval(final SessionContext ctx)
	{
		return (Integer)getProperty( ctx, BILLINGINTERVAL);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>CustomerCreditAccount.billingInterval</code> attribute.
	 * @return the billingInterval - 账期时间
	 */
	public Integer getBillingInterval()
	{
		return getBillingInterval( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>CustomerCreditAccount.billingInterval</code> attribute. 
	 * @return the billingInterval - 账期时间
	 */
	public int getBillingIntervalAsPrimitive(final SessionContext ctx)
	{
		Integer value = getBillingInterval( ctx );
		return value != null ? value.intValue() : 0;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>CustomerCreditAccount.billingInterval</code> attribute. 
	 * @return the billingInterval - 账期时间
	 */
	public int getBillingIntervalAsPrimitive()
	{
		return getBillingIntervalAsPrimitive( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>CustomerCreditAccount.billingInterval</code> attribute. 
	 * @param value the billingInterval - 账期时间
	 */
	public void setBillingInterval(final SessionContext ctx, final Integer value)
	{
		setProperty(ctx, BILLINGINTERVAL,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>CustomerCreditAccount.billingInterval</code> attribute. 
	 * @param value the billingInterval - 账期时间
	 */
	public void setBillingInterval(final Integer value)
	{
		setBillingInterval( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>CustomerCreditAccount.billingInterval</code> attribute. 
	 * @param value the billingInterval - 账期时间
	 */
	public void setBillingInterval(final SessionContext ctx, final int value)
	{
		setBillingInterval( ctx,Integer.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>CustomerCreditAccount.billingInterval</code> attribute. 
	 * @param value the billingInterval - 账期时间
	 */
	public void setBillingInterval(final int value)
	{
		setBillingInterval( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>CustomerCreditAccount.creaditRemainedAmount</code> attribute.
	 * @return the creaditRemainedAmount - 可用额度
	 */
	public BigDecimal getCreaditRemainedAmount(final SessionContext ctx)
	{
		return (BigDecimal)getProperty( ctx, CREADITREMAINEDAMOUNT);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>CustomerCreditAccount.creaditRemainedAmount</code> attribute.
	 * @return the creaditRemainedAmount - 可用额度
	 */
	public BigDecimal getCreaditRemainedAmount()
	{
		return getCreaditRemainedAmount( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>CustomerCreditAccount.creaditRemainedAmount</code> attribute. 
	 * @param value the creaditRemainedAmount - 可用额度
	 */
	public void setCreaditRemainedAmount(final SessionContext ctx, final BigDecimal value)
	{
		setProperty(ctx, CREADITREMAINEDAMOUNT,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>CustomerCreditAccount.creaditRemainedAmount</code> attribute. 
	 * @param value the creaditRemainedAmount - 可用额度
	 */
	public void setCreaditRemainedAmount(final BigDecimal value)
	{
		setCreaditRemainedAmount( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>CustomerCreditAccount.creditTotalAmount</code> attribute.
	 * @return the creditTotalAmount - 总额度
	 */
	public BigDecimal getCreditTotalAmount(final SessionContext ctx)
	{
		return (BigDecimal)getProperty( ctx, CREDITTOTALAMOUNT);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>CustomerCreditAccount.creditTotalAmount</code> attribute.
	 * @return the creditTotalAmount - 总额度
	 */
	public BigDecimal getCreditTotalAmount()
	{
		return getCreditTotalAmount( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>CustomerCreditAccount.creditTotalAmount</code> attribute. 
	 * @param value the creditTotalAmount - 总额度
	 */
	public void setCreditTotalAmount(final SessionContext ctx, final BigDecimal value)
	{
		setProperty(ctx, CREDITTOTALAMOUNT,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>CustomerCreditAccount.creditTotalAmount</code> attribute. 
	 * @param value the creditTotalAmount - 总额度
	 */
	public void setCreditTotalAmount(final BigDecimal value)
	{
		setCreditTotalAmount( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>CustomerCreditAccount.customer</code> attribute.
	 * @return the customer
	 */
	public List<Customer> getCustomer(final SessionContext ctx)
	{
		return (List<Customer>)CUSTOMERHANDLER.getValues( ctx, this );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>CustomerCreditAccount.customer</code> attribute.
	 * @return the customer
	 */
	public List<Customer> getCustomer()
	{
		return getCustomer( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>CustomerCreditAccount.customer</code> attribute. 
	 * @param value the customer
	 */
	public void setCustomer(final SessionContext ctx, final List<Customer> value)
	{
		CUSTOMERHANDLER.setValues( ctx, this, value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>CustomerCreditAccount.customer</code> attribute. 
	 * @param value the customer
	 */
	public void setCustomer(final List<Customer> value)
	{
		setCustomer( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Adds <code>value</code> to customer. 
	 * @param value the item to add to customer
	 */
	public void addToCustomer(final SessionContext ctx, final Customer value)
	{
		CUSTOMERHANDLER.addValue( ctx, this, value );
	}
	
	/**
	 * <i>Generated method</i> - Adds <code>value</code> to customer. 
	 * @param value the item to add to customer
	 */
	public void addToCustomer(final Customer value)
	{
		addToCustomer( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Removes <code>value</code> from customer. 
	 * @param value the item to remove from customer
	 */
	public void removeFromCustomer(final SessionContext ctx, final Customer value)
	{
		CUSTOMERHANDLER.removeValue( ctx, this, value );
	}
	
	/**
	 * <i>Generated method</i> - Removes <code>value</code> from customer. 
	 * @param value the item to remove from customer
	 */
	public void removeFromCustomer(final Customer value)
	{
		removeFromCustomer( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>CustomerCreditAccount.status</code> attribute.
	 * @return the status
	 */
	public EnumerationValue getStatus(final SessionContext ctx)
	{
		return (EnumerationValue)getProperty( ctx, STATUS);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>CustomerCreditAccount.status</code> attribute.
	 * @return the status
	 */
	public EnumerationValue getStatus()
	{
		return getStatus( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>CustomerCreditAccount.status</code> attribute. 
	 * @param value the status
	 */
	public void setStatus(final SessionContext ctx, final EnumerationValue value)
	{
		setProperty(ctx, STATUS,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>CustomerCreditAccount.status</code> attribute. 
	 * @param value the status
	 */
	public void setStatus(final EnumerationValue value)
	{
		setStatus( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>CustomerCreditAccount.transactions</code> attribute.
	 * @return the transactions
	 */
	public List<CreditTransaction> getTransactions(final SessionContext ctx)
	{
		return (List<CreditTransaction>)TRANSACTIONSHANDLER.getValues( ctx, this );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>CustomerCreditAccount.transactions</code> attribute.
	 * @return the transactions
	 */
	public List<CreditTransaction> getTransactions()
	{
		return getTransactions( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>CustomerCreditAccount.transactions</code> attribute. 
	 * @param value the transactions
	 */
	public void setTransactions(final SessionContext ctx, final List<CreditTransaction> value)
	{
		TRANSACTIONSHANDLER.setValues( ctx, this, value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>CustomerCreditAccount.transactions</code> attribute. 
	 * @param value the transactions
	 */
	public void setTransactions(final List<CreditTransaction> value)
	{
		setTransactions( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Adds <code>value</code> to transactions. 
	 * @param value the item to add to transactions
	 */
	public void addToTransactions(final SessionContext ctx, final CreditTransaction value)
	{
		TRANSACTIONSHANDLER.addValue( ctx, this, value );
	}
	
	/**
	 * <i>Generated method</i> - Adds <code>value</code> to transactions. 
	 * @param value the item to add to transactions
	 */
	public void addToTransactions(final CreditTransaction value)
	{
		addToTransactions( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Removes <code>value</code> from transactions. 
	 * @param value the item to remove from transactions
	 */
	public void removeFromTransactions(final SessionContext ctx, final CreditTransaction value)
	{
		TRANSACTIONSHANDLER.removeValue( ctx, this, value );
	}
	
	/**
	 * <i>Generated method</i> - Removes <code>value</code> from transactions. 
	 * @param value the item to remove from transactions
	 */
	public void removeFromTransactions(final CreditTransaction value)
	{
		removeFromTransactions( getSession().getSessionContext(), value );
	}
	
}
