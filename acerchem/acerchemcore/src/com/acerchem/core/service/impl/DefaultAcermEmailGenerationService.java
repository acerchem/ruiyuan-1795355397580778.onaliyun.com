package com.acerchem.core.service.impl;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.log4j.Logger;
import org.springframework.util.Assert;

import de.hybris.platform.acceleratorservices.email.impl.DefaultEmailGenerationService;
import de.hybris.platform.acceleratorservices.model.cms2.pages.EmailPageModel;
import de.hybris.platform.acceleratorservices.model.cms2.pages.EmailPageTemplateModel;
import de.hybris.platform.acceleratorservices.model.email.EmailAddressModel;
import de.hybris.platform.acceleratorservices.model.email.EmailMessageModel;
import de.hybris.platform.acceleratorservices.process.email.context.AbstractEmailContext;
import de.hybris.platform.commons.model.renderer.RendererTemplateModel;
import de.hybris.platform.processengine.model.BusinessProcessModel;
import de.hybris.platform.servicelayer.util.ServicesUtil;
import de.hybris.platform.util.Config;

public class DefaultAcermEmailGenerationService extends DefaultEmailGenerationService {

	private static final Logger LOG = Logger.getLogger(DefaultAcermEmailGenerationService.class);
	
	@Override
	public EmailMessageModel generate(final BusinessProcessModel businessProcessModel, final EmailPageModel emailPageModel)
	{
		ServicesUtil.validateParameterNotNull(emailPageModel, "EmailPageModel cannot be null");
		Assert.isInstanceOf(EmailPageTemplateModel.class, emailPageModel.getMasterTemplate(),
				"MasterTemplate associated with EmailPageModel should be EmailPageTemplate");
		final EmailMessageModel emailMessageModel;
		final EmailPageTemplateModel emailPageTemplateModel = (EmailPageTemplateModel) emailPageModel.getMasterTemplate();
		
		//if("AcerchemSendEmployeeRegisterEmail".equalsIgnoreCase(emailPageTemplateModel.getUid()) || "AcerchemSendOrderConfirmEmail".equalsIgnoreCase(emailPageTemplateModel.getUid())){
		if("AcerchemDeliveryNoteEmailTemplate".equalsIgnoreCase(emailPageTemplateModel.getUid()) || "AcerchemReleaseNoteEmailTemplate".equalsIgnoreCase(emailPageTemplateModel.getUid())){
			final RendererTemplateModel bodyRenderTemplate = emailPageTemplateModel.getHtmlTemplate();
			Assert.notNull(bodyRenderTemplate, "HtmlTemplate associated with MasterTemplate of EmailPageModel cannot be null");
			final RendererTemplateModel subjectRenderTemplate = emailPageTemplateModel.getSubject();
			Assert.notNull(subjectRenderTemplate, "Subject associated with MasterTemplate of EmailPageModel cannot be null");

			
			//This call creates the context to be used for rendering of subject and body templates.
			final AbstractEmailContext<BusinessProcessModel> emailContext = getEmailContextFactory().create(businessProcessModel,
					emailPageModel, bodyRenderTemplate);

			if (emailContext == null)
			{
				LOG.error("Failed to create email context for businessProcess [" + businessProcessModel + "]");
				throw new IllegalStateException("Failed to create email context for businessProcess [" + businessProcessModel + "]");
			}
			else
			{
				if (!validate(emailContext))
				{
					LOG.error("Email context for businessProcess [" + businessProcessModel + "] is not valid: "
							+ ReflectionToStringBuilder.toString(emailContext));
					throw new IllegalStateException("Email context for businessProcess [" + businessProcessModel + "] is not valid: "
							+ ReflectionToStringBuilder.toString(emailContext));
				}

				final StringWriter subject = new StringWriter();
				getRendererService().render(subjectRenderTemplate, emailContext, subject);

				final StringWriter body = new StringWriter();
				getRendererService().render(bodyRenderTemplate, emailContext, body);
				
				emailMessageModel = createSendEmployeeEmailMessage(subject.toString(), body.toString(), emailContext);

				if (LOG.isDebugEnabled())
				{
					LOG.debug("Email Subject: " + emailMessageModel.getSubject());
					LOG.debug("Email Body: " + emailMessageModel.getBody());
				}

			}
			return emailMessageModel;
		}else{
			final RendererTemplateModel bodyRenderTemplate = emailPageTemplateModel.getHtmlTemplate();
			Assert.notNull(bodyRenderTemplate, "HtmlTemplate associated with MasterTemplate of EmailPageModel cannot be null");
			final RendererTemplateModel subjectRenderTemplate = emailPageTemplateModel.getSubject();
			Assert.notNull(subjectRenderTemplate, "Subject associated with MasterTemplate of EmailPageModel cannot be null");

			
			//This call creates the context to be used for rendering of subject and body templates.
			final AbstractEmailContext<BusinessProcessModel> emailContext = getEmailContextFactory().create(businessProcessModel,
					emailPageModel, bodyRenderTemplate);

			if (emailContext == null)
			{
				LOG.error("Failed to create email context for businessProcess [" + businessProcessModel + "]");
				throw new IllegalStateException("Failed to create email context for businessProcess [" + businessProcessModel + "]");
			}
			else
			{
				if (!validate(emailContext))
				{
					LOG.error("Email context for businessProcess [" + businessProcessModel + "] is not valid: "
							+ ReflectionToStringBuilder.toString(emailContext));
					throw new IllegalStateException("Email context for businessProcess [" + businessProcessModel + "] is not valid: "
							+ ReflectionToStringBuilder.toString(emailContext));
				}

				final StringWriter subject = new StringWriter();
				getRendererService().render(subjectRenderTemplate, emailContext, subject);

				final StringWriter body = new StringWriter();
				getRendererService().render(bodyRenderTemplate, emailContext, body);
				
				emailMessageModel = createEmailMessage(subject.toString(), body.toString(), emailContext);

				if (LOG.isDebugEnabled())
				{
					LOG.debug("Email Subject: " + emailMessageModel.getSubject());
					LOG.debug("Email Body: " + emailMessageModel.getBody());
				}

			}
			return emailMessageModel;
		}
	}
	
