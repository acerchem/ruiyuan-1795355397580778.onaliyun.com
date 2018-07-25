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
package com.acerchem.v2.controller;

import de.hybris.platform.cms2.exceptions.CMSItemNotFoundException;
import de.hybris.platform.commercefacades.customer.CustomerFacade;
import de.hybris.platform.commerceservices.search.flexiblesearch.PagedFlexibleSearchService;
import de.hybris.platform.commerceservices.search.flexiblesearch.data.SortQueryData;
import de.hybris.platform.commerceservices.search.pagedata.PageableData;
import de.hybris.platform.commerceservices.search.pagedata.SearchPageData;
import de.hybris.platform.commercewebservicescommons.dto.TicketListWsDTO;
import de.hybris.platform.commercewebservicescommons.dto.TicketWsDTO;
import de.hybris.platform.converters.Converters;

import com.acerchem.supportticket.impl.AcerchemFacadeTicketFacadeImpl;
import de.hybris.platform.customerticketingfacades.TicketFacade;
import de.hybris.platform.customerticketingfacades.data.StatusData;
import de.hybris.platform.customerticketingfacades.data.TicketCategory;
import de.hybris.platform.customerticketingfacades.data.TicketData;
import de.hybris.platform.servicelayer.dto.converter.Converter;
import de.hybris.platform.servicelayer.session.SessionService;
import de.hybris.platform.servicelayer.user.UserService;
import de.hybris.platform.servicelayer.util.ServicesUtil;
import de.hybris.platform.site.BaseSiteService;
import de.hybris.platform.ticket.model.CsTicketModel;
import de.hybris.platform.webservicescommons.cache.CacheControl;
import de.hybris.platform.webservicescommons.cache.CacheControlDirective;
import de.hybris.platform.webservicescommons.errors.exceptions.WebserviceValidationException;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.apache.commons.lang.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.sap.security.core.server.csi.XSSEncoder;

/**
 * Controller for Customer Support tickets.
 */
@Controller
@RequestMapping(value = "/{baseSiteId}/tickets")
public class TicketsController extends BaseCommerceController {
	
	public static final int MAX_PAGE_LIMIT = 100;
	private static final String SUPPORT_TICKET_CODE_PATH_VARIABLE_PATTERN = "{ticketId:.*}";

	@Resource(name = "sessionService")
	private SessionService sessionService;
	@Resource(name = "webDefaultTicketFacade")
	AcerchemFacadeTicketFacadeImpl webDefaultTicketFacade;
	@Resource(name = "defaultTicketFacade")
	private TicketFacade ticketFacade;
	@Resource(name = "customerFacade")
	private CustomerFacade customerFacade;
	@Resource
	private UserService userService;
	@Resource
	private BaseSiteService baseSiteService;
	@Resource
	private Converter<CsTicketModel, TicketData> ticketListConverter;
	@Resource
	private PagedFlexibleSearchService pagedFlexibleSearchService;
	
	
	@RequestMapping(method = RequestMethod.GET)
	@CacheControl(directive = CacheControlDirective.PUBLIC, maxAge = 300)
	@ResponseBody
	public TicketListWsDTO supportTickets(@RequestParam(value = "page", defaultValue = "0") final int pageNumber)
	{
		 ServicesUtil.validateParameterNotNull(userService.getCurrentUser(), "Customer must not be null");
		 ServicesUtil.validateParameterNotNull(baseSiteService.getCurrentBaseSite(), "Store must not be null");
		 Map<String, Object> queryParams = new HashMap<String, Object>();
		 queryParams.put("user", userService.getCurrentUser());
		 queryParams.put("baseSite", baseSiteService.getCurrentBaseSite());
		 queryParams.put("sessionId", sessionService.getCurrentSession().getSessionId());
		 
		 final boolean isAnonymousUser = userService.isAnonymousUser(userService.getCurrentUser());
		 
		 String sql1 = "SELECT {pk} FROM {CsTicket} WHERE {customer} = ?user AND {baseSite} = ?baseSite ";
		 if(isAnonymousUser)
		 {
			 sql1 += " AND {sessionId} = ?sessionId ";
		 }
		 sql1 += " ORDER BY {modifiedtime} DESC";
		 
		 SortQueryData sortQueryData = new SortQueryData();
		 sortQueryData.setSortCode("byDate");
		 sortQueryData.setQuery(sql1);
		 SortQueryData byDateSql= sortQueryData;
		 
		 List<SortQueryData> sortQueries = Arrays.asList(new SortQueryData[] {byDateSql});
		 
		 PageableData pageableData = new PageableData();
		 pageableData.setCurrentPage(pageNumber);
		 pageableData.setPageSize(MAX_PAGE_LIMIT);
		 pageableData.setSort("byDate");
		 
		 final SearchPageData<CsTicketModel> TicketModels = pagedFlexibleSearchService.search(sortQueries, "byDate", queryParams, pageableData);
		 
		 final TicketListWsDTO ticketListWsDTO = new TicketListWsDTO();
		 ticketListWsDTO.setTickets(Converters.convertAll(TicketModels.getResults(), ticketListConverter));
		 ticketListWsDTO.setSorts(TicketModels.getSorts());
		 ticketListWsDTO.setPagination(TicketModels.getPagination());
		 
		 return ticketListWsDTO;
	}
	
