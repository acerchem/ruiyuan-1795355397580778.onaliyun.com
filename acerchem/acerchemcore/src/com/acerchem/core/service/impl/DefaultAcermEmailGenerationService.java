package com.acerchem.core.service.impl;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import de.hybris.platform.core.model.order.OrderModel;
import de.hybris.platform.core.model.user.CustomerModel;
import de.hybris.platform.core.model.user.EmployeeModel;
import de.hybris.platform.core.model.user.UserModel;
import de.hybris.platform.orderprocessing.model.OrderProcessModel;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.util.Assert;

import com.acerchem.core.util.CommonConvertTools;

import de.hybris.platform.acceleratorservices.email.EmailService;
import de.hybris.platform.acceleratorservices.email.impl.DefaultEmailGenerationService;
import de.hybris.platform.acceleratorservices.model.cms2.pages.EmailPageModel;
import de.hybris.platform.acceleratorservices.model.cms2.pages.EmailPageTemplateModel;
import de.hybris.platform.acceleratorservices.model.email.EmailAddressModel;
import de.hybris.platform.acceleratorservices.model.email.EmailAttachmentModel;
import de.hybris.platform.acceleratorservices.model.email.EmailMessageModel;
import de.hybris.platform.acceleratorservices.process.email.context.AbstractEmailContext;
import de.hybris.platform.catalog.CatalogVersionService;
import de.hybris.platform.catalog.model.CatalogVersionModel;
import de.hybris.platform.commons.model.renderer.RendererTemplateModel;
import de.hybris.platform.core.model.media.MediaModel;
import de.hybris.platform.processengine.model.BusinessProcessModel;
import de.hybris.platform.servicelayer.media.MediaService;
import de.hybris.platform.servicelayer.model.ModelService;
import de.hybris.platform.servicelayer.util.ServicesUtil;
import de.hybris.platform.util.Config;
import de.hybris.platform.util.MediaUtil;

public class DefaultAcermEmailGenerationService extends DefaultEmailGenerationService {

	private static final Logger LOG = Logger.getLogger(DefaultAcermEmailGenerationService.class);

	@Resource
	private CatalogVersionService catalogVersionService;
	@Resource
	private ModelService modelService;
	@Resource
	private MediaService mediaService;
	@Resource
	private EmailService emailService;

	private final static boolean SEND_CUSTOMER_EMAIL_CONTRACT = Config.getBoolean("email.sendcustomer.contract",false);
	private final static boolean SEND_CUSTOMER_EMAIL_PROFORMA = Config.getBoolean("email.sendcustomer.proforma",false);
	private final static boolean SEND_CUSTOMER_EMAIL_INVOICE = Config.getBoolean("email.sendcustomer.invoice",false);
	private final static boolean SEND_CUSTOMER_EMAIL_DELIVERY = Config.getBoolean("email.sendcustomer.delivery",false);
	private final static boolean SEND_CUSTOMER_EMAIL_RELEASE = Config.getBoolean("email.sendcustomer.release",false);
	private final static boolean SEND_CUSTOMER_EMAIL_EMPLOYEE = Config.getBoolean("email.sendcustomer.employee",false);
	private final static boolean SEND_CUSTOMER_EMAIL_FIRST = Config.getBoolean("email.sendcustomer.first",false);
	private final static boolean SEND_CUSTOMER_EMAIL_ONCE = Config.getBoolean("email.sendcustomer.once",false);

