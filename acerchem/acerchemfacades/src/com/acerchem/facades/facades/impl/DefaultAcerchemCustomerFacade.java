package com.acerchem.facades.facades.impl;

import com.acerchem.facades.facades.AcerchemCustomerFacade;
import de.hybris.platform.commercefacades.customer.impl.DefaultCustomerFacade;
import de.hybris.platform.commercefacades.user.data.CustomerData;
import de.hybris.platform.core.model.user.CustomerModel;
import de.hybris.platform.servicelayer.dto.converter.Converter;
import de.hybris.platform.servicelayer.user.UserService;
import org.springframework.beans.factory.annotation.Required;

/**
 * Created by Jacob.Ji on 2018/3/20.
 */
public class DefaultAcerchemCustomerFacade extends DefaultCustomerFacade implements AcerchemCustomerFacade {

    private Converter<CustomerModel,CustomerData> acerchemCustomerConverter;

    @Override
    public CustomerData getCurrentCustomer() {
        CustomerModel customerModel = (CustomerModel) getCurrentUser();
        CustomerData customerData =  acerchemCustomerConverter.convert(customerModel);
        return customerData;
    }

    @Required
    public void setAcerchemCustomerConverter(Converter<CustomerModel, CustomerData> acerchemCustomerConverter) {
        this.acerchemCustomerConverter = acerchemCustomerConverter;
    }
}
