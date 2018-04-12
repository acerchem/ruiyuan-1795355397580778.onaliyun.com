package com.acerchem.core.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;
import org.springframework.web.multipart.MultipartFile;

import com.acerchem.core.service.AcerChemSupportTicketService;
import de.hybris.platform.ticketsystem.data.CsTicketParameter;
import de.hybris.platform.comments.model.CommentAttachmentModel;
import de.hybris.platform.servicelayer.dto.converter.Converter;
import de.hybris.platform.ticket.events.model.CsCustomerEventModel;
import de.hybris.platform.ticket.model.CsTicketModel;
import de.hybris.platform.ticket.service.impl.DefaultTicketBusinessService;
import de.hybris.platform.ticket.strategies.TicketEventStrategy;




public class AcerChemSupportTicketServiceImpl extends DefaultTicketBusinessService implements AcerChemSupportTicketService {
	private static final Logger LOG = Logger.getLogger(DefaultTicketBusinessService.class);
	
	private static final String CANNOT_UPDATE_TICKET_TO_NULL_STATE = "Cannot update ticket to null state";
	private static final String CANNOT_UPDATE_NULL_TICKET = "Cannot update null ticket";
	private static final String DEFAULT_SUBJECT_MESSAGE = "Support Ticket Status Updated from %s to  %s";
	@Resource
	private Converter<CsTicketParameter, CsTicketModel> defaultTicketParameterConverter;
	@Resource
	private TicketEventStrategy defaultTicketEventStrategy;
	/* (non-Javadoc)
	 * @see com.acerchem.core.service.AcerChemSupportTicketService#createSupportTicket(de.hybris.platform.ticket.model.CsTicketModel)
	 */

	@Override
	public CsTicketModel createTicket(CsTicketParameter ticketParameter) {
		ArrayList attachments = new ArrayList();
		if (CollectionUtils.isNotEmpty(ticketParameter.getAttachments())) {
			Iterator creationEvent = ticketParameter.getAttachments().iterator();

			while (creationEvent.hasNext()) {
				MultipartFile ticket = (MultipartFile) creationEvent.next();

				try {
					CommentAttachmentModel e = (CommentAttachmentModel) this.getModelService()
							.create(CommentAttachmentModel.class);
					e.setItem(this.getTicketAttachmentsService().createAttachment(ticket.getOriginalFilename(),
							ticket.getContentType(), ticket.getBytes(), this.getUserService().getCurrentUser()));
					attachments.add(e);
				} catch (IOException arg5) {
					LOG.error(arg5.getMessage(), arg5);
					return null;
				}
			}
		}

		CsTicketModel ticket1 = (CsTicketModel) this.defaultTicketParameterConverter.convert(ticketParameter);
		ticket1.setProductId(ticketParameter.getProductId());
		ticket1.setProductName(ticketParameter.getProductName());
		ticket1.setYourname(ticketParameter.getYourname());
		ticket1.setTelephone(ticketParameter.getTelephone());
		ticket1.setAddress(ticketParameter.getAddress());
		ticket1.setEmail(ticketParameter.getEmail());
		
		CsCustomerEventModel creationEvent1 = this.defaultTicketEventStrategy.createCreationEventForTicket(ticket1,
				ticketParameter.getReason(), ticketParameter.getInterventionType(), ticketParameter.getCreationNotes());
		attachments.forEach((attachmentModel) -> {
			((CommentAttachmentModel) attachmentModel).setAbstractComment(creationEvent1);
		});
		this.getModelService().saveAll(attachments);
		return this.createTicketInternal(ticket1, creationEvent1);
	}
}
