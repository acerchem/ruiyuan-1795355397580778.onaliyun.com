package com.acerchem.core.dao.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.acerchem.core.dao.AcerchemOrderDao;
import com.acerchem.core.enums.CustomerArea;
import com.acerchem.core.model.CustomerCreditAccountModel;
import com.acerchem.core.util.CommonConvertTools;

import de.hybris.platform.commercefacades.customer.data.CustomerBillAnalysisData;
import de.hybris.platform.commercefacades.customer.data.CustomerSalesAnalysisData;
import de.hybris.platform.commercefacades.order.data.MonthlySalesAnalysis;
import de.hybris.platform.commercefacades.order.data.OrderDetailsReportData;
import de.hybris.platform.commercefacades.order.data.SalesByEmployeeReportData;
import de.hybris.platform.commercefacades.user.data.AddressData;
import de.hybris.platform.core.enums.OrderStatus;
import de.hybris.platform.core.model.order.OrderEntryModel;
import de.hybris.platform.core.model.order.OrderModel;
import de.hybris.platform.core.model.user.AddressModel;
import de.hybris.platform.core.model.user.CustomerModel;
import de.hybris.platform.core.model.user.EmployeeModel;
import de.hybris.platform.core.model.user.UserModel;
import de.hybris.platform.order.DeliveryModeService;
import de.hybris.platform.servicelayer.dto.converter.Converter;
import de.hybris.platform.servicelayer.model.ModelService;
import de.hybris.platform.servicelayer.search.FlexibleSearchQuery;
import de.hybris.platform.servicelayer.search.FlexibleSearchService;
import de.hybris.platform.servicelayer.search.SearchResult;
import de.hybris.platform.storelocator.model.PointOfServiceModel;

public class AcerchemOrderDaoImpl implements AcerchemOrderDao {

	private static final Logger LOG = Logger.getLogger(AcerchemOrderDaoImpl.class);
	@Resource
	private FlexibleSearchService flexibleSearchService;
	@Resource
	private Converter<AddressModel, AddressData> addressConverter;
	@Resource
	private DeliveryModeService deliveryModeService;

	@Resource
	private ModelService modelService;

