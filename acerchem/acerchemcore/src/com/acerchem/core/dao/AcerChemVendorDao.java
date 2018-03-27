package com.acerchem.core.dao;

import java.util.List;

import de.hybris.platform.ordersplitting.model.VendorModel;

public interface AcerChemVendorDao {
	

	/**
	 * 通过like商品名称,获得供应商列表
	 * @param productName
	 * @return
	 */
	public List<VendorModel> getVendorsByProductName(final String productName);
	/**
	 * 通过产品代码，只能获得一个供应商
	 * @param productCode
	 * @return
	 */
	public VendorModel getVendorByProductCode(final String productCode);
}
