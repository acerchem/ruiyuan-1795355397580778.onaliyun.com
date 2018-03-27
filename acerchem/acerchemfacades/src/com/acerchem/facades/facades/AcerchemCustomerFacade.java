package com.acerchem.facades.facades;

import com.acerchem.facades.product.data.CountryToWarehouseData;
import de.hybris.platform.commercefacades.user.data.CustomerData;
import de.hybris.platform.commerceservices.search.pagedata.PageableData;
import de.hybris.platform.commerceservices.search.pagedata.SearchPageData;

/**
 * Created by Jacob.Ji on 2018/3/20.
 */

public interface AcerchemCustomerFacade {
    CustomerData getCurrentCustomer();

    SearchPageData<CountryToWarehouseData> getAllPointOfServices(PageableData pageableData);
}

