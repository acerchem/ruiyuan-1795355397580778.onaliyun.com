package com.acerchem.storefront.checkout.steps.validation.impl;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;


/**
 * @author
 * @directions customRegistration forms.
 */
@Component("addSupportTicketValidator")
public class AddSupportTicketValidator implements Validator {
	public static final Pattern EMAIL_REGEX = Pattern.compile("\\b[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}\\b");

	@Override
	public boolean supports(final Class<?> aClass) {
		return SupportTicketForm.class.equals(aClass);
	}

	@Override
	public void validate(final Object object, final Errors errors) {
		final SupportTicketForm supportTicketForm = (SupportTicketForm) object;
		
		final String email = supportTicketForm.getEmail();
		final String message = supportTicketForm.getMessage();
		final String yourname = supportTicketForm.getYourname();
		final String telephone = supportTicketForm.getTelephone();
		final String address = supportTicketForm.getAddress();
		
		if(supportTicketForm.getId()!=null)
		{
			validateNullValuel(errors, message, "message");
		}
		else
		{
			validateEmail(errors, email);
			validateNullValuel(errors, yourname, "yourname");
			validateNullValuel(errors, message, "message");
			validateNullValuel(errors, telephone, "telephone");
			validateNullValuel(errors, address, "address");
		}
	}

	private void validateNullValuel(Errors errors, String filed, String propertyName) {
		if (StringUtils.isBlank(filed)) {
			errors.rejectValue(propertyName, "addSupportTicket." + propertyName + ".invalid");
		} else if (StringUtils.length(filed) > 255) {
			errors.rejectValue(propertyName, "addSupportTicket." + propertyName + ".invalid");
		}
	}

	protected void validateEmail(final Errors errors, final String email) {
		if (StringUtils.isEmpty(email)) {
			errors.rejectValue("email", "addSupportTicket.email.invalid");
		} else if (StringUtils.length(email) > 255 || !validateEmailAddress(email)) {
			errors.rejectValue("email", "addSupportTicket.email.invalid");
		}
	}

	public boolean validateEmailAddress(final String email) {
		final Matcher matcher = EMAIL_REGEX.matcher(email);
		return matcher.matches();
	}
}
