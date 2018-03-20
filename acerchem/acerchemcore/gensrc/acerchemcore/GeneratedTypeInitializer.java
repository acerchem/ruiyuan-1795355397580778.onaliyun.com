

package acerchemcore;

import java.util.*;
import java.io.Serializable;
import de.hybris.platform.util.*;
import de.hybris.platform.core.*;
import de.hybris.platform.jalo.JaloBusinessException;
import de.hybris.platform.jalo.type.*;
import de.hybris.platform.persistence.type.*;
import de.hybris.platform.persistence.enumeration.*;
import de.hybris.platform.persistence.property.PersistenceManager;
import de.hybris.platform.persistence.*;

/**
 * Generated by hybris Platform.
 */
@SuppressWarnings({"cast","unused","boxing","null", "PMD"})
public class GeneratedTypeInitializer extends AbstractTypeInitializer
{
	/**
	 * Generated by hybris Platform.
	 */
	public GeneratedTypeInitializer( ManagerEJB manager, Map params )
	{
		super( manager, params );
	}


	/**
	 * Generated by hybris Platform.
	 */
	@Override
	protected void performRemoveObjects( ManagerEJB manager, Map params ) throws JaloBusinessException
	{
		// no-op by now
	}

	/**
	 * Generated by hybris Platform.
	 */
	@Override
	protected final void performCreateTypes( final ManagerEJB manager, Map params ) throws JaloBusinessException
	{
		// performCreateTypes
	
	
		createItemType(
			"ApparelProduct",
			"Product",
			com.acerchem.core.jalo.ApparelProduct.class,
			null,
			false,
			null,
			false
		);
	
		createItemType(
			"ApparelStyleVariantProduct",
			"VariantProduct",
			com.acerchem.core.jalo.ApparelStyleVariantProduct.class,
			null,
			false,
			null,
			false
		);
	
		createItemType(
			"ApparelSizeVariantProduct",
			"ApparelStyleVariantProduct",
			com.acerchem.core.jalo.ApparelSizeVariantProduct.class,
			null,
			false,
			null,
			false
		);
	
		createItemType(
			"ElectronicsColorVariantProduct",
			"VariantProduct",
			com.acerchem.core.jalo.ElectronicsColorVariantProduct.class,
			null,
			false,
			null,
			false
		);
	
		createItemType(
			"ImageFailedRecord",
			"GenericItem",
			com.acerchem.core.jalo.ImageFailedRecord.class,
			"de.hybris.platform.persistence.acerchemcore_ImageFailedRecord",
			false,
			null,
			false
		);
	
		createItemType(
			"ImageUploadedLog",
			"GenericItem",
			com.acerchem.core.jalo.ImageUploadedLog.class,
			"de.hybris.platform.persistence.acerchemcore_ImageUploadedLog",
			false,
			null,
			false
		);
	
		createEnumerationType(
			"SwatchColorEnum",
			null
		);
	
		createEnumerationType(
			"ImageFailedActionType",
			null
		);
	
		createCollectionType(
			"GenderList",
			"Gender",
			CollectionType.LIST
		);
	
		createCollectionType(
			"SwatchColorSet",
			"SwatchColorEnum",
			CollectionType.SET
		);
	
	}

