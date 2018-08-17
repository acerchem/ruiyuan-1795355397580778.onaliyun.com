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
import java.util.Locale;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.acerchem.core.dao.AcerchemOrderDao;
import com.acerchem.core.service.AcerChemProductService;
import com.acerchem.core.service.AcerChemVendorService;
import com.acerchem.core.service.AcerchemOrderAnalysisService;
import com.acerchem.core.util.CommonConvertTools;
import com.acerchem.facades.product.data.VendorData;
import com.acerchem.storefront.data.AcerchemCategoryBean;
import com.acerchem.storefront.data.AcerchemEmployeeSalesBean;
import com.acerchem.storefront.data.CustomerCreditAnalysisForReportBean;
import com.acerchem.storefront.data.CustomerSalesAnalysisForm;
import com.acerchem.storefront.data.EmployeeMonthlySalesBean;
import com.acerchem.storefront.data.MonthlySalesAnalysisForm;
import com.acerchem.storefront.data.ProductSalesForm;
import com.acerchem.storefront.data.SearchCriteriaFrom;
import com.acerchem.storefront.data.VendorAnalysisForm;
import com.acerchem.storefront.data.VendorInventoryForm;

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
import de.hybris.platform.core.model.user.CustomerModel;
import de.hybris.platform.core.model.user.UserModel;
import de.hybris.platform.ordersplitting.model.VendorModel;
import de.hybris.platform.servicelayer.model.ModelService;
import de.hybris.platform.servicelayer.user.UserService;
import reactor.util.CollectionUtils;

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

	@Resource
	private AcerChemVendorService acerChemVendorService;

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

	@ModelAttribute("vendors")
	public Collection<VendorData> getVendors() {
		final List<VendorData> vendors = new ArrayList<>();
		final List<VendorModel> list = acerChemVendorService.getAllVendors();
		if (list != null) {
			for (final VendorModel v : list) {
				final VendorData data = new VendorData();
				data.setCode(v.getCode());
				data.setName(v.getName());

				vendors.add(data);
			}

		}

		return vendors;
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
	public String showOrderDetailsPage(final Model model, final RedirectAttributes attr)
			throws CMSItemNotFoundException {
		if (isVendorAccount()) {
			attr.addFlashAttribute("myMessage", "订单明细表");
			return "redirect:/reports/message";
		}

		storeCmsPageInModel(model, getContentPageForLabelOrId("login"));
		// init
		final Date d = new Date();
		final SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
		final String curMonth = sdf.format(d);

		final List<OrderDetailsReportData> searchPageData = acerchemOrderDao.getOrderDetails(curMonth, null, null, null,
				null);
		final SearchCriteriaFrom searchCriteriaFrom = new SearchCriteriaFrom();
		searchCriteriaFrom.setMonth(curMonth);

		model.addAttribute("searchPageData", searchPageData);
		model.addAttribute("searchCriteriaFrom", searchCriteriaFrom);

		final int numberPagesShown = getSiteConfigService().getInt("pagination.number.results.count", 100);
		model.addAttribute("numberPagesShown", Integer.valueOf(numberPagesShown));
		model.addAttribute("isShowPageAllowed", false);
		return "pages/reports/orderDetails";
	}

	@RequestMapping(value = "/orderDetails", method = RequestMethod.POST)
	public String getOrderDetails(final SearchCriteriaFrom searchCriteriaFrom, final Model model,
			@RequestParam(value = "show", defaultValue = "Page") final ShowMode showMode)
			throws CMSItemNotFoundException {
		// if (searchCriteriaFrom.getPageNumber() == null ||
		// searchCriteriaFrom.getPageNumber() < 1) {
		searchCriteriaFrom.setPageNumber(1);
		// }

		String curMonth = searchCriteriaFrom.getMonth();
		if (StringUtils.isBlank(curMonth) || curMonth.length() != 7 || curMonth.indexOf("-") < 0) {

			final Date d = new Date();
			final SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
			curMonth = sdf.format(d);

		} else {
			curMonth = curMonth.replace("-", "");
		}

		final List<OrderDetailsReportData> searchPageData = acerchemOrderDao.getOrderDetails(curMonth,
				searchCriteriaFrom.getArea(), searchCriteriaFrom.getCountryCode(), searchCriteriaFrom.getUserName(),
				searchCriteriaFrom.getOrderCode());

		curMonth = curMonth.substring(0, 4) + "-" + curMonth.substring(4);

		final SearchCriteriaFrom newForm = new SearchCriteriaFrom();
		newForm.setArea(searchCriteriaFrom.getArea());
		newForm.setCountryCode(searchCriteriaFrom.getCountryCode());
		newForm.setMonth(curMonth);
		newForm.setOrderCode(searchCriteriaFrom.getOrderCode());
		newForm.setPageNumber(searchCriteriaFrom.getPageNumber());

		model.addAttribute("searchPageData", searchPageData);
		model.addAttribute("searchCriteriaFrom", newForm);

		final int numberPagesShown = getSiteConfigService().getInt("pagination.number.results.count", 100);
		model.addAttribute("numberPagesShown", Integer.valueOf(numberPagesShown));
		model.addAttribute("isShowPageAllowed", false);

		return "pages/reports/orderDetails";
	}

	@RequestMapping(value = "/monthlySalesAnalysis", method = RequestMethod.GET)
	public String showMonthlySalesAnalysisPage(final Model model, final RedirectAttributes attr)
			throws CMSItemNotFoundException {
		if (isVendorAccount()) {
			attr.addFlashAttribute("myMessage", "区域月度销售分析");
			return "redirect:/reports/message";
		}

		storeCmsPageInModel(model, getContentPageForLabelOrId("login"));
		// init
		final Calendar calendar = Calendar.getInstance();
		final String year = String.valueOf(calendar.get(Calendar.YEAR));

		final MonthlySalesAnalysisForm monthlySalesAnalysisForm = new MonthlySalesAnalysisForm();
		monthlySalesAnalysisForm.setYear(year);

		final List<MonthlySalesAnalysis> list = acerchemOrderAnalysisService.getMonthlySalesAnalysis(year, null);

		model.addAttribute("monthlySalesAnalysisForm", monthlySalesAnalysisForm);
		model.addAttribute("salesList", list);
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
		model.addAttribute("monthlySalesAnalysisForm", monthlySalesAnalysisForm);
		return "pages/reports/monthlySalesAnalysis";
	}

	@RequestMapping(value = "/downloadMonthlySalesAnalysis", method = RequestMethod.POST)
	public String downloadMonthlySalesAnalysisPage(final HttpServletResponse response) throws CMSItemNotFoundException {

		// final HSSFWorkbook wkb = new HSSFWorkbook();
		// final HSSFSheet sheet=wkb.createSheet("salesAnalysis");
		return "pages/reports/monthlySalesAnalysis";
	}

	@RequestMapping(value = "/employeeSalesAnalysis", method = RequestMethod.GET)
	public String showEmployeeSalesAnalysisPage(final Model model, final RedirectAttributes attr)
			throws CMSItemNotFoundException {
		if (isVendorAccount()) {
			attr.addFlashAttribute("myMessage", "各业务员完成情况");
			return "redirect:/reports/message";
		}
		storeCmsPageInModel(model, getContentPageForLabelOrId("login"));
		// init
		final String year = String.valueOf(Calendar.getInstance().get(Calendar.YEAR));

		final List<SalesByEmployeeReportData> listPrincipal = acerchemOrderAnalysisService.getEmployeeSales(year);
		final List<EmployeeMonthlySalesBean> list = getCrossTabOfEmployeeSalesAnalysis(listPrincipal);

		model.addAttribute("curYear", year);
		model.addAttribute("salesList", list);
		return "pages/reports/employeeSalesAnalysis";
	}

	@RequestMapping(value = "/employeeSalesAnalysis", method = RequestMethod.POST)
	public String getEmployeeSalesAnalysisPage(final Model model, @RequestParam("year") final String year) {

		final List<SalesByEmployeeReportData> listPrincipal = acerchemOrderAnalysisService.getEmployeeSales(year);
		final List<EmployeeMonthlySalesBean> list = getCrossTabOfEmployeeSalesAnalysis(listPrincipal);
		// list = getEmployeeSalesAnalysisSum(list);
		model.addAttribute("salesList", list);
		model.addAttribute("curYear", year);
		return "pages/reports/employeeSalesAnalysis";
	}

	private List<EmployeeMonthlySalesBean> getCrossTabOfEmployeeSalesAnalysis(
			final List<SalesByEmployeeReportData> list) {
		final List<EmployeeMonthlySalesBean> crossReport = new ArrayList<EmployeeMonthlySalesBean>();

		if (list.size() > 0) {
			final Map<String, List<SalesByEmployeeReportData>> mapList = new HashMap<String, List<SalesByEmployeeReportData>>();
			// 按照employee分组
			for (final SalesByEmployeeReportData data : list) {
				List<SalesByEmployeeReportData> tempList = mapList.get(data.getEmployee());
				if (CollectionUtils.isEmpty(tempList)) {
					tempList = new ArrayList<SalesByEmployeeReportData>();
					tempList.add(data);
					mapList.put(data.getEmployee(), tempList);
				} else {
					tempList.add(data);
				}
			}

			// 遍历map,add to crossReport
			Double janTotal = Double.valueOf(0);
			Double febTotal = Double.valueOf(0);
			Double marTotal = Double.valueOf(0);
			Double aprTotal = Double.valueOf(0);
			Double mayTotal = Double.valueOf(0);
			Double junTotal = Double.valueOf(0);
			Double julTotal = Double.valueOf(0);
			Double augTotal = Double.valueOf(0);
			Double sepTotal = Double.valueOf(0);
			Double octTotal = Double.valueOf(0);
			Double novTotal = Double.valueOf(0);
			Double decTotal = Double.valueOf(0);

			Double gTotal = Double.valueOf(0);
			for (final String employee : mapList.keySet()) {
				final EmployeeMonthlySalesBean bean = new EmployeeMonthlySalesBean();
				bean.setEmpName(employee);
				final List<SalesByEmployeeReportData> tmp = mapList.get(employee);
				Double total = Double.valueOf(0);

				for (final SalesByEmployeeReportData data : tmp) {
					if (data.getMonth().substring(4).equals("01")) {
						bean.setJanAmount(data.getAmount());
						janTotal = CommonConvertTools.addDouble(janTotal, data.getAmount());

					} else if (data.getMonth().substring(4).equals("02")) {
						bean.setFebAmount(data.getAmount());
						febTotal = CommonConvertTools.addDouble(febTotal, data.getAmount());
					} else if (data.getMonth().substring(4).equals("03")) {
						bean.setMarAmount(data.getAmount());
						marTotal = CommonConvertTools.addDouble(marTotal, data.getAmount());
					} else if (data.getMonth().substring(4).equals("04")) {
						bean.setAprAmount(data.getAmount());
						aprTotal = CommonConvertTools.addDouble(aprTotal, data.getAmount());
					} else if (data.getMonth().substring(4).equals("05")) {
						bean.setMayAmount(data.getAmount());
						mayTotal = CommonConvertTools.addDouble(mayTotal, data.getAmount());
					} else if (data.getMonth().substring(4).equals("06")) {
						bean.setJunAmount(data.getAmount());
						junTotal = CommonConvertTools.addDouble(junTotal, data.getAmount());
					} else if (data.getMonth().substring(4).equals("07")) {
						bean.setJulAmount(data.getAmount());
						julTotal = CommonConvertTools.addDouble(julTotal, data.getAmount());
					} else if (data.getMonth().substring(4).equals("08")) {
						bean.setAugAmount(data.getAmount());
						augTotal = CommonConvertTools.addDouble(augTotal, data.getAmount());
					} else if (data.getMonth().substring(4).equals("09")) {
						bean.setSepAmount(data.getAmount());
						sepTotal = CommonConvertTools.addDouble(sepTotal, data.getAmount());
					} else if (data.getMonth().substring(4).equals("10")) {
						bean.setOctAmount(data.getAmount());
						octTotal = CommonConvertTools.addDouble(octTotal, data.getAmount());
					} else if (data.getMonth().substring(4).equals("11")) {
						bean.setNovAmount(data.getAmount());
						novTotal = CommonConvertTools.addDouble(novTotal, data.getAmount());
					} else if (data.getMonth().substring(4).equals("12")) {
						bean.setDecAmount(data.getAmount());
						decTotal = CommonConvertTools.addDouble(decTotal, data.getAmount());
					}
					total = CommonConvertTools.addDouble(total, data.getAmount());

				}

				gTotal = CommonConvertTools.addDouble(gTotal, total);
				bean.setSubTotal(total);
				crossReport.add(bean);
			}
			// gTotal
			final EmployeeMonthlySalesBean gBean = new EmployeeMonthlySalesBean();
			gBean.setEmpName("Total");
			gBean.setJanAmount(janTotal);
			gBean.setFebAmount(febTotal);
			gBean.setMarAmount(marTotal);
			gBean.setAprAmount(aprTotal);
			gBean.setMayAmount(mayTotal);
			gBean.setJunAmount(junTotal);
			gBean.setJulAmount(julTotal);
			gBean.setAugAmount(augTotal);
			gBean.setSepAmount(sepTotal);
			gBean.setOctAmount(octTotal);
			gBean.setNovAmount(novTotal);
			gBean.setDecAmount(decTotal);
			gBean.setSubTotal(gTotal);
			crossReport.add(gBean);

		}
		return crossReport;
	}

	private static Comparator<SalesByEmployeeReportData> ComparatorByEmpName = new Comparator<SalesByEmployeeReportData>() {

		@Override
		public int compare(final SalesByEmployeeReportData o1, final SalesByEmployeeReportData o2) {
			int n = o1.getEmployee().compareTo(o2.getEmployee());
			if (n == 0) {
				n = o1.getMonth().compareTo(o2.getMonth());
			}
			return n;
		}

	};

	private List<SalesByEmployeeReportData> getEmployeeSalesAnalysisSum(final List<SalesByEmployeeReportData> list) {
		final List<SalesByEmployeeReportData> sumList = list;
		if (list.size() > 0) {
			Double total = Double.valueOf(0);
			for (final SalesByEmployeeReportData data : list) {
				total = CommonConvertTools.addDouble(total, data.getAmount());
			}
			// add total
			final SalesByEmployeeReportData totalData = new SalesByEmployeeReportData();
			totalData.setMonth("Total");
			totalData.setAmount(total);

			sumList.add(totalData);

		}
		return sumList;
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

	@RequestMapping(value = "/message", method = RequestMethod.GET)
	public String getMessagePage(final Model model, @ModelAttribute("myMessage") final String message) {

		// final String message = (String) model.asMap().get("reportMessage");

		model.addAttribute("reportMessage", "您没有权限查看" + message);
		return "pages/reports/reportMessage";
	}

	//////////// temporary start/////////////////////////////
	@RequestMapping(value = "/vendorInventory/temp", method = RequestMethod.GET)
	public String getVendorInventoryReportTemp(final Model model) {

		// init
		List<InventoryReportData> list = acerChemProductService.getInventory("");

		final VendorInventoryForm form = new VendorInventoryForm();
		final UserModel user = userService.getCurrentUser();
		if (user instanceof CustomerModel) {
			final CustomerModel customer = (CustomerModel) user;
			final VendorModel vendor = customer.getVendorAccount();
			if (vendor != null) {
				final String vendorcode = vendor.getCode();
				form.setVendor(vendorcode);
				form.setVendorFlag(true);
				form.setVendorName(vendor.getName());

				list = acerChemProductService.getInventory(form.getVendor());

			}
		}
		list = acerChemProductService.getInventory(list);
		model.addAttribute("list", list);
		model.addAttribute("vendorInventoryForm", form);

		return "pages/reports/vendorInventoryAnalysis";
	}

	@RequestMapping(value = "/vendorInventory/temp", method = RequestMethod.POST)
	public String showVendorInventoryReportTemp(final Model model, final VendorInventoryForm form) {

		// final String vendorCode =
		// StringUtils.defaultString(form.getVendor());

		// final UserModel employee =
		// acerChemVendorService.getEmployeeByVendorCode(vendorCode);
		// final String uid = employee==null?"":employee.getUid();

		List<InventoryReportData> list = acerChemProductService.getInventory(form.getVendor());
		list = acerChemProductService.getInventory(list);
		model.addAttribute("list", list);

		final VendorInventoryForm newform = new VendorInventoryForm();
		final UserModel user = userService.getCurrentUser();
		if (user instanceof CustomerModel) {
			final CustomerModel customer = (CustomerModel) user;
			final VendorModel vendor = customer.getVendorAccount();
			if (vendor != null) {
				final String vendorcode = vendor.getCode();
				newform.setVendor(vendorcode);
				newform.setVendorFlag(true);
				newform.setVendorName(vendor.getName());
			} else {
				newform.setVendor(form.getVendor());
			}
		}
		model.addAttribute("vendorInventoryForm", newform);
		return "pages/reports/vendorInventoryAnalysis";
	}

	@RequestMapping(value = "/vendorOrderProduct/temp", method = RequestMethod.GET)
	public String showVendorOrderProductTemp(final Model model) {

		final SimpleDateFormat sdf = new SimpleDateFormat("M/d/yyyy");
		final Calendar calendar = Calendar.getInstance();
		final Date end = calendar.getTime();
		calendar.set(Calendar.DATE, -7);
		final Date start = calendar.getTime();

		final VendorAnalysisForm form = new VendorAnalysisForm();

		form.setStartDate(sdf.format(start));

		form.setEndDate(sdf.format(end));

		// init
		List<OrderProductReportData> list = acerChemProductService.getOrderProductByVendor(null, start, end);
		final UserModel user = userService.getCurrentUser();
		if (user instanceof CustomerModel) {
			final CustomerModel customer = (CustomerModel) user;
			final VendorModel vendor = customer.getVendorAccount();
			if (vendor != null) {
				final String vendorcode = vendor.getCode();
				form.setVendor(vendorcode);
				form.setVendorFlag(true);
				form.setVendorName(vendor.getName());

				list = acerChemProductService.getOrderProductByVendor(vendorcode, start, end);
			}
		}

		model.addAttribute("list", list);
		model.addAttribute("vendorAnalysisForm", form);
		return "pages/reports/vendorOrderProduct";
	}

	@RequestMapping(value = "/vendorOrderProduct/temp", method = RequestMethod.POST)
	public String getVendorOrderProductTemp(final Model model, final VendorAnalysisForm form) throws ParseException {
		final String vendorCode = StringUtils.defaultString(form.getVendor());

		// final UserModel employee =
		// acerChemVendorService.getEmployeeByVendorCode(vendorCode);
		// final String uid = employee==null?"":employee.getUid();

		Date start = new Date();
		Date end = new Date();
		final SimpleDateFormat sdf = new SimpleDateFormat("M/d/yyyy");
		if (StringUtils.isNotBlank(form.getStartDate())) {
			start = sdf.parse(form.getStartDate());
		}
		if (StringUtils.isNotBlank(form.getEndDate())) {
			end = sdf.parse(form.getEndDate());
		}

		final List<OrderProductReportData> list = acerChemProductService.getOrderProductByVendor(vendorCode, start,
				end);
		model.addAttribute("list", list);
		// reset form
		VendorAnalysisForm newform = new VendorAnalysisForm();
		final UserModel user = userService.getCurrentUser();
		if (user instanceof CustomerModel) {
			final CustomerModel customer = (CustomerModel) user;
			final VendorModel vendor = customer.getVendorAccount();
			if (vendor != null) {
				final String vendorcode = vendor.getCode();
				newform.setVendor(vendorcode);
				newform.setVendorFlag(true);
				newform.setVendorName(vendor.getName());
				newform.setStartDate(sdf.format(start));
				newform.setEndDate(sdf.format(end));
			} else {
				newform = form;
			}
		}

		model.addAttribute("vendorAnalysisForm", newform);
		return "pages/reports/vendorOrderProduct";
	}

	//////////// temporary end/////////////////////////////

	@RequestMapping(value = "/productPriceAnalysis", method = RequestMethod.GET)
	public String showProductPriceAnalysist(final Model model, final RedirectAttributes attr)
			throws CMSItemNotFoundException {
		if (isVendorAccount()) {
			attr.addFlashAttribute("myMessage", "商品价格趋势分析");
			return "redirect:/reports/message";
		}
		storeCmsPageInModel(model, getContentPageForLabelOrId("login"));
		// model.addAttribute("list",list);

		final Date d = new Date();
		final SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
		final String month = sdf.format(d);

		// init

		final Calendar calendar = Calendar.getInstance(Locale.CHINA);
		calendar.setFirstDayOfWeek(Calendar.MONDAY);
		final int iyear = Integer.valueOf(month.substring(0, 4)).intValue();
		final int imonth = Integer.valueOf(month.substring(5)).intValue();
		calendar.set(iyear, imonth, 1);

		calendar.add(calendar.DATE, -1);
		final int maxWeek = calendar.get(Calendar.WEEK_OF_MONTH);
		final List<ProductPriceAnalysisData> list = acerChemProductService.getProductWithBaserealPrice(month);

		model.addAttribute("list", list);
		model.addAttribute("month", month);
		model.addAttribute("maxWeek", maxWeek);
		return "pages/reports/productPriceAnalysis";

	}

	@RequestMapping(value = "/productPriceAnalysis", method = RequestMethod.POST)
	public String getProductPriceAnalysist(final Model model, @RequestParam("month") final String month)
			throws CMSItemNotFoundException {

		String curMonth = month;
		if (StringUtils.isBlank(curMonth) || month.length() != 7 || month.indexOf("-") < 0) {

			final Date d = new Date();
			final SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
			curMonth = sdf.format(d);

		} else {
			curMonth = curMonth.replace("-", "");
		}

		final Calendar calendar = Calendar.getInstance(Locale.CHINA);
		calendar.setFirstDayOfWeek(Calendar.MONDAY);
		final int iyear = Integer.valueOf(curMonth.substring(0, 4)).intValue();
		final int imonth = Integer.valueOf(curMonth.substring(5)).intValue();
		calendar.set(iyear, imonth - 1, 1);
		calendar.add(Calendar.MONTH, 1);
		calendar.add(calendar.DATE, -1);
		final int maxWeek = calendar.get(Calendar.WEEK_OF_MONTH);

		final List<ProductPriceAnalysisData> list = acerChemProductService.getProductWithBaserealPrice(curMonth);

		curMonth = curMonth.substring(0, 4) + "-" + curMonth.substring(4);
		model.addAttribute("list", list);
		model.addAttribute("month", curMonth);
		model.addAttribute("maxWeek", maxWeek);
		return "pages/reports/productPriceAnalysis";

	}

	@RequestMapping(value = "/productSalesRecord", method = RequestMethod.GET)
	public String showProductSalesRecord(final Model model, final RedirectAttributes attr)
			throws CMSItemNotFoundException {
		if (isVendorAccount()) {
			attr.addFlashAttribute("myMessage", "商品销售记录");
			return "redirect:/reports/message";
		}

		storeCmsPageInModel(model, getContentPageForLabelOrId("login"));
		// model.addAttribute("list",list);

		// init
		final Date d = new Date();
		final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
		final String month = sdf.format(d);

		final List<ProductSalesRecordData> list = acerChemProductService.getProductSalesForReport(month, null, null,
				null);

		final ProductSalesForm productSalesForm = new ProductSalesForm();
		model.addAttribute("productSalesForm", productSalesForm);
		model.addAttribute("month", month);
		return "pages/reports/productSalesRecord";

	}

	@RequestMapping(value = "/productSalesRecord", method = RequestMethod.POST)
	public String getProductSalesRecord(final Model model, final ProductSalesForm productSalesForm)
			throws CMSItemNotFoundException {

		String curMonth = productSalesForm.getMonth();
		if (StringUtils.isBlank(curMonth) || curMonth.length() != 7 || curMonth.indexOf("-") < 0) {

			final Date d = new Date();
			final SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
			curMonth = sdf.format(d);

		} else {
			curMonth = curMonth.replace("-", "");
		}
		System.out.println(curMonth);
		final List<ProductSalesRecordData> list = acerChemProductService.getProductSalesForReport(curMonth,
				productSalesForm.getCategoryCode(), productSalesForm.getArea(), productSalesForm.getCountryCode());

		model.addAttribute("list", list);
		// 重置form
		final ProductSalesForm newForm = new ProductSalesForm();
		newForm.setArea(productSalesForm.getArea());
		newForm.setCategoryCode(productSalesForm.getCategoryCode());
		newForm.setCountryCode(productSalesForm.getCountryCode());
		curMonth = curMonth.substring(0, 4) + "-" + curMonth.substring(4);
		newForm.setMonth(curMonth);

		model.addAttribute("productSalesForm", newForm);
		model.addAttribute("month", curMonth);
		return "pages/reports/productSalesRecord";

	}

	@RequestMapping(value = "/customerSalesAnalysis", method = RequestMethod.GET)
	public String showCustomerSalesAnalysis(final Model model, final RedirectAttributes attr)
			throws CMSItemNotFoundException {
		if (isVendorAccount()) {
			attr.addFlashAttribute("myMessage", "用户购买情况分析");
			return "redirect:/reports/message";
		}
		storeCmsPageInModel(model, getContentPageForLabelOrId("login"));

		// init
		final List<CustomerSalesAnalysisData> list = acerchemOrderAnalysisService.getCustomerSalesAnalysis(null, null,
				0);
		final CustomerSalesAnalysisForm customerSalesAnalysisForm = new CustomerSalesAnalysisForm();
		model.addAttribute("customerSalesAnalysisForm", customerSalesAnalysisForm);
		model.addAttribute("list", list);
		return "pages/reports/customerSalesAnalysis";

	}

	@RequestMapping(value = "/customerSalesAnalysis", method = RequestMethod.POST)
	public String getCustomerSalesAnalysis(final Model model, final CustomerSalesAnalysisForm customerSalesAnalysisForm)
			throws CMSItemNotFoundException {

		final String area = customerSalesAnalysisForm.getArea() == null ? "" : customerSalesAnalysisForm.getArea();
		final String name = customerSalesAnalysisForm.getCustomerName() == null ? ""
				: customerSalesAnalysisForm.getCustomerName();
		final double amount = customerSalesAnalysisForm.getAmount() == null ? 0 : customerSalesAnalysisForm.getAmount();
		final List<CustomerSalesAnalysisData> list = acerchemOrderAnalysisService.getCustomerSalesAnalysis(area, name,
				amount);

		model.addAttribute("list", list);
		model.addAttribute("customerSalesAnalysisForm", customerSalesAnalysisForm);

		return "pages/reports/customerSalesAnalysis";

	}

	@RequestMapping(value = "/customerBillAnalysis", method = RequestMethod.GET)
	public String showCustomerBillAnalysis(final Model model, final RedirectAttributes attr)
			throws CMSItemNotFoundException {
		if (isVendorAccount()) {
			attr.addFlashAttribute("myMessage", "账龄分析报表");
			return "redirect:/reports/message";
		}
		storeCmsPageInModel(model, getContentPageForLabelOrId("login"));

		// init
		final SimpleDateFormat sdf = new SimpleDateFormat("M/d/yyyy");
		final Calendar calendar = Calendar.getInstance();

		Date end = calendar.getTime();
		calendar.add(Calendar.DATE, -7);
		final Date start = calendar.getTime();

		final String startDate = sdf.format(start);
		final String endDate = sdf.format(end);
		// init
		// 对账单最后选择日期+1，保证最后一天选到
		final Calendar cal = Calendar.getInstance();
		cal.setTime(end);
		cal.add(Calendar.DATE, 1);
		end = cal.getTime();
		List<CustomerBillAnalysisData> list = acerchemOrderAnalysisService.getCustomerBillAnalysis(start, end);
		final List<CustomerCreditAnalysisForReportBean> creditList = getCustomerCredit(list);

		list = getCustomerBillAnalysisSum(list);

		model.addAttribute("list", list);
		model.addAttribute("creditList", creditList);
		model.addAttribute("startDate", startDate);
		model.addAttribute("endDate", endDate);
		return "pages/reports/customerBillAnalysis";

	}

	@RequestMapping(value = "/customerBillAnalysis", method = RequestMethod.POST)
	public String getCustomerBillAnalysis(final Model model, @RequestParam("startDate") final String startDate,
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

		// 对账单最后选择日期+1，保证最后一天选到
		final Calendar cal = Calendar.getInstance();
		cal.setTime(end);
		cal.add(Calendar.DATE, 1);
		end = cal.getTime();
		List<CustomerBillAnalysisData> list = acerchemOrderAnalysisService.getCustomerBillAnalysis(start, end);

		final List<CustomerCreditAnalysisForReportBean> creditList = getCustomerCredit(list);

		list = getCustomerBillAnalysisSum(list);

		model.addAttribute("list", list);
		model.addAttribute("creditList", creditList);
		model.addAttribute("startDate", startDate);
		model.addAttribute("endDate", endDate);
		return "pages/reports/customerBillAnalysis";

	}

	private List<CustomerBillAnalysisData> getCustomerBillAnalysisSum(final List<CustomerBillAnalysisData> list) {

		if (list.size() > 0) {

			Double prePay = Double.valueOf(0);
			Double inPay = Double.valueOf(0);
			Double thirtyPayAmount = Double.valueOf(0);
			Double sixtyPayAmount = Double.valueOf(0);
			Double ninetyPayAmount = Double.valueOf(0);
			Double outerNinetyPayAmount = Double.valueOf(0);
			for (final CustomerBillAnalysisData data : list) {

				// 计算合计
				if (data.getPrePay() != null) {
					prePay = CommonConvertTools.addDouble(prePay, data.getPrePay());
				}
				if (data.getInPay() != null) {
					inPay = CommonConvertTools.addDouble(inPay, data.getInPay());
				}
				if (data.getThirtyPayAmount() != null) {
					thirtyPayAmount = CommonConvertTools.addDouble(thirtyPayAmount, data.getThirtyPayAmount());
				}
				if (data.getSixtyPayAmount() != null) {
					sixtyPayAmount = CommonConvertTools.addDouble(sixtyPayAmount, data.getSixtyPayAmount());
				}
				if (data.getNinetyPayAmount() != null) {
					ninetyPayAmount = CommonConvertTools.addDouble(ninetyPayAmount, data.getNinetyPayAmount());
				}
				if (data.getOuterNinetyPayAmount() != null) {
					outerNinetyPayAmount = CommonConvertTools.addDouble(outerNinetyPayAmount,
							data.getOuterNinetyPayAmount());

				}

			}

			// 增加合计
			final CustomerBillAnalysisData totalData = new CustomerBillAnalysisData();
			totalData.setOrderCode("Total");
			totalData.setPrePay(prePay);
			totalData.setInPay(inPay);
			totalData.setThirtyPayAmount(thirtyPayAmount);
			totalData.setSixtyPayAmount(sixtyPayAmount);
			totalData.setNinetyPayAmount(ninetyPayAmount);
			totalData.setOuterNinetyPayAmount(outerNinetyPayAmount);

			list.add(totalData);

		}

		return list;
	}

	public List<CustomerCreditAnalysisForReportBean> getCustomerCredit(final List<CustomerBillAnalysisData> list) {
		final List<CustomerCreditAnalysisForReportBean> creditList = new ArrayList<CustomerCreditAnalysisForReportBean>();
		if (list.size() > 0) {
			for (final CustomerBillAnalysisData data : list) {

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
		return creditList;
	}

	private boolean isVendorAccount() {
		final UserModel user = userService.getCurrentUser();
		if (user instanceof CustomerModel) {
			final CustomerModel customer = (CustomerModel) user;
			final VendorModel vendor = customer.getVendorAccount();
			if (vendor != null) {
				return true;
			}
		}

		return false;
	}

}
