package com.acerchem.core.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.acerchem.core.dao.AcerchemProductWarehouseDao;
import de.hybris.platform.ordersplitting.model.WarehouseModel;
import de.hybris.platform.servicelayer.search.FlexibleSearchQuery;
import de.hybris.platform.servicelayer.search.FlexibleSearchService;
import de.hybris.platform.servicelayer.search.SearchResult;

public class DefaultAcerchemProductWarehouseDao implements AcerchemProductWarehouseDao {

	private FlexibleSearchService flexibleSearchService;

	public FlexibleSearchService getFlexibleSearchService() {
		return flexibleSearchService;
	}

	public void setFlexibleSearchService(FlexibleSearchService flexibleSearchService) {
		this.flexibleSearchService=flexibleSearchService;
	}

	@Override
	public List<WarehouseModel> getProductWarehouseByProductCode(String productCode) {
		String BASE_QUERY = "SELECT {w:PK} FROM {Warehouse as w} WHERE exists ({{SELECT 'X' FROM {StockLevel AS sl} WHERE {w:PK} = {sl:warehouse} AND {sl:productcode} = ?productcode}})";
		Map<String, String> params = new HashMap<>(1);
		params.put("productcode", productCode);
		FlexibleSearchQuery searchQuery = new FlexibleSearchQuery(BASE_QUERY);
		searchQuery.addQueryParameters(params);
		SearchResult<WarehouseModel> searchResult=flexibleSearchService.search(searchQuery);
		return searchResult.getResult();
	}
}
