package com.acerchem.facades.populators;

import com.acerchem.core.model.CustomerCreditAccountModel;
import com.acerchem.core.model.UserLevelModel;
import de.hybris.platform.commercefacades.user.converters.populator.CustomerPopulator;
import de.hybris.platform.commercefacades.user.data.*;
import de.hybris.platform.converters.Populator;
import de.hybris.platform.core.model.user.AddressModel;
import de.hybris.platform.core.model.user.CustomerModel;
import de.hybris.platform.ordersplitting.model.WarehouseModel;
import de.hybris.platform.servicelayer.dto.converter.ConversionException;
import de.hybris.platform.servicelayer.dto.converter.Converter;
import org.springframework.beans.factory.annotation.Required;

public class AcerchemCustomerPopulator extends CustomerPopulator implements Populator<CustomerModel, CustomerData>
{
	private Converter<AddressModel,AddressData> addressConverter;
	private Converter<CustomerCreditAccountModel,CustomerCreditAccountData> creditAccountConverter;
	private Converter<UserLevelModel,UserLevelData> userLevelConverter;
	private Converter<WarehouseModel, AcerchemWarehouseData> warehouseConverter;

	@Override
	public void populate(CustomerModel source, CustomerData target) throws ConversionException {
		super.populate(source,target);
		if (source.getAddresses()!=null) {
			target.setAddressDataList(addressConverter.convertAll(source.getAddresses()));
		}
		if (source.getCreditAccount() != null)
		{
			target.setCreditAccount(creditAccountConverter.convert(source.getCreditAccount()));
		}
		target.setCompanyType(source.getCompanyType());
		target.setCompanyName(source.getCompanyName());
		target.setEstablishedIn(source.getEstablishedIn());
		target.setRevenue(source.getRevenue());
		target.setEmployeesNo(source.getEmployeesNo());
		target.setLimitCreditAmount(source.getLimitCreditAmount());
		target.setVatNo(source.getVatNo());
		target.setHaveFinancialReport(source.getHaveFinancialReport());
		target.setProvideTradeReference(source.getProvideTradeReference());
		if (source.getUserLevel() != null)
		{
			target.setUserLevel(userLevelConverter.convert(source.getUserLevel()));
		}

		if (source.getWarehouse() != null && !source.getWarehouse().isEmpty()) {
			target.setWarehouseList(warehouseConverter.convertAll(source.getWarehouse()));
		}
	}

	@Required
	public void setAddressConverter(Converter<AddressModel, AddressData> addressConverter) {
		this.addressConverter = addressConverter;
	}
	
	@Required
	public void setCreditAccountConverter(Converter<CustomerCreditAccountModel, CustomerCreditAccountData> creditAccountConverter) {
		this.creditAccountConverter = creditAccountConverter;
	}
	
	@Required
	public void setUserLevelConverter(Converter<UserLevelModel, UserLevelData> userLevelConverter) {
		this.userLevelConverter = userLevelConverter;
	}

	public final Converter<WarehouseModel, AcerchemWarehouseData> getWarehouseConverter() {
		return warehouseConverter;
	}

	public final void setWarehouseConverter(Converter<WarehouseModel, AcerchemWarehouseData> warehouseConverter) {
		this.warehouseConverter=warehouseConverter;
	}
}

