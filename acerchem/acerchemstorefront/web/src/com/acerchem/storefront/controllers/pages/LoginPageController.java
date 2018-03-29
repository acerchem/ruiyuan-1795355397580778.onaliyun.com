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
package com.acerchem.storefront.controllers.pages;

import de.hybris.platform.acceleratorservices.storefront.data.MetaElementData;
import de.hybris.platform.acceleratorstorefrontcommons.breadcrumb.ResourceBreadcrumbBuilder;
import de.hybris.platform.acceleratorstorefrontcommons.controllers.pages.AbstractLoginPageController;
import de.hybris.platform.acceleratorstorefrontcommons.controllers.pages.AbstractRegisterPageController;
import de.hybris.platform.acceleratorstorefrontcommons.controllers.util.GlobalMessages;
import de.hybris.platform.acceleratorstorefrontcommons.forms.AddressForm;
import de.hybris.platform.acceleratorstorefrontcommons.forms.GuestForm;
import de.hybris.platform.acceleratorstorefrontcommons.forms.LoginForm;
import de.hybris.platform.cms2.exceptions.CMSItemNotFoundException;
import de.hybris.platform.cms2.model.pages.AbstractPageModel;
import de.hybris.platform.cms2.model.pages.ContentPageModel;
import de.hybris.platform.cms2.servicelayer.services.CMSPageService;
import de.hybris.platform.commercefacades.customer.CustomerFacade;
import de.hybris.platform.commercefacades.i18n.I18NFacade;
import de.hybris.platform.commercefacades.order.CheckoutFacade;
import de.hybris.platform.commercefacades.user.UserFacade;
import de.hybris.platform.commercefacades.user.data.CountryData;
import de.hybris.platform.commercefacades.user.data.RegionData;
import de.hybris.platform.commerceservices.customer.CustomerAccountService;
import de.hybris.platform.commerceservices.customer.DuplicateUidException;
import de.hybris.platform.core.model.c2l.CountryModel;
import de.hybris.platform.core.model.c2l.RegionModel;
import de.hybris.platform.core.model.order.OrderModel;
import de.hybris.platform.core.model.user.AddressModel;
import de.hybris.platform.core.model.user.CustomerModel;
import de.hybris.platform.mobileservices.enums.PhoneType;
import de.hybris.platform.mobileservices.model.text.PhoneNumberModel;
import de.hybris.platform.mobileservices.model.text.UserPhoneNumberModel;
import de.hybris.platform.servicelayer.dto.converter.Converter;
import de.hybris.platform.servicelayer.exceptions.ModelSavingException;
import de.hybris.platform.servicelayer.i18n.CommonI18NService;
import de.hybris.platform.servicelayer.model.ModelService;
import de.hybris.platform.servicelayer.search.FlexibleSearchService;
import de.hybris.platform.servicelayer.search.SearchResult;
import de.hybris.platform.servicelayer.user.UserService;
import de.hybris.platform.tx.Transaction;

import com.acerchem.storefront.controllers.ControllerConstants;
import com.acerchem.storefront.data.CustomRegisterForm;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


/**
 * Login Controller. Handles login and register for the account flow.
 */
@Controller
@RequestMapping(value = "/login")
public class LoginPageController extends AbstractLoginPageController
{
	
	private HttpSessionRequestCache httpSessionRequestCache;

	@Override
	protected String getView()
	{
		return ControllerConstants.Views.Pages.Account.AccountLoginPage;
	}

	@Override
	protected String getSuccessRedirect(final HttpServletRequest request, final HttpServletResponse response)
	{
		if (httpSessionRequestCache.getRequest(request, response) != null)
		{
			return httpSessionRequestCache.getRequest(request, response).getRedirectUrl();
		}
		return "/";
	}

	@Override
	protected AbstractPageModel getCmsPage() throws CMSItemNotFoundException
	{
		return getContentPageForLabelOrId("login");
	}


	@Resource(name = "httpSessionRequestCache")
	public void setHttpSessionRequestCache(final HttpSessionRequestCache accHttpSessionRequestCache)
	{
		this.httpSessionRequestCache = accHttpSessionRequestCache;
	}