	@Override
	public List<OrderDetailsReportData> getOrderDetails(final String month, final String area, final String countryCode,
			final String customerCompanyName, final String orderCode) {

		final Integer pageSize = 100;

		final Map<String, Object> params = new HashMap<String, Object>();
		// String SQL = "select {e.pk} from {OrderEntry as e" + " JOIN Order as
		// o ON {e:order} = {o:pk}"
		// + " JOIN Customer as u ON {u:pk} = {o:user}" + " JOIN Address as a ON
		// {a:pk} = {o:deliveryAddress}"
		// + " JOIN Country as c ON {c:pk} = {a:country}" + " JOIN Address as ua
		// ON {ua:owner} = {u:pk}"
		// + " JOIN Country as uc ON {uc:pk} = {ua:country}" + "} where
		// {ua:contactAddress} = true ";
		String SQL = "select {e.pk},{ua.pk} from {OrderEntry as e" + " JOIN Order as o ON {e:order} = {o:pk}"
				+ " JOIN Customer as u ON {u:pk} = {o:user}" + " JOIN Address as ua ON {ua:owner} = {u:pk}"
				+ " JOIN Country as uc ON {uc:pk} = {ua:country}" + "} where {ua:contactAddress} = true ";
//		String SQL = "select {e.pk},{ua.pk} from {OrderEntry as e" + " JOIN Order as o ON {e:order} = {o:pk}"
//				+ " JOIN Customer as u ON {u:pk} = {o:user}" + " JOIN Address as ua ON {ua:owner} = {u:pk}"
//				+ " JOIN Country as uc ON {uc:pk} = {ua:country}" + "} where ";


		//增加订单状态不等于cancelled
		SQL += " and {o:status}<>?status";
//		SQL += " {o:status}<>?status";
		params.put("status", OrderStatus.valueOf("Cancelled"));
		if (month != null && !month.equals("")) {
			SQL += " AND DATE_FORMAT({o:creationtime},'%Y%m') =?month ";
			params.put("month", month);
		}
		if (area != null && !area.equals("") && !area.equals("no")) {
			SQL += " AND {u:area} =?area ";
			params.put("area", CustomerArea.valueOf(area));
		}
		if (countryCode != null && !countryCode.equals("") && !countryCode.equals("no")) {
			SQL += " AND {uc:isocode} =?isocode";
			// params.put("deliveryMode",
			// deliveryModeService.getDeliveryModeForCode("DELIVERY_MENTION"));
			params.put("isocode", countryCode);
		}
		//username filter-》company filter
		if (customerCompanyName != null && !customerCompanyName.equals("")) {
			SQL += " AND {u:companyName} like ?companyName ";
			params.put("companyName", "%" + customerCompanyName + "%");
		}

		if (orderCode != null && !orderCode.equals("")) {
			SQL += " AND {o:code} like ?orderCode ";
			params.put("orderCode", "%" + orderCode + "%");
		}

		SQL += " ORDER BY {o:code}";
		final StringBuilder builder = new StringBuilder(SQL);
		final FlexibleSearchQuery query = new FlexibleSearchQuery(builder.toString());
		query.setResultClassList(Arrays.asList(OrderEntryModel.class, AddressModel.class));
		query.addQueryParameters(params);
		//query.setNeedTotal(false);
		//query.setCount(pageSize);
		//query.setStart(pageSize * (pageNumber - 1));
		final SearchResult<List<Object>> result = flexibleSearchService.search(query);

		final List<OrderDetailsReportData> orderDetails = new ArrayList<OrderDetailsReportData>();
		// for (final OrderEntryModel od : result.getResult()) {
		LOG.info(">>>>>>>>>OrderDetailsReport originalCount="+result.getCount());
		for (final List<Object> columnValueForRow : result.getResult()) {
			final OrderEntryModel od = (OrderEntryModel) columnValueForRow.get(0);
			final AddressModel addressModel = (AddressModel) columnValueForRow.get(1);
			// AddressModel addressModel = null;
			// if
			// (od.getOrder().getDeliveryMode().getCode().equals("DELIVERY_MENTION"))
			// {// 自提
			// for (final AddressModel address :
			// od.getOrder().getUser().getAddresses()) {
			// if (address.getContactAddress()) {
			// addressModel = address;
			// }
			// }
			// } else {// 送货
			// addressModel = od.getOrder().getDeliveryAddress();
			// }

			final OrderDetailsReportData detail = new OrderDetailsReportData();
			detail.setCurrency(od.getOrder().getCurrency().getName());
			if (addressModel != null) {
				detail.setCountry(addressModel.getCountry().getName());
			}
			if (od.getOrder().getUser().getArea() != null) {
				detail.setArea(od.getOrder().getUser().getArea().toString());
			}
			detail.setOrderCode(od.getOrder().getCode());
			detail.setOrderTime(od.getOrder().getCreationtime());
			detail.setOrderFinishedTime(od.getOrder().getOrderFinishedDate());
			detail.setProductName(od.getProduct().getName());
			detail.setProductQuantity(od.getQuantity());
			detail.setOrderAmount(od.getTotalRealPrice());
			//modified userid to customer's companyname
			if(od.getOrder().getUser() != null){
				final CustomerModel customer = (CustomerModel)od.getOrder().getUser();
				if (StringUtils.isNotBlank(customer.getCompanyName())){
					detail.setUserUid(customer.getCompanyName());
				}

			}
			final String deliveryCode = od.getOrder().getDeliveryMode() == null ? ""
					: od.getOrder().getDeliveryMode().getCode();

			if (deliveryCode.equals("DELIVERY_GROSS")) {// 配送 DELIVERY_GROSS)
				if (od.getOrder().getDeliveryAddress() != null) {
					detail.setDeliveryAddress(addressConverter.convert(od.getOrder().getDeliveryAddress()));
				}

			} else {
				detail.setDeliveryAddress(addressConverter.convert(addressModel));
				// 增加仓库地址
				final PointOfServiceModel pos = od.getDeliveryPointOfService();
				if (pos != null) {
					final AddressModel posAddress = pos.getAddress();
					if (posAddress != null) {
						String _temp = "";
						if (StringUtils.isNotBlank(posAddress.getLine1())) {
							_temp += posAddress.getLine1() + ",";
						}
						if (StringUtils.isNotBlank(posAddress.getLine2())) {
							_temp += posAddress.getLine2() + ",";
						}
						if (StringUtils.isNotBlank(posAddress.getTown())) {
							_temp += posAddress.getTown() + ",";
						}
						if (posAddress.getRegion() != null) {
							if (StringUtils.isNotBlank(posAddress.getRegion().getName())) {
								_temp += posAddress.getRegion().getName() + ",";
							}
						}
						if (StringUtils.isNotBlank(posAddress.getPostalcode())) {
							_temp += posAddress.getPostalcode() + ",";
						}
						if (posAddress.getCountry() != null) {
							if (StringUtils.isNotBlank(posAddress.getCountry().getName())) {
								_temp += posAddress.getCountry().getName();
							}
						}

						final String warehouse = _temp;

						detail.setWarehouse(warehouse);

					}
				}

			}
			// 商家业务员
			// if (od.getOrder().getPlacedBy() != null) {
			// detail.setSalesman(od.getOrder().getPlacedBy().getName());
			// }
			final CustomerModel customer = (CustomerModel) od.getOrder().getUser();
			if (customer != null) {
				final EmployeeModel emp = customer.getEmployee();
				if (emp != null) {
					detail.setSalesman(emp.getName());
				}
			}
			if (od.getProduct().getAcerChemVendor() != null) {
				detail.setSupplier(od.getProduct().getAcerChemVendor().getName());
			}
			orderDetails.add(detail);
		}
		return orderDetails;

	}

