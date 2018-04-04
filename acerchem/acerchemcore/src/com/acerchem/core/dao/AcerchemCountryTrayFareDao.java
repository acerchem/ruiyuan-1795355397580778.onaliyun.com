package com.acerchem.core.dao;

import com.acerchem.core.model.CountryTrayFareConfModel;
import de.hybris.platform.core.model.c2l.CountryModel;

/**
 * Created by Jacob.Ji on 2018/4/2.
 */
public interface AcerchemCountryTrayFareDao {

    CountryTrayFareConfModel getCouTrayFareConf(int quantity, CountryModel countryModel);
}
