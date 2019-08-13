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
package com.acerchem.storefront.controllers.supporttickets.customerticketingaddon.controllers.pages;

import com.acerchem.facade.supportticket.facade.impl.AcerchemFacadeTicketFacadeImpl;
import com.acerchem.storefront.checkout.steps.validation.impl.AddSupportTicketValidator;
import com.acerchem.storefront.checkout.steps.validation.impl.SupportTicketForm;
import com.google.common.collect.Maps;
import com.sap.security.core.server.csi.XSSEncoder;
import de.hybris.platform.acceleratorservices.email.EmailService;
import de.hybris.platform.acceleratorservices.model.email.EmailAddressModel;
import de.hybris.platform.acceleratorservices.model.email.EmailMessageModel;
import de.hybris.platform.acceleratorstorefrontcommons.breadcrumb.Breadcrumb;
import de.hybris.platform.acceleratorstorefrontcommons.breadcrumb.ResourceBreadcrumbBuilder;
import de.hybris.platform.acceleratorstorefrontcommons.constants.WebConstants;
import de.hybris.platform.acceleratorstorefrontcommons.controllers.ThirdPartyConstants;
import de.hybris.platform.acceleratorstorefrontcommons.controllers.pages.AbstractSearchPageController;
import de.hybris.platform.acceleratorstorefrontcommons.controllers.util.GlobalMessages;
import de.hybris.platform.cms2.exceptions.CMSItemNotFoundException;
import de.hybris.platform.commercefacades.customer.CustomerFacade;
import de.hybris.platform.commercefacades.order.CartFacade;
import de.hybris.platform.commercefacades.order.data.CartData;
import de.hybris.platform.commercefacades.product.data.ProductData;
import de.hybris.platform.commercefacades.user.data.CustomerData;
import de.hybris.platform.commerceservices.search.flexiblesearch.PagedFlexibleSearchService;
import de.hybris.platform.commerceservices.search.flexiblesearch.data.SortQueryData;
import de.hybris.platform.commerceservices.search.pagedata.PageableData;
import de.hybris.platform.commerceservices.search.pagedata.SearchPageData;
import de.hybris.platform.converters.Converters;
import de.hybris.platform.core.model.product.ProductModel;
import de.hybris.platform.core.model.user.UserModel;
import de.hybris.platform.customerticketingfacades.TicketFacade;
import de.hybris.platform.customerticketingfacades.data.StatusData;
import de.hybris.platform.customerticketingfacades.data.TicketCategory;
import de.hybris.platform.customerticketingfacades.data.TicketData;
import de.hybris.platform.servicelayer.dto.converter.Converter;
import de.hybris.platform.servicelayer.search.FlexibleSearchQuery;
import de.hybris.platform.servicelayer.search.FlexibleSearchService;
import de.hybris.platform.servicelayer.search.SearchResult;
import de.hybris.platform.servicelayer.user.UserService;
import de.hybris.platform.servicelayer.util.ServicesUtil;
import de.hybris.platform.site.BaseSiteService;
import de.hybris.platform.ticket.dao.TicketDao;
import de.hybris.platform.ticket.model.CsTicketModel;
import de.hybris.platform.ticket.service.UnsupportedAttachmentException;
import de.hybris.platform.util.Config;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.Resource;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Controller for Customer Support tickets.
 */
@Controller
@RequestMapping("/account")
public class AccountSupportTicketsPageController extends AbstractSearchPageController {
	private static final Logger LOG = Logger.getLogger(AccountSupportTicketsPageController.class);

	// CMS Pages
	private static final String SUPPORT_TICKET_CODE_PATH_VARIABLE_PATTERN = "{ticketId:.*}";
	private static String REDIRECT_TO_SUPPORT_TICKETS_PAGE = REDIRECT_PREFIX + "/account/support-tickets";

	private long maxUploadSizeValue = Long.MAX_VALUE;

	@Resource(name = "ticketFacadeImpl")
	AcerchemFacadeTicketFacadeImpl ticketFacadeImpl;

	@Resource(name = "accountBreadcrumbBuilder")
	private ResourceBreadcrumbBuilder accountBreadcrumbBuilder;

	@Resource(name = "defaultTicketFacade")
	private TicketFacade ticketFacade;

	@Resource(name = "cartFacade")
	private CartFacade cartFacade;

	@Resource(name = "customerFacade")
	private CustomerFacade customerFacade;

	@Resource(name = "validator")
	private Validator validator;

