package com.acerchem.core.service;

import java.util.List;

import de.hybris.platform.core.model.product.ProductModel;

public interface AcerChemProductService {

	/**
	 * 通过模糊匹配供应商名称，获得商品列表
	 * @param vendorName
	 * @return
	 */
	public List<ProductModel> getProductByVendorName(final String vendorName);
	
	/**
	 * 通过供应商代码，获得商品列表
	 * @param vendorCode
	 * @return
	 */
	public List<ProductModel> getProductByVendorCode(final String vendorCode);
	
	/**
	 * 通过商品代码和供应商代码，判断是否商品是否存在该供应商
	 * @param productCode
	 * @param vendorCode
	 * @return
	 */
	public boolean isExistProductWithVendor(final String productCode,final String vendorCode);
}
