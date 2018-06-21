package com.acerchem.core.dao.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;

import com.acerchem.core.dao.AcerchemOrderDao;
import com.acerchem.core.enums.CustomerArea;

import de.hybris.platform.commercefacades.order.data.MonthlySalesAnalysis;
import de.hybris.platform.commercefacades.order.data.OrderDetailsReportData;
import de.hybris.platform.commercefacades.user.data.AddressData;
import de.hybris.platform.core.model.order.OrderEntryModel;
import de.hybris.platform.core.model.order.OrderModel;
import de.hybris.platform.core.model.user.AddressModel;
import de.hybris.platform.core.model.user.CustomerModel;
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
//		String SQL = "select {e.pk} from {OrderEntry as e" + " JOIN Order as o ON {e:order} = {o:pk}"
//				+ " JOIN Customer as u ON {u:pk} = {o:user}" + " JOIN Address as a ON {a:pk} = {o:deliveryAddress}"
//				+ " JOIN Country as c ON {c:pk} = {a:country}" + " JOIN Address as ua ON {ua:owner} = {u:pk}"
//				+ " JOIN Country as uc ON {uc:pk} = {ua:country}" + "} where {ua:contactAddress} = true ";
		String SQL = "select {e.pk},{ua.pk} from {OrderEntry as e" + " JOIN Order as o ON {e:order} = {o:pk}"
				+ " JOIN Customer as u ON {u:pk} = {o:user}" +  " JOIN Address as ua ON {ua:owner} = {u:pk}"
				+ " JOIN Country as uc ON {uc:pk} = {ua:country}" + "} where {ua:contactAddress} = true ";

		if (month != null && !month.equals("")) {
			SQL += " AND DATE_FORMAT({o:creationtime},'%Y%m') =?month ";
			params.put("month", month);
		}
		if (area != null && !area.equals("") && !area.equals("no")) {
			SQL += " AND {u:area} =?area ";
			params.put("area", CustomerArea.valueOf(area));
		}
		if (countryCode != null && !countryCode.equals("")&& !countryCode.equals("no")) {
			SQL += " AND {uc:isocode} =?isocode";
			//params.put("deliveryMode", deliveryModeService.getDeliveryModeForCode("DELIVERY_MENTION"));
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
		query.setResultClassList(Arrays.asList(OrderEntryModel.class,AddressModel.class));
		query.addQueryParameters(params);
		query.setNeedTotal(false);
		query.setCount(pageSize);
		query.setStart(pageSize * (pageNumber - 1));
		final SearchResult<List<Object>> result = flexibleSearchService.search(query);

		
		final List<OrderDetailsReportData> orderDetails = new ArrayList<OrderDetailsReportData>();
		//for (final OrderEntryModel od : result.getResult()) {
		for (final List<Object> columnValueForRow:result.getResult()){
			final OrderEntryModel od =(OrderEntryModel)columnValueForRow.get(0);
			final AddressModel addressModel = (AddressModel)columnValueForRow.get(1);
//			AddressModel addressModel = null;
			//if (od.getOrder().getDeliveryMode().getCode().equals("DELIVERY_MENTION")) {// 自提
//				for (final AddressModel address : od.getOrder().getUser().getAddresses()) {
//					if (address.getContactAddress()) {
//						addressModel = address;
//					}
//				}
//			} else {// 送货
//				addressModel = od.getOrder().getDeliveryAddress();
//			}

		
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
		if (area != null && !area.equals("")&& !area.equals("no")) {
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

		final String SQL = "select {pk},{area} from {Customer}";
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

}
