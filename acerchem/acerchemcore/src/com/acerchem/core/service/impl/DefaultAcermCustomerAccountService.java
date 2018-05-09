package com.acerchem.core.service.impl;

import com.acerchem.core.event.SendEmployeeEmailEvent;

import de.hybris.platform.commerceservices.customer.DuplicateUidException;
import de.hybris.platform.commerceservices.customer.impl.DefaultCustomerAccountService;
import de.hybris.platform.commerceservices.event.RegisterEvent;
import de.hybris.platform.core.model.user.CustomerModel;
import de.hybris.platform.servicelayer.event.EventService;

public class DefaultAcermCustomerAccountService extends DefaultCustomerAccountService {
	
	
	private EventService eventService;
	
	public EventService getEventService() {
		return eventService;
	}


	public void setEventService(EventService eventService) {
		this.eventService = eventService;
	}




	@Override
	public void register(final CustomerModel customerModel, final String password) throws DuplicateUidException
	{
		registerCustomer(customerModel, password);
		getEventService().publishEvent(initializeEvent(new RegisterEvent(), customerModel));
		getEventService().publishEvent(initializeEvent(new SendEmployeeEmailEvent(), customerModel));
	}
}