	@RequestMapping(method = RequestMethod.GET)
	public String doLogin(@RequestHeader(value = "referer", required = false) final String referer,
			@RequestParam(value = "error", defaultValue = "false") final boolean loginError, final Model model,
			final HttpServletRequest request, final HttpServletResponse response, final HttpSession session)
			throws CMSItemNotFoundException
	{
		if (!loginError)
		{
			storeReferer(referer, request, response);
		}
		return getDefaultLoginPage(loginError, session, model);
	}

	protected void storeReferer(final String referer, final HttpServletRequest request, final HttpServletResponse response)
	{
		if (StringUtils.isNotBlank(referer) && !StringUtils.endsWith(referer, "/login")
				&& StringUtils.contains(referer, request.getServerName()))
		{
			httpSessionRequestCache.saveRequest(request, response);
		}
	}
	
	
	//add by alice
	@Resource
	private UserService userService;
	
	@Resource(name = "modelService")
	private ModelService modelService;
	protected ModelService getModelService()
	{
		return modelService;
	}
	
	@Resource(name = "commonI18NService")
	private CommonI18NService commonI18NService;
	protected CommonI18NService getCommonI18NService()
	{
		return commonI18NService;
	}
	
	@Resource(name = "customerAccountService")
	private CustomerAccountService customerAccountService;
	protected CustomerAccountService getCustomerAccountService()
	{
		return customerAccountService;
	}
	
	@Resource(name = "countryConverter")
	private Converter<CountryModel, CountryData> countryConverter;
	
	@Resource(name = "regionConverter")
	private Converter<RegionModel, RegionData> regionConverter;
	
	@Resource(name = "i18NFacade")
	private I18NFacade i18NFacade;
	
	@Resource(name = "cmsPageService")
	private CMSPageService cmsPageService;
	
	@Resource(name = "acceleratorCheckoutFacade")
	private CheckoutFacade checkoutFacade;
	
	@Resource(name = "userFacade")
	private UserFacade userFacade;
	
	@Resource(name = "accountBreadcrumbBuilder")
	private ResourceBreadcrumbBuilder accountBreadcrumbBuilder;
	
	@Resource(name = "customerFacade")
	private CustomerFacade customerFacade;
	
	@ModelAttribute("countries")
	public Collection<CountryData> getCountries()
	{
		return checkoutFacade.getDeliveryCountries();
	}

	@ModelAttribute("countryDataMap")
	public Map<String, CountryData> getCountryDataMap()
	{
		final Map<String, CountryData> countryDataMap = new HashMap<>();
		for (final CountryData countryData : getCountries())
		{
			countryDataMap.put(countryData.getIsocode(), countryData);
		}
		return countryDataMap;
	}
	
	@RequestMapping(value = "/register", method = RequestMethod.GET)
	public String doRegister(final Model model) throws CMSItemNotFoundException
	{
		model.addAttribute("countryData", checkoutFacade.getDeliveryCountries());
		final AbstractPageModel cmsPage = cmsPageService.getPageForLabelOrId("add-edit-address");
		if (model != null && cmsPage != null)
		{
			model.addAttribute("cmsPage", cmsPage);
			if (cmsPage instanceof ContentPageModel)
			{
				model.addAttribute("pageTitle", getPageTitleResolver().resolveContentPageTitle(cmsPage.getTitle()));
			}
		}
		final List<MetaElementData> metadata = new LinkedList<>();
		metadata.add(createMetaElement("keywords", cmsPageService.getPageForLabelOrId("add-edit-address").getKeywords()));
		metadata.add(createMetaElement("description", cmsPageService.getPageForLabelOrId("add-edit-address").getDescription()));
		model.addAttribute("metatags", metadata);
		
		final CustomRegisterForm CustomRegisterForm = new CustomRegisterForm();
		CustomRegisterForm.setShipAddress(new AddressForm());
		CustomRegisterForm.setContactAddress(new AddressForm());
		
		model.addAttribute(CustomRegisterForm);
		storeCmsPageInModel(model, getCmsPage());
		return ControllerConstants.Views.Pages.Account.AccountRegisterPage;
	}
	
