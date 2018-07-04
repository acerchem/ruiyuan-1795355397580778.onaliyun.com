package com.acerchem.core.service;

import java.util.Date;
import java.util.List;

import de.hybris.platform.commercefacades.product.data.ProductPriceAnalysisData;
import de.hybris.platform.commercefacades.product.data.ProductSalesRecordData;
import de.hybris.platform.commercefacades.vendor.data.InventoryReportData;
import de.hybris.platform.commercefacades.vendor.data.OrderProductReportData;
import de.hybris.platform.core.model.product.ProductModel;

public interface AcerChemProductService {

	/**
	 * 通过模糊匹配供应商名称，获得商品列表
	 * 
	 * @param vendorName
	 * @return
	 */
	public List<ProductModel> getProductByVendorName(final String vendorName);

	/**
	 * 通过供应商代码，获得商品列表
	 * 
	 * @param vendorCode
	 * @return
	 */
	public List<ProductModel> getProductByVendorCode(final String vendorCode);

	/**
	 * 通过商品代码和供应商代码，判断是否商品是否存在该供应商
	 * 
	 * @param productCode
	 * @param vendorCode
	 * @return
	 */
	public boolean isExistProductWithVendor(final String productCode, final String vendorCode);

	/**
	 * 通过供应商账户，得到供应商产品库存
	 * 
	 * @param uid
	 * @return
	 */
	public List<InventoryReportData> getInventoryProductByVendor(String uid);

	/**
	 * 通过供应商账户和下单时间段，得到供应商产品所在的订单
	 * 
	 * @param uid
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public List<OrderProductReportData> getOrderProductByVendor(String uid, Date startDate, Date endDate);

	public List<ProductPriceAnalysisData> getProductWithBaserealPrice(final String month);

	public List<ProductSalesRecordData> getProductSalesForReport(final String month, final String categoryCode,
			final String area, final String countryCode);
}
