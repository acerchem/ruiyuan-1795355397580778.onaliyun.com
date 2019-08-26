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

import static de.hybris.platform.servicelayer.util.ServicesUtil.validateParameterNotNullStandardMessage;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.acerchem.core.model.CountryTrayFareConfModel;
import com.acerchem.core.model.CustomerCreditAccountModel;
import com.acerchem.core.service.AcerchemStockService;
import com.acerchem.core.service.AcerchemTrayService;
import com.acerchem.service.customercreditaccount.DefaultCustomerCreditAccountService;
import com.acerchem.storefront.controllers.ControllerConstants;
import com.acerchem.storefront.data.CustomRegisterForm;
import com.acerchem.storefront.filters.StorefrontFilter;

import de.hybris.platform.acceleratorfacades.ordergridform.OrderGridFormFacade;
import de.hybris.platform.acceleratorfacades.product.data.ReadOnlyOrderGridData;
import de.hybris.platform.acceleratorservices.storefront.data.MetaElementData;
import de.hybris.platform.acceleratorservices.urlencoder.UrlEncoderService;
import de.hybris.platform.acceleratorstorefrontcommons.annotations.RequireHardLogIn;
import de.hybris.platform.acceleratorstorefrontcommons.breadcrumb.Breadcrumb;
import de.hybris.platform.acceleratorstorefrontcommons.breadcrumb.ResourceBreadcrumbBuilder;
import de.hybris.platform.acceleratorstorefrontcommons.controllers.ThirdPartyConstants;
import de.hybris.platform.acceleratorstorefrontcommons.controllers.pages.AbstractSearchPageController;
import de.hybris.platform.acceleratorstorefrontcommons.controllers.util.GlobalMessages;
import de.hybris.platform.acceleratorstorefrontcommons.forms.AddressForm;
import de.hybris.platform.acceleratorstorefrontcommons.forms.UpdateEmailForm;
import de.hybris.platform.acceleratorstorefrontcommons.forms.UpdatePasswordForm;
import de.hybris.platform.acceleratorstorefrontcommons.forms.UpdateProfileForm;
import de.hybris.platform.acceleratorstorefrontcommons.forms.validation.AddressValidator;
import de.hybris.platform.acceleratorstorefrontcommons.forms.validation.EmailValidator;
import de.hybris.platform.acceleratorstorefrontcommons.forms.validation.PasswordValidator;
import de.hybris.platform.acceleratorstorefrontcommons.forms.validation.ProfileValidator;
import de.hybris.platform.acceleratorstorefrontcommons.forms.verification.AddressVerificationResultHandler;
import de.hybris.platform.acceleratorstorefrontcommons.util.AddressDataUtil;
import de.hybris.platform.cms2.exceptions.CMSItemNotFoundException;
import de.hybris.platform.cms2.model.pages.AbstractPageModel;
import de.hybris.platform.cms2.model.pages.ContentPageModel;
import de.hybris.platform.cms2.servicelayer.services.CMSPageService;
import de.hybris.platform.commercefacades.address.AddressVerificationFacade;
import de.hybris.platform.commercefacades.customer.CustomerFacade;
import de.hybris.platform.commercefacades.i18n.I18NFacade;
import de.hybris.platform.commercefacades.order.CheckoutFacade;
import de.hybris.platform.commercefacades.order.OrderFacade;
import de.hybris.platform.commercefacades.order.data.CCPaymentInfoData;
import de.hybris.platform.commercefacades.order.data.OrderData;
import de.hybris.platform.commercefacades.order.data.OrderHistoryData;
import de.hybris.platform.commercefacades.product.ProductFacade;
import de.hybris.platform.commercefacades.product.ProductOption;
import de.hybris.platform.commercefacades.product.data.ProductData;
import de.hybris.platform.commercefacades.storesession.StoreSessionFacade;
import de.hybris.platform.commercefacades.user.UserFacade;
import de.hybris.platform.commercefacades.user.data.AddressData;
import de.hybris.platform.commercefacades.user.data.CountryData;
import de.hybris.platform.commercefacades.user.data.CustomerData;
import de.hybris.platform.commercefacades.user.data.TitleData;
import de.hybris.platform.commercefacades.user.exceptions.PasswordMismatchException;
import de.hybris.platform.commerceservices.customer.CustomerAccountService;
import de.hybris.platform.commerceservices.customer.DuplicateUidException;
import de.hybris.platform.commerceservices.event.OrderCancelledEvent;
import de.hybris.platform.commerceservices.search.flexiblesearch.PagedFlexibleSearchService;
import de.hybris.platform.commerceservices.search.flexiblesearch.data.SortQueryData;
import de.hybris.platform.commerceservices.search.pagedata.PageableData;
import de.hybris.platform.commerceservices.search.pagedata.PaginationData;
import de.hybris.platform.commerceservices.search.pagedata.SearchPageData;
import de.hybris.platform.commerceservices.search.pagedata.SortData;
import de.hybris.platform.commerceservices.strategies.CheckoutCustomerStrategy;
import de.hybris.platform.commerceservices.util.ResponsiveUtils;
import de.hybris.platform.converters.Converters;
import de.hybris.platform.converters.Populator;
import de.hybris.platform.core.PK;
import de.hybris.platform.core.enums.OrderStatus;
import de.hybris.platform.core.model.c2l.CountryModel;
import de.hybris.platform.core.model.c2l.RegionModel;
import de.hybris.platform.core.model.order.AbstractOrderEntryModel;
import de.hybris.platform.core.model.order.AbstractOrderModel;
import de.hybris.platform.core.model.order.OrderModel;
import de.hybris.platform.core.model.product.ProductModel;
import de.hybris.platform.core.model.user.AddressModel;
import de.hybris.platform.core.model.user.CustomerModel;
import de.hybris.platform.orderprocessing.model.OrderProcessModel;
import de.hybris.platform.processengine.BusinessProcessEvent;
import de.hybris.platform.processengine.BusinessProcessService;
import de.hybris.platform.servicelayer.dto.converter.Converter;
import de.hybris.platform.servicelayer.event.EventService;
import de.hybris.platform.servicelayer.exceptions.UnknownIdentifierException;
import de.hybris.platform.servicelayer.i18n.CommonI18NService;
import de.hybris.platform.servicelayer.model.ModelService;
import de.hybris.platform.servicelayer.search.FlexibleSearchQuery;
import de.hybris.platform.servicelayer.search.FlexibleSearchService;
import de.hybris.platform.servicelayer.search.SearchResult;
import de.hybris.platform.servicelayer.user.UserService;
import de.hybris.platform.store.BaseStoreModel;
import de.hybris.platform.store.services.BaseStoreService;
import de.hybris.platform.tx.Transaction;
import de.hybris.platform.util.Config;

/**
 * Controller for home page
 */
@Controller
@RequestMapping("/my-account")
public class AccountPageController extends AbstractSearchPageController {
	private static final String TEXT_ACCOUNT_ADDRESS_BOOK = "text.account.addressBook";
	private static final String BREADCRUMBS_ATTR = "breadcrumbs";
	private static final String IS_DEFAULT_ADDRESS_ATTR = "isDefaultAddress";
	private static final String COUNTRY_DATA_ATTR = "countryData";
	private static final String ADDRESS_BOOK_EMPTY_ATTR = "addressBookEmpty";
	private static final String TITLE_DATA_ATTR = "titleData";
	private static final String FORM_GLOBAL_ERROR = "form.global.error";
	private static final String PROFILE_CURRENT_PASSWORD_INVALID = "profile.currentPassword.invalid";
	private static final String TEXT_ACCOUNT_PROFILE = "text.account.profile";
	private static final String ADDRESS_DATA_ATTR = "addressData";
	private static final String ADDRESS_FORM_ATTR = "addressForm";
	private static final String COUNTRY_ATTR = "country";
	private static final String REGIONS_ATTR = "regions";
	private static final String MY_ACCOUNT_ADDRESS_BOOK_URL = "/my-account/address-book";
	// Internal Redirects
	private static final String REDIRECT_TO_ADDRESS_BOOK_PAGE = REDIRECT_PREFIX + MY_ACCOUNT_ADDRESS_BOOK_URL;
	private static final String REDIRECT_TO_PAYMENT_INFO_PAGE = REDIRECT_PREFIX + "/my-account/payment-details";
	private static final String REDIRECT_TO_EDIT_ADDRESS_PAGE = REDIRECT_PREFIX + "/my-account/edit-address/";
	private static final String REDIRECT_TO_UPDATE_EMAIL_PAGE = REDIRECT_PREFIX + "/my-account/update-email";
	private static final String REDIRECT_TO_UPDATE_PROFILE = REDIRECT_PREFIX + "/my-account/update-profile";
	private static final String REDIRECT_TO_PASSWORD_UPDATE_PAGE = REDIRECT_PREFIX + "/my-account/update-password";
	private static final String REDIRECT_TO_ORDER_HISTORY_PAGE = REDIRECT_PREFIX + "/my-account/orders";

	/**
	 * We use this suffix pattern because of an issue with Spring 3.1 where a
	 * Uri value is incorrectly extracted if it contains on or more '.'
	 * characters. Please see https://jira.springsource.org/browse/SPR-6164 for
	 * a discussion on the issue and future resolution.
	 */
	private static final String ORDER_CODE_PATH_VARIABLE_PATTERN = "{orderCode:.*}";
	private static final String ADDRESS_CODE_PATH_VARIABLE_PATTERN = "{addressCode:.*}";

