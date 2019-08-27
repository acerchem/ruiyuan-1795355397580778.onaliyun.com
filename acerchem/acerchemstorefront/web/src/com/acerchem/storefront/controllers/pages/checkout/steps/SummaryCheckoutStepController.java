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
package com.acerchem.storefront.controllers.pages.checkout.steps;

import de.hybris.platform.acceleratorservices.enums.CheckoutPciOptionEnum;
import de.hybris.platform.acceleratorstorefrontcommons.annotations.PreValidateCheckoutStep;
import de.hybris.platform.acceleratorstorefrontcommons.annotations.PreValidateQuoteCheckoutStep;
import de.hybris.platform.acceleratorstorefrontcommons.annotations.RequireHardLogIn;
import de.hybris.platform.acceleratorstorefrontcommons.checkout.steps.CheckoutStep;
import de.hybris.platform.acceleratorstorefrontcommons.constants.WebConstants;
import de.hybris.platform.acceleratorstorefrontcommons.controllers.pages.checkout.steps.AbstractCheckoutStepController;
import de.hybris.platform.acceleratorstorefrontcommons.controllers.util.GlobalMessages;
import de.hybris.platform.acceleratorstorefrontcommons.forms.AddressForm;
import com.acerchem.storefront.data.PlaceOrderForm;
import de.hybris.platform.cms2.exceptions.CMSItemNotFoundException;
import de.hybris.platform.commercefacades.order.data.CartData;
import de.hybris.platform.commercefacades.order.data.OrderData;
import de.hybris.platform.commercefacades.order.data.OrderEntryData;
import de.hybris.platform.commercefacades.product.ProductOption;
import de.hybris.platform.commercefacades.product.data.ProductData;
import de.hybris.platform.commercefacades.user.data.AddressData;
import de.hybris.platform.commercefacades.user.data.CountryData;
import de.hybris.platform.commerceservices.order.CommerceCartModificationException;
import de.hybris.platform.core.model.order.CartModel;
import de.hybris.platform.order.InvalidCartException;
import de.hybris.platform.payment.AdapterException;
import com.acerchem.core.service.AcerchemStockService;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.acerchem.facades.facades.AcerchemCheckoutFacade;
import com.acerchem.facades.facades.AcerchemOrderException;
import com.acerchem.facades.facades.AcerchemTrayFacade;
import com.acerchem.storefront.controllers.ControllerConstants;


@Controller
@RequestMapping(value = "/checkout/multi/summary")
public class SummaryCheckoutStepController extends AbstractCheckoutStepController
{
	private static final Logger LOGGER = Logger.getLogger(SummaryCheckoutStepController.class);

	private static final String SUMMARY = "summary";
	
	 @Resource(name = "defaultAcerchemCheckoutFacade")
	 private AcerchemCheckoutFacade acerchemCheckoutFacade;
	 
