package com.acerchem.core.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.hybris.platform.ordersplitting.model.StockLevelModel;
import de.hybris.platform.servicelayer.internal.dao.AbstractItemDao;
import de.hybris.platform.servicelayer.search.SearchResult;

public class DefaultAcerchemFutureStockDao extends  AbstractItemDao implements AcerchemFutureStockDao  {

	@Override
	public List<StockLevelModel> findAcerChemFutureStock(final String product_pk) {
		// TODO Auto-generated method stub
		
		final String REF_QUERY_STOCKLEVEL_START = "SELECT PK "
				+ " FROM {"+StockLevelModel._TYPECODE+"}"
				+ " WHERE {"+StockLevelModel.PRODUCTCODE+"} =?propk ";
		
//		  query.append("SELECT {").append("pk").append("} FROM {").append("StockLevel").append("} WHERE {")
//		   .append("productCode").append("} = ?").append("productCode");
		  
		final Map<String, Object> params = new HashMap<String, Object>();
		final StringBuilder builder = new StringBuilder(REF_QUERY_STOCKLEVEL_START);
		params.put("propk",product_pk);
		final SearchResult<StockLevelModel> result = getFlexibleSearchService().search(builder.toString(),params);
		return result.getResult();
	}
}