	// CMS Pages
	private static final String ACCOUNT_CMS_PAGE = "account";
	private static final String PROFILE_CMS_PAGE = "profile";
	private static final String UPDATE_PASSWORD_CMS_PAGE = "updatePassword";
	private static final String UPDATE_PROFILE_CMS_PAGE = "update-profile";
	private static final String UPDATE_EMAIL_CMS_PAGE = "update-email";
	private static final String ADDRESS_BOOK_CMS_PAGE = "address-book";
	private static final String ADD_EDIT_ADDRESS_CMS_PAGE = "add-edit-address";
	private static final String PAYMENT_DETAILS_CMS_PAGE = "payment-details";
	private static final String ORDER_HISTORY_CMS_PAGE = "orders";
	private static final String ORDER_DETAIL_CMS_PAGE = "order";

	private static final Logger LOG = Logger.getLogger(AccountPageController.class);

	@Resource(name = "orderFacade")
	private OrderFacade orderFacade;

	@Resource(name = "acceleratorCheckoutFacade")
	private CheckoutFacade checkoutFacade;

	@Resource(name = "userFacade")
	private UserFacade userFacade;

	@Resource(name = "customerFacade")
	private CustomerFacade customerFacade;

	@Resource(name = "accountBreadcrumbBuilder")
	private ResourceBreadcrumbBuilder accountBreadcrumbBuilder;

	@Resource(name = "passwordValidator")
	private PasswordValidator passwordValidator;

	@Resource(name = "addressValidator")
	private AddressValidator addressValidator;

	@Resource(name = "customAddressValidator")
	private Validator customAddressValidator;

	@Resource(name = "profileValidator")
	private ProfileValidator profileValidator;

	@Resource(name = "emailValidator")
	private EmailValidator emailValidator;

	@Resource(name = "i18NFacade")
	private I18NFacade i18NFacade;

	@Resource(name = "addressVerificationFacade")
	private AddressVerificationFacade addressVerificationFacade;

	@Resource(name = "addressVerificationResultHandler")
	private AddressVerificationResultHandler addressVerificationResultHandler;

	@Resource(name = "productVariantFacade")
	private ProductFacade productFacade;

	@Resource(name = "orderGridFormFacade")
	private OrderGridFormFacade orderGridFormFacade;

	@Resource(name = "addressDataUtil")
	private AddressDataUtil addressDataUtil;
	
	@Resource
	private EventService eventService;

	protected PasswordValidator getPasswordValidator() {
		return passwordValidator;
	}

	protected AddressValidator getAddressValidator() {
		return addressValidator;
	}

	protected ProfileValidator getProfileValidator() {
		return profileValidator;
	}

	protected EmailValidator getEmailValidator() {
		return emailValidator;
	}

	protected I18NFacade getI18NFacade() {
		return i18NFacade;
	}

	protected AddressVerificationFacade getAddressVerificationFacade() {
		return addressVerificationFacade;
	}

	protected AddressVerificationResultHandler getAddressVerificationResultHandler() {
		return addressVerificationResultHandler;
	}

	@ModelAttribute("countries")
	public Collection<CountryData> getCountries() {
		return checkoutFacade.getDeliveryCountries();
	}

	@ModelAttribute("titles")
	public Collection<TitleData> getTitles() {
		return userFacade.getTitles();
	}

	@ModelAttribute("countryDataMap")
	public Map<String, CountryData> getCountryDataMap() {
		final Map<String, CountryData> countryDataMap = new HashMap<>();
		for (final CountryData countryData : getCountries()) {
			countryDataMap.put(countryData.getIsocode(), countryData);
		}
		return countryDataMap;
	}

	@RequestMapping(value = "/addressform", method = RequestMethod.GET)
	public String getCountryAddressForm(@RequestParam("addressCode") final String addressCode,
			@RequestParam("countryIsoCode") final String countryIsoCode, final Model model) {
		promotionItem(model);
		model.addAttribute("supportedCountries", getCountries());
		populateModelRegionAndCountry(model, countryIsoCode);

		final AddressForm addressForm = new AddressForm();
		model.addAttribute(ADDRESS_FORM_ATTR, addressForm);
		for (final AddressData addressData : userFacade.getAddressBook()) {
			if (addressData.getId() != null && addressData.getId().equals(addressCode)
					&& countryIsoCode.equals(addressData.getCountry().getIsocode())) {
				model.addAttribute(ADDRESS_DATA_ATTR, addressData);
				addressDataUtil.convert(addressData, addressForm);
				break;
			}
		}
		return ControllerConstants.Views.Fragments.Account.CountryAddressForm;
	}

	protected void populateModelRegionAndCountry(final Model model, final String countryIsoCode) {
		model.addAttribute(REGIONS_ATTR, getI18NFacade().getRegionsForCountryIso(countryIsoCode));
		model.addAttribute(COUNTRY_ATTR, countryIsoCode);
	}

	@RequestMapping(method = RequestMethod.GET)
	@RequireHardLogIn
	public String account(final Model model, final RedirectAttributes redirectModel) throws CMSItemNotFoundException {
		if (ResponsiveUtils.isResponsive()) {
			GlobalMessages.addFlashMessage(redirectModel, GlobalMessages.ERROR_MESSAGES_HOLDER,
					"system.error.page.not.found", null);
			return REDIRECT_PREFIX + "/";
		}
		storeCmsPageInModel(model, getContentPageForLabelOrId(ACCOUNT_CMS_PAGE));
		setUpMetaDataForContentPage(model, getContentPageForLabelOrId(ACCOUNT_CMS_PAGE));
		model.addAttribute(BREADCRUMBS_ATTR, accountBreadcrumbBuilder.getBreadcrumbs(null));
		model.addAttribute(ThirdPartyConstants.SeoRobots.META_ROBOTS, ThirdPartyConstants.SeoRobots.NOINDEX_NOFOLLOW);
		return getViewForPage(model);
	}

	@RequestMapping(value = "/order/" + ORDER_CODE_PATH_VARIABLE_PATTERN, method = RequestMethod.GET)
	@RequireHardLogIn
	public String order(@PathVariable("orderCode") final String orderCode, final Model model,
			final RedirectAttributes redirectModel) throws CMSItemNotFoundException {
		try {
			promotionItem(model);
			model.addAttribute("maxday", Integer.valueOf(Config.getParameter("cancel.order.day")));
			final OrderData orderDetails = orderFacade.getOrderDetailsForCode(orderCode);
			model.addAttribute("orderData", orderDetails);

			final CustomerModel customer = (CustomerModel) userService.getCurrentUser();
			model.addAttribute("companyName", customer.getCompanyName());

			final List<Breadcrumb> breadcrumbs = accountBreadcrumbBuilder.getBreadcrumbs(null);
			breadcrumbs.add(new Breadcrumb("/my-account/orders", getMessageSource()
					.getMessage("text.account.orderHistory", null, getI18nService().getCurrentLocale()), null));
			breadcrumbs.add(new Breadcrumb("#",
					getMessageSource().getMessage("text.account.order.orderBreadcrumb",
							new Object[] { orderDetails.getCode() }, "Order {0}", getI18nService().getCurrentLocale()),
					null));
			model.addAttribute(BREADCRUMBS_ATTR, breadcrumbs);

			final Date pickupDate = orderDetails.getPickupDateOfExtended() != null
					? orderDetails.getPickupDateOfExtended() : orderDetails.getPickUpDate();
			final Calendar c = Calendar.getInstance();
			c.setTime(pickupDate);
			//修改为含缺省值，为1天
			c.set(Calendar.DATE, c.get(Calendar.DATE) - Config.getInt("cancel.order.day",
					1));
			final Date date = c.getTime();

			final Calendar today = Calendar.getInstance();
			final Date todaydate = today.getTime();

			model.addAttribute("canCancel", todaydate.before(date));

		} catch (final UnknownIdentifierException e) {
			LOG.warn("Attempted to load a order that does not exist or is not visible", e);
			GlobalMessages.addFlashMessage(redirectModel, GlobalMessages.ERROR_MESSAGES_HOLDER,
					"system.error.page.not.found", null);
			return REDIRECT_TO_ORDER_HISTORY_PAGE;
		}
		storeCmsPageInModel(model, getContentPageForLabelOrId(ORDER_DETAIL_CMS_PAGE));
		model.addAttribute(ThirdPartyConstants.SeoRobots.META_ROBOTS, ThirdPartyConstants.SeoRobots.NOINDEX_NOFOLLOW);
		setUpMetaDataForContentPage(model, getContentPageForLabelOrId(ORDER_DETAIL_CMS_PAGE));
		return "/pages/account/orderPageNew";
	}

	@RequestMapping(value = "/order/" + ORDER_CODE_PATH_VARIABLE_PATTERN
			+ "/getReadOnlyProductVariantMatrix", method = RequestMethod.GET)
	@RequireHardLogIn
	public String getProductVariantMatrixForResponsive(@PathVariable("orderCode") final String orderCode,
			@RequestParam("productCode") final String productCode, final Model model) {
		promotionItem(model);
		final OrderData orderData = orderFacade.getOrderDetailsForCodeWithoutUser(orderCode);

		final Map<String, ReadOnlyOrderGridData> readOnlyMultiDMap = orderGridFormFacade
				.getReadOnlyOrderGridForProductInOrder(productCode,
						Arrays.asList(ProductOption.BASIC, ProductOption.CATEGORIES), orderData);
		model.addAttribute("readOnlyMultiDMap", readOnlyMultiDMap);

		return ControllerConstants.Views.Fragments.Checkout.ReadOnlyExpandedOrderForm;
	}