	@Override
	public List<OrderDetailsReportData> getOrderDetails(final String month, final String area, final String countryCode,
														final String customerCompanyName, final String orderCode,
														final String vendorCode,final String productName,final String productCode,
														final String productCharacteristic,final String warehouseCode,
														final String employeeName,final String deliveryModeCode) {

		final Integer pageSize = 100;

		final Map<String, Object> params = new HashMap<String, Object>();
		String SQL = "select {e.pk},{ua.pk},{emp.pk} from {"
				+ " OrderEntry as e"
				+ " JOIN Order as o ON {e:order} = {o:pk}"
				+ " JOIN Customer as u ON {u:pk} = {o:user}"
				+ " JOIN Address as ua ON {ua:owner} = {u:pk}"
				+ " JOIN Country as uc ON {uc:pk} = {ua:country}"
				+ " JOIN Product as p ON {e:product} = {p:pk}"
                + " LEFT JOIN Employee as emp on {emp.pk} = {o:employeeNo} ";
				if(StringUtils.isNotBlank(vendorCode)) {
					SQL += " JOIN Vendor as v ON {v.pk} = {p.acerChemVendor}" ;
				}
				if(StringUtils.isNotBlank(deliveryModeCode)){
					SQL += " JOIN DeliveryMode as dm on {dm:pk} = {o:deliveryMode}";
				}
				SQL += "}";
		SQL += " where {ua:contactAddress} = true ";


		//增加订单状态不等于cancelled
		SQL += " and {o:status} = ?status";
//		SQL += " {o:status}<>?status";
//		params.put("status", OrderStatus.valueOf("Cancelled"));
		params.put("status", OrderStatus.valueOf("Completed"));
		if (month != null && !month.equals("")) {
			SQL += " AND DATE_FORMAT({o:creationtime},'%Y%m') =?month ";
			params.put("month", month);
		}
		if (area != null && !area.equals("") && !area.equals("no")) {
			SQL += " AND {u:area} =?area ";
			params.put("area", CustomerArea.valueOf(area));
		}
		if (countryCode != null && !countryCode.equals("") && !countryCode.equals("no")) {
			SQL += " AND {uc:isocode} =?isocode";
			// params.put("deliveryMode",
			// deliveryModeService.getDeliveryModeForCode("DELIVERY_MENTION"));
			params.put("isocode", countryCode);
		}
		//username filter-》company filter
		if (customerCompanyName != null && !customerCompanyName.equals("")) {
			SQL += " AND {u:companyName} like ?companyName ";
			params.put("companyName", "%" + customerCompanyName + "%");
		}

		if (orderCode != null && !orderCode.equals("")) {
			SQL += " AND {o:code} like ?orderCode ";
			params.put("orderCode", "%" + orderCode + "%");
		}

		if(StringUtils.isNotBlank(vendorCode)){
			SQL += " AND {v.code}=?code ";
			params.put("code", vendorCode);
		}

		if(StringUtils.isNotBlank(productName)){
			SQL += " AND {p.name} like ?productName";
			params.put("productName","%"+productName+"%");
		}

		if(StringUtils.isNotBlank(productCode)){
			SQL += " AND {p.code} like ?productCode";
			params.put("productCode","%"+productCode+"%");
		}

		if(StringUtils.isNotBlank(productCharacteristic)){
			SQL += " AND {p.characteristic} like ?productCharacteristic";
			params.put("productCharacteristic","%"+productCharacteristic+"%");
		}

		if(StringUtils.isNotBlank(warehouseCode)){
			SQL += " AND {p.code} in (?pCodeWH)";
			params.put("pCodeWH",getProductCodeByWarehouse(warehouseCode));
		}

		if(StringUtils.isNotBlank(employeeName)){
			SQL += " AND {emp.name} like ?employeeName";
			params.put("employeeName","%"+employeeName+"%");
		}

		if(StringUtils.isNotBlank(deliveryModeCode)){
			SQL += " AND {dm.code} = ?deliveryModeCode";
			params.put("deliveryModeCode",deliveryModeCode);
		}

		SQL += " ORDER BY {o:code} desc";
		final StringBuilder builder = new StringBuilder(SQL);
		final FlexibleSearchQuery query = new FlexibleSearchQuery(builder.toString());
		query.setResultClassList(Arrays.asList(OrderEntryModel.class, AddressModel.class,EmployeeModel.class));
		query.addQueryParameters(params);
		//query.setNeedTotal(false);
		//query.setCount(pageSize);
		//query.setStart(pageSize * (pageNumber - 1));
		final SearchResult<List<Object>> result = flexibleSearchService.search(query);

		final List<OrderDetailsReportData> orderDetails = new ArrayList<OrderDetailsReportData>();
		// for (final OrderEntryModel od : result.getResult()) {
		LOG.info(">>>>>>>>>OrderDetailsReport originalCount="+result.getCount());
		for (final List<Object> columnValueForRow : result.getResult()) {
			final OrderEntryModel od = (OrderEntryModel) columnValueForRow.get(0);
			final AddressModel addressModel = (AddressModel) columnValueForRow.get(1);
			final EmployeeModel employeeModel = (EmployeeModel) columnValueForRow.get(2);
			// AddressModel addressModel = null;
			// if
			// (od.getOrder().getDeliveryMode().getCode().equals("DELIVERY_MENTION"))
			// {// 自提
			// for (final AddressModel address :
			// od.getOrder().getUser().getAddresses()) {
			// if (address.getContactAddress()) {
			// addressModel = address;
			// }
			// }
			// } else {// 送货
			// addressModel = od.getOrder().getDeliveryAddress();
			// }

			final OrderDetailsReportData detail = new OrderDetailsReportData();
			detail.setCurrency(od.getOrder().getCurrency().getIsocode());
			if (addressModel != null) {
				detail.setCountry(addressModel.getCountry().getName());
			}
			if (od.getOrder().getUser().getArea() != null) {
				detail.setArea(od.getOrder().getUser().getArea().toString());
			}
			detail.setOrderCode(od.getOrder().getCode());
			detail.setOrderTime(od.getOrder().getCreationtime());
			detail.setOrderFinishedTime(od.getOrder().getOrderFinishedDate());
			detail.setProductName(od.getProduct().getName());
			detail.setProductQuantity(od.getQuantity());
			detail.setOrderAmount(od.getTotalRealPrice());
			//modified userid to customer's companyname
			if(od.getOrder().getUser() != null){
				final CustomerModel customer = (CustomerModel)od.getOrder().getUser();
				if (StringUtils.isNotBlank(customer.getCompanyName())){
					detail.setUserUid(customer.getCompanyName());
				}

			}
			final String deliveryCode = od.getOrder().getDeliveryMode() == null ? ""
					: od.getOrder().getDeliveryMode().getCode();

			if (deliveryCode.equals("DELIVERY_GROSS")) {// 配送 DELIVERY_GROSS)
				if (od.getOrder().getDeliveryAddress() != null) {
					detail.setDeliveryAddress(addressConverter.convert(od.getOrder().getDeliveryAddress()));
				}

			} else {
				detail.setDeliveryAddress(addressConverter.convert(addressModel));
				// 增加仓库地址
				final PointOfServiceModel pos = od.getDeliveryPointOfService();
				if (pos != null) {
					final AddressModel posAddress = pos.getAddress();
					if (posAddress != null) {
						String _temp = "";
						if (StringUtils.isNotBlank(posAddress.getLine1())) {
							_temp += posAddress.getLine1() + ",";
						}
						if (StringUtils.isNotBlank(posAddress.getLine2())) {
							_temp += posAddress.getLine2() + ",";
						}
						if (StringUtils.isNotBlank(posAddress.getTown())) {
							_temp += posAddress.getTown() + ",";
						}
						if (posAddress.getRegion() != null) {
							if (StringUtils.isNotBlank(posAddress.getRegion().getName())) {
								_temp += posAddress.getRegion().getName() + ",";
							}
						}
						if (StringUtils.isNotBlank(posAddress.getPostalcode())) {
							_temp += posAddress.getPostalcode() + ",";
						}
						if (posAddress.getCountry() != null) {
							if (StringUtils.isNotBlank(posAddress.getCountry().getName())) {
								_temp += posAddress.getCountry().getName();
							}
						}

						final String warehouse = _temp;

						detail.setWarehouse(warehouse);

					}
				}

			}
			// 商家业务员
			// if (od.getOrder().getPlacedBy() != null) {1
			// detail.setSalesman(od.getOrder().getPlacedBy().getName());
			// }
//			final CustomerModel customer = (CustomerModel) od.getOrder().getUser();
			if (employeeModel != null) {
//				final EmployeeModel emp = customer.getEmployee();
//				if (emp != null) {
					detail.setSalesman(employeeModel.getName());
//				}
			}
			if (od.getProduct().getAcerChemVendor() != null) {
				detail.setSupplier(od.getProduct().getAcerChemVendor().getName());
			}
			orderDetails.add(detail);
		}
		return orderDetails;

	}