	/**
	 * Generated by hybris Platform.
	 */
	@Override
	protected final void performModifyTypes( final ManagerEJB manager, Map params ) throws JaloBusinessException
	{
		// performModifyTypes
	

	
	
				single_createattr_ApparelProduct_genders();
			
				single_createattr_ApparelStyleVariantProduct_style();
			
				single_createattr_ApparelStyleVariantProduct_swatchColors();
			
				single_createattr_ApparelSizeVariantProduct_size();
			
				single_createattr_ElectronicsColorVariantProduct_color();
			
				single_createattr_Media_aliyunUrl();
			
				single_createattr_ImageFailedRecord_fileName();
			
				single_createattr_ImageFailedRecord_actionType();
			
				single_createattr_ImageFailedRecord_aliyunUrl();
			
				single_createattr_ImageFailedRecord_location();
			
				single_createattr_ImageFailedRecord_status();
			
				single_createattr_ImageUploadedLog_imagePK();
			
				single_createattr_ImageUploadedLog_aliyunUrl();
			
				single_createattr_Product_chemicalInfo();
			
				single_createattr_Product_unitCalculateRato();
			
				single_createattr_Product_anonymousDisplayPrice();
			
				single_createattr_StockLevel_preOrderReleaseDay();
			

	}

	
	public void single_createattr_ApparelProduct_genders() throws JaloBusinessException
	{
		
						Map sqlColumnDefinitions = null;
					
				createPropertyAttribute(
					"ApparelProduct", 
					"genders",  
					null,
					"GenderList",
					de.hybris.platform.jalo.type.AttributeDescriptor.READ_FLAG|de.hybris.platform.jalo.type.AttributeDescriptor.WRITE_FLAG|de.hybris.platform.jalo.type.AttributeDescriptor.OPTIONAL_FLAG|de.hybris.platform.jalo.type.AttributeDescriptor.REMOVE_FLAG|de.hybris.platform.jalo.type.AttributeDescriptor.SEARCH_FLAG,
					null,
					sqlColumnDefinitions
				);
			
	}
	
	public void single_createattr_ApparelStyleVariantProduct_style() throws JaloBusinessException
	{
		
						Map sqlColumnDefinitions = null;
					
				createPropertyAttribute(
					"ApparelStyleVariantProduct", 
					"style",  
					null,
					"localized:java.lang.String",
					de.hybris.platform.jalo.type.AttributeDescriptor.READ_FLAG|de.hybris.platform.jalo.type.AttributeDescriptor.WRITE_FLAG|de.hybris.platform.jalo.type.AttributeDescriptor.OPTIONAL_FLAG|de.hybris.platform.jalo.type.AttributeDescriptor.REMOVE_FLAG|de.hybris.platform.jalo.type.AttributeDescriptor.SEARCH_FLAG,
					"VariantAttributeDescriptor",
					sqlColumnDefinitions
				);
			
	}
	
	public void single_createattr_ApparelStyleVariantProduct_swatchColors() throws JaloBusinessException
	{
		
						Map sqlColumnDefinitions = null;
					
				createPropertyAttribute(
					"ApparelStyleVariantProduct", 
					"swatchColors",  
					null,
					"SwatchColorSet",
					de.hybris.platform.jalo.type.AttributeDescriptor.READ_FLAG|de.hybris.platform.jalo.type.AttributeDescriptor.WRITE_FLAG|de.hybris.platform.jalo.type.AttributeDescriptor.OPTIONAL_FLAG|de.hybris.platform.jalo.type.AttributeDescriptor.REMOVE_FLAG|de.hybris.platform.jalo.type.AttributeDescriptor.SEARCH_FLAG,
					null,
					sqlColumnDefinitions
				);
			
	}
	
	public void single_createattr_ApparelSizeVariantProduct_size() throws JaloBusinessException
	{
		
						Map sqlColumnDefinitions = null;
					
				createPropertyAttribute(
					"ApparelSizeVariantProduct", 
					"size",  
					null,
					"localized:java.lang.String",
					de.hybris.platform.jalo.type.AttributeDescriptor.READ_FLAG|de.hybris.platform.jalo.type.AttributeDescriptor.WRITE_FLAG|de.hybris.platform.jalo.type.AttributeDescriptor.OPTIONAL_FLAG|de.hybris.platform.jalo.type.AttributeDescriptor.REMOVE_FLAG|de.hybris.platform.jalo.type.AttributeDescriptor.SEARCH_FLAG,
					"VariantAttributeDescriptor",
					sqlColumnDefinitions
				);
			
	}
	
	public void single_createattr_ElectronicsColorVariantProduct_color() throws JaloBusinessException
	{
		
						Map sqlColumnDefinitions = null;
					
				createPropertyAttribute(
					"ElectronicsColorVariantProduct", 
					"color",  
					null,
					"localized:java.lang.String",
					de.hybris.platform.jalo.type.AttributeDescriptor.READ_FLAG|de.hybris.platform.jalo.type.AttributeDescriptor.WRITE_FLAG|de.hybris.platform.jalo.type.AttributeDescriptor.OPTIONAL_FLAG|de.hybris.platform.jalo.type.AttributeDescriptor.REMOVE_FLAG|de.hybris.platform.jalo.type.AttributeDescriptor.SEARCH_FLAG,
					"VariantAttributeDescriptor",
					sqlColumnDefinitions
				);
			
	}
	