	@RequestMapping(value = "/profile", method = RequestMethod.GET)
	@RequireHardLogIn
	public String profile(final Model model) throws CMSItemNotFoundException {
		promotionItem(model);
		final List<TitleData> titles = userFacade.getTitles();

		final CustomerData customerData = customerFacade.getCurrentCustomer();
		if (customerData.getTitleCode() != null) {
			model.addAttribute("title", findTitleForCode(titles, customerData.getTitleCode()));
		}

		model.addAttribute("customerData", customerData);

		storeCmsPageInModel(model, getContentPageForLabelOrId(PROFILE_CMS_PAGE));
		setUpMetaDataForContentPage(model, getContentPageForLabelOrId(PROFILE_CMS_PAGE));
		model.addAttribute(BREADCRUMBS_ATTR, accountBreadcrumbBuilder.getBreadcrumbs(TEXT_ACCOUNT_PROFILE));
		model.addAttribute(ThirdPartyConstants.SeoRobots.META_ROBOTS, ThirdPartyConstants.SeoRobots.NOINDEX_NOFOLLOW);
		return getViewForPage(model);
	}

	protected TitleData findTitleForCode(final List<TitleData> titles, final String code) {
		if (code != null && !code.isEmpty() && titles != null && !titles.isEmpty()) {
			for (final TitleData title : titles) {
				if (code.equals(title.getCode())) {
					return title;
				}
			}
		}
		return null;
	}

	@RequestMapping(value = "/update-email", method = RequestMethod.GET)
	@RequireHardLogIn
	public String editEmail(final Model model) throws CMSItemNotFoundException {
		promotionItem(model);
		final CustomerData customerData = customerFacade.getCurrentCustomer();
		final UpdateEmailForm updateEmailForm = new UpdateEmailForm();

		updateEmailForm.setEmail(customerData.getDisplayUid());

		model.addAttribute("updateEmailForm", updateEmailForm);

		storeCmsPageInModel(model, getContentPageForLabelOrId(UPDATE_EMAIL_CMS_PAGE));
		setUpMetaDataForContentPage(model, getContentPageForLabelOrId(UPDATE_EMAIL_CMS_PAGE));
		model.addAttribute(BREADCRUMBS_ATTR, accountBreadcrumbBuilder.getBreadcrumbs(TEXT_ACCOUNT_PROFILE));
		model.addAttribute(ThirdPartyConstants.SeoRobots.META_ROBOTS, ThirdPartyConstants.SeoRobots.NOINDEX_NOFOLLOW);
		return getViewForPage(model);
	}

	@RequestMapping(value = "/update-email", method = RequestMethod.POST)
	@RequireHardLogIn
	public String updateEmail(final UpdateEmailForm updateEmailForm, final BindingResult bindingResult,
			final Model model, final RedirectAttributes redirectAttributes) throws CMSItemNotFoundException {
		promotionItem(model);
		getEmailValidator().validate(updateEmailForm, bindingResult);
		String returnAction = REDIRECT_TO_UPDATE_EMAIL_PAGE;

		if (!bindingResult.hasErrors() && !updateEmailForm.getEmail().equals(updateEmailForm.getChkEmail())) {
			bindingResult.rejectValue("chkEmail", "validation.checkEmail.equals", new Object[] {},
					"validation.checkEmail.equals");
		}

		if (bindingResult.hasErrors()) {
			returnAction = setErrorMessagesAndCMSPage(model, UPDATE_EMAIL_CMS_PAGE);
		} else {
			try {
				customerFacade.changeUid(updateEmailForm.getEmail(), updateEmailForm.getPassword());
				GlobalMessages.addFlashMessage(redirectAttributes, GlobalMessages.CONF_MESSAGES_HOLDER,
						"text.account.profile.confirmationUpdated", null);

				// Replace the spring security authentication with the new UID
				final String newUid = customerFacade.getCurrentCustomer().getUid().toLowerCase();
				final Authentication oldAuthentication = SecurityContextHolder.getContext().getAuthentication();
				final UsernamePasswordAuthenticationToken newAuthentication = new UsernamePasswordAuthenticationToken(
						newUid, null, oldAuthentication.getAuthorities());
				newAuthentication.setDetails(oldAuthentication.getDetails());
				SecurityContextHolder.getContext().setAuthentication(newAuthentication);
			} catch (final DuplicateUidException e) {
				bindingResult.rejectValue("email", "profile.email.unique");
				returnAction = setErrorMessagesAndCMSPage(model, UPDATE_EMAIL_CMS_PAGE);
			} catch (final PasswordMismatchException passwordMismatchException) {
				bindingResult.rejectValue("password", PROFILE_CURRENT_PASSWORD_INVALID);
				returnAction = setErrorMessagesAndCMSPage(model, UPDATE_EMAIL_CMS_PAGE);
			}
		}

		return returnAction;
	}

	protected String setErrorMessagesAndCMSPage(final Model model, final String cmsPageLabelOrId)
			throws CMSItemNotFoundException {
		GlobalMessages.addErrorMessage(model, FORM_GLOBAL_ERROR);
		storeCmsPageInModel(model, getContentPageForLabelOrId(cmsPageLabelOrId));
		setUpMetaDataForContentPage(model, getContentPageForLabelOrId(cmsPageLabelOrId));
		model.addAttribute(BREADCRUMBS_ATTR, accountBreadcrumbBuilder.getBreadcrumbs(TEXT_ACCOUNT_PROFILE));
		return getViewForPage(model);
	}

	@RequestMapping(value = "/add-address", method = RequestMethod.GET)
	@RequireHardLogIn
	public String addAddress(final Model model) throws CMSItemNotFoundException {
		promotionItem(model);
		model.addAttribute("nowPage", "address-book");
		model.addAttribute(COUNTRY_DATA_ATTR, checkoutFacade.getDeliveryCountries());
		model.addAttribute(TITLE_DATA_ATTR, userFacade.getTitles());
		final AddressForm addressForm = getPreparedAddressForm();
		model.addAttribute(ADDRESS_FORM_ATTR, addressForm);
		model.addAttribute(ADDRESS_BOOK_EMPTY_ATTR, Boolean.valueOf(userFacade.isAddressBookEmpty()));
		model.addAttribute(IS_DEFAULT_ADDRESS_ATTR, Boolean.FALSE);
		storeCmsPageInModel(model, getContentPageForLabelOrId(ADD_EDIT_ADDRESS_CMS_PAGE));
		setUpMetaDataForContentPage(model, getContentPageForLabelOrId(ADD_EDIT_ADDRESS_CMS_PAGE));

		final List<Breadcrumb> breadcrumbs = accountBreadcrumbBuilder.getBreadcrumbs(null);
		breadcrumbs.add(new Breadcrumb(MY_ACCOUNT_ADDRESS_BOOK_URL,
				getMessageSource().getMessage(TEXT_ACCOUNT_ADDRESS_BOOK, null, getI18nService().getCurrentLocale()),
				null));
		breadcrumbs.add(new Breadcrumb("#", getMessageSource().getMessage("text.account.addressBook.addEditAddress",
				null, getI18nService().getCurrentLocale()), null));
		model.addAttribute(BREADCRUMBS_ATTR, breadcrumbs);
		model.addAttribute(ThirdPartyConstants.SeoRobots.META_ROBOTS, ThirdPartyConstants.SeoRobots.NOINDEX_NOFOLLOW);
		return getViewForPage(model);
	}

	protected AddressForm getPreparedAddressForm() {
		final CustomerData currentCustomerData = customerFacade.getCurrentCustomer();
		final AddressForm addressForm = new AddressForm();
		addressForm.setFirstName(currentCustomerData.getFirstName());
		addressForm.setLastName(currentCustomerData.getLastName());
		addressForm.setTitleCode(currentCustomerData.getTitleCode());
		return addressForm;
	}

	@RequestMapping(value = "/add-address", method = RequestMethod.POST)
	@RequireHardLogIn
	public String addAddress(final AddressForm addressForm, final BindingResult bindingResult, final Model model,
			final RedirectAttributes redirectModel) throws CMSItemNotFoundException {
		promotionItem(model);
		model.addAttribute("nowPage", "address-book");
		customAddressValidator.validate(addressForm, bindingResult);
		if (bindingResult.hasErrors()) {
			GlobalMessages.addErrorMessage(model, FORM_GLOBAL_ERROR);
			storeCmsPageInModel(model, getContentPageForLabelOrId(ADD_EDIT_ADDRESS_CMS_PAGE));
			setUpMetaDataForContentPage(model, getContentPageForLabelOrId(ADD_EDIT_ADDRESS_CMS_PAGE));
			setUpAddressFormAfterError(addressForm, model);
			return getViewForPage(model);
		}

		final AddressData newAddress = addressDataUtil.convertToVisibleAddressData(addressForm);
		if (userFacade.isAddressBookEmpty()) {
			newAddress.setDefaultAddress(true);
		} else {
			newAddress.setDefaultAddress(
					addressForm.getDefaultAddress() != null && addressForm.getDefaultAddress().booleanValue());
		}

		populateModelRegionAndCountry(model, addressForm.getCountryIso());
		model.addAttribute("edit", Boolean.TRUE);
		model.addAttribute(IS_DEFAULT_ADDRESS_ATTR, Boolean.valueOf(isDefaultAddress(addressForm.getAddressId())));

		validateParameterNotNullStandardMessage("addressData", newAddress);
		final CustomerModel currentCustomer = checkoutCustomerStrategy.getCurrentUserForCheckout();
		final boolean makeThisAddressTheDefault = newAddress.isDefaultAddress()
				|| (currentCustomer.getDefaultShipmentAddress() == null && newAddress.isVisibleInAddressBook());

		final AddressModel Address = modelService.create(AddressModel.class);
		addressReversePopulator.populate(newAddress, Address);
		Address.setPhone2(addressForm.getLine1());
		Address.setLine1(null);
		Address.setVisibleInAddressBook(true);
		customerAccountService.saveAddressEntry(currentCustomer, Address);
		newAddress.setId(Address.getPk().toString());
		if (makeThisAddressTheDefault) {
			customerAccountService.setDefaultAddressEntry(currentCustomer, Address);
		}
		GlobalMessages.addFlashMessage(redirectModel, GlobalMessages.CONF_MESSAGES_HOLDER,
				"account.confirmation.address.added", null);
		return REDIRECT_TO_EDIT_ADDRESS_PAGE + newAddress.getId();
	}

