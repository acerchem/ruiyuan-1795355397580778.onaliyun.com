package com.acerchem.core.service.impl;

import com.acerchem.core.dao.AcerchemCustomerDao;
import com.acerchem.core.service.AcerchemCustomerService;
import de.hybris.platform.basecommerce.enums.PointOfServiceTypeEnum;
import de.hybris.platform.commerceservices.search.pagedata.PageableData;
import de.hybris.platform.commerceservices.search.pagedata.SearchPageData;
import de.hybris.platform.store.BaseStoreModel;
import de.hybris.platform.storelocator.model.PointOfServiceModel;
import org.springframework.beans.factory.annotation.Required;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Jacob.Ji on 2018/3/21.
 */
public class DefaultAcerchemCustomerService implements AcerchemCustomerService {

    private AcerchemCustomerDao acerchemCustomerDao;

    @Override
    public SearchPageData<PointOfServiceModel> getAllPos(final BaseStoreModel baseStore, final PageableData pageableData) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("baseStore", baseStore);
        paramMap.put("type",PointOfServiceTypeEnum.POS);

        final SearchPageData<PointOfServiceModel> posResults = acerchemCustomerDao.getAllPos(paramMap, pageableData);

        return posResults;
    }

    @Required
    public void setAcerchemCustomerDao(AcerchemCustomerDao acerchemCustomerDao) {
        this.acerchemCustomerDao = acerchemCustomerDao;
    }
}
