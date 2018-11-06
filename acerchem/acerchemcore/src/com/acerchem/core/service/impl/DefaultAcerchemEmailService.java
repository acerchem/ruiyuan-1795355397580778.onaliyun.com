package com.acerchem.core.service.impl;

import java.io.DataInputStream;
import java.util.Date;
import java.util.List;

import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;
import org.apache.log4j.Logger;
import org.jgroups.util.UUID;

import de.hybris.platform.acceleratorservices.email.impl.DefaultEmailService;
import de.hybris.platform.acceleratorservices.model.email.EmailAddressModel;
import de.hybris.platform.acceleratorservices.model.email.EmailAttachmentModel;
import de.hybris.platform.acceleratorservices.model.email.EmailMessageModel;

public class DefaultAcerchemEmailService extends DefaultEmailService {
	private static final Logger LOG = Logger.getLogger(DefaultAcerchemEmailService.class);
	public static final String EMAIL_SSLSMTP_PORT_KEY = "mail.sslsmtp.port";
	
	@Override
	public boolean send(final EmailMessageModel message)
	{
		if (message == null)
		{
			throw new IllegalArgumentException("message must not be null");
		}

		final boolean sendEnabled = getConfigurationService().getConfiguration().getBoolean(EMAILSERVICE_SEND_ENABLED_CONFIG_KEY,
				true);
		final int sslSmtpSport = getConfigurationService().getConfiguration().getInt(EMAIL_SSLSMTP_PORT_KEY, 465);
		if (sendEnabled)
		{
			try
			{
				final HtmlEmail email = getPerConfiguredEmail();
				email.setCharset("UTF-8");

				//-------add ssl smtp by Jayson.wang start--------
				email.setSSL(true);
				email.setSslSmtpPort(String.valueOf(sslSmtpSport));
				//-------add ssl smtp by Jayson.wang end ----------
				
				final List<EmailAddressModel> toAddresses = message.getToAddresses();
				setAddresses(message, email, toAddresses);

				final EmailAddressModel fromAddress = message.getFromAddress();
				email.setFrom(fromAddress.getEmailAddress(), nullifyEmpty(fromAddress.getDisplayName()));

				addReplyTo(message, email);

				email.setSubject(message.getSubject());
				email.setHtmlMsg(getBody(message));

				// To support plain text parts use email.setTextMsg()

				final List<EmailAttachmentModel> attachments = message.getAttachments();
				if (!processAttachmentsSuccessful(email, attachments))
				{
					return false;
				}

				// Important to log all emails sent out
				LOG.info("Sending Email [" + message.getPk() + "] To [" + convertToStrings(toAddresses) + "] From ["
						+ fromAddress.getEmailAddress() + "] Subject [" + email.getSubject() + "]");

				// Send the email and capture the message ID
				LOG.info("=======================send Start======================"+new Date());
				final String messageID = email.send();
				LOG.info("=======================send sucess======================"+new Date());
				message.setSent(true);
				message.setSentMessageID(messageID);
				message.setSentDate(new Date());
				getModelService().save(message);
				LOG.info("=======================send end======================"+new Date());
				return true;
			}
			catch (final EmailException e)
			{
				logInfo(message, e);
			}
		}
		else
		{
			LOG.warn("Could not send e-mail pk [" + message.getPk() + "] subject [" + message.getSubject() + "]");
			LOG.info("Email sending has been disabled. Check the config property 'emailservice.send.enabled'");
			return true;
		}

		return false;
	}
	
	
	@Override
	protected void logInfo(final EmailMessageModel message, final EmailException e)
	{
		LOG.warn("Could not send e-mail pk [" + message.getPk() + "] subject [" + message.getSubject() + "] cause: "
				+ e.getMessage());
		if (LOG.isDebugEnabled())
		{
			LOG.debug(e);
		}
	}

	@Override
	public EmailAttachmentModel createEmailAttachment(final DataInputStream masterDataStream, final String filename,
			final String mimeType) {
		final EmailAttachmentModel attachment = getModelService().create(EmailAttachmentModel.class);
		//modified setcode(finlename0->setcode(uuid);
		attachment.setCode(UUID.randomUUID().toString());
		attachment.setMime(mimeType);
		attachment.setRealFileName(filename);
		attachment.setCatalogVersion(getCatalogVersion());
		getModelService().save(attachment);

		getMediaService().setStreamForMedia(attachment, masterDataStream, filename, mimeType, getEmailAttachmentsMediaFolder());
		return attachment;
	}
	
	
}
