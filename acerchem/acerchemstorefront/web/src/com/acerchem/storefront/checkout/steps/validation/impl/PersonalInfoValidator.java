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
import com.acerchem.storefront.data.CustomRegisterForm;

/**
* @author alice
* @directions personalInfo forms.
*/
@Component("personalInfoValidator")
public class PersonalInfoValidator implements Validator
{
	@Resource(name = "i18NFacade")
	private I18NFacade i18NFacade;
	
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
		//final String contacts = registerForm.getContacts();
		final String companyName = registerForm.getCompanyName();
		final AddressForm contactCountry = registerForm.getContactAddress();
		
		if(contactCountry==null||contactCountry.getCountryIso()==null)
		{
			errors.rejectValue("contactAddress.countryIso", "register.contactAddress.invalid");
		}
		else
		{
			validateNullValue(errors, contactCountry.getCountryIso(), "contactAddress.countryIso", "register.contactAddress.countryIso.invalid");
			validateNullValue(errors, contactCountry.getTownCity(), "contactAddress.townCity", "register.contactAddress.townCity.invalid");
			List<RegionData> regions=i18NFacade.getRegionsForCountryIso(contactCountry.getCountryIso());
			if(regions.size()>0)
			{
				validateNullValue(errors, contactCountry.getRegionIso(), "contactAddress.regionIso", "register.contactAddress.regionIso.invalid");
			}
		}
		
		validateNullValue(errors, name, "name", "register.name.invalid");
		validateNullValue(errors, currency, "currency", "register.currency.invalid");
		validateNullValue(errors, language, "language", "register.language.invalid");
		//validateNullValue(errors, contacts, "contacts", "register.contacts.invalid");
		validateNullValue(errors, companyName, "companyName", "register.companyName.invalid");
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