	@Resource(name = "addSupportTicketValidator")
	private AddSupportTicketValidator addSupportTicketValidator;

	@Resource(name = "allowedUploadedFormats")
	private String allowedUploadedFormats;

	private final String[] DISALLOWED_FIELDS = new String[] {};

	@InitBinder
	public void initBinder(final WebDataBinder binder) {
		binder.setDisallowedFields(DISALLOWED_FIELDS);
	}
	
	@Resource
	private UserService userService;
	
	@Resource
	private BaseSiteService baseSiteService;
	
	@Resource
	private Converter<CsTicketModel, TicketData> ticketListConverter;
	
	@Resource
	private TicketDao ticketDao;
	
	@Resource
	private PagedFlexibleSearchService pagedFlexibleSearchService;

	@Resource(name = "emailService")
	private EmailService emailService;

	/**
	 * Lists all tickets
	 *
	 * @param pageNumber
	 * @param showMode
	 * @param sortCode
	 * @param model
	 * @param ticketAdded
	 * @return View String
	 * @throws CMSItemNotFoundException
	 */
	@RequestMapping(value = "/support-tickets", method = RequestMethod.GET)
	public String supportTickets(@RequestParam(value = "page", defaultValue = "0") final int pageNumber,
			@RequestParam(value = "show", defaultValue = "Page") final ShowMode showMode,
			@RequestParam(value = "sort", required = false) final String sortCode,
			@RequestParam(value = "ticketAdded", required = false, defaultValue = "false") final boolean ticketAdded,
			final Model model) throws CMSItemNotFoundException {

		return getListView(pageNumber, showMode, sortCode, ticketAdded, model);
	}
	
	@RequestMapping(value = "/personal-tickets", method = RequestMethod.GET)
	public String personalTickets(@RequestParam(value = "page", defaultValue = "0") final int pageNumber,
			@RequestParam(value = "show", defaultValue = "Page") final ShowMode showMode,
			@RequestParam(value = "sort", required = false) final String sortCode,
			@RequestParam(value = "ticketAdded", required = false, defaultValue = "false") final boolean ticketAdded,
			final Model model) throws CMSItemNotFoundException {

		getListView(pageNumber, showMode, sortCode, ticketAdded, model);
		model.addAttribute("nowPage","tickets");
		return "pages/account/accountPersonalTicketsPage";
		
	}

	@InitBinder
	public void init(final WebDataBinder binder) {
		binder.setBindEmptyMultipartFiles(false);
	}

	/**
	 * Used for retrieving page to create a customer support ticket.
	 *
	 * @param model
	 * @return View String
	 * @throws CMSItemNotFoundException
	 */
	@RequestMapping(value = "/add-support-ticket", method = RequestMethod.GET)
	public String addSupportTicket(final Model model,String productId,String productName,String email,String telephone) throws CMSItemNotFoundException {
		return getAddView(model,productId,productName,email,telephone,null);
	}
	
	@RequestMapping(value = "/add-personal-ticket", method = RequestMethod.GET)
	public String addPersonalTicket(final Model model) throws CMSItemNotFoundException {
		model.addAttribute("nowPage","tickets");
		getAddView(model,null,null,null,null,null);
		return "pages/account/accountAddPersonalTicketPage";
	}

