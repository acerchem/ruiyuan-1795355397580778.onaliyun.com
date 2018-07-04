package com.acerchem.core.dao.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import com.acerchem.core.dao.AcerchemOrderDao;
import com.acerchem.core.enums.CustomerArea;
import com.acerchem.core.util.CommonConvertTools;

import de.hybris.platform.commercefacades.order.data.MonthlySalesAnalysis;
import de.hybris.platform.commercefacades.order.data.OrderDetailsReportData;
import de.hybris.platform.commercefacades.order.data.SalesByEmployeeReportData;
import de.hybris.platform.commercefacades.user.data.AddressData;
import de.hybris.platform.core.model.order.OrderEntryModel;
import de.hybris.platform.core.model.order.OrderModel;
import de.hybris.platform.core.model.user.AddressModel;
import de.hybris.platform.core.model.user.CustomerModel;
import de.hybris.platform.core.model.user.UserModel;
import de.hybris.platform.order.DeliveryModeService;
import de.hybris.platform.servicelayer.dto.converter.Converter;
import de.hybris.platform.servicelayer.search.FlexibleSearchQuery;
import de.hybris.platform.servicelayer.search.FlexibleSearchService;
import de.hybris.platform.servicelayer.search.SearchResult;

public class AcerchemOrderDaoImpl implements AcerchemOrderDao {

	@Resource
	private FlexibleSearchService flexibleSearchService;
	@Resource
	private Converter<AddressModel, AddressData> addressConverter;
	@Resource
	private DeliveryModeService deliveryModeService;

