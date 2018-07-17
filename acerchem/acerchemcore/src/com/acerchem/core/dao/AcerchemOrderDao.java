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
	List<OrderDetailsReportData> getOrderDetails(String month,String area,String countryCode,String userName,String orderCode);
	List<MonthlySalesAnalysis> getMonthlySalesAnalysis(String year,String area);
	List<SalesByEmployeeReportData> getEmployeeSales(String year);
	
	List<CustomerSalesAnalysisData> getCustomerSalesAnalysis(String area,String customerName,double amount);
	List<CustomerBillAnalysisData> getCustomerBillAnalysis(Date startDate,Date endDate);
	
}
