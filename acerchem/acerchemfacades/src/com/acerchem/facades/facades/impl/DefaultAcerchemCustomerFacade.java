package com.acerchem.facades.facades.impl;

import com.acerchem.core.service.AcerchemCustomerService;
import com.acerchem.facades.facades.AcerchemCustomerFacade;
import com.acerchem.facades.product.data.CountryToWarehouseData;
import de.hybris.platform.commercefacades.customer.impl.DefaultCustomerFacade;
import de.hybris.platform.commercefacades.user.data.CustomerData;
import de.hybris.platform.commerceservices.search.pagedata.PageableData;
import de.hybris.platform.commerceservices.search.pagedata.SearchPageData;
import de.hybris.platform.core.model.product.ProductModel;
import de.hybris.platform.core.model.user.CustomerModel;
import de.hybris.platform.ordersplitting.model.WarehouseModel;
import de.hybris.platform.product.ProductService;
import de.hybris.platform.servicelayer.dto.converter.Converter;
import de.hybris.platform.store.BaseStoreModel;
import de.hybris.platform.store.services.BaseStoreService;
import de.hybris.platform.storelocator.model.PointOfServiceModel;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.util.ObjectUtils;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by Jacob.Ji on 2018/3/20.
 */
public class DefaultAcerchemCustomerFacade extends DefaultCustomerFacade implements AcerchemCustomerFacade {

    private Converter<CustomerModel,CustomerData> acerchemCustomerConverter;

    private Converter<WarehouseModel,CountryToWarehouseData> countryToWarehouseConverter;

    private AcerchemCustomerService acerchemCustomerService;

    private BaseStoreService baseStoreService;

    private ProductService productService;

    @Override
    public CustomerData getCurrentCustomer() {
        CustomerModel customerModel = (CustomerModel) getCurrentUser();
        CustomerData customerData =  acerchemCustomerConverter.convert(customerModel);
        return customerData;
    }

    @Override
    public SearchPageData<CountryToWarehouseData> getAllPointOfServices(PageableData pageableData) {

        final BaseStoreModel currentBaseStore = getBaseStoreService().getCurrentBaseStore();
        SearchPageData<PointOfServiceModel> result = acerchemCustomerService.getAllPos(currentBaseStore,pageableData);

        List<CountryToWarehouseData> dataList =null;
        if (!ObjectUtils.isEmpty(result) && result.getResults()!=null){
            //需求变动
//            dataList = countryToWarehouseConverter.convertAll(result.getResults());
        }

        SearchPageData<CountryToWarehouseData> searchPageData = new SearchPageData<>();

        searchPageData.setResults(dataList);

        return searchPageData;
    }

    @Override
    public List<CountryToWarehouseData> getAllWarehouses(String productCode) {
        BaseStoreModel baseStoreModel = getBaseStoreService().getCurrentBaseStore();

        List<CountryToWarehouseData> dataList =null;
        ProductModel productModel = productService.getProductForCode(productCode);
        if (CollectionUtils.isNotEmpty(productModel.getStockLevels())) {
            List<WarehouseModel> warehouseModelList = productModel.getStockLevels().stream()
                    .filter(stockLevelModel -> stockLevelModel.getWarehouse()!=null)
                    .flatMap(stockLevelModel -> Stream.of(stockLevelModel.getWarehouse()))
                    .filter(warehouseModel -> warehouseModel.getBaseStores()!=null
                            && warehouseModel.getBaseStores().contains(baseStoreModel))
                    .collect(Collectors.toList());
            if (warehouseModelList!=null){
                dataList = countryToWarehouseConverter.convertAll(warehouseModelList);
            }
        }

        return dataList;
    }

    @Required
    public void setProductService(ProductService productService) {
        this.productService = productService;
    }

    @Required
    public void setCountryToWarehouseConverter(Converter<WarehouseModel, CountryToWarehouseData> countryToWarehouseConverter) {
        this.countryToWarehouseConverter = countryToWarehouseConverter;
    }

    @Required
    public void setAcerchemCustomerConverter(Converter<CustomerModel, CustomerData> acerchemCustomerConverter) {
        this.acerchemCustomerConverter = acerchemCustomerConverter;
    }

    @Required
    public void setAcerchemCustomerService(AcerchemCustomerService acerchemCustomerService) {
        this.acerchemCustomerService = acerchemCustomerService;
    }

    public BaseStoreService getBaseStoreService() {
        return baseStoreService;
    }

    @Required
    public void setBaseStoreService(BaseStoreService baseStoreService) {
        this.baseStoreService = baseStoreService;
    }
}

