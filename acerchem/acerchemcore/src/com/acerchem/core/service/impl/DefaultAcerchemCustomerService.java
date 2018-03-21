package com.acerchem.core.service.impl;

import com.acerchem.core.dao.AcerchemCustomerDao;
import com.acerchem.core.model.CountryToWarehouseModel;
import com.acerchem.core.service.AcerchemCustomerService;
import org.springframework.beans.factory.annotation.Required;

import java.util.List;

/**
 * Created by Jacob.Ji on 2018/3/21.
 */
public class DefaultAcerchemCustomerService implements AcerchemCustomerService {

    private AcerchemCustomerDao acerchemCustomerDao;

    @Override
    public List<CountryToWarehouseModel> getCountryAndWarehouse() {
        return acerchemCustomerDao.getCountryAndWarehouse();
    }

    @Required
    public void setAcerchemCustomerDao(AcerchemCustomerDao acerchemCustomerDao) {
        this.acerchemCustomerDao = acerchemCustomerDao;
    }
}
