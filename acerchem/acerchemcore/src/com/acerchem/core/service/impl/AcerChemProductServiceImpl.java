package com.acerchem.core.service.impl;

import java.util.*;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

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
import de.hybris.platform.core.model.user.CustomerModel;
import de.hybris.platform.ordersplitting.model.StockLevelModel;
import de.hybris.platform.product.ProductService;
import de.hybris.platform.product.daos.ProductDao;

public class AcerChemProductServiceImpl implements AcerChemProductService {
	private static final Logger LOG = Logger.getLogger(AcerChemProductServiceImpl.class);
	@Resource
	private AcerChemProductDao acerChemProductDao;
	@Resource
	private ProductService productService;
	@Resource
	private AcerChemVendorDao acerChemVendorDao;

	@Resource
	private ProductDao productDao;

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
				final ProductModel product = getProduct(stock.getProductCode());
				if (product != null) {
					item.setProductCode(stock.getProductCode());
					item.setProductName(product.getName());
					final int inventory = stock.getAvailable() - stock.getReserved();
					item.setInventoryCount(inventory);
					item.setFutureInventory(stock.getPreOrder());

					report.add(item);
				}
			}
		}

		return report;
	}

	@Override
	public List<InventoryReportData> getInventory(final String vendorCode) {
		final List<InventoryReportData> report = new ArrayList<InventoryReportData>();
		final List<StockLevelModel> stocks = acerChemProductDao.getInventory(vendorCode);
		if (CollectionUtils.isNotEmpty(stocks)) {

			for (final StockLevelModel stock : stocks) {
				final InventoryReportData item = new InventoryReportData();
				final ProductModel product = getProduct(stock.getProductCode());

				if (product != null) {
					item.setProductCode(stock.getProductCode());
					item.setProductName(product.getName());
					final int inventory = stock.getAvailable() - stock.getReserved();
					item.setInventoryCount(inventory);
					item.setFutureInventory(stock.getPreOrder());

					report.add(item);
				}
			}
		}

		return report;
	}

	@Override
	public List<InventoryReportData> getInventory(final List<InventoryReportData> list) {
		final List<InventoryReportData> report = new ArrayList<InventoryReportData>();
		if (CollectionUtils.isNotEmpty(list)) {
			if (list.size() > 0) {
				list.sort(compatatorInventory);
				String pCode = list.get(0).getProductCode();
				int store = 0;
				int future = 0;
				final int count = list.size();
				for (int i = 0; i < count; i++) {
					final InventoryReportData data = list.get(i);
					if (pCode.equals(data.getProductCode())) {
						store += data.getInventoryCount();
						future += data.getFutureInventory();
					} else {
						pCode = data.getProductCode();
						i--;
						final String itemCode = list.get(i).getProductCode();
						final String itemName = list.get(i).getProductName();
						final InventoryReportData item = new InventoryReportData();
						item.setProductCode(itemCode);
						item.setProductName(itemName);
						item.setFutureInventory(future);
						item.setInventoryCount(store);

						report.add(item);
						store = 0;
						future = 0;
					}

				}

				// last item union
				final String lastCode = list.get(count - 1).getProductCode();
				final String lastName = list.get(count - 1).getProductName();
				final InventoryReportData item = new InventoryReportData();
				item.setProductCode(lastCode);
				item.setProductName(lastName);
				item.setFutureInventory(future);
				item.setInventoryCount(store);

				report.add(item);

			}
		}

		return report;
	}

	private static Comparator<InventoryReportData> compatatorInventory = new Comparator<InventoryReportData>() {

		@Override
		public int compare(final InventoryReportData o1, final InventoryReportData o2) {
			// TODO Auto-generated method stub
			return o1.getProductCode().compareTo(o2.getProductCode());
		}

	};

	public ProductModel getProduct(final String code) {
		final List<ProductModel> list = productDao.findProductsByCode(code);

		if (CollectionUtils.isNotEmpty(list) && list.size() > 0) {
			for (final ProductModel p : list) {

				if (p.getCatalogVersion().getVersion().equals("Online")) {
					return p;
				}

			}

		}
		return null;
	}

	@Override
	public List<OrderProductReportData> getOrderProductByVendor(final String vendorcode, final Date startDate,
			final Date endDate) {
		final List<OrderProductReportData> report = new ArrayList<OrderProductReportData>();

		final List<OrderEntryModel> orderEntries = acerChemProductDao.getOrderEntryProductByVendorcode(vendorcode,
				startDate, endDate);
		if (CollectionUtils.isNotEmpty(orderEntries)) {
			// final VendorModel vendor =
			// acerChemVendorDao.getVendorByCode(vendorcode);
			// final String vendorName = vendor != null ? vendor.getName() : "";
			for (final OrderEntryModel oe : orderEntries) {
				final OrderProductReportData item = new OrderProductReportData();

				// final VendorModel vendor =
				// oe.getProduct().getAcerChemVendor();
				// if(vendor!=null){
				// final String vendorName =
				// StringUtils.isNotBlank(vendor.getName() ) ? vendor.getName()
				// : "";
				// item.setVendorName(vendorName);
				// }
				// modified vendor->customer company
				if (oe.getOrder().getUser() != null) {
					//如果该订单是客户所下，则显示公司，否则，显示用户名字
					String displayName = "";
					if (oe.getOrder().getUser() instanceof CustomerModel) {
						final CustomerModel customer = (CustomerModel) oe.getOrder().getUser();
						displayName = StringUtils.defaultString(customer.getCompanyName());
						
					}else{
						displayName = oe.getOrder().getUser().getName();
					}
					item.setVendorName(displayName);
				}
				item.setOrderCode(oe.getOrder().getCode());
				item.setFinishedTime(oe.getOrder().getOrderFinishedDate());
				item.setPlaceTime(oe.getOrder().getCreationtime());
				item.setProductName(oe.getProduct().getName());
				item.setProductQuantity(oe.getQuantity().intValue());

				report.add(item);
			}
			Collections.sort(report, new Comparator<OrderProductReportData>() {
				@Override
				public int compare(OrderProductReportData o1, OrderProductReportData o2) {
					return o2.getOrderCode().compareTo(o1.getOrderCode());
				}
			});
		}
		return report;
	}

	@Override
	public List<ProductPriceAnalysisData> getProductWithBaserealPrice(final String month) {
		final List<AcerchemProductPriceBean> listWithWeek = acerChemProductDao.getProductWithBaserealPrice(month);
		final List<ProductPriceAnalysisData> report = fillReport(listWithWeek);
		// 通过weeknum 用map分组
		// final Map<Integer, List<AcerchemProductPriceBean>> mapList = new
		// HashMap<Integer, List<AcerchemProductPriceBean>>();
		// if (CollectionUtils.isNotEmpty(listWithWeek)) {
		// for (final AcerchemProductPriceBean bean : listWithWeek) {
		// List<AcerchemProductPriceBean> tempList =
		// mapList.get(bean.getWeeknum());
		// if (tempList == null) {
		// tempList = new ArrayList<>();
		// tempList.add(bean);
		// mapList.put(bean.getWeeknum(), tempList);
		// } else {
		// tempList.add(bean);
		// }
		// }
		//
		// //遍历分组，进行排序，并合并同productcode项
		// final Map<Integer, List<AcerchemProductPriceBean>> mapcompList = new
		// HashMap<Integer, List<AcerchemProductPriceBean>>();
		// for(final int weekNum : mapList.keySet()){
		// final List<AcerchemProductPriceBean> tempList = mapList.get(weekNum);
		//
		// tempList.sort(compatatorbyCodeWeek);
		// //合并
		// final List<AcerchemProductPriceBean> list = new ArrayList<>();
		// String code = tempList.get(0).getProductCode();
		//
		// int count = 0;
		// long quanlity = 0;
		// double price = 0;
		//
		// for (int i = 0; i < tempList.size(); i++) {
		// AcerchemProductPriceBean bean = tempList.get(i);
		// if (code.equals(bean.getProductCode())) {
		// count++;
		// quanlity += bean.getSaleQuantity();
		// price = CommonConvertTools.addDouble(price,
		// bean.getBaseRealPrice()).doubleValue();
		// }else{
		// code = bean.getProductCode();
		//
		// i--;
		// bean = tempList.get(i);
		// bean.setBaseRealPrice(Double.valueOf(price / count));
		// bean.setSaleQuantity(quanlity);
		// list.add(bean);
		//
		// count = 0;
		// quanlity = 0;
		// price = 0;
		// }
		// }
		// //处理tmplist最后项
		// final AcerchemProductPriceBean last =
		// tempList.get(tempList.size()-1);
		//
		// last.setBaseRealPrice(Double.valueOf(price / count));
		// last.setSaleQuantity(quanlity);
		// list.add(last);
		//
		// mapcompList.put(weekNum, list);
		// }
		//
		// final List<AcerchemProductPriceBean> comboList = new ArrayList<>();
		// for(final int weekNum : mapcompList.keySet()){
		// final List<AcerchemProductPriceBean> tempList = mapList.get(weekNum);
		// comboList.addAll(tempList);
		// }

		// 填充最后报表
		// report = fillReport(comboList);
		// }
		// if (CollectionUtils.isNotEmpty(listWithWeek)) {
		// if (listWithWeek.size() > 0) {
		// // 按code和weeknum组合计算
		// listWithWeek.sort(compatatorbyWeekCode);
		// String code = listWithWeek.get(0).getProductCode();
		// int week = listWithWeek.get(0).getWeeknum();
		//
		// int count = 0;
		// long quanlity = 0;
		// double price = 0;
		// final List<AcerchemProductPriceBean> comboList = new ArrayList<>();
		// // CommonConvertTools
		//
		// for (int i = 0; i < listWithWeek.size(); i++) {
		//
		// final AcerchemProductPriceBean bean = listWithWeek.get(i);
		//
		// LOG.debug(">>>>>" + bean.getProductCode() + "-" + bean.getWeeknum() +
		// ">>>>>>>>>>>");
		// if (week == bean.getWeeknum() && code.equals(bean.getProductCode()))
		// {
		// count++;
		// quanlity += bean.getSaleQuantity().longValue();
		// price = CommonConvertTools.addDouble(price,
		// bean.getBaseRealPrice()).doubleValue();
		//
		// } else {
		// code = bean.getProductCode();
		// week = bean.getWeeknum();
		//
		// i--;
		// final AcerchemProductPriceBean pre = listWithWeek.get(i);
		// final AcerchemProductPriceBean combo = new
		// AcerchemProductPriceBean();
		//
		// combo.setProductCode(pre.getProductCode());
		// combo.setProductName(pre.getProductName());
		// combo.setWeeknum(pre.getWeeknum());
		//
		// combo.setBaseRealPrice(Double.valueOf(price / count));
		// combo.setSaleQuantity(Long.valueOf(quanlity));
		// comboList.add(combo);
		//
		// count = 0;
		// quanlity = 0;
		// price = 0;
		//
		// }
		// }
		// // 处理最后组合
		//
		// final AcerchemProductPriceBean last =
		// listWithWeek.get(listWithWeek.size() - 1);
		//
		// final AcerchemProductPriceBean combolast = new
		// AcerchemProductPriceBean();
		//
		// combolast.setProductCode(last.getProductCode());
		// combolast.setProductName(last.getProductName());
		// combolast.setWeeknum(last.getWeeknum());
		//
		// combolast.setBaseRealPrice(Double.valueOf(price / count));
		// combolast.setSaleQuantity(Long.valueOf(quanlity));
		// comboList.add(combolast);
		//
		//
		//
		//
		// }
		// }
		return report;
	}


	@Override
	public List<ProductPriceAnalysisData> getProductWithBaserealPrice(final String month,final String vendorCode){
		final List<AcerchemProductPriceBean> listWithWeek = acerChemProductDao.getProductWithBaserealPrice(month,vendorCode);
		final List<ProductPriceAnalysisData> report = fillReport(listWithWeek);
		return report;
	}

	private List<ProductPriceAnalysisData> fillReport(final List<AcerchemProductPriceBean> list) {
		final List<ProductPriceAnalysisData> report = new ArrayList<>();
		if (CollectionUtils.isNotEmpty(list) && list.size() > 0) {

			list.sort(compatatorbyCodeWeek);
			String codeReport = list.get(0).getProductCode();
			long sumQuantity = 0;
			double sumPrice = 0;
			double one = 0;
			double two = 0;
			double three = 0;
			double four = 0;
			double five = 0;
			double six = 0;
			int xcount = 0;
			long n1 = 0;
			long n2 = 0;
			long n3 = 0;
			long n4 = 0;
			long n5 = 0;
			long n6 = 0;

			for (int i = 0; i < list.size(); i++) {
				final AcerchemProductPriceBean bean = list.get(i);
				if (codeReport.equals(bean.getProductCode())) {
					final double r = bean.getBaseRealPrice() == null ? 0 : bean.getBaseRealPrice();
					sumPrice += r;
					if (bean.getWeeknum() == 1) {

						one = r;
						xcount = 1;
					} else if (bean.getWeeknum() == 2) {
						two = r;
						xcount = 2;
					} else if (bean.getWeeknum() == 3) {

						three = r ;
						xcount = 3;
					} else if (bean.getWeeknum() == 4) {

						four = r ;
						xcount = 4;
					} else if (bean.getWeeknum() == 5) {

						five = r ;
						xcount = 5;

					} else {

						six = r;
						xcount = 6;
					}

				} else {
					codeReport = bean.getProductCode();

					i--;
					final AcerchemProductPriceBean pre = list.get(i);
					final ProductPriceAnalysisData item = new ProductPriceAnalysisData();
					item.setProductCode(pre.getProductCode());
					item.setProductName(pre.getProductName());
					item.setSalesQuantity(sumQuantity);
					item.setMaxWeek(xcount);

					item.setAveragePrice(sumPrice / xcount);
					item.setFirstWeekPrice(one);
					item.setSecondWeekPrice(two);
					item.setThirdWeekPrice(three);
					item.setFouthWeekPrice(four);
					item.setFifthWeekPrice(five);
					item.setSixthWeekPrice(six);

					report.add(item);
					sumQuantity = 0;
					sumPrice = 0;
					one = 0;
					two = 0;
					three = 0;
					four = 0;
					five = 0;
					six = 0;
					xcount = 0;
					n1 = 0;
					n2 = 0;
					n3 = 0;
					n4 = 0;
					n5 = 0;
					n6 = 0;
				}
			}

			// 处理最后项
			final AcerchemProductPriceBean lastReport = list.get(list.size() - 1);
			final ProductPriceAnalysisData item = new ProductPriceAnalysisData();

			item.setProductCode(lastReport.getProductCode());
			item.setProductName(lastReport.getProductName());
			item.setSalesQuantity(Long.valueOf(sumQuantity));
			item.setMaxWeek(xcount);

			item.setAveragePrice(sumPrice / xcount);

			item.setFirstWeekPrice(one);
			item.setSecondWeekPrice(two);
			item.setThirdWeekPrice(three);
			item.setFouthWeekPrice(four);
			item.setFifthWeekPrice(five);
			item.setSixthWeekPrice(six);

			report.add(item);

		}

		return report;
	}

	private static Comparator<AcerchemProductPriceBean> compatatorbyWeekCode = new Comparator<AcerchemProductPriceBean>() {

		@Override
		public int compare(final AcerchemProductPriceBean o1, final AcerchemProductPriceBean o2) {
			// TODO Auto-generated method stub
			int n = o1.getWeeknum() - o2.getWeeknum();
			if (n == 0) {
				n = o1.getProductCode().compareTo(o2.getProductCode());
			}

			return n;
		}

	};

	private static Comparator<AcerchemProductPriceBean> compatatorbyCodeWeek = new Comparator<AcerchemProductPriceBean>() {

		@Override
		public int compare(final AcerchemProductPriceBean o1, final AcerchemProductPriceBean o2) {
			// TODO Auto-generated method stub
			int n = o1.getProductCode().trim().compareTo(o2.getProductCode().trim());

			if (n == 0) {
				n = o1.getWeeknum() - o2.getWeeknum();
			}

			return n;
		}

	};

	@Override
	public List<ProductSalesRecordData> getProductSalesForReport(final String month, final String categoryCode,
			final String area, final String countryCode,final String productName,final String productCode,final String employeeName) {
		final List<AcerchemProductBuyerBean> list = acerChemProductDao.getProductSalesForReport(month, categoryCode,
				area, countryCode,productName,productCode,employeeName);

		final List<ProductSalesRecordData> report = new ArrayList<>();
		if (CollectionUtils.isNotEmpty(list)) {
			if (list.size() > 0) {
				list.sort(compatatorForSales);
				String pCode = list.get(0).getProductCode();
				String customer = list.get(0).getBuyer();

				long quantity = 0;
				for (int i = 0; i < list.size(); i++) {
					final AcerchemProductBuyerBean bean = list.get(i);
					if (pCode.equals(bean.getProductCode()) && customer.equals(bean.getBuyer())) {
						quantity += bean.getBuyQuantity().longValue();

					} else {
						pCode = bean.getProductCode();
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
				final AcerchemProductBuyerBean last = list.get(list.size() - 1);
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

	@Override
	public List<ProductSalesRecordData> getProductSalesForReport(final String month, final String categoryCode,final String area,
																 final String countryCode,final String productName,final String productCode,
																 final String employeeName,final String vendorCode) {
		final List<AcerchemProductBuyerBean> list = acerChemProductDao.getProductSalesForReport(month, categoryCode,
				area, countryCode,productName,productCode,employeeName,vendorCode);

		final List<ProductSalesRecordData> report = new ArrayList<>();
		if (CollectionUtils.isNotEmpty(list)) {
			if (list.size() > 0) {
				list.sort(compatatorForSales);
				String pCode = list.get(0).getProductCode();
				String customer = list.get(0).getBuyer();

				long quantity = 0;
				for (int i = 0; i < list.size(); i++) {
					final AcerchemProductBuyerBean bean = list.get(i);
					if (pCode.equals(bean.getProductCode()) && customer.equals(bean.getBuyer())) {
						quantity += bean.getBuyQuantity().longValue();

					} else {
						pCode = bean.getProductCode();
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
				final AcerchemProductBuyerBean last = list.get(list.size() - 1);
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

			int n = o1.getProductCode().compareTo(o2.getProductCode());
			if (n == 0) {
				n = o1.getBuyer().compareTo(o2.getBuyer());
			}

			return n;
		}

	};

}