	protected void setUpAddressFormAfterError(final AddressForm addressForm, final Model model) {
		model.addAttribute(COUNTRY_DATA_ATTR, checkoutFacade.getDeliveryCountries());
		model.addAttribute(TITLE_DATA_ATTR, userFacade.getTitles());
		model.addAttribute(ADDRESS_BOOK_EMPTY_ATTR, Boolean.valueOf(userFacade.isAddressBookEmpty()));
		model.addAttribute(IS_DEFAULT_ADDRESS_ATTR, Boolean.valueOf(isDefaultAddress(addressForm.getAddressId())));
		if (addressForm.getCountryIso() != null) {
			populateModelRegionAndCountry(model, addressForm.getCountryIso());
		}
	}

	@RequestMapping(value = "/edit-address/" + ADDRESS_CODE_PATH_VARIABLE_PATTERN, method = RequestMethod.GET)
	@RequireHardLogIn
	public String editAddress(@PathVariable("addressCode") final String addressCode, final Model model)
			throws CMSItemNotFoundException {
		promotionItem(model);
		model.addAttribute("nowPage", "address-book");
		final AddressForm addressForm = new AddressForm();
		model.addAttribute(COUNTRY_DATA_ATTR, checkoutFacade.getDeliveryCountries());
		model.addAttribute(TITLE_DATA_ATTR, userFacade.getTitles());
		model.addAttribute(ADDRESS_FORM_ATTR, addressForm);

		final CustomerModel currentCustomer = checkoutCustomerStrategy.getCurrentUserForCheckout();
		final List<AddressData> addressBook = userFacade.getAddressBook();

		model.addAttribute(ADDRESS_BOOK_EMPTY_ATTR, Boolean.valueOf(CollectionUtils.isEmpty(addressBook)));

		for (final AddressData addressData : addressBook) {
			if (addressData.getId() != null && addressData.getId().equals(addressCode)) {
				model.addAttribute(REGIONS_ATTR,
						getI18NFacade().getRegionsForCountryIso(addressData.getCountry().getIsocode()));
				model.addAttribute(COUNTRY_ATTR, addressData.getCountry().getIsocode());

				final AddressModel addressModel = customerAccountService.getAddressForCode(currentCustomer,
						addressData.getId());
				addressData.setLine1(addressModel.getPhone2());

				model.addAttribute(ADDRESS_DATA_ATTR, addressData);
				addressDataUtil.convert(addressData, addressForm);

				if (isDefaultAddress(addressData.getId())) {
					addressForm.setDefaultAddress(Boolean.TRUE);
					model.addAttribute(IS_DEFAULT_ADDRESS_ATTR, Boolean.TRUE);
				} else {
					addressForm.setDefaultAddress(Boolean.FALSE);
					model.addAttribute(IS_DEFAULT_ADDRESS_ATTR, Boolean.FALSE);
				}
				break;
			}
		}

		storeCmsPageInModel(model, getContentPageForLabelOrId(ADD_EDIT_ADDRESS_CMS_PAGE));
		setUpMetaDataForContentPage(model, getContentPageForLabelOrId(ADD_EDIT_ADDRESS_CMS_PAGE));

		final List<Breadcrumb> breadcrumbs = accountBreadcrumbBuilder.getBreadcrumbs(null);
		breadcrumbs.add(new Breadcrumb(MY_ACCOUNT_ADDRESS_BOOK_URL,
				getMessageSource().getMessage(TEXT_ACCOUNT_ADDRESS_BOOK, null, getI18nService().getCurrentLocale()),
				null));
		breadcrumbs.add(new Breadcrumb("#", getMessageSource().getMessage("text.account.addressBook.addEditAddress",
				null, getI18nService().getCurrentLocale()), null));
		model.addAttribute(BREADCRUMBS_ATTR, breadcrumbs);
		model.addAttribute(ThirdPartyConstants.SeoRobots.META_ROBOTS, ThirdPartyConstants.SeoRobots.NOINDEX_NOFOLLOW);
		model.addAttribute("edit", Boolean.TRUE);
		return getViewForPage(model);
	}

	/**
	 * Method checks if address is set as default
	 *
	 * @param addressId
	 *            - identifier for address to check
	 * @return true if address is default, false if address is not default
	 */
	protected boolean isDefaultAddress(final String addressId) {
		final AddressData defaultAddress = userFacade.getDefaultAddress();
		return defaultAddress != null && defaultAddress.getId() != null && defaultAddress.getId().equals(addressId);
	}

	@Resource
	private CheckoutCustomerStrategy checkoutCustomerStrategy;
	@Resource
	private CustomerAccountService customerAccountService;
	@Resource
	private Populator<AddressData, AddressModel> addressReversePopulator;

	@RequestMapping(value = "/edit-address/" + ADDRESS_CODE_PATH_VARIABLE_PATTERN, method = RequestMethod.POST)
	@RequireHardLogIn
	public String editAddress(final AddressForm addressForm, final BindingResult bindingResult, final Model model,
			final RedirectAttributes redirectModel) throws CMSItemNotFoundException {
		promotionItem(model);
		model.addAttribute("nowPage", "address-book");
		customAddressValidator.validate(addressForm, bindingResult);
		if (bindingResult.hasErrors()) {
			GlobalMessages.addErrorMessage(model, FORM_GLOBAL_ERROR);
			storeCmsPageInModel(model, getContentPageForLabelOrId(ADD_EDIT_ADDRESS_CMS_PAGE));
			setUpMetaDataForContentPage(model, getContentPageForLabelOrId(ADD_EDIT_ADDRESS_CMS_PAGE));
			setUpAddressFormAfterError(addressForm, model);
			return getViewForPage(model);
		}

		model.addAttribute(ThirdPartyConstants.SeoRobots.META_ROBOTS, ThirdPartyConstants.SeoRobots.NOINDEX_NOFOLLOW);

		final AddressData newAddress = addressDataUtil.convertToVisibleAddressData(addressForm);
		if (Boolean.TRUE.equals(addressForm.getDefaultAddress()) || userFacade.getAddressBook().size() <= 1) {
			newAddress.setDefaultAddress(true);
		}
		model.addAttribute(REGIONS_ATTR, getI18NFacade().getRegionsForCountryIso(addressForm.getCountryIso()));
		model.addAttribute(COUNTRY_ATTR, addressForm.getCountryIso());
		model.addAttribute("edit", Boolean.TRUE);
		model.addAttribute(IS_DEFAULT_ADDRESS_ATTR, Boolean.valueOf(isDefaultAddress(addressForm.getAddressId())));

		validateParameterNotNullStandardMessage("addressData", newAddress);
		final CustomerModel currentCustomer = checkoutCustomerStrategy.getCurrentUserForCheckout();
		final AddressModel addressModel = customerAccountService.getAddressForCode(currentCustomer, newAddress.getId());
		addressModel.setRegion(null);
		addressReversePopulator.populate(newAddress, addressModel);

		addressModel.setPhone2(addressForm.getLine1());
		addressModel.setLine1(null);

		customerAccountService.saveAddressEntry(currentCustomer, addressModel);
		if (newAddress.isDefaultAddress()) {
			customerAccountService.setDefaultAddressEntry(currentCustomer, addressModel);
		} else if (addressModel.equals(currentCustomer.getDefaultShipmentAddress())) {
			customerAccountService.clearDefaultAddressEntry(currentCustomer);
		}

		GlobalMessages.addFlashMessage(redirectModel, GlobalMessages.CONF_MESSAGES_HOLDER,
				"account.confirmation.address.updated", null);
		return REDIRECT_TO_EDIT_ADDRESS_PAGE + newAddress.getId();
	}

	@RequestMapping(value = "/getAddressRegions", method = RequestMethod.GET)
	public String getAddressRegions(@RequestParam("addressCode") final String addressCode,
			@RequestParam("countryIsoCode") final String countryIsoCode, final Model model) {
		model.addAttribute("supportedCountries", getCountries());
		model.addAttribute("regions", i18NFacade.getRegionsForCountryIso(countryIsoCode));
		model.addAttribute("country", countryIsoCode);
		final AddressForm AddressForm = new AddressForm();
		model.addAttribute(AddressForm);
		return ControllerConstants.Views.Fragments.Address.AddressFormByCountry;
	}

