package com.acerchem.storefront.controllers.reports;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
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
import com.acerchem.core.service.AcerChemProductService;
import com.acerchem.core.service.AcerchemOrderAnalysisService;
import com.acerchem.core.util.CommonConvertTools;
import com.acerchem.storefront.data.AcerchemCategoryBean;
import com.acerchem.storefront.data.AcerchemEmployeeSalesBean;
import com.acerchem.storefront.data.CustomerCreditAnalysisForReportBean;
import com.acerchem.storefront.data.CustomerSalesAnalysisForm;
import com.acerchem.storefront.data.MonthlySalesAnalysisForm;
import com.acerchem.storefront.data.ProductSalesForm;
import com.acerchem.storefront.data.SearchCriteriaFrom;

import de.hybris.platform.acceleratorstorefrontcommons.breadcrumb.ResourceBreadcrumbBuilder;
import de.hybris.platform.acceleratorstorefrontcommons.controllers.pages.AbstractSearchPageController;
import de.hybris.platform.catalog.CatalogVersionService;
import de.hybris.platform.catalog.model.CatalogVersionModel;
import de.hybris.platform.category.CategoryService;
import de.hybris.platform.category.model.CategoryModel;
import de.hybris.platform.cms2.exceptions.CMSItemNotFoundException;
import de.hybris.platform.commercefacades.customer.data.CustomerBillAnalysisData;
import de.hybris.platform.commercefacades.customer.data.CustomerSalesAnalysisData;
import de.hybris.platform.commercefacades.order.CheckoutFacade;
import de.hybris.platform.commercefacades.order.data.MonthlySalesAnalysis;
import de.hybris.platform.commercefacades.order.data.OrderDetailsReportData;
import de.hybris.platform.commercefacades.order.data.SalesByEmployeeReportData;
import de.hybris.platform.commercefacades.product.data.ProductPriceAnalysisData;
import de.hybris.platform.commercefacades.product.data.ProductSalesRecordData;
import de.hybris.platform.commercefacades.user.data.CountryData;
import de.hybris.platform.commercefacades.vendor.data.InventoryReportData;
import de.hybris.platform.commercefacades.vendor.data.OrderProductReportData;
import de.hybris.platform.commerceservices.category.CommerceCategoryService;
import de.hybris.platform.core.model.user.UserModel;
import de.hybris.platform.servicelayer.model.ModelService;
import de.hybris.platform.servicelayer.user.UserService;

@Controller
@RequestMapping("/reports")
public class AcerchemReportsController extends AbstractSearchPageController {// AbstractPageController

	@Resource
	private AcerchemOrderDao acerchemOrderDao;

	@Resource
	private AcerchemOrderAnalysisService acerchemOrderAnalysisService;

	@Resource(name = "acceleratorCheckoutFacade")
	private CheckoutFacade checkoutFacade;

	@Resource
	private UserService userService;
	@Resource
	private AcerChemProductService acerChemProductService;

	@Resource
	private ModelService modelService;
	@Resource
	private CategoryService categoryService;
	@Resource
	private CatalogVersionService catalogVersionService;

	@Resource
	private CommerceCategoryService commerceCategoryService;

	@ModelAttribute("countries")
	public Collection<CountryData> getCountries() {
		final List<CountryData> countries = new ArrayList<CountryData>();
		
		countries.addAll(checkoutFacade.getDeliveryCountries());

		return countries;
	}

	@ModelAttribute("areas")
	public Collection<CountryData> getAreas() {
		final Set<String> areas = acerchemOrderDao.getAllAreas();
		final List<CountryData> areaList = new ArrayList<CountryData>();
		
		for (final String aa : areas) {
			final CountryData area = new CountryData();
			area.setName(aa);
			area.setIsocode(aa);
			areaList.add(area);
		}

		return areaList;
	}

	@ModelAttribute("categories")
	public Collection<AcerchemCategoryBean> getCategories() {
		final String qualifier = "allSubcategories";

		final List<AcerchemCategoryBean> allCategories = new ArrayList<>();

		final List<CategoryModel> rootCategories = getRootCategories();

		if (rootCategories.size() > 0) {
			final List<CategoryModel> allOrginalCategories = new ArrayList<>();
			for (final CategoryModel root : rootCategories) {
				// CategoryModel cate =
				// commerceCategoryService.getCategoryForCode("1");

				final Collection<CategoryModel> subCategories = modelService.getAttributeValue(root, qualifier);

				allOrginalCategories.addAll(subCategories);
			}

			// remove repeat
			for (final CategoryModel c : allOrginalCategories) {
				final AcerchemCategoryBean bean = new AcerchemCategoryBean();
				bean.setCode(c.getCode());
				bean.setName(c.getName());
				allCategories.add(bean);
			}

			final Set<AcerchemCategoryBean> set = new HashSet<>();
			set.addAll(allCategories);
			allCategories.clear();
			allCategories.addAll(set);

			// sort
			allCategories.sort(cateComparator);
		}

		return allCategories;
	}

