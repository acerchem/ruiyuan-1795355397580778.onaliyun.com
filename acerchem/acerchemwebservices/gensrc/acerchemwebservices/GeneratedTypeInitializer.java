

package acerchemwebservices;

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
			"ProductExpressUpdateCleanerCronJob",
			"CronJob",
			com.acerchem.jalo.expressupdate.cron.ProductExpressUpdateCleanerCronJob.class,
			null,
			false,
			null,
			false
		);
	
		createItemType(
			"OrderStatusUpdateCleanerCronJob",
			"CronJob",
			com.acerchem.jalo.expressupdate.cron.OrderStatusUpdateCleanerCronJob.class,
			null,
			false,
			null,
			false
		);
	
		createItemType(
			"OldCartRemovalCronJob",
			"CronJob",
			com.acerchem.jalo.OldCartRemovalCronJob.class,
			null,
			false,
			null,
			false
		);
	
		createCollectionType(
			"BaseSiteCollection",
			"BaseSite",
			CollectionType.COLLECTION
		);
	
	}

	/**
	 * Generated by hybris Platform.
	 */
	@Override
	protected final void performModifyTypes( final ManagerEJB manager, Map params ) throws JaloBusinessException
	{
		// performModifyTypes
	

	
	
				single_createattr_ProductExpressUpdateCleanerCronJob_queueTimeLimit();
			
				single_createattr_OrderStatusUpdateCleanerCronJob_queueTimeLimit();
			
				single_createattr_OldCartRemovalCronJob_sites();
			
				single_createattr_OldCartRemovalCronJob_cartRemovalAge();
			
				single_createattr_OldCartRemovalCronJob_anonymousCartRemovalAge();
			

	}

	
	public void single_createattr_ProductExpressUpdateCleanerCronJob_queueTimeLimit() throws JaloBusinessException
	{
		
						Map sqlColumnDefinitions = null;
					
				createPropertyAttribute(
					"ProductExpressUpdateCleanerCronJob", 
					"queueTimeLimit",  
					null,
					"java.lang.Integer",
					de.hybris.platform.jalo.type.AttributeDescriptor.READ_FLAG|de.hybris.platform.jalo.type.AttributeDescriptor.WRITE_FLAG|de.hybris.platform.jalo.type.AttributeDescriptor.OPTIONAL_FLAG|de.hybris.platform.jalo.type.AttributeDescriptor.REMOVE_FLAG|de.hybris.platform.jalo.type.AttributeDescriptor.SEARCH_FLAG,
					null,
					sqlColumnDefinitions
				);
			
	}
	
	public void single_createattr_OrderStatusUpdateCleanerCronJob_queueTimeLimit() throws JaloBusinessException
	{
		
						Map sqlColumnDefinitions = null;
					
				createPropertyAttribute(
					"OrderStatusUpdateCleanerCronJob", 
					"queueTimeLimit",  
					null,
					"java.lang.Integer",
					de.hybris.platform.jalo.type.AttributeDescriptor.READ_FLAG|de.hybris.platform.jalo.type.AttributeDescriptor.WRITE_FLAG|de.hybris.platform.jalo.type.AttributeDescriptor.OPTIONAL_FLAG|de.hybris.platform.jalo.type.AttributeDescriptor.REMOVE_FLAG|de.hybris.platform.jalo.type.AttributeDescriptor.SEARCH_FLAG,
					null,
					sqlColumnDefinitions
				);
			
	}
	
	public void single_createattr_OldCartRemovalCronJob_sites() throws JaloBusinessException
	{
		
						Map sqlColumnDefinitions = null;
					
				createPropertyAttribute(
					"OldCartRemovalCronJob", 
					"sites",  
					null,
					"BaseSiteCollection",
					de.hybris.platform.jalo.type.AttributeDescriptor.READ_FLAG|de.hybris.platform.jalo.type.AttributeDescriptor.WRITE_FLAG|de.hybris.platform.jalo.type.AttributeDescriptor.OPTIONAL_FLAG|de.hybris.platform.jalo.type.AttributeDescriptor.REMOVE_FLAG|de.hybris.platform.jalo.type.AttributeDescriptor.SEARCH_FLAG,
					null,
					sqlColumnDefinitions
				);
			
	}
	
	public void single_createattr_OldCartRemovalCronJob_cartRemovalAge() throws JaloBusinessException
	{
		
						Map sqlColumnDefinitions = null;
					
				createPropertyAttribute(
					"OldCartRemovalCronJob", 
					"cartRemovalAge",  
					null,
					"java.lang.Integer",
					de.hybris.platform.jalo.type.AttributeDescriptor.READ_FLAG|de.hybris.platform.jalo.type.AttributeDescriptor.WRITE_FLAG|de.hybris.platform.jalo.type.AttributeDescriptor.OPTIONAL_FLAG|de.hybris.platform.jalo.type.AttributeDescriptor.REMOVE_FLAG|de.hybris.platform.jalo.type.AttributeDescriptor.SEARCH_FLAG,
					null,
					sqlColumnDefinitions
				);
			
	}
	
	public void single_createattr_OldCartRemovalCronJob_anonymousCartRemovalAge() throws JaloBusinessException
	{
		
						Map sqlColumnDefinitions = null;
					
				createPropertyAttribute(
					"OldCartRemovalCronJob", 
					"anonymousCartRemovalAge",  
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
	
	
				{
				Map customPropsMap = new HashMap();
				
				setItemTypeProperties(
					"ProductExpressUpdateCleanerCronJob",
					false,
					true,
					true,
					null,
					customPropsMap
				);
				}
			
			single_setAttributeProperties_ProductExpressUpdateCleanerCronJob_queueTimeLimit();
		
				{
				Map customPropsMap = new HashMap();
				
				setItemTypeProperties(
					"OrderStatusUpdateCleanerCronJob",
					false,
					true,
					true,
					null,
					customPropsMap
				);
				}
			
			single_setAttributeProperties_OrderStatusUpdateCleanerCronJob_queueTimeLimit();
		
				{
				Map customPropsMap = new HashMap();
				
				setItemTypeProperties(
					"OldCartRemovalCronJob",
					false,
					true,
					true,
					null,
					customPropsMap
				);
				}
			
			single_setAttributeProperties_OldCartRemovalCronJob_sites();
		
			single_setAttributeProperties_OldCartRemovalCronJob_cartRemovalAge();
		
			single_setAttributeProperties_OldCartRemovalCronJob_anonymousCartRemovalAge();
		
				setDefaultProperties(
					"BaseSiteCollection",
					true,
					true,
					null
				);
			
	}


		
						public void single_setAttributeProperties_ProductExpressUpdateCleanerCronJob_queueTimeLimit() throws JaloBusinessException
						{
							
							
							
							Map customPropsMap = new HashMap();
							
							setAttributeProperties(
								"ProductExpressUpdateCleanerCronJob", 
								"queueTimeLimit",
								false, 
								Integer.valueOf(1440),
								"Integer.valueOf(1440)",
								null,
								true,
								true,
								null,
								customPropsMap,
								null
							);
						}
					
						public void single_setAttributeProperties_OrderStatusUpdateCleanerCronJob_queueTimeLimit() throws JaloBusinessException
						{
							
							
							
							Map customPropsMap = new HashMap();
							
							setAttributeProperties(
								"OrderStatusUpdateCleanerCronJob", 
								"queueTimeLimit",
								false, 
								Integer.valueOf(1440),
								"Integer.valueOf(1440)",
								null,
								true,
								true,
								null,
								customPropsMap,
								null
							);
						}
					
						public void single_setAttributeProperties_OldCartRemovalCronJob_sites() throws JaloBusinessException
						{
							
							
							
							Map customPropsMap = new HashMap();
							
							setAttributeProperties(
								"OldCartRemovalCronJob", 
								"sites",
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
					
						public void single_setAttributeProperties_OldCartRemovalCronJob_cartRemovalAge() throws JaloBusinessException
						{
							
							
							
							Map customPropsMap = new HashMap();
							
							setAttributeProperties(
								"OldCartRemovalCronJob", 
								"cartRemovalAge",
								false, 
								Integer.valueOf(2419200),
								"Integer.valueOf(2419200)",
								null,
								true,
								true,
								null,
								customPropsMap,
								null
							);
						}
					
						public void single_setAttributeProperties_OldCartRemovalCronJob_anonymousCartRemovalAge() throws JaloBusinessException
						{
							
							
							
							Map customPropsMap = new HashMap();
							
							setAttributeProperties(
								"OldCartRemovalCronJob", 
								"anonymousCartRemovalAge",
								false, 
								Integer.valueOf(1209600),
								"Integer.valueOf(1209600)",
								null,
								true,
								true,
								null,
								customPropsMap,
								null
							);
						}
					
}

	