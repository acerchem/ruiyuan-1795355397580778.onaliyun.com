package com.acerchem.facades.facades.impl;

import com.acerchem.core.service.AcerchemCustomerService;
import com.acerchem.facades.facades.AcerchemCustomerFacade;
import com.acerchem.facades.facades.AcerchemStockFacade;
import com.acerchem.facades.product.data.StockDataList;
import de.hybris.platform.commercefacades.customer.impl.DefaultCustomerFacade;
import de.hybris.platform.commercefacades.product.data.StockData;
import de.hybris.platform.commercefacades.user.data.CustomerData;
import de.hybris.platform.commerceservices.search.pagedata.PageableData;
import de.hybris.platform.commerceservices.search.pagedata.SearchPageData;
import de.hybris.platform.core.model.product.ProductModel;
import de.hybris.platform.core.model.user.CustomerModel;
import de.hybris.platform.ordersplitting.model.StockLevelModel;
import de.hybris.platform.product.ProductService;
import de.hybris.platform.servicelayer.dto.converter.Converter;
import de.hybris.platform.store.BaseStoreModel;
import de.hybris.platform.store.services.BaseStoreService;
import de.hybris.platform.storelocator.model.PointOfServiceModel;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.util.ObjectUtils;

import java.util.List;

/**
 * Created by Jacob.Ji on 2018/3/20.
 */
public class DefaultAcerchemStockFacade implements AcerchemStockFacade {

    private Converter<StockLevelModel,StockData> acerchemStockConverter;

    private ProductService productService;

    @Override
    public StockDataList getAllStockDataByProduct(String productCode) {

        ProductModel productModel = productService.getProductForCode(productCode);
        StockDataList stockDataList = new StockDataList();
        if (CollectionUtils.isNotEmpty(productModel.getStockLevels())){
            List<StockData> dataList = acerchemStockConverter.convertAll(productModel.getStockLevels());
            stockDataList.setStockDataList(dataList);
        }
        return stockDataList;
    }

    @Required
    public void setProductService(ProductService productService) {
        this.productService = productService;
    }

    @Required
    public void setAcerchemStockConverter(Converter<StockLevelModel, StockData> acerchemStockConverter) {
        this.acerchemStockConverter = acerchemStockConverter;
    }
}

