package com.acerchem.core.dao;

import java.util.List;

import de.hybris.platform.commercefacades.order.data.MonthlySalesAnalysis;
import de.hybris.platform.commercefacades.order.data.OrderDetailsReportData;

public interface AcerchemOrderDao {
	
	List<OrderDetailsReportData> getOrderDetails(Integer month,String area,String countryCode,String userName,String orderCode,Integer pageNumber);
	List<MonthlySalesAnalysis> getMonthlySalesAnalysis(Integer year,String area);
}