	/**
	 * Creates a ticket.
	 *
	 * @param supportTicketForm
	 * @param bindingResult
	 * @return View String
	 * @throws CMSItemNotFoundException
	 */
	@RequestMapping(value = "/add-support-ticket", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public String addSupportTicket(@RequestParam(value = "page", defaultValue = "0") final int pageNumber,
			@RequestParam(value = "show", defaultValue = "Page") final ShowMode showMode,
			@RequestParam(value = "sort", required = false) final String sortCode,
			@RequestParam(value = "ticketAdded", required = false, defaultValue = "false") final boolean ticketAdded,
			final SupportTicketForm supportTicketForm, final BindingResult bindingResult,Model model)
			throws CMSItemNotFoundException {
		addSupportTicketValidator.validate(supportTicketForm, bindingResult);
		
		if (bindingResult.hasErrors()) {
			final List<Map<String, String>> list = buildErrorMessagesMap(bindingResult);
			list.add(buildMessageMap(CustomerticketingaddonConstants.FORM_GLOBAL_ERROR_KEY,CustomerticketingaddonConstants.FORM_GLOBAL_ERROR));

			return getAddView(model,supportTicketForm.getProductId(),supportTicketForm.getProductName(),null,null,supportTicketForm);
		}

		try {
			ticketFacadeImpl.createTicket(this.populateTicketData(supportTicketForm));

		} catch (final UnsupportedAttachmentException usAttEx) {
			LOG.error(usAttEx.getMessage(), usAttEx);
			final Map<String, String> map = Maps.newHashMap();
			map.put(CustomerticketingaddonConstants.SUPPORT_TICKET_TRY_LATER,
					getMessageSource().getMessage(
							CustomerticketingaddonConstants.TEXT_SUPPORT_TICKETING_ATTACHMENT_BLOCK_LISTED,
							new Object[] { allowedUploadedFormats }, getI18nService().getCurrentLocale()));
			return getAddView(model,supportTicketForm.getProductId(),supportTicketForm.getProductName(),null,null,supportTicketForm);
		} catch (final RuntimeException rEX) {
			final Map<String, String> map = Maps.newHashMap();
			LOG.error(rEX.getMessage(), rEX);

			map.put(CustomerticketingaddonConstants.SUPPORT_TICKET_TRY_LATER,
					getMessageSource().getMessage(CustomerticketingaddonConstants.TEXT_SUPPORT_TICKETING_TRY_LATER,
							null, getI18nService().getCurrentLocale()));
			return getAddView(model,supportTicketForm.getProductId(),supportTicketForm.getProductName(),null,null,supportTicketForm);
		}
		return getListView(pageNumber, showMode, sortCode, ticketAdded, model);
	}
	
	@RequestMapping(value = "/add-personal-ticket", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public String addPersonalTicket(@RequestParam(value = "page", defaultValue = "0") final int pageNumber,
			@RequestParam(value = "show", defaultValue = "Page") final ShowMode showMode,
			@RequestParam(value = "sort", required = false) final String sortCode,
			@RequestParam(value = "ticketAdded", required = false, defaultValue = "false") final boolean ticketAdded,
			final SupportTicketForm supportTicketForm, final BindingResult bindingResult,Model model)
			throws CMSItemNotFoundException {
		
		model.addAttribute("nowPage","tickets");
		addSupportTicketValidator.validate(supportTicketForm, bindingResult);

		if (bindingResult.hasErrors()) {
			final List<Map<String, String>> list = buildErrorMessagesMap(bindingResult);
			list.add(buildMessageMap(CustomerticketingaddonConstants.FORM_GLOBAL_ERROR_KEY,CustomerticketingaddonConstants.FORM_GLOBAL_ERROR));

			getAddView(model,supportTicketForm.getProductId(),supportTicketForm.getProductName(),null,null,supportTicketForm);
			return "pages/account/accountAddPersonalTicketPage";
		}

		try {
			ticketFacadeImpl.createTicket(this.populateTicketData(supportTicketForm));

		} catch (final UnsupportedAttachmentException usAttEx) {
			LOG.error(usAttEx.getMessage(), usAttEx);
			final Map<String, String> map = Maps.newHashMap();
			map.put(CustomerticketingaddonConstants.SUPPORT_TICKET_TRY_LATER,
					getMessageSource().getMessage(
							CustomerticketingaddonConstants.TEXT_SUPPORT_TICKETING_ATTACHMENT_BLOCK_LISTED,
							new Object[] { allowedUploadedFormats }, getI18nService().getCurrentLocale()));
			getAddView(model,supportTicketForm.getProductId(),supportTicketForm.getProductName(),null,null,supportTicketForm);
			return "pages/account/accountAddPersonalTicketPage";
		} catch (final RuntimeException rEX) {
			final Map<String, String> map = Maps.newHashMap();
			LOG.error(rEX.getMessage(), rEX);

			map.put(CustomerticketingaddonConstants.SUPPORT_TICKET_TRY_LATER,
					getMessageSource().getMessage(CustomerticketingaddonConstants.TEXT_SUPPORT_TICKETING_TRY_LATER,
							null, getI18nService().getCurrentLocale()));
			getAddView(model,supportTicketForm.getProductId(),supportTicketForm.getProductName(),null,null,supportTicketForm);
			return "pages/account/accountAddPersonalTicketPage";
		}
		
		getListView(pageNumber, showMode, sortCode, ticketAdded, model);
		return "pages/account/accountPersonalTicketsPage";
	}
	
	private String getListView(int pageNumber, ShowMode showMode, String sortCode, boolean ticketAdded, Model model)
			throws CMSItemNotFoundException {
		promotionItem(model);
		storeCmsPageInModel(model, getContentPageForLabelOrId(CustomerticketingaddonConstants.SUPPORT_TICKETS_PAGE));
		setUpMetaDataForContentPage(model,
				getContentPageForLabelOrId(CustomerticketingaddonConstants.SUPPORT_TICKETS_PAGE));
		model.addAttribute(WebConstants.BREADCRUMBS_KEY, accountBreadcrumbBuilder
				.getBreadcrumbs(CustomerticketingaddonConstants.TEXT_SUPPORT_TICKETING_HISTORY));
		model.addAttribute(ThirdPartyConstants.SeoRobots.META_ROBOTS, ThirdPartyConstants.SeoRobots.NOINDEX_NOFOLLOW);

		final PageableData pageableData = createPageableData(pageNumber, 5, sortCode, showMode);
		
		 ticketDao.findTicketsByCustomerOrderByModifiedTime(userService.getCurrentUser(), baseSiteService.getCurrentBaseSite(), pageableData);

		UserModel userModel = userService.getCurrentUser();
		if ("anonymous".equals(userModel.getUid())){
//			(SupportTicketForm)model.asMap().get("supportTicketForm");
			EmailMessageModel emailMessageModel = this.createEmailMessage("测试");
			this.sendEmail(emailMessageModel);
			return "pages/account/accountSendSuccessPage";
		}

		 ServicesUtil.validateParameterNotNull(userService.getCurrentUser(), "Customer must not be null");
		 ServicesUtil.validateParameterNotNull(baseSiteService.getCurrentBaseSite(), "Store must not be null");
		 Map<String, Object> queryParams = new HashMap();
		 queryParams.put("user", userService.getCurrentUser());
		 queryParams.put("baseSite", baseSiteService.getCurrentBaseSite());
		 queryParams.put("sessionId", getSessionService().getCurrentSession().getSessionId());
		 
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
		 
		 String sql2 = "SELECT {pk} FROM {CsTicket} WHERE {customer} = ?user AND {baseSite} = ?baseSite ";
		 if(isAnonymousUser)
		 {
			 sql2 += " AND {sessionId} = ?sessionId ";
		 }
		 sql2 += " ORDER BY {ticketID} DESC";
		 
		 SortQueryData sortQueryData2 = new SortQueryData();
		 sortQueryData2.setSortCode("byTicketId");
		 sortQueryData2.setQuery(sql2);
		 SortQueryData byTicketIdSql= sortQueryData2;
		 
		 List<SortQueryData> sortQueries = Arrays.asList(new SortQueryData[] {byDateSql,byTicketIdSql});
		 final SearchPageData<CsTicketModel> TicketModels = pagedFlexibleSearchService.search(sortQueries, "byDate", queryParams, pageableData);
		
		final SearchPageData<TicketData> searchPageData = convertPageData(TicketModels, ticketListConverter);
		
		populateModel(model, searchPageData, showMode);

		if (ticketAdded) {
			GlobalMessages.addConfMessage(model, CustomerticketingaddonConstants.TEXT_SUPPORT_TICKETING_ADDED);
		}
		return "pages/account/accountSupportTicketsPage";
	}
	
	protected <S, T> SearchPageData<T> convertPageData(final SearchPageData<S> source, final Converter<S, T> converter)
	{
		final SearchPageData<T> result = new SearchPageData<T>();
		result.setPagination(source.getPagination());
		result.setSorts(source.getSorts());
		result.setResults(Converters.convertAll(source.getResults(), converter));
		return result;
	}

	private String getAddView(Model model,String productId,String productName,String email,String telephone,SupportTicketForm SupportTicketForm) throws CMSItemNotFoundException {
		promotionItem(model);
		final CustomerData customerData = customerFacade.getCurrentCustomer();

		storeCmsPageInModel(model, getContentPageForLabelOrId(CustomerticketingaddonConstants.ADD_SUPPORT_TICKET_PAGE));
		setUpMetaDataForContentPage(model,
				getContentPageForLabelOrId(CustomerticketingaddonConstants.ADD_SUPPORT_TICKET_PAGE));

		model.addAttribute(WebConstants.BREADCRUMBS_KEY,
				getBreadcrumbs(CustomerticketingaddonConstants.TEXT_SUPPORT_TICKETING_ADD));
		model.addAttribute(ThirdPartyConstants.SeoRobots.META_ROBOTS, ThirdPartyConstants.SeoRobots.NOINDEX_NOFOLLOW);
		
		if(SupportTicketForm!=null)
		{
			model.addAttribute(CustomerticketingaddonConstants.SUPPORT_TICKET_FORM, SupportTicketForm);
		}
		else
		{
			SupportTicketForm stf = new SupportTicketForm();
			stf.setYourname(customerData.getName() == null ||customerData.getName().equals("Anonymous")? "" : customerData.getName());
			stf.setEmail(customerData.getUid() == null ||customerData.getUid().equals("anonymous")? "" : customerData.getUid());
			stf.setProductId(productId==null||productId.equals("")?"":productId);
			stf.setProductName(productName==null||productName.equals("")?"":productName);
			if(telephone!=null&&!telephone.equals(""))
			{
				stf.setTelephone(telephone);
			}
			if(email!=null&&!email.equals(""))
			{
				stf.setEmail(email);
			}
			model.addAttribute(CustomerticketingaddonConstants.SUPPORT_TICKET_FORM, stf);
		}
		model.addAttribute(CustomerticketingaddonConstants.MAX_UPLOAD_SIZE, Long.valueOf(maxUploadSizeValue));
		model.addAttribute(CustomerticketingaddonConstants.MAX_UPLOAD_SIZE_MB,
				FileUtils.byteCountToDisplaySize(maxUploadSizeValue));

		try {
			model.addAttribute(CustomerticketingaddonConstants.SUPPORT_TICKET_ASSOCIATED_OBJECTS,
					ticketFacade.getAssociatedToObjects());
			model.addAttribute(CustomerticketingaddonConstants.SUPPORT_TICKET_CATEGORIES,
					ticketFacade.getTicketCategories());
		} catch (final UnsupportedOperationException ex) {
			LOG.error(ex.getMessage(), ex);
		}
		return "pages/account/accountAddSupportTicketPage";
	}

	/**
	 * Get Ticket Details.
	 *
	 * @param ticketId
	 * @param model
	 * @param redirectModel
	 * @param ticketUpdated
	 * @return View String
	 * @throws CMSItemNotFoundException
	 */
	@RequestMapping(value = "/support-ticket/" + SUPPORT_TICKET_CODE_PATH_VARIABLE_PATTERN, method = RequestMethod.GET)
	public String getSupportTicket(@PathVariable("ticketId") final String ticketId, final Model model,
								   @RequestParam(value = "ticketUpdated", required = false, defaultValue = "false") final boolean ticketUpdated,
								   final RedirectAttributes redirectModel) throws CMSItemNotFoundException {
		promotionItem(model);
		storeCmsPageInModel(model,getContentPageForLabelOrId(CustomerticketingaddonConstants.UPDATE_SUPPORT_TICKET_PAGE));
		setUpMetaDataForContentPage(model,getContentPageForLabelOrId(CustomerticketingaddonConstants.UPDATE_SUPPORT_TICKET_PAGE));
		model.addAttribute(WebConstants.BREADCRUMBS_KEY,getBreadcrumbs(CustomerticketingaddonConstants.TEXT_SUPPORT_TICKETING_UPDATE));
		model.addAttribute(ThirdPartyConstants.SeoRobots.META_ROBOTS, ThirdPartyConstants.SeoRobots.NOINDEX_NOFOLLOW);
		model.addAttribute(CustomerticketingaddonConstants.SUPPORT_TICKET_FORM, new SupportTicketForm());
		model.addAttribute(CustomerticketingaddonConstants.MAX_UPLOAD_SIZE, Long.valueOf(maxUploadSizeValue));
		model.addAttribute(CustomerticketingaddonConstants.MAX_UPLOAD_SIZE_MB,FileUtils.byteCountToDisplaySize(maxUploadSizeValue));
		try {
			final TicketData ticketData = ticketFacade.getTicket(XSSEncoder.encodeHTML(ticketId));
			model.addAttribute(CustomerticketingaddonConstants.SUPPORT_TICKET_DATA, ticketData);
		} catch (final Exception e) {
			LOG.error("Attempted to load ticket details that does not exist or is not visible", e);
			GlobalMessages.addFlashMessage(redirectModel, GlobalMessages.ERROR_MESSAGES_HOLDER,
					CustomerticketingaddonConstants.TEXT_SUPPORT_TICKETING_TRY_LATER, null);
			return REDIRECT_TO_SUPPORT_TICKETS_PAGE;
		}

		if (ticketUpdated) {
			GlobalMessages.addConfMessage(model, CustomerticketingaddonConstants.TEXT_SUPPORT_TICKETING_ADDED);
		}
		return "pages/account/accountUpdateSupportTicketPage";
	}
	
	@RequestMapping(value = "/personal-ticket/" + SUPPORT_TICKET_CODE_PATH_VARIABLE_PATTERN, method = RequestMethod.GET)
	public String getPersonalTicket(@PathVariable("ticketId") final String ticketId, final Model model,
			@RequestParam(value = "ticketUpdated", required = false, defaultValue = "false") final boolean ticketUpdated,
			final RedirectAttributes redirectModel) throws CMSItemNotFoundException {
		REDIRECT_TO_SUPPORT_TICKETS_PAGE=REDIRECT_PREFIX + "/account/personal-tickets";
		getSupportTicket(ticketId,model,ticketUpdated,redirectModel);
		return "pages/account/accountUpdatePersonalTicketPage";
	}

	/**
	 * Updates a ticket with new information from form.
	 *
	 * @param supportTicketForm
	 * @param bindingResult
	 * @return View String
	 */
	@RequestMapping(value = "/support-ticket/"+ SUPPORT_TICKET_CODE_PATH_VARIABLE_PATTERN, method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public String updateSupportTicket(final SupportTicketForm supportTicketForm,final BindingResult bindingResult,
			@PathVariable("ticketId") final String ticketId, final Model model,
			@RequestParam(value = "ticketUpdated", required = false, defaultValue = "false") final boolean ticketUpdated,
			final RedirectAttributes redirectModel) throws CMSItemNotFoundException
	{
		promotionItem(model);
		addSupportTicketValidator.validate(supportTicketForm, bindingResult);
		if (bindingResult.hasErrors()) {
			final List<Map<String, String>> list = buildErrorMessagesMap(bindingResult);
			list.add(buildMessageMap(CustomerticketingaddonConstants.FORM_GLOBAL_ERROR_KEY,CustomerticketingaddonConstants.FORM_GLOBAL_ERROR));
			return returnPage(model,ticketId);
		}

		try {
			ticketFacade.updateTicket(this.populateTicketData(supportTicketForm));
		} catch (final UnsupportedAttachmentException usAttEx) {
			LOG.error(usAttEx.getMessage(), usAttEx);
			final Map<String, String> map = Maps.newHashMap();

			map.put(CustomerticketingaddonConstants.SUPPORT_TICKET_TRY_LATER,
					getMessageSource().getMessage(
							CustomerticketingaddonConstants.TEXT_SUPPORT_TICKETING_ATTACHMENT_BLOCK_LISTED,
							new Object[] { allowedUploadedFormats }, getI18nService().getCurrentLocale()));
			return returnPage(model,ticketId);
		} catch (final RuntimeException rEx) {
			LOG.error(rEx.getMessage(), rEx);
			final Map<String, String> map = Maps.newHashMap();
			map.put(CustomerticketingaddonConstants.SUPPORT_TICKET_TRY_LATER,
					getMessageSource().getMessage(CustomerticketingaddonConstants.TEXT_SUPPORT_TICKETING_TRY_LATER,
							null, getI18nService().getCurrentLocale()));
			return returnPage(model,ticketId);
		} catch (final Exception e) {
			LOG.error("Attempted to load ticket details that does not exist or is not visible", e);
			GlobalMessages.addFlashMessage(redirectModel, GlobalMessages.ERROR_MESSAGES_HOLDER,CustomerticketingaddonConstants.TEXT_SUPPORT_TICKETING_TRY_LATER, null);
			return REDIRECT_TO_SUPPORT_TICKETS_PAGE;
		}
		
		if (ticketUpdated) {
			GlobalMessages.addConfMessage(model, CustomerticketingaddonConstants.TEXT_SUPPORT_TICKETING_ADDED);
		}
		return returnPage(model,ticketId);
	}
	
	@RequestMapping(value = "/personal-ticket/"+ SUPPORT_TICKET_CODE_PATH_VARIABLE_PATTERN, method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public String updatePersonalTicket(final SupportTicketForm supportTicketForm,final BindingResult bindingResult,
			@PathVariable("ticketId") final String ticketId, final Model model,
			@RequestParam(value = "ticketUpdated", required = false, defaultValue = "false") final boolean ticketUpdated,
			final RedirectAttributes redirectModel) throws CMSItemNotFoundException
	{
		promotionItem(model);
		addSupportTicketValidator.validate(supportTicketForm, bindingResult);
		if (bindingResult.hasErrors()) {
			final List<Map<String, String>> list = buildErrorMessagesMap(bindingResult);
			list.add(buildMessageMap(CustomerticketingaddonConstants.FORM_GLOBAL_ERROR_KEY,CustomerticketingaddonConstants.FORM_GLOBAL_ERROR));
			returnPage(model,ticketId);
			return "pages/account/accountUpdatePersonalTicketPage";
		}

		try {
			ticketFacade.updateTicket(this.populateTicketData(supportTicketForm));
		} catch (final UnsupportedAttachmentException usAttEx) {
			LOG.error(usAttEx.getMessage(), usAttEx);
			final Map<String, String> map = Maps.newHashMap();

			map.put(CustomerticketingaddonConstants.SUPPORT_TICKET_TRY_LATER,
					getMessageSource().getMessage(
							CustomerticketingaddonConstants.TEXT_SUPPORT_TICKETING_ATTACHMENT_BLOCK_LISTED,
							new Object[] { allowedUploadedFormats }, getI18nService().getCurrentLocale()));
		} catch (final RuntimeException rEx) {
			LOG.error(rEx.getMessage(), rEx);
			final Map<String, String> map = Maps.newHashMap();
			map.put(CustomerticketingaddonConstants.SUPPORT_TICKET_TRY_LATER,
					getMessageSource().getMessage(CustomerticketingaddonConstants.TEXT_SUPPORT_TICKETING_TRY_LATER,
							null, getI18nService().getCurrentLocale()));
		} catch (final Exception e) {
			LOG.error("Attempted to load ticket details that does not exist or is not visible", e);
			GlobalMessages.addFlashMessage(redirectModel, GlobalMessages.ERROR_MESSAGES_HOLDER,CustomerticketingaddonConstants.TEXT_SUPPORT_TICKETING_TRY_LATER, null);
			return REDIRECT_PREFIX + "/account/personal-tickets";
		}
		
		if (ticketUpdated) {
			GlobalMessages.addConfMessage(model, CustomerticketingaddonConstants.TEXT_SUPPORT_TICKETING_ADDED);
		}
		returnPage(model,ticketId);
		return "pages/account/accountUpdatePersonalTicketPage";
	}
	
	protected String returnPage(final Model model,@PathVariable("ticketId") final String ticketId) throws CMSItemNotFoundException{
		
		storeCmsPageInModel(model,getContentPageForLabelOrId(CustomerticketingaddonConstants.UPDATE_SUPPORT_TICKET_PAGE));
		setUpMetaDataForContentPage(model,getContentPageForLabelOrId(CustomerticketingaddonConstants.UPDATE_SUPPORT_TICKET_PAGE));
		model.addAttribute(WebConstants.BREADCRUMBS_KEY,getBreadcrumbs(CustomerticketingaddonConstants.TEXT_SUPPORT_TICKETING_UPDATE));
		model.addAttribute(ThirdPartyConstants.SeoRobots.META_ROBOTS, ThirdPartyConstants.SeoRobots.NOINDEX_NOFOLLOW);
		model.addAttribute(CustomerticketingaddonConstants.SUPPORT_TICKET_FORM, new SupportTicketForm());
		model.addAttribute(CustomerticketingaddonConstants.MAX_UPLOAD_SIZE, Long.valueOf(maxUploadSizeValue));
		model.addAttribute(CustomerticketingaddonConstants.MAX_UPLOAD_SIZE_MB,FileUtils.byteCountToDisplaySize(maxUploadSizeValue));
		TicketData ticketData = new TicketData();
		try {
			ticketData = ticketFacade.getTicket(XSSEncoder.encodeHTML(ticketId));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		model.addAttribute(CustomerticketingaddonConstants.SUPPORT_TICKET_DATA, ticketData);
		return "pages/account/accountUpdateSupportTicketPage";
	}
	

	/**
	 * Populated the data from the form bean to ticket data object.
	 *
	 * @param supportTicketForm
	 * @return TicketData
	 */
	protected TicketData populateTicketData(final SupportTicketForm supportTicketForm) {
		final TicketData ticketData = new TicketData();
		if (cartFacade.hasSessionCart()) {
			final CartData cartData = cartFacade.getSessionCart();
			if (!cartData.getEntries().isEmpty()) {
				ticketData.setCartId(cartData.getCode());
			}
		}
		if (StringUtils.isNotBlank(supportTicketForm.getId())) {
			ticketData.setId(supportTicketForm.getId());
		}
		final StatusData status = new StatusData();
		status.setId(supportTicketForm.getStatus());
		ticketData.setStatus(status);
		ticketData.setCustomerId(customerFacade.getCurrentCustomerUid());
		ticketData.setSubject((supportTicketForm.getProductId()==""||supportTicketForm.getProductId()==null)?"Home Advisory":supportTicketForm.getProductId());
		ticketData.setMessage(supportTicketForm.getMessage());
		ticketData.setAssociatedTo(supportTicketForm.getAssociatedTo());
		ticketData.setTicketCategory(supportTicketForm.getTicketCategory());
		ticketData.setAttachments(supportTicketForm.getFiles());
		ticketData.setProductId(supportTicketForm.getProductId());
		ticketData.setProductName(supportTicketForm.getProductName());
		ticketData.setYourname(supportTicketForm.getYourname());
		ticketData.setTelephone(supportTicketForm.getTelephone());
		ticketData.setEmail(supportTicketForm.getEmail());
		ticketData.setAddress(supportTicketForm.getAddress());
		ticketData.setTicketCategory(TicketCategory.ENQUIRY);

		return ticketData;
	}

	protected List<Breadcrumb> getBreadcrumbs(final String breadcrumbCode) {
		final List<Breadcrumb> breadcrumbs = accountBreadcrumbBuilder.getBreadcrumbs(null);
		breadcrumbs.add(new Breadcrumb("/account/support-tickets",
				getMessageSource().getMessage(CustomerticketingaddonConstants.TEXT_SUPPORT_TICKETING_HISTORY, null,
						getI18nService().getCurrentLocale()),
				null));
		breadcrumbs.add(new Breadcrumb("#",
				getMessageSource().getMessage(breadcrumbCode, null, getI18nService().getCurrentLocale()), null));
		return breadcrumbs;
	}

	/**
	 * Build the error message list with map contains the validation error code
	 * and localised message.
	 *
	 * @param bindingResult
	 * @return Map of error code and message
	 */
	protected List<Map<String, String>> buildErrorMessagesMap(final BindingResult bindingResult) {
		return bindingResult.getAllErrors().stream().filter(err -> err.getCode() != null && err.getCode().length() > 0)
				.map(err -> {
					final Map<String, String> map = Maps.newHashMap();
					map.put(err.getCodes()[0].replaceAll("\\.", "-"), err.getDefaultMessage());
					return map;
				}).collect(Collectors.toList());
	}

	/**
	 * Build a map with key and localsed Message.
	 *
	 * @param key
	 *            the render key
	 * @param localisedKey
	 *            the localised message key
	 * @return Map of error code and message
	 */
	protected Map<String, String> buildMessageMap(final String key, final String localisedKey) {
		final Map<String, String> map = Maps.newHashMap();
		map.put(key, getMessageSource().getMessage(localisedKey, null, getI18nService().getCurrentLocale()));

		return map;
	}
	
	@Resource
	private FlexibleSearchService flexibleSearchService;
	@Resource
	private Converter<ProductModel, ProductData> productConverter;
	
	public void promotionItem(final Model model)
	{
		final String SQL = "SELECT PK FROM {"+ProductModel._TYPECODE+"} WHERE {"+ProductModel.PROMOTIONITEM+"} =true ";//limit 1,15
		final FlexibleSearchQuery query = new FlexibleSearchQuery(SQL);
		query.setNeedTotal(false);
		query.setCount(15);
		final SearchResult<ProductModel> result = flexibleSearchService.search(query);
		model.addAttribute("promotionItem", Converters.convertAll(result.getResult(), productConverter));
		
	}



	public EmailMessageModel createEmailMessage(final String emailBody) {
		final List<EmailAddressModel> toEmails = new ArrayList<EmailAddressModel>();
		final List<EmailAddressModel> ccAddress = new ArrayList<EmailAddressModel>();
		String fromEmail = Config.getParameter("mail.from");
		final EmailAddressModel fromAddress = emailService
				.getOrCreateEmailAddressForEmail(fromEmail, "customerservice");
		final EmailAddressModel ccEmailOneAddressModel = emailService.getOrCreateEmailAddressForEmail(
				Config.getParameter("mail.ccAddress.one"), Config.getParameter("mail.ccAddress.displayOneName"));
		toEmails.add(ccEmailOneAddressModel);

		return emailService.createEmailMessage(toEmails, ccAddress, new ArrayList<EmailAddressModel>(),
				fromAddress, fromEmail, "Support Tickets", emailBody, null);
	}


	public boolean sendEmail(EmailMessageModel message){
		return emailService.send(message);
	}
}