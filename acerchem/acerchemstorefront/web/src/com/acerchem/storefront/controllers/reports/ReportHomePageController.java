package com.acerchem.storefront.controllers.reports;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import de.hybris.platform.acceleratorstorefrontcommons.controllers.pages.AbstractPageController;
import de.hybris.platform.cms2.exceptions.CMSItemNotFoundException;
import de.hybris.platform.cms2.model.pages.AbstractPageModel;

/**
 * Home of report
 * @author Jayson.wang
 * 2018年8月2日
 */
@Controller
@RequestMapping("/")
public class ReportHomePageController extends AbstractPageController{

	@RequestMapping(value={"report","report/"},method = RequestMethod.GET)
	public String homeReport(final Model model) throws CMSItemNotFoundException{
		storeCmsPageInModel(model, getContentPageForLabelOrId(null));
		setUpMetaDataForContentPage(model, getContentPageForLabelOrId(null));
		updatePageTitle(model, getContentPageForLabelOrId(null));
		
		return REDIRECT_PREFIX + "/reports/productPriceAnalysis";
	}
	
	protected void updatePageTitle(final Model model, final AbstractPageModel cmsPage) {
		storeContentPageTitleInModel(model, getPageTitleResolver().resolveHomePageTitle(cmsPage.getTitle()));
	}
}