	public void single_createattr_Media_aliyunUrl() throws JaloBusinessException
	{
		
						Map sqlColumnDefinitions = null;
					
				createPropertyAttribute(
					"Media", 
					"aliyunUrl",  
					null,
					"java.lang.String",
					de.hybris.platform.jalo.type.AttributeDescriptor.READ_FLAG|de.hybris.platform.jalo.type.AttributeDescriptor.WRITE_FLAG|de.hybris.platform.jalo.type.AttributeDescriptor.OPTIONAL_FLAG|de.hybris.platform.jalo.type.AttributeDescriptor.REMOVE_FLAG|de.hybris.platform.jalo.type.AttributeDescriptor.SEARCH_FLAG,
					null,
					sqlColumnDefinitions
				);
			
	}
	
	public void single_createattr_ImageFailedRecord_fileName() throws JaloBusinessException
	{
		
						Map sqlColumnDefinitions = null;
					
				createPropertyAttribute(
					"ImageFailedRecord", 
					"fileName",  
					null,
					"java.lang.String",
					de.hybris.platform.jalo.type.AttributeDescriptor.READ_FLAG|de.hybris.platform.jalo.type.AttributeDescriptor.WRITE_FLAG|de.hybris.platform.jalo.type.AttributeDescriptor.OPTIONAL_FLAG|de.hybris.platform.jalo.type.AttributeDescriptor.REMOVE_FLAG|de.hybris.platform.jalo.type.AttributeDescriptor.SEARCH_FLAG,
					null,
					sqlColumnDefinitions
				);
			
	}
	
	public void single_createattr_ImageFailedRecord_actionType() throws JaloBusinessException
	{
		
						Map sqlColumnDefinitions = null;
					
				createPropertyAttribute(
					"ImageFailedRecord", 
					"actionType",  
					null,
					"ImageFailedActionType",
					de.hybris.platform.jalo.type.AttributeDescriptor.READ_FLAG|de.hybris.platform.jalo.type.AttributeDescriptor.WRITE_FLAG|de.hybris.platform.jalo.type.AttributeDescriptor.OPTIONAL_FLAG|de.hybris.platform.jalo.type.AttributeDescriptor.REMOVE_FLAG|de.hybris.platform.jalo.type.AttributeDescriptor.SEARCH_FLAG,
					null,
					sqlColumnDefinitions
				);
			
	}
	
	public void single_createattr_ImageFailedRecord_aliyunUrl() throws JaloBusinessException
	{
		
						Map sqlColumnDefinitions = null;
					
				createPropertyAttribute(
					"ImageFailedRecord", 
					"aliyunUrl",  
					null,
					"java.lang.String",
					de.hybris.platform.jalo.type.AttributeDescriptor.READ_FLAG|de.hybris.platform.jalo.type.AttributeDescriptor.WRITE_FLAG|de.hybris.platform.jalo.type.AttributeDescriptor.OPTIONAL_FLAG|de.hybris.platform.jalo.type.AttributeDescriptor.REMOVE_FLAG|de.hybris.platform.jalo.type.AttributeDescriptor.SEARCH_FLAG,
					null,
					sqlColumnDefinitions
				);
			
	}
	
	public void single_createattr_ImageFailedRecord_location() throws JaloBusinessException
	{
		
						Map sqlColumnDefinitions = null;
					
				createPropertyAttribute(
					"ImageFailedRecord", 
					"location",  
					null,
					"java.lang.String",
					de.hybris.platform.jalo.type.AttributeDescriptor.READ_FLAG|de.hybris.platform.jalo.type.AttributeDescriptor.WRITE_FLAG|de.hybris.platform.jalo.type.AttributeDescriptor.OPTIONAL_FLAG|de.hybris.platform.jalo.type.AttributeDescriptor.REMOVE_FLAG|de.hybris.platform.jalo.type.AttributeDescriptor.SEARCH_FLAG,
					null,
					sqlColumnDefinitions
				);
			
	}
	
