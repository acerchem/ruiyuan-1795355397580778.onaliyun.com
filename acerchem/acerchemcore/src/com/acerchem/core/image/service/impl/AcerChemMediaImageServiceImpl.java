package com.acerchem.core.image.service.impl;

import javax.annotation.Resource;

import com.acerchem.core.image.service.AcerChemMediaImageService;

import de.hybris.platform.core.model.media.MediaModel;
import de.hybris.platform.servicelayer.model.ModelService;
import de.hybris.platform.servicelayer.util.ServicesUtil;

public class AcerChemMediaImageServiceImpl implements AcerChemMediaImageService {

	@Resource
	private ModelService modelService;

	@Override
	public MediaModel appendImageAliyunUrlForMedia(MediaModel mediaModel) {
		// TODO Auto-generated method stub

		ServicesUtil.validateParameterNotNull(mediaModel, "Argument mediaModel cannot be null");
		try {
			modelService.save(mediaModel);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return mediaModel;
	}

}
