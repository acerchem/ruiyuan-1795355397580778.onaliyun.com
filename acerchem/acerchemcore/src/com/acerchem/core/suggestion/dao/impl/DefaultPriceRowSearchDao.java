package com.acerchem.core.suggestion.dao.impl;

import de.hybris.platform.europe1.model.PriceRowModel;
import de.hybris.platform.servicelayer.internal.dao.AbstractItemDao;
import de.hybris.platform.servicelayer.search.SearchResult;
import com.acerchem.core.suggestion.dao.PriceRowSearchDao;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Alice 
 */
public class DefaultPriceRowSearchDao extends AbstractItemDao implements PriceRowSearchDao
{
	@Override
	public List<PriceRowModel> findPriceRowBySameProductToPriceRow(final PriceRowModel priceRow)
	{
		final String REF_QUERY_PRODUCTROW_START = "SELECT PK "
				+ " FROM {"+PriceRowModel._TYPECODE+"}"
				+ " WHERE {"+PriceRowModel.PRODUCT+"} =?pro ";
		final Map<String, Object> params = new HashMap<String, Object>();
		final StringBuilder builder = new StringBuilder(REF_QUERY_PRODUCTROW_START);
		params.put("pro", priceRow.getProduct());
		final SearchResult<PriceRowModel> result = getFlexibleSearchService().search(builder.toString(),params);
		return result.getResult();
	}
}