	@Resource(name = "customRegistrationValidator")
	private Validator customRegistrationValidator;
	
	@Resource
	private FlexibleSearchService flexibleSearchService;
	
	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public String doRegister(final CustomRegisterForm form,
			@RequestHeader(value = "referer", required = false) final String referer, 
			final BindingResult bindingResult, final Model model, final HttpServletRequest request,
			final HttpServletResponse response, final RedirectAttributes redirectModel) throws CMSItemNotFoundException
	{
		final Transaction tx = Transaction.current();
		tx.begin();
		boolean success = false;
		try
		{	
			customRegistrationValidator.validate(form, bindingResult);
			
			final String REF_QUERY_PRODUCTROW_START = "SELECT PK FROM {"+CustomerModel._TYPECODE+"} WHERE {"+CustomerModel.UID+"} =?email ";
			final Map<String, Object> params = new HashMap<String, Object>();
			final StringBuilder builder = new StringBuilder(REF_QUERY_PRODUCTROW_START);
			params.put("email", form.getEmail());
			final SearchResult<CustomerModel> emailUser = flexibleSearchService.search(builder.toString(),params);
			
			if(emailUser.getResult().size()>0)
			{
				GlobalMessages.addErrorMessage(model, "register.failed: The user already exists. Do not double register!");
			}
			else if(bindingResult.hasErrors())
			{
				GlobalMessages.addErrorMessage(model, "form.global.error");
			}
			else
			{
				CustomerModel user=RegisterCustomerService(form,model);
				userService.setCurrentUser(user);
				customerAccountService.register(user, form.getPwd());
				getAutoLoginStrategy().login(form.getEmail().toLowerCase(), form.getPwd(), request, response);
				GlobalMessages.addFlashMessage(redirectModel, GlobalMessages.CONF_MESSAGES_HOLDER,"registration.confirmation.message.title");
				success=true;
			}
		}
		catch(Exception exception)
		{
			GlobalMessages.addErrorMessage(model, "register.failed: Please confirm whether the information you filled in is correct!");
			System.out.print("register exception==="+exception);
		}
		finally
		{
			if (success)
			{
				tx.commit();
			}
			else
			{
				tx.rollback();
			}
		}
		
		if(success)
		{
			storeCmsPageInModel(model, getCmsPage());
			return ControllerConstants.Views.Pages.Account.AccountRegisterSuccessPage;
		}
		else
		{
			model.addAttribute("regions", i18NFacade.getRegionsForCountryIso(form.getContactAddress().getCountryIso()));
			model.addAttribute("CustomRegisterForm",form);
			GlobalMessages.addErrorMessage(model, "form.global.error");
			storeCmsPageInModel(model, getCmsPage());
			return ControllerConstants.Views.Pages.Account.AccountRegisterPage;
		}
		
	}
	
