package com.acerchem.core.dao;

import java.util.Date;
import java.util.List;

import com.acerchem.core.report.order.beans.AcerchemProductBuyerBean;
import com.acerchem.core.report.order.beans.AcerchemProductPriceBean;

import de.hybris.platform.core.model.order.OrderEntryModel;
import de.hybris.platform.core.model.product.ProductModel;
import de.hybris.platform.ordersplitting.model.StockLevelModel;

public interface AcerChemProductDao {
	
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

	/**
	 * 通过供应商的emloyee账号对应的商品，得到库存产品
	 * @param employee
	 * @return
	 */
	public List<StockLevelModel> getInventoryProduct(final String uid);

	/**
	 * 通过供应商的emloyee账号对应的商品，和下单时间段，得到订单行
	 * @param uid
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public List<OrderEntryModel> getOrderEntryProduct(String uid,Date startDate, Date endDate);

	public List<AcerchemProductPriceBean> getProductWithBaserealPrice(String month,String productName,String productCode);

	public List<AcerchemProductPriceBean> getProductWithBaserealPrice(String month,String productName,String productCode,String vendorCode);

	public List<AcerchemProductBuyerBean> getProductSalesForReport(String month,String category,String area,String country,String productName,String productCode,String employeeName);

	public List<AcerchemProductBuyerBean> getProductSalesForReport(String month,String category,String area,String country,String productName,String productCode,String employeeName,String vendorCode);

	public List<StockLevelModel> getInventory(final String vendorCode);
	
	public List<OrderEntryModel> getOrderEntryProductByVendorcode(String vendorcode,Date startDate, Date endDate);

}
