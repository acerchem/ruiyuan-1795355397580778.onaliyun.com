package com.acerchem.core.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import com.acerchem.core.dao.AcerchemOrderDao;
import com.acerchem.core.service.AcerchemOrderAnalysisService;

import de.hybris.platform.commercefacades.customer.data.CustomerBillAnalysisData;
import de.hybris.platform.commercefacades.customer.data.CustomerSalesAnalysisData;
import de.hybris.platform.commercefacades.order.data.MonthlySalesAnalysis;
import de.hybris.platform.commercefacades.order.data.OrderDetailsReportData;
import de.hybris.platform.commercefacades.order.data.SalesByEmployeeReportData;

public class AcerchemOrderAnalysisServiceImpl implements AcerchemOrderAnalysisService {

	@Resource
	private AcerchemOrderDao acerchemOrderDao;
	
	
	
	@Override
	public List<MonthlySalesAnalysis> getMonthlySalesAnalysis(final String year, final String area) {
		// TODO Auto-generated method stub
		return acerchemOrderDao.getMonthlySalesAnalysis(year, area);
	}
	@Override
	public List<SalesByEmployeeReportData> getEmployeeSales(final String year) {
		// TODO Auto-generated method stub
		return acerchemOrderDao.getEmployeeSales(year);
	}
	@Override
	public List<CustomerSalesAnalysisData> getCustomerSalesAnalysis(final String area, final String customerName, final double amount,final Date startDate,final Date endDate) {
		// TODO Auto-generated method stub
		return acerchemOrderDao.getCustomerSalesAnalysis(area, customerName, amount,startDate,endDate);
	}
	@Override
	public List<CustomerBillAnalysisData> getCustomerBillAnalysis(final Date startDate, final Date endDate,String customerName,String employeeName) {
		// TODO Auto-generated method stub
		return acerchemOrderDao.getCustomerBillAnalysis(startDate, endDate,customerName,employeeName);
	}
	@Override
	public Set<String> getAllAreas() {
		// TODO Auto-generated method stub
		return acerchemOrderDao.getAllAreas();
	}
	@Override
	public List<OrderDetailsReportData> getOrderDetails(final String month, final String area, final String countryCode,
			final String customerCompanyname, final String orderCode) {
		// TODO Auto-generated method stub
		return acerchemOrderDao.getOrderDetails(month, area, countryCode, customerCompanyname, orderCode);
	}
	@Override
	public List<OrderDetailsReportData> getOrderDetails(final String month, final String area, final String countryCode,
														final String customerCompanyname, final String orderCode,
														final String vendorCode,final String productName,final String productCode,
														final String productCharacteristic,final String warehouseCode,
														final String employeeName,final String deliveryModeCode) {
		// TODO Auto-generated method stub
		return acerchemOrderDao.getOrderDetails(month, area, countryCode, customerCompanyname, orderCode,vendorCode,productName,
				productCode,productCharacteristic,warehouseCode,employeeName,deliveryModeCode);
	}

	
}
