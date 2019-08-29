package com.acerchem.storefront.checkout.steps.validation.impl;

import de.hybris.platform.acceleratorstorefrontcommons.forms.AddressForm;
import de.hybris.platform.commercefacades.i18n.I18NFacade;
import de.hybris.platform.commercefacades.user.data.RegionData;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
* @author alice
* @directions customAddress forms.
*/
@Component("customAddressValidator")
public class CustomAddressValidator implements Validator
{
	@Resource(name = "i18NFacade")
	private I18NFacade i18NFacade;
	private static final int MAX_FIELD_LENGTH = 255;
	private static final int MAX_POSTCODE_LENGTH = 10;
	
	@Override
	public boolean supports(final Class<?> aClass)
	{
		return AddressForm.class.equals(aClass);
	}

	@Override
	public void validate(final Object object, final Errors errors)
	{
		final AddressForm addressForm = (AddressForm) object;
		
		validateStringField(addressForm.getCountryIso(), AddressField.COUNTRY, MAX_FIELD_LENGTH, errors);
		validateStringField(addressForm.getLastName(), AddressField.LASTNAME, MAX_FIELD_LENGTH, errors);
		validateStringField(addressForm.getTownCity(), AddressField.TOWN, MAX_FIELD_LENGTH, errors);
		validateStringField(addressForm.getPostcode(), AddressField.POSTCODE, MAX_POSTCODE_LENGTH, errors);
		if(StringUtils.isNotBlank(addressForm.getCountryIso()))
		{
			List<RegionData> regions = i18NFacade.getRegionsForCountryIso(addressForm.getCountryIso());
			if (regions.size() > 0)
			{
				validateFieldNotNull(addressForm.getRegionIso(), AddressField.REGION, errors);
			}
		}
		validateStringField(addressForm.getPhone(), AddressField.PHONE, 15, errors);
	}

	

	protected static void validateStringField(final String addressField, final AddressField fieldType,
			  final int maxFieldLength, final Errors errors)
	{
		if (addressField == null || StringUtils.isEmpty(addressField) || (StringUtils.length(addressField) > maxFieldLength))
		{
			errors.rejectValue(fieldType.getFieldKey(), fieldType.getErrorKey());
		}
	}

	protected static void validateFieldNotNull(final String addressField, final AddressField fieldType,final Errors errors)
	{
		if (addressField == null)
		{
			errors.rejectValue(fieldType.getFieldKey(), fieldType.getErrorKey());
		}
	}

	protected enum AddressField
	{
		TITLE("titleCode", "address.title.invalid"), FIRSTNAME("firstName", "address.firstName.invalid"),
		LASTNAME("lastName", "register.contacts.invalid"), LINE1("line1", "address.line1.invalid"),
		LINE2("line2", "address.line2.invalid"), TOWN("townCity", "address.townCity.invalid"),
		POSTCODE("postcode", "address.postcode.invalid"), REGION("regionIso", "address.regionIso.invalid"),
		COUNTRY("countryIso", "address.country.invalid"),PHONE("telephone", "register.telephone.invalid");

		private String fieldKey;
		private String errorKey;

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

