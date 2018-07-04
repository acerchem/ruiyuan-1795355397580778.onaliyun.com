package com.acerchem.core.dao;

import java.util.List;
import java.util.Set;

import de.hybris.platform.commercefacades.order.data.MonthlySalesAnalysis;
import de.hybris.platform.commercefacades.order.data.OrderDetailsReportData;
import de.hybris.platform.commercefacades.order.data.SalesByEmployeeReportData;

public interface AcerchemOrderDao {
	
	Set<String> getAllAreas();
	List<OrderDetailsReportData> getOrderDetails(String month,String area,String countryCode,String userName,String orderCode,Integer pageNumber);
	List<MonthlySalesAnalysis> getMonthlySalesAnalysis(String year,String area);
	List<SalesByEmployeeReportData> getEmployeeSales(String year);
}
