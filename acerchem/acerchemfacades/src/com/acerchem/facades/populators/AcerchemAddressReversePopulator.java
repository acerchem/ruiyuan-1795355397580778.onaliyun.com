package com.acerchem.facades.populators;

import de.hybris.platform.commercefacades.user.converters.populator.AddressReversePopulator;
import de.hybris.platform.commercefacades.user.data.AddressData;
import de.hybris.platform.core.model.user.AddressModel;
import de.hybris.platform.servicelayer.dto.converter.ConversionException;

public class AcerchemAddressReversePopulator extends AddressReversePopulator{
	
	@Override
	public void populate(final AddressData addressData, final AddressModel addressModel) throws ConversionException
	{
		super.populate(addressData, addressModel);
		addressModel.setPhone2(addressData.getPhone2());
	}
}
