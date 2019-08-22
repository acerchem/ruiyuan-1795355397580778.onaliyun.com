package com.acerchem.core.dao;

import java.util.Date;
import java.util.List;
import java.util.Set;

import de.hybris.platform.commercefacades.customer.data.CustomerBillAnalysisData;
import de.hybris.platform.commercefacades.customer.data.CustomerSalesAnalysisData;
import de.hybris.platform.commercefacades.order.data.MonthlySalesAnalysis;
import de.hybris.platform.commercefacades.order.data.OrderDetailsReportData;
import de.hybris.platform.commercefacades.order.data.SalesByEmployeeReportData;

public interface AcerchemOrderDao {
	
	Set<String> getAllAreas();
	List<OrderDetailsReportData> getOrderDetails(String month,String area,String countryCode,String customerCompanyname,String orderCode);;
	List<OrderDetailsReportData> getOrderDetails(String month,String area,String countryCode,String customerCompanyname,String orderCode,
												 String vendorCode,String productName,String productCode,String productCharacteristic,
												 String warehouseCode,String employeeName,String deliveryModeCode);
	List<MonthlySalesAnalysis> getMonthlySalesAnalysis(String year,String area);
	List<SalesByEmployeeReportData> getEmployeeSales(String year,String employeeName);

	List<CustomerSalesAnalysisData> getCustomerSalesAnalysis(String area,String customerName,double amount,Date startDate,Date endDate);
	List<CustomerBillAnalysisData> getCustomerBillAnalysis(Date startDate,Date endDate,String customerName,String employeeName,String orderCode);
	
}
