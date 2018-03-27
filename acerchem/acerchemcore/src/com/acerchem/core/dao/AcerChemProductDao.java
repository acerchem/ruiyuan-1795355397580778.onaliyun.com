package com.acerchem.core.dao;

import java.util.List;

import de.hybris.platform.core.model.product.ProductModel;
import de.hybris.platform.ordersplitting.model.VendorModel;

public interface AcerChemProductDao {
	
	public List<ProductModel> getProductByVendorName(final String vendorName);
	public List<ProductModel> getProductByVendorCode(final String vendorCode);
	
	public VendorModel getVendorByProductCode(final String productCode);
	

}