	@Override
	public List<MonthlySalesAnalysis> getMonthlySalesAnalysis(final String year, final String area) {

		final Map<String, Object> params = new HashMap<String, Object>();
		String SQL = "select {o.pk} from {Order as o JOIN Customer as u ON {u:pk} = {o:user}} where 1=1 ";

		//增加订单状态不等于cancelled
//		SQL += " and {o:status}<>?status";
//		params.put("status", OrderStatus.valueOf("Cancelled"));
		SQL += " and {o:status} = ?status";
		params.put("status", OrderStatus.valueOf("Completed"));
		if (year != null && StringUtils.isNumeric(year) && Integer.valueOf(year) > 0) {
			SQL += " AND DATE_FORMAT({o:creationtime},'%Y') =?year ";
			params.put("year", year);
		}
		if (area != null && !area.equals("") && !area.equals("no")) {
			SQL += " AND {u:area} =?area ";
			params.put("area", CustomerArea.valueOf(area));
		}
		SQL += " Order by {u:area} ";

		final StringBuilder builder = new StringBuilder(SQL);
		final FlexibleSearchQuery query = new FlexibleSearchQuery(builder.toString());
		query.addQueryParameters(params);
		// query.setResultClassList(Arrays.asList(OrderModel.class,Integer.class));
		final SearchResult<OrderModel> result = flexibleSearchService.search(query);

		final Map<String, Map<Integer, Double>> countryMap = new HashMap<String, Map<Integer, Double>>();
		for (final OrderModel oo : result.getResult()) {
			Map<Integer, Double> MonthAmount = new HashMap<Integer, Double>();
			Double amount = Double.valueOf(0);
			final Calendar calendar = Calendar.getInstance();
			calendar.setTime(oo.getCreationtime());

			final UserModel u = oo.getUser();
			final List<AddressModel> addresses = u.getAddresses().stream().filter(x -> x.getContactAddress())
					.collect(Collectors.toList());
			if (addresses.size() > 0) {
				final AddressModel address = addresses.get(0);
				if (address.getCountry() != null) {
					final String countryName = address.getCountry().getName();

					if (countryMap.get(countryName) != null) {
						MonthAmount = countryMap.get(countryName);
						amount = MonthAmount.get(calendar.get(Calendar.MONTH) + 1);
						if (amount == null) {
							amount = Double.valueOf(0);
						}
					}
					// if (oo.getCurrency().getIsocode().equals("USD")) {
					// Amount += oo.getTotalPrice() /
					// oo.getCurrency().getConversion();
					// } else {
					// Amount += oo.getTotalPrice();
					// }
					final double price = new BigDecimal(oo.getTotalPrice()).divide(new BigDecimal(oo.getCurrency().getConversion()),2,BigDecimal.ROUND_HALF_UP).doubleValue();
					amount += price;
					MonthAmount.put(calendar.get(Calendar.MONTH) + 1, amount);
                    Double countryTotal = MonthAmount.get(20);
					if(countryTotal == null){
					    countryTotal = Double.valueOf(0);
                    }
                    countryTotal += price;
                    MonthAmount.put(20,countryTotal);
					countryMap.put(countryName, MonthAmount);
				}

			}
		}

		final List<MonthlySalesAnalysis> orderDetails = new ArrayList<MonthlySalesAnalysis>();
		final  MonthlySalesAnalysis total = new MonthlySalesAnalysis();
        final Map<Integer, Double> totalMonthMap = new HashMap<>();
        for (final String country : countryMap.keySet()) {
			if (countryMap.get(country) != null) {
				final Map<Integer, Double> MonthMap = countryMap.get(country);
				final MonthlySalesAnalysis detail = new MonthlySalesAnalysis();
				detail.setCountry(country);
				detail.setJanuaryAmount(MonthMap.get(1) != null ? MonthMap.get(1) : 0);
				detail.setFebruaryAmount(MonthMap.get(2) != null ? MonthMap.get(2) : 0);
				detail.setMarchAmount(MonthMap.get(3) != null ? MonthMap.get(3) : 0);
				detail.setAprllAmount(MonthMap.get(4) != null ? MonthMap.get(4) : 0);
				detail.setMayAmount(MonthMap.get(5) != null ? MonthMap.get(5) : 0);
				detail.setJuneAmount(MonthMap.get(6) != null ? MonthMap.get(6) : 0);
				detail.setJulyAmount(MonthMap.get(7) != null ? MonthMap.get(7) : 0);
				detail.setAugustAmount(MonthMap.get(8) != null ? MonthMap.get(8) : 0);
				detail.setSeptemberAmount(MonthMap.get(9) != null ? MonthMap.get(9) : 0);
				detail.setOctoberAmount(MonthMap.get(10) != null ? MonthMap.get(10) : 0);
				detail.setNovemberAmount(MonthMap.get(11) != null ? MonthMap.get(11) : 0);
				detail.setDecemberAmount(MonthMap.get(12) != null ? MonthMap.get(12) : 0);
				detail.setTotalAmount(MonthMap.get(20) != null ? MonthMap.get(20) : 0);
				orderDetails.add(detail);
				for(final int month : MonthMap.keySet()){
                    Double amount = totalMonthMap.get(month);
                    if(amount == null){
                        amount = Double.valueOf(0);
                    }
                    amount += MonthMap.get(month);
                    totalMonthMap.put(month,amount);
                }
			}
		}
        total.setCountry("Total");
        total.setJanuaryAmount(totalMonthMap.get(1) != null ? totalMonthMap.get(1) : 0);
        total.setFebruaryAmount(totalMonthMap.get(2) != null ? totalMonthMap.get(2) : 0);
        total.setMarchAmount(totalMonthMap.get(3) != null ? totalMonthMap.get(3) : 0);
        total.setAprllAmount(totalMonthMap.get(4) != null ? totalMonthMap.get(4) : 0);
        total.setMayAmount(totalMonthMap.get(5) != null ? totalMonthMap.get(5) : 0);
        total.setJuneAmount(totalMonthMap.get(6) != null ? totalMonthMap.get(6) : 0);
        total.setJulyAmount(totalMonthMap.get(7) != null ? totalMonthMap.get(7) : 0);
        total.setAugustAmount(totalMonthMap.get(8) != null ? totalMonthMap.get(8) : 0);
        total.setSeptemberAmount(totalMonthMap.get(9) != null ? totalMonthMap.get(9) : 0);
        total.setOctoberAmount(totalMonthMap.get(10) != null ? totalMonthMap.get(10) : 0);
        total.setNovemberAmount(totalMonthMap.get(11) != null ? totalMonthMap.get(11) : 0);
        total.setDecemberAmount(totalMonthMap.get(12) != null ? totalMonthMap.get(12) : 0);
        total.setTotalAmount(totalMonthMap.get(20) != null ? totalMonthMap.get(20) : 0);
        orderDetails.add(total);
		return orderDetails;
	}

