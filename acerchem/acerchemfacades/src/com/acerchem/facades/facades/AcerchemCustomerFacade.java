package com.acerchem.facades.facades;

import com.acerchem.facades.product.data.CountryToWarehouseDataList;
import de.hybris.platform.commercefacades.user.data.CustomerData;

/**
 * Created by Jacob.Ji on 2018/3/20.
 */

public interface AcerchemCustomerFacade {
    CustomerData getCurrentCustomer();

    CountryToWarehouseDataList getCountryAndWarehouse();
}
