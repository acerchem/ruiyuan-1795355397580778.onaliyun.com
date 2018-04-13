package com.acerchem.facades.populators;

import de.hybris.platform.commercefacades.user.converters.populator.CustomerPopulator;
import de.hybris.platform.commercefacades.user.data.AddressData;
import de.hybris.platform.commercefacades.user.data.CustomerCreditAccountData;
import de.hybris.platform.commercefacades.user.data.CustomerData;
import de.hybris.platform.converters.Populator;
import de.hybris.platform.core.model.user.AddressModel;
import de.hybris.platform.core.model.user.CustomerModel;
import de.hybris.platform.servicelayer.dto.converter.ConversionException;
import de.hybris.platform.servicelayer.dto.converter.Converter;
import org.springframework.beans.factory.annotation.Required;

import com.acerchem.core.model.CustomerCreditAccountModel;

import java.math.BigDecimal;
import java.util.Objects;

public class AcerchemCustomerPopulator extends CustomerPopulator implements Populator<CustomerModel, CustomerData>
{
	private Converter<AddressModel,AddressData> addressConverter;
	private Converter<CustomerCreditAccountModel,CustomerCreditAccountData> creditAccount;
	
	@Override
	public void populate(CustomerModel source, CustomerData target) throws ConversionException {
		super.populate(source,target);
		if (Objects.nonNull(source.getAddresses())) {
			target.setAddressDataList(addressConverter.convertAll(source.getAddresses()));
		}
		if (source.getCreditAccount() != null)
		{
			target.setCreditAccount(creditAccount.convert(source.getCreditAccount()));
		}
		target.setCompanyType(source.getCompanyType());
		target.setEstablishedIn(source.getEstablishedIn());
		target.setRevenue(source.getRevenue());
		target.setEmployeesNo(source.getEmployeesNo());
		target.setLimitCreditAmount(source.getLimitCreditAmount());
		target.setVatNo(source.getVatNo());
		target.setHaveFinancialReport(source.getHaveFinancialReport());
		target.setProvideTradeReference(source.getProvideTradeReference());
		
	}

	@Required
	public void setAddressConverter(Converter<AddressModel, AddressData> addressConverter) {
		this.addressConverter = addressConverter;
	}
	
	
}

