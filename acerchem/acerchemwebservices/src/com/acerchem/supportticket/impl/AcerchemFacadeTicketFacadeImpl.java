/*
 * [y] hybris Platform
 *
 * Copyright (c) 2017 SAP SE or an SAP affiliate company.
 * All rights reserved.
 *
 * This software is the confidential and proprietary information of SAP
 * ("Confidential Information"). You shall not disclose such Confidential
 * Information and shall use it only in accordance with the terms of the
 * license agreement you entered into with SAP.
 */
package com.acerchem.supportticket.impl;

import de.hybris.platform.customerticketingfacades.customerticket.DefaultCustomerTicketingFacade;
import de.hybris.platform.customerticketingfacades.data.TicketData;
import de.hybris.platform.enumeration.EnumerationService;
import de.hybris.platform.servicelayer.config.ConfigurationService;
import de.hybris.platform.servicelayer.dto.converter.Converter;
import de.hybris.platform.ticket.model.CsTicketModel;
import de.hybris.platform.ticket.service.TicketService;
import de.hybris.platform.ticketsystem.data.CsTicketParameter;
import javax.annotation.Resource;
import com.acerchem.core.service.impl.AcerChemSupportTicketServiceImpl;
import com.acerchem.supportticket.AcerchemFacadeTicketSupportFacade;


/**
 * interface that holds the operations for ticketing system
 */
public class AcerchemFacadeTicketFacadeImpl extends DefaultCustomerTicketingFacade  implements AcerchemFacadeTicketSupportFacade
{
	Converter<CsTicketParameter, CsTicketModel> defaultTicketParameterConverter;
	TicketService ticketService;
	EnumerationService enumerationService;
	ConfigurationService configurationService;
	@Resource                    
	AcerChemSupportTicketServiceImpl ticketDaoImpl;
	


	@Override
	public TicketData createTicket(TicketData ticketData) {
		
		final CsTicketModel ticket;
		final CsTicketParameter ticketParameter = createCsTicketParameter(ticketData);
		ticketParameter.setProductId(ticketData.getProductId());
		ticketParameter.setProductName(ticketData.getProductName());
		ticketParameter.setYourname(ticketData.getYourname());
		ticketParameter.setTelephone(ticketData.getTelephone());
		ticketParameter.setAddress(ticketData.getAddress());
		ticketParameter.setEmail(ticketData.getEmail());
		
		ticket = ticketDaoImpl.createTicket(ticketParameter);
		ticketData.setId(ticket.getTicketID());
		return ticketData;
		
	}


	
	
	public Converter<CsTicketParameter, CsTicketModel> getDefaultTicketParameterConverter() {
		return defaultTicketParameterConverter;
	}





	public void setDefaultTicketParameterConverter(
			Converter<CsTicketParameter, CsTicketModel> defaultTicketParameterConverter) {
		this.defaultTicketParameterConverter = defaultTicketParameterConverter;
	}





	public TicketService getTicketService() {
		return ticketService;
	}





	public void setTicketService(TicketService ticketService) {
		this.ticketService = ticketService;
	}





	public EnumerationService getEnumerationService() {
		return enumerationService;
	}





	public void setEnumerationService(EnumerationService enumerationService) {
		this.enumerationService = enumerationService;
	}





	public ConfigurationService getConfigurationService() {
		return configurationService;
	}





	public void setConfigurationService(ConfigurationService configurationService) {
		this.configurationService = configurationService;
	}









}