	public void single_createattr_ImageFailedRecord_status() throws JaloBusinessException
	{
		
						Map sqlColumnDefinitions = null;
					
				createPropertyAttribute(
					"ImageFailedRecord", 
					"status",  
					null,
					"java.lang.String",
					de.hybris.platform.jalo.type.AttributeDescriptor.READ_FLAG|de.hybris.platform.jalo.type.AttributeDescriptor.WRITE_FLAG|de.hybris.platform.jalo.type.AttributeDescriptor.OPTIONAL_FLAG|de.hybris.platform.jalo.type.AttributeDescriptor.REMOVE_FLAG|de.hybris.platform.jalo.type.AttributeDescriptor.SEARCH_FLAG,
					null,
					sqlColumnDefinitions
				);
			
	}
	
	public void single_createattr_ImageUploadedLog_imagePK() throws JaloBusinessException
	{
		
						Map sqlColumnDefinitions = null;
					
				createPropertyAttribute(
					"ImageUploadedLog", 
					"imagePK",  
					null,
					"java.lang.String",
					de.hybris.platform.jalo.type.AttributeDescriptor.READ_FLAG|de.hybris.platform.jalo.type.AttributeDescriptor.WRITE_FLAG|de.hybris.platform.jalo.type.AttributeDescriptor.OPTIONAL_FLAG|de.hybris.platform.jalo.type.AttributeDescriptor.REMOVE_FLAG|de.hybris.platform.jalo.type.AttributeDescriptor.SEARCH_FLAG,
					null,
					sqlColumnDefinitions
				);
			
	}
	
	public void single_createattr_ImageUploadedLog_aliyunUrl() throws JaloBusinessException
	{
		
						Map sqlColumnDefinitions = null;
					
				createPropertyAttribute(
					"ImageUploadedLog", 
					"aliyunUrl",  
					null,
					"java.lang.String",
					de.hybris.platform.jalo.type.AttributeDescriptor.READ_FLAG|de.hybris.platform.jalo.type.AttributeDescriptor.WRITE_FLAG|de.hybris.platform.jalo.type.AttributeDescriptor.OPTIONAL_FLAG|de.hybris.platform.jalo.type.AttributeDescriptor.REMOVE_FLAG|de.hybris.platform.jalo.type.AttributeDescriptor.SEARCH_FLAG,
					null,
					sqlColumnDefinitions
				);
			
	}
	
	public void single_createattr_Product_chemicalInfo() throws JaloBusinessException
	{
		
						Map sqlColumnDefinitions = null;
					
				createPropertyAttribute(
					"Product", 
					"chemicalInfo",  
					null,
					"java.lang.String",
					de.hybris.platform.jalo.type.AttributeDescriptor.READ_FLAG|de.hybris.platform.jalo.type.AttributeDescriptor.WRITE_FLAG|de.hybris.platform.jalo.type.AttributeDescriptor.OPTIONAL_FLAG|de.hybris.platform.jalo.type.AttributeDescriptor.REMOVE_FLAG,
					null,
					sqlColumnDefinitions
				);
			
	}
	
	public void single_createattr_Product_unitCalculateRato() throws JaloBusinessException
	{
		
						Map sqlColumnDefinitions = null;
					
				createPropertyAttribute(
					"Product", 
					"unitCalculateRato",  
					null,
					"java.lang.String",
					de.hybris.platform.jalo.type.AttributeDescriptor.READ_FLAG|de.hybris.platform.jalo.type.AttributeDescriptor.WRITE_FLAG|de.hybris.platform.jalo.type.AttributeDescriptor.OPTIONAL_FLAG|de.hybris.platform.jalo.type.AttributeDescriptor.REMOVE_FLAG,
					null,
					sqlColumnDefinitions
				);
			
	}
	
