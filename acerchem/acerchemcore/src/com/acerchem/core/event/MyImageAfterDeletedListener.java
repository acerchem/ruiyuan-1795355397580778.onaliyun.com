package com.acerchem.core.event;

import de.hybris.platform.core.model.media.MediaModel;
import de.hybris.platform.servicelayer.event.events.AfterItemRemovalEvent;
import de.hybris.platform.servicelayer.event.impl.AbstractEventListener;

public class MyImageAfterDeletedListener extends AbstractEventListener<AfterItemRemovalEvent> {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.hybris.platform.servicelayer.event.impl.AbstractEventListener#onEvent(
	 * de.hybris.platform.servicelayer.event.events.AbstractEvent)
	 */
	@Override
	protected void onEvent(AfterItemRemovalEvent event) {
		// TODO Auto-generated method stub

		if (MediaModel._TYPECODE.equals(event.getSource())) {
			// AfterItemCreationEvent e = new AfterItemCreationEvent();
			try {
				MediaModel media = (MediaModel) event.getSource();

				if (media != null) {
					System.out.println("****capture mediaModel****");
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

}
