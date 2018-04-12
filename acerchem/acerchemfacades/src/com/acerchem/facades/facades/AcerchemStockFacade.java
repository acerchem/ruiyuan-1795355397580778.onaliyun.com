package com.acerchem.facades.facades;

import com.acerchem.facades.product.data.StockDataList;
import de.hybris.platform.commercefacades.user.data.CustomerData;
import de.hybris.platform.commerceservices.search.pagedata.PageableData;
import de.hybris.platform.commerceservices.search.pagedata.SearchPageData;
import de.hybris.platform.core.model.product.ProductModel;

/**
 * Created by Jacob.Ji on 2018/3/20.
 */

public interface AcerchemStockFacade {
    StockDataList getAllStockDataByProduct(String productCode);
}

