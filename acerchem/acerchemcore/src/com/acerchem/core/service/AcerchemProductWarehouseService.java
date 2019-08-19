package com.acerchem.core.service;

import de.hybris.platform.core.model.product.ProductModel;
import de.hybris.platform.ordersplitting.model.WarehouseModel;

import java.util.List;

/**
 * Created by Jacob.Ji on 2018/3/21.
 */
public interface AcerchemProductWarehouseService {

    List<WarehouseModel> getProductWarehouse(ProductModel product);

    List<WarehouseModel> getAllProductWarehouse();
}