	public CustomerModel RegisterCustomerService(final CustomRegisterForm form,final Model model)
	{
		
		final CustomerModel user = new CustomerModel();
		user.setUid(form.getEmail().toLowerCase());
		user.setName(form.getName());
		user.setLoginDisabled(true);
		modelService.save(user);
		
		String shipCountryIso=form.getShipAddress().getCountryIso();
		String shipRegionIso=form.getShipAddress().getRegionIso();
		CountryModel shipCountry=commonI18NService.getCountry(shipCountryIso);
		RegionModel shipRegion=commonI18NService.getRegion(shipCountry,shipRegionIso);
		
		AddressModel am=modelService.create(AddressModel.class);
		am.setContactAddress(false);
		am.setShippingAddress(true);
		am.setVisibleInAddressBook(true);
		am.setLastname(form.getContacts());
		am.setPhone1(form.getMobileNumber());
		am.setCellphone(form.getMobileNumber());
		am.setCountry(shipCountry);
		am.setRegion(shipRegion);
		am.setTown(form.getShipAddress().getTownCity());
		am.setOwner(user);
		
		String contactCountryIso=form.getContactAddress().getCountryIso();
		String contactRegionIso=form.getContactAddress().getRegionIso();
		CountryModel contactCountry=commonI18NService.getCountry(contactCountryIso);
		RegionModel contactRegion=commonI18NService.getRegion(contactCountry,contactRegionIso);
		AddressModel am2=modelService.create(AddressModel.class);
		am2.setContactAddress(true);
		am2.setShippingAddress(false);
		am2.setVisibleInAddressBook(false);
		am2.setLastname(form.getContacts());
		am2.setPhone1(form.getMobileNumber());
		am2.setCellphone(form.getMobileNumber());
		am2.setCountry(contactCountry);
		am2.setRegion(contactRegion);
		am2.setTown(form.getContactAddress().getTownCity());
		am2.setOwner(user);
		
		List<AddressModel> amlist=new ArrayList<AddressModel>();
		amlist.add(am);
		amlist.add(am2);
		
		PhoneNumberModel phone=modelService.create(PhoneNumberModel.class);
		phone.setNumber(form.getMobileNumber());
		
		UserPhoneNumberModel pn=modelService.create(UserPhoneNumberModel.class);
		pn.setPhoneNumber(phone);
		pn.setType(PhoneType.valueOf("MOBILE"));//HOME\OFFICE\MOBILE
		pn.setDefault(true);
		
		PhoneNumberModel tel=modelService.create(PhoneNumberModel.class);
		tel.setNumber(form.getTelephone());
		
		UserPhoneNumberModel pn2=modelService.create(UserPhoneNumberModel.class);
		pn2.setPhoneNumber(tel);
		pn2.setType(PhoneType.valueOf("HOME"));
		pn2.setDefault(false);
		
		List<UserPhoneNumberModel> phoneNumbers=new ArrayList<UserPhoneNumberModel>();
		phoneNumbers.add(pn);
		phoneNumbers.add(pn2);
		
		user.setOriginalUid(form.getEmail());
		
		user.setSessionLanguage(getCommonI18NService().getLanguage(form.getLanguage()));
		user.setSessionCurrency(getCommonI18NService().getCurrency(form.getCurrency()));
		user.setAddresses(amlist);
		user.setPhoneNumbers(phoneNumbers);
		user.setPassword(form.getPwd());
		
		modelService.saveAll(user,pn2,tel,pn,phone,am2,am);
		return user;
	}
	
	@RequestMapping(value = "/getShipRegions", method = RequestMethod.GET)
	public String getShipRegions(@RequestParam("addressCode") final String addressCode,
			@RequestParam("countryIsoCode") final String countryIsoCode, final Model model)
	{
		model.addAttribute("supportedCountries", getCountries());
		model.addAttribute("regions", i18NFacade.getRegionsForCountryIso(countryIsoCode));
		model.addAttribute("country", countryIsoCode);
		final CustomRegisterForm CustomRegisterForm = new CustomRegisterForm();
		CustomRegisterForm.setShipAddress(new AddressForm());
		CustomRegisterForm.setContactAddress(new AddressForm());
		model.addAttribute(CustomRegisterForm);
		return ControllerConstants.Views.Fragments.Address.ShipRegionsByCountry;
	}
	
	@RequestMapping(value = "/getContactRegions", method = RequestMethod.GET)
	public String getContactRegions(@RequestParam("addressCode") final String addressCode,
			@RequestParam("countryIsoCode") final String countryIsoCode, final Model model)
	{
		model.addAttribute("supportedCountries", getCountries());
		model.addAttribute("regions", i18NFacade.getRegionsForCountryIso(countryIsoCode));
		model.addAttribute("country", countryIsoCode);
		final CustomRegisterForm CustomRegisterForm = new CustomRegisterForm();
		CustomRegisterForm.setShipAddress(new AddressForm());
		CustomRegisterForm.setContactAddress(new AddressForm());
		model.addAttribute(CustomRegisterForm);
		return ControllerConstants.Views.Fragments.Address.ContactRegionsByCountry;
	}
	
}
