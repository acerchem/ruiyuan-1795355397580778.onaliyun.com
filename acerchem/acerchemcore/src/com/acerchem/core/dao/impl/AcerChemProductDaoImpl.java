package com.acerchem.core.dao.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import com.acerchem.core.dao.AcerChemProductDao;
import com.acerchem.core.enums.CustomerArea;
import com.acerchem.core.report.order.beans.AcerchemProductBuyerBean;
import com.acerchem.core.report.order.beans.AcerchemProductPriceBean;

import de.hybris.platform.core.model.order.OrderEntryModel;
import de.hybris.platform.core.model.product.ProductModel;
import de.hybris.platform.core.model.user.AddressModel;
import de.hybris.platform.ordersplitting.model.StockLevelModel;
import de.hybris.platform.ordersplitting.model.VendorModel;
import de.hybris.platform.servicelayer.search.FlexibleSearchQuery;
import de.hybris.platform.servicelayer.search.FlexibleSearchService;
import de.hybris.platform.servicelayer.search.SearchResult;

public class AcerChemProductDaoImpl implements AcerChemProductDao {

	@Resource
	private FlexibleSearchService flexibleSearchService;

	@Override
	public List<ProductModel> getProductByVendorName(final String vendorName) {
		final String SQL = "select {p.pk} from {" + ProductModel._TYPECODE + " as p JOIN AcerChemVendor2Product as p2v"
				+ " ON {p2v.target} = {p." + ProductModel.PK + "}" + " JOIN " + VendorModel._TYPECODE + " as v"
				+ " ON {p2v.source} = {v." + VendorModel.PK + "} }" + " where {v." + VendorModel.NAME
				+ "} like ?vendorName";

		final Map<String, Object> params = new HashMap<String, Object>();
		final StringBuilder builder = new StringBuilder(SQL);
		params.put("vendorName", "%" + vendorName + "%");

		final FlexibleSearchQuery query = new FlexibleSearchQuery(builder.toString());
		query.addQueryParameters(params);

		final SearchResult<ProductModel> result = flexibleSearchService.search(query);
		return result.getResult();
	}

	@Override
	public List<ProductModel> getProductByVendorCode(final String vendorCode) {
		final String SQL = "select {p.pk} from {" + ProductModel._TYPECODE + " as p JOIN AcerChemVendor2Product as p2v"
				+ " ON {p2v.target} = {p." + ProductModel.PK + "}" + " JOIN " + VendorModel._TYPECODE + " as v"
				+ " ON {p2v.source} = {v." + VendorModel.PK + "} }" + " where {v." + VendorModel.CODE
				+ "} = ?vendorCode";

		final Map<String, Object> params = new HashMap<String, Object>();
		final StringBuilder builder = new StringBuilder(SQL);
		params.put("vendorCode", vendorCode);

		final FlexibleSearchQuery query = new FlexibleSearchQuery(builder.toString());
		query.addQueryParameters(params);

		final SearchResult<ProductModel> result = flexibleSearchService.search(query);
		return result.getResult();
	}

	@Override
	public boolean isExistProductWithVendor(final String productCode, final String vendorCode) {
		final String SQL = "select {p.pk} from {" + ProductModel._TYPECODE + " as p JOIN AcerChemVendor2Product as p2v"
				+ " ON {p2v.target} = {p." + ProductModel.PK + "}" + " JOIN " + VendorModel._TYPECODE + " as v"
				+ " ON {p2v.source} = {v." + VendorModel.PK + "} }" + " where {v." + VendorModel.CODE
				+ "} = ?vendorCode" + " AND {p." + ProductModel.CODE + "} = ?prodCode";

		final Map<String, Object> params = new HashMap<String, Object>();
		final StringBuilder builder = new StringBuilder(SQL);
		params.put("vendorCode", vendorCode);
		params.put("prodCode", productCode);

		final FlexibleSearchQuery query = new FlexibleSearchQuery(builder.toString());
		query.addQueryParameters(params);

		final SearchResult<ProductModel> result = flexibleSearchService.search(query);
		if (result.getCount() > 0) {
			return true;
		}
		return false;
	}

	@Override
	public List<StockLevelModel> getInventoryProduct(final String uid) {
		if (StringUtils.isBlank(uid)) {
			return null;
		}
		final String SQL = "select {s.pk} from {StockLevel as s} where {s.productCode} IN ({{" +
				"select {p.code} from {Product as p JOIN Vendor as v ON {p.acerChemVendor} = {v.pk} "
				+ " JOIN Employee as e ON {e.toVendor} = {v.pk}} " + " where {e.pk}={v.toEmployee} and {e.uid} = ?id }})";
		final FlexibleSearchQuery query = new FlexibleSearchQuery(SQL);
		
		query.addQueryParameter("id", uid);
		final SearchResult<StockLevelModel> result = flexibleSearchService.search(query);
		return result.getResult();
	}

