package com.acerchem.core.image.service.impl;

import javax.annotation.Resource;

import com.acerchem.core.image.service.AcerChemImageUploadLogService;
import com.acerchem.core.model.ImageUploadedLogModel;

import de.hybris.platform.core.model.media.MediaModel;
import de.hybris.platform.media.services.MimeService;
import de.hybris.platform.servicelayer.media.impl.DefaultMediaService;
import de.hybris.platform.servicelayer.media.impl.MediaDao;
import de.hybris.platform.servicelayer.search.FlexibleSearchService;


public class AcerChemAlternativeMediaServiceImpl extends DefaultMediaService {
	    @Resource
	    private AcerChemImageUploadLogService acerChemImageUploadLogService;

		@Override
		public String getUrlForMedia(MediaModel media) {
			// TODO Auto-generated method stub
			String pk = media.getPk().getLongValueAsString();
			ImageUploadedLogModel imageModel =  acerChemImageUploadLogService.getImageUploadedLog(pk);
			if(imageModel != null){
				return imageModel.getAliyunUrl();
			}
			return super.getUrlForMedia(media);
		}

		@Override
		public void setMediaDao(MediaDao mediaDao) {
			// TODO Auto-generated method stub
			super.setMediaDao(mediaDao);
		}

		@Override
		public void setMimeService(MimeService mimeService) {
			// TODO Auto-generated method stub
			super.setMimeService(mimeService);
		}

		@Override
		public void setFlexibleSearchService(FlexibleSearchService flexibleSearchService) {
			// TODO Auto-generated method stub
			super.setFlexibleSearchService(flexibleSearchService);
		}
	   


	

}
