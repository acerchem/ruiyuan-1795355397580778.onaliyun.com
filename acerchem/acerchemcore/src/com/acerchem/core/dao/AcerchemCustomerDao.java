package com.acerchem.core.dao;

import com.acerchem.core.model.CountryToWarehouseModel;

import java.util.List;

/**
 * Created by Jacob.Ji on 2018/3/21.
 */
public interface AcerchemCustomerDao {
    List<CountryToWarehouseModel> getCountryAndWarehouse();
}
