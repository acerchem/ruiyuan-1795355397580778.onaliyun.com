package com.acerchem.core.dao;


import de.hybris.platform.commerceservices.search.pagedata.PageableData;
import de.hybris.platform.commerceservices.search.pagedata.SearchPageData;
import de.hybris.platform.store.BaseStoreModel;
import de.hybris.platform.storelocator.model.PointOfServiceModel;

import java.util.List;
import java.util.Map;

/**
 * Created by Jacob.Ji on 2018/3/21.
 */
public interface AcerchemCustomerDao {
    SearchPageData<PointOfServiceModel> getAllPos(Map<String, Object> paramMap, final PageableData pageableData);
}
