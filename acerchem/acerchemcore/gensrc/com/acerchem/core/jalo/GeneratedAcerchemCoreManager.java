/*
 * ----------------------------------------------------------------
 * --- WARNING: THIS FILE IS GENERATED AND WILL BE OVERWRITTEN! ---
 * --- Generated at 2018-4-13 11:18:26                          ---
 * ----------------------------------------------------------------
 */
package com.acerchem.core.jalo;

import com.acerchem.core.constants.AcerchemCoreConstants;
import com.acerchem.core.jalo.ApparelProduct;
import com.acerchem.core.jalo.ApparelSizeVariantProduct;
import com.acerchem.core.jalo.ApparelStyleVariantProduct;
import com.acerchem.core.jalo.CountryTrayFareConf;
import com.acerchem.core.jalo.CreditTransaction;
import com.acerchem.core.jalo.CustomerCreditAccount;
import com.acerchem.core.jalo.ElectronicsColorVariantProduct;
import com.acerchem.core.jalo.ImageFailedRecord;
import com.acerchem.core.jalo.ImageUploadedLog;
import com.acerchem.core.jalo.UserLevel;
import de.hybris.platform.acceleratorcms.jalo.components.AbstractResponsiveBannerComponent;
import de.hybris.platform.acceleratorcms.jalo.components.SimpleResponsiveBannerComponent;
import de.hybris.platform.cms2.jalo.contents.components.SimpleCMSComponent;
import de.hybris.platform.cms2lib.components.FunctionButtonComponent;
import de.hybris.platform.cms2lib.components.MultipleBannerComponent;
import de.hybris.platform.cms2lib.components.ProductCarouselComponent;
import de.hybris.platform.constants.CoreConstants;
import de.hybris.platform.deliveryzone.jalo.Zone;
import de.hybris.platform.jalo.GenericItem;
import de.hybris.platform.jalo.Item;
import de.hybris.platform.jalo.Item.AttributeMode;
import de.hybris.platform.jalo.JaloBusinessException;
import de.hybris.platform.jalo.JaloSystemException;
import de.hybris.platform.jalo.SessionContext;
import de.hybris.platform.jalo.extension.Extension;
import de.hybris.platform.jalo.media.AbstractMedia;
import de.hybris.platform.jalo.media.Media;
import de.hybris.platform.jalo.order.AbstractOrderEntry;
import de.hybris.platform.jalo.product.Product;
import de.hybris.platform.jalo.security.Principal;
import de.hybris.platform.jalo.type.CollectionType;
import de.hybris.platform.jalo.type.ComposedType;
import de.hybris.platform.jalo.type.JaloGenericCreationException;
import de.hybris.platform.jalo.user.Customer;
import de.hybris.platform.jalo.user.Employee;
import de.hybris.platform.jalo.user.User;
import de.hybris.platform.ordersplitting.jalo.StockLevel;
import de.hybris.platform.ordersplitting.jalo.Vendor;
import de.hybris.platform.storelocator.jalo.PointOfService;
import de.hybris.platform.ticket.jalo.CsTicket;
import de.hybris.platform.util.OneToManyHandler;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Generated class for type <code>AcerchemCoreManager</code>.
 */
