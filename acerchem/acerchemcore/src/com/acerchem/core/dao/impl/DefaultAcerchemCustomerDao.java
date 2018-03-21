package com.acerchem.core.dao.impl;

import com.acerchem.core.dao.AcerchemCustomerDao;
import com.acerchem.core.model.CountryToWarehouseModel;
import com.acerchem.core.service.AcerchemCustomerService;
import de.hybris.platform.servicelayer.internal.dao.AbstractItemDao;
import de.hybris.platform.servicelayer.search.SearchResult;
import org.apache.commons.collections.CollectionUtils;

import java.util.List;
import java.util.Objects;

/**
 * Created by Jacob.Ji on 2018/3/21.
 */
public class DefaultAcerchemCustomerDao extends AbstractItemDao implements AcerchemCustomerDao {

    private static String GET_COUNTRY_AND_WAREHOUSE = "select {PK} from {CountryToWarehouse}";
    @Override
    public List<CountryToWarehouseModel> getCountryAndWarehouse() {

        SearchResult<CountryToWarehouseModel> result= getFlexibleSearchService().search(GET_COUNTRY_AND_WAREHOUSE);
        if (Objects.nonNull(result)&& CollectionUtils.isNotEmpty(result.getResult())){
            return result.getResult();
        }
        return null;
    }
}