	@Override
	public Set<String> getAllAreas() {

		// final String SQL = "select {pk},{area} from {Customer}";
		final String SQL = "select {pk} from {Customer}";
		final FlexibleSearchQuery query = new FlexibleSearchQuery(SQL);
		final SearchResult<CustomerModel> result = flexibleSearchService.search(query);

		final Set<String> areas = new TreeSet<String>();

		for (final CustomerModel customer : result.getResult()) {
			if (customer.getArea() != null) {
				areas.add(customer.getArea().toString());
			}
		}
		return areas;

	}

	@Override
	public List<SalesByEmployeeReportData> getEmployeeSales(final String year,final String employeeName) {

		// final String paramSql = "";
		// final String SQL1 = "select sum({o.totalPrice}/{cur.conversion})
		// ,{o.user},DATE_FORMAT({o.creationtime},'%Y%m') "
		// + " from {Order as o JOIN Customer as u ON {u.pk} = {o.user} "
		// + " JOIN Currency as cur ON {o.currency} = {cur.pk}} " + " where
		// {cur.isocode}='USD' " + paramSql
		// + " group by {o.user},DATE_FORMAT({o.creationtime},'%Y%m')";
		//
		// final String SQL2 = "select sum({o.totalPrice})
		// ,{o.user},DATE_FORMAT({o.creationtime},'%Y%m') "
		// + " from {Order as o JOIN Customer as u ON {u.pk} = {o.user} "
		// + " JOIN Currency as cur ON {o.currency} = {cur.pk}} " + " where
		// {cur.isocode} !='USD' " + paramSql
		// + " group by {o.user},DATE_FORMAT({o.creationtime},'%Y%m')";
		final Map<String, Object> params = new HashMap<String, Object>();
		// 采用union处理
		final StringBuilder SQL = new StringBuilder();
		SQL.append("select q.a,q.b,q.c from (\n");
		SQL.append("{{\n");
		SQL.append(
				"select sum({o.totalPrice}/{cur.conversion}) as a,{e.pk} as b,DATE_FORMAT({o.creationtime},'%Y%m') as c\n");
		SQL.append("from {Order as o JOIN Customer as u ON {u.pk} = {o.user}\n");
		SQL.append("JOIN Employee as e ON {e.pk}={o.employeeNo}\n");
		SQL.append("JOIN Currency as cur ON {o.currency} = {cur.pk}\n");
		SQL.append("}\n");
		SQL.append("where {cur.isocode}!='USD'\n");

		//增加订单状态不等于cancelled
		SQL.append(" and {o:status} = ?status\n");
//		params.put("status", OrderStatus.valueOf("Cancelled"));
		params.put("status", OrderStatus.valueOf("Completed"));
		if (year != null && StringUtils.isNumeric(year) && Integer.valueOf(year) > 0) {
			SQL.append(" AND DATE_FORMAT({o.creationtime},'%Y') =?year\n");
			params.put("year", year);
		}
        if(StringUtils.isNotBlank(employeeName)){
            SQL.append(" AND {e:name} like ?employeeName ");
            params.put("employeeName", "%" + employeeName + "%");
        }
		SQL.append("group by {e.pk},DATE_FORMAT({o.creationtime},'%Y%m')\n");
		SQL.append("}}\n");
		SQL.append("UNION\n");
		SQL.append("{{\n");
		SQL.append("select sum({o1.totalPrice}) as a,{e1.pk} as b,DATE_FORMAT({o1.creationtime},'%Y%m') as c\n");
		SQL.append("from {Order as o1 JOIN Customer as u1 ON {u1.pk} = {o1.user}\n");
		SQL.append("JOIN Employee as e1 ON {e1.pk}={o1.employeeNo}\n");
		SQL.append("JOIN Currency as cur1 ON {o1.currency} = {cur1.pk}\n");
		SQL.append("}\n");
		SQL.append("where {cur1.isocode}='USD'\n");
		//增加订单状态不等于cancelled
		SQL.append(" and {o1:status} = ?status\n");
//		params.put("status", OrderStatus.valueOf("Cancelled"));
		params.put("status", OrderStatus.valueOf("Completed"));
		if (year != null && StringUtils.isNumeric(year) && Integer.valueOf(year) > 0) {
			SQL.append(" AND DATE_FORMAT({o1.creationtime},'%Y') =?year1\n");
			params.put("year1", year);
		}
        if(StringUtils.isNotBlank(employeeName)){
            SQL.append(" AND {e1:name} like ?employeeName ");
            params.put("employeeName", "%" + employeeName + "%");
        }
		SQL.append("group by {e1.pk},DATE_FORMAT({o1.creationtime},'%Y%m')\n");
		SQL.append("}}\n");
		SQL.append(")q\n");
		SQL.append("order by q.c");

		final FlexibleSearchQuery query = new FlexibleSearchQuery(SQL.toString());
		query.addQueryParameters(params);

		query.setResultClassList(Arrays.asList(Double.class, EmployeeModel.class, String.class));
		final SearchResult<List<Object>> result = flexibleSearchService.search(query);
		final List<List<Object>> resultList = result.getResult();
		// final FlexibleSearchQuery query1 = new FlexibleSearchQuery(SQL1);
		// query1.addQueryParameters(params);
		//
		// query1.setResultClassList(Arrays.asList(Double.class,
		// UserModel.class, String.class));
		// final SearchResult<List<Object>> result1 =
		// flexibleSearchService.search(query1);
		//
		// final List<List<Object>> resultList1 = result1.getResult();
		//
		// final FlexibleSearchQuery query2 = new FlexibleSearchQuery(SQL2);
		// query2.addQueryParameters(params);
		//
		// query2.setResultClassList(Arrays.asList(Double.class,
		// UserModel.class, String.class));
		// final SearchResult<List<Object>> result2 =
		// flexibleSearchService.search(query2);
		//
		// final List<List<Object>> resultList2 = result2.getResult();
		//
		final List<SalesByEmployeeReportData> report = getReportList(resultList);

		return report;
	}