	@Override
	public EmailMessageModel generate(final BusinessProcessModel businessProcessModel,
			final EmailPageModel emailPageModel) {
		ServicesUtil.validateParameterNotNull(emailPageModel, "EmailPageModel cannot be null");
		Assert.isInstanceOf(EmailPageTemplateModel.class, emailPageModel.getMasterTemplate(),
				"MasterTemplate associated with EmailPageModel should be EmailPageTemplate");
		EmailMessageModel emailMessageModel = null;
		final EmailPageTemplateModel emailPageTemplateModel = (EmailPageTemplateModel) emailPageModel
				.getMasterTemplate();

		if ("AcerchemSendEmployeeRegisterEmail".equalsIgnoreCase(emailPageTemplateModel.getUid())
				|| "AcerchemSendOrderConfirmEmail".equalsIgnoreCase(emailPageTemplateModel.getUid())) {
			// if("ForgottenPasswordEmailTemplate".equalsIgnoreCase(emailPageTemplateModel.getUid())
			// ||
			// "AcerchemReleaseNoteEmailTemplate".equalsIgnoreCase(emailPageTemplateModel.getUid())){
			final RendererTemplateModel bodyRenderTemplate = emailPageTemplateModel.getHtmlTemplate();
			Assert.notNull(bodyRenderTemplate,
					"HtmlTemplate associated with MasterTemplate of EmailPageModel cannot be null");
			final RendererTemplateModel subjectRenderTemplate = emailPageTemplateModel.getSubject();
			Assert.notNull(subjectRenderTemplate,
					"Subject associated with MasterTemplate of EmailPageModel cannot be null");

			// This call creates the context to be used for rendering of subject
			// and body templates.
			final AbstractEmailContext<BusinessProcessModel> emailContext = getEmailContextFactory()
					.create(businessProcessModel, emailPageModel, bodyRenderTemplate);

			if (emailContext == null) {
				LOG.error("Failed to create email context for businessProcess [" + businessProcessModel + "]");
				throw new IllegalStateException(
						"Failed to create email context for businessProcess [" + businessProcessModel + "]");
			} else {
				// if (!validate(emailContext))
				// {
				// LOG.error("Email context for businessProcess [" +
				// businessProcessModel + "] is not valid: "
				// + ReflectionToStringBuilder.toString(emailContext));
				// throw new IllegalStateException("Email context for
				// businessProcess [" + businessProcessModel + "] is not valid:
				// "
				// + ReflectionToStringBuilder.toString(emailContext));
				// }

				final StringWriter subject = new StringWriter();
				getRendererService().render(subjectRenderTemplate, emailContext, subject);

				final StringWriter body = new StringWriter();
				getRendererService().render(bodyRenderTemplate, emailContext, body);
				LOG.info("=======================generate1 start====================" + new Date());
				emailMessageModel = createSendEmployeeEmailMessage(subject.toString(), body.toString(), emailContext);
				LOG.info("=======================generate1 end====================" + new Date());
				if (LOG.isDebugEnabled()) {
					LOG.debug("Email Subject: " + emailMessageModel.getSubject());
					LOG.debug("Email Body: " + emailMessageModel.getBody());
				}

			}

			return emailMessageModel;
		} else {
			final RendererTemplateModel bodyRenderTemplate = emailPageTemplateModel.getHtmlTemplate();
			Assert.notNull(bodyRenderTemplate,
					"HtmlTemplate associated with MasterTemplate of EmailPageModel cannot be null");
			final RendererTemplateModel subjectRenderTemplate = emailPageTemplateModel.getSubject();
			Assert.notNull(subjectRenderTemplate,
					"Subject associated with MasterTemplate of EmailPageModel cannot be null");

			// This call creates the context to be used for rendering of subject
			// and body templates.
			final AbstractEmailContext<BusinessProcessModel> emailContext = getEmailContextFactory()
					.create(businessProcessModel, emailPageModel, bodyRenderTemplate);

			if (emailContext == null) {
				LOG.error("Failed to create email context for businessProcess [" + businessProcessModel + "]");
				throw new IllegalStateException(
						"Failed to create email context for businessProcess [" + businessProcessModel + "]");
			} else {
				if (!validate(emailContext)) {
					LOG.error("Email context for businessProcess [" + businessProcessModel + "] is not valid: "
							+ ReflectionToStringBuilder.toString(emailContext));
					throw new IllegalStateException("Email context for businessProcess [" + businessProcessModel
							+ "] is not valid: " + ReflectionToStringBuilder.toString(emailContext));
				}

				final StringWriter subject = new StringWriter();
				getRendererService().render(subjectRenderTemplate, emailContext, subject);

				final StringWriter body = new StringWriter();
				getRendererService().render(bodyRenderTemplate, emailContext, body);

//				LOG.info("发送邮件模版============================> "+ emailPageTemplateModel.getUid());

				if ("CustomerRegistrationEmailTemplate".equalsIgnoreCase(emailPageTemplateModel.getUid())) {
					LOG.info("=======================generate2 start====================" + new Date());
					emailMessageModel = createSendEmployeeEmailUNCCMessage(subject.toString(), body.toString(),
							emailContext);
					LOG.info("=======================generate2 end====================" + new Date());

				} else if ("AcerchemContractEmailTemplate".equalsIgnoreCase(emailPageTemplateModel.getUid())
						|| "AcerchemProformaInvoiceEmailTemplate".equalsIgnoreCase(emailPageTemplateModel.getUid())
						|| "AcerchemDeliveryNoteEmailTemplate".equalsIgnoreCase(emailPageTemplateModel.getUid())
						|| "AcerchemReleaseNoteEmailTemplate".equalsIgnoreCase(emailPageTemplateModel.getUid())
				) {

					emailMessageModel = createEmailMessageWithAttachment(subject.toString(), body.toString(),
							emailContext);
				} else if ("AcerchemInvoicePackingListEmailATemplate".equalsIgnoreCase(emailPageTemplateModel.getUid())
						|| "AcerchemInvoicePackingListEmailBTemplate".equalsIgnoreCase(emailPageTemplateModel.getUid())
						|| "AcerchemInvoicePackingListEmailCTemplate".equalsIgnoreCase(emailPageTemplateModel.getUid())
						|| "AcerchemInvoicePackingListEmailDTemplate".equalsIgnoreCase(emailPageTemplateModel.getUid())
						|| "AcerchemInvoicePackingListEmailETemplate".equalsIgnoreCase(emailPageTemplateModel.getUid())
						|| "AcerchemInvoicePackingListEmailFTemplate"
								.equalsIgnoreCase(emailPageTemplateModel.getUid())) {
					
					emailMessageModel = createEmailMessageWithAttachment(subject.toString(), body.toString(),
							emailContext);

				}else if("OrderCancelledEmailTemplate".equalsIgnoreCase(emailPageTemplateModel.getUid())) {
					emailMessageModel = createEmailMessageOfOrderCancelled(subject.toString(), body.toString(), emailContext);
				}else if("QuoteBuyerSubmissionEmailTemplate".equalsIgnoreCase(emailPageTemplateModel.getUid())) {
					emailMessageModel = createEmailMessageWithAttachmentForQuote(subject.toString(), body.toString(), emailContext, businessProcessModel);
				}else if ("RemindEmployeeEmailTemplate".equalsIgnoreCase(emailPageTemplateModel.getUid())){
					emailMessageModel = createEmployeeEmailMessageWithAttachment(subject.toString(), body.toString(),
							emailContext,businessProcessModel);
				}else if ("FirstRemindEmailTemplate".equalsIgnoreCase(emailPageTemplateModel.getUid())){
					emailMessageModel = createFirstEmailMessageWithAttachment(subject.toString(), body.toString(),
							emailContext);
				}else if ("OnceRemindEmailTemplate".equalsIgnoreCase(emailPageTemplateModel.getUid())){
					emailMessageModel = createOnceEmailMessageWithAttachment(subject.toString(), body.toString(),
							emailContext,businessProcessModel);
				}
				else {
					emailMessageModel = createEmailMessage(subject.toString(), body.toString(), emailContext);
				}

				if (LOG.isDebugEnabled()) {
					LOG.debug("Email Subject: " + emailMessageModel.getSubject());
					LOG.debug("Email Body: " + emailMessageModel.getBody());
				}

			}
			return emailMessageModel;
		}
	}

