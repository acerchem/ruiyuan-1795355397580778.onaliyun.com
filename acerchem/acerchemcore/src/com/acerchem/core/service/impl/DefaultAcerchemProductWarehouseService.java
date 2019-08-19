package com.acerchem.core.service.impl;

import com.acerchem.core.dao.AcerchemProductWarehouseDao;
import com.acerchem.core.service.AcerchemProductWarehouseService;
import de.hybris.platform.core.model.product.ProductModel;
import de.hybris.platform.ordersplitting.model.WarehouseModel;
import de.hybris.platform.servicelayer.search.FlexibleSearchQuery;
import de.hybris.platform.servicelayer.search.FlexibleSearchService;
import de.hybris.platform.servicelayer.search.SearchResult;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by Jacob.Ji on 2018/3/21.
 */
public class DefaultAcerchemProductWarehouseService implements AcerchemProductWarehouseService {

    private AcerchemProductWarehouseDao productWarehouseDao;

    public AcerchemProductWarehouseDao getProductWarehouseDao() {
        return productWarehouseDao;
    }

    public void setProductWarehouseDao(AcerchemProductWarehouseDao productWarehouseDao) {
        this.productWarehouseDao=productWarehouseDao;
    }

    @Override
    public List<WarehouseModel> getProductWarehouse(ProductModel product) {
        return productWarehouseDao.getProductWarehouseByProductCode(product.getCode());
    }

    @Override
    public List<WarehouseModel> getAllProductWarehouse() {
        return productWarehouseDao.getAllProductWarehouse();
    }
}
