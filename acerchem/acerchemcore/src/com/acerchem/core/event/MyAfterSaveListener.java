package com.acerchem.core.event;

import java.util.Collection;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;

import de.hybris.platform.commons.model.DocumentModel;
import de.hybris.platform.core.PK;
import de.hybris.platform.core.model.media.MediaModel;
import de.hybris.platform.servicelayer.model.ModelService;
import de.hybris.platform.tx.AfterSaveEvent;
import de.hybris.platform.tx.AfterSaveListener;

public class MyAfterSaveListener implements AfterSaveListener {

	@Resource
	private ModelService modelService;

	@Override
	public void afterSave(Collection<AfterSaveEvent> collection) {
		// TODO Auto-generated method stub

		for (final AfterSaveEvent event : collection) {
			final int type = event.getType();
			if (AfterSaveEvent.UPDATE == type) {
				final PK pk = event.getPk();
				// 30 is media
				if (30 == pk.getTypeCode()) {
					System.out.println("*****Media After save event is active!********");
					final MediaModel media = modelService.get(pk);
					// logic。。。。。。。。
					// when media is images
					String mediaType = StringUtils.isNotBlank(media.getFolder().getPath()) ? media.getFolder().getPath()
							: "";
					if (mediaType.equals("images")) {
						final String ls = media.getLocation();

						if (StringUtils.isNotBlank(ls)) {
							// 采用一个线程调用
							new Thread(new Runnable() {
								@Override
								public void run() {
									try {
										// 休眠100毫秒
										Thread.sleep(100);
									} catch (InterruptedException e) {
										e.printStackTrace();
									}
									// callback logic here
									uploadImageSendProcessor();
									
									System.out.println(ls);
								}
							}).start();
						} else {
							System.out.println("Media's Location is null!");
						}
					}
				}
			}
		}

	}
	
	private void uploadImageSendProcessor(){
		DocumentModel doc = new DocumentModel();
	}

}
