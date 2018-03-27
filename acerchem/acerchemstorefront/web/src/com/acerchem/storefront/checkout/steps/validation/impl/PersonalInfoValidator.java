package com.acerchem.storefront.checkout.steps.validation.impl;

import de.hybris.platform.acceleratorstorefrontcommons.forms.AddressForm;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import com.acerchem.storefront.data.CustomRegisterForm;

/**
* @author alice
* @directions personalInfo forms.
*/
@Component("personalInfoValidator")
public class PersonalInfoValidator implements Validator
{
	@Override
	public boolean supports(final Class<?> aClass)
	{
		return CustomRegisterForm.class.equals(aClass);
	}

	@Override
	public void validate(final Object object, final Errors errors)
	{
		final CustomRegisterForm registerForm = (CustomRegisterForm) object;
		final String name = registerForm.getName();
		final String currency = registerForm.getCurrency();
		final String language = registerForm.getLanguage();
		final String contacts = registerForm.getContacts();
		final AddressForm contactCountry = registerForm.getContactAddress();
		
		if(contactCountry==null)
		{
			errors.rejectValue("contactAddress.countryIso", "register.contactAddress.invalid");
		}
		else
		{
			validateNullValue(errors, contactCountry.getCountryIso(), "contactAddress.countryIso", "register.contactAddress.countryIso.invalid");
			validateNullValue(errors, contactCountry.getRegionIso(), "contactAddress.regionIso", "register.contactAddress.regionIso.invalid");
			validateNullValue(errors, contactCountry.getTownCity(), "contactAddress.townCity", "register.contactAddress.townCity.invalid");
		}
		
		validateNullValue(errors, name, "name", "register.name.invalid");
		validateNullValue(errors, currency, "currency", "register.currency.invalid");
		validateNullValue(errors, language, "language", "register.language.invalid");
		validateNullValue(errors, contacts, "contacts", "register.contacts.invalid");

	}

	protected void validateNullValue(final Errors errors, final String filed, final String propertyName, final String property)
	{
		if (StringUtils.isBlank(filed))
		{
			errors.rejectValue(propertyName, property);
		}
		else if (StringUtils.length(filed) > 255)
		{
			errors.rejectValue(propertyName, property);
		}
	}
	
}