	@Override
	public List<OrderDetailsReportData> getOrderDetails(final String month, final String area, final String countryCode,
			final String userName, final String orderCode, final Integer pageNumber) {

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
		if (userName != null && !userName.equals("")) {
			SQL += " AND {u:name} like ?userName ";
			params.put("userName", "%" + userName + "%");
		}

		if (orderCode != null && !orderCode.equals("")) {
			SQL += " AND {o:code} like ?orderCode ";
			params.put("orderCode", "%" + orderCode + "%");
		}

		final StringBuilder builder = new StringBuilder(SQL);
		final FlexibleSearchQuery query = new FlexibleSearchQuery(builder.toString());
		query.setResultClassList(Arrays.asList(OrderEntryModel.class, AddressModel.class));
		query.addQueryParameters(params);
		query.setNeedTotal(false);
		query.setCount(pageSize);
		query.setStart(pageSize * (pageNumber - 1));
		final SearchResult<List<Object>> result = flexibleSearchService.search(query);

		final List<OrderDetailsReportData> orderDetails = new ArrayList<OrderDetailsReportData>();
		// for (final OrderEntryModel od : result.getResult()) {
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
			detail.setOrderAmount(od.getTotalPrice());
			detail.setUserUid(od.getOrder().getUser().getUid());
			detail.setDeliveryAddress(addressConverter.convert(addressModel));
			if (od.getOrder().getPlacedBy() != null) {
				detail.setSalesman(od.getOrder().getPlacedBy().getName());
			}
			if (od.getProduct().getAcerChemVendor() != null) {
				detail.setSupplier(od.getProduct().getAcerChemVendor().getName());
			}
			orderDetails.add(detail);
		}
		return orderDetails;

	}

	@SuppressWarnings("deprecation")
	@Override
	public List<MonthlySalesAnalysis> getMonthlySalesAnalysis(final String year, final String area) {

		final Map<String, Object> params = new HashMap<String, Object>();
		String SQL = "select {o.pk} from {Order as o JOIN Customer as u ON {u:pk} = {o:user}} where 1=1 ";

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
			Double Amount = Double.valueOf(0);
			if (oo.getDeliveryAddress() != null && oo.getDeliveryAddress().getCountry() != null
					&& oo.getDeliveryAddress().getCountry().getName() != null) {
				if (countryMap.get(oo.getDeliveryAddress().getCountry().getName()) != null) {
					Amount = countryMap.get(oo.getDeliveryAddress().getCountry().getName())
							.get(oo.getCreationtime().getMonth());
					MonthAmount = countryMap.get(oo.getDeliveryAddress().getCountry().getName());
				}

				if (oo.getCurrency().getIsocode().equals("USD")) {
					Amount += oo.getTotalPrice() / oo.getCurrency().getConversion();
				} else {
					Amount += oo.getTotalPrice();
				}
				MonthAmount.put(oo.getCreationtime().getMonth(), Amount);
				countryMap.put(oo.getDeliveryAddress().getCountry().getName(), MonthAmount);
			}
		}

		final List<MonthlySalesAnalysis> orderDetails = new ArrayList<MonthlySalesAnalysis>();
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
				orderDetails.add(detail);
			}
		}
		return orderDetails;
	}

	@Override
	public Set<String> getAllAreas() {

		//final String SQL = "select {pk},{area} from {Customer}";
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
	public List<SalesByEmployeeReportData> getEmployeeSales(final String year) {

//		final String paramSql = "";
//		final String SQL1 = "select sum({o.totalPrice}/{cur.conversion}) ,{o.user},DATE_FORMAT({o.creationtime},'%Y%m') "
//				+ " from {Order as o JOIN Customer as u ON {u.pk} = {o.user} "
//				+ " JOIN Currency as cur ON {o.currency} = {cur.pk}} " + " where {cur.isocode}='USD' " + paramSql
//				+ " group by {o.user},DATE_FORMAT({o.creationtime},'%Y%m')";
//
//		final String SQL2 = "select sum({o.totalPrice}) ,{o.user},DATE_FORMAT({o.creationtime},'%Y%m') "
//				+ " from {Order as o JOIN Customer as u ON {u.pk} = {o.user} "
//				+ " JOIN Currency as cur ON {o.currency} = {cur.pk}} " + " where {cur.isocode} !='USD' " + paramSql
//				+ " group by {o.user},DATE_FORMAT({o.creationtime},'%Y%m')";
		final Map<String, Object> params = new HashMap<String, Object>();
		//采用union处理
		final StringBuilder SQL = new StringBuilder();
		SQL.append("select q.a,q.b,q.c from (\n");
		SQL.append("{{\n");
		SQL.append(
				"select sum({o.totalPrice}/{cur.conversion}) as a,{o.user} as b,DATE_FORMAT({o.creationtime},'%Y%m') as c\n");
		SQL.append("from {Order as o JOIN Customer as u ON {u.pk} = {o.user}\n");
		SQL.append("JOIN Currency as cur ON {o.currency} = {cur.pk}\n");
		SQL.append("}\n");
		SQL.append("where {cur.isocode}='USD'\n");
		if (year != null && StringUtils.isNumeric(year) && Integer.valueOf(year) > 0) {
			SQL.append(" AND DATE_FORMAT({o.creationtime},'%Y') =?year\n");
			params.put("year", year);
		}
		SQL.append("group by {o.user},DATE_FORMAT({o.creationtime},'%Y%m')\n");
		SQL.append("}}\n");
		SQL.append("UNION\n");
		SQL.append("{{\n");
		SQL.append("select sum({o1.totalPrice}) as a,{o1.user} as b,DATE_FORMAT({o1.creationtime},'%Y%m') as c\n");
		SQL.append("from {Order as o1 JOIN Customer as u1 ON {u1.pk} = {o1.user}\n");
		SQL.append("JOIN Currency as cur1 ON {o1.currency} = {cur1.pk}\n");
		SQL.append("}\n");
		SQL.append("where {cur1.isocode}!='USD'\n");
		if (year != null && StringUtils.isNumeric(year) && Integer.valueOf(year) > 0) {
			SQL.append(" AND DATE_FORMAT({o1.creationtime},'%Y') =?year1\n");
			params.put("year1", year);
		}
		SQL.append("group by {o1.user},DATE_FORMAT({o1.creationtime},'%Y%m')\n");
		SQL.append("}}\n");
		SQL.append(")q\n");
		SQL.append("order by q.c");

		final FlexibleSearchQuery query = new FlexibleSearchQuery(SQL.toString());
		query.addQueryParameters(params);

		query.setResultClassList(Arrays.asList(Double.class, UserModel.class, String.class));
		final SearchResult<List<Object>> result = flexibleSearchService.search(query);
		final List<List<Object>> resultList = result.getResult();
//		final FlexibleSearchQuery query1 = new FlexibleSearchQuery(SQL1);
//		query1.addQueryParameters(params);
//
//		query1.setResultClassList(Arrays.asList(Double.class, UserModel.class, String.class));
//		final SearchResult<List<Object>> result1 = flexibleSearchService.search(query1);
//
//		final List<List<Object>> resultList1 = result1.getResult();
//
//		final FlexibleSearchQuery query2 = new FlexibleSearchQuery(SQL2);
//		query2.addQueryParameters(params);
//
//		query2.setResultClassList(Arrays.asList(Double.class, UserModel.class, String.class));
//		final SearchResult<List<Object>> result2 = flexibleSearchService.search(query2);
//
//		final List<List<Object>> resultList2 = result2.getResult();
//
		final List<SalesByEmployeeReportData> report = getReportList(resultList);

		return report;
	}
	
	//处理返回数据
	private List<SalesByEmployeeReportData> getReportList(final List<List<Object>> list){
		final List<SalesByEmployeeReportData> report = new ArrayList<SalesByEmployeeReportData>();
		if (CollectionUtils.isNotEmpty(list)) {
			for (final List<Object> item : list) {
				final SalesByEmployeeReportData dataItem = new SalesByEmployeeReportData();
				dataItem.setAmount((Double) item.get(0));
				dataItem.setEmployee(((UserModel) item.get(1)).getName());
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
}
