package com.acerchem.core.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.util.Assert;

import com.acerchem.core.dao.AcerChemVendorDao;

import de.hybris.platform.core.model.product.ProductModel;
import de.hybris.platform.core.model.user.UserModel;
import de.hybris.platform.ordersplitting.model.VendorModel;
import de.hybris.platform.servicelayer.search.FlexibleSearchQuery;
import de.hybris.platform.servicelayer.search.FlexibleSearchService;
import de.hybris.platform.servicelayer.search.SearchResult;

public class AcerChemVendorDaoImpl implements AcerChemVendorDao {

	@Resource
	private FlexibleSearchService flexibleSearchService;

	@Override
	public List<VendorModel> getVendorsByProductName(final String productName) {
		final String SQL = "select {v.pk} from {" + VendorModel._TYPECODE + " as v JOIN " + ProductModel._TYPECODE
				+ " as p" + " ON {p." + ProductModel.ACERCHEMVENDOR + "} = {v." + VendorModel.PK + "} }" + " where {p."
				+ ProductModel.NAME + "} like ?prodName";

		final Map<String, Object> params = new HashMap<String, Object>();
		final StringBuilder builder = new StringBuilder(SQL);
		params.put("prodName", "%" + productName + "%");

		final FlexibleSearchQuery query = new FlexibleSearchQuery(builder.toString());
		query.addQueryParameters(params);

		final SearchResult<VendorModel> result = flexibleSearchService.search(query);
		return result.getResult();
	}

	@Override
	public VendorModel getVendorByProductCode(final String productCode) {
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

	@Override
	public VendorModel getVendorByEmployeeUid(final String uid) {
		if(StringUtils.isBlank(uid)){
			return null;
		}
		final String SQL ="select {v.pk} from {Vendor as v JOIN Employee as e ON {v.pk}={e.toVendor}} where {e.uid} = ?uid";
		
		final FlexibleSearchQuery query = new FlexibleSearchQuery(SQL);
		query.addQueryParameter("uid", uid);
		final SearchResult<VendorModel> result = flexibleSearchService.search(query);
		
		if (result.getCount() > 0){
			return result.getResult().get(0);
		}
		return null;
	}

	@Override
	public List<VendorModel> getAllVendors() {
		final String SQL = "select {pk} from {Vendor}";
		final FlexibleSearchQuery query = new  FlexibleSearchQuery(SQL);
		
		final SearchResult<VendorModel> result = flexibleSearchService.search(query);
		
		return result.getResult();
	}

	@Override
	public UserModel getEmployeeByVendorCode(final String vendorCode) {
		Assert.notNull(vendorCode);
		final String SQL = "select {e.pk} from {Employee as e JOIN Vendor as v ON {e.toVendor}={v.pk}} where {v.code}=?code ";
		final FlexibleSearchQuery query = new  FlexibleSearchQuery(SQL);
		
		query.addQueryParameter("code", vendorCode);
		 final SearchResult<UserModel> result = flexibleSearchService.search(query);
		 
		 if(result.getCount()>0){
			 return result.getResult().get(0);
		 }
		return null;
	}

	@Override
	public VendorModel getVendorByCode(final String code) {
		Assert.notNull(code);
		final String SQL = "select {pk} from {Vendor} where {code} = ?code";
		final FlexibleSearchQuery query = new  FlexibleSearchQuery(SQL);
		query.addQueryParameter("code", code);
		 final SearchResult<VendorModel> result = flexibleSearchService.search(query);
		if (result.getCount()>0){
			return result.getResult().get(0);
		}
		return null;
	}
	
	
	
}