	@Override
	protected EmailMessageModel createEmailMessage(final String emailSubject, final String emailBody,
			final AbstractEmailContext<BusinessProcessModel> emailContext) {
		final List<EmailAddressModel> toEmails = new ArrayList<EmailAddressModel>();
		final List<EmailAddressModel> ccAddress = new ArrayList<EmailAddressModel>();
		final EmailAddressModel toAddress = getEmailService().getOrCreateEmailAddressForEmail(emailContext.getToEmail(),
				emailContext.getToDisplayName());
		toEmails.add(toAddress);
		final EmailAddressModel fromAddress = getEmailService()
				.getOrCreateEmailAddressForEmail(emailContext.getFromEmail(), emailContext.getFromDisplayName());
		if(StringUtils.isNotBlank(Config.getString("mail.ccAddress.one","")))
		{
			final EmailAddressModel ccEmailOneAddressModel = getEmailService()
					.getOrCreateEmailAddressForEmail(Config.getParameter("mail.ccAddress.one"), Config.getParameter("mail.ccAddress.displayOneName"));
			ccAddress.add(ccEmailOneAddressModel);
		}
		if(StringUtils.isNotBlank(Config.getString("mail.ccAddress.two","")))
		{
			final EmailAddressModel ccEmailTwoAddressModel = getEmailService()
					.getOrCreateEmailAddressForEmail(Config.getParameter("mail.ccAddress.two"), Config.getParameter("mail.ccAddress.displayTwoName"));
			ccAddress.add(ccEmailTwoAddressModel);
		}

		return getEmailService().createEmailMessage(toEmails, ccAddress, new ArrayList<EmailAddressModel>(),
				fromAddress, emailContext.getFromEmail(), emailSubject, emailBody, null);
	}
	
	protected EmailMessageModel createEmailMessageOfOrderCancelled(final String emailSubject, final String emailBody,
			final AbstractEmailContext<BusinessProcessModel> emailContext) {
		final List<EmailAddressModel> toEmails = new ArrayList<EmailAddressModel>();
		final List<EmailAddressModel> ccAddress = new ArrayList<EmailAddressModel>();
//		final EmailAddressModel toAddress = getEmailService().getOrCreateEmailAddressForEmail(emailContext.getToEmail(),
//				emailContext.getToDisplayName());
//		toEmails.add(toAddress);
		final EmailAddressModel fromAddress = getEmailService()
				.getOrCreateEmailAddressForEmail(emailContext.getFromEmail(), emailContext.getFromDisplayName());
		final EmailAddressModel ccEmailOneAddressModel = getEmailService().getOrCreateEmailAddressForEmail(
				Config.getParameter("mail.ccAddress.one"), Config.getParameter("mail.ccAddress.displayOneName"));
		final EmailAddressModel ccEmailTwoAddressModel = getEmailService().getOrCreateEmailAddressForEmail(
				Config.getParameter("mail.ccAddress.two"), Config.getParameter("mail.ccAddress.displayTwoName"));
		
		//ccAddress.add(ccEmailOneAddressModel);
		toEmails.add(ccEmailOneAddressModel);
		ccAddress.add(ccEmailTwoAddressModel);

		return getEmailService().createEmailMessage(toEmails, ccAddress, new ArrayList<EmailAddressModel>(),
				fromAddress, emailContext.getFromEmail(), emailSubject, emailBody, null);
	}

