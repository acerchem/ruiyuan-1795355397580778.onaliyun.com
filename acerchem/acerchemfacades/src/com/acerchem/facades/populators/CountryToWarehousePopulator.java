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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CountryToWarehousePopulator implements Populator<WarehouseModel, CountryToWarehouseData>
{
	private Converter<CountryModel,CountryData> countryConverter;
	private Converter<WarehouseModel,WarehouseData> warehouseConverter;
	private UserService userService;

	@Override
	public void populate(WarehouseModel source, CountryToWarehouseData target) throws ConversionException {


		if (source!=null){
			target.setWarehouseData(warehouseConverter.convert(source));
		}
		if (source.getPointsOfService()!=null){
			//get add all pos CountryData
			List<CountryData> countryDataList = new ArrayList<>();
			for (PointOfServiceModel pos :source.getPointsOfService()){
				if (pos.getAddress()!=null){
					CountryData countryData = new CountryData();
					countryData = countryConverter.convert(pos.getAddress().getCountry());
					countryData.setStoreId(pos.getName());
					countryDataList.add(countryData);
				}
			}
			target.setCountryDataList(countryDataList);
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
