package com.acerchem.core.dao.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;

import com.acerchem.core.dao.AcerChemProductDao;

import de.hybris.platform.core.model.order.OrderEntryModel;
import de.hybris.platform.core.model.product.ProductModel;
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

}
