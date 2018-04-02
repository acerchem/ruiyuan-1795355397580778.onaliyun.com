package com.acerchem.facades.facades;

import com.acerchem.facades.product.data.StockDataList;
import de.hybris.platform.commercefacades.user.data.CountryData;

/**
 * Created by Jacob.Ji on 2018/3/20.
 */

public interface AcerchemCheckoutFacade {

    void validateCartAddress(CountryData countryData) throws AcerchemOrderException;
}

