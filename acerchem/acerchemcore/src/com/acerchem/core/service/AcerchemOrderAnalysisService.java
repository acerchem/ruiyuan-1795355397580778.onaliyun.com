package com.acerchem.core.service;

import java.util.Date;
import java.util.List;

import de.hybris.platform.commercefacades.customer.data.CustomerBillAnalysisData;
import de.hybris.platform.commercefacades.customer.data.CustomerSalesAnalysisData;
import de.hybris.platform.commercefacades.order.data.MonthlySalesAnalysis;
import de.hybris.platform.commercefacades.order.data.SalesByEmployeeReportData;

public interface AcerchemOrderAnalysisService {
	/**
	 * 按照年度和区域分析每月的销售额度
	 * @param year
	 * @param area
	 * @return
	 */
	List<MonthlySalesAnalysis> getMonthlySalesAnalysis(String year,String area);
	
	/**
	 * 按照年度，分析业务员每月销售情况
	 * @param year
	 * @return
	 */
	public List<SalesByEmployeeReportData> getEmployeeSales(final String year);
	
	List<CustomerSalesAnalysisData> getCustomerSalesAnalysis(String area,String customerName,double amount,Date startDate,Date endDate);
	List<CustomerBillAnalysisData> getCustomerBillAnalysis(Date startDate,Date endDate);
}
