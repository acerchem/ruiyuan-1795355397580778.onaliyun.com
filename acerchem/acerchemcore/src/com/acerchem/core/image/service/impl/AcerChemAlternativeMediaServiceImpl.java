package com.acerchem.core.image.service.impl;

import javax.annotation.Resource;

import com.acerchem.core.image.service.AcerChemImageUploadLogService;
import com.acerchem.core.model.ImageUploadedLogModel;

import de.hybris.platform.core.model.media.MediaModel;
import de.hybris.platform.servicelayer.media.impl.DefaultMediaService;


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
	   


	

}
