package com.acerchem.facades.populators;

import de.hybris.platform.customerticketingfacades.data.TicketData;
import de.hybris.platform.ticket.model.CsTicketModel;

import java.text.SimpleDateFormat;

import de.hybris.platform.customerticketingfacades.converters.populators.DefaultTicketListPopulator;
import de.hybris.platform.servicelayer.dto.converter.ConversionException;
import de.hybris.platform.converters.Populator;
import de.hybris.platform.customerticketingfacades.data.StatusData;
import de.hybris.platform.ticket.enums.CsTicketState;
import java.util.Map;
import java.util.TimeZone;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Required;

public class AcerchemTicketPopulator<SOURCE extends CsTicketModel, TARGET extends TicketData> implements Populator<SOURCE, TARGET>{
	
	protected static final Logger LOG = Logger.getLogger(DefaultTicketListPopulator.class);
	private Map<String, StatusData> statusMapping;
	
	public void populate(final CsTicketModel source, final TicketData target) throws ConversionException
	{
		target.setId(source.getTicketID());
		target.setSubject(source.getHeadline());
		target.setCreationDate(source.getCreationtime());
		target.setLastModificationDate(source.getModifiedtime());
		
		target.setCreationDateStr(source.getCreationtime().toLocaleString().replaceAll("-", "/"));
		target.setLastModificationDateStr(source.getModifiedtime().toLocaleString().replaceAll("-", "/"));
		
		final CsTicketState csTicketState = source.getState();
		target.setStatus(getStatusMapping().get(csTicketState.getCode()));
	}

	/**
	 * @return the statusMapping
	 */
	public Map<String, StatusData> getStatusMapping()
	{
		return statusMapping;
	}

	/**
	 * @param statusMapping
	 *           the statusMapping to set
	 */
	@Required
	public void setStatusMapping(final Map<String, StatusData> statusMapping)
	{
		this.statusMapping = statusMapping;
	}
}