	// 处理返回数据
	private List<SalesByEmployeeReportData> getReportList(final List<List<Object>> list) {
		final List<SalesByEmployeeReportData> report = new ArrayList<SalesByEmployeeReportData>();
		if (CollectionUtils.isNotEmpty(list)) {
			for (final List<Object> item : list) {
				final SalesByEmployeeReportData dataItem = new SalesByEmployeeReportData();
				dataItem.setAmount((Double) item.get(0));
				if(item.get(1)!=null){
					dataItem.setEmployee(((EmployeeModel) item.get(1)).getName());
				}
				dataItem.setMonth((String) item.get(2));

				report.add(dataItem);
			}
		}

		return report;
	}

	// 合并
	private List<SalesByEmployeeReportData> unionList(final List<List<Object>> list1, final List<List<Object>> list2) {
		final List<SalesByEmployeeReportData> report = new ArrayList<SalesByEmployeeReportData>();
		if (CollectionUtils.isNotEmpty(list1)) {
			for (final List<Object> item1 : list1) {
				final Double amount1 = (Double) item1.get(0);
				final UserModel user1 = (UserModel) item1.get(1);
				final String yearMonth1 = (String) item1.get(2);

				if (CollectionUtils.isNotEmpty(list2)) {
					for (final List<Object> item2 : list2) {
						final SalesByEmployeeReportData dataItem2 = new SalesByEmployeeReportData();
						final Double amount2 = (Double) item2.get(0);
						final UserModel user2 = (UserModel) item2.get(1);
						final String yearMonth2 = (String) item2.get(2);
						if (user2.getUid().equals(user1.getUid()) && yearMonth1.equals(yearMonth2)) {
							final Double amount = CommonConvertTools.addDouble(amount1, amount2);
							dataItem2.setAmount(amount);

						} else {
							dataItem2.setAmount(amount2);
							// 不等时，把list1增加到报表
							final SalesByEmployeeReportData dataItem1 = new SalesByEmployeeReportData();
							dataItem1.setAmount(amount1);
							dataItem1.setEmployee(user1.getName());
							dataItem1.setMonth(yearMonth1);
							report.add(dataItem1);
						}
						dataItem2.setEmployee(user2.getName());
						dataItem2.setMonth(yearMonth2);
						// 增加list2各元素
						report.add(dataItem2);
					}
				} else {
					// 只有list1
					final SalesByEmployeeReportData dataItem01 = new SalesByEmployeeReportData();
					dataItem01.setAmount(amount1);
					dataItem01.setEmployee(user1.getName());
					dataItem01.setMonth(yearMonth1);
					report.add(dataItem01);
				}

			}
		} else {
			if (CollectionUtils.isNotEmpty(list2)) {
				// 只有list2
				for (final List<Object> item : list2) {
					final SalesByEmployeeReportData dataItem0 = new SalesByEmployeeReportData();
					dataItem0.setAmount((Double) item.get(0));
					dataItem0.setEmployee(((UserModel) item.get(1)).getName());
					dataItem0.setMonth((String) item.get(2));

					report.add(dataItem0);
				}
			}
		}

		return report;
	}

