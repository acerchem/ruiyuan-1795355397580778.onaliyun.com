package com.acerchem.core.service;

import java.util.List;

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
	
	
}
