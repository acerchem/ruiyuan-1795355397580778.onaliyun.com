/*
 * ----------------------------------------------------------------
 * --- WARNING: THIS FILE IS GENERATED AND WILL BE OVERWRITTEN! ---
 * --- Generated at 2018-3-28 0:33:53                           ---
 * ----------------------------------------------------------------
 */
package com.acerchem.core.jalo;

import com.acerchem.core.constants.AcerchemCoreConstants;
import com.acerchem.core.jalo.ApparelProduct;
import com.acerchem.core.jalo.ApparelSizeVariantProduct;
import com.acerchem.core.jalo.ApparelStyleVariantProduct;
import com.acerchem.core.jalo.ElectronicsColorVariantProduct;
import com.acerchem.core.jalo.ImageFailedRecord;
import com.acerchem.core.jalo.ImageUploadedLog;
import de.hybris.platform.acceleratorcms.jalo.components.AbstractResponsiveBannerComponent;
import de.hybris.platform.acceleratorcms.jalo.components.SimpleResponsiveBannerComponent;
import de.hybris.platform.cms2.jalo.contents.components.SimpleCMSComponent;
import de.hybris.platform.cms2lib.components.FunctionButtonComponent;
import de.hybris.platform.cms2lib.components.MultipleBannerComponent;
import de.hybris.platform.cms2lib.components.ProductCarouselComponent;
import de.hybris.platform.jalo.GenericItem;
import de.hybris.platform.jalo.Item;
import de.hybris.platform.jalo.Item.AttributeMode;
import de.hybris.platform.jalo.JaloBusinessException;
import de.hybris.platform.jalo.JaloSystemException;
import de.hybris.platform.jalo.SessionContext;
import de.hybris.platform.jalo.extension.Extension;
import de.hybris.platform.jalo.link.Link;
import de.hybris.platform.jalo.media.AbstractMedia;
import de.hybris.platform.jalo.media.Media;
import de.hybris.platform.jalo.order.AbstractOrderEntry;
import de.hybris.platform.jalo.product.Product;
import de.hybris.platform.jalo.type.ComposedType;
import de.hybris.platform.jalo.type.JaloGenericCreationException;
import de.hybris.platform.ordersplitting.jalo.StockLevel;
import de.hybris.platform.ordersplitting.jalo.Vendor;
import de.hybris.platform.util.Utilities;
import java.util.Collection;
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
	/** Relation ordering override parameter constants for AcerChemVendor2Product from ((acerchemcore))*/
	protected static String ACERCHEMVENDOR2PRODUCT_SRC_ORDERED = "relation.AcerChemVendor2Product.source.ordered";
	protected static String ACERCHEMVENDOR2PRODUCT_TGT_ORDERED = "relation.AcerChemVendor2Product.target.ordered";
	/** Relation disable markmodifed parameter constants for AcerChemVendor2Product from ((acerchemcore))*/
	protected static String ACERCHEMVENDOR2PRODUCT_MARKMODIFIED = "relation.AcerChemVendor2Product.markmodified";
	protected static final Map<String, Map<String, AttributeMode>> DEFAULT_INITIAL_ATTRIBUTES;
	static
	{
		final Map<String, Map<String, AttributeMode>> ttmp = new HashMap();
		Map<String, AttributeMode> tmp = new HashMap<String, AttributeMode>();
		tmp.put("aliyunUrl", AttributeMode.INITIAL);
		ttmp.put("de.hybris.platform.jalo.media.Media", Collections.unmodifiableMap(tmp));
		tmp = new HashMap<String, AttributeMode>();
		tmp.put("chemicalInfo", AttributeMode.INITIAL);
		tmp.put("unitCalculateRato", AttributeMode.INITIAL);
		tmp.put("anonymousDisplayPrice", AttributeMode.INITIAL);
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
		ttmp.put("de.hybris.platform.ordersplitting.jalo.StockLevel", Collections.unmodifiableMap(tmp));
		tmp = new HashMap<String, AttributeMode>();
		tmp.put("isUseFutureStock", AttributeMode.INITIAL);
		tmp.put("warehouseCode", AttributeMode.INITIAL);
		tmp.put("shipDate", AttributeMode.INITIAL);
		ttmp.put("de.hybris.platform.jalo.order.AbstractOrderEntry", Collections.unmodifiableMap(tmp));
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
	public Collection<Product> getAcerChemProducts(final SessionContext ctx, final Vendor item)
	{
		final List<Product> items = item.getLinkedItems( 
			ctx,
			true,
			AcerchemCoreConstants.Relations.ACERCHEMVENDOR2PRODUCT,
			"Product",
			null,
			false,
			false
		);
		return items;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Vendor.acerChemProducts</code> attribute.
	 * @return the acerChemProducts
	 */
	public Collection<Product> getAcerChemProducts(final Vendor item)
	{
		return getAcerChemProducts( getSession().getSessionContext(), item );
	}
	
	public long getAcerChemProductsCount(final SessionContext ctx, final Vendor item)
	{
		return item.getLinkedItemsCount(
			ctx,
			true,
			AcerchemCoreConstants.Relations.ACERCHEMVENDOR2PRODUCT,
			"Product",
			null
		);
	}
	
	public long getAcerChemProductsCount(final Vendor item)
	{
		return getAcerChemProductsCount( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Vendor.acerChemProducts</code> attribute. 
	 * @param value the acerChemProducts
	 */
	public void setAcerChemProducts(final SessionContext ctx, final Vendor item, final Collection<Product> value)
	{
		item.setLinkedItems( 
			ctx,
			true,
			AcerchemCoreConstants.Relations.ACERCHEMVENDOR2PRODUCT,
			null,
			value,
			false,
			false,
			Utilities.getMarkModifiedOverride(ACERCHEMVENDOR2PRODUCT_MARKMODIFIED)
		);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Vendor.acerChemProducts</code> attribute. 
	 * @param value the acerChemProducts
	 */
	public void setAcerChemProducts(final Vendor item, final Collection<Product> value)
	{
		setAcerChemProducts( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Adds <code>value</code> to acerChemProducts. 
	 * @param value the item to add to acerChemProducts
	 */
	public void addToAcerChemProducts(final SessionContext ctx, final Vendor item, final Product value)
	{
		item.addLinkedItems( 
			ctx,
			true,
			AcerchemCoreConstants.Relations.ACERCHEMVENDOR2PRODUCT,
			null,
			Collections.singletonList(value),
			false,
			false,
			Utilities.getMarkModifiedOverride(ACERCHEMVENDOR2PRODUCT_MARKMODIFIED)
		);
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
		item.removeLinkedItems( 
			ctx,
			true,
			AcerchemCoreConstants.Relations.ACERCHEMVENDOR2PRODUCT,
			null,
			Collections.singletonList(value),
			false,
			false,
			Utilities.getMarkModifiedOverride(ACERCHEMVENDOR2PRODUCT_MARKMODIFIED)
		);
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
	 * <i>Generated method</i> - Getter of the <code>Product.acerChemVendors</code> attribute.
	 * @return the acerChemVendors
	 */
	public Collection<Vendor> getAcerChemVendors(final SessionContext ctx, final Product item)
	{
		final List<Vendor> items = item.getLinkedItems( 
			ctx,
			false,
			AcerchemCoreConstants.Relations.ACERCHEMVENDOR2PRODUCT,
			"Vendor",
			null,
			false,
			false
		);
		return items;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Product.acerChemVendors</code> attribute.
	 * @return the acerChemVendors
	 */
	public Collection<Vendor> getAcerChemVendors(final Product item)
	{
		return getAcerChemVendors( getSession().getSessionContext(), item );
	}
	
	public long getAcerChemVendorsCount(final SessionContext ctx, final Product item)
	{
		return item.getLinkedItemsCount(
			ctx,
			false,
			AcerchemCoreConstants.Relations.ACERCHEMVENDOR2PRODUCT,
			"Vendor",
			null
		);
	}
	
	public long getAcerChemVendorsCount(final Product item)
	{
		return getAcerChemVendorsCount( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Product.acerChemVendors</code> attribute. 
	 * @param value the acerChemVendors
	 */
	public void setAcerChemVendors(final SessionContext ctx, final Product item, final Collection<Vendor> value)
	{
		item.setLinkedItems( 
			ctx,
			false,
			AcerchemCoreConstants.Relations.ACERCHEMVENDOR2PRODUCT,
			null,
			value,
			false,
			false,
			Utilities.getMarkModifiedOverride(ACERCHEMVENDOR2PRODUCT_MARKMODIFIED)
		);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Product.acerChemVendors</code> attribute. 
	 * @param value the acerChemVendors
	 */
	public void setAcerChemVendors(final Product item, final Collection<Vendor> value)
	{
		setAcerChemVendors( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Adds <code>value</code> to acerChemVendors. 
	 * @param value the item to add to acerChemVendors
	 */
	public void addToAcerChemVendors(final SessionContext ctx, final Product item, final Vendor value)
	{
		item.addLinkedItems( 
			ctx,
			false,
			AcerchemCoreConstants.Relations.ACERCHEMVENDOR2PRODUCT,
			null,
			Collections.singletonList(value),
			false,
			false,
			Utilities.getMarkModifiedOverride(ACERCHEMVENDOR2PRODUCT_MARKMODIFIED)
		);
	}
	
	/**
	 * <i>Generated method</i> - Adds <code>value</code> to acerChemVendors. 
	 * @param value the item to add to acerChemVendors
	 */
	public void addToAcerChemVendors(final Product item, final Vendor value)
	{
		addToAcerChemVendors( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Removes <code>value</code> from acerChemVendors. 
	 * @param value the item to remove from acerChemVendors
	 */
	public void removeFromAcerChemVendors(final SessionContext ctx, final Product item, final Vendor value)
	{
		item.removeLinkedItems( 
			ctx,
			false,
			AcerchemCoreConstants.Relations.ACERCHEMVENDOR2PRODUCT,
			null,
			Collections.singletonList(value),
			false,
			false,
			Utilities.getMarkModifiedOverride(ACERCHEMVENDOR2PRODUCT_MARKMODIFIED)
		);
	}
	
	/**
	 * <i>Generated method</i> - Removes <code>value</code> from acerChemVendors. 
	 * @param value the item to remove from acerChemVendors
	 */
	public void removeFromAcerChemVendors(final Product item, final Vendor value)
	{
		removeFromAcerChemVendors( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Media.aliyunUrl</code> attribute.
	 * @return the aliyunUrl - é¿éäºÂUrl
	 */
	public String getAliyunUrl(final SessionContext ctx, final Media item)
	{
		return (String)item.getProperty( ctx, AcerchemCoreConstants.Attributes.Media.ALIYUNURL);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Media.aliyunUrl</code> attribute.
	 * @return the aliyunUrl - é¿éäºÂUrl
	 */
	public String getAliyunUrl(final Media item)
	{
		return getAliyunUrl( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Media.aliyunUrl</code> attribute. 
	 * @param value the aliyunUrl - é¿éäºÂUrl
	 */
	public void setAliyunUrl(final SessionContext ctx, final Media item, final String value)
	{
		item.setProperty(ctx, AcerchemCoreConstants.Attributes.Media.ALIYUNURL,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Media.aliyunUrl</code> attribute. 
	 * @param value the aliyunUrl - é¿éäºÂUrl
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
	 * <i>Generated method</i> - Getter of the <code>Product.chemicalInfo</code> attribute.
	 * @return the chemicalInfo - Attribute about Ã§ÂÂ¾hemicalInfo of Product
	 */
	public String getChemicalInfo(final SessionContext ctx, final Product item)
	{
		return (String)item.getProperty( ctx, AcerchemCoreConstants.Attributes.Product.CHEMICALINFO);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Product.chemicalInfo</code> attribute.
	 * @return the chemicalInfo - Attribute about Ã§ÂÂ¾hemicalInfo of Product
	 */
	public String getChemicalInfo(final Product item)
	{
		return getChemicalInfo( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Product.chemicalInfo</code> attribute. 
	 * @param value the chemicalInfo - Attribute about Ã§ÂÂ¾hemicalInfo of Product
	 */
	public void setChemicalInfo(final SessionContext ctx, final Product item, final String value)
	{
		item.setProperty(ctx, AcerchemCoreConstants.Attributes.Product.CHEMICALINFO,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Product.chemicalInfo</code> attribute. 
	 * @param value the chemicalInfo - Attribute about Ã§ÂÂ¾hemicalInfo of Product
	 */
	public void setChemicalInfo(final Product item, final String value)
	{
		setChemicalInfo( getSession().getSessionContext(), item, value );
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
	
	@Override
	public String getName()
	{
		return AcerchemCoreConstants.EXTENSIONNAME;
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
	 * <i>Generated method</i> - Getter of the <code>AbstractOrderEntry.shipDate</code> attribute.
	 * @return the shipDate
	 */
	public Date getShipDate(final SessionContext ctx, final AbstractOrderEntry item)
	{
		return (Date)item.getProperty( ctx, AcerchemCoreConstants.Attributes.AbstractOrderEntry.SHIPDATE);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AbstractOrderEntry.shipDate</code> attribute.
	 * @return the shipDate
	 */
	public Date getShipDate(final AbstractOrderEntry item)
	{
		return getShipDate( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AbstractOrderEntry.shipDate</code> attribute. 
	 * @param value the shipDate
	 */
	public void setShipDate(final SessionContext ctx, final AbstractOrderEntry item, final Date value)
	{
		item.setProperty(ctx, AcerchemCoreConstants.Attributes.AbstractOrderEntry.SHIPDATE,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AbstractOrderEntry.shipDate</code> attribute. 
	 * @param value the shipDate
	 */
	public void setShipDate(final AbstractOrderEntry item, final Date value)
	{
		setShipDate( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Product.unitCalculateRato</code> attribute.
	 * @return the unitCalculateRato - Attribute about Ã§ÂÂnitCalculateRato of Product
	 */
	public String getUnitCalculateRato(final SessionContext ctx, final Product item)
	{
		return (String)item.getProperty( ctx, AcerchemCoreConstants.Attributes.Product.UNITCALCULATERATO);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Product.unitCalculateRato</code> attribute.
	 * @return the unitCalculateRato - Attribute about Ã§ÂÂnitCalculateRato of Product
	 */
	public String getUnitCalculateRato(final Product item)
	{
		return getUnitCalculateRato( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Product.unitCalculateRato</code> attribute. 
	 * @param value the unitCalculateRato - Attribute about Ã§ÂÂnitCalculateRato of Product
	 */
	public void setUnitCalculateRato(final SessionContext ctx, final Product item, final String value)
	{
		item.setProperty(ctx, AcerchemCoreConstants.Attributes.Product.UNITCALCULATERATO,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Product.unitCalculateRato</code> attribute. 
	 * @param value the unitCalculateRato - Attribute about Ã§ÂÂnitCalculateRato of Product
	 */
	public void setUnitCalculateRato(final Product item, final String value)
	{
		setUnitCalculateRato( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AbstractOrderEntry.warehouseCode</code> attribute.
	 * @return the warehouseCode
	 */
	public String getWarehouseCode(final SessionContext ctx, final AbstractOrderEntry item)
	{
		return (String)item.getProperty( ctx, AcerchemCoreConstants.Attributes.AbstractOrderEntry.WAREHOUSECODE);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AbstractOrderEntry.warehouseCode</code> attribute.
	 * @return the warehouseCode
	 */
	public String getWarehouseCode(final AbstractOrderEntry item)
	{
		return getWarehouseCode( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AbstractOrderEntry.warehouseCode</code> attribute. 
	 * @param value the warehouseCode
	 */
	public void setWarehouseCode(final SessionContext ctx, final AbstractOrderEntry item, final String value)
	{
		item.setProperty(ctx, AcerchemCoreConstants.Attributes.AbstractOrderEntry.WAREHOUSECODE,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AbstractOrderEntry.warehouseCode</code> attribute. 
	 * @param value the warehouseCode
	 */
	public void setWarehouseCode(final AbstractOrderEntry item, final String value)
	{
		setWarehouseCode( getSession().getSessionContext(), item, value );
	}
	
}