@SuppressWarnings({"deprecation","unused","cast","PMD"})
public abstract class GeneratedAcerchemCoreManager extends Extension
{
	/**
	* {@link OneToManyHandler} for handling 1:n ACERCHEMPRODUCTS's relation attributes from 'many' side.
	**/
	protected static final OneToManyHandler<Product> ACERCHEMVENDOR2PRODUCTACERCHEMPRODUCTSHANDLER = new OneToManyHandler<Product>(
	CoreConstants.TC.PRODUCT,
	false,
	"acerChemVendor",
	"acerChemVendorPOS",
	true,
	true,
	CollectionType.LIST
	);
	/**
	* {@link OneToManyHandler} for handling 1:n RECEPTIONIST's relation attributes from 'many' side.
	**/
	protected static final OneToManyHandler<Customer> RECEPTIONIST2CUSTOMERRECEPTIONISTHANDLER = new OneToManyHandler<Customer>(
	CoreConstants.TC.CUSTOMER,
	false,
	"relatedCustomer",
	"relatedCustomerPOS",
	true,
	true,
	CollectionType.LIST
	);
	protected static final Map<String, Map<String, AttributeMode>> DEFAULT_INITIAL_ATTRIBUTES;
	static
	{
		final Map<String, Map<String, AttributeMode>> ttmp = new HashMap();
		Map<String, AttributeMode> tmp = new HashMap<String, AttributeMode>();
		tmp.put("productId", AttributeMode.INITIAL);
		tmp.put("productName", AttributeMode.INITIAL);
		tmp.put("yourname", AttributeMode.INITIAL);
		tmp.put("telephone", AttributeMode.INITIAL);
		tmp.put("Email", AttributeMode.INITIAL);
		tmp.put("address", AttributeMode.INITIAL);
		ttmp.put("de.hybris.platform.ticket.jalo.CsTicket", Collections.unmodifiableMap(tmp));
		tmp = new HashMap<String, AttributeMode>();
		tmp.put("aliyunUrl", AttributeMode.INITIAL);
		ttmp.put("de.hybris.platform.jalo.media.Media", Collections.unmodifiableMap(tmp));
		tmp = new HashMap<String, AttributeMode>();
		tmp.put("chemicalInfo", AttributeMode.INITIAL);
		tmp.put("unitCalculateRato", AttributeMode.INITIAL);
		tmp.put("anonymousDisplayPrice", AttributeMode.INITIAL);
		tmp.put("acerChemVendorPOS", AttributeMode.INITIAL);
		tmp.put("acerChemVendor", AttributeMode.INITIAL);
		ttmp.put("de.hybris.platform.jalo.product.Product", Collections.unmodifiableMap(tmp));
		tmp = new HashMap<String, AttributeMode>();
		tmp.put("isPromotion", AttributeMode.INITIAL);
		tmp.put("isWell", AttributeMode.INITIAL);
		tmp.put("isSale", AttributeMode.INITIAL);
		ttmp.put("de.hybris.platform.cms2lib.components.ProductCarouselComponent", Collections.unmodifiableMap(tmp));
		tmp = new HashMap<String, AttributeMode>();
		tmp.put("isFeature", AttributeMode.INITIAL);
		ttmp.put("de.hybris.platform.acceleratorcms.jalo.components.SimpleResponsiveBannerComponent", Collections.unmodifiableMap(tmp));
		tmp = new HashMap<String, AttributeMode>();
		tmp.put("preOrderReleaseDay", AttributeMode.INITIAL);
		tmp.put("avaPreOrderReleaseDay", AttributeMode.INITIAL);
		ttmp.put("de.hybris.platform.ordersplitting.jalo.StockLevel", Collections.unmodifiableMap(tmp));
		tmp = new HashMap<String, AttributeMode>();
		tmp.put("isUseFutureStock", AttributeMode.INITIAL);
		tmp.put("availableDate", AttributeMode.INITIAL);
		ttmp.put("de.hybris.platform.jalo.order.AbstractOrderEntry", Collections.unmodifiableMap(tmp));
		tmp = new HashMap<String, AttributeMode>();
		tmp.put("creditAccount", AttributeMode.INITIAL);
		tmp.put("companyType", AttributeMode.INITIAL);
		tmp.put("establishedIn", AttributeMode.INITIAL);
		tmp.put("revenue", AttributeMode.INITIAL);
		tmp.put("employeesNo", AttributeMode.INITIAL);
		tmp.put("limitCreditAmount", AttributeMode.INITIAL);
		tmp.put("vatNo", AttributeMode.INITIAL);
		tmp.put("haveFinancialReport", AttributeMode.INITIAL);
		tmp.put("provideTradeReference", AttributeMode.INITIAL);
		tmp.put("relatedCustomerPOS", AttributeMode.INITIAL);
		tmp.put("relatedCustomer", AttributeMode.INITIAL);
		ttmp.put("de.hybris.platform.jalo.user.Customer", Collections.unmodifiableMap(tmp));
		tmp = new HashMap<String, AttributeMode>();
		tmp.put("deliveryZone", AttributeMode.INITIAL);
		ttmp.put("de.hybris.platform.storelocator.jalo.PointOfService", Collections.unmodifiableMap(tmp));
		tmp = new HashMap<String, AttributeMode>();
		tmp.put("userLevelPOS", AttributeMode.INITIAL);
		tmp.put("userLevel", AttributeMode.INITIAL);
		ttmp.put("de.hybris.platform.jalo.user.User", Collections.unmodifiableMap(tmp));
		DEFAULT_INITIAL_ATTRIBUTES = ttmp;
	}
	@Override
	public Map<String, AttributeMode> getDefaultAttributeModes(final Class<? extends Item> itemClass)
	{
		Map<String, AttributeMode> ret = new HashMap<>();
		final Map<String, AttributeMode> attr = DEFAULT_INITIAL_ATTRIBUTES.get(itemClass.getName());
		if (attr != null)
		{
			ret.putAll(attr);
		}
		return ret;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Vendor.acerChemProducts</code> attribute.
	 * @return the acerChemProducts
	 */
	public List<Product> getAcerChemProducts(final SessionContext ctx, final Vendor item)
	{
		return (List<Product>)ACERCHEMVENDOR2PRODUCTACERCHEMPRODUCTSHANDLER.getValues( ctx, item );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Vendor.acerChemProducts</code> attribute.
	 * @return the acerChemProducts
	 */
	public List<Product> getAcerChemProducts(final Vendor item)
	{
		return getAcerChemProducts( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Vendor.acerChemProducts</code> attribute. 
	 * @param value the acerChemProducts
	 */
	public void setAcerChemProducts(final SessionContext ctx, final Vendor item, final List<Product> value)
	{
		ACERCHEMVENDOR2PRODUCTACERCHEMPRODUCTSHANDLER.setValues( ctx, item, value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Vendor.acerChemProducts</code> attribute. 
	 * @param value the acerChemProducts
	 */
	public void setAcerChemProducts(final Vendor item, final List<Product> value)
	{
		setAcerChemProducts( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Adds <code>value</code> to acerChemProducts. 
	 * @param value the item to add to acerChemProducts
	 */
	public void addToAcerChemProducts(final SessionContext ctx, final Vendor item, final Product value)
	{
		ACERCHEMVENDOR2PRODUCTACERCHEMPRODUCTSHANDLER.addValue( ctx, item, value );
	}
	
	/**
	 * <i>Generated method</i> - Adds <code>value</code> to acerChemProducts. 
	 * @param value the item to add to acerChemProducts
	 */
	public void addToAcerChemProducts(final Vendor item, final Product value)
	{
		addToAcerChemProducts( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Removes <code>value</code> from acerChemProducts. 
	 * @param value the item to remove from acerChemProducts
	 */
	public void removeFromAcerChemProducts(final SessionContext ctx, final Vendor item, final Product value)
	{
		ACERCHEMVENDOR2PRODUCTACERCHEMPRODUCTSHANDLER.removeValue( ctx, item, value );
	}
	
	/**
	 * <i>Generated method</i> - Removes <code>value</code> from acerChemProducts. 
	 * @param value the item to remove from acerChemProducts
	 */
	public void removeFromAcerChemProducts(final Vendor item, final Product value)
	{
		removeFromAcerChemProducts( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Product.acerChemVendor</code> attribute.
	 * @return the acerChemVendor
	 */
	public Vendor getAcerChemVendor(final SessionContext ctx, final Product item)
	{
		return (Vendor)item.getProperty( ctx, AcerchemCoreConstants.Attributes.Product.ACERCHEMVENDOR);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Product.acerChemVendor</code> attribute.
	 * @return the acerChemVendor
	 */
	public Vendor getAcerChemVendor(final Product item)
	{
		return getAcerChemVendor( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Product.acerChemVendor</code> attribute. 
	 * @param value the acerChemVendor
	 */
	public void setAcerChemVendor(final SessionContext ctx, final Product item, final Vendor value)
	{
		item.setProperty(ctx, AcerchemCoreConstants.Attributes.Product.ACERCHEMVENDOR,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Product.acerChemVendor</code> attribute. 
	 * @param value the acerChemVendor
	 */
	public void setAcerChemVendor(final Product item, final Vendor value)
	{
		setAcerChemVendor( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Product.acerChemVendorPOS</code> attribute.
	 * @return the acerChemVendorPOS
	 */
	 Integer getAcerChemVendorPOS(final SessionContext ctx, final Product item)
	{
		return (Integer)item.getProperty( ctx, AcerchemCoreConstants.Attributes.Product.ACERCHEMVENDORPOS);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Product.acerChemVendorPOS</code> attribute.
	 * @return the acerChemVendorPOS
	 */
	 Integer getAcerChemVendorPOS(final Product item)
	{
		return getAcerChemVendorPOS( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Product.acerChemVendorPOS</code> attribute. 
	 * @return the acerChemVendorPOS
	 */
	 int getAcerChemVendorPOSAsPrimitive(final SessionContext ctx, final Product item)
	{
		Integer value = getAcerChemVendorPOS( ctx,item );
		return value != null ? value.intValue() : 0;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Product.acerChemVendorPOS</code> attribute. 
	 * @return the acerChemVendorPOS
	 */
	 int getAcerChemVendorPOSAsPrimitive(final Product item)
	{
		return getAcerChemVendorPOSAsPrimitive( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Product.acerChemVendorPOS</code> attribute. 
	 * @param value the acerChemVendorPOS
	 */
	 void setAcerChemVendorPOS(final SessionContext ctx, final Product item, final Integer value)
	{
		item.setProperty(ctx, AcerchemCoreConstants.Attributes.Product.ACERCHEMVENDORPOS,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Product.acerChemVendorPOS</code> attribute. 
	 * @param value the acerChemVendorPOS
	 */
	 void setAcerChemVendorPOS(final Product item, final Integer value)
	{
		setAcerChemVendorPOS( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Product.acerChemVendorPOS</code> attribute. 
	 * @param value the acerChemVendorPOS
	 */
	 void setAcerChemVendorPOS(final SessionContext ctx, final Product item, final int value)
	{
		setAcerChemVendorPOS( ctx, item, Integer.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Product.acerChemVendorPOS</code> attribute. 
	 * @param value the acerChemVendorPOS
	 */
	 void setAcerChemVendorPOS(final Product item, final int value)
	{
		setAcerChemVendorPOS( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>CsTicket.address</code> attribute.
	 * @return the address
	 */
	public String getAddress(final SessionContext ctx, final CsTicket item)
	{
		return (String)item.getProperty( ctx, AcerchemCoreConstants.Attributes.CsTicket.ADDRESS);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>CsTicket.address</code> attribute.
	 * @return the address
	 */
	public String getAddress(final CsTicket item)
	{
		return getAddress( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>CsTicket.address</code> attribute. 
	 * @param value the address
	 */
	public void setAddress(final SessionContext ctx, final CsTicket item, final String value)
	{
		item.setProperty(ctx, AcerchemCoreConstants.Attributes.CsTicket.ADDRESS,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>CsTicket.address</code> attribute. 
	 * @param value the address
	 */
	public void setAddress(final CsTicket item, final String value)
	{
		setAddress( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Media.aliyunUrl</code> attribute.
	 * @return the aliyunUrl - 阿里云Url
	 */
	public String getAliyunUrl(final SessionContext ctx, final Media item)
	{
		return (String)item.getProperty( ctx, AcerchemCoreConstants.Attributes.Media.ALIYUNURL);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Media.aliyunUrl</code> attribute.
	 * @return the aliyunUrl - 阿里云Url
	 */
	public String getAliyunUrl(final Media item)
	{
		return getAliyunUrl( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Media.aliyunUrl</code> attribute. 
	 * @param value the aliyunUrl - 阿里云Url
	 */
	public void setAliyunUrl(final SessionContext ctx, final Media item, final String value)
	{
		item.setProperty(ctx, AcerchemCoreConstants.Attributes.Media.ALIYUNURL,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Media.aliyunUrl</code> attribute. 
	 * @param value the aliyunUrl - 阿里云Url
	 */
	public void setAliyunUrl(final Media item, final String value)
	{
		setAliyunUrl( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Product.anonymousDisplayPrice</code> attribute.
	 * @return the anonymousDisplayPrice - Anonymous whether to show the price
	 */
	public Boolean isAnonymousDisplayPrice(final SessionContext ctx, final Product item)
	{
		return (Boolean)item.getProperty( ctx, AcerchemCoreConstants.Attributes.Product.ANONYMOUSDISPLAYPRICE);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Product.anonymousDisplayPrice</code> attribute.
	 * @return the anonymousDisplayPrice - Anonymous whether to show the price
	 */
	public Boolean isAnonymousDisplayPrice(final Product item)
	{
		return isAnonymousDisplayPrice( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Product.anonymousDisplayPrice</code> attribute. 
	 * @return the anonymousDisplayPrice - Anonymous whether to show the price
	 */
	public boolean isAnonymousDisplayPriceAsPrimitive(final SessionContext ctx, final Product item)
	{
		Boolean value = isAnonymousDisplayPrice( ctx,item );
		return value != null ? value.booleanValue() : false;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Product.anonymousDisplayPrice</code> attribute. 
	 * @return the anonymousDisplayPrice - Anonymous whether to show the price
	 */
	public boolean isAnonymousDisplayPriceAsPrimitive(final Product item)
	{
		return isAnonymousDisplayPriceAsPrimitive( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Product.anonymousDisplayPrice</code> attribute. 
	 * @param value the anonymousDisplayPrice - Anonymous whether to show the price
	 */
	public void setAnonymousDisplayPrice(final SessionContext ctx, final Product item, final Boolean value)
	{
		item.setProperty(ctx, AcerchemCoreConstants.Attributes.Product.ANONYMOUSDISPLAYPRICE,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Product.anonymousDisplayPrice</code> attribute. 
	 * @param value the anonymousDisplayPrice - Anonymous whether to show the price
	 */
	public void setAnonymousDisplayPrice(final Product item, final Boolean value)
	{
		setAnonymousDisplayPrice( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Product.anonymousDisplayPrice</code> attribute. 
	 * @param value the anonymousDisplayPrice - Anonymous whether to show the price
	 */
	public void setAnonymousDisplayPrice(final SessionContext ctx, final Product item, final boolean value)
	{
		setAnonymousDisplayPrice( ctx, item, Boolean.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Product.anonymousDisplayPrice</code> attribute. 
	 * @param value the anonymousDisplayPrice - Anonymous whether to show the price
	 */
	public void setAnonymousDisplayPrice(final Product item, final boolean value)
	{
		setAnonymousDisplayPrice( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AbstractOrderEntry.availableDate</code> attribute.
	 * @return the availableDate
	 */
	public Date getAvailableDate(final SessionContext ctx, final AbstractOrderEntry item)
	{
		return (Date)item.getProperty( ctx, AcerchemCoreConstants.Attributes.AbstractOrderEntry.AVAILABLEDATE);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AbstractOrderEntry.availableDate</code> attribute.
	 * @return the availableDate
	 */
	public Date getAvailableDate(final AbstractOrderEntry item)
	{
		return getAvailableDate( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AbstractOrderEntry.availableDate</code> attribute. 
	 * @param value the availableDate
	 */
	public void setAvailableDate(final SessionContext ctx, final AbstractOrderEntry item, final Date value)
	{
		item.setProperty(ctx, AcerchemCoreConstants.Attributes.AbstractOrderEntry.AVAILABLEDATE,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AbstractOrderEntry.availableDate</code> attribute. 
	 * @param value the availableDate
	 */
	public void setAvailableDate(final AbstractOrderEntry item, final Date value)
	{
		setAvailableDate( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>StockLevel.avaPreOrderReleaseDay</code> attribute.
	 * @return the avaPreOrderReleaseDay
	 */
	public Integer getAvaPreOrderReleaseDay(final SessionContext ctx, final StockLevel item)
	{
		return (Integer)item.getProperty( ctx, AcerchemCoreConstants.Attributes.StockLevel.AVAPREORDERRELEASEDAY);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>StockLevel.avaPreOrderReleaseDay</code> attribute.
	 * @return the avaPreOrderReleaseDay
	 */
	public Integer getAvaPreOrderReleaseDay(final StockLevel item)
	{
		return getAvaPreOrderReleaseDay( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>StockLevel.avaPreOrderReleaseDay</code> attribute. 
	 * @return the avaPreOrderReleaseDay
	 */
	public int getAvaPreOrderReleaseDayAsPrimitive(final SessionContext ctx, final StockLevel item)
	{
		Integer value = getAvaPreOrderReleaseDay( ctx,item );
		return value != null ? value.intValue() : 0;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>StockLevel.avaPreOrderReleaseDay</code> attribute. 
	 * @return the avaPreOrderReleaseDay
	 */
	public int getAvaPreOrderReleaseDayAsPrimitive(final StockLevel item)
	{
		return getAvaPreOrderReleaseDayAsPrimitive( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>StockLevel.avaPreOrderReleaseDay</code> attribute. 
	 * @param value the avaPreOrderReleaseDay
	 */
	public void setAvaPreOrderReleaseDay(final SessionContext ctx, final StockLevel item, final Integer value)
	{
		item.setProperty(ctx, AcerchemCoreConstants.Attributes.StockLevel.AVAPREORDERRELEASEDAY,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>StockLevel.avaPreOrderReleaseDay</code> attribute. 
	 * @param value the avaPreOrderReleaseDay
	 */
	public void setAvaPreOrderReleaseDay(final StockLevel item, final Integer value)
	{
		setAvaPreOrderReleaseDay( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>StockLevel.avaPreOrderReleaseDay</code> attribute. 
	 * @param value the avaPreOrderReleaseDay
	 */
	public void setAvaPreOrderReleaseDay(final SessionContext ctx, final StockLevel item, final int value)
	{
		setAvaPreOrderReleaseDay( ctx, item, Integer.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>StockLevel.avaPreOrderReleaseDay</code> attribute. 
	 * @param value the avaPreOrderReleaseDay
	 */
	public void setAvaPreOrderReleaseDay(final StockLevel item, final int value)
	{
		setAvaPreOrderReleaseDay( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Product.chemicalInfo</code> attribute.
	 * @return the chemicalInfo - Attribute about ç¾hemicalInfo of Product
	 */
	public String getChemicalInfo(final SessionContext ctx, final Product item)
	{
		return (String)item.getProperty( ctx, AcerchemCoreConstants.Attributes.Product.CHEMICALINFO);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Product.chemicalInfo</code> attribute.
	 * @return the chemicalInfo - Attribute about ç¾hemicalInfo of Product
	 */
	public String getChemicalInfo(final Product item)
	{
		return getChemicalInfo( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Product.chemicalInfo</code> attribute. 
	 * @param value the chemicalInfo - Attribute about ç¾hemicalInfo of Product
	 */
	public void setChemicalInfo(final SessionContext ctx, final Product item, final String value)
	{
		item.setProperty(ctx, AcerchemCoreConstants.Attributes.Product.CHEMICALINFO,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Product.chemicalInfo</code> attribute. 
	 * @param value the chemicalInfo - Attribute about ç¾hemicalInfo of Product
	 */
	public void setChemicalInfo(final Product item, final String value)
	{
		setChemicalInfo( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Customer.companyType</code> attribute.
	 * @return the companyType - Type of Company
	 */
	public String getCompanyType(final SessionContext ctx, final Customer item)
	{
		return (String)item.getProperty( ctx, AcerchemCoreConstants.Attributes.Customer.COMPANYTYPE);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Customer.companyType</code> attribute.
	 * @return the companyType - Type of Company
	 */
	public String getCompanyType(final Customer item)
	{
		return getCompanyType( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Customer.companyType</code> attribute. 
	 * @param value the companyType - Type of Company
	 */
	public void setCompanyType(final SessionContext ctx, final Customer item, final String value)
	{
		item.setProperty(ctx, AcerchemCoreConstants.Attributes.Customer.COMPANYTYPE,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Customer.companyType</code> attribute. 
	 * @param value the companyType - Type of Company
	 */
	public void setCompanyType(final Customer item, final String value)
	{
		setCompanyType( getSession().getSessionContext(), item, value );
	}
	
	public ApparelProduct createApparelProduct(final SessionContext ctx, final Map attributeValues)
	{
		try
		{
			ComposedType type = getTenant().getJaloConnection().getTypeManager().getComposedType( AcerchemCoreConstants.TC.APPARELPRODUCT );
			return (ApparelProduct)type.newInstance( ctx, attributeValues );
		}
		catch( JaloGenericCreationException e)
		{
			final Throwable cause = e.getCause();
			throw (cause instanceof RuntimeException ?
			(RuntimeException)cause
			:
			new JaloSystemException( cause, cause.getMessage(), e.getErrorCode() ) );
		}
		catch( JaloBusinessException e )
		{
			throw new JaloSystemException( e ,"error creating ApparelProduct : "+e.getMessage(), 0 );
		}
	}
	
	public ApparelProduct createApparelProduct(final Map attributeValues)
	{
		return createApparelProduct( getSession().getSessionContext(), attributeValues );
	}
	
	public ApparelSizeVariantProduct createApparelSizeVariantProduct(final SessionContext ctx, final Map attributeValues)
	{
		try
		{
			ComposedType type = getTenant().getJaloConnection().getTypeManager().getComposedType( AcerchemCoreConstants.TC.APPARELSIZEVARIANTPRODUCT );
			return (ApparelSizeVariantProduct)type.newInstance( ctx, attributeValues );
		}
		catch( JaloGenericCreationException e)
		{
			final Throwable cause = e.getCause();
			throw (cause instanceof RuntimeException ?
			(RuntimeException)cause
			:
			new JaloSystemException( cause, cause.getMessage(), e.getErrorCode() ) );
		}
		catch( JaloBusinessException e )
		{
			throw new JaloSystemException( e ,"error creating ApparelSizeVariantProduct : "+e.getMessage(), 0 );
		}
	}
	
	public ApparelSizeVariantProduct createApparelSizeVariantProduct(final Map attributeValues)
	{
		return createApparelSizeVariantProduct( getSession().getSessionContext(), attributeValues );
	}
	
	public ApparelStyleVariantProduct createApparelStyleVariantProduct(final SessionContext ctx, final Map attributeValues)
	{
		try
		{
			ComposedType type = getTenant().getJaloConnection().getTypeManager().getComposedType( AcerchemCoreConstants.TC.APPARELSTYLEVARIANTPRODUCT );
			return (ApparelStyleVariantProduct)type.newInstance( ctx, attributeValues );
		}
		catch( JaloGenericCreationException e)
		{
			final Throwable cause = e.getCause();
			throw (cause instanceof RuntimeException ?
			(RuntimeException)cause
			:
			new JaloSystemException( cause, cause.getMessage(), e.getErrorCode() ) );
		}
		catch( JaloBusinessException e )
		{
			throw new JaloSystemException( e ,"error creating ApparelStyleVariantProduct : "+e.getMessage(), 0 );
		}
	}
	
	public ApparelStyleVariantProduct createApparelStyleVariantProduct(final Map attributeValues)
	{
		return createApparelStyleVariantProduct( getSession().getSessionContext(), attributeValues );
	}
	
	public CountryTrayFareConf createCountryTrayFareConf(final SessionContext ctx, final Map attributeValues)
	{
		try
		{
			ComposedType type = getTenant().getJaloConnection().getTypeManager().getComposedType( AcerchemCoreConstants.TC.COUNTRYTRAYFARECONF );
			return (CountryTrayFareConf)type.newInstance( ctx, attributeValues );
		}
		catch( JaloGenericCreationException e)
		{
			final Throwable cause = e.getCause();
			throw (cause instanceof RuntimeException ?
			(RuntimeException)cause
			:
			new JaloSystemException( cause, cause.getMessage(), e.getErrorCode() ) );
		}
		catch( JaloBusinessException e )
		{
			throw new JaloSystemException( e ,"error creating CountryTrayFareConf : "+e.getMessage(), 0 );
		}
	}
	
	public CountryTrayFareConf createCountryTrayFareConf(final Map attributeValues)
	{
		return createCountryTrayFareConf( getSession().getSessionContext(), attributeValues );
	}
	
	public CreditTransaction createCreditTransaction(final SessionContext ctx, final Map attributeValues)
	{
		try
		{
			ComposedType type = getTenant().getJaloConnection().getTypeManager().getComposedType( AcerchemCoreConstants.TC.CREDITTRANSACTION );
			return (CreditTransaction)type.newInstance( ctx, attributeValues );
		}
		catch( JaloGenericCreationException e)
		{
			final Throwable cause = e.getCause();
			throw (cause instanceof RuntimeException ?
			(RuntimeException)cause
			:
			new JaloSystemException( cause, cause.getMessage(), e.getErrorCode() ) );
		}
		catch( JaloBusinessException e )
		{
			throw new JaloSystemException( e ,"error creating CreditTransaction : "+e.getMessage(), 0 );
		}
	}
	
	public CreditTransaction createCreditTransaction(final Map attributeValues)
	{
		return createCreditTransaction( getSession().getSessionContext(), attributeValues );
	}
	
	public CustomerCreditAccount createCustomerCreditAccount(final SessionContext ctx, final Map attributeValues)
	{
		try
		{
			ComposedType type = getTenant().getJaloConnection().getTypeManager().getComposedType( AcerchemCoreConstants.TC.CUSTOMERCREDITACCOUNT );
			return (CustomerCreditAccount)type.newInstance( ctx, attributeValues );
		}
		catch( JaloGenericCreationException e)
		{
			final Throwable cause = e.getCause();
			throw (cause instanceof RuntimeException ?
			(RuntimeException)cause
			:
			new JaloSystemException( cause, cause.getMessage(), e.getErrorCode() ) );
		}
		catch( JaloBusinessException e )
		{
			throw new JaloSystemException( e ,"error creating CustomerCreditAccount : "+e.getMessage(), 0 );
		}
	}
	
	public CustomerCreditAccount createCustomerCreditAccount(final Map attributeValues)
	{
		return createCustomerCreditAccount( getSession().getSessionContext(), attributeValues );
	}
	
	public ElectronicsColorVariantProduct createElectronicsColorVariantProduct(final SessionContext ctx, final Map attributeValues)
	{
		try
		{
			ComposedType type = getTenant().getJaloConnection().getTypeManager().getComposedType( AcerchemCoreConstants.TC.ELECTRONICSCOLORVARIANTPRODUCT );
			return (ElectronicsColorVariantProduct)type.newInstance( ctx, attributeValues );
		}
		catch( JaloGenericCreationException e)
		{
			final Throwable cause = e.getCause();
			throw (cause instanceof RuntimeException ?
			(RuntimeException)cause
			:
			new JaloSystemException( cause, cause.getMessage(), e.getErrorCode() ) );
		}
		catch( JaloBusinessException e )
		{
			throw new JaloSystemException( e ,"error creating ElectronicsColorVariantProduct : "+e.getMessage(), 0 );
		}
	}
	
	public ElectronicsColorVariantProduct createElectronicsColorVariantProduct(final Map attributeValues)
	{
		return createElectronicsColorVariantProduct( getSession().getSessionContext(), attributeValues );
	}
	
	public FunctionButtonComponent createFunctionButtonComponent(final SessionContext ctx, final Map attributeValues)
	{
		try
		{
			ComposedType type = getTenant().getJaloConnection().getTypeManager().getComposedType( AcerchemCoreConstants.TC.FUNCTIONBUTTONCOMPONENT );
			return (FunctionButtonComponent)type.newInstance( ctx, attributeValues );
		}
		catch( JaloGenericCreationException e)
		{
			final Throwable cause = e.getCause();
			throw (cause instanceof RuntimeException ?
			(RuntimeException)cause
			:
			new JaloSystemException( cause, cause.getMessage(), e.getErrorCode() ) );
		}
		catch( JaloBusinessException e )
		{
			throw new JaloSystemException( e ,"error creating FunctionButtonComponent : "+e.getMessage(), 0 );
		}
	}
	
	public FunctionButtonComponent createFunctionButtonComponent(final Map attributeValues)
	{
		return createFunctionButtonComponent( getSession().getSessionContext(), attributeValues );
	}
	
	public ImageFailedRecord createImageFailedRecord(final SessionContext ctx, final Map attributeValues)
	{
		try
		{
			ComposedType type = getTenant().getJaloConnection().getTypeManager().getComposedType( AcerchemCoreConstants.TC.IMAGEFAILEDRECORD );
			return (ImageFailedRecord)type.newInstance( ctx, attributeValues );
		}
		catch( JaloGenericCreationException e)
		{
			final Throwable cause = e.getCause();
			throw (cause instanceof RuntimeException ?
			(RuntimeException)cause
			:
			new JaloSystemException( cause, cause.getMessage(), e.getErrorCode() ) );
		}
		catch( JaloBusinessException e )
		{
			throw new JaloSystemException( e ,"error creating ImageFailedRecord : "+e.getMessage(), 0 );
		}
	}
	
	public ImageFailedRecord createImageFailedRecord(final Map attributeValues)
	{
		return createImageFailedRecord( getSession().getSessionContext(), attributeValues );
	}
	
	public ImageUploadedLog createImageUploadedLog(final SessionContext ctx, final Map attributeValues)
	{
		try
		{
			ComposedType type = getTenant().getJaloConnection().getTypeManager().getComposedType( AcerchemCoreConstants.TC.IMAGEUPLOADEDLOG );
			return (ImageUploadedLog)type.newInstance( ctx, attributeValues );
		}
		catch( JaloGenericCreationException e)
		{
			final Throwable cause = e.getCause();
			throw (cause instanceof RuntimeException ?
			(RuntimeException)cause
			:
			new JaloSystemException( cause, cause.getMessage(), e.getErrorCode() ) );
		}
		catch( JaloBusinessException e )
		{
			throw new JaloSystemException( e ,"error creating ImageUploadedLog : "+e.getMessage(), 0 );
		}
	}
	
	public ImageUploadedLog createImageUploadedLog(final Map attributeValues)
	{
		return createImageUploadedLog( getSession().getSessionContext(), attributeValues );
	}
	
	public MultipleBannerComponent createMultipleBannerComponent(final SessionContext ctx, final Map attributeValues)
	{
		try
		{
			ComposedType type = getTenant().getJaloConnection().getTypeManager().getComposedType( AcerchemCoreConstants.TC.MULTIPLEBANNERCOMPONENT );
			return (MultipleBannerComponent)type.newInstance( ctx, attributeValues );
		}
		catch( JaloGenericCreationException e)
		{
			final Throwable cause = e.getCause();
			throw (cause instanceof RuntimeException ?
			(RuntimeException)cause
			:
			new JaloSystemException( cause, cause.getMessage(), e.getErrorCode() ) );
		}
		catch( JaloBusinessException e )
		{
			throw new JaloSystemException( e ,"error creating MultipleBannerComponent : "+e.getMessage(), 0 );
		}
	}
	
	public MultipleBannerComponent createMultipleBannerComponent(final Map attributeValues)
	{
		return createMultipleBannerComponent( getSession().getSessionContext(), attributeValues );
	}
	
	public UserLevel createUserLevel(final SessionContext ctx, final Map attributeValues)
	{
		try
		{
			ComposedType type = getTenant().getJaloConnection().getTypeManager().getComposedType( AcerchemCoreConstants.TC.USERLEVEL );
			return (UserLevel)type.newInstance( ctx, attributeValues );
		}
		catch( JaloGenericCreationException e)
		{
			final Throwable cause = e.getCause();
			throw (cause instanceof RuntimeException ?
			(RuntimeException)cause
			:
			new JaloSystemException( cause, cause.getMessage(), e.getErrorCode() ) );
		}
		catch( JaloBusinessException e )
		{
			throw new JaloSystemException( e ,"error creating UserLevel : "+e.getMessage(), 0 );
		}
	}
	
	public UserLevel createUserLevel(final Map attributeValues)
	{
		return createUserLevel( getSession().getSessionContext(), attributeValues );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Customer.creditAccount</code> attribute.
	 * @return the creditAccount
	 */
	public CustomerCreditAccount getCreditAccount(final SessionContext ctx, final Customer item)
	{
		return (CustomerCreditAccount)item.getProperty( ctx, AcerchemCoreConstants.Attributes.Customer.CREDITACCOUNT);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Customer.creditAccount</code> attribute.
	 * @return the creditAccount
	 */
	public CustomerCreditAccount getCreditAccount(final Customer item)
	{
		return getCreditAccount( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Customer.creditAccount</code> attribute. 
	 * @param value the creditAccount
	 */
	public void setCreditAccount(final SessionContext ctx, final Customer item, final CustomerCreditAccount value)
	{
		item.setProperty(ctx, AcerchemCoreConstants.Attributes.Customer.CREDITACCOUNT,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Customer.creditAccount</code> attribute. 
	 * @param value the creditAccount
	 */
	public void setCreditAccount(final Customer item, final CustomerCreditAccount value)
	{
		setCreditAccount( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>PointOfService.deliveryZone</code> attribute.
	 * @return the deliveryZone - 提货点配送范围
	 */
	public Zone getDeliveryZone(final SessionContext ctx, final PointOfService item)
	{
		return (Zone)item.getProperty( ctx, AcerchemCoreConstants.Attributes.PointOfService.DELIVERYZONE);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>PointOfService.deliveryZone</code> attribute.
	 * @return the deliveryZone - 提货点配送范围
	 */
	public Zone getDeliveryZone(final PointOfService item)
	{
		return getDeliveryZone( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>PointOfService.deliveryZone</code> attribute. 
	 * @param value the deliveryZone - 提货点配送范围
	 */
	public void setDeliveryZone(final SessionContext ctx, final PointOfService item, final Zone value)
	{
		item.setProperty(ctx, AcerchemCoreConstants.Attributes.PointOfService.DELIVERYZONE,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>PointOfService.deliveryZone</code> attribute. 
	 * @param value the deliveryZone - 提货点配送范围
	 */
	public void setDeliveryZone(final PointOfService item, final Zone value)
	{
		setDeliveryZone( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>CsTicket.Email</code> attribute.
	 * @return the Email
	 */
	public String getEmail(final SessionContext ctx, final CsTicket item)
	{
		return (String)item.getProperty( ctx, AcerchemCoreConstants.Attributes.CsTicket.EMAIL);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>CsTicket.Email</code> attribute.
	 * @return the Email
	 */
	public String getEmail(final CsTicket item)
	{
		return getEmail( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>CsTicket.Email</code> attribute. 
	 * @param value the Email
	 */
	public void setEmail(final SessionContext ctx, final CsTicket item, final String value)
	{
		item.setProperty(ctx, AcerchemCoreConstants.Attributes.CsTicket.EMAIL,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>CsTicket.Email</code> attribute. 
	 * @param value the Email
	 */
	public void setEmail(final CsTicket item, final String value)
	{
		setEmail( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Customer.employeesNo</code> attribute.
	 * @return the employeesNo - No. of Employees
	 */
	public Integer getEmployeesNo(final SessionContext ctx, final Customer item)
	{
		return (Integer)item.getProperty( ctx, AcerchemCoreConstants.Attributes.Customer.EMPLOYEESNO);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Customer.employeesNo</code> attribute.
	 * @return the employeesNo - No. of Employees
	 */
	public Integer getEmployeesNo(final Customer item)
	{
		return getEmployeesNo( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Customer.employeesNo</code> attribute. 
	 * @return the employeesNo - No. of Employees
	 */
	public int getEmployeesNoAsPrimitive(final SessionContext ctx, final Customer item)
	{
		Integer value = getEmployeesNo( ctx,item );
		return value != null ? value.intValue() : 0;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Customer.employeesNo</code> attribute. 
	 * @return the employeesNo - No. of Employees
	 */
	public int getEmployeesNoAsPrimitive(final Customer item)
	{
		return getEmployeesNoAsPrimitive( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Customer.employeesNo</code> attribute. 
	 * @param value the employeesNo - No. of Employees
	 */
	public void setEmployeesNo(final SessionContext ctx, final Customer item, final Integer value)
	{
		item.setProperty(ctx, AcerchemCoreConstants.Attributes.Customer.EMPLOYEESNO,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Customer.employeesNo</code> attribute. 
	 * @param value the employeesNo - No. of Employees
	 */
	public void setEmployeesNo(final Customer item, final Integer value)
	{
		setEmployeesNo( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Customer.employeesNo</code> attribute. 
	 * @param value the employeesNo - No. of Employees
	 */
	public void setEmployeesNo(final SessionContext ctx, final Customer item, final int value)
	{
		setEmployeesNo( ctx, item, Integer.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Customer.employeesNo</code> attribute. 
	 * @param value the employeesNo - No. of Employees
	 */
	public void setEmployeesNo(final Customer item, final int value)
	{
		setEmployeesNo( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Customer.establishedIn</code> attribute.
	 * @return the establishedIn - Established in
	 */
	public String getEstablishedIn(final SessionContext ctx, final Customer item)
	{
		return (String)item.getProperty( ctx, AcerchemCoreConstants.Attributes.Customer.ESTABLISHEDIN);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Customer.establishedIn</code> attribute.
	 * @return the establishedIn - Established in
	 */
	public String getEstablishedIn(final Customer item)
	{
		return getEstablishedIn( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Customer.establishedIn</code> attribute. 
	 * @param value the establishedIn - Established in
	 */
	public void setEstablishedIn(final SessionContext ctx, final Customer item, final String value)
	{
		item.setProperty(ctx, AcerchemCoreConstants.Attributes.Customer.ESTABLISHEDIN,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Customer.establishedIn</code> attribute. 
	 * @param value the establishedIn - Established in
	 */
	public void setEstablishedIn(final Customer item, final String value)
	{
		setEstablishedIn( getSession().getSessionContext(), item, value );
	}
	
	@Override
	public String getName()
	{
		return AcerchemCoreConstants.EXTENSIONNAME;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Customer.haveFinancialReport</code> attribute.
	 * @return the haveFinancialReport - Do you have public financial report?If so, please send to customerservice@acerchem.com
	 */
	public Boolean isHaveFinancialReport(final SessionContext ctx, final Customer item)
	{
		return (Boolean)item.getProperty( ctx, AcerchemCoreConstants.Attributes.Customer.HAVEFINANCIALREPORT);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Customer.haveFinancialReport</code> attribute.
	 * @return the haveFinancialReport - Do you have public financial report?If so, please send to customerservice@acerchem.com
	 */
	public Boolean isHaveFinancialReport(final Customer item)
	{
		return isHaveFinancialReport( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Customer.haveFinancialReport</code> attribute. 
	 * @return the haveFinancialReport - Do you have public financial report?If so, please send to customerservice@acerchem.com
	 */
	public boolean isHaveFinancialReportAsPrimitive(final SessionContext ctx, final Customer item)
	{
		Boolean value = isHaveFinancialReport( ctx,item );
		return value != null ? value.booleanValue() : false;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Customer.haveFinancialReport</code> attribute. 
	 * @return the haveFinancialReport - Do you have public financial report?If so, please send to customerservice@acerchem.com
	 */
	public boolean isHaveFinancialReportAsPrimitive(final Customer item)
	{
		return isHaveFinancialReportAsPrimitive( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Customer.haveFinancialReport</code> attribute. 
	 * @param value the haveFinancialReport - Do you have public financial report?If so, please send to customerservice@acerchem.com
	 */
	public void setHaveFinancialReport(final SessionContext ctx, final Customer item, final Boolean value)
	{
		item.setProperty(ctx, AcerchemCoreConstants.Attributes.Customer.HAVEFINANCIALREPORT,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Customer.haveFinancialReport</code> attribute. 
	 * @param value the haveFinancialReport - Do you have public financial report?If so, please send to customerservice@acerchem.com
	 */
	public void setHaveFinancialReport(final Customer item, final Boolean value)
	{
		setHaveFinancialReport( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Customer.haveFinancialReport</code> attribute. 
	 * @param value the haveFinancialReport - Do you have public financial report?If so, please send to customerservice@acerchem.com
	 */
	public void setHaveFinancialReport(final SessionContext ctx, final Customer item, final boolean value)
	{
		setHaveFinancialReport( ctx, item, Boolean.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Customer.haveFinancialReport</code> attribute. 
	 * @param value the haveFinancialReport - Do you have public financial report?If so, please send to customerservice@acerchem.com
	 */
	public void setHaveFinancialReport(final Customer item, final boolean value)
	{
		setHaveFinancialReport( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SimpleResponsiveBannerComponent.isFeature</code> attribute.
	 * @return the isFeature - If show the feature page
	 */
	public Boolean isIsFeature(final SessionContext ctx, final SimpleResponsiveBannerComponent item)
	{
		return (Boolean)item.getProperty( ctx, AcerchemCoreConstants.Attributes.SimpleResponsiveBannerComponent.ISFEATURE);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SimpleResponsiveBannerComponent.isFeature</code> attribute.
	 * @return the isFeature - If show the feature page
	 */
	public Boolean isIsFeature(final SimpleResponsiveBannerComponent item)
	{
		return isIsFeature( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SimpleResponsiveBannerComponent.isFeature</code> attribute. 
	 * @return the isFeature - If show the feature page
	 */
	public boolean isIsFeatureAsPrimitive(final SessionContext ctx, final SimpleResponsiveBannerComponent item)
	{
		Boolean value = isIsFeature( ctx,item );
		return value != null ? value.booleanValue() : false;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SimpleResponsiveBannerComponent.isFeature</code> attribute. 
	 * @return the isFeature - If show the feature page
	 */
	public boolean isIsFeatureAsPrimitive(final SimpleResponsiveBannerComponent item)
	{
		return isIsFeatureAsPrimitive( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SimpleResponsiveBannerComponent.isFeature</code> attribute. 
	 * @param value the isFeature - If show the feature page
	 */
	public void setIsFeature(final SessionContext ctx, final SimpleResponsiveBannerComponent item, final Boolean value)
	{
		item.setProperty(ctx, AcerchemCoreConstants.Attributes.SimpleResponsiveBannerComponent.ISFEATURE,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SimpleResponsiveBannerComponent.isFeature</code> attribute. 
	 * @param value the isFeature - If show the feature page
	 */
	public void setIsFeature(final SimpleResponsiveBannerComponent item, final Boolean value)
	{
		setIsFeature( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SimpleResponsiveBannerComponent.isFeature</code> attribute. 
	 * @param value the isFeature - If show the feature page
	 */
	public void setIsFeature(final SessionContext ctx, final SimpleResponsiveBannerComponent item, final boolean value)
	{
		setIsFeature( ctx, item, Boolean.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SimpleResponsiveBannerComponent.isFeature</code> attribute. 
	 * @param value the isFeature - If show the feature page
	 */
	public void setIsFeature(final SimpleResponsiveBannerComponent item, final boolean value)
	{
		setIsFeature( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ProductCarouselComponent.isPromotion</code> attribute.
	 * @return the isPromotion - If show the promotion page
	 */
	public Boolean isIsPromotion(final SessionContext ctx, final ProductCarouselComponent item)
	{
		return (Boolean)item.getProperty( ctx, AcerchemCoreConstants.Attributes.ProductCarouselComponent.ISPROMOTION);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ProductCarouselComponent.isPromotion</code> attribute.
	 * @return the isPromotion - If show the promotion page
	 */
	public Boolean isIsPromotion(final ProductCarouselComponent item)
	{
		return isIsPromotion( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ProductCarouselComponent.isPromotion</code> attribute. 
	 * @return the isPromotion - If show the promotion page
	 */
	public boolean isIsPromotionAsPrimitive(final SessionContext ctx, final ProductCarouselComponent item)
	{
		Boolean value = isIsPromotion( ctx,item );
		return value != null ? value.booleanValue() : false;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ProductCarouselComponent.isPromotion</code> attribute. 
	 * @return the isPromotion - If show the promotion page
	 */
	public boolean isIsPromotionAsPrimitive(final ProductCarouselComponent item)
	{
		return isIsPromotionAsPrimitive( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ProductCarouselComponent.isPromotion</code> attribute. 
	 * @param value the isPromotion - If show the promotion page
	 */
	public void setIsPromotion(final SessionContext ctx, final ProductCarouselComponent item, final Boolean value)
	{
		item.setProperty(ctx, AcerchemCoreConstants.Attributes.ProductCarouselComponent.ISPROMOTION,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ProductCarouselComponent.isPromotion</code> attribute. 
	 * @param value the isPromotion - If show the promotion page
	 */
	public void setIsPromotion(final ProductCarouselComponent item, final Boolean value)
	{
		setIsPromotion( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ProductCarouselComponent.isPromotion</code> attribute. 
	 * @param value the isPromotion - If show the promotion page
	 */
	public void setIsPromotion(final SessionContext ctx, final ProductCarouselComponent item, final boolean value)
	{
		setIsPromotion( ctx, item, Boolean.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ProductCarouselComponent.isPromotion</code> attribute. 
	 * @param value the isPromotion - If show the promotion page
	 */
	public void setIsPromotion(final ProductCarouselComponent item, final boolean value)
	{
		setIsPromotion( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ProductCarouselComponent.isSale</code> attribute.
	 * @return the isSale - If show the sale page
	 */
	public Boolean isIsSale(final SessionContext ctx, final ProductCarouselComponent item)
	{
		return (Boolean)item.getProperty( ctx, AcerchemCoreConstants.Attributes.ProductCarouselComponent.ISSALE);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ProductCarouselComponent.isSale</code> attribute.
	 * @return the isSale - If show the sale page
	 */
	public Boolean isIsSale(final ProductCarouselComponent item)
	{
		return isIsSale( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ProductCarouselComponent.isSale</code> attribute. 
	 * @return the isSale - If show the sale page
	 */
	public boolean isIsSaleAsPrimitive(final SessionContext ctx, final ProductCarouselComponent item)
	{
		Boolean value = isIsSale( ctx,item );
		return value != null ? value.booleanValue() : false;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ProductCarouselComponent.isSale</code> attribute. 
	 * @return the isSale - If show the sale page
	 */
	public boolean isIsSaleAsPrimitive(final ProductCarouselComponent item)
	{
		return isIsSaleAsPrimitive( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ProductCarouselComponent.isSale</code> attribute. 
	 * @param value the isSale - If show the sale page
	 */
	public void setIsSale(final SessionContext ctx, final ProductCarouselComponent item, final Boolean value)
	{
		item.setProperty(ctx, AcerchemCoreConstants.Attributes.ProductCarouselComponent.ISSALE,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ProductCarouselComponent.isSale</code> attribute. 
	 * @param value the isSale - If show the sale page
	 */
	public void setIsSale(final ProductCarouselComponent item, final Boolean value)
	{
		setIsSale( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ProductCarouselComponent.isSale</code> attribute. 
	 * @param value the isSale - If show the sale page
	 */
	public void setIsSale(final SessionContext ctx, final ProductCarouselComponent item, final boolean value)
	{
		setIsSale( ctx, item, Boolean.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ProductCarouselComponent.isSale</code> attribute. 
	 * @param value the isSale - If show the sale page
	 */
	public void setIsSale(final ProductCarouselComponent item, final boolean value)
	{
		setIsSale( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AbstractOrderEntry.isUseFutureStock</code> attribute.
	 * @return the isUseFutureStock - flag for futureStock
	 */
	public Boolean isIsUseFutureStock(final SessionContext ctx, final AbstractOrderEntry item)
	{
		return (Boolean)item.getProperty( ctx, AcerchemCoreConstants.Attributes.AbstractOrderEntry.ISUSEFUTURESTOCK);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AbstractOrderEntry.isUseFutureStock</code> attribute.
	 * @return the isUseFutureStock - flag for futureStock
	 */
	public Boolean isIsUseFutureStock(final AbstractOrderEntry item)
	{
		return isIsUseFutureStock( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AbstractOrderEntry.isUseFutureStock</code> attribute. 
	 * @return the isUseFutureStock - flag for futureStock
	 */
	public boolean isIsUseFutureStockAsPrimitive(final SessionContext ctx, final AbstractOrderEntry item)
	{
		Boolean value = isIsUseFutureStock( ctx,item );
		return value != null ? value.booleanValue() : false;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AbstractOrderEntry.isUseFutureStock</code> attribute. 
	 * @return the isUseFutureStock - flag for futureStock
	 */
	public boolean isIsUseFutureStockAsPrimitive(final AbstractOrderEntry item)
	{
		return isIsUseFutureStockAsPrimitive( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AbstractOrderEntry.isUseFutureStock</code> attribute. 
	 * @param value the isUseFutureStock - flag for futureStock
	 */
	public void setIsUseFutureStock(final SessionContext ctx, final AbstractOrderEntry item, final Boolean value)
	{
		item.setProperty(ctx, AcerchemCoreConstants.Attributes.AbstractOrderEntry.ISUSEFUTURESTOCK,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AbstractOrderEntry.isUseFutureStock</code> attribute. 
	 * @param value the isUseFutureStock - flag for futureStock
	 */
	public void setIsUseFutureStock(final AbstractOrderEntry item, final Boolean value)
	{
		setIsUseFutureStock( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AbstractOrderEntry.isUseFutureStock</code> attribute. 
	 * @param value the isUseFutureStock - flag for futureStock
	 */
	public void setIsUseFutureStock(final SessionContext ctx, final AbstractOrderEntry item, final boolean value)
	{
		setIsUseFutureStock( ctx, item, Boolean.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AbstractOrderEntry.isUseFutureStock</code> attribute. 
	 * @param value the isUseFutureStock - flag for futureStock
	 */
	public void setIsUseFutureStock(final AbstractOrderEntry item, final boolean value)
	{
		setIsUseFutureStock( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ProductCarouselComponent.isWell</code> attribute.
	 * @return the isWell - If show the well sale page
	 */
	public Boolean isIsWell(final SessionContext ctx, final ProductCarouselComponent item)
	{
		return (Boolean)item.getProperty( ctx, AcerchemCoreConstants.Attributes.ProductCarouselComponent.ISWELL);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ProductCarouselComponent.isWell</code> attribute.
	 * @return the isWell - If show the well sale page
	 */
	public Boolean isIsWell(final ProductCarouselComponent item)
	{
		return isIsWell( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ProductCarouselComponent.isWell</code> attribute. 
	 * @return the isWell - If show the well sale page
	 */
	public boolean isIsWellAsPrimitive(final SessionContext ctx, final ProductCarouselComponent item)
	{
		Boolean value = isIsWell( ctx,item );
		return value != null ? value.booleanValue() : false;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ProductCarouselComponent.isWell</code> attribute. 
	 * @return the isWell - If show the well sale page
	 */
	public boolean isIsWellAsPrimitive(final ProductCarouselComponent item)
	{
		return isIsWellAsPrimitive( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ProductCarouselComponent.isWell</code> attribute. 
	 * @param value the isWell - If show the well sale page
	 */
	public void setIsWell(final SessionContext ctx, final ProductCarouselComponent item, final Boolean value)
	{
		item.setProperty(ctx, AcerchemCoreConstants.Attributes.ProductCarouselComponent.ISWELL,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ProductCarouselComponent.isWell</code> attribute. 
	 * @param value the isWell - If show the well sale page
	 */
	public void setIsWell(final ProductCarouselComponent item, final Boolean value)
	{
		setIsWell( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ProductCarouselComponent.isWell</code> attribute. 
	 * @param value the isWell - If show the well sale page
	 */
	public void setIsWell(final SessionContext ctx, final ProductCarouselComponent item, final boolean value)
	{
		setIsWell( ctx, item, Boolean.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ProductCarouselComponent.isWell</code> attribute. 
	 * @param value the isWell - If show the well sale page
	 */
	public void setIsWell(final ProductCarouselComponent item, final boolean value)
	{
		setIsWell( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Customer.limitCreditAmount</code> attribute.
	 * @return the limitCreditAmount - The credit limit you will need for your business
	 */
	public BigDecimal getLimitCreditAmount(final SessionContext ctx, final Customer item)
	{
		return (BigDecimal)item.getProperty( ctx, AcerchemCoreConstants.Attributes.Customer.LIMITCREDITAMOUNT);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Customer.limitCreditAmount</code> attribute.
	 * @return the limitCreditAmount - The credit limit you will need for your business
	 */
	public BigDecimal getLimitCreditAmount(final Customer item)
	{
		return getLimitCreditAmount( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Customer.limitCreditAmount</code> attribute. 
	 * @param value the limitCreditAmount - The credit limit you will need for your business
	 */
	public void setLimitCreditAmount(final SessionContext ctx, final Customer item, final BigDecimal value)
	{
		item.setProperty(ctx, AcerchemCoreConstants.Attributes.Customer.LIMITCREDITAMOUNT,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Customer.limitCreditAmount</code> attribute. 
	 * @param value the limitCreditAmount - The credit limit you will need for your business
	 */
	public void setLimitCreditAmount(final Customer item, final BigDecimal value)
	{
		setLimitCreditAmount( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>StockLevel.preOrderReleaseDay</code> attribute.
	 * @return the preOrderReleaseDay
	 */
	public Integer getPreOrderReleaseDay(final SessionContext ctx, final StockLevel item)
	{
		return (Integer)item.getProperty( ctx, AcerchemCoreConstants.Attributes.StockLevel.PREORDERRELEASEDAY);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>StockLevel.preOrderReleaseDay</code> attribute.
	 * @return the preOrderReleaseDay
	 */
	public Integer getPreOrderReleaseDay(final StockLevel item)
	{
		return getPreOrderReleaseDay( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>StockLevel.preOrderReleaseDay</code> attribute. 
	 * @return the preOrderReleaseDay
	 */
	public int getPreOrderReleaseDayAsPrimitive(final SessionContext ctx, final StockLevel item)
	{
		Integer value = getPreOrderReleaseDay( ctx,item );
		return value != null ? value.intValue() : 0;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>StockLevel.preOrderReleaseDay</code> attribute. 
	 * @return the preOrderReleaseDay
	 */
	public int getPreOrderReleaseDayAsPrimitive(final StockLevel item)
	{
		return getPreOrderReleaseDayAsPrimitive( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>StockLevel.preOrderReleaseDay</code> attribute. 
	 * @param value the preOrderReleaseDay
	 */
	public void setPreOrderReleaseDay(final SessionContext ctx, final StockLevel item, final Integer value)
	{
		item.setProperty(ctx, AcerchemCoreConstants.Attributes.StockLevel.PREORDERRELEASEDAY,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>StockLevel.preOrderReleaseDay</code> attribute. 
	 * @param value the preOrderReleaseDay
	 */
	public void setPreOrderReleaseDay(final StockLevel item, final Integer value)
	{
		setPreOrderReleaseDay( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>StockLevel.preOrderReleaseDay</code> attribute. 
	 * @param value the preOrderReleaseDay
	 */
	public void setPreOrderReleaseDay(final SessionContext ctx, final StockLevel item, final int value)
	{
		setPreOrderReleaseDay( ctx, item, Integer.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>StockLevel.preOrderReleaseDay</code> attribute. 
	 * @param value the preOrderReleaseDay
	 */
	public void setPreOrderReleaseDay(final StockLevel item, final int value)
	{
		setPreOrderReleaseDay( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>CsTicket.productId</code> attribute.
	 * @return the productId
	 */
	public String getProductId(final SessionContext ctx, final CsTicket item)
	{
		return (String)item.getProperty( ctx, AcerchemCoreConstants.Attributes.CsTicket.PRODUCTID);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>CsTicket.productId</code> attribute.
	 * @return the productId
	 */
	public String getProductId(final CsTicket item)
	{
		return getProductId( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>CsTicket.productId</code> attribute. 
	 * @param value the productId
	 */
	public void setProductId(final SessionContext ctx, final CsTicket item, final String value)
	{
		item.setProperty(ctx, AcerchemCoreConstants.Attributes.CsTicket.PRODUCTID,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>CsTicket.productId</code> attribute. 
	 * @param value the productId
	 */
	public void setProductId(final CsTicket item, final String value)
	{
		setProductId( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>CsTicket.productName</code> attribute.
	 * @return the productName
	 */
	public String getProductName(final SessionContext ctx, final CsTicket item)
	{
		return (String)item.getProperty( ctx, AcerchemCoreConstants.Attributes.CsTicket.PRODUCTNAME);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>CsTicket.productName</code> attribute.
	 * @return the productName
	 */
	public String getProductName(final CsTicket item)
	{
		return getProductName( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>CsTicket.productName</code> attribute. 
	 * @param value the productName
	 */
	public void setProductName(final SessionContext ctx, final CsTicket item, final String value)
	{
		item.setProperty(ctx, AcerchemCoreConstants.Attributes.CsTicket.PRODUCTNAME,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>CsTicket.productName</code> attribute. 
	 * @param value the productName
	 */
	public void setProductName(final CsTicket item, final String value)
	{
		setProductName( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Customer.provideTradeReference</code> attribute.
	 * @return the provideTradeReference - Can you provide any trade reference for Acerchem?
	 */
	public Boolean isProvideTradeReference(final SessionContext ctx, final Customer item)
	{
		return (Boolean)item.getProperty( ctx, AcerchemCoreConstants.Attributes.Customer.PROVIDETRADEREFERENCE);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Customer.provideTradeReference</code> attribute.
	 * @return the provideTradeReference - Can you provide any trade reference for Acerchem?
	 */
	public Boolean isProvideTradeReference(final Customer item)
	{
		return isProvideTradeReference( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Customer.provideTradeReference</code> attribute. 
	 * @return the provideTradeReference - Can you provide any trade reference for Acerchem?
	 */
	public boolean isProvideTradeReferenceAsPrimitive(final SessionContext ctx, final Customer item)
	{
		Boolean value = isProvideTradeReference( ctx,item );
		return value != null ? value.booleanValue() : false;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Customer.provideTradeReference</code> attribute. 
	 * @return the provideTradeReference - Can you provide any trade reference for Acerchem?
	 */
	public boolean isProvideTradeReferenceAsPrimitive(final Customer item)
	{
		return isProvideTradeReferenceAsPrimitive( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Customer.provideTradeReference</code> attribute. 
	 * @param value the provideTradeReference - Can you provide any trade reference for Acerchem?
	 */
	public void setProvideTradeReference(final SessionContext ctx, final Customer item, final Boolean value)
	{
		item.setProperty(ctx, AcerchemCoreConstants.Attributes.Customer.PROVIDETRADEREFERENCE,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Customer.provideTradeReference</code> attribute. 
	 * @param value the provideTradeReference - Can you provide any trade reference for Acerchem?
	 */
	public void setProvideTradeReference(final Customer item, final Boolean value)
	{
		setProvideTradeReference( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Customer.provideTradeReference</code> attribute. 
	 * @param value the provideTradeReference - Can you provide any trade reference for Acerchem?
	 */
	public void setProvideTradeReference(final SessionContext ctx, final Customer item, final boolean value)
	{
		setProvideTradeReference( ctx, item, Boolean.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Customer.provideTradeReference</code> attribute. 
	 * @param value the provideTradeReference - Can you provide any trade reference for Acerchem?
	 */
	public void setProvideTradeReference(final Customer item, final boolean value)
	{
		setProvideTradeReference( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Employee.receptionist</code> attribute.
	 * @return the receptionist
	 */
	public List<Customer> getReceptionist(final SessionContext ctx, final Employee item)
	{
		return (List<Customer>)RECEPTIONIST2CUSTOMERRECEPTIONISTHANDLER.getValues( ctx, item );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Employee.receptionist</code> attribute.
	 * @return the receptionist
	 */
	public List<Customer> getReceptionist(final Employee item)
	{
		return getReceptionist( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Employee.receptionist</code> attribute. 
	 * @param value the receptionist
	 */
	public void setReceptionist(final SessionContext ctx, final Employee item, final List<Customer> value)
	{
		RECEPTIONIST2CUSTOMERRECEPTIONISTHANDLER.setValues( ctx, item, value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Employee.receptionist</code> attribute. 
	 * @param value the receptionist
	 */
	public void setReceptionist(final Employee item, final List<Customer> value)
	{
		setReceptionist( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Adds <code>value</code> to receptionist. 
	 * @param value the item to add to receptionist
	 */
	public void addToReceptionist(final SessionContext ctx, final Employee item, final Customer value)
	{
		RECEPTIONIST2CUSTOMERRECEPTIONISTHANDLER.addValue( ctx, item, value );
	}
	
	/**
	 * <i>Generated method</i> - Adds <code>value</code> to receptionist. 
	 * @param value the item to add to receptionist
	 */
	public void addToReceptionist(final Employee item, final Customer value)
	{
		addToReceptionist( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Removes <code>value</code> from receptionist. 
	 * @param value the item to remove from receptionist
	 */
	public void removeFromReceptionist(final SessionContext ctx, final Employee item, final Customer value)
	{
		RECEPTIONIST2CUSTOMERRECEPTIONISTHANDLER.removeValue( ctx, item, value );
	}
	
	/**
	 * <i>Generated method</i> - Removes <code>value</code> from receptionist. 
	 * @param value the item to remove from receptionist
	 */
	public void removeFromReceptionist(final Employee item, final Customer value)
	{
		removeFromReceptionist( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Customer.relatedCustomer</code> attribute.
	 * @return the relatedCustomer
	 */
	public Employee getRelatedCustomer(final SessionContext ctx, final Customer item)
	{
		return (Employee)item.getProperty( ctx, AcerchemCoreConstants.Attributes.Customer.RELATEDCUSTOMER);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Customer.relatedCustomer</code> attribute.
	 * @return the relatedCustomer
	 */
	public Employee getRelatedCustomer(final Customer item)
	{
		return getRelatedCustomer( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Customer.relatedCustomer</code> attribute. 
	 * @param value the relatedCustomer
	 */
	public void setRelatedCustomer(final SessionContext ctx, final Customer item, final Employee value)
	{
		item.setProperty(ctx, AcerchemCoreConstants.Attributes.Customer.RELATEDCUSTOMER,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Customer.relatedCustomer</code> attribute. 
	 * @param value the relatedCustomer
	 */
	public void setRelatedCustomer(final Customer item, final Employee value)
	{
		setRelatedCustomer( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Customer.relatedCustomerPOS</code> attribute.
	 * @return the relatedCustomerPOS
	 */
	 Integer getRelatedCustomerPOS(final SessionContext ctx, final Customer item)
	{
		return (Integer)item.getProperty( ctx, AcerchemCoreConstants.Attributes.Customer.RELATEDCUSTOMERPOS);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Customer.relatedCustomerPOS</code> attribute.
	 * @return the relatedCustomerPOS
	 */
	 Integer getRelatedCustomerPOS(final Customer item)
	{
		return getRelatedCustomerPOS( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Customer.relatedCustomerPOS</code> attribute. 
	 * @return the relatedCustomerPOS
	 */
	 int getRelatedCustomerPOSAsPrimitive(final SessionContext ctx, final Customer item)
	{
		Integer value = getRelatedCustomerPOS( ctx,item );
		return value != null ? value.intValue() : 0;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Customer.relatedCustomerPOS</code> attribute. 
	 * @return the relatedCustomerPOS
	 */
	 int getRelatedCustomerPOSAsPrimitive(final Customer item)
	{
		return getRelatedCustomerPOSAsPrimitive( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Customer.relatedCustomerPOS</code> attribute. 
	 * @param value the relatedCustomerPOS
	 */
	 void setRelatedCustomerPOS(final SessionContext ctx, final Customer item, final Integer value)
	{
		item.setProperty(ctx, AcerchemCoreConstants.Attributes.Customer.RELATEDCUSTOMERPOS,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Customer.relatedCustomerPOS</code> attribute. 
	 * @param value the relatedCustomerPOS
	 */
	 void setRelatedCustomerPOS(final Customer item, final Integer value)
	{
		setRelatedCustomerPOS( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Customer.relatedCustomerPOS</code> attribute. 
	 * @param value the relatedCustomerPOS
	 */
	 void setRelatedCustomerPOS(final SessionContext ctx, final Customer item, final int value)
	{
		setRelatedCustomerPOS( ctx, item, Integer.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Customer.relatedCustomerPOS</code> attribute. 
	 * @param value the relatedCustomerPOS
	 */
	 void setRelatedCustomerPOS(final Customer item, final int value)
	{
		setRelatedCustomerPOS( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Customer.revenue</code> attribute.
	 * @return the revenue - Revenue
	 */
	public BigDecimal getRevenue(final SessionContext ctx, final Customer item)
	{
		return (BigDecimal)item.getProperty( ctx, AcerchemCoreConstants.Attributes.Customer.REVENUE);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Customer.revenue</code> attribute.
	 * @return the revenue - Revenue
	 */
	public BigDecimal getRevenue(final Customer item)
	{
		return getRevenue( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Customer.revenue</code> attribute. 
	 * @param value the revenue - Revenue
	 */
	public void setRevenue(final SessionContext ctx, final Customer item, final BigDecimal value)
	{
		item.setProperty(ctx, AcerchemCoreConstants.Attributes.Customer.REVENUE,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Customer.revenue</code> attribute. 
	 * @param value the revenue - Revenue
	 */
	public void setRevenue(final Customer item, final BigDecimal value)
	{
		setRevenue( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>CsTicket.telephone</code> attribute.
	 * @return the telephone
	 */
	public String getTelephone(final SessionContext ctx, final CsTicket item)
	{
		return (String)item.getProperty( ctx, AcerchemCoreConstants.Attributes.CsTicket.TELEPHONE);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>CsTicket.telephone</code> attribute.
	 * @return the telephone
	 */
	public String getTelephone(final CsTicket item)
	{
		return getTelephone( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>CsTicket.telephone</code> attribute. 
	 * @param value the telephone
	 */
	public void setTelephone(final SessionContext ctx, final CsTicket item, final String value)
	{
		item.setProperty(ctx, AcerchemCoreConstants.Attributes.CsTicket.TELEPHONE,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>CsTicket.telephone</code> attribute. 
	 * @param value the telephone
	 */
	public void setTelephone(final CsTicket item, final String value)
	{
		setTelephone( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Product.unitCalculateRato</code> attribute.
	 * @return the unitCalculateRato - Attribute about çnitCalculateRato of Product
	 */
	public String getUnitCalculateRato(final SessionContext ctx, final Product item)
	{
		return (String)item.getProperty( ctx, AcerchemCoreConstants.Attributes.Product.UNITCALCULATERATO);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Product.unitCalculateRato</code> attribute.
	 * @return the unitCalculateRato - Attribute about çnitCalculateRato of Product
	 */
	public String getUnitCalculateRato(final Product item)
	{
		return getUnitCalculateRato( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Product.unitCalculateRato</code> attribute. 
	 * @param value the unitCalculateRato - Attribute about çnitCalculateRato of Product
	 */
	public void setUnitCalculateRato(final SessionContext ctx, final Product item, final String value)
	{
		item.setProperty(ctx, AcerchemCoreConstants.Attributes.Product.UNITCALCULATERATO,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Product.unitCalculateRato</code> attribute. 
	 * @param value the unitCalculateRato - Attribute about çnitCalculateRato of Product
	 */
	public void setUnitCalculateRato(final Product item, final String value)
	{
		setUnitCalculateRato( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>User.userLevel</code> attribute.
	 * @return the userLevel
	 */
	public UserLevel getUserLevel(final SessionContext ctx, final User item)
	{
		return (UserLevel)item.getProperty( ctx, AcerchemCoreConstants.Attributes.User.USERLEVEL);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>User.userLevel</code> attribute.
	 * @return the userLevel
	 */
	public UserLevel getUserLevel(final User item)
	{
		return getUserLevel( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>User.userLevel</code> attribute. 
	 * @param value the userLevel
	 */
	public void setUserLevel(final SessionContext ctx, final User item, final UserLevel value)
	{
		item.setProperty(ctx, AcerchemCoreConstants.Attributes.User.USERLEVEL,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>User.userLevel</code> attribute. 
	 * @param value the userLevel
	 */
	public void setUserLevel(final User item, final UserLevel value)
	{
		setUserLevel( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>User.userLevelPOS</code> attribute.
	 * @return the userLevelPOS
	 */
	 Integer getUserLevelPOS(final SessionContext ctx, final User item)
	{
		return (Integer)item.getProperty( ctx, AcerchemCoreConstants.Attributes.User.USERLEVELPOS);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>User.userLevelPOS</code> attribute.
	 * @return the userLevelPOS
	 */
	 Integer getUserLevelPOS(final User item)
	{
		return getUserLevelPOS( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>User.userLevelPOS</code> attribute. 
	 * @return the userLevelPOS
	 */
	 int getUserLevelPOSAsPrimitive(final SessionContext ctx, final User item)
	{
		Integer value = getUserLevelPOS( ctx,item );
		return value != null ? value.intValue() : 0;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>User.userLevelPOS</code> attribute. 
	 * @return the userLevelPOS
	 */
	 int getUserLevelPOSAsPrimitive(final User item)
	{
		return getUserLevelPOSAsPrimitive( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>User.userLevelPOS</code> attribute. 
	 * @param value the userLevelPOS
	 */
	 void setUserLevelPOS(final SessionContext ctx, final User item, final Integer value)
	{
		item.setProperty(ctx, AcerchemCoreConstants.Attributes.User.USERLEVELPOS,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>User.userLevelPOS</code> attribute. 
	 * @param value the userLevelPOS
	 */
	 void setUserLevelPOS(final User item, final Integer value)
	{
		setUserLevelPOS( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>User.userLevelPOS</code> attribute. 
	 * @param value the userLevelPOS
	 */
	 void setUserLevelPOS(final SessionContext ctx, final User item, final int value)
	{
		setUserLevelPOS( ctx, item, Integer.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>User.userLevelPOS</code> attribute. 
	 * @param value the userLevelPOS
	 */
	 void setUserLevelPOS(final User item, final int value)
	{
		setUserLevelPOS( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Customer.vatNo</code> attribute.
	 * @return the vatNo - VAT No.
	 */
	public String getVatNo(final SessionContext ctx, final Customer item)
	{
		return (String)item.getProperty( ctx, AcerchemCoreConstants.Attributes.Customer.VATNO);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Customer.vatNo</code> attribute.
	 * @return the vatNo - VAT No.
	 */
	public String getVatNo(final Customer item)
	{
		return getVatNo( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Customer.vatNo</code> attribute. 
	 * @param value the vatNo - VAT No.
	 */
	public void setVatNo(final SessionContext ctx, final Customer item, final String value)
	{
		item.setProperty(ctx, AcerchemCoreConstants.Attributes.Customer.VATNO,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Customer.vatNo</code> attribute. 
	 * @param value the vatNo - VAT No.
	 */
	public void setVatNo(final Customer item, final String value)
	{
		setVatNo( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>CsTicket.yourname</code> attribute.
	 * @return the yourname
	 */
	public String getYourname(final SessionContext ctx, final CsTicket item)
	{
		return (String)item.getProperty( ctx, AcerchemCoreConstants.Attributes.CsTicket.YOURNAME);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>CsTicket.yourname</code> attribute.
	 * @return the yourname
	 */
	public String getYourname(final CsTicket item)
	{
		return getYourname( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>CsTicket.yourname</code> attribute. 
	 * @param value the yourname
	 */
	public void setYourname(final SessionContext ctx, final CsTicket item, final String value)
	{
		item.setProperty(ctx, AcerchemCoreConstants.Attributes.CsTicket.YOURNAME,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>CsTicket.yourname</code> attribute. 
	 * @param value the yourname
	 */
	public void setYourname(final CsTicket item, final String value)
	{
		setYourname( getSession().getSessionContext(), item, value );
	}
	
}
