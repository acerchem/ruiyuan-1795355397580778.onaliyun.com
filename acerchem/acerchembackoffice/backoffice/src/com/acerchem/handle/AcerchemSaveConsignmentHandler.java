/**
 *
 */
package com.acerchem.handle;

import java.util.Date;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import com.hybris.cockpitng.config.jaxb.wizard.CustomType;
import com.hybris.cockpitng.widgets.configurableflow.FlowActionHandler;
import com.hybris.cockpitng.widgets.configurableflow.FlowActionHandlerAdapter;

import de.hybris.platform.core.model.order.OrderModel;
import de.hybris.platform.ordersplitting.model.ConsignmentModel;
import de.hybris.platform.servicelayer.model.ModelService;


@Component
public class AcerchemSaveConsignmentHandler implements FlowActionHandler
{
	private static final Logger LOG = Logger.getLogger(AcerchemSaveConsignmentHandler.class);
	
	@Resource
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
		final ConsignmentModel consignmentModel = adapter.getWidgetInstanceManager().getModel().getValue("consignment",
				ConsignmentModel.class);
		final OrderModel order = modelService.get(consignmentModel);
		order.setCode(String.valueOf(new Date().getTime()));
		modelService.save(order);
		LOG.info("--------------END AcerchemSaveConsignmentHandler-----------");
	}
	

}