	@Resource
	private AcerchemTrayFacade acerchemTrayFacade;
    @Resource
	private AcerchemStockService acerchemStockService;
	@RequestMapping(value = "/view", method = RequestMethod.GET)
	@RequireHardLogIn
	@Override
	@PreValidateQuoteCheckoutStep
	@PreValidateCheckoutStep(checkoutStep = SUMMARY)
	public String enterStep(final Model model, final RedirectAttributes redirectAttributes) throws CMSItemNotFoundException, // NOSONAR
			CommerceCartModificationException
	{
		final CartData cartData = acerchemCheckoutFacade.getCheckoutCart();
		
		/*if (cartData.getEntries() != null && !cartData.getEntries().isEmpty())
		{
			for (final OrderEntryData entry : cartData.getEntries())
			{
				final String productCode = entry.getProduct().getCode();
				final ProductData product = getProductFacade().getProductForCodeAndOptions(
						productCode,
						Arrays.asList(ProductOption.BASIC, ProductOption.PRICE, ProductOption.VARIANT_MATRIX_BASE,
								ProductOption.PRICE_RANGE));
				entry.setProduct(product);
			}
		}*/
		
		
		try {
			model.addAttribute("deliveryMethods", acerchemCheckoutFacade.getAllDeliveryModes());
		} catch (AcerchemOrderException e) {
			// TODO Auto-generated catch block
//			e.printStackTrace();
			LOGGER.error(e.getMessage(),e);
		}
		if (cartData.getDeliveryMode()!=null) {
			model.addAttribute("paymentInfos", acerchemCheckoutFacade.getSupportedCardTypes(cartData.getDeliveryMode().getCode()));
		}else{
			model.addAttribute("paymentInfos", acerchemCheckoutFacade.getSupportedCardTypes("DELIVERY_MENTION"));
		}
		cartData.setDeliveryMode(acerchemCheckoutFacade.getDeliveryModes());
		model.addAttribute("cartData", cartData);
		
		model.addAttribute("addressForm", new AddressForm());
		model.addAttribute("allItems", cartData.getEntries());
				
		//cartData.setDeliveryMode(acerchemCheckoutFacade.getDeliveryModes());
		
		model.addAttribute("deliveryMode", acerchemCheckoutFacade.getDeliveryModes());
		model.addAttribute("paymentInfo", cartData.getPaymentInfo());
		model.addAttribute("paymentModeData", cartData.getPaymentModeData());
		
		if (cartData.getDeliveryMode()!=null&&"DELIVERY_MENTION".equals(cartData.getDeliveryMode().getCode())) {
			model.addAttribute("deliveryAddress", acerchemCheckoutFacade.getDeliveryAddresses().get(0));
			model.addAttribute("deliveryAddresses", acerchemCheckoutFacade.getDeliveryAddresses());
		}
		
		if (cartData.getDeliveryMode()!=null&&"DELIVERY_GROSS".equals(cartData.getDeliveryMode().getCode())) {
			model.addAttribute("deliveryAddress", cartData.getDeliveryAddress());
			model.addAttribute("deliveryAddresses", getDeliveryAddresses(cartData.getDeliveryAddress()));
			 double deliveryCost = 0;
			try {
				deliveryCost = acerchemTrayFacade.getTotalPriceForCart(cartData, cartData.getDeliveryAddress());
			} catch (AcerchemOrderException e) {
				// TODO Auto-generated catch block
//				e.printStackTrace();
				LOGGER.error(e.getMessage(),e);
			}
			 CartModel cartModel = acerchemCheckoutFacade.getCartModel();
		     cartData.setDeliveryCost(acerchemCheckoutFacade.createPrice(cartModel, deliveryCost));
		     
		    
		     if (deliveryCost>0){
		    	 
		    	 double total=0;
		    	 total= cartData.getDeliveryCost().getValue().doubleValue()+cartData.getTotalPrice().getValue().doubleValue();
		    	 
		    	 cartData.setTotalPrice(acerchemCheckoutFacade.createPrice(cartModel, total));
		     }
		     
		     if (cartData.getPickUpdate() != null){
			      SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
					String waitDelivereyDate = cartData.getPickUpdate();
					Calendar ca = Calendar.getInstance();
					try {
						ca.setTime(sdf1.parse(waitDelivereyDate));
						ca.add(Calendar.DATE, acerchemCheckoutFacade.getTotalPriceForCart(cartData));// 
						waitDelivereyDate = sdf1.format(ca.getTime());
						cartData.setWaitDeliveiedDate(waitDelivereyDate);
						//Date endDate = sdf.parse(waitDelivereyDate);
						//orderModel.setWaitDeliveiedDate(endDate);
					} catch (ParseException e1) {
						// TODO Auto-generated catch block
//						e1.printStackTrace();
						LOGGER.error(e1.getMessage(),e1);
					}
			  }else{
				  SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
				  String waitDelivereyDate = cartData.getPickUpdate();
				  Calendar ca = Calendar.getInstance();
				  try {
					  ca.setTime(new Date());
					  ca.add(Calendar.DATE, acerchemCheckoutFacade.getTotalPriceForCart(cartData));//
					  waitDelivereyDate = sdf1.format(ca.getTime());
					  cartData.setWaitDeliveiedDate(waitDelivereyDate);
				  } catch (Exception e1) {
					  LOGGER.error(e1.getMessage(),e1);
				  }
			  }
		}
		

		// Only request the security code if the SubscriptionPciOption is set to Default.
		final boolean requestSecurityCode = CheckoutPciOptionEnum.DEFAULT
				.equals(getCheckoutFlowFacade().getSubscriptionPciOption());
		model.addAttribute("requestSecurityCode", Boolean.valueOf(requestSecurityCode));

		model.addAttribute(new PlaceOrderForm());

		storeCmsPageInModel(model, getContentPageForLabelOrId(MULTI_CHECKOUT_SUMMARY_CMS_PAGE_LABEL));
		setUpMetaDataForContentPage(model, getContentPageForLabelOrId(MULTI_CHECKOUT_SUMMARY_CMS_PAGE_LABEL));
		model.addAttribute(WebConstants.BREADCRUMBS_KEY,
				getResourceBreadcrumbBuilder().getBreadcrumbs("checkout.multi.summary.breadcrumb"));
		model.addAttribute("metaRobots", "noindex,nofollow");
		setCheckoutStepLinksForModel(model, getCheckoutStep());
		return ControllerConstants.Views.Pages.MultiStepCheckout.CheckoutSummaryPage;
	}