	private static final Comparator<AcerchemCategoryBean> cateComparator = new Comparator<AcerchemCategoryBean>() {

		@Override
		public int compare(final AcerchemCategoryBean c1, final AcerchemCategoryBean c2) {
			return c1.getCode().compareTo(c2.getCode());
		}
	};

	private List<CategoryModel> getRootCategories() {
		final Collection<CatalogVersionModel> sessionCatalogVersions = catalogVersionService
				.getSessionCatalogVersions();
		final List<CategoryModel> list = new ArrayList<>();
		for (final CatalogVersionModel cata : sessionCatalogVersions) {
			list.addAll(categoryService.getRootCategoriesForCatalogVersion(cata));
		}
		return list;
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

	@RequestMapping(value = "/employeeSalesAnalysis", method = RequestMethod.GET)
	public String showEmployeeSalesAnalysisPage(final Model model) throws CMSItemNotFoundException {
		storeCmsPageInModel(model, getContentPageForLabelOrId("login"));
		final String year = String.valueOf(Calendar.getInstance().get(Calendar.YEAR));
		model.addAttribute("curYear", year);
		// model.addAttribute("monthlySalesAnalysisForm",
		// monthlySalesAnalysisForm);
		return "pages/reports/employeeSalesAnalysis";
	}

	@RequestMapping(value = "/employeeSalesAnalysis", method = RequestMethod.POST)
	public String getEmployeeSalesAnalysisPage(final Model model, @RequestParam("year") final String year) {

		final List<SalesByEmployeeReportData> list = acerchemOrderAnalysisService.getEmployeeSales(year);

		// final List<AcerchemEmployeeSalesBean> crosstab =
		// getCrossTableFromEmployeeSales(list);
		model.addAttribute("salesList", list);

		return "pages/reports/employeeSalesAnalysis";
	}

	// private List<String,String> getK(){
	// List<String,String> l = new ArrayList<>();
	//
	// return l;
	// }

	private List<String> getColumnTab(final List<SalesByEmployeeReportData> list) {
		final List<String> tab = new ArrayList<>();
		if (list.size() > 0) {
			for (final SalesByEmployeeReportData dataItem : list) {
				tab.add(dataItem.getEmployee());
			}

			final HashSet<String> hs = new HashSet<String>(tab);
			tab.clear();
			tab.addAll(hs);
		}
		return tab;
	}

	private List<AcerchemEmployeeSalesBean> getCrossTableFromEmployeeSales(final List<SalesByEmployeeReportData> list) {
		final List<AcerchemEmployeeSalesBean> crossTable = new ArrayList<>();
		if (list.size() > 0) {
			// 先获取表头值和纵向列值
			final List<String> employees = new ArrayList<>();
			final List<String> months = new ArrayList<>();
			for (final SalesByEmployeeReportData dataItem : list) {
				employees.add(dataItem.getEmployee());
				months.add(dataItem.getMonth());
			}

			final HashSet<String> empHs = new HashSet<String>(employees);
			employees.clear();
			employees.addAll(empHs);

			final HashSet<String> monthHs = new HashSet<String>(months);
			months.clear();
			months.addAll(monthHs);

			// 数据处理
			// 首行
			final AcerchemEmployeeSalesBean row0 = new AcerchemEmployeeSalesBean();
			row0.setQueryMonth("");
			final Map<String, Double> map0 = new HashMap<>();
			for (final String s : employees) {
				map0.put(s, Double.valueOf(0));
			}
			row0.setEmployeeSales(map0);
			crossTable.add(row0);

			// 处理数据
			for (final String month : months) {
				final AcerchemEmployeeSalesBean row = new AcerchemEmployeeSalesBean();
				row.setQueryMonth(month);
				final Map<String, Double> map = new HashMap<>();
				// 列
				Double total = Double.valueOf(0);
				for (final SalesByEmployeeReportData data : list) {
					if (month.equals(data.getMonth())) {
						map.put(data.getEmployee(), data.getAmount());

						total = CommonConvertTools.addDouble(total, data.getAmount());
					}
				}
				// 增加 total
				map.put("total", total);
				row.setEmployeeSales(map);
				crossTable.add(row);
			}

		}

		return crossTable;
	}

	@RequestMapping(value = "/vendorInventory", method = RequestMethod.GET)
	public String showVendorInventoryReport(final Model model) {
		final UserModel employee = userService.getCurrentUser();
		if (employee == null || employee.getUid().equals("anonymous")) {
			return "redirect:/reports/noSignIn ";
			// }else{
			// System.out.println(employee.getUid());
		}
		final List<InventoryReportData> list = acerChemProductService.getInventoryProductByVendor(employee.getUid());

		model.addAttribute("list", list);

		return "pages/reports/vendorInventoryAnalysis";
	}

	@RequestMapping(value = "/vendorOrderProduct", method = RequestMethod.GET)
	public String showVendorOrderProduct(final Model model) throws CMSItemNotFoundException {
		storeCmsPageInModel(model, getContentPageForLabelOrId("login"));
		// model.addAttribute("list",list);

		return "pages/reports/vendorOrderProduct";
	}

	@RequestMapping(value = "/vendorOrderProduct", method = RequestMethod.POST)
	public String getVendorOrderProduct(final Model model, @RequestParam("startDate") final String startDate,
			@RequestParam("endDate") final String endDate) throws CMSItemNotFoundException, ParseException {
		final UserModel employee = userService.getCurrentUser();
		if (employee == null || employee.getUid().equals("anonymous")) {
			return "redirect:/reports/noSignIn ";
		}
		Date start = new Date();
		Date end = new Date();
		final SimpleDateFormat sdf = new SimpleDateFormat("M/d/yyyy");
		if (StringUtils.isNotBlank(startDate)) {
			start = sdf.parse(startDate);
		}
		if (StringUtils.isNotBlank(endDate)) {
			end = sdf.parse(endDate);
		}

		final List<OrderProductReportData> list = acerChemProductService.getOrderProductByVendor(employee.getUid(),
				start, end);
		model.addAttribute("list", list);
		return "pages/reports/vendorOrderProduct";
	}

	@RequestMapping(value = "/noSignIn", method = RequestMethod.GET)
	public String showSignInMessage(final Model model) {

		return "pages/reports/noSignIn";
	}

	@RequestMapping(value = "/productPriceAnalysis", method = RequestMethod.GET)
	public String showProductPriceAnalysist(final Model model) throws CMSItemNotFoundException {
		storeCmsPageInModel(model, getContentPageForLabelOrId("login"));
		// model.addAttribute("list",list);

		final Date d = new Date();
		final SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
		final String month = sdf.format(d);

		model.addAttribute("month", month);
		return "pages/reports/productPriceAnalysis";

	}

	@RequestMapping(value = "/productPriceAnalysis", method = RequestMethod.POST)
	public String getProductPriceAnalysist(final Model model, @RequestParam("month") final String month)
			throws CMSItemNotFoundException {

		String curMonth = month;
		if (StringUtils.isBlank(month)) {
			final Date d = new Date();
			final SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
			curMonth = sdf.format(d);

		}

		final Calendar calendar = Calendar.getInstance();
		final int iyear = Integer.valueOf(curMonth.substring(0, 4)).intValue();
		final int imonth = Integer.valueOf(curMonth.substring(5)).intValue();
		calendar.set(iyear, imonth-1,1);
		calendar.add(Calendar.MONTH, 1);
		calendar.add(calendar.DATE, -1);
		final int maxWeek = calendar.get(Calendar.WEEK_OF_MONTH);
		
		final List<ProductPriceAnalysisData> list = acerChemProductService.getProductWithBaserealPrice(curMonth);

		model.addAttribute("list", list);
		model.addAttribute("month", curMonth);
		model.addAttribute("maxWeek",maxWeek);
		return "pages/reports/productPriceAnalysis";

	}

	@RequestMapping(value = "/productSalesRecord", method = RequestMethod.GET)
	public String showProductSalesRecord(final Model model) throws CMSItemNotFoundException {
		storeCmsPageInModel(model, getContentPageForLabelOrId("login"));
		// model.addAttribute("list",list);

		final Date d = new Date();
		final SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
		final String month = sdf.format(d);

		final ProductSalesForm productSalesForm = new ProductSalesForm();
		model.addAttribute("productSalesForm", productSalesForm);
		model.addAttribute("month", month);
		return "pages/reports/productSalesRecord";

	}

	@RequestMapping(value = "/productSalesRecord", method = RequestMethod.POST)
	public String getProductSalesRecord(final Model model,final ProductSalesForm productSalesForm) throws CMSItemNotFoundException {
		
		
		final List<ProductSalesRecordData> list = acerChemProductService.getProductSalesForReport(productSalesForm.getMonth(),productSalesForm.getCategoryCode(), productSalesForm.getArea(), productSalesForm.getCountryCode());
		
		model.addAttribute("list", list);
		model.addAttribute("productSalesForm", productSalesForm);
		
		return "pages/reports/productSalesRecord";
		
		
	}

	@RequestMapping(value = "/customerSalesAnalysis", method = RequestMethod.GET)
	public String showCustomerSalesAnalysis(final Model model) throws CMSItemNotFoundException {
		storeCmsPageInModel(model, getContentPageForLabelOrId("login"));
		

		final CustomerSalesAnalysisForm customerSalesAnalysisForm = new CustomerSalesAnalysisForm();
		model.addAttribute("customerSalesAnalysisForm", customerSalesAnalysisForm);
		
		return "pages/reports/customerSalesAnalysis";

	}
	
	@RequestMapping(value = "/customerSalesAnalysis", method = RequestMethod.POST)
	public String getCustomerSalesAnalysis(final Model model ,final CustomerSalesAnalysisForm customerSalesAnalysisForm) throws CMSItemNotFoundException {
		
		final String area = customerSalesAnalysisForm.getArea()==null?"":customerSalesAnalysisForm.getArea();
		final String name = customerSalesAnalysisForm.getCustomerName()==null?"":customerSalesAnalysisForm.getCustomerName();
		final double amount = customerSalesAnalysisForm.getAmount()==null?0:customerSalesAnalysisForm.getAmount();
		final List<CustomerSalesAnalysisData> list = acerchemOrderAnalysisService.
				getCustomerSalesAnalysis(area, name, amount);
		

		model.addAttribute("list",list);
		model.addAttribute("customerSalesAnalysisForm", customerSalesAnalysisForm);
		
		return "pages/reports/customerSalesAnalysis";

	}
	
	@RequestMapping(value = "/customerBillAnalysis", method = RequestMethod.GET)
	public String showCustomerBillAnalysis(final Model model) throws CMSItemNotFoundException {
		storeCmsPageInModel(model, getContentPageForLabelOrId("login"));
		
		final SimpleDateFormat sdf = new SimpleDateFormat("M/d/yyyy");
		final Calendar calendar = Calendar.getInstance();
		
		final Date end = calendar.getTime();
		calendar.add(Calendar.DATE, -7);
		final Date start = calendar.getTime();
		
		final String startDate = sdf.format(start);
		final String endDate = sdf.format(end);
		model.addAttribute("startDate",startDate);
		model.addAttribute("endDate",endDate);
		return "pages/reports/customerBillAnalysis";

	}
	
	@RequestMapping(value = "/customerBillAnalysis", method = RequestMethod.POST)
	public String getCustomerBillAnalysis(final Model model,@RequestParam("startDate") final String startDate,
			@RequestParam("endDate") final String endDate) throws CMSItemNotFoundException, ParseException {
		
		Date start = new Date();
		Date end = new Date();
		final SimpleDateFormat sdf = new SimpleDateFormat("M/d/yyyy");
		if (StringUtils.isNotBlank(startDate)) {
			start = sdf.parse(startDate);
		}
		if (StringUtils.isNotBlank(endDate)) {
			end = sdf.parse(endDate);
		}
		
		final List<CustomerBillAnalysisData> list = acerchemOrderAnalysisService.getCustomerBillAnalysis(start, end);
		
		final List<CustomerCreditAnalysisForReportBean> creditList = new ArrayList<CustomerCreditAnalysisForReportBean>();
		
		if(list.size() > 0){
			for(final CustomerBillAnalysisData data : list){
				
				final CustomerCreditAnalysisForReportBean bean = new CustomerCreditAnalysisForReportBean();
				bean.setCustomerName(data.getCustomerName());
				bean.setLineOfCredit(data.getLineOfCredit());
				bean.setLineOfResedueCredit(data.getLineOfResedueCredit());
				bean.setLineOfUsedCredit(data.getLineOfUsedCredit());
				
				creditList.add(bean);
			}
			
			final Set<CustomerCreditAnalysisForReportBean> set = new HashSet<CustomerCreditAnalysisForReportBean>();
			set.addAll(creditList);
			
			creditList.clear();
			creditList.addAll(set);
			
		}
		
		model.addAttribute("list",list);
		model.addAttribute("creditList",creditList);
		model.addAttribute("startDate",startDate);
		model.addAttribute("endDate",endDate);
		return "pages/reports/customerBillAnalysis";

	}

}
