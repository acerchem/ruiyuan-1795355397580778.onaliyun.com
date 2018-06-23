package com.acerchem.core.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;

import com.acerchem.core.dao.AcerChemProductDao;
import com.acerchem.core.dao.AcerChemVendorDao;
import com.acerchem.core.service.AcerChemProductService;

import de.hybris.platform.commercefacades.vendor.data.InventoryReportData;
import de.hybris.platform.commercefacades.vendor.data.OrderProductReportData;
import de.hybris.platform.core.model.order.OrderEntryModel;
import de.hybris.platform.core.model.product.ProductModel;
import de.hybris.platform.ordersplitting.model.StockLevelModel;
import de.hybris.platform.ordersplitting.model.VendorModel;
import de.hybris.platform.product.ProductService;

public class AcerChemProductServiceImpl implements AcerChemProductService {

	@Resource
	private AcerChemProductDao acerChemProductDao;
	@Resource
	private ProductService  productService;
	@Resource
	private  AcerChemVendorDao acerChemVendorDao;
	@Override
	public List<ProductModel> getProductByVendorName(final String vendorName) {
		// TODO Auto-generated method stub
		return acerChemProductDao.getProductByVendorName(vendorName);
	}

	@Override
	public List<ProductModel> getProductByVendorCode(final String vendorCode) {
		// TODO Auto-generated method stub
		return acerChemProductDao.getProductByVendorCode(vendorCode);
	}

	@Override
	public boolean isExistProductWithVendor(final String productCode, final String vendorCode) {
		// TODO Auto-generated method stub
		return acerChemProductDao.isExistProductWithVendor(productCode, vendorCode);
	}

	@Override
	public List<InventoryReportData> getInventoryProductByVendor(final String uid) {
		
		final List<InventoryReportData> report = new ArrayList<InventoryReportData>();
		final List<StockLevelModel> stocks = acerChemProductDao.getInventoryProduct(uid);
		if(CollectionUtils.isNotEmpty(stocks)){
			
			for(final StockLevelModel stock:stocks){
				final InventoryReportData item = new InventoryReportData();
				final ProductModel product = productService.getProductForCode(stock.getProductCode());//because of StockLevelModel's product attribute invalid
				
				item.setProductCode(stock.getProductCode());
				item.setProductName(product.getName());
				final int inventory = stock.getAvailable() - stock.getReserved();
				item.setInventoryCount(inventory);
				item.setFutureInventory(stock.getPreOrder());
				
				report.add(item);
				
			}
		}
		
		
		return report;
	}

	@Override
	public List<OrderProductReportData> getOrderProductByVendor(final String uid, final Date startDate, final Date endDate) {
		final List<OrderProductReportData> report = new ArrayList<OrderProductReportData>();
		
		final List<OrderEntryModel> orderEntries = acerChemProductDao.getOrderEntryProduct(uid, startDate, endDate);
		if(CollectionUtils.isNotEmpty(orderEntries)){
			final VendorModel vendor = acerChemVendorDao.getVendorByEmployeeUid(uid);
			final String vendorName =vendor != null?vendor.getName():"";
			for (final OrderEntryModel oe : orderEntries){
				final OrderProductReportData item = new OrderProductReportData();
				
				item.setOrderCode(oe.getOrder().getCode());
				item.setFinishedTime(oe.getOrder().getOrderFinishedDate());
				item.setPlaceTime(oe.getOrder().getCreationtime());
				item.setProductName(oe.getProduct().getName());
				item.setProductQuantity(oe.getQuantity().intValue());
				item.setVendorName(vendorName);
				
				report.add(item);
			}
		}
		return report;
	}

}
