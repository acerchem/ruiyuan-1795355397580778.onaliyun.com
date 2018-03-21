package com.acerchem.core.service;

import com.acerchem.core.model.CountryToWarehouseModel;

import java.util.List;

/**
 * Created by Jacob.Ji on 2018/3/21.
 */
public interface AcerchemCustomerService {
    List<CountryToWarehouseModel> getCountryAndWarehouse();
}