	@RequestMapping(value = "/addTicket", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	public void addSupportTicket(
			@RequestParam final String subject,
			@RequestParam final String yourname,
			@RequestParam final String telephone,
			@RequestParam final String address,
			@RequestParam final String email,
			@RequestParam final String message,
			@RequestParam final String productId,
			@RequestParam final String productName)
	{
		if(yourname==null||telephone==null||address==null||email==null||message==null ||yourname.equals("")||telephone.equals("")||address.equals("")||email.equals("")||message.equals(""))
		{
			 throw new WebserviceValidationException("Information must not be null");
		}
		
		final TicketData ticketData = new TicketData();
		final StatusData status = new StatusData();
		status.setId("OPEN");
		ticketData.setStatus(status);
		ticketData.setCustomerId(customerFacade.getCurrentCustomerUid());
		ticketData.setSubject(subject);
		//ticketData.setSubject("Home Advisory");
		ticketData.setMessage(message);
		ticketData.setYourname(yourname);
		ticketData.setTelephone(telephone);
		ticketData.setEmail(email);
		ticketData.setAddress(address);
		ticketData.setProductId(productId);
		ticketData.setProductName(productName);
		ticketData.setTicketCategory(TicketCategory.ENQUIRY);
		

		webDefaultTicketFacade.createTicket(ticketData);
		
	}
	
	@RequestMapping(value = "/updateTicket/"+ SUPPORT_TICKET_CODE_PATH_VARIABLE_PATTERN, method = RequestMethod.POST)
	@CacheControl(directive = CacheControlDirective.PUBLIC, maxAge = 300)
	@ResponseBody
	public TicketWsDTO updateSupportTicket(
			@PathVariable final String ticketId,
			@RequestParam final String ticketStatus,
			@RequestParam final String message) throws CMSItemNotFoundException
	{
		if(StringUtils.isBlank(ticketId)||message==null)
		{
			throw new WebserviceValidationException("Information must not be null");
		}
		
		final TicketData ticketData = new TicketData();
		ticketData.setId(ticketId);
		final StatusData status = new StatusData();
		status.setId(ticketStatus);
		ticketData.setStatus(status);
		ticketData.setCustomerId(customerFacade.getCurrentCustomerUid());
		ticketData.setSubject("Home Advisory");
		ticketData.setMessage(message);
		ticketData.setTicketCategory(TicketCategory.ENQUIRY);

		ticketFacade.updateTicket(ticketData);
		return getSupportTicket(ticketId);
	}
	
	@RequestMapping(value = "/updateTicket/"+ SUPPORT_TICKET_CODE_PATH_VARIABLE_PATTERN, method = RequestMethod.GET)
	@CacheControl(directive = CacheControlDirective.PUBLIC, maxAge = 300)
	@ResponseBody
	public TicketWsDTO getSupportTicket(@PathVariable("ticketId") final String ticketId) throws CMSItemNotFoundException {
		TicketWsDTO ticket=new TicketWsDTO();
		
		try {
			TicketData ticketData = ticketFacade.getTicket(XSSEncoder.encodeHTML(ticketId));
			ticket.setSubject(ticketData.getSubject());
			ticket.setCreationDate(ticketData.getCreationDate());
			ticket.setLastModificationDate(ticketData.getLastModificationDate());;
			ticket.setStatus(ticketData.getStatus());
			ticket.setTicketEvents(ticketData.getTicketEvents());
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return ticket;
	}
	
}