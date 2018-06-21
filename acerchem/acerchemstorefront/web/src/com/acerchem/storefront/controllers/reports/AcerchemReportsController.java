package com.acerchem.storefront.controllers.reports;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.acerchem.core.dao.AcerchemOrderDao;
import com.acerchem.core.service.AcerchemOrderAnalysisService;
import com.acerchem.storefront.data.MonthlySalesAnalysisForm;
import com.acerchem.storefront.data.SearchCriteriaFrom;

import de.hybris.platform.acceleratorstorefrontcommons.breadcrumb.ResourceBreadcrumbBuilder;
import de.hybris.platform.acceleratorstorefrontcommons.controllers.pages.AbstractSearchPageController;
import de.hybris.platform.cms2.exceptions.CMSItemNotFoundException;
import de.hybris.platform.commercefacades.order.CheckoutFacade;
import de.hybris.platform.commercefacades.order.data.MonthlySalesAnalysis;
import de.hybris.platform.commercefacades.order.data.OrderDetailsReportData;
import de.hybris.platform.commercefacades.user.data.CountryData;

@Controller
@RequestMapping("/reports")
public class AcerchemReportsController extends AbstractSearchPageController {// AbstractPageController

	@Resource
	private AcerchemOrderDao acerchemOrderDao;

	@Resource
	private AcerchemOrderAnalysisService acerchemOrderAnalysisService;

	@Resource(name = "acceleratorCheckoutFacade")
	private CheckoutFacade checkoutFacade;

	@ModelAttribute("countries")
	public Collection<CountryData> getCountries() {
		final List<CountryData> countries = new ArrayList<CountryData>();
		final CountryData areaItem = new CountryData();
		areaItem.setName("no item");
		areaItem.setIsocode("no");

		countries.add(areaItem);
		countries.addAll(checkoutFacade.getDeliveryCountries());

		return countries;
	}

	@ModelAttribute("areas")
	public Collection<CountryData> getAreas() {
		final Set<String> areas = acerchemOrderDao.getAllAreas();
		final List<CountryData> areaList = new ArrayList<CountryData>();
		final CountryData areaItem = new CountryData();
		areaItem.setName("no item");
		areaItem.setIsocode("no");
		areaList.add(areaItem);
		for (final String aa : areas) {
			final CountryData area = new CountryData();
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
	public String getOrderDetails(final SearchCriteriaFrom searchCriteriaFrom, final Model model,
			@RequestParam(value = "show", defaultValue = "Page") final ShowMode showMode)
			throws CMSItemNotFoundException {
		if (searchCriteriaFrom.getPageNumber() == null || searchCriteriaFrom.getPageNumber() < 1) {
			searchCriteriaFrom.setPageNumber(1);
		}
		final List<OrderDetailsReportData> searchPageData = acerchemOrderDao.getOrderDetails(
				searchCriteriaFrom.getMonth(), searchCriteriaFrom.getArea(), searchCriteriaFrom.getCountryCode(),
				searchCriteriaFrom.getUserName(), searchCriteriaFrom.getOrderCode(),
				searchCriteriaFrom.getPageNumber());
		model.addAttribute("searchPageData", searchPageData);
		model.addAttribute("searchCriteriaFrom", searchCriteriaFrom);

		final int numberPagesShown = getSiteConfigService().getInt("pagination.number.results.count", 100);
		model.addAttribute("numberPagesShown", Integer.valueOf(numberPagesShown));
		model.addAttribute("isShowPageAllowed", false);

		return "pages/reports/orderDetails";
	}

	@RequestMapping(value = "/monthlySalesAnalysis", method = RequestMethod.GET)
	public String showMonthlySalesAnalysisPage(final Model model) throws CMSItemNotFoundException {
		storeCmsPageInModel(model, getContentPageForLabelOrId("login"));
		final MonthlySalesAnalysisForm monthlySalesAnalysisForm = new MonthlySalesAnalysisForm();
		model.addAttribute("monthlySalesAnalysisForm", monthlySalesAnalysisForm);
		return "pages/reports/monthlySalesAnalysis";
	}

	@RequestMapping(value = "/monthlySalesAnalysis", method = RequestMethod.POST)
	public String getMonthlySalesAnalysisPage(final MonthlySalesAnalysisForm monthlySalesAnalysisForm,
			final Model model) throws CMSItemNotFoundException {
		String year = "2018";
		if (StringUtils.isNotBlank(monthlySalesAnalysisForm.getYear())) {
			year = monthlySalesAnalysisForm.getYear();
		}
		final List<MonthlySalesAnalysis> list = acerchemOrderAnalysisService.getMonthlySalesAnalysis(year,
				monthlySalesAnalysisForm.getArea());

		model.addAttribute("salesList", list);

		return "pages/reports/monthlySalesAnalysis";
	}

	@RequestMapping(value = "/downloadMonthlySalesAnalysis", method = RequestMethod.POST)
	public String downloadMonthlySalesAnalysisPage(final HttpServletResponse response) throws CMSItemNotFoundException {

		// final HSSFWorkbook wkb = new HSSFWorkbook();
		// final HSSFSheet sheet=wkb.createSheet("salesAnalysis");
		return "pages/reports/monthlySalesAnalysis";
	}

}