	public void single_createattr_Product_anonymousDisplayPrice() throws JaloBusinessException
	{
		
						Map sqlColumnDefinitions = null;
					
				createPropertyAttribute(
					"Product", 
					"anonymousDisplayPrice",  
					null,
					"java.lang.Boolean",
					de.hybris.platform.jalo.type.AttributeDescriptor.READ_FLAG|de.hybris.platform.jalo.type.AttributeDescriptor.WRITE_FLAG|de.hybris.platform.jalo.type.AttributeDescriptor.REMOVE_FLAG|de.hybris.platform.jalo.type.AttributeDescriptor.SEARCH_FLAG|de.hybris.platform.jalo.type.AttributeDescriptor.PRIMITIVE_FLAG,
					null,
					sqlColumnDefinitions
				);
			
	}
	
	public void single_createattr_StockLevel_preOrderReleaseDay() throws JaloBusinessException
	{
		
						Map sqlColumnDefinitions = null;
					
				createPropertyAttribute(
					"StockLevel", 
					"preOrderReleaseDay",  
					null,
					"java.lang.Integer",
					de.hybris.platform.jalo.type.AttributeDescriptor.READ_FLAG|de.hybris.platform.jalo.type.AttributeDescriptor.WRITE_FLAG|de.hybris.platform.jalo.type.AttributeDescriptor.OPTIONAL_FLAG|de.hybris.platform.jalo.type.AttributeDescriptor.REMOVE_FLAG|de.hybris.platform.jalo.type.AttributeDescriptor.SEARCH_FLAG,
					null,
					sqlColumnDefinitions
				);
			
	}
	


	/**
	 * Generated by hybris Platform.
	 */
	@Override
	protected final void performCreateObjects( final ManagerEJB manager, Map params ) throws JaloBusinessException
	{
		// performCreateObjects
	
	
		createEnumerationValues(
			"SwatchColorEnum",
			true,
			Arrays.asList( new String[] {
			
				"BLACK",
				"BLUE",
				"BROWN",
				"GREEN",
				"GREY",
				"ORANGE",
				"PINK",
				"PURPLE",
				"RED",
				"SILVER",
				"WHITE",
				"YELLOW"
			} )
		);
	
		createEnumerationValues(
			"ImageFailedActionType",
			true,
			Arrays.asList( new String[] {
			
				"ADD",
				"DEL"
			} )
		);
	
				{
				Map customPropsMap = new HashMap();
				
				setItemTypeProperties(
					"ApparelProduct",
					false,
					true,
					true,
					null,
					customPropsMap
				);
				}
			
			single_setAttributeProperties_ApparelProduct_genders();
		
				{
				Map customPropsMap = new HashMap();
				
				setItemTypeProperties(
					"ApparelStyleVariantProduct",
					false,
					true,
					true,
					null,
					customPropsMap
				);
				}
			
			single_setAttributeProperties_ApparelStyleVariantProduct_style();
		
			single_setAttributeProperties_ApparelStyleVariantProduct_swatchColors();
		
				{
				Map customPropsMap = new HashMap();
				
				setItemTypeProperties(
					"ApparelSizeVariantProduct",
					false,
					true,
					true,
					null,
					customPropsMap
				);
				}
			
			single_setAttributeProperties_ApparelSizeVariantProduct_size();
		
				{
				Map customPropsMap = new HashMap();
				
				setItemTypeProperties(
					"ElectronicsColorVariantProduct",
					false,
					true,
					true,
					null,
					customPropsMap
				);
				}
			
			single_setAttributeProperties_ElectronicsColorVariantProduct_color();
		
				{
				Map customPropsMap = new HashMap();
				
				changeMetaType(
					"Media",
					null,
					customPropsMap
				);
				}
			
			single_setAttributeProperties_Media_aliyunUrl();
		
				{
				Map customPropsMap = new HashMap();
				
				setItemTypeProperties(
					"ImageFailedRecord",
					false,
					true,
					true,
					null,
					customPropsMap
				);
				}
			
			single_setAttributeProperties_ImageFailedRecord_fileName();
		
			single_setAttributeProperties_ImageFailedRecord_actionType();
		
			single_setAttributeProperties_ImageFailedRecord_aliyunUrl();
		
			single_setAttributeProperties_ImageFailedRecord_location();
		
			single_setAttributeProperties_ImageFailedRecord_status();
		
				{
				Map customPropsMap = new HashMap();
				
				setItemTypeProperties(
					"ImageUploadedLog",
					false,
					true,
					true,
					null,
					customPropsMap
				);
				}
			
			single_setAttributeProperties_ImageUploadedLog_imagePK();
		
			single_setAttributeProperties_ImageUploadedLog_aliyunUrl();
		
				{
				Map customPropsMap = new HashMap();
				
				changeMetaType(
					"Product",
					null,
					customPropsMap
				);
				}
			
			single_setAttributeProperties_Product_chemicalInfo();
		
			single_setAttributeProperties_Product_unitCalculateRato();
		
				{
				Map customPropsMap = new HashMap();
				
				changeMetaType(
					"Product",
					null,
					customPropsMap
				);
				}
			
			single_setAttributeProperties_Product_anonymousDisplayPrice();
		
				{
				Map customPropsMap = new HashMap();
				
				changeMetaType(
					"StockLevel",
					null,
					customPropsMap
				);
				}
			
			single_setAttributeProperties_StockLevel_preOrderReleaseDay();
		
				setDefaultProperties(
					"GenderList",
					true,
					true,
					null
				);
			
				setDefaultProperties(
					"SwatchColorSet",
					true,
					true,
					null
				);
			
				setDefaultProperties(
					"SwatchColorEnum",
					true,
					true,
					null
				);
			
				setDefaultProperties(
					"ImageFailedActionType",
					true,
					true,
					null
				);
			
	}


		
						public void single_setAttributeProperties_ApparelProduct_genders() throws JaloBusinessException
						{
							
							
							
							Map customPropsMap = new HashMap();
							
							setAttributeProperties(
								"ApparelProduct", 
								"genders",
								false, 
								null,
								null,
								null,
								true,
								true,
								null,
								customPropsMap,
								null
							);
						}
					