	@RequestMapping(value = "/select-suggested-address", method = RequestMethod.POST)
	public String doSelectSuggestedAddress(final AddressForm addressForm, final RedirectAttributes redirectModel) {
		final Set<String> resolveCountryRegions = org.springframework.util.StringUtils
				.commaDelimitedListToSet(Config.getParameter("resolve.country.regions"));

		final AddressData selectedAddress = addressDataUtil.convertToVisibleAddressData(addressForm);

		final CountryData countryData = selectedAddress.getCountry();

		if (!resolveCountryRegions.contains(countryData.getIsocode())) {
			selectedAddress.setRegion(null);
		}

		if (Boolean.TRUE.equals(addressForm.getEditAddress())) {
			userFacade.editAddress(selectedAddress);
		} else {
			userFacade.addAddress(selectedAddress);
		}

		GlobalMessages.addFlashMessage(redirectModel, GlobalMessages.CONF_MESSAGES_HOLDER,
				"account.confirmation.address.added");

		return REDIRECT_TO_ADDRESS_BOOK_PAGE;
	}

	@RequestMapping(value = "/remove-address/" + ADDRESS_CODE_PATH_VARIABLE_PATTERN, method = { RequestMethod.GET,
			RequestMethod.POST })
	@RequireHardLogIn
	public String removeAddress(@PathVariable("addressCode") final String addressCode,
			final RedirectAttributes redirectModel) {
		AddressData addressData = userFacade.getAddressForCode(addressCode);
		if(addressData.isDefaultAddress()){
			GlobalMessages.addErrorMessage(redirectModel,"account.confirmation.address.removed.default");
			return REDIRECT_TO_ADDRESS_BOOK_PAGE;
		}
		addressData = new AddressData();
		addressData.setId(addressCode);
		userFacade.removeAddress(addressData);

		GlobalMessages.addFlashMessage(redirectModel, GlobalMessages.CONF_MESSAGES_HOLDER,
				"account.confirmation.address.removed");
		return REDIRECT_TO_ADDRESS_BOOK_PAGE;
	}

	@RequestMapping(value = "/set-default-address/" + ADDRESS_CODE_PATH_VARIABLE_PATTERN, method = RequestMethod.GET)
	@RequireHardLogIn
	public String setDefaultAddress(@PathVariable("addressCode") final String addressCode,
			final RedirectAttributes redirectModel) {
		final AddressData addressData = new AddressData();
		addressData.setDefaultAddress(true);
		addressData.setVisibleInAddressBook(true);
		addressData.setId(addressCode);
		userFacade.setDefaultAddress(addressData);
		GlobalMessages.addFlashMessage(redirectModel, GlobalMessages.CONF_MESSAGES_HOLDER,
				"account.confirmation.default.address.changed");
		return REDIRECT_TO_ADDRESS_BOOK_PAGE;
	}

	@RequestMapping(value = "/payment-details", method = RequestMethod.GET)
	@RequireHardLogIn
	public String paymentDetails(final Model model) throws CMSItemNotFoundException {
		model.addAttribute("customerData", customerFacade.getCurrentCustomer());
		model.addAttribute("paymentInfoData", userFacade.getCCPaymentInfos(true));
		storeCmsPageInModel(model, getContentPageForLabelOrId(PAYMENT_DETAILS_CMS_PAGE));
		setUpMetaDataForContentPage(model, getContentPageForLabelOrId(ADD_EDIT_ADDRESS_CMS_PAGE));
		model.addAttribute(BREADCRUMBS_ATTR, accountBreadcrumbBuilder.getBreadcrumbs("text.account.paymentDetails"));
		model.addAttribute(ThirdPartyConstants.SeoRobots.META_ROBOTS, ThirdPartyConstants.SeoRobots.NOINDEX_NOFOLLOW);
		return getViewForPage(model);
	}

	@RequestMapping(value = "/set-default-payment-details", method = RequestMethod.POST)
	@RequireHardLogIn
	public String setDefaultPaymentDetails(@RequestParam final String paymentInfoId) {
		CCPaymentInfoData paymentInfoData = null;
		if (StringUtils.isNotBlank(paymentInfoId)) {
			paymentInfoData = userFacade.getCCPaymentInfoForCode(paymentInfoId);
		}
		userFacade.setDefaultPaymentInfo(paymentInfoData);
		return REDIRECT_TO_PAYMENT_INFO_PAGE;
	}

	@RequestMapping(value = "/remove-payment-method", method = RequestMethod.POST)
	@RequireHardLogIn
	public String removePaymentMethod(@RequestParam(value = "paymentInfoId") final String paymentMethodId,
			final RedirectAttributes redirectAttributes) throws CMSItemNotFoundException {
		userFacade.unlinkCCPaymentInfo(paymentMethodId);
		GlobalMessages.addFlashMessage(redirectAttributes, GlobalMessages.CONF_MESSAGES_HOLDER,
				"text.account.profile.paymentCart.removed");
		return REDIRECT_TO_PAYMENT_INFO_PAGE;
	}

	@RequestMapping(value = "/update-profile-old", method = RequestMethod.POST)
	@RequireHardLogIn
	public String updateProfile(final UpdateProfileForm updateProfileForm, final BindingResult bindingResult,
			final Model model, final RedirectAttributes redirectAttributes) throws CMSItemNotFoundException {
		return null;
	}

	@RequestMapping(value = "/orders-old", method = RequestMethod.GET)
	@RequireHardLogIn
	public String orders(@RequestParam(value = "page", defaultValue = "0") final int page,
			@RequestParam(value = "show", defaultValue = "Page") final ShowMode showMode,
			@RequestParam(value = "sort", required = false) final String sortCode, final Model model)
			throws CMSItemNotFoundException {
		return null;
	}

	/* add by alice */
	@Resource(name = "cmsPageService")
	private CMSPageService cmsPageService;

	@Resource
	private UserService userService;

	@Resource(name = "personalInfoValidator")
	private Validator personalInfoValidator;

	@Resource(name = "modelService")
	private ModelService modelService;

	@Resource(name = "commonI18NService")
	private CommonI18NService commonI18NService;

	@Resource(name = "storeSessionFacade")
	private StoreSessionFacade storeSessionFacade;

	@Resource(name = "urlEncoderService")
	private UrlEncoderService urlEncoderService;

	@RequestMapping(value = "/update-profile", method = RequestMethod.GET)
	@RequireHardLogIn
	public String editProfile(final Model model) throws CMSItemNotFoundException {
		promotionItem(model);
		model.addAttribute("countryData", checkoutFacade.getDeliveryCountries());
		final AbstractPageModel cmsPage = cmsPageService.getPageForLabelOrId("add-edit-address");
		if (model != null && cmsPage != null) {
			model.addAttribute("cmsPage", cmsPage);
			if (cmsPage instanceof ContentPageModel) {
				model.addAttribute("pageTitle", getPageTitleResolver().resolveContentPageTitle(cmsPage.getTitle()));
			}
		}
		final List<MetaElementData> metadata = new LinkedList<>();
		metadata.add(
				createMetaElement("keywords", cmsPageService.getPageForLabelOrId("add-edit-address").getKeywords()));
		metadata.add(createMetaElement("description",
				cmsPageService.getPageForLabelOrId("add-edit-address").getDescription()));
		model.addAttribute("metatags", metadata);

		final CustomRegisterForm CustomRegisterForm = new CustomRegisterForm();

		final CustomerData customerData = customerFacade.getCurrentCustomer();
		CustomRegisterForm.setLanguage(customerData.getLanguage().getIsocode());
		CustomRegisterForm.setCurrency(customerData.getCurrency().getIsocode());

		final CustomerModel customer = (CustomerModel) userService.getCurrentUser();
		CustomRegisterForm.setName(customer.getName());
		CustomRegisterForm.setEmail(customer.getUid());
		CustomRegisterForm.setCompanyType(customer.getCompanyType());
		CustomRegisterForm.setCompanyName(customer.getCompanyName());
		CustomRegisterForm.setEstablishedIn(customer.getEstablishedIn());
		CustomRegisterForm.setRevenue(customer.getRevenue());
		CustomRegisterForm.setEmployeesNo(customer.getEmployeesNo());
		CustomRegisterForm.setLimitCreditAmount(customer.getLimitCreditAmount());
		CustomRegisterForm.setVatNo(customer.getVatNo());
		CustomRegisterForm.setHaveFinancialReport(
				customer.getHaveFinancialReport() != null ? customer.getHaveFinancialReport() : false);
		CustomRegisterForm.setProvideTradeReference(
				customer.getProvideTradeReference() != null ? customer.getProvideTradeReference() : false);

		final Collection<AddressModel> amlist = customer.getAddresses();
		if (amlist != null && amlist.size() > 0) {
			for (final AddressModel am : amlist) {
				if (am.getContactAddress()) {
					final AddressForm address = new AddressForm();
					if (am.getCountry()!=null && StringUtils.isNotBlank(am.getCountry().getIsocode())){
						address.setCountryIso(am.getCountry().getIsocode());
					}
					if (am.getRegion() != null) {
						address.setRegionIso(am.getRegion().getIsocode());
					}
					address.setAddressId(am.getPk().toString());
					address.setTownCity(am.getTown());
					address.setPostcode(am.getPostalcode());
					CustomRegisterForm.setContactAddress(address);
					// CustomRegisterForm.setContacts(am.getLastname());
					if (am.getCountry()!=null && StringUtils.isNotBlank(am.getCountry().getIsocode())){
						model.addAttribute("regions", i18NFacade.getRegionsForCountryIso(am.getCountry().getIsocode()));
					}
				}
			}
		}
		if (CustomRegisterForm.getContactAddress() == null) {
			CustomRegisterForm.setContactAddress(new AddressForm());
		}
		CustomRegisterForm.setShipAddress(new AddressForm());
		model.addAttribute(CustomRegisterForm);
		model.addAttribute("nowPage", "update-profile");
		storeCmsPageInModel(model, getContentPageForLabelOrId(UPDATE_PROFILE_CMS_PAGE));
		setUpMetaDataForContentPage(model, getContentPageForLabelOrId(UPDATE_PROFILE_CMS_PAGE));
		model.addAttribute(BREADCRUMBS_ATTR, accountBreadcrumbBuilder.getBreadcrumbs(TEXT_ACCOUNT_PROFILE));
		model.addAttribute(ThirdPartyConstants.SeoRobots.META_ROBOTS, ThirdPartyConstants.SeoRobots.NOINDEX_NOFOLLOW);
		return getViewForPage(model);
	}

