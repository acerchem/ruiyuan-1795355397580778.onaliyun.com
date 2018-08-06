package com.acerchem.facades.populators;

import de.hybris.platform.customerticketingfacades.converters.populators.DefaultTicketEventPopulator;
import de.hybris.platform.customerticketingfacades.data.TicketEventData;
import de.hybris.platform.ticket.events.model.CsTicketEventModel;
import de.hybris.platform.servicelayer.dto.converter.ConversionException;

public class AcerchemTicketEventPopulator<SOURCE extends CsTicketEventModel, TARGET extends TicketEventData> extends DefaultTicketEventPopulator<SOURCE,TARGET>{
	
	@Override
	public void populate(final SOURCE source, final TARGET target) throws ConversionException
	{
		super.populate(source, target);
		target.setStartDateTimeStr(source.getCreationtime().toLocaleString().replaceAll("-", "/"));
	}
}
