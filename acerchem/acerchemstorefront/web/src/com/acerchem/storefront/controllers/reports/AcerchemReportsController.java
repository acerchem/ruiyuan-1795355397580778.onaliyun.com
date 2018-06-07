package com.acerchem.storefront.controllers.reports;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import javax.annotation.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import com.acerchem.core.dao.AcerchemOrderDao;
import com.acerchem.storefront.data.SearchCriteriaFrom;
import de.hybris.platform.acceleratorstorefrontcommons.breadcrumb.ResourceBreadcrumbBuilder;
import de.hybris.platform.acceleratorstorefrontcommons.controllers.pages.AbstractSearchPageController;
import de.hybris.platform.cms2.exceptions.CMSItemNotFoundException;
import de.hybris.platform.commercefacades.order.CheckoutFacade;
import de.hybris.platform.commercefacades.order.data.OrderDetailsReportData;
import de.hybris.platform.commercefacades.user.data.CountryData;

@Controller
@RequestMapping("/reports")
public class AcerchemReportsController extends AbstractSearchPageController{//AbstractPageController
 
	
	@Resource
	private AcerchemOrderDao acerchemOrderDao;
	
	@Resource(name = "acceleratorCheckoutFacade")
	private CheckoutFacade checkoutFacade;
	
	@ModelAttribute("countries")
	public Collection<CountryData> getCountries()
	{
		return checkoutFacade.getDeliveryCountries();
	}
	
	@ModelAttribute("areas")
	public Collection<CountryData> getAreas()
	{
		Set<String> areas = acerchemOrderDao.getAllAreas();
		List<CountryData> areaList=new ArrayList<>();
		for(String aa:areas)
		{
			CountryData area=new CountryData();
			area.setName(aa);
			area.setIsocode(aa);
			areaList.add(area);
		}
		return areaList;
	}

	@Resource(name = "accountBreadcrumbBuilder")
	private ResourceBreadcrumbBuilder accountBreadcrumbBuilder;
	
	@RequestMapping(value = "/orderDetails", method = RequestMethod.GET)
	public String showOrderDetailsPage(final Model model) throws CMSItemNotFoundException {
		storeCmsPageInModel(model, getContentPageForLabelOrId("login"));
		final SearchCriteriaFrom searchCriteriaFrom = new SearchCriteriaFrom();
		model.addAttribute("searchCriteriaFrom", searchCriteriaFrom);
		return "pages/reports/orderDetails";
	}
	
	@RequestMapping(value = "/orderDetails", method = RequestMethod.POST)
	public String getOrderDetails(final SearchCriteriaFrom searchCriteriaFrom,final Model model,@RequestParam(value = "show", defaultValue = "Page") final ShowMode showMode) throws CMSItemNotFoundException {
		if(searchCriteriaFrom.getPageNumber()==null||searchCriteriaFrom.getPageNumber()<1)
		{
			searchCriteriaFrom.setPageNumber(1);
		}
		List<OrderDetailsReportData> searchPageData = acerchemOrderDao.getOrderDetails(searchCriteriaFrom.getMonth(),
				searchCriteriaFrom.getArea(),searchCriteriaFrom.getCountryCode(),searchCriteriaFrom.getUserName(),searchCriteriaFrom.getOrderCode(),
				searchCriteriaFrom.getPageNumber());
		model.addAttribute("searchPageData", searchPageData);
		model.addAttribute("searchCriteriaFrom", searchCriteriaFrom);
		
		
		
		final int numberPagesShown = getSiteConfigService().getInt("pagination.number.results.count", 100);
		model.addAttribute("numberPagesShown", Integer.valueOf(numberPagesShown));
		model.addAttribute("isShowPageAllowed",false);
		
		return "pages/reports/orderDetails";
	}
	
	 
	
	
}
