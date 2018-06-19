package com.acerchem.core.service;

import java.util.List;

import de.hybris.platform.commercefacades.order.data.MonthlySalesAnalysis;

public interface AcerchemOrderAnalysisService {
	List<MonthlySalesAnalysis> getMonthlySalesAnalysis(String year,String area);
}
