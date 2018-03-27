package com.acerchem.facades.facades.impl;

import com.acerchem.core.service.AcerchemCustomerService;
import com.acerchem.facades.facades.AcerchemCustomerFacade;
import com.acerchem.facades.product.data.CountryToWarehouseData;
import de.hybris.platform.commercefacades.customer.impl.DefaultCustomerFacade;
import de.hybris.platform.commercefacades.user.data.CustomerData;
import de.hybris.platform.commerceservices.search.pagedata.PageableData;
import de.hybris.platform.commerceservices.search.pagedata.SearchPageData;
import de.hybris.platform.core.model.user.CustomerModel;
import de.hybris.platform.servicelayer.dto.converter.Converter;
import de.hybris.platform.store.BaseStoreModel;
import de.hybris.platform.store.services.BaseStoreService;
import de.hybris.platform.storelocator.model.PointOfServiceModel;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.util.ObjectUtils;

import java.util.List;

/**
 * Created by Jacob.Ji on 2018/3/20.
 */
public class DefaultAcerchemCustomerFacade extends DefaultCustomerFacade implements AcerchemCustomerFacade {

    private Converter<CustomerModel,CustomerData> acerchemCustomerConverter;

    private Converter<PointOfServiceModel,CountryToWarehouseData> countryToWarehouseConverter;

    private AcerchemCustomerService acerchemCustomerService;

    private BaseStoreService baseStoreService;

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
            dataList = countryToWarehouseConverter.convertAll(result.getResults());
        }

        SearchPageData<CountryToWarehouseData> searchPageData = new SearchPageData<>();

        searchPageData.setResults(dataList);

        return searchPageData;
    }

    @Required
    public void setCountryToWarehouseConverter(Converter<PointOfServiceModel, CountryToWarehouseData> countryToWarehouseConverter) {
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

