package com.acerchem.facades.populators;

import de.hybris.platform.commercefacades.user.converters.populator.AddressPopulator;
import de.hybris.platform.commercefacades.user.converters.populator.CustomerPopulator;
import de.hybris.platform.commercefacades.user.data.AddressData;
import de.hybris.platform.commercefacades.user.data.CustomerData;
import de.hybris.platform.converters.Populator;
import de.hybris.platform.core.model.user.AddressModel;
import de.hybris.platform.core.model.user.CustomerModel;
import de.hybris.platform.servicelayer.dto.converter.ConversionException;
import de.hybris.platform.servicelayer.dto.converter.Converter;
import org.springframework.beans.factory.annotation.Required;

import java.util.Objects;

public class AcerchemCustomerPopulator extends CustomerPopulator implements Populator<CustomerModel, CustomerData>
{
	private Converter<AddressModel,AddressData> addressConverter;

	@Override
	public void populate(CustomerModel source, CustomerData target) throws ConversionException {
		super.populate(source,target);
		if (Objects.nonNull(source.getAddresses())) {
			target.setAddressDataList(addressConverter.convertAll(source.getAddresses()));
		}
	}

	@Required
	public void setAddressConverter(Converter<AddressModel, AddressData> addressConverter) {
		this.addressConverter = addressConverter;
	}
}