	@RequestMapping(value = "/placeOrder")
	@PreValidateQuoteCheckoutStep
	@RequireHardLogIn
	public String placeOrder(@ModelAttribute("placeOrderForm") final PlaceOrderForm placeOrderForm, final Model model,
			final HttpServletRequest request, final RedirectAttributes redirectModel) throws CMSItemNotFoundException, // NOSONAR
			InvalidCartException, CommerceCartModificationException
	{
		if (validateOrderForm(placeOrderForm, model))
		{
			return enterStep(model, redirectModel);
		}

		//Validate the cart
		if (acerchemStockService.validateCart(redirectModel))
		{
			// Invalid cart. Bounce back to the cart page.
			return REDIRECT_PREFIX + "/cart";
		}

		/*// authorize, if failure occurs don't allow to place the order
		boolean isPaymentUthorized = false;
		try
		{
			isPaymentUthorized = getCheckoutFacade().authorizePayment(placeOrderForm.getSecurityCode());
		}
		catch (final AdapterException ae)
		{
			// handle a case where a wrong paymentProvider configurations on the store see getCommerceCheckoutService().getPaymentProvider()
			LOGGER.error(ae.getMessage(), ae);
		}
		if (!isPaymentUthorized)
		{
			GlobalMessages.addErrorMessage(model, "checkout.error.authorization.failed");
			return enterStep(model, redirectModel);
		}*/

		final OrderData orderData;
		try
		{
			orderData = acerchemCheckoutFacade.placeOrder();
		}
		catch (final Exception e)
		{
			LOGGER.error("Failed to place Order", e);
			GlobalMessages.addErrorMessage(model, "checkout.placeOrder.failed");
			return enterStep(model, redirectModel);
		}

		return redirectToOrderConfirmationPage(orderData);
	}

