/*
 * [y] hybris Platform
 *
 * Copyright (c) 2017 SAP SE or an SAP affiliate company.  All rights reserved.
 *
 * This software is the confidential and proprietary information of SAP
 * ("Confidential Information"). You shall not disclose such Confidential
 * Information and shall use it only in accordance with the terms of the
 * license agreement you entered into with SAP.
 */
package com.acerchem.actions.user;

import javax.annotation.Resource;

import org.apache.log4j.Logger;

import com.acerchem.core.event.ApproveCustomerEvent;
import com.acerchem.core.service.AcerchemStockService;
import com.hybris.cockpitng.actions.ActionContext;
import com.hybris.cockpitng.actions.ActionResult;
import com.hybris.cockpitng.actions.CockpitAction;
import com.hybris.cockpitng.engine.impl.AbstractComponentWidgetAdapterAware;

import de.hybris.platform.basecommerce.model.site.BaseSiteModel;
import de.hybris.platform.commerceservices.enums.CustomerType;
import de.hybris.platform.commerceservices.event.AbstractCommerceUserEvent;
import de.hybris.platform.core.model.user.CustomerModel;
import de.hybris.platform.processengine.BusinessProcessService;
import de.hybris.platform.servicelayer.event.EventService;
import de.hybris.platform.servicelayer.i18n.CommonI18NService;
import de.hybris.platform.servicelayer.model.ModelService;
import de.hybris.platform.site.BaseSiteService;
import de.hybris.platform.store.services.BaseStoreService;

public class EmployeeApproveUserAction extends AbstractComponentWidgetAdapterAware implements CockpitAction<CustomerModel, Object>
{
	private static final Logger LOG = Logger.getLogger(EmployeeApproveUserAction.class);
	private static final String CURRENT_OBJECT = "currentObject";
	
	@Resource
	BusinessProcessService businessProcessService;
	
	@Resource
	private BaseStoreService baseStoreService;
	
	@Resource
	private ModelService modelService;
	
	@Resource
	private AcerchemStockService acerchemStockService;
	
	@Resource
	private EventService eventService;
	
	@Resource
	private BaseSiteService baseSiteService;
	
	@Resource
	private CommonI18NService commonI18NService;
	
	public BaseSiteService getBaseSiteService() {
		return baseSiteService;
	}

	public void setBaseSiteService(BaseSiteService baseSiteService) {
		this.baseSiteService = baseSiteService;
	}

	public CommonI18NService getCommonI18NService() {
		return commonI18NService;
	}

	public void setCommonI18NService(CommonI18NService commonI18NService) {
		this.commonI18NService = commonI18NService;
	}

	public BaseStoreService getBaseStoreService() {
		return baseStoreService;
	}

	public void setBaseStoreService(BaseStoreService baseStoreService) {
		this.baseStoreService = baseStoreService;
	}

	public EventService getEventService() {
		return eventService;
	}

	public void setEventService(EventService eventService) {
		this.eventService = eventService;
	
	}
	public AcerchemStockService getAcerchemStockService() {
		return acerchemStockService;
	}

	public void setAcerchemStockService(AcerchemStockService acerchemStockService) {
		this.acerchemStockService = acerchemStockService;
	}

	public BusinessProcessService getBusinessProcessService() {
		return businessProcessService;
	}

	public void setBusinessProcessService(BusinessProcessService businessProcessService) {
		this.businessProcessService = businessProcessService;
	}


	@Override
	public ActionResult<Object> perform(ActionContext<CustomerModel> ctx) {
		// TODO Auto-generated method stub
		CustomerModel customerModel = (CustomerModel) ctx.getData();
		if(customerModel!=null&&!CustomerType.APPROVED.equals(customerModel.getType()))
		{
			LOG.info("===========CustomerModel start==========" + customerModel.getUid());
			getEventService().publishEvent(initializeEvent(new ApproveCustomerEvent(), customerModel));
			customerModel.setLoginDisabled(false);
			customerModel.setUnAudited(false);
			customerModel.setType(CustomerType.APPROVED);
			modelService.save(customerModel);
			LOG.info("===========CustomerModel end==========");
		}
		return new ActionResult<Object>("success");
	}
	
	protected AbstractCommerceUserEvent initializeEvent(final AbstractCommerceUserEvent event, final CustomerModel customerModel)
	{
		LOG.info("===========getBaseStoreForUid start=========="+getBaseStoreService().getBaseStoreForUid("ingredients4u"));
		LOG.info("===========getBaseSiteForUID start=========="+getBaseSiteService().getBaseSiteForUID("ingredients4u"));
		event.setBaseStore(getBaseStoreService().getBaseStoreForUid("ingredients4u"));
		event.setSite(getBaseSiteService().getBaseSiteForUID("ingredients4u"));
		event.setCustomer(customerModel);
		event.setLanguage(getCommonI18NService().getCurrentLanguage());
		event.setCurrency(getCommonI18NService().getCurrentCurrency());
		return event;
	}
	
}