	@Override
	public List<CustomerSalesAnalysisData> getCustomerSalesAnalysis(final String area, final String customerName,
			final double amount,final Date startDate,final Date endDate) {
		final Map<String, Object> params = new HashMap<String, Object>();

		String SQL = "select sum({e.totalRealPrice}/{cur.conversion}),{u.pk},{ua.pk} " +
                "from {"
                + " OrderEntry as e"
				+ " JOIN Order as o ON {e:order} = {o:pk}"
                + " JOIN Customer as u ON {u:pk} = {o:user}"
				+ " JOIN Address as ua ON {ua:owner} = {u:pk}"
                + " JOIN Country as uc ON {uc:pk} = {ua:country}"
                + " JOIN Currency as cur ON {o.currency} = {cur.pk}"
				+ "} where {ua:contactAddress} = true ";

		//增加订单状态不等于cancelled
		SQL += " and {o:status}<>?status";
		params.put("status", OrderStatus.valueOf("Cancelled"));
		if (area != null && !area.equals("") && !area.equals("no")) {
			SQL += " AND {u:area} =?area ";
			params.put("area", CustomerArea.valueOf(area));
		}

		if (StringUtils.isNotBlank(customerName)) {
			SQL += " AND {u:name} like ?userName ";
			params.put("userName", "%" + customerName + "%");
		}

		if(startDate !=null){
			SQL += " AND {o:creationtime} >= ?startDate ";
			params.put("startDate", startDate);
		}

		if(endDate != null){
			SQL += " AND {o:creationtime} <= ?endDate ";
			params.put("endDate", endDate);
		}
		SQL += " group by {u.pk},{ua.pk}";

		if (amount > 0) {
			SQL += " having sum({e.totalRealPrice}) > ?amount";
			params.put("amount", amount);
		}

		final FlexibleSearchQuery query = new FlexibleSearchQuery(SQL);
		query.addQueryParameters(params);

		query.setResultClassList(Arrays.asList(Double.class, CustomerModel.class, AddressModel.class));
		final SearchResult<List<Object>> result = flexibleSearchService.search(query);
		final List<List<Object>> resultList = result.getResult();

		final List<CustomerSalesAnalysisData> list = new ArrayList<CustomerSalesAnalysisData>();

		if (resultList.size() > 0) {
			for (final List<Object> item : resultList) {
				final Double sum = (Double) item.get(0);
				if (item.get(1) != null) {
					final CustomerModel u = (CustomerModel) item.get(1);
					final AddressModel address = (AddressModel) item.get(2);
					String areaCode = "";
					if (u != null) {
						if (u.getArea() != null) {
							areaCode = u.getArea().getCode();
						}
					} else {
						LOG.debug(">>>>>>>>>>>>>>customerModel is null>>>>>>>>>>>>>>>>>>");
					}

					final CustomerSalesAnalysisData data = new CustomerSalesAnalysisData();
					data.setArea(areaCode);
					if (address != null) {
						final String country = address.getCountry() != null ? address.getCountry().getName() : "";
						data.setCountry(country);
					}
					if (u != null) {
						data.setCustomerName(u.getName());
					}
					data.setArea(areaCode);
					data.setSalesAmount(sum);

					list.add(data);
				}
			}
		}

		return list;
	}

