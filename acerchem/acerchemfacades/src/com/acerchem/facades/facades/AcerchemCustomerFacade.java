package com.acerchem.facades.facades;

import com.acerchem.facades.product.data.StoreOfProductData;
import de.hybris.platform.commercefacades.user.data.CustomerData;

import java.util.List;

/**
 * Created by Jacob.Ji on 2018/3/20.
 */

public interface AcerchemCustomerFacade {
    CustomerData getCurrentCustomer();

    List<StoreOfProductData> getAllPos(String productCode);
}

