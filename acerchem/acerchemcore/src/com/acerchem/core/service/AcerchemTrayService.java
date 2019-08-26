package com.acerchem.core.service;

import com.acerchem.core.model.CountryTrayFareConfModel;

import de.hybris.platform.core.model.c2l.CountryModel;
import de.hybris.platform.core.model.c2l.RegionModel;

/**
 * Created by Jacob.Ji on 2018/4/8.
 */
public interface AcerchemTrayService {

    CountryTrayFareConfModel getPriceByCountryAndTray(CountryModel country, String postcode,int amount);
}
