package com.acerchem.storefront.controllers.pages;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.acerchem.core.model.AcerchemDocMessageModel;
import com.acerchem.core.service.AcerchemDocMessageService;
import com.sun.research.ws.wadl.Request;

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
	public String doRegister(final Model model ,HttpServletRequest request) throws CMSItemNotFoundException
	{
		
//		final String REF_QUERY_PRODUCTROW_START = "SELECT PK FROM {"+ArticleModel._TYPECODE+"} ";
//		final StringBuilder builder = new StringBuilder(REF_QUERY_PRODUCTROW_START);
//		final SearchResult<ArticleModel> articles = flexibleSearchService.search(builder.toString()
		//这里获取搜索条件，好，那就直接获
		//好，直接获取
		//条件加上request,你没有工具类，我就直接获取了好，
		//你要是想把这种搜索用到多处，建议写一个封装，我就不用了，直接写了ke'yi
		String pString = request.getParameter("searching");
		model.addAttribute("searching",pString);
		final List<AcerchemDocMessageModel> list = acerchemDocMessageService.getDocMessageList(pString);
		
		model.addAttribute("articles",list);
		
		storeCmsPageInModel(model, getCmsPage());
		return "pages/account/articleListsPage";
				
	}
	
	
	
	protected AbstractPageModel getCmsPage() throws CMSItemNotFoundException
	{
		return getContentPageForLabelOrId("login");
	}

	
}

