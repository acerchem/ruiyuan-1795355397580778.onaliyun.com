package com.acerchem.facades.populators;

import com.acerchem.core.model.CountryToWarehouseModel;
import com.acerchem.facades.product.data.CountryToWarehouseData;
import com.acerchem.facades.product.data.WarehouseData;
import de.hybris.platform.commercefacades.user.converters.populator.CustomerPopulator;
import de.hybris.platform.commercefacades.user.data.AddressData;
import de.hybris.platform.commercefacades.user.data.CountryData;
import de.hybris.platform.commercefacades.user.data.CustomerData;
import de.hybris.platform.converters.Populator;
import de.hybris.platform.core.model.c2l.CountryModel;
import de.hybris.platform.core.model.user.AddressModel;
import de.hybris.platform.core.model.user.CustomerModel;
import de.hybris.platform.ordersplitting.model.WarehouseModel;
import de.hybris.platform.servicelayer.dto.converter.ConversionException;
import de.hybris.platform.servicelayer.dto.converter.Converter;
import org.springframework.beans.factory.annotation.Required;

import java.util.Objects;

public class CountryToWarehousePopulator implements Populator<CountryToWarehouseModel, CountryToWarehouseData>
{
	private Converter<CountryModel,CountryData> countryConverter;
	private Converter<WarehouseModel,WarehouseData> warehouseConverter;

	@Override
	public void populate(CountryToWarehouseModel source, CountryToWarehouseData target) throws ConversionException {
		if (Objects.nonNull(source.getCountry())) {
			target.setCountryData(countryConverter.convert(source.getCountry()));
		}
		if (Objects.nonNull(source.getWarehouseList())){
			target.setWarehouseList(warehouseConverter.convertAll(source.getWarehouseList()));
		}
	}

	@Required
	public void setWarehouseConverter(Converter<WarehouseModel, WarehouseData> warehouseConverter) {
		this.warehouseConverter = warehouseConverter;
	}

	@Required
	public void setCountryConverter(Converter<CountryModel, CountryData> countryConverter) {
		this.countryConverter = countryConverter;
	}
}