	protected EmailMessageModel createEmailMessageWithAttachmentForQuote(final String emailSubject, final String emailBody,
			final AbstractEmailContext<BusinessProcessModel> emailContext, final BusinessProcessModel businessProcessModel) {
		String[] emailBodys = emailBody.split(Config.getString("email.with.attachments.content.separation.flag","---"));

		// 获取pdf文件名字，来源于subject
		final String pdfName = CommonConvertTools.getPdfName(emailSubject);

		final File pdfFile = generatePdfToAttachment(emailBodys[0], pdfName);
		EmailAttachmentModel attachment = null;
		FileInputStream fileInputStream = null;
		DataInputStream dis = null;
		if (pdfFile.exists()) {
			try {

				fileInputStream = new FileInputStream(pdfFile);

				dis = new DataInputStream(fileInputStream);
				attachment = createEmailAttachment(dis, pdfFile.getName(), "application/pdf");

				dis.close();
				pdfFile.delete();
			} catch (final Exception e) {
				e.printStackTrace();
			}

		}

		final List<EmailAddressModel> toEmails = new ArrayList<EmailAddressModel>();
		final List<EmailAddressModel> ccAddress = new ArrayList<EmailAddressModel>();
		final EmailAddressModel toAddress = getEmailService().getOrCreateEmailAddressForEmail(emailContext.getToEmail(),
				emailContext.getToDisplayName());
		toEmails.add(toAddress);
		final EmailAddressModel fromAddress = getEmailService()
				.getOrCreateEmailAddressForEmail(emailContext.getFromEmail(), emailContext.getFromDisplayName());
		if(StringUtils.isNotBlank(Config.getString("mail.ccAddress.one","")))
		{
			final EmailAddressModel ccEmailOneAddressModel = getEmailService()
					.getOrCreateEmailAddressForEmail(Config.getParameter("mail.ccAddress.one"), Config.getParameter("mail.ccAddress.displayOneName"));
			ccAddress.add(ccEmailOneAddressModel);
		}
		if(StringUtils.isNotBlank(Config.getString("mail.ccAddress.two","")))
		{
			final EmailAddressModel ccEmailTwoAddressModel = getEmailService()
					.getOrCreateEmailAddressForEmail(Config.getParameter("mail.ccAddress.two"), Config.getParameter("mail.ccAddress.displayTwoName"));
			ccAddress.add(ccEmailTwoAddressModel);
		}

		if(businessProcessModel instanceof OrderProcessModel){
			OrderModel orderModel = ((OrderProcessModel)businessProcessModel).getOrder();
			UserModel user = orderModel.getUser();
			if(user instanceof CustomerModel){
				CustomerModel customerModel = (CustomerModel)user;
				if(customerModel.getEmployee()!=null){
					final EmailAddressModel ccEmailEmployeeAddressModel = getEmailService()
							.getOrCreateEmailAddressForEmail(customerModel.getEmployee().getUid(), customerModel.getEmployee().getName());
					ccAddress.add(ccEmailEmployeeAddressModel);
				}
			}
		}

		final List<EmailAttachmentModel> attachments = new ArrayList<EmailAttachmentModel>();
		if (attachment != null) {
			attachments.add(attachment);
		}

		String contentEmailBoday = emailBodys.length>1?emailBodys[1]:emailBodys[0];
		return getEmailService().createEmailMessage(toEmails, ccAddress, new ArrayList<EmailAddressModel>(),
				fromAddress, emailContext.getFromEmail(), emailSubject, contentEmailBoday, attachments);
	}

