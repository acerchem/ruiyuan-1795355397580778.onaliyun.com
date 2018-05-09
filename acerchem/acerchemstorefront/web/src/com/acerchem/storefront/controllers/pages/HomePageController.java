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

import de.hybris.platform.acceleratorstorefrontcommons.controllers.pages.AbstractPageController;
import de.hybris.platform.cms2.exceptions.CMSItemNotFoundException;
import de.hybris.platform.cms2.model.pages.AbstractPageModel;
import de.hybris.platform.cms2.model.pages.ContentPageModel;
import de.hybris.platform.cms2.model.site.CMSSiteModel;
import de.hybris.platform.acceleratorstorefrontcommons.controllers.util.GlobalMessages;
import de.hybris.platform.acceleratorstorefrontcommons.forms.AddressForm;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.acerchem.storefront.controllers.ControllerConstants;
import com.acerchem.storefront.data.CustomRegisterForm;

/**
 * Controller for home page
 */
@Controller
@RequestMapping("/")
public class HomePageController extends AbstractPageController
{
	@RequestMapping(method = RequestMethod.GET)
	public String home(@RequestParam(value = "logout", defaultValue = "false") final boolean logout, final Model model,
			final RedirectAttributes redirectModel) throws CMSItemNotFoundException
	{
		if (logout)
		{
			GlobalMessages.addFlashMessage(redirectModel, GlobalMessages.INFO_MESSAGES_HOLDER,
					"account.confirmation.signout.title");
			return REDIRECT_PREFIX + ROOT;
		}

		storeCmsPageInModel(model, getContentPageForLabelOrId(null));
		setUpMetaDataForContentPage(model, getContentPageForLabelOrId(null));
		updatePageTitle(model, getContentPageForLabelOrId(null));

		return getViewForPage(model);
	}

	protected void updatePageTitle(final Model model, final AbstractPageModel cmsPage)
	{
		storeContentPageTitleInModel(model, getPageTitleResolver().resolveHomePageTitle(cmsPage.getTitle()));
	}
	
	@RequestMapping(value = "/faq", method = RequestMethod.GET)
	public String getFaqPage(final Model model) throws CMSItemNotFoundException
	{
		storeCmsPageInModel(model, getContentPageForLabelOrId(null));
		return "pages/documents/faq";
	}
	@RequestMapping(value = "/aboutUs", method = RequestMethod.GET)
	public String getAboutUsPage(final Model model) throws CMSItemNotFoundException
	{
		storeCmsPageInModel(model, getContentPageForLabelOrId(null));
		return "pages/documents/aboutUs";
	}
	@RequestMapping(value = "/contactUs", method = RequestMethod.GET)
	public String getContactUsPage(final Model model) throws CMSItemNotFoundException
	{
		storeCmsPageInModel(model, getContentPageForLabelOrId(null));
		return "pages/documents/contactUs";
	}
	@RequestMapping(value = "/help", method = RequestMethod.GET)
	public String getHelpPage(final Model model) throws CMSItemNotFoundException
	{
		storeCmsPageInModel(model, getContentPageForLabelOrId(null));
		return "pages/documents/help";
	}
	@RequestMapping(value = "/privacyPolicy", method = RequestMethod.GET)
	public String getPrivacyPolicyPage(final Model model) throws CMSItemNotFoundException
	{
		storeCmsPageInModel(model, getContentPageForLabelOrId(null));
		return "pages/documents/privacyPolicy";
	}
	@RequestMapping(value = "/shippingOrReturnPolicy", method = RequestMethod.GET)
	public String getShippingOrReturnPolicyPage(final Model model) throws CMSItemNotFoundException
	{
		storeCmsPageInModel(model, getContentPageForLabelOrId(null));
		return "pages/documents/shippingOrReturnPolicy";
	}
	@RequestMapping(value = "/termsConditions", method = RequestMethod.GET)
	public String getTermsConditionsPage(final Model model) throws CMSItemNotFoundException
	{
		storeCmsPageInModel(model, getContentPageForLabelOrId(null));
		return "pages/documents/termsConditions";
	}
	
}
