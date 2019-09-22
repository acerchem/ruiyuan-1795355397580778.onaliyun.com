package com.acerchem.core.dao.impl;

import com.acerchem.core.dao.AcerchemBuPriceDao;
import com.acerchem.core.model.BUPriceAdditionalConfModel;
import de.hybris.platform.ordersplitting.model.StockLevelModel;
import de.hybris.platform.servicelayer.search.FlexibleSearchQuery;
import de.hybris.platform.servicelayer.search.FlexibleSearchService;
import de.hybris.platform.servicelayer.search.SearchResult;
import org.apache.commons.collections.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;


public class AcerchemBuPriceDaoImpl implements AcerchemBuPriceDao
{
	@Resource
	private FlexibleSearchService flexibleSearchService;

	public BUPriceAdditionalConfModel getBuPriceModelByCode(String code){
		final String SQL = "select {pk} from {BUPriceAdditionalConf} where {buKey} IN (?code)";

		final FlexibleSearchQuery query = new FlexibleSearchQuery(SQL);
		query.addQueryParameter("code", code);

		final SearchResult<BUPriceAdditionalConfModel> result = flexibleSearchService.search(query);
		return CollectionUtils.isNotEmpty(result.getResult()) ? result.getResult().get(0) : null;
	}
}