	@RequestMapping(value = "/update-profile", method = RequestMethod.POST)
	@RequireHardLogIn
	public String updateProfile(final CustomRegisterForm form, final BindingResult bindingResult, final Model model,
			final RedirectAttributes redirectAttributes, final String aidField, final HttpServletRequest request)
			throws CMSItemNotFoundException {
		promotionItem(model);
		personalInfoValidator.validate(form, bindingResult);
		if (bindingResult.hasErrors()) {
			GlobalMessages.addErrorMessage(model, "form.global.error");
			if (form.getContactAddress().getCountryIso() != null) {
				model.addAttribute("regions",
						i18NFacade.getRegionsForCountryIso(form.getContactAddress().getCountryIso()));
			}
			model.addAttribute("CustomRegisterForm", form);
			model.addAttribute("nowPage", "update-profile");
			storeCmsPageInModel(model, getContentPageForLabelOrId("update-profile"));
			return getViewForPage(model);
		}
		try {
			if (aidField == null) {
				form.setHaveFinancialReport(false);
				form.setProvideTradeReference(false);
			} else {
				form.setHaveFinancialReport(aidField.indexOf("haveFinancialReport") != -1);
				form.setProvideTradeReference(aidField.indexOf("provideTradeReference") != -1);
			}
			CountryModel contactCountry = null;
			if (null != form.getContactAddress() && StringUtils.isNotBlank(form.getContactAddress().getCountryIso())){
				contactCountry = commonI18NService.getCountry(form.getContactAddress().getCountryIso());
			}

//			final String contactCountryIso = form.getContactAddress().getCountryIso();
//			final String contactRegionIso = form.getContactAddress().getRegionIso();
//			final CountryModel contactCountry = commonI18NService.getCountry(contactCountryIso);

			final CustomerModel user = (CustomerModel) userService.getCurrentUser();

			AddressModel am2;
			if (form.getContactAddress().getAddressId() != null && form.getContactAddress().getAddressId() != "") {
				am2 = modelService.get(PK.fromLong(Long.valueOf(form.getContactAddress().getAddressId())));
			} else {
				am2 = modelService.create(AddressModel.class);
				am2.setOwner(user);
				am2.setVisibleInAddressBook(false);
				am2.setContactAddress(true);
			}
			am2.setLastname(form.getName());
			am2.setCountry(contactCountry);

//			final String contactRegionIso = form.getContactAddress().getRegionIso();
			if (null != form.getContactAddress() && StringUtils.isNotBlank(form.getContactAddress().getRegionIso())){
				am2.setRegion(commonI18NService.getRegion(contactCountry, form.getContactAddress().getRegionIso()));
			}else {
				am2.setRegion(null);
			}
//			if (contactRegionIso != null && contactRegionIso != "") {
//				am2.setRegion(commonI18NService.getRegion(contactCountry, contactRegionIso));
//			}
//			else
//			{
//				am2.setRegion(null);
//			}
			am2.setTown(form.getContactAddress().getTownCity());
			am2.setPostalcode(form.getContactAddress().getPostcode());

			user.setSessionLanguage(commonI18NService.getLanguage(form.getLanguage()));
			user.setSessionCurrency(commonI18NService.getCurrency(form.getCurrency()));
			user.setName(form.getName());
			user.setCompanyType(form.getCompanyType());
			user.setCompanyName(form.getCompanyName());
			user.setEstablishedIn(form.getEstablishedIn());
			user.setRevenue(form.getRevenue());
			user.setEmployeesNo(form.getEmployeesNo());
			user.setLimitCreditAmount(form.getLimitCreditAmount());
			user.setVatNo(form.getVatNo());
			user.setHaveFinancialReport(form.getHaveFinancialReport());
			user.setProvideTradeReference(form.getProvideTradeReference());

			modelService.saveAll(user, am2);

			GlobalMessages.addInfoMessage(model, "form.global.success");
			if (null != form.getContactAddress() && StringUtils.isNotBlank(form.getContactAddress().getCountryIso())){
				model.addAttribute("regions", i18NFacade.getRegionsForCountryIso(form.getContactAddress().getCountryIso()));
			}
			model.addAttribute("CustomRegisterForm", form);
			model.addAttribute("nowPage", "update-profile");
			storeCmsPageInModel(model, getContentPageForLabelOrId("update-profile"));

			final String previousLanguage = storeSessionFacade.getCurrentLanguage().getIsocode();
			storeSessionFacade.setCurrentLanguage(form.getLanguage());
			if (!userFacade.isAnonymousUser()) {
				userFacade.syncSessionLanguage();
			}

			return REDIRECT_PREFIX + StringUtils.replace(
					(String) request.getSession().getAttribute(StorefrontFilter.ORIGINAL_REFERER),
					"/" + previousLanguage + "/", "/" + storeSessionFacade.getCurrentLanguage().getIsocode() + "/");
		} catch (final Exception exception) {
			if (null != form.getContactAddress() && StringUtils.isNotBlank(form.getContactAddress().getCountryIso())){
				model.addAttribute("regions", i18NFacade.getRegionsForCountryIso(form.getContactAddress().getCountryIso()));
			}else {
				model.addAttribute("regions", "");
			}
			model.addAttribute("CustomRegisterForm", form);
			model.addAttribute("nowPage", "update-profile");
			storeCmsPageInModel(model, getContentPageForLabelOrId("update-profile"));
			GlobalMessages.addErrorMessage(model, "fail to edit: " + exception);
			return getViewForPage(model);
		}
	}

	protected String getReturnRedirectUrlWithoutReferer(final HttpServletRequest request) {
		final String originalReferer = (String) request.getSession().getAttribute(StorefrontFilter.ORIGINAL_REFERER);
		if (StringUtils.isNotBlank(originalReferer)) {
			return REDIRECT_PREFIX + originalReferer;
		}

		final String referer = StringUtils.remove(request.getRequestURL().toString(), request.getServletPath());
		if (referer != null && !referer.isEmpty()) {
			return REDIRECT_PREFIX + referer;
		}
		return REDIRECT_PREFIX + '/';
	}

	@RequestMapping(value = "/update-password", method = RequestMethod.GET)
	@RequireHardLogIn
	public String updatePassword(final Model model) throws CMSItemNotFoundException {
		promotionItem(model);
		final UpdatePasswordForm updatePasswordForm = new UpdatePasswordForm();
		model.addAttribute("nowPage", "update-password");
		model.addAttribute("updatePasswordForm", updatePasswordForm);

		storeCmsPageInModel(model, getContentPageForLabelOrId(UPDATE_PASSWORD_CMS_PAGE));
		setUpMetaDataForContentPage(model, getContentPageForLabelOrId(UPDATE_PASSWORD_CMS_PAGE));

		model.addAttribute(BREADCRUMBS_ATTR,
				accountBreadcrumbBuilder.getBreadcrumbs("text.account.profile.updatePasswordForm"));
		model.addAttribute(ThirdPartyConstants.SeoRobots.META_ROBOTS, ThirdPartyConstants.SeoRobots.NOINDEX_NOFOLLOW);
		return getViewForPage(model);
	}

	@RequestMapping(value = "/update-password", method = RequestMethod.POST)
	@RequireHardLogIn
	public String updatePassword(final UpdatePasswordForm updatePasswordForm, final BindingResult bindingResult,
			final Model model, final RedirectAttributes redirectAttributes) throws CMSItemNotFoundException {
		promotionItem(model);
		model.addAttribute("nowPage", "update-password");
		getPasswordValidator().validate(updatePasswordForm, bindingResult);
		if (!bindingResult.hasErrors()) {
			if (updatePasswordForm.getNewPassword().equals(updatePasswordForm.getCheckNewPassword())) {
				try {
					customerFacade.changePassword(updatePasswordForm.getCurrentPassword(),
							updatePasswordForm.getNewPassword());
				} catch (final PasswordMismatchException localException) {
					bindingResult.rejectValue("currentPassword", PROFILE_CURRENT_PASSWORD_INVALID, new Object[] {},
							PROFILE_CURRENT_PASSWORD_INVALID);
				}
			} else {
				bindingResult.rejectValue("checkNewPassword", "validation.checkPwd.equals", new Object[] {},
						"validation.checkPwd.equals");
			}
		}

		if (bindingResult.hasErrors()) {
			GlobalMessages.addErrorMessage(model, FORM_GLOBAL_ERROR);
			storeCmsPageInModel(model, getContentPageForLabelOrId(UPDATE_PASSWORD_CMS_PAGE));
			setUpMetaDataForContentPage(model, getContentPageForLabelOrId(UPDATE_PASSWORD_CMS_PAGE));

			model.addAttribute(BREADCRUMBS_ATTR,
					accountBreadcrumbBuilder.getBreadcrumbs("text.account.profile.updatePasswordForm"));
			return getViewForPage(model);
		} else {
			// GlobalMessages.addFlashMessage(redirectAttributes,
			// GlobalMessages.CONF_MESSAGES_HOLDER,
			// "text.account.confirmation.password.updated", null);
			// return REDIRECT_TO_PASSWORD_UPDATE_PAGE;

			storeCmsPageInModel(model, getContentPageForLabelOrId(UPDATE_PASSWORD_CMS_PAGE));
			setUpMetaDataForContentPage(model, getContentPageForLabelOrId(UPDATE_PASSWORD_CMS_PAGE));
			model.addAttribute(BREADCRUMBS_ATTR,
					accountBreadcrumbBuilder.getBreadcrumbs("text.account.profile.updatePasswordForm"));
			return "/pages/account/reLogin";
		}
	}

