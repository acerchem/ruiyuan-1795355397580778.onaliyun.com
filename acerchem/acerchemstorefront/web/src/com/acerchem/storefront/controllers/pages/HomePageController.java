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

import java.util.Optional;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import de.hybris.platform.acceleratorstorefrontcommons.controllers.pages.AbstractPageController;
import de.hybris.platform.acceleratorstorefrontcommons.controllers.util.GlobalMessages;
import de.hybris.platform.cms2.exceptions.CMSItemNotFoundException;
import de.hybris.platform.cms2.model.pages.AbstractPageModel;
import de.hybris.platform.core.model.user.UserModel;
import de.hybris.platform.servicelayer.user.UserService;

/**
 * Controller for home page
 */
@Controller
@RequestMapping("/")
public class HomePageController extends AbstractPageController {
	@Resource
	private UserService userService;

	@RequestMapping(method = RequestMethod.GET)
	public String home(@RequestParam(value = "logout", defaultValue = "false") final boolean logout, final Model model,
			final RedirectAttributes redirectModel) throws CMSItemNotFoundException {
		if (logout) {
			GlobalMessages.addFlashMessage(redirectModel, GlobalMessages.INFO_MESSAGES_HOLDER,
					"account.confirmation.signout.title");
			return REDIRECT_PREFIX + ROOT;
		}

		//control report user root login start
		final UserModel user = userService.getCurrentUser();

		if (CollectionUtils.isNotEmpty(user.getGroups())) {
			final Optional optional = user.getGroups().stream().filter(group -> group.getUid().equals("reportgroup"))
					.findAny();
			if (optional.isPresent()) {
				return REDIRECT_PREFIX + ROOT + "logout";
			}

		}

		//control report user root login end
		storeCmsPageInModel(model, getContentPageForLabelOrId(null));
		setUpMetaDataForContentPage(model, getContentPageForLabelOrId(null));
		updatePageTitle(model, getContentPageForLabelOrId(null));

		return getViewForPage(model);
	}

	protected void updatePageTitle(final Model model, final AbstractPageModel cmsPage) {
		storeContentPageTitleInModel(model, getPageTitleResolver().resolveHomePageTitle(cmsPage.getTitle()));
	}

	@RequestMapping(value = "/faq", method = RequestMethod.GET)
	public String getFaqPage(final Model model) throws CMSItemNotFoundException {
		storeCmsPageInModel(model, getContentPageForLabelOrId(null));
		return "pages/documents/faq";
	}

	@RequestMapping(value = "/aboutUs", method = RequestMethod.GET)
	public String getAboutUsPage(final Model model) throws CMSItemNotFoundException {
		storeCmsPageInModel(model, getContentPageForLabelOrId(null));
		return "pages/documents/aboutUs";
	}

	@RequestMapping(value = "/contactUs", method = RequestMethod.GET)
	public String getContactUsPage(final Model model) throws CMSItemNotFoundException {
		storeCmsPageInModel(model, getContentPageForLabelOrId(null));
		return "pages/documents/contactUs";
	}

	@RequestMapping(value = "/help", method = RequestMethod.GET)
	public String getHelpPage(final Model model) throws CMSItemNotFoundException {
		storeCmsPageInModel(model, getContentPageForLabelOrId(null));
		return "pages/documents/help";
	}

	@RequestMapping(value = "/privacyPolicy", method = RequestMethod.GET)
	public String getPrivacyPolicyPage(final Model model) throws CMSItemNotFoundException {
		storeCmsPageInModel(model, getContentPageForLabelOrId(null));
		return "pages/documents/privacyPolicy";
	}

	@RequestMapping(value = "/shippingOrReturnPolicy", method = RequestMethod.GET)
	public String getShippingOrReturnPolicyPage(final Model model) throws CMSItemNotFoundException {
		storeCmsPageInModel(model, getContentPageForLabelOrId(null));
		return "pages/documents/shippingOrReturnPolicy";
	}

	@RequestMapping(value = "/termsConditions", method = RequestMethod.GET)
	public String getTermsConditionsPage(final Model model) throws CMSItemNotFoundException {
		storeCmsPageInModel(model, getContentPageForLabelOrId(null));
		return "pages/documents/termsConditions";
	}

}