						public void single_setAttributeProperties_ApparelStyleVariantProduct_style() throws JaloBusinessException
						{
							
							
							
							Map customPropsMap = new HashMap();
							
							setAttributeProperties(
								"ApparelStyleVariantProduct", 
								"style",
								false, 
								null,
								null,
								null,
								true,
								true,
								"VariantAttributeDescriptor",
								customPropsMap,
								null
							);
						}
					
						public void single_setAttributeProperties_ApparelStyleVariantProduct_swatchColors() throws JaloBusinessException
						{
							
							
							
							Map customPropsMap = new HashMap();
							
							setAttributeProperties(
								"ApparelStyleVariantProduct", 
								"swatchColors",
								false, 
								null,
								null,
								null,
								true,
								true,
								null,
								customPropsMap,
								null
							);
						}
					
						public void single_setAttributeProperties_ApparelSizeVariantProduct_size() throws JaloBusinessException
						{
							
							
							
							Map customPropsMap = new HashMap();
							
							setAttributeProperties(
								"ApparelSizeVariantProduct", 
								"size",
								false, 
								null,
								null,
								null,
								true,
								true,
								"VariantAttributeDescriptor",
								customPropsMap,
								null
							);
						}
					
						public void single_setAttributeProperties_ElectronicsColorVariantProduct_color() throws JaloBusinessException
						{
							
							
							
							Map customPropsMap = new HashMap();
							
							setAttributeProperties(
								"ElectronicsColorVariantProduct", 
								"color",
								false, 
								null,
								null,
								null,
								true,
								true,
								"VariantAttributeDescriptor",
								customPropsMap,
								null
							);
						}
					
						public void single_setAttributeProperties_Media_aliyunUrl() throws JaloBusinessException
						{
							
							
							
							Map customPropsMap = new HashMap();
							
							setAttributeProperties(
								"Media", 
								"aliyunUrl",
								false, 
								null,
								null,
								null,
								true,
								true,
								null,
								customPropsMap,
								null
							);
						}
					