	@Resource
	private BaseStoreService baseStoreService;
	@Resource
	private Converter<OrderModel, OrderHistoryData> orderHistoryConverter;
	@Resource
	private PagedFlexibleSearchService pagedFlexibleSearchService;
	@Resource
	private FlexibleSearchService flexibleSearchService;

	@RequestMapping(value = "/orders", method = RequestMethod.GET)
	@RequireHardLogIn
	public String orders(@RequestParam(value = "page", defaultValue = "0") final int page,
			@RequestParam(value = "show", defaultValue = "Page") final ShowMode showMode,
			@RequestParam(value = "sort", required = false) final String sortCode,
			@RequestParam(value = "key", required = false) final String key, final Model model)
			throws CMSItemNotFoundException {
		promotionItem(model);
		final PageableData pageableData = createPageableData(page, 10, sortCode, showMode);
		final Map<String, Object> queryParams = new HashMap<String, Object>();
		queryParams.put("customer", userService.getCurrentUser());
		queryParams.put("store", baseStoreService.getCurrentBaseStore());

		String FIND_ORDERS_BY_CUSTOMER_STORE_QUERY = "SELECT {" + OrderModel.PK + "},{" + OrderModel.CREATIONTIME
				+ "}, {" + OrderModel.CODE + "} FROM {" + OrderModel._TYPECODE + "} WHERE {" + OrderModel.USER
				+ "} = ?customer AND {" + OrderModel.VERSIONID + "} IS NULL AND {" + OrderModel.STORE + "} = ?store ";

		if (key != null) {
			FIND_ORDERS_BY_CUSTOMER_STORE_QUERY += " AND {" + OrderModel.CODE + "} like '%" + key + "%' ";
		}

		if (sortCode != null && sortCode.equals("total")) {
			FIND_ORDERS_BY_CUSTOMER_STORE_QUERY += " ORDER BY {" + OrderModel.TOTALPRICE + "} DESC,{"
					+ OrderModel.CREATIONTIME + "} DESC, {" + OrderModel.PK + "}";
		} else if (sortCode != null && sortCode.equals("status")) {
			FIND_ORDERS_BY_CUSTOMER_STORE_QUERY += " ORDER BY {" + OrderModel.STATUSINFO + "},{"
					+ OrderModel.CREATIONTIME + "} DESC, {" + OrderModel.PK + "}";
		} else if (sortCode != null && sortCode.equals("code")) {
			FIND_ORDERS_BY_CUSTOMER_STORE_QUERY += " ORDER BY {" + OrderModel.CODE + "},{" + OrderModel.CREATIONTIME
					+ "} DESC, {" + OrderModel.PK + "}";
		} else {
			FIND_ORDERS_BY_CUSTOMER_STORE_QUERY += " ORDER BY {" + OrderModel.CREATIONTIME + "} DESC, {" + OrderModel.PK
					+ "}";
		}

		final StringBuilder queryBuilder = new StringBuilder(FIND_ORDERS_BY_CUSTOMER_STORE_QUERY);
		final SortQueryData result1 = new SortQueryData();
		result1.setQuery(queryBuilder.toString());
		SortQueryData selectedSortQuery = null;
		final List<SortQueryData> sortQueries = Arrays.asList(result1);
		for (final SortQueryData sortQueryData : sortQueries) {
			selectedSortQuery = sortQueryData;
		}

		final String query = selectedSortQuery.getQuery();
		final FlexibleSearchQuery searchQuery = new FlexibleSearchQuery(query);
		searchQuery.addQueryParameters(queryParams);
		searchQuery.setNeedTotal(true);
		searchQuery.setStart(pageableData.getCurrentPage() * pageableData.getPageSize());
		searchQuery.setCount(pageableData.getPageSize());
		final SearchResult<OrderModel> searchResult = flexibleSearchService.search(searchQuery);
		final SearchPageData<OrderModel> orderResults = new SearchPageData<OrderModel>();
		orderResults.setResults(searchResult.getResult());
		final PaginationData paginationData = new PaginationData();
		paginationData.setPageSize(pageableData.getPageSize());
		paginationData.setSort(pageableData.getSort());
		paginationData.setTotalNumberOfResults(searchResult.getTotalCount());
		paginationData.setNumberOfPages(
				(int) Math.ceil(((double) paginationData.getTotalNumberOfResults()) / paginationData.getPageSize()));
		paginationData.setCurrentPage(
				Math.max(0, Math.min(paginationData.getNumberOfPages(), pageableData.getCurrentPage())));
		orderResults.setPagination(paginationData);
		orderResults.getPagination().setSort(selectedSortQuery.getSortCode());
		final String selectedSortCode = selectedSortQuery.getSortCode();
		final List<SortData> result = new ArrayList<SortData>(sortQueries.size());
		for (final SortQueryData sortQuery : sortQueries) {
			final SortData sortData = new SortData();
			sortData.setCode(sortQuery.getSortCode());
			sortData.setName(sortQuery.getSortName());
			sortData.setSelected(selectedSortCode != null && selectedSortCode.equals(sortQuery.getSortCode()));
			result.add(sortData);
		}
		orderResults.setSorts(result);

		final SearchPageData<OrderHistoryData> searchPageData = new SearchPageData<OrderHistoryData>();
		searchPageData.setPagination(orderResults.getPagination());
		searchPageData.setSorts(orderResults.getSorts());
		searchPageData.setResults(Converters.convertAll(orderResults.getResults(), orderHistoryConverter));

		populateModel(model, searchPageData, showMode);
		storeCmsPageInModel(model, getContentPageForLabelOrId(ORDER_HISTORY_CMS_PAGE));
		setUpMetaDataForContentPage(model, getContentPageForLabelOrId(ORDER_HISTORY_CMS_PAGE));
		model.addAttribute(BREADCRUMBS_ATTR, accountBreadcrumbBuilder.getBreadcrumbs("text.account.orderHistory"));
		model.addAttribute(ThirdPartyConstants.SeoRobots.META_ROBOTS, ThirdPartyConstants.SeoRobots.NOINDEX_NOFOLLOW);
		model.addAttribute("nowPage", "orders");
		model.addAttribute("sort", sortCode);
		return getViewForPage(model);
	}

	@Resource
	private Converter<AddressModel, AddressData> addressConverter;

	@RequestMapping(value = "/address-book", method = RequestMethod.GET)
	@RequireHardLogIn
	public String getAddressBook(final Model model) throws CMSItemNotFoundException {
		promotionItem(model);
		model.addAttribute("nowPage", "address-book");

		final List<AddressData> result = new ArrayList<AddressData>();
		final CustomerModel currentUser = (CustomerModel) userService.getCurrentUser();
		final Collection<AddressModel> addresses = customerAccountService.getAddressBookDeliveryEntries(currentUser);
		if (CollectionUtils.isNotEmpty(addresses)) {
			final AddressModel defaultAddress = customerAccountService.getDefaultAddress(currentUser);
			for (final AddressModel address : addresses) {
				if (defaultAddress != null && defaultAddress.getPk() != null
						&& defaultAddress.getPk().equals(address.getPk())) {
					final AddressData addressData = addressConverter.convert(address);
					addressData.setDefaultAddress(true);
					result.add(0, addressData);
				} else {
					result.add(addressConverter.convert(address));
				}
			}
		}
		model.addAttribute(ADDRESS_DATA_ATTR, result);

		storeCmsPageInModel(model, getContentPageForLabelOrId(ADDRESS_BOOK_CMS_PAGE));
		setUpMetaDataForContentPage(model, getContentPageForLabelOrId(ADDRESS_BOOK_CMS_PAGE));
		model.addAttribute(BREADCRUMBS_ATTR, accountBreadcrumbBuilder.getBreadcrumbs(TEXT_ACCOUNT_ADDRESS_BOOK));
		model.addAttribute(ThirdPartyConstants.SeoRobots.META_ROBOTS, ThirdPartyConstants.SeoRobots.NOINDEX_NOFOLLOW);
		return getViewForPage(model);
	}

	@Resource
	private DefaultCustomerCreditAccountService defaultCustomerCreditAccountService;

	@RequestMapping(value = "/credit", method = RequestMethod.GET)
	@RequireHardLogIn
	public String getCreditAccount(final Model model) throws CMSItemNotFoundException {
		promotionItem(model);
		model.addAttribute("nowPage", "credit");
		model.addAttribute("customerCreditAccountData", defaultCustomerCreditAccountService.getCustomerCreditAccount());
		storeCmsPageInModel(model, getContentPageForLabelOrId("address-book"));
		setUpMetaDataForContentPage(model, getContentPageForLabelOrId("address-book"));
		model.addAttribute(BREADCRUMBS_ATTR, accountBreadcrumbBuilder.getBreadcrumbs(TEXT_ACCOUNT_ADDRESS_BOOK));
		model.addAttribute(ThirdPartyConstants.SeoRobots.META_ROBOTS, ThirdPartyConstants.SeoRobots.NOINDEX_NOFOLLOW);
		return "/pages/account/accountCreditPage";
	}

