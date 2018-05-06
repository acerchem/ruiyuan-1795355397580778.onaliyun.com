package com.acerchem.storefront.checkout.steps.validation.impl;

import de.hybris.platform.acceleratorstorefrontcommons.forms.AddressForm;
import de.hybris.platform.acceleratorstorefrontcommons.forms.RegisterForm;
import de.hybris.platform.commercefacades.i18n.I18NFacade;
import de.hybris.platform.commercefacades.user.data.RegionData;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.acerchem.storefront.data.CustomRegisterForm;

/**
* @author alice
* @directions customRegistration forms.
*/
@Component("customRegistrationValidator")
public class CustomRegistrationValidator implements Validator
{
	@Resource(name = "i18NFacade")
	private I18NFacade i18NFacade;
	public static final Pattern EMAIL_REGEX = Pattern.compile("\\b[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}\\b");

	@Override
	public boolean supports(final Class<?> aClass)
	{
		return RegisterForm.class.equals(aClass);
	}

	@Override
	public void validate(final Object object, final Errors errors)
	{
		final CustomRegisterForm registerForm = (CustomRegisterForm) object;

		final String email = registerForm.getEmail();
		final String pwd = registerForm.getPwd();
		final String checkPwd = registerForm.getCheckPwd();
		final String name = registerForm.getName();
		final String currency = registerForm.getCurrency();
		final String language = registerForm.getLanguage();
		final String telephone = registerForm.getTelephone();
		
		final String companyName = registerForm.getCompanyName();
		
		//final String mobileNumber = registerForm.getMobileNumber();
		//final String contacts = registerForm.getContacts();
		final AddressForm shipCountry = registerForm.getShipAddress();
		final AddressForm contactCountry = registerForm.getContactAddress();
		
		if(shipCountry==null||shipCountry.getCountryIso()==null)
		{
			errors.rejectValue("shipAddress.countryIso", "register.shipAddress.invalid");
		}
		else
		{
			validateNullValue(errors, shipCountry.getCountryIso(), "shipAddress.countryIso", "register.shipAddress.countryIso.invalid");
			validateNullValue(errors, shipCountry.getTownCity(), "shipAddress.townCity", "register.shipAddress.townCity.invalid");
			
			List<RegionData> regions=i18NFacade.getRegionsForCountryIso(shipCountry.getCountryIso());
			if(regions.size()>0)
			{
				validateNullValue(errors, shipCountry.getRegionIso(), "shipAddress.regionIso", "register.shipAddress.regionIso.invalid");
			}
		}
			
		if(contactCountry==null)
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
		
		validateEmail(errors, email);
		validatePassword(errors, pwd);
		comparePasswords(errors, pwd, checkPwd);
		validateNullValue(errors, name, "name", "register.name.invalid");
		validateNullValue(errors, currency, "currency", "register.currency.invalid");
		validateNullValue(errors, language, "language", "register.language.invalid");
		validateNullValue(errors, telephone, "telephone", "register.telephone.invalid");
		
		
		validateNullValue(errors, companyName, "companyName", "register.companyName.invalid");
		//validateNullValue(errors, mobileNumber, "mobileNumber", "register.mobileNumber.invalid");
		//validateNullValue(errors, contacts, "contacts", "register.contacts.invalid");

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

	protected void comparePasswords(final Errors errors, final String pwd, final String checkPwd)
	{
		if (StringUtils.isNotEmpty(pwd) && StringUtils.isNotEmpty(checkPwd) && !StringUtils.equals(pwd, checkPwd))
		{
			errors.rejectValue("checkPwd", "validation.checkPwd.equals");
		}
		else
		{
			if (StringUtils.isEmpty(checkPwd))
			{
				errors.rejectValue("checkPwd", "register.checkPwd.invalid");
			}
		}
	}

	protected void validatePassword(final Errors errors, final String pwd)
	{
		String regex = "^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,16}$";	
		if (StringUtils.isEmpty(pwd))
		{
			errors.rejectValue("pwd", "register.pwd.invalid");
		}
		else if (StringUtils.length(pwd) < 6 || StringUtils.length(pwd) > 16)
		{
			errors.rejectValue("pwd", "register.pwd.invalid");
		}
		else if(!pwd.matches(regex))
		{
			errors.rejectValue("pwd", "register.pwd.invalid");
		}

	}

	protected void validateEmail(final Errors errors, final String email)
	{
		if (StringUtils.isEmpty(email))
		{
			errors.rejectValue("email", "register.email.invalid");
		}
		else if (StringUtils.length(email) > 255 || !validateEmailAddress(email))
		{
			errors.rejectValue("email", "register.email.invalid");
		}
	}

	public boolean validateEmailAddress(final String email)
	{
		final Matcher matcher = EMAIL_REGEX.matcher(email);
		return matcher.matches();
	}
}
