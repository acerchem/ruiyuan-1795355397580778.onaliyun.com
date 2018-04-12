package com.acerchem.core.service.impl;

import com.acerchem.core.dao.AcerchemTrayDao;
import com.acerchem.core.model.CountryTrayFareConfModel;
import com.acerchem.core.service.AcerchemTrayService;
import de.hybris.platform.core.model.c2l.CountryModel;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by Jacob.Ji on 2018/4/8.
 */
@Service("acerchemTrayService")
public class DefaultAcerchemTrayService implements AcerchemTrayService {

    @Resource
    private AcerchemTrayDao acerchemTrayDao;

    @Override
    public CountryTrayFareConfModel getPriceByCountryAndTray(CountryModel countryModel, int quantity) {

        CountryTrayFareConfModel countryTrayFareConf = acerchemTrayDao.getCouTrayFareConf(countryModel,quantity);

        return countryTrayFareConf;
    }
}
