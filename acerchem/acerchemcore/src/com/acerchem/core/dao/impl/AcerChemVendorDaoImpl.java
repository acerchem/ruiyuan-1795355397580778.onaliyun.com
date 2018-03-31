package com.acerchem.core.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;

import com.acerchem.core.dao.AcerChemVendorDao;

import de.hybris.platform.core.model.product.ProductModel;
import de.hybris.platform.ordersplitting.model.VendorModel;
import de.hybris.platform.servicelayer.search.FlexibleSearchQuery;
import de.hybris.platform.servicelayer.search.FlexibleSearchService;
import de.hybris.platform.servicelayer.search.SearchResult;

public class AcerChemVendorDaoImpl implements AcerChemVendorDao {

	@Resource
	private FlexibleSearchService flexibleSearchService;

	@Override
	public List<VendorModel> getVendorsByProductName(String productName) {
		final String SQL = "select {v.pk} from {" + VendorModel._TYPECODE + " as v JOIN " + ProductModel._TYPECODE
				+ " as p" + " ON {p." + ProductModel.ACERCHEMVENDOR + "} = {v." + VendorModel.PK + "} }" + " where {p."
				+ ProductModel.NAME + "} like ?prodName";

		final Map<String, Object> params = new HashMap<String, Object>();
		final StringBuilder builder = new StringBuilder(SQL);
		params.put("prodName", "'%" + productName + "%'");

		final FlexibleSearchQuery query = new FlexibleSearchQuery(builder.toString());
		query.addQueryParameters(params);

		final SearchResult<VendorModel> result = flexibleSearchService.search(query);
		return result.getResult();
	}

	@Override
	public VendorModel getVendorByProductCode(String productCode) {
		final String SQL = "select {v.pk} from {" + VendorModel._TYPECODE + " as v JOIN " + ProductModel._TYPECODE
				+ " as p" + " ON {p." + ProductModel.ACERCHEMVENDOR + "} = {v." + VendorModel.PK + "} }" + " where {p."
				+ ProductModel.CODE + "} = ?prodCode";

		final Map<String, Object> params = new HashMap<String, Object>();
		final StringBuilder builder = new StringBuilder(SQL);
		params.put("prodCode", productCode);

		final FlexibleSearchQuery query = new FlexibleSearchQuery(builder.toString());
		query.addQueryParameters(params);

		final SearchResult<VendorModel> result = flexibleSearchService.search(query);

		if (result.getCount() > 0) {
			return result.getResult().get(0);
		}
		return null;
	}

}