	// add attachment by Jayson.wang
	protected EmailMessageModel createEmailMessageWithAttachment(final String emailSubject, final String emailBody,
			final AbstractEmailContext<BusinessProcessModel> emailContext) {

		final List<EmailAddressModel> toEmails = new ArrayList<EmailAddressModel>();
		final List<EmailAddressModel> ccAddress = new ArrayList<EmailAddressModel>();
		final EmailAddressModel toAddress = getEmailService().getOrCreateEmailAddressForEmail(emailContext.getToEmail(),
				"");
		toEmails.add(toAddress);
		final EmailAddressModel fromAddress = getEmailService()
				.getOrCreateEmailAddressForEmail(emailContext.getFromEmail(), emailContext.getFromDisplayName());
		final EmailAddressModel ccEmailOneAddressModel = getEmailService().getOrCreateEmailAddressForEmail(
				Config.getParameter("mail.ccAddress.one"), Config.getParameter("mail.ccAddress.displayOneName"));
		final EmailAddressModel ccEmailTwoAddressModel = getEmailService().getOrCreateEmailAddressForEmail(
				Config.getParameter("mail.ccAddress.two"), Config.getParameter("mail.ccAddress.displayTwoName"));
		ccAddress.add(ccEmailOneAddressModel);
		ccAddress.add(ccEmailTwoAddressModel);

		// 获取pdf文件名字，来源于subject
		final String pdfName = CommonConvertTools.getPdfName(emailSubject);

		final File pdfFile = generatePdfToAttachment(emailBody, pdfName);
		EmailAttachmentModel attachment = null;
		FileInputStream fileInputStream = null;
		DataInputStream dis = null;
		if (pdfFile.exists()) {
			try {

				fileInputStream = new FileInputStream(pdfFile);

				dis = new DataInputStream(fileInputStream);
				attachment = createEmailAttachment(dis, pdfFile.getName(), "application/pdf");

				dis.close();
				pdfFile.delete();
			} catch (final Exception e) {
				e.printStackTrace();
			}

		}
		final List<EmailAttachmentModel> attachments = new ArrayList<EmailAttachmentModel>();
		if (attachment != null) {
			attachments.add(attachment);
		}

		// 替换email body message
		String emailBodyMessage = "For email's content, please refer to the attachment of .pdf file.";
		String key = "";
		if (StringUtils.containsIgnoreCase(pdfName, "contract")) {

			emailBodyMessage = "For contract content, please refer to pdf attachment.";
			key = "contract";
			if (!SEND_CUSTOMER_EMAIL_CONTRACT){
				toEmails.remove(toAddress);
				ccAddress.remove(ccEmailOneAddressModel);
				toEmails.add(ccEmailOneAddressModel);
			}

		} else if (StringUtils.containsIgnoreCase(pdfName, "proforma")) {
			emailBodyMessage = "For proforma invoice content, please refer to pdf attachment.";
			key = "proformaInvoice";
			if(!SEND_CUSTOMER_EMAIL_PROFORMA){
				toEmails.remove(toAddress);
				ccAddress.remove(ccEmailOneAddressModel);
				toEmails.add(ccEmailOneAddressModel);
			}
			
		} else if (StringUtils.containsIgnoreCase(pdfName, "invoice")) {
			emailBodyMessage = "For invoice content, please refer to pdf attachment.";
			key = "invoice";
			if(!SEND_CUSTOMER_EMAIL_INVOICE){
				toEmails.remove(toAddress);
				ccAddress.remove(ccEmailOneAddressModel);
				toEmails.add(ccEmailOneAddressModel);
			}
		} else if (StringUtils.containsIgnoreCase(pdfName, "delivery")) {
			emailBodyMessage = "For delivery content, please refer to pdf attachment.";
			key = "delivery";
			if(!SEND_CUSTOMER_EMAIL_DELIVERY){
				toEmails.remove(toAddress);
				ccAddress.remove(ccEmailOneAddressModel);
				toEmails.add(ccEmailOneAddressModel);
			}
			
		} else if (StringUtils.containsIgnoreCase(pdfName, "release")) {
			emailBodyMessage = "For release content, please refer to pdf attachment.";
			key = "release";
			if(!SEND_CUSTOMER_EMAIL_RELEASE){
				toEmails.remove(toAddress);
				ccAddress.remove(ccEmailOneAddressModel);
				toEmails.add(ccEmailOneAddressModel);
			}
			
		}
		LOG.info("=========="+key+" email generate start=============");
		// final String tempbody = (String) emailContext.getMessages().get(key);
		final String tempbody = CommonConvertTools.getSpecialProperties(key, emailBodyMessage);
		if (StringUtils.isNotBlank(tempbody)) {
			emailBodyMessage = tempbody;
		}
		emailBodyMessage = CommonConvertTools.getFormatHtml(emailBodyMessage);
		//
		final EmailMessageModel emailMessage = getEmailService().createEmailMessage(toEmails, ccAddress,
				new ArrayList<EmailAddressModel>(), fromAddress, emailContext.getFromEmail(), emailSubject,
				emailBodyMessage, attachments);

		LOG.info("=========="+key+" email generate end=============");
		if (dis != null) {
			try {
				dis.close();
			} catch (final IOException ioe) {
				ioe.printStackTrace();
			}
		}
		if (fileInputStream != null) {
			try {
				fileInputStream.close();
			} catch (final IOException ioe) {
				ioe.printStackTrace();
			}
		}

		return emailMessage;
	}

