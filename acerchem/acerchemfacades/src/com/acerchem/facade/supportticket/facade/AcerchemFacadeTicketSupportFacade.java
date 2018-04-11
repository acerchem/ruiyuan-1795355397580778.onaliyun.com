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
package com.acerchem.facade.supportticket.facade;

import de.hybris.platform.customerticketingfacades.data.TicketData;

/**
 * interface that holds the operations for ticketing system
 */
public interface AcerchemFacadeTicketSupportFacade
{
	/**
	 * Creates ticket.
	 *
	 * @param ticketData
	 * @return TicketData or null
	 */
	TicketData createTicket(TicketData ticketData);


}
