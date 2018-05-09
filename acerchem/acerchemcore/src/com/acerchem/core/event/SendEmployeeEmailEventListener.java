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
package com.acerchem.core.event;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Required;

import de.hybris.platform.acceleratorservices.site.AbstractAcceleratorSiteEventListener;
import de.hybris.platform.basecommerce.model.site.BaseSiteModel;
import de.hybris.platform.commerceservices.enums.SiteChannel;
import de.hybris.platform.commerceservices.model.process.StoreFrontCustomerProcessModel;
import de.hybris.platform.processengine.BusinessProcessService;
import de.hybris.platform.servicelayer.model.ModelService;
import de.hybris.platform.servicelayer.util.ServicesUtil;
import de.hybris.platform.store.services.BaseStoreService;



/**
 * Listener for customer registration events.
 */
public class SendEmployeeEmailEventListener extends AbstractAcceleratorSiteEventListener<SendEmployeeEmailEvent>
{
	private static final Logger LOG = Logger.getLogger(SendEmployeeEmailEventListener.class);

	private ModelService modelService;
	private BusinessProcessService businessProcessService;
	
	private BaseStoreService baseStoreService;
	
	public BaseStoreService getBaseStoreService() {
		return baseStoreService;
	}

	public void setBaseStoreService(BaseStoreService baseStoreService) {
		this.baseStoreService = baseStoreService;
	}

	protected BusinessProcessService getBusinessProcessService()
	{
		return businessProcessService;
	}

	@Required
	public void setBusinessProcessService(final BusinessProcessService businessProcessService)
	{
		this.businessProcessService = businessProcessService;
	}

	/**
	 * @return the modelService
	 */
	protected ModelService getModelService()
	{
		return modelService;
	}

	/**
	 * @param modelService
	 *           the modelService to set
	 */
	@Required
	public void setModelService(final ModelService modelService)
	{
		this.modelService = modelService;
	}

	@Override
	protected void onSiteEvent(final SendEmployeeEmailEvent sendeEmployeeEmailEvent)
	{
		LOG.info("-------------------------onSiteEvent start------------------");
		final StoreFrontCustomerProcessModel storeFrontCustomerProcessModel = (StoreFrontCustomerProcessModel) getBusinessProcessService()
				.createProcess(
						"approveCustomerEmailProcess-" + sendeEmployeeEmailEvent.getCustomer().getUid() + "-" + System.currentTimeMillis(),
						"approveCustomerEmailProcess");
		storeFrontCustomerProcessModel.setSite(sendeEmployeeEmailEvent.getSite());
		storeFrontCustomerProcessModel.setCustomer(sendeEmployeeEmailEvent.getCustomer());
		storeFrontCustomerProcessModel.setLanguage(sendeEmployeeEmailEvent.getLanguage());
		storeFrontCustomerProcessModel.setCurrency(sendeEmployeeEmailEvent.getCurrency());
		storeFrontCustomerProcessModel.setStore(sendeEmployeeEmailEvent.getBaseStore());
		getModelService().save(storeFrontCustomerProcessModel);
		getBusinessProcessService().startProcess(storeFrontCustomerProcessModel);
		LOG.info("-------------------------onSiteEvent end------------------");
	}

	@Override
	protected SiteChannel getSiteChannelForEvent(final SendEmployeeEmailEvent event)
	{
		LOG.info("-------------------------onSiteEvent start------------------"+event.getSite());
		LOG.info("-------------------------onSiteEvent start------------------"+event.getBaseStore());
		LOG.info("-------------------------onSiteEvent start------------------"+event.getCurrency());
		LOG.info("-------------------------onSiteEvent start------------------"+event.getLanguage());
		LOG.info("-------------------------onSiteEvent start------------------"+event.getCustomer());
		final BaseSiteModel site = event.getSite();
		ServicesUtil.validateParameterNotNullStandardMessage("event.order.site", site);
		return site.getChannel();
	}
}
