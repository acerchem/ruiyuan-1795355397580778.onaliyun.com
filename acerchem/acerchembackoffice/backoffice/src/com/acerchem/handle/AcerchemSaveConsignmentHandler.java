/**
 *
 */
package com.acerchem.handle;

import java.util.Date;
import java.util.Map;

import org.apache.log4j.Logger;

import com.hybris.cockpitng.config.jaxb.wizard.CustomType;
import com.hybris.cockpitng.widgets.configurableflow.FlowActionHandler;
import com.hybris.cockpitng.widgets.configurableflow.FlowActionHandlerAdapter;

import de.hybris.platform.basecommerce.enums.ConsignmentStatus;
import de.hybris.platform.ordersplitting.model.ConsignmentEntryModel;
import de.hybris.platform.ordersplitting.model.ConsignmentModel;
import de.hybris.platform.servicelayer.model.ModelService;


public class AcerchemSaveConsignmentHandler implements FlowActionHandler
{
	private static final Logger LOG = Logger.getLogger(AcerchemSaveConsignmentHandler.class);
	
	private ModelService modelService;
	
	public ModelService getModelService() {
		return modelService;
	}

	public void setModelService(ModelService modelService) {
		this.modelService = modelService;
	}



	@Override
	public void perform(CustomType customType, FlowActionHandlerAdapter adapter, Map<String, String> parameters) {
		// TODO Auto-generated method stub
		LOG.info("--------------START AcerchemSaveConsignmentHandler-----------");
		final ConsignmentEntryModel consignmentEntryModel =  adapter.getWidgetInstanceManager().getModel().getValue("newConsignmentEntry",
				ConsignmentEntryModel.class);
		consignmentEntryModel.getPk();
		consignmentEntryModel.getOrderEntry().getDeliveryPointOfService().getWarehouses();
		final ConsignmentModel consignmentModel = modelService.create(ConsignmentModel.class);
		consignmentModel.setCode(String.valueOf(new Date().getTime()));
		consignmentModel.setStatus(ConsignmentStatus.DELIVERING);
		if(consignmentEntryModel.getOrderEntry() != null){
			if(consignmentEntryModel.getOrderEntry().getOrder() != null){
				if(consignmentEntryModel.getOrderEntry().getOrder().getDeliveryAddress() != null){
					consignmentModel.setShippingAddress(consignmentEntryModel.getOrderEntry().getOrder().getDeliveryAddress());
				}
				consignmentModel.setOrder(consignmentEntryModel.getOrderEntry().getOrder());
			}
		}
		if(consignmentEntryModel.getOrderEntry() != null && consignmentEntryModel.getOrderEntry().getDeliveryPointOfService() != null && consignmentEntryModel.getOrderEntry().getDeliveryPointOfService().getWarehouses() != null){
			consignmentModel.setWarehouse(consignmentEntryModel.getOrderEntry().getDeliveryPointOfService().getWarehouses().get(0));
		}
		
		modelService.save(consignmentModel);
		consignmentEntryModel.setConsignment(consignmentModel);
		modelService.save(consignmentEntryModel);
		adapter.done();
		LOG.info("--------------END AcerchemSaveConsignmentHandler-----------");
	}
	

}