	@Override
	public List<OrderEntryModel> getOrderEntryProduct(final String uid, final Date startDate, final Date endDate) {
		if (StringUtils.isBlank(uid) || startDate == null || endDate == null ||endDate.before(startDate)) {
			return null;
		}
		final String SQL="select {oe.pk} from {OrderEntry as oe JOIN Order as o ON {oe.order}={o.pk} } where " +
	                 "{o.creationtime} > ?startDate AND {o.creationtime} < ?endDate AND {oe.product} IN ({{ " +
	                 "select {p.pk} from {Product as p JOIN Vendor as v ON {p.acerChemVendor} = {v.pk} "
	 				+ " JOIN Employee as e ON {e.toVendor} = {v.pk}} " + " where {e.pk}={v.toEmployee} and {e.uid} = ?id }})";
		final FlexibleSearchQuery query = new FlexibleSearchQuery(SQL);
		

		query.addQueryParameter("id", uid);
		
		query.addQueryParameter("startDate", startDate);
		query.addQueryParameter("endDate", endDate);
		final SearchResult<OrderEntryModel> result = flexibleSearchService.search(query);
		return result.getResult();
	}

	@Override
	public List<AcerchemProductPriceBean> getProductWithBaserealPrice(final String month) {
		if(StringUtils.isNotBlank(month)){
		final String SQL = "select {oe.pk} from {OrderEntry as oe JOIN Order as o ON {oe.order}={o.pk} } "+
		             "where DATE_FORMAT({o.creationtime},'%Y%m') =?month order by {o.creationtime}";
		final FlexibleSearchQuery query = new FlexibleSearchQuery(SQL);
		
		query.addQueryParameter("month", month);
		//query.setResultClassList(Arrays.asList(OrderEntryModel.class, Date.class));
		final SearchResult<OrderEntryModel> result = flexibleSearchService.search(query);
		
		final List<OrderEntryModel> list = result.getResult();
		
		if (CollectionUtils.isNotEmpty(list)){
			final List<AcerchemProductPriceBean> resultList = new ArrayList<>();
			for(final OrderEntryModel oe : list){
				
				final Date placeOrderTime = oe.getOrder().getCreationtime();
				final AcerchemProductPriceBean bean = new AcerchemProductPriceBean();
				
				bean.setProductCode(oe.getProduct().getCode());
				bean.setProductName(oe.getProduct().getName());
				bean.setSaleQuantity(oe.getQuantity());
				bean.setBaseRealPrice(oe.getBasePrice());
				bean.setOrderPlaceTime(placeOrderTime);
			
				//计算周次
				final Calendar calendar = Calendar.getInstance();
				calendar.setTime(placeOrderTime);
				final int week = calendar.get(Calendar.WEEK_OF_MONTH);
				bean.setWeeknum(week);
			
				resultList.add(bean);
			}
			return resultList;
			
		}
		
		
		}
		return null;
	}

	@Override
	public List<AcerchemProductBuyerBean> getProductSalesForReport(final String month, final String categoryCode, final String area,
			final String countryCode) {
		String SQL = "select {e.pk},{ua.pk} from {OrderEntry as e" + " JOIN Order as o ON {e:order} = {o:pk}"
				+ " JOIN Customer as u ON {u:pk} = {o:user}" + " JOIN Address as ua ON {ua:owner} = {u:pk}"
				+ " JOIN Country as uc ON {uc:pk} = {ua:country}" + "} where {ua:contactAddress} = true ";
		final Map<String, Object> params = new HashMap<String, Object>();
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
		
		if(StringUtils.isNotBlank(categoryCode)){
			SQL += " AND {e:product} in ({{" +
                    "select {p1.pk} from {Product as p1 JOIN CategoryProductRelation as cpr on {cpr.target} = {p1.pk} " +
                    "JOIN Category as cat on {cpr.source} = {cat.pk}} " + 
                    "where {cat.code}=?categoryCode}})";
			
			params.put("categoryCode", categoryCode);
		}
		
		final StringBuilder builder = new StringBuilder(SQL);
		final FlexibleSearchQuery query = new FlexibleSearchQuery(builder.toString());
		
		query.setResultClassList(Arrays.asList(OrderEntryModel.class, AddressModel.class));
		query.addQueryParameters(params);
		
		final SearchResult<List<Object>> result = flexibleSearchService.search(query);
		
		final List<AcerchemProductBuyerBean> reportList = new ArrayList<>();
		
		if (CollectionUtils.isNotEmpty(result.getResult())){
			
			for(final List<Object> item : result.getResult()){
				final OrderEntryModel oe = (OrderEntryModel)item.get(0);
				final AddressModel addr = (AddressModel)item.get(1);
				
				final AcerchemProductBuyerBean bean = new AcerchemProductBuyerBean();
				if(oe.getOrder().getUser()!=null){
					if(oe.getOrder().getUser().getArea()!=null){
						bean.setAreaOfBuyer(oe.getOrder().getUser().getArea().getCode());
					}
					
					bean.setBuyer(oe.getOrder().getUser().getName());
				}
				
				
				
				bean.setBuyQuantity(oe.getQuantity());
				
				//Collection<CategoryModel> cate = oe.getProduct().getSupercategories();
				//bean.setCategory(category);
				final Date placeOrderDate = oe.getOrder().getCreationtime();
				
				final SimpleDateFormat format = new SimpleDateFormat("yyyyMM");
				final String orderMonth = format.format(placeOrderDate);
				bean.setMonth(orderMonth);
				
				bean.setProductCode(oe.getProduct().getCode());
				bean.setProductName(oe.getProduct().getName());
				
				if (addr != null ) {
					if(addr.getCountry()!=null){
						bean.setCountryOfBuyer(addr.getCountry().getName());
					}
				}
				
				reportList.add(bean);
			}
			
		}
		
		return reportList;
	}

}
