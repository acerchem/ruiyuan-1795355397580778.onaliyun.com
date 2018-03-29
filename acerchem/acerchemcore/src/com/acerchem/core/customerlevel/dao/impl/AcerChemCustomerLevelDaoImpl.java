package com.acerchem.core.customerlevel.dao.impl;

import java.util.List;

import javax.annotation.Resource;

import com.acerchem.core.customerlevel.dao.AcerChemCustomerLevelDao;
import com.acerchem.core.model.CustomerLevelModel;

import de.hybris.platform.servicelayer.search.FlexibleSearchQuery;
import de.hybris.platform.servicelayer.search.FlexibleSearchService;
import de.hybris.platform.servicelayer.search.SearchResult;

public class AcerChemCustomerLevelDaoImpl implements AcerChemCustomerLevelDao {

	@Resource
	private FlexibleSearchService flexibleSearchService;
	@Override
	public List<CustomerLevelModel> getALLCustomerLevel() {
		String queryStr = "SELECT {pk} "
				+ " FROM {"+CustomerLevelModel._TYPECODE+"}";
		FlexibleSearchQuery fsq = new FlexibleSearchQuery(queryStr);
		SearchResult<CustomerLevelModel> result = flexibleSearchService.search(fsq);
		List<CustomerLevelModel> levels = result.getResult();
		return levels;
	}

}