	protected EmailMessageModel createSendEmployeeEmailMessage(final String emailSubject, final String emailBody,
			final AbstractEmailContext<BusinessProcessModel> emailContext) {
		final String fromServerEmail = Config.getParameter("mail.from");
		final List<EmailAddressModel> toEmails = new ArrayList<EmailAddressModel>();
		final EmailAddressModel ccEmailOneAddressModel = getEmailService().getOrCreateEmailAddressForEmail(
				Config.getParameter("mail.ccAddress.one"), Config.getParameter("mail.ccAddress.displayOneName"));
		final EmailAddressModel ccEmailTwoAddressModel = getEmailService().getOrCreateEmailAddressForEmail(
				Config.getParameter("mail.ccAddress.two"), Config.getParameter("mail.ccAddress.displayTwoName"));
		toEmails.add(ccEmailOneAddressModel);
		toEmails.add(ccEmailTwoAddressModel);
		final EmailAddressModel fromAddress = getEmailService().getOrCreateEmailAddressForEmail(fromServerEmail,
				"email for employee");
		return getEmailService().createEmailMessage(toEmails, new ArrayList<EmailAddressModel>(),
				new ArrayList<EmailAddressModel>(), fromAddress, fromServerEmail, emailSubject, emailBody, null);
	}

	protected EmailMessageModel createSendEmployeeEmailUNCCMessage(final String emailSubject, final String emailBody,
			final AbstractEmailContext<BusinessProcessModel> emailContext) {
		final List<EmailAddressModel> toEmails = new ArrayList<EmailAddressModel>();
		final EmailAddressModel toAddress = getEmailService().getOrCreateEmailAddressForEmail(emailContext.getToEmail(),
				emailContext.getToDisplayName());
		toEmails.add(toAddress);
		final EmailAddressModel fromAddress = getEmailService()
				.getOrCreateEmailAddressForEmail(emailContext.getFromEmail(), emailContext.getFromDisplayName());
		return getEmailService().createEmailMessage(toEmails, new ArrayList<EmailAddressModel>(),
				new ArrayList<EmailAddressModel>(), fromAddress, emailContext.getFromEmail(), emailSubject, emailBody,
				null);
	}

	// add pdf attachments>>>>>>>>>>>>>>>>>>>>>>>>>>>

	protected CatalogVersionModel getSessionCatalogVersion() {
		final Collection<CatalogVersionModel> sessionCatalogVersions = catalogVersionService
				.getSessionCatalogVersions();
		ServicesUtil.validateIfSingleResult(sessionCatalogVersions,
				"Catalog Version not identified in the session. Use CatalogVersionService#setSessionCatalogVersion(String, String) before using this method.",
				"It must exist only one Catalog Version set in the session. Please review the stack call and remove other catalog versions from the session.");
		return sessionCatalogVersions.stream().findFirst().get();
	}

	protected void createMediaModel(final String code, final CatalogVersionModel catalogVersion) {

		final MediaModel media = modelService.create(MediaModel.class);
		media.setCode(code);
		media.setCatalogVersion(catalogVersion);
		modelService.save(media);

	}

	protected CatalogVersionModel getAttachmentCatalogVersion() {
		return catalogVersionService.getSessionCatalogVersionForCatalog("acerchemContentCatalog");
	}

	protected EmailAttachmentModel createEmailAttachment(final DataInputStream masterDataStream, final String filename,
			final String mimeType) {

		final EmailAttachmentModel attachment = emailService.createEmailAttachment(masterDataStream, filename,
				mimeType);
		attachment.setAliyunUrl("emailPdf");
		attachment.setCatalogVersion(getAttachmentCatalogVersion());
		modelService.refresh(attachment);
		return attachment;
	}

	protected File generatePdfToAttachment(final String content, final String displayName) {
		File file;
		// temp pdf dir
		final File mainDataDir = MediaUtil.getLocalStorageDataDir();
		final File tempDir = new File(mainDataDir, "emailPdf");
		if (!tempDir.exists()) {
			tempDir.mkdir();
		}

		if (StringUtils.isBlank(displayName)) {
			file = MediaUtil.composeOrGetParent(tempDir, "email.pdf");
		} else {
			file = MediaUtil.composeOrGetParent(tempDir, displayName + ".pdf");
		}

		CommonConvertTools.HtmlCovertPdf(content, file.getAbsolutePath());

		return file;
	}


