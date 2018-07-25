/*
 * [y] hybris Platform
 *
 * Copyright (c) 2017 SAP SE or an SAP affiliate company.  All rights reserved.
 *
 * This software is the confidential and proprietary information of SAP
 * ("Confidential Information"). You shall not disclose such Confidential
 * Information and shall use it only in accordance with the terms of the
 * license agreement you entered into with SAP.
 */
package com.acerchem.validator;

import de.hybris.platform.commercefacades.user.data.AddressData;
import de.hybris.platform.commercefacades.i18n.I18NFacade;
import de.hybris.platform.commercefacades.user.data.RegionData;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;


/**
 * Validator for address forms. Enforces the order of validation
 */
@Component("customerAddressValidator")
public class CustomerAddressValidator implements Validator
{
	@Resource(name = "i18NFacade")
	private I18NFacade i18NFacade;
	private static final int MAX_FIELD_LENGTH = 255;
	private static final int MAX_POSTCODE_LENGTH = 10;

	@Override
	public boolean supports(final Class<?> aClass)
	{
		return AddressData.class.equals(aClass);
	}

	@Override
	public void validate(final Object object, final Errors errors)
	{
		final AddressData AddressData = (AddressData) object;
		validateStringField(AddressData.getLastName(), AddressField.LASTNAME, MAX_FIELD_LENGTH, errors);
		validateStringField(AddressData.getCountry().getIsocode(), AddressField.COUNTRY, MAX_FIELD_LENGTH, errors);
		validateStringField(AddressData.getTown(), AddressField.TOWN, MAX_FIELD_LENGTH, errors);
		validateStringField(AddressData.getPostalCode(), AddressField.POSTCODE, MAX_POSTCODE_LENGTH, errors);

		final List<RegionData> regions = i18NFacade.getRegionsForCountryIso(AddressData.getCountry().getIsocode());
		if (regions.size() > 0)
		{
			validateFieldNotNull(AddressData.getRegion().getIsocode(), AddressField.REGION, errors);
		}
		validateStringField(AddressData.getPhone(), AddressField.Phone, 15, errors);
		validateStringField(AddressData.getPhone2(), AddressField.Phone2, 15, errors);
	}

	protected static void validateStringField(final String addressField, final AddressField fieldType, final int maxFieldLength,
			final Errors errors)
	{
		if (addressField == null || StringUtils.isEmpty(addressField) || (StringUtils.length(addressField) > maxFieldLength))
		{
			errors.rejectValue(fieldType.getFieldKey(), fieldType.getErrorKey());
		}
	}

	protected static void validateFieldNotNull(final String addressField, final AddressField fieldType, final Errors errors)
	{
		if (addressField == null)
		{
			errors.rejectValue(fieldType.getFieldKey(), fieldType.getErrorKey());
		}
	}

	protected enum CountryCode
	{
		USA("US"), CANADA("CA"), JAPAN("JP"), CHINA("CN"), BRITAIN("GB"), GERMANY("DE"), DEFAULT("");

		private final String isoCode;

		private static Map<String, CountryCode> lookupMap = new HashMap<String, CountryCode>();
		static
		{
			for (final CountryCode code : CountryCode.values())
			{
				lookupMap.put(code.getIsoCode(), code);
			}
		}

		private CountryCode(final String isoCodeStr)
		{
			this.isoCode = isoCodeStr;
		}

		public static CountryCode lookup(final String isoCodeStr)
		{
			CountryCode code = lookupMap.get(isoCodeStr);
			if (code == null)
			{
				code = DEFAULT;
			}
			return code;
		}

		public String getIsoCode()
		{
			return isoCode;
		}
	}

	protected enum AddressField
	{
		LASTNAME("lastName", "address.lastName.invalid"), TOWN("townCity", "address.townCity.invalid"), POSTCODE("postcode",
				"address.postcode.invalid"), REGION("regionIso", "address.regionIso.invalid"), COUNTRY("countryIso",
						"address.country.invalid"), Phone("phone", "address.phone.invalid"), Phone2("phone2", "address.phone.invalid");

		private final String fieldKey;
		private final String errorKey;

		private AddressField(final String fieldKey, final String errorKey)
		{
			this.fieldKey = fieldKey;
			this.errorKey = errorKey;
		}

		public String getFieldKey()
		{
			return fieldKey;
		}

		public String getErrorKey()
		{
			return errorKey;
		}
	}
}
