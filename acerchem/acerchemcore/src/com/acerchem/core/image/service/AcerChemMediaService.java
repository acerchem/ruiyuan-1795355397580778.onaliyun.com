package com.acerchem.core.image.service;

import java.util.List;

import de.hybris.platform.core.model.media.MediaModel;

public interface AcerChemMediaService {

	public MediaModel getMediaModelByPk(final String pk);
	public List<MediaModel> getMediasOfLimit(final List<String> mimes,final int limitCount); 
}