	@Override
	public List<CustomerBillAnalysisData> getCustomerBillAnalysis(final Date startDate, final Date endDate,String customerName,String employeeName) {

		// date
		Date start = startDate;
		Date end = endDate;
		if (start == null) {
			start = Calendar.getInstance().getTime();
		}
		if (end == null) {
			end = Calendar.getInstance().getTime();
		}

		String SQL = "select {pk} from {" +
                "Order as o " +
                "JOIN Customer as u ON {u:pk} = {o:user} ";
                if(StringUtils.isNotBlank(employeeName)) {
                    SQL += "JOIN Employee as e on {e:pk} = {u:employee}";
                }
               SQL +=  "} " +
            "where {o:creationtime}> ?startDate and {o:creationtime} < ?endDate  and {o:status}<>?status";


        final Map<String, Object> params = new HashMap<String, Object>();
        params.put("startDate", start);
        params.put("endDate", end);
		if(StringUtils.isNotBlank(customerName)){
		    SQL += " AND {u:name} like ?customerName ";
            params.put("customerName", "%" + customerName + "%");
        }
        if(StringUtils.isNotBlank(employeeName)){
            SQL += " AND {e:name} like ?employeeName ";
            params.put("employeeName", "%" + employeeName + "%");
        }
		//增加订单状态不等于cancelled
        params.put("status", OrderStatus.valueOf("Cancelled"));

        final FlexibleSearchQuery query = new FlexibleSearchQuery(SQL);
        query.addQueryParameters(params);
		final SearchResult<OrderModel> result = flexibleSearchService.search(query);
		final List<OrderModel> list = result.getResult();




		final List<CustomerBillAnalysisData> report = new ArrayList<CustomerBillAnalysisData>();

		if (CollectionUtils.isNotEmpty(list)) {
			// 当前时间
			final Date currentTime = Calendar.getInstance().getTime();

			for (final OrderModel o : list) {
				final CustomerBillAnalysisData data = new CustomerBillAnalysisData();

				if (o.getUser() != null) {
					if (o.getUser() instanceof CustomerModel) {
						data.setOrderCode(o.getCode());
						data.setCustomerName(o.getUser().getName());

						final CustomerModel customer = (CustomerModel) o.getUser();
//						if (customer.getEmployee() != null) {
						if (StringUtils.isNotBlank(o.getEmployeeNo())){
							data.setEmployeeName(getEmployeeName(o.getEmployeeNo()));
						}
//						}
						data.setPlaceTime(o.getCreationtime());
						data.setFinishedTime(o.getOrderFinishedDate());

						// 计算账期
						int billDays = 0;
						final CustomerCreditAccountModel creditAccount = customer.getCreditAccount();
						if (creditAccount != null) {
							billDays = creditAccount.getBillingInterval();
							// 处理当下客户的信用额度
							data.setLineOfCredit(creditAccount.getCreditTotalAmount().doubleValue());
							data.setLineOfUsedCredit(
									CommonConvertTools.subDouble(creditAccount.getCreditTotalAmount().doubleValue(),
											creditAccount.getCreaditRemainedAmount().doubleValue()));
							data.setLineOfResedueCredit(creditAccount.getCreaditRemainedAmount().doubleValue());
						}

						// 发票时间
						final String deliveryCode = o.getDeliveryMode() == null ? "" : o.getDeliveryMode().getCode();
						Date invoiceDate = deliveryCode.equals("DELIVERY_GROSS") ? o.getWaitDeliveriedDate()
								: o.getPickUpDate();
						if (invoiceDate == null) {
							invoiceDate = currentTime;
						}

						// 计算金额
						String paymode = "";
						if (o.getPaymentMode() != null) {
							paymode = o.getPaymentMode().getCode(); // InvoicePayment--->prepay

						}
						final String orderStatus = o.getStatus() == null ? "" : o.getStatus().getCode();// UNPAIED
						if (paymode.equalsIgnoreCase("InvoicePayment") && orderStatus.equalsIgnoreCase("UNPAIED")) {

							double totalPrice = new BigDecimal(o.getTotalPrice()).divide(new BigDecimal(o.getCurrency().getConversion()),2,BigDecimal.ROUND_HALF_UP).doubleValue();
							data.setPrePay(totalPrice);
						} else {
							// 计算账期内外
							if (paymode.equalsIgnoreCase("CreditPayment")) {
								final int remainDays = (int) (currentTime.getTime() - invoiceDate.getTime())
										/ (1000 * 3600 * 24);
								final int flag = remainDays - billDays;
								double totalPrice = new BigDecimal(o.getTotalPrice()).divide(new BigDecimal(o.getCurrency().getConversion()),2,BigDecimal.ROUND_HALF_UP).doubleValue();
								if (flag < 0) {
									data.setInPay(o.getTotalPrice());
								} else if (flag >= 0 && flag < 30) {
									data.setThirtyPayAmount(totalPrice);
								} else if (flag >= 30 && flag < 60) {
									data.setSixtyPayAmount(totalPrice);
								} else if (flag >= 60 && flag <= 90) {
									data.setNinetyPayAmount(totalPrice);
								} else {
									data.setOuterNinetyPayAmount(totalPrice);
								}
							}

						}
						report.add(data);
					}
				}
			}

		}

		return report;
	}

	public List<String> getProductCodeByWarehouse(final String warehouseCode){
		final String SQL = "select {productCode} from {StockLevel as s JOIN Warehouse as w on {w:pk} = {s:warehouse}} where {w.code} = ?code";
		final FlexibleSearchQuery pQuery = new FlexibleSearchQuery(SQL);
		pQuery.addQueryParameter("code",warehouseCode);
		pQuery.setResultClassList(Arrays.asList(String.class));
		final SearchResult<String> pResult = flexibleSearchService.search(pQuery);
		return pResult.getResult();
	}

	private String getEmployeeName(String employeeNo){
		String userSql = "select {PK} from {User} where {PK} = ?employeeNo ";
		final FlexibleSearchQuery query = new FlexibleSearchQuery(userSql);
		final Map<String, Object> params = new HashMap<String, Object>();
		params.put("employeeNo", employeeNo);
		query.addQueryParameters(params);
		SearchResult<UserModel> search = flexibleSearchService.search(query);
		int size = search.getResult().size();
		String name = "";
		if (size>0){
			name = search.getResult().get(0).getName();
		}
		return name;
	}
}
