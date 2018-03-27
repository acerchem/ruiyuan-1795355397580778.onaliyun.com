package com.acerchem.facades.populators;

import com.acerchem.facades.product.data.CountryToWarehouseData;
import com.acerchem.facades.product.data.WarehouseData;
import de.hybris.platform.commercefacades.user.data.CountryData;
import de.hybris.platform.converters.Populator;
import de.hybris.platform.core.model.c2l.CountryModel;
import de.hybris.platform.ordersplitting.model.WarehouseModel;
import de.hybris.platform.servicelayer.dto.converter.ConversionException;
import de.hybris.platform.servicelayer.dto.converter.Converter;
import de.hybris.platform.servicelayer.user.UserService;
import de.hybris.platform.storelocator.model.PointOfServiceModel;
import org.springframework.beans.factory.annotation.Required;

import java.util.Objects;

public class CountryToWarehousePopulator implements Populator<PointOfServiceModel, CountryToWarehouseData>
{
	private Converter<CountryModel,CountryData> countryConverter;
	private Converter<WarehouseModel,WarehouseData> warehouseConverter;
	private UserService userService;

	@Override
	public void populate(PointOfServiceModel source, CountryToWarehouseData target) throws ConversionException {

		if (Objects.nonNull(source.getAddress()) && Objects.nonNull(source.getAddress().getCountry())) {
			target.setCountryData(countryConverter.convert(source.getAddress().getCountry()));
		}
		if (Objects.nonNull(source.getWarehouses())){
			target.setWarehouseList(warehouseConverter.convertAll(source.getWarehouses()));
		}
	}

	@Required
	public void setUserService(UserService userService) {
		this.userService = userService;
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
