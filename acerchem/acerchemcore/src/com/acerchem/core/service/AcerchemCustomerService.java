package com.acerchem.core.service;

import de.hybris.platform.commerceservices.search.pagedata.PageableData;
import de.hybris.platform.commerceservices.search.pagedata.SearchPageData;
import de.hybris.platform.store.BaseStoreModel;
import de.hybris.platform.storelocator.model.PointOfServiceModel;

/**
 * Created by Jacob.Ji on 2018/3/21.
 */
public interface AcerchemCustomerService {

    SearchPageData<PointOfServiceModel> getAllPos(BaseStoreModel baseStore, PageableData pageableData);
}