	protected EmailMessageModel createEmployeeEmailMessageWithAttachment(final String emailSubject, final String emailBody,
																 final AbstractEmailContext<BusinessProcessModel> emailContext,BusinessProcessModel businessProcessModel) {

		final List<EmailAddressModel> toEmails = new ArrayList<EmailAddressModel>();
		final List<EmailAddressModel> ccAddress = new ArrayList<EmailAddressModel>();

		OrderProcessModel orderProcessModel=(OrderProcessModel)businessProcessModel;
		OrderModel order = orderProcessModel.getOrder();
		String toEmailAdd = "";
		String displayName = "";
		if (null != order){
			CustomerModel customerModel = (CustomerModel)order.getUser();
			EmployeeModel employee = customerModel.getEmployee();
			if (employee == null){
				LOG.info("employee is null , send error");
				return null;
			}

			if(StringUtils.isBlank(employee.getUid())){
				LOG.info("employee emailAddress is null , send error");
				return null;
			}
			toEmailAdd = employee.getUid();
			displayName = employee.getDisplayName();
		}
		LOG.info("send employee emailAddress => "+toEmailAdd+", displayName => "+displayName);
		final EmailAddressModel toAddress = getEmailService().getOrCreateEmailAddressForEmail(toEmailAdd,
				displayName);
		toEmails.add(toAddress);
		final EmailAddressModel fromAddress = getEmailService()
				.getOrCreateEmailAddressForEmail(emailContext.getFromEmail(), emailContext.getFromDisplayName());

		// 获取pdf文件名字，来源于subject
		final String pdfName = CommonConvertTools.getPdfName(emailSubject);

		final File pdfFile = generatePdfToAttachment(emailBody, pdfName);
		EmailAttachmentModel attachment = null;
		FileInputStream fileInputStream = null;
		DataInputStream dis = null;
		if (pdfFile.exists()) {
			try {

				fileInputStream = new FileInputStream(pdfFile);

				dis = new DataInputStream(fileInputStream);
				attachment = createEmailAttachment(dis, pdfFile.getName(), "application/pdf");

				dis.close();
				pdfFile.delete();
			} catch (final Exception e) {
				e.printStackTrace();
			}

		}
		final List<EmailAttachmentModel> attachments = new ArrayList<EmailAttachmentModel>();
		if (attachment != null) {
			attachments.add(attachment);
		}

		// 替换email body message
		String emailBodyMessage = "For email's content, please refer to the attachment of .pdf file.";
		String key = "";
		if (StringUtils.containsIgnoreCase(pdfName, "employee")) {
			emailBodyMessage = "For employee content, please refer to pdf attachment.";
			key = "employee";
			if(!SEND_CUSTOMER_EMAIL_EMPLOYEE){
				toEmails.remove(toAddress);
			}

		}
		LOG.info("=========="+key+" email generate start=============");
		// final String tempbody = (String) emailContext.getMessages().get(key);
		final String tempbody = CommonConvertTools.getSpecialProperties(key, emailBodyMessage);
		if (StringUtils.isNotBlank(tempbody)) {
			emailBodyMessage = tempbody;
		}
		emailBodyMessage = CommonConvertTools.getFormatHtml(emailBodyMessage);
		//只需要发送给业务员
		final EmailMessageModel emailMessage = getEmailService().createEmailMessage(toEmails, new ArrayList<EmailAddressModel>(),
				new ArrayList<EmailAddressModel>(), fromAddress, emailContext.getFromEmail(), emailSubject,
				emailBodyMessage, attachments);

		LOG.info("=========="+key+" email generate end=============");
		if (dis != null) {
			try {
				dis.close();
			} catch (final IOException ioe) {
				ioe.printStackTrace();
			}
		}
		if (fileInputStream != null) {
			try {
				fileInputStream.close();
			} catch (final IOException ioe) {
				ioe.printStackTrace();
			}
		}

		return emailMessage;
	}

	protected EmailMessageModel createFirstEmailMessageWithAttachment(final String emailSubject, final String emailBody,
																		 final AbstractEmailContext<BusinessProcessModel> emailContext) {

		final List<EmailAddressModel> toEmails = new ArrayList<EmailAddressModel>();
		final EmailAddressModel toAddress = getEmailService().getOrCreateEmailAddressForEmail(emailContext.getToEmail(),
				"");
		toEmails.add(toAddress);
		final EmailAddressModel fromAddress = getEmailService()
				.getOrCreateEmailAddressForEmail(emailContext.getFromEmail(), emailContext.getFromDisplayName());
		// 获取pdf文件名字，来源于subject
		final String pdfName = CommonConvertTools.getPdfName(emailSubject);

		final File pdfFile = generatePdfToAttachment(emailBody, pdfName);
		EmailAttachmentModel attachment = null;
		FileInputStream fileInputStream = null;
		DataInputStream dis = null;
		if (pdfFile.exists()) {
			try {

				fileInputStream = new FileInputStream(pdfFile);

				dis = new DataInputStream(fileInputStream);
				attachment = createEmailAttachment(dis, pdfFile.getName(), "application/pdf");

				dis.close();
				pdfFile.delete();
			} catch (final Exception e) {
				e.printStackTrace();
			}

		}
		final List<EmailAttachmentModel> attachments = new ArrayList<EmailAttachmentModel>();
		if (attachment != null) {
			attachments.add(attachment);
		}

		// 替换email body message
		String emailBodyMessage = "For email's content, please refer to the attachment of .pdf file.";
		String key = "";
		if (StringUtils.containsIgnoreCase(pdfName, "firstRemind")) {
			emailBodyMessage = "For first remind content, please refer to pdf attachment.";
			key = "firstRemind";
			if(!SEND_CUSTOMER_EMAIL_FIRST){
				toEmails.remove(toAddress);
			}
		}
		LOG.info("=========="+key+" email generate start=============");
		// final String tempbody = (String) emailContext.getMessages().get(key);
		final String tempbody = CommonConvertTools.getSpecialProperties(key, emailBodyMessage);
		if (StringUtils.isNotBlank(tempbody)) {
			emailBodyMessage = tempbody;
		}
		emailBodyMessage = CommonConvertTools.getFormatHtml(emailBodyMessage);
		//只需要发送给客户
		final EmailMessageModel emailMessage = getEmailService().createEmailMessage(toEmails, new ArrayList<EmailAddressModel>(),
				new ArrayList<EmailAddressModel>(), fromAddress, emailContext.getFromEmail(), emailSubject,
				emailBodyMessage, attachments);

		LOG.info("=========="+key+" email generate end=============");
		if (dis != null) {
			try {
				dis.close();
			} catch (final IOException ioe) {
				ioe.printStackTrace();
			}
		}
		if (fileInputStream != null) {
			try {
				fileInputStream.close();
			} catch (final IOException ioe) {
				ioe.printStackTrace();
			}
		}

		return emailMessage;
	}

