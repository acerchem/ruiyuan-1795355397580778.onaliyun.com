package com.acerchem.core.dao.impl;

import com.acerchem.core.dao.AcerchemTrayDao;
import com.acerchem.core.model.CountryTrayFareConfModel;
import de.hybris.platform.core.model.c2l.CountryModel;
import de.hybris.platform.servicelayer.internal.dao.AbstractItemDao;
import de.hybris.platform.servicelayer.search.SearchResult;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Jacob.Ji on 2018/3/21.
 */
public class DefaultAcerchemTrayDao extends AbstractItemDao implements AcerchemTrayDao {

    private final String GET_COUNTRY_TRAY_FARE_CONF = "select {PK} from {countryFare} where {country}=?country and {trayAmount} =?trayAmount";

    @Override
    public CountryTrayFareConfModel getCouTrayFareConf(CountryModel country, int trayAmount) {

        final Map<String, Object> params = new HashMap<String, Object>();
        final StringBuilder builder = new StringBuilder(GET_COUNTRY_TRAY_FARE_CONF);
        params.put("country",country);
        params.put("trayAmount",trayAmount);
        final SearchResult<CountryTrayFareConfModel> result = getFlexibleSearchService().search(builder.toString(),params);
        if (result!=null && result.getResult()!=null){
            return result.getResult().get(0);
        }
        return null;
    }
}
