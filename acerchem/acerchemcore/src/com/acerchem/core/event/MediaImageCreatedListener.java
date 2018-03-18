package com.acerchem.core.event;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;

import com.acerchem.core.image.service.AcerChemMediaService;

import de.hybris.platform.core.PK;
import de.hybris.platform.core.model.media.MediaModel;
import de.hybris.platform.genericsearch.GenericSearchService;
import de.hybris.platform.servicelayer.event.events.AfterItemCreationEvent;
import de.hybris.platform.servicelayer.event.impl.AbstractEventListener;
import de.hybris.platform.servicelayer.model.ModelService;

public class MediaImageCreatedListener extends AbstractEventListener<AfterItemCreationEvent> {

	@Resource
	GenericSearchService genericSearchService;
	@Resource
	private ModelService modelService;

	@Resource
	private AcerChemMediaService acerChemMediaService;
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.hybris.platform.servicelayer.event.impl.AbstractEventListener#onEvent(
	 * de.hybris.platform.servicelayer.event.events.AbstractEvent)
	 */
	@Override
	protected void onEvent(AfterItemCreationEvent event) {
		// TODO Auto-generated method stub
		if (MediaModel._TYPECODE.equals(event.getTypeCode())) {

			PK pk = (PK) event.getSource();

			MediaModel media = modelService.get(pk);

			String pkString = media.getPk().getLong().toString();
			
			String ls = media.getLocation();

			System.out.println("***************111");
			if (StringUtils.isNotBlank(ls)) {
				System.out.println(ls);
			} else {
				System.out.println("location is null");
			}
			
			System.out.println("***************调用service");
			media = acerChemMediaService.getMediaModelByPk(pkString);
			if(StringUtils.isNotBlank(media.getLocation())){
				System.out.println("*****Location");
				System.out.println(media.getLocation());
			}
			else{
				System.out.println("location is null from AfterItemCreationEvent");
			}
			
		}

	}

}
