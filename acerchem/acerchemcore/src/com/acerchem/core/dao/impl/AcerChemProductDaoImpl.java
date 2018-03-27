package com.acerchem.core.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.acerchem.core.dao.AcerChemProductDao;
import com.acerchem.core.model.ImageUploadedLogModel;

import de.hybris.platform.core.model.product.ProductModel;
import de.hybris.platform.ordersplitting.model.VendorModel;
import de.hybris.platform.servicelayer.search.FlexibleSearchQuery;

public class AcerChemProductDaoImpl implements AcerChemProductDao {

	@Override
	public List<ProductModel> getProductByVendorName(String vendorName) {
//		final String SQL = "select {pk} from {" + ProductModel._TYPECODE + "} where {"
//				+ ProductModel.LOCATION + "} =?location";
		
//		final Map<String, Object> params = new HashMap<String, Object>();
//		final StringBuilder builder = new StringBuilder(SQL);
//		params.put("location", location);
//		
//		final FlexibleSearchQuery query = new FlexibleSearchQuery(builder.toString());
//		query.addQueryParameters(params);
		
		return null;
	}

	@Override
	public List<ProductModel> getProductByVendorCode(String vendorCode) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public VendorModel getVendorByProductCode(String productCode) {
		// TODO Auto-generated method stub
		return null;
	}

}
