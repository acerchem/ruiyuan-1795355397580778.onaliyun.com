package com.acerchem.storefront.controllers.pages;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.acerchem.core.model.AcerchemDocMessageModel;
import com.acerchem.core.service.AcerchemDocMessageService;

import de.hybris.platform.acceleratorstorefrontcommons.controllers.pages.AbstractPageController;
import de.hybris.platform.cms2.exceptions.CMSItemNotFoundException;
import de.hybris.platform.cms2.model.pages.AbstractPageModel;
import de.hybris.platform.servicelayer.search.FlexibleSearchService;


/**
 * Login Controller. Handles login and register for the account flow.
 */
@Controller
@RequestMapping(value = "/article")
public class ArticlePageController extends AbstractPageController
{
	@Resource
	private FlexibleSearchService flexibleSearchService;
	@Resource
	private AcerchemDocMessageService acerchemDocMessageService;
	
	@RequestMapping(method = RequestMethod.GET)
	public String doRegister(final Model model) throws CMSItemNotFoundException
	{
		
//		final String REF_QUERY_PRODUCTROW_START = "SELECT PK FROM {"+ArticleModel._TYPECODE+"} ";
//		final StringBuilder builder = new StringBuilder(REF_QUERY_PRODUCTROW_START);
//		final SearchResult<ArticleModel> articles = flexibleSearchService.search(builder.toString());
		
		final List<AcerchemDocMessageModel> list = acerchemDocMessageService.getDocMessageList();
		
		model.addAttribute("articles",list);
		
		storeCmsPageInModel(model, getCmsPage());
		return "pages/account/articleListsPage";
				
	}
	
	
	protected AbstractPageModel getCmsPage() throws CMSItemNotFoundException
	{
		return getContentPageForLabelOrId("login");
	}

	
}