						public void single_setAttributeProperties_ImageFailedRecord_fileName() throws JaloBusinessException
						{
							
							
							
							Map customPropsMap = new HashMap();
							
							setAttributeProperties(
								"ImageFailedRecord", 
								"fileName",
								false, 
								null,
								null,
								null,
								true,
								true,
								null,
								customPropsMap,
								null
							);
						}
					
						public void single_setAttributeProperties_ImageFailedRecord_actionType() throws JaloBusinessException
						{
							
							
							
							Map customPropsMap = new HashMap();
							
							setAttributeProperties(
								"ImageFailedRecord", 
								"actionType",
								false, 
								null,
								null,
								null,
								true,
								true,
								null,
								customPropsMap,
								null
							);
						}
					
						public void single_setAttributeProperties_ImageFailedRecord_aliyunUrl() throws JaloBusinessException
						{
							
							
							
							Map customPropsMap = new HashMap();
							
							setAttributeProperties(
								"ImageFailedRecord", 
								"aliyunUrl",
								false, 
								null,
								null,
								null,
								true,
								true,
								null,
								customPropsMap,
								null
							);
						}
					
						public void single_setAttributeProperties_ImageFailedRecord_location() throws JaloBusinessException
						{
							
							
							
							Map customPropsMap = new HashMap();
							
							setAttributeProperties(
								"ImageFailedRecord", 
								"location",
								false, 
								null,
								null,
								null,
								true,
								true,
								null,
								customPropsMap,
								null
							);
						}
					
						public void single_setAttributeProperties_ImageFailedRecord_status() throws JaloBusinessException
						{
							
							
							
							Map customPropsMap = new HashMap();
							
							setAttributeProperties(
								"ImageFailedRecord", 
								"status",
								false, 
								null,
								null,
								null,
								true,
								true,
								null,
								customPropsMap,
								null
							);
						}
					
						public void single_setAttributeProperties_ImageUploadedLog_imagePK() throws JaloBusinessException
						{
							
							
							
							Map customPropsMap = new HashMap();
							
							setAttributeProperties(
								"ImageUploadedLog", 
								"imagePK",
								false, 
								null,
								null,
								null,
								true,
								true,
								null,
								customPropsMap,
								null
							);
						}
					
						public void single_setAttributeProperties_ImageUploadedLog_aliyunUrl() throws JaloBusinessException
						{
							
							
							
							Map customPropsMap = new HashMap();
							
							setAttributeProperties(
								"ImageUploadedLog", 
								"aliyunUrl",
								false, 
								null,
								null,
								null,
								true,
								true,
								null,
								customPropsMap,
								null
							);
						}
					
						public void single_setAttributeProperties_Product_chemicalInfo() throws JaloBusinessException
						{
							
							
							
							Map customPropsMap = new HashMap();
							
							setAttributeProperties(
								"Product", 
								"chemicalInfo",
								false, 
								null,
								null,
								null,
								true,
								true,
								null,
								customPropsMap,
								null
							);
						}
					
						public void single_setAttributeProperties_Product_unitCalculateRato() throws JaloBusinessException
						{
							
							
							
							Map customPropsMap = new HashMap();
							
							setAttributeProperties(
								"Product", 
								"unitCalculateRato",
								false, 
								null,
								null,
								null,
								true,
								true,
								null,
								customPropsMap,
								null
							);
						}
					
						public void single_setAttributeProperties_Product_anonymousDisplayPrice() throws JaloBusinessException
						{
							
							
							
							Map customPropsMap = new HashMap();
							
							setAttributeProperties(
								"Product", 
								"anonymousDisplayPrice",
								false, 
								Boolean.FALSE,
								"Boolean.FALSE",
								null,
								true,
								true,
								null,
								customPropsMap,
								null
							);
						}
					
						public void single_setAttributeProperties_StockLevel_preOrderReleaseDay() throws JaloBusinessException
						{
							
							
							
							Map customPropsMap = new HashMap();
							
							setAttributeProperties(
								"StockLevel", 
								"preOrderReleaseDay",
								false, 
								null,
								null,
								null,
								true,
								true,
								null,
								customPropsMap,
								null
							);
						}
					
}

	