package com.acerchem.core.dao.impl;

import com.acerchem.core.dao.AcerchemCustomerDao;
import com.acerchem.core.service.AcerchemCustomerService;
import de.hybris.platform.commerceservices.search.flexiblesearch.PagedFlexibleSearchService;
import de.hybris.platform.commerceservices.search.pagedata.PageableData;
import de.hybris.platform.commerceservices.search.pagedata.SearchPageData;
import de.hybris.platform.core.model.product.ProductModel;
import de.hybris.platform.ordersplitting.model.WarehouseModel;
import de.hybris.platform.servicelayer.internal.dao.AbstractItemDao;
import de.hybris.platform.servicelayer.search.SearchResult;
import de.hybris.platform.store.BaseStoreModel;
import de.hybris.platform.storelocator.model.PointOfServiceModel;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Required;

import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Created by Jacob.Ji on 2018/3/21.
 */
public class DefaultAcerchemCustomerDao extends AbstractItemDao implements AcerchemCustomerDao {

    private final String GET_ALL_POINNTOFSERVICE = "select {PK} from {pointOfService} where {baseStore} = ?baseStore and {type}=?type";

    private final String GET_ALL_WAREHOUSE_BY_PRODUCT = "select {PK} from {pointOfService} where {baseStore} = ?baseStore and {type}=?type";

    private PagedFlexibleSearchService pagedFlexibleSearchService;

    @Override
    public SearchPageData<PointOfServiceModel> getAllPos(Map<String, Object> paramMap, PageableData pageableData) {

        return pagedFlexibleSearchService.search(GET_ALL_POINNTOFSERVICE,paramMap,pageableData);
    }

    @Override
    public List<WarehouseModel> getAllWarehouses(ProductModel productModel, BaseStoreModel baseStoreModel) {
        WarehouseModel warehouseModel =new WarehouseModel();

        return null;
    }

    @Required
    public void setPagedFlexibleSearchService(PagedFlexibleSearchService pagedFlexibleSearchService) {
        this.pagedFlexibleSearchService = pagedFlexibleSearchService;
    }
}
