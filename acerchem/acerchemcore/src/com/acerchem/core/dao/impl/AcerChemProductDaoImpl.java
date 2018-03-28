package com.acerchem.core.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.acerchem.core.dao.AcerChemProductDao;
import com.acerchem.core.model.ImageUploadedLogModel;

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
	public List<ProductModel> getProductByVendorName(String vendorName) {
		final String SQL = "select {p.pk} from {" + ProductModel._TYPECODE + 
				" as p JOIN AcerChemVendor2Product as p2v"+ 
	            " ON {p2v.target} = {p."+ProductModel.PK+"}"+
				" JOIN " + VendorModel._TYPECODE + " as v" +
	            " ON {p2v.source} = {v." + VendorModel.PK + "} }" +
				" where {v." + VendorModel.NAME + "} like ?vendorName" ;
	                                                                          																																					
		final Map<String, Object> params = new HashMap<String, Object>();
		final StringBuilder builder = new StringBuilder(SQL);
		params.put("vendorName", "'%"+vendorName+"%'");
		
		final FlexibleSearchQuery query = new FlexibleSearchQuery(builder.toString());
		query.addQueryParameters(params);
		
		final SearchResult<ProductModel> result = flexibleSearchService.search(query);
		return result.getResult();
	}

	@Override
	public List<ProductModel> getProductByVendorCode(String vendorCode) {
		final String SQL = "select {p.pk} from {" + ProductModel._TYPECODE + 
				" as p JOIN AcerChemVendor2Product as p2v"+ 
	            " ON {p2v.target} = {p."+ProductModel.PK+"}"+
				" JOIN " + VendorModel._TYPECODE + " as v" +
	            " ON {p2v.source} = {v." + VendorModel.PK + "} }" +
				" where {v." + VendorModel.CODE + "} = ?vendorCode" ;
	                                                                          																																					
		final Map<String, Object> params = new HashMap<String, Object>();
		final StringBuilder builder = new StringBuilder(SQL);
		params.put("vendorCode", vendorCode);
		
		final FlexibleSearchQuery query = new FlexibleSearchQuery(builder.toString());
		query.addQueryParameters(params);
		
		final SearchResult<ProductModel> result = flexibleSearchService.search(query);
		return result.getResult();
	}

	
	@Override
	public boolean isExistProductWithVendor(String productCode, String vendorCode) {
		final String SQL = "select {p.pk} from {" + ProductModel._TYPECODE + 
				" as p JOIN AcerChemVendor2Product as p2v"+ 
	            " ON {p2v.target} = {p."+ProductModel.PK+"}"+
				" JOIN " + VendorModel._TYPECODE + " as v" +
	            " ON {p2v.source} = {v." + VendorModel.PK + "} }" +
				" where {v." + VendorModel.CODE + "} = ?vendorCode" + 
	            " AND {p." + ProductModel.CODE + "} = ?prodCode";
	                                                                          																																					
		final Map<String, Object> params = new HashMap<String, Object>();
		final StringBuilder builder = new StringBuilder(SQL);
		params.put("vendorCode", vendorCode);
		params.put("prodCode", productCode);
		
		final FlexibleSearchQuery query = new FlexibleSearchQuery(builder.toString());
		query.addQueryParameters(params);
		
		final SearchResult<ProductModel> result = flexibleSearchService.search(query);
		if(result.getCount() > 0){
			return true;
		}
		return false;
	}
	
}
