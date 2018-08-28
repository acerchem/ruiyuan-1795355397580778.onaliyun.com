package com.acerchem.storefront.controllers.reports;

import java.util.Optional;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import de.hybris.platform.acceleratorstorefrontcommons.controllers.pages.AbstractPageController;
import de.hybris.platform.cms2.exceptions.CMSItemNotFoundException;
import de.hybris.platform.cms2.model.pages.AbstractPageModel;
import de.hybris.platform.core.model.user.UserModel;
import de.hybris.platform.servicelayer.user.UserService;

/**
 * Home of report
 * @author Jayson.wang
 * 2018年8月2日
 */
@Controller
@RequestMapping("/")
public class ReportHomePageController extends AbstractPageController{

	@Resource
	private UserService userService;
	
	@RequestMapping(value={"report","report/"},method = RequestMethod.GET)
	public String homeReport(final Model model) throws CMSItemNotFoundException{
		storeCmsPageInModel(model, getContentPageForLabelOrId(null));
		setUpMetaDataForContentPage(model, getContentPageForLabelOrId(null));
		updatePageTitle(model, getContentPageForLabelOrId(null));
		
		model.addAttribute("isDocMenu", isVisibleDocMenu());
		
		return REDIRECT_PREFIX + "/reports/message";
	}
	
	protected void updatePageTitle(final Model model, final AbstractPageModel cmsPage) {
		storeContentPageTitleInModel(model, getPageTitleResolver().resolveHomePageTitle(cmsPage.getTitle()));
	}
	
	private String isVisibleDocMenu(){
		 String s="not";
		final UserModel user = userService.getCurrentUser();
		if (CollectionUtils.isNotEmpty(user.getGroups())) {
			final Optional optional = user.getGroups().stream()
					.filter(group -> group.getUid().equals("docgroup")).findAny();
			if (optional.isPresent()) {
					s="yes";	
					System.out.println(s);
				}
		}
		return  s;
	}
}
