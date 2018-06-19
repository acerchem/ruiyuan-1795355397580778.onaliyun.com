package com.acerchem.core.service.impl;

import java.util.List;

import javax.annotation.Resource;

import com.acerchem.core.dao.AcerchemOrderDao;
import com.acerchem.core.service.AcerchemOrderAnalysisService;

import de.hybris.platform.commercefacades.order.data.MonthlySalesAnalysis;

public class AcerchemOrderAnalysisServiceImpl implements AcerchemOrderAnalysisService {

	@Resource
	private AcerchemOrderDao acerchemOrderDao;
	@Override
	public List<MonthlySalesAnalysis> getMonthlySalesAnalysis(final String year, final String area) {
		// TODO Auto-generated method stub
		return acerchemOrderDao.getMonthlySalesAnalysis(year, area);
	}

}