	protected EmailMessageModel createEmailMessage(final String emailSubject, final String emailBody,
			final AbstractEmailContext<BusinessProcessModel> emailContext)
	{
		final List<EmailAddressModel> toEmails = new ArrayList<EmailAddressModel>();
		final EmailAddressModel toAddress = getEmailService().getOrCreateEmailAddressForEmail(emailContext.getToEmail(),
				emailContext.getToDisplayName());
		toEmails.add(toAddress);
		final EmailAddressModel fromAddress = getEmailService().getOrCreateEmailAddressForEmail(emailContext.getFromEmail(),
				emailContext.getFromDisplayName());
		return getEmailService().createEmailMessage(toEmails, new ArrayList<EmailAddressModel>(), new ArrayList<EmailAddressModel>(), fromAddress,
				emailContext.getFromEmail(), emailSubject, emailBody, null);
	}
	
	protected EmailMessageModel createSendEmployeeEmailMessage(final String emailSubject, final String emailBody,
			final AbstractEmailContext<BusinessProcessModel> emailContext)
	{
		final List<EmailAddressModel> toEmails = new ArrayList<EmailAddressModel>();
		final EmailAddressModel ccEmailOneAddressModel = getEmailService().getOrCreateEmailAddressForEmail(Config.getParameter("mail.ccAddress.one"),
				Config.getParameter("mail.ccAddress.displayOneName"));
		final EmailAddressModel ccEmailTwoAddressModel = getEmailService().getOrCreateEmailAddressForEmail(Config.getParameter("mail.ccAddress.two"),
				Config.getParameter("mail.ccAddress.displayTwoName"));
		toEmails.add(ccEmailOneAddressModel);
		toEmails.add(ccEmailTwoAddressModel);
		final EmailAddressModel fromAddress = getEmailService().getOrCreateEmailAddressForEmail(emailContext.getFromEmail(),
				emailContext.getFromDisplayName());
		return getEmailService().createEmailMessage(toEmails, new ArrayList<EmailAddressModel>(), new ArrayList<EmailAddressModel>(), fromAddress,
				emailContext.getFromEmail(), emailSubject, emailBody, null);
	}
	

}