	@Resource
	private BusinessProcessService businessProcessService;

	@RequestMapping(value = "/confirm/" + ORDER_CODE_PATH_VARIABLE_PATTERN, method = RequestMethod.GET)
	@RequireHardLogIn
	public String listConfirm(@RequestParam(value = "page", defaultValue = "0") final int page,
			@RequestParam(value = "show", defaultValue = "Page") final ShowMode showMode,
			@RequestParam(value = "sort", required = false) final String sortCode,
			@RequestParam(value = "key", required = false) final String key, final Model model,
			@PathVariable("orderCode") final String orderCode, final String confirm) throws CMSItemNotFoundException {
		promotionItem(model);
		confirm(orderCode, confirm);
		return orders(page, showMode, sortCode, key, model);
	}

	@RequestMapping(value = "/detailsConfirm/" + ORDER_CODE_PATH_VARIABLE_PATTERN, method = RequestMethod.GET)
	@RequireHardLogIn
	public String detailsConfirm(@PathVariable("orderCode") final String orderCode, final Model model,
			final RedirectAttributes redirectModel, final String confirm) throws CMSItemNotFoundException {
		confirm(orderCode, confirm);
		return order(orderCode, model, redirectModel);
	}

	@RequestMapping(value = "/extendedPickup/" + ORDER_CODE_PATH_VARIABLE_PATTERN, method = RequestMethod.GET)
	@RequireHardLogIn
	public String extendedPickupDate(@PathVariable("orderCode") final String orderCode, final Model model,
			final RedirectAttributes redirectModel, final Integer days) throws CMSItemNotFoundException {
		promotionItem(model);
		final BaseStoreModel baseStoreModel = baseStoreService.getCurrentBaseStore();
		final OrderModel order = customerAccountService.getOrderForCode((CustomerModel) userService.getCurrentUser(),
				orderCode, baseStoreModel);

		final Integer maxday = Integer.valueOf(Config.getParameter("cancel.order.day"));

		final Calendar c = Calendar.getInstance();
		c.setTime(order.getPickUpDate());
		c.set(Calendar.DATE, c.get(Calendar.DATE) + days);
		final Date date = c.getTime();
		if (order.getPickupDateOfExtended() == null && maxday >= days) {
			final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String waitDelivereyDate = sdf.format(date);
			final Calendar ca = Calendar.getInstance();
			try {
				ca.setTime(sdf.parse(waitDelivereyDate));
				ca.add(Calendar.DATE, getTotalPriceForCart(order));// num为增加的天数，可以改变的
				waitDelivereyDate = sdf.format(ca.getTime());
				final Date endDate = sdf.parse(waitDelivereyDate);
				order.setWaitDeliveriedDate(endDate);
				order.setPickupDateOfExtended(date);
				modelService.save(order);
				modelService.refresh(order);
			} catch (final ParseException e1) {
				e1.printStackTrace();
			}
		}

		return order(orderCode, model, redirectModel);
	}

	@Resource
	private AcerchemTrayService acerchemTrayService;

	private Integer getTotalPriceForCart(final AbstractOrderModel order) {
		CountryModel countryModel = null;
		String postCode = null;
		CountryTrayFareConfModel countryTrayFareConf = null;
		BigDecimal totalTrayAmount = BigDecimal.ZERO;
		if (order != null) {
			for (final AbstractOrderEntryModel aoe : order.getEntries()) {
				if (aoe.getDeliveryPointOfService().getAddress() != null) {
					countryModel = aoe.getOrder().getDeliveryAddress().getCountry();
				}
				final ProductModel productModel = aoe.getProduct();
				final String unitCalculateRato = productModel.getUnitCalculateRato();
				if (ObjectUtils.isEmpty(unitCalculateRato)) {

				}
				final Long quantity = (aoe.getQuantity()) * (Long.parseLong(aoe.getProduct().getNetWeight()));
				final BigDecimal entryTrayAmount = BigDecimal.valueOf(quantity)
						.divide(new BigDecimal(unitCalculateRato), BigDecimal.ROUND_HALF_UP, BigDecimal.ROUND_DOWN);
				totalTrayAmount = totalTrayAmount.add(entryTrayAmount);
			}
		}
		if (countryModel != null) {
			countryTrayFareConf = acerchemTrayService.getPriceByCountryAndTray(countryModel, postCode,
					(int) Math.ceil(totalTrayAmount.doubleValue()));
		}
		if (countryTrayFareConf != null) {
			return countryTrayFareConf.getDeliveriedDay();
		}
		return 0;
	}

	@Resource
	private AcerchemStockService acerchemStockService;

	public void confirm(final String orderCode, final String confirm) {
		final BaseStoreModel baseStoreModel = baseStoreService.getCurrentBaseStore();
		final OrderModel order = customerAccountService.getOrderForCode((CustomerModel) userService.getCurrentUser(),
				orderCode, baseStoreModel);
		final Collection<OrderProcessModel> orderProcessList = order.getOrderProcess();
		if (orderProcessList != null) {
			String fulfilmentProcessDefinitionName = "";
			for (final OrderProcessModel orderProcess : orderProcessList) {
				fulfilmentProcessDefinitionName = orderProcess.getCode();
				break;
			}

			if (confirm.equals("order") && !order.getCustomerConfirm()) {
				final String eventID = new StringBuilder().append(fulfilmentProcessDefinitionName)
						.append("_ConfirmActionEvent").toString();
				final BusinessProcessEvent event = BusinessProcessEvent.builder(eventID)
						.withChoice("waitForCustomerConfirm").build();
				final Boolean falg = businessProcessService.triggerEvent(event);
				if (falg) {
					order.setCustomerConfirm(true);
					modelService.save(order);
					modelService.refresh(order);
				}
			}
			if (confirm.equals("receipt")) {
				final String eventID = new StringBuilder().append(fulfilmentProcessDefinitionName)
						.append("_ConfirmConsignmentActionEvent").toString();
				final BusinessProcessEvent event = BusinessProcessEvent.builder(eventID)
						.withChoice("waitForCustomerConfirmConsignment").build();
				final Boolean falg = businessProcessService.triggerEvent(event);
				if (falg) {
					order.setCustomerConfirmDelivery(true);
					modelService.save(order);
					modelService.refresh(order);
				}
			}
			if (confirm.equals("payment") && !order.getCustomerConfirmPay()) {
				final String eventID = new StringBuilder().append(fulfilmentProcessDefinitionName)
						.append("_ConfirmPayActionEvent").toString();
				final BusinessProcessEvent event = BusinessProcessEvent.builder(eventID)
						.withChoice("waitForCustomerConfirmPay").build();
				final Boolean falg = businessProcessService.triggerEvent(event);
				if (falg) {
					order.setCustomerConfirmPay(true);
					modelService.save(order);
					modelService.refresh(order);
				}
			}

			final Date pickupDate = order.getPickupDateOfExtended() != null ? order.getPickupDateOfExtended()
					: order.getPickUpDate();
			final Calendar c = Calendar.getInstance();
			c.setTime(pickupDate);
			c.set(Calendar.DATE, c.get(Calendar.DATE) - Integer.valueOf(Config.getParameter("cancel.order.day")));
			final Date date = c.getTime();

			final Calendar today = Calendar.getInstance();
			final Date todaydate = today.getTime();

			if (confirm.equals("cancel") && todaydate.before(date)) {
				LOG.info(">>>>>>>>>cancel order start>>>>>>>>>");
				
				eventService.publishEvent(new OrderCancelledEvent(orderProcessList.iterator().next()));
				
				final Transaction tx = Transaction.current();
				tx.begin();
				
				order.setStatus(OrderStatus.CANCELLED);
				modelService.save(order);
				modelService.refresh(order);
				acerchemStockService.releaseStock(order);
				if (order.getPaymentMode().getCode().equals("InvoicePayment")) {
					tx.commit();
					LOG.info(">>>>>>>>>cancel order end>>>>>>>>>");
				} else {
					final CustomerCreditAccountModel customerCreditAccount = defaultCustomerCreditAccountService.updateCreditAccountRepaymentByOrder(order,true);

					if (customerCreditAccount == null) {
						tx.rollback();
						LOG.info(">>>>>>>>>cancel order can't proceed,because update customerCreditAccount is null >>>>>>>>>");
						
					}else{
						tx.commit();
						LOG.info(">>>>>>>>>cancel order end>>>>>>>>>");
					}

				}
				

			}

		}
	}

	@Resource
	private Converter<ProductModel, ProductData> productConverter;

	public void promotionItem(final Model model) {
		final String SQL = "SELECT PK FROM {" + ProductModel._TYPECODE + "} WHERE {" + ProductModel.PROMOTIONITEM
				+ "} =true ";// limit 1,15
		final FlexibleSearchQuery query = new FlexibleSearchQuery(SQL);
		query.setNeedTotal(false);
		query.setCount(15);
		final SearchResult<ProductModel> result = flexibleSearchService.search(query);
		model.addAttribute("promotionItem", Converters.convertAll(result.getResult(), productConverter));

	}

}