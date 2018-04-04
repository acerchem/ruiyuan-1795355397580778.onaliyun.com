package com.acerchem.core.dao.impl;

import com.acerchem.core.dao.AcerchemCountryTrayFareDao;
import com.acerchem.core.model.CountryTrayFareConfModel;
import de.hybris.platform.core.model.c2l.CountryModel;
import de.hybris.platform.servicelayer.internal.dao.AbstractItemDao;

/**
 * Created by Jacob.Ji on 2018/3/21.
 */
public class DefaultAcerchemCountryTrayFareDao extends AbstractItemDao implements AcerchemCountryTrayFareDao {

    private final String GET_COUNTRY_TRAY_FARE_CONF = "select {PK} from {countryFare} where {country}=?country and {trayAmount} =?trayAmount";

    @Override
    public CountryTrayFareConfModel getCouTrayFareConf(int quantity, CountryModel countryModel) {
        return null;
    }
}