	protected EmailMessageModel createOnceEmailMessageWithAttachment(final String emailSubject, final String emailBody,
																 final AbstractEmailContext<BusinessProcessModel> emailContext,BusinessProcessModel businessProcessModel) {

		final List<EmailAddressModel> toEmails = new ArrayList<EmailAddressModel>();
		final List<EmailAddressModel> ccAddress = new ArrayList<EmailAddressModel>();

		OrderProcessModel orderProcessModel=(OrderProcessModel)businessProcessModel;
		OrderModel order = orderProcessModel.getOrder();
		String toEmailAdd = "";
		String displayName = "";
		if (null != order){
			CustomerModel customerModel = (CustomerModel)order.getUser();
			EmployeeModel financialContact = customerModel.getFinancialContact();
			if (financialContact != null){
				if(StringUtils.isNotBlank(financialContact.getUid())){
					toEmailAdd = financialContact.getUid();
					displayName = financialContact.getDisplayName();
				}else {
					LOG.info("financialContact emailAddress is null , send error");
				}
			}else {
				LOG.info("financialContact is null , send error");
			}
		}

		final EmailAddressModel toAddress = getEmailService().getOrCreateEmailAddressForEmail(emailContext.getToEmail(),
				"");
		toEmails.add(toAddress);
		final EmailAddressModel fromAddress = getEmailService()
				.getOrCreateEmailAddressForEmail(emailContext.getFromEmail(), emailContext.getFromDisplayName());
		final EmailAddressModel ccEmailOneAddressModel = getEmailService().getOrCreateEmailAddressForEmail(
				toEmailAdd,displayName);
		ccAddress.add(ccEmailOneAddressModel);

		// 获取pdf文件名字，来源于subject
		final String pdfName = CommonConvertTools.getPdfName(emailSubject);

		final File pdfFile = generatePdfToAttachment(emailBody, pdfName);
		EmailAttachmentModel attachment = null;
		FileInputStream fileInputStream = null;
		DataInputStream dis = null;
		if (pdfFile.exists()) {
			try {

				fileInputStream = new FileInputStream(pdfFile);

				dis = new DataInputStream(fileInputStream);
				attachment = createEmailAttachment(dis, pdfFile.getName(), "application/pdf");

				dis.close();
				pdfFile.delete();
			} catch (final Exception e) {
				e.printStackTrace();
			}

		}
		final List<EmailAttachmentModel> attachments = new ArrayList<EmailAttachmentModel>();
		if (attachment != null) {
			attachments.add(attachment);
		}

		// 替换email body message
		String emailBodyMessage = "For email's content, please refer to the attachment of .pdf file.";
		String key = "";
		if (StringUtils.containsIgnoreCase(pdfName, "onceRemind")) {
			emailBodyMessage = "For once remind content, please refer to pdf attachment.";
			key = "onceRemind";
//			if(!SEND_CUSTOMER_EMAIL_ONCE){
//				toEmails.remove(toAddress);
//				ccAddress.remove(ccEmailOneAddressModel);
//				toEmails.add(ccEmailOneAddressModel);
//			}

		}
		LOG.info("=========="+key+" email generate start=============");
		// final String tempbody = (String) emailContext.getMessages().get(key);
		final String tempbody = CommonConvertTools.getSpecialProperties(key, emailBodyMessage);
		if (StringUtils.isNotBlank(tempbody)) {
			emailBodyMessage = tempbody;
		}
		emailBodyMessage = CommonConvertTools.getFormatHtml(emailBodyMessage);
		//
		final EmailMessageModel emailMessage = getEmailService().createEmailMessage(toEmails, ccAddress,
				new ArrayList<EmailAddressModel>(), fromAddress, emailContext.getFromEmail(), emailSubject,
				emailBodyMessage, attachments);

		LOG.info("=========="+key+" email generate end=============");
		if (dis != null) {
			try {
				dis.close();
			} catch (final IOException ioe) {
				ioe.printStackTrace();
			}
		}
		if (fileInputStream != null) {
			try {
				fileInputStream.close();
			} catch (final IOException ioe) {
				ioe.printStackTrace();
			}
		}

		return emailMessage;
	}

}
