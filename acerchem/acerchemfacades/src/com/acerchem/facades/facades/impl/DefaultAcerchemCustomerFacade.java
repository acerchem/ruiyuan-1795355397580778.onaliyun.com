package com.acerchem.facades.facades.impl;

import com.acerchem.core.model.CountryToWarehouseModel;
import com.acerchem.core.service.AcerchemCustomerService;
import com.acerchem.facades.facades.AcerchemCustomerFacade;
import com.acerchem.facades.product.data.CountryToWarehouseData;
import com.acerchem.facades.product.data.CountryToWarehouseDataList;
import de.hybris.platform.commercefacades.customer.impl.DefaultCustomerFacade;
import de.hybris.platform.commercefacades.user.data.CustomerData;
import de.hybris.platform.core.model.user.CustomerModel;
import de.hybris.platform.servicelayer.dto.converter.Converter;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Required;

import java.util.List;

/**
 * Created by Jacob.Ji on 2018/3/20.
 */
public class DefaultAcerchemCustomerFacade extends DefaultCustomerFacade implements AcerchemCustomerFacade {

    private Converter<CustomerModel,CustomerData> acerchemCustomerConverter;
    private Converter<CountryToWarehouseModel,CountryToWarehouseData> countryToWarehouseConverter;

    private AcerchemCustomerService acerchemCustomerService;

    @Override
    public CustomerData getCurrentCustomer() {
        CustomerModel customerModel = (CustomerModel) getCurrentUser();
        CustomerData customerData =  acerchemCustomerConverter.convert(customerModel);
        return customerData;
    }

    @Override
    public CountryToWarehouseDataList getCountryAndWarehouse() {
        CountryToWarehouseDataList countryToWarehouseDataList = new CountryToWarehouseDataList();
        List<CountryToWarehouseModel> modelList = acerchemCustomerService.getCountryAndWarehouse();
        if (CollectionUtils.isNotEmpty(modelList)){
            List<CountryToWarehouseData> dataList = countryToWarehouseConverter.convertAll(modelList);
            countryToWarehouseDataList.setCountryToWarehouseDataList(dataList);
        }
        return countryToWarehouseDataList;
    }

    @Required
    public void setCountryToWarehouseConverter(Converter<CountryToWarehouseModel, CountryToWarehouseData> countryToWarehouseConverter) {
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
}