	/**
	 * Validates the order form before to filter out invalid order states
	 *
	 * @param placeOrderForm
	 *           The spring form of the order being submitted
	 * @param model
	 *           A spring Model
	 * @return True if the order form is invalid and false if everything is valid.
	 */
	protected boolean validateOrderForm(final PlaceOrderForm placeOrderForm, final Model model)
	{
		//final String securityCode = placeOrderForm.getSecurityCode();
		boolean invalid = false;
		
		if (!placeOrderForm.isTermsCheck())
		{
			GlobalMessages.addErrorMessage(model, "checkout.error.terms.not.accepted");
			invalid = true;
			return invalid;
		}

		final CartData cartData = acerchemCheckoutFacade.getCheckoutCart();

		if (acerchemCheckoutFacade.getDeliveryModes() == null)
		{
			GlobalMessages.addErrorMessage(model, "checkout.deliveryMethod.notSelected");
			invalid = true;
			return invalid;
		}
		
		if (cartData.getDeliveryAddress() == null)
		{
			GlobalMessages.addErrorMessage(model, "checkout.deliveryAddress.notSelected");
			invalid = true;
			return invalid;
		}else {
			if ("DELIVERY_GROSS".equalsIgnoreCase(acerchemCheckoutFacade.getDeliveryModes().getCode())){
				
				AddressData address = cartData.getDeliveryAddress() ;
				
				CountryData countryData = address.getCountry();
				
				try {
					acerchemCheckoutFacade.validateCartAddress(countryData);

					if(acerchemTrayFacade.getTotalPriceForCart(cartData, cartData.getDeliveryAddress()) <= 0.0d){
						GlobalMessages.addErrorMessage(model,"Do not get the delivery cost information，please contact with I4U.");
						invalid = true;
						return invalid;
					}
				} catch (AcerchemOrderException e) {
					 GlobalMessages.addErrorMessage(model, e.getMessage());
					 
					 invalid = true;
					 return invalid;
				}
			}
			
			
		}

		if (cartData.getPickUpdate() == null)
		{
			GlobalMessages.addErrorMessage(model, "checkout.pickUpDate.notSelected");
			invalid = true;
			return invalid;
		}

		
		
		if ( placeOrderForm.getPaymentCode()== null ||placeOrderForm.getPaymentCode().length()==0)
		{
			GlobalMessages.addErrorMessage(model, "checkout.paymentMethod.notSelected");
			invalid = true;
			return invalid;
		} else {
				try {
					acerchemCheckoutFacade.setPaymentDetail( placeOrderForm.getPaymentCode());
				} catch (AcerchemOrderException e) {
					GlobalMessages.addErrorMessage(model, e.getMessage());
					
					invalid = true;
					return invalid;
				}
			}
			
		
		
		

		/*if (getCheckoutFlowFacade().hasNoPaymentInfo())
		{
			GlobalMessages.addErrorMessage(model, "checkout.paymentMethod.notSelected");
			invalid = true;
		}*/
	/*	else
		{
			// Only require the Security Code to be entered on the summary page if the SubscriptionPciOption is set to Default.
			if (CheckoutPciOptionEnum.DEFAULT.equals(getCheckoutFlowFacade().getSubscriptionPciOption())
					&& StringUtils.isBlank(securityCode))
			{
				GlobalMessages.addErrorMessage(model, "checkout.paymentMethod.noSecurityCode");
				invalid = true;
			}
		}
*/
	/*	if (!placeOrderForm.isTermsCheck())
		{
			GlobalMessages.addErrorMessage(model, "checkout.error.terms.not.accepted");
			invalid = true;
			return invalid;
		}*/
		//final CartData cartData = getCheckoutFacade().getCheckoutCart();

		//		if (!getCheckoutFacade().containsTaxValues())
		//		{
		//			LOGGER.error(String.format(
		//					"Cart %s does not have any tax values, which means the tax cacluation was not properly done, placement of order can't continue",
		//					cartData.getCode()));
		//			GlobalMessages.addErrorMessage(model, "checkout.error.tax.missing");
		//			invalid = true;
		//		}

		/*if (!cartData.isCalculated())
		{
			LOGGER.error(String.format("Cart %s has a calculated flag of FALSE, placement of order can't continue",
					cartData.getCode()));
			GlobalMessages.addErrorMessage(model, "checkout.error.cart.notcalculated");
			invalid = true;
		}*/

		return invalid;
	}

	@RequestMapping(value = "/back", method = RequestMethod.GET)
	@RequireHardLogIn
	@Override
	public String back(final RedirectAttributes redirectAttributes)
	{
		return getCheckoutStep().previousStep();
	}

	@RequestMapping(value = "/next", method = RequestMethod.GET)
	@RequireHardLogIn
	@Override
	public String next(final RedirectAttributes redirectAttributes)
	{
		return getCheckoutStep().nextStep();
	}

	protected CheckoutStep getCheckoutStep()
	{
		return getCheckoutStep(SUMMARY);
	}
	
	
	@RequestMapping(value = "/choose", method = RequestMethod.GET)
	@RequireHardLogIn
	public String doSelectPaymentMethod(@RequestParam("selectedPaymentMethodId") final String selectedPaymentMethodId,final Model model, final RedirectAttributes redirectModel) throws CMSItemNotFoundException, CommerceCartModificationException
	{
		if (StringUtils.isNotBlank(selectedPaymentMethodId))
		{
//			getCheckoutFacade().setPaymentDetails(selectedPaymentMethodId);
			try {
				acerchemCheckoutFacade.setPaymentDetail(selectedPaymentMethodId);
			} catch (AcerchemOrderException e) {
				GlobalMessages.addErrorMessage(model, e.getMessage());
			}
		}
		
		return enterStep(model, redirectModel);
	}
	
	
	protected List<? extends AddressData> 	getDeliveryAddresses(final AddressData selectedAddressData) // NOSONAR
	{
		List<AddressData> deliveryAddresses = null;
//		if (selectedAddressData != null)
//		{
			deliveryAddresses = (List<AddressData>) getCheckoutFacade().getSupportedDeliveryAddresses(true);

//			if (deliveryAddresses == null || deliveryAddresses.isEmpty())
//			{
//				deliveryAddresses = Collections.singletonList(selectedAddressData);
//			}
//			else if (!isAddressOnList(deliveryAddresses, selectedAddressData))
//			{
//				deliveryAddresses.add(selectedAddressData);
//			}
//		}

		return deliveryAddresses == null ? Collections.<AddressData> emptyList() : deliveryAddresses;
	}


}
