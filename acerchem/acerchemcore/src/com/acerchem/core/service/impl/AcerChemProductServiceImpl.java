package com.acerchem.core.service.impl;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;

import com.acerchem.core.dao.AcerChemProductDao;
import com.acerchem.core.dao.AcerChemVendorDao;
import com.acerchem.core.report.order.beans.AcerchemProductBuyerBean;
import com.acerchem.core.report.order.beans.AcerchemProductPriceBean;
import com.acerchem.core.service.AcerChemProductService;
import com.acerchem.core.util.CommonConvertTools;

import de.hybris.platform.commercefacades.product.data.ProductPriceAnalysisData;
import de.hybris.platform.commercefacades.product.data.ProductSalesRecordData;
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
	private ProductService productService;
	@Resource
	private AcerChemVendorDao acerChemVendorDao;

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
		if (CollectionUtils.isNotEmpty(stocks)) {

			for (final StockLevelModel stock : stocks) {
				final InventoryReportData item = new InventoryReportData();
				final ProductModel product = productService.getProductForCode(stock.getProductCode());// because
																										// of
																										// StockLevelModel's
																										// product
																										// attribute
																										// invalid

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
	public List<OrderProductReportData> getOrderProductByVendor(final String uid, final Date startDate,
			final Date endDate) {
		final List<OrderProductReportData> report = new ArrayList<OrderProductReportData>();

		final List<OrderEntryModel> orderEntries = acerChemProductDao.getOrderEntryProduct(uid, startDate, endDate);
		if (CollectionUtils.isNotEmpty(orderEntries)) {
			final VendorModel vendor = acerChemVendorDao.getVendorByEmployeeUid(uid);
			final String vendorName = vendor != null ? vendor.getName() : "";
			for (final OrderEntryModel oe : orderEntries) {
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

	@Override
	public List<ProductPriceAnalysisData> getProductWithBaserealPrice(final String month) {
		final List<AcerchemProductPriceBean> listWithWeek = acerChemProductDao.getProductWithBaserealPrice(month);

		final List<ProductPriceAnalysisData> report = new ArrayList<>();
		
		//通过map分组 week
		final Map<Integer,List<AcerchemProductPriceBean>> map = new HashMap<Integer,List<AcerchemProductPriceBean>>();
		
		
		
		if (CollectionUtils.isNotEmpty(listWithWeek)) {
			if (listWithWeek.size() > 0) {
				// 按code和weeknum组合计算
				listWithWeek.sort(compatatorbyWeekCode);
				String code = listWithWeek.get(0).getProductCode();
				int week = listWithWeek.get(0).getWeeknum();
				
				int count = 0;
				long quanlity = 0;
				double price = 0;
				final List<AcerchemProductPriceBean> comboList = new ArrayList<>();
				// CommonConvertTools

				for (int i = 0; i < listWithWeek.size(); i++) {
					final AcerchemProductPriceBean bean = listWithWeek.get(i);
					if (week == bean.getWeeknum() && code.equals(bean.getProductCode())) {
						count++;
						quanlity += bean.getSaleQuantity().longValue();
						price = CommonConvertTools.addDouble(price, bean.getBaseRealPrice()).doubleValue();

					} else {
						code = bean.getProductCode();
						week = bean.getWeeknum();

						i--;
						final AcerchemProductPriceBean pre = listWithWeek.get(i);
						final AcerchemProductPriceBean combo = new AcerchemProductPriceBean();

						combo.setProductCode(pre.getProductCode());
						combo.setProductName(pre.getProductName());
						combo.setWeeknum(pre.getWeeknum());

						combo.setBaseRealPrice(Double.valueOf(price / count));
						combo.setSaleQuantity(Long.valueOf(quanlity));
						comboList.add(combo);

						count = 0;
						quanlity = 0;
						price = 0;

					}
				}
				// 处理最后组合

				final AcerchemProductPriceBean last = listWithWeek.get(listWithWeek.size()-1);

				final AcerchemProductPriceBean combolast = new AcerchemProductPriceBean();

				combolast.setProductCode(last.getProductCode());
				combolast.setProductName(last.getProductName());
				combolast.setWeeknum(last.getWeeknum());

				combolast.setBaseRealPrice(Double.valueOf(price / count));
				combolast.setSaleQuantity(Long.valueOf(quanlity));
				comboList.add(combolast);

				// 填充最后报表
				comboList.sort(compatatorbyCodeWeek);
				String codeReport = comboList.get(0).getProductCode();
				long sumQuantity = 0;
				double one = 0;
				double two = 0;
				double three = 0;
				double four = 0;
				double five = 0;
				double six = 0;
				int xcount = 0;
				// final double xprice=0;
				for (int i = 0; i < comboList.size(); i++) {
					final AcerchemProductPriceBean bean = comboList.get(i);
					if (codeReport.equals(bean.getProductCode())) {
						sumQuantity += bean.getSaleQuantity().longValue();
						if (bean.getWeeknum() == 1) {
							xcount++;
							one += bean.getBaseRealPrice();
						} else if (bean.getWeeknum() == 2) {
							xcount++;
							two += bean.getBaseRealPrice();
						} else if (bean.getWeeknum() == 3) {
							xcount++;
							three += bean.getBaseRealPrice();
						} else if (bean.getWeeknum() == 4) {
							xcount++;
							four += bean.getBaseRealPrice();
						} else if (bean.getWeeknum() == 5) {
							xcount++;
							five += bean.getBaseRealPrice();

						} else {
							xcount++;
							six += bean.getBaseRealPrice();
						}

					} else {
						codeReport = bean.getProductCode();

						i--;
						final AcerchemProductPriceBean pre = listWithWeek.get(i);
						final ProductPriceAnalysisData item = new ProductPriceAnalysisData();
						item.setProductCode(pre.getProductCode());
						item.setProductName(pre.getProductName());
						item.setSalesQuantity(Long.valueOf(sumQuantity));
						item.setMaxWeek(xcount);
						final double sumprice = CommonConvertTools
								.addDouble(
										CommonConvertTools.addDouble(CommonConvertTools.addDouble(CommonConvertTools
												.addDouble(CommonConvertTools.addDouble(one, two), three), four), five),
										six);
						item.setAveragePrice(sumprice / xcount);

						item.setFirstWeekPrice(one);
						item.setSecondWeekPrice(two);
						item.setThirdWeekPrice(three);
						item.setFouthWeekPrice(four);
						item.setFifthWeekPrice(five);
						item.setSixthWeekPrice(six);

						report.add(item);

						sumQuantity = 0;
						one = 0;
						two = 0;
						three = 0;
						four = 0;
						five = 0;
						six = 0;
						xcount = 0;
					}
				}

				// 处理最后项
				final AcerchemProductPriceBean lastReport = listWithWeek.get(listWithWeek.size()-1);
				final ProductPriceAnalysisData item = new ProductPriceAnalysisData();

				item.setProductCode(lastReport.getProductCode());
				item.setProductName(lastReport.getProductName());
				item.setSalesQuantity(Long.valueOf(sumQuantity));
				item.setMaxWeek(xcount);
				final double sumprice = CommonConvertTools.addDouble(CommonConvertTools.addDouble(
						CommonConvertTools.addDouble(
								CommonConvertTools.addDouble(CommonConvertTools.addDouble(one, two), three), four),
						five), six);
				item.setAveragePrice(sumprice / xcount);

				item.setFirstWeekPrice(one);
				item.setSecondWeekPrice(two);
				item.setThirdWeekPrice(three);
				item.setFouthWeekPrice(four);
				item.setFifthWeekPrice(five);
				item.setSixthWeekPrice(six);

				report.add(item);

			}
		}
		return report;
	}

	private static Comparator<AcerchemProductPriceBean> compatatorbyWeekCode = new Comparator<AcerchemProductPriceBean>() {

		@Override
		public int compare(final AcerchemProductPriceBean o1, final AcerchemProductPriceBean o2) {
			// TODO Auto-generated method stub
			int n = o1.getWeeknum() - o2.getWeeknum();
			if (n==0 ) {
				n = o1.getProductCode().compareTo(o2.getProductCode());
			}

			return n;
		}

	};

	private static Comparator<AcerchemProductPriceBean> compatatorbyCodeWeek = new Comparator<AcerchemProductPriceBean>() {

		@Override
		public int compare(final AcerchemProductPriceBean o1, final AcerchemProductPriceBean o2) {
			// TODO Auto-generated method stub
			int n = o1.getProductCode().compareTo(o2.getProductCode());

			if (n==0) {
				n = o1.getWeeknum() - o2.getWeeknum();
			}

			return n;
		}

	};

	@Override
	public List<ProductSalesRecordData> getProductSalesForReport(final String month, final String categoryCode,
			final String area, final String countryCode) {
		final List<AcerchemProductBuyerBean> list = acerChemProductDao.getProductSalesForReport(month, categoryCode,
				area, countryCode);

		final List<ProductSalesRecordData> report = new ArrayList<>();
		if (CollectionUtils.isNotEmpty(list)) {
			if (list.size() > 0) {
				list.sort(compatatorForSales);
				String pName = list.get(0).getProductName();
				String customer = list.get(0).getBuyer();

				long quantity = 0;
				for (int i = 0; i < list.size(); i++) {
					final AcerchemProductBuyerBean bean = list.get(i);
					if (pName.equals(bean.getProductName()) && customer.equals(bean.getBuyer())) {
						quantity += bean.getBuyQuantity().longValue();

					} else {
						pName = bean.getProductName();
						customer = bean.getBuyer();

						i--;
						final AcerchemProductBuyerBean old = list.get(i);
						final ProductSalesRecordData item = new ProductSalesRecordData();

						item.setProductCode(old.getProductCode());
						item.setProductName(old.getProductName());
						item.setCustomerName(old.getBuyer());
						item.setSalesQuantity(quantity);

						report.add(item);
						quantity = 0;
					}

				}
				// 处理最后
				final AcerchemProductBuyerBean last = list.get(list.size()-1);
				final ProductSalesRecordData item = new ProductSalesRecordData();

				item.setProductCode(last.getProductCode());
				item.setProductName(last.getProductName());
				item.setCustomerName(last.getBuyer());
				item.setSalesQuantity(quantity);

				report.add(item);

			}

		}
		return report;
	}

	private static Comparator<AcerchemProductBuyerBean> compatatorForSales = new Comparator<AcerchemProductBuyerBean>() {

		@Override
		public int compare(final AcerchemProductBuyerBean o1, final AcerchemProductBuyerBean o2) {

			return o1.getProductName().compareTo(o2.getProductName());
		}

	};

}
