package com.acerchem.core.dao.impl;

import com.acerchem.core.dao.AcerchemTrayDao;
import com.acerchem.core.model.CountryTrayFareConfModel;
import de.hybris.platform.core.model.c2l.CountryModel;
import de.hybris.platform.core.model.c2l.RegionModel;
import de.hybris.platform.servicelayer.internal.dao.AbstractItemDao;
import de.hybris.platform.servicelayer.search.SearchResult;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Jacob.Ji on 2018/3/21.
 */
public class DefaultAcerchemTrayDao extends AbstractItemDao implements AcerchemTrayDao {

    private final String GET_COUNTRY_TRAY_FARE_CONF = "select {PK} from {CountryTrayFareConf} where {region}=?region and {trayAmount} =?trayAmount";
    
    private final String GET_COUNTRY_MAX = "select {PK} from {CountryTrayFareConf} where {region}=?region order by {trayAmount} desc";

    @Override
    public CountryTrayFareConfModel getCouTrayFareConf(RegionModel region, int trayAmount) {
    	
    	final Map<String, Object> params = new HashMap<String, Object>();
        
    	final StringBuilder builderMax = new StringBuilder(GET_COUNTRY_MAX);
        params.put("region",region.getIsocode());
    	final SearchResult<CountryTrayFareConfModel> resultMax = getFlexibleSearchService().search(builderMax.toString(),params);
    	
        final StringBuilder builder = new StringBuilder(GET_COUNTRY_TRAY_FARE_CONF);
        params.put("trayAmount",trayAmount);
        final SearchResult<CountryTrayFareConfModel> result = getFlexibleSearchService().search(builder.toString(),params);
        if (result!=null && CollectionUtils.isNotEmpty(result.getResult())){
            return result.getResult().get(0);
        }else if(resultMax!=null && CollectionUtils.isNotEmpty(resultMax.getResult())){
        	return resultMax.getResult().get(0);
        }
        return null;
    }
}
