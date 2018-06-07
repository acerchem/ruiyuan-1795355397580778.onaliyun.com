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
	
	@ModelAttribute("months")
	public Collection<CountryData> getMonths()
	{
		List<CountryData> areaList=new ArrayList<>();
		
		CountryData January=new CountryData();
		January.setName("January");
		January.setIsocode("1");
		areaList.add(January);
		
		CountryData February=new CountryData();
		February.setName("February");
		February.setIsocode("2");
		areaList.add(February);
		
		CountryData March=new CountryData();
		March.setName("March");
		March.setIsocode("3");
		areaList.add(March);
		
		CountryData Aprll=new CountryData();
		Aprll.setName("Aprll");
		Aprll.setIsocode("4");
		areaList.add(Aprll);
		
		CountryData May=new CountryData();
		May.setName("May");
		May.setIsocode("5");
		areaList.add(May);
		
		CountryData June=new CountryData();
		June.setName("June");
		June.setIsocode("6");
		areaList.add(June);
		
		CountryData July=new CountryData();
		July.setName("July");
		July.setIsocode("7");
		areaList.add(July);
		
		CountryData August=new CountryData();
		August.setName("August");
		August.setIsocode("8");
		areaList.add(August);
		
		CountryData September=new CountryData();
		September.setName("September");
		September.setIsocode("9");
		areaList.add(September);
		
		CountryData October=new CountryData();
		October.setName("October");
		October.setIsocode("10");
		areaList.add(October);
		
		CountryData November=new CountryData();
		November.setName("November");
		November.setIsocode("11");
		areaList.add(November);
		
		CountryData December=new CountryData();
		December.setName("December");
		December.setIsocode("12");
		areaList.add(December);
	
		return areaList;
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
