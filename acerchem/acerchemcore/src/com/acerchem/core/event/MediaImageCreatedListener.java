package com.acerchem.core.event;

import java.lang.reflect.Field;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;

import com.acerchem.core.image.service.AcerChemMediaService;

import de.hybris.platform.core.GenericCondition;
import de.hybris.platform.core.GenericQuery;
import de.hybris.platform.core.GenericSearchField;
import de.hybris.platform.core.Operator;
import de.hybris.platform.core.PK;
import de.hybris.platform.core.model.media.MediaModel;
import de.hybris.platform.europe1.model.PriceRowModel;
import de.hybris.platform.genericsearch.GenericSearchService;
import de.hybris.platform.servicelayer.event.events.AfterItemCreationEvent;
import de.hybris.platform.servicelayer.event.impl.AbstractEventListener;
import de.hybris.platform.servicelayer.model.ModelService;
import de.hybris.platform.servicelayer.search.SearchResult;

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

			// PK pk = (PK) event.getSource();
			//
			// MediaModel media = modelService.get(pk);

			final GenericQuery genericQuery = new GenericQuery(MediaModel._TYPECODE);
			genericQuery.addCondition(GenericCondition.createConditionForValueComparison(
					new GenericSearchField(MediaModel.PK), Operator.EQUAL, event.getSource()));
			final SearchResult<MediaModel> searchResult = genericSearchService.<MediaModel>search(genericQuery);
			if (searchResult.getCount() > 0) {
				MediaModel media = searchResult.getResult().get(0);
				if (media != null) {

					String pkString = media.getPk().getLong().toString();
					
					String ls = media.getLocation();

					System.out.println("***************AfterItemCreationEvent is active***********");
					if (StringUtils.isNotBlank(ls)) {
						System.out.println(ls);
					} else {
						System.out.println("location is null");
					}

					System.out.println("***************调用service");
					media = acerChemMediaService.getMediaModelByPk(pkString);
					if (StringUtils.isNotBlank(media.getLocation())) {
						System.out.println("*****Location");
						System.out.println(media.getLocation());
					} else {
						System.out.println("location is null by callback service");
					}
				}
			}
		}

	}

}
