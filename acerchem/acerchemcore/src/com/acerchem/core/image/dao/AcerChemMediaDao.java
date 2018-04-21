package com.acerchem.core.image.dao;

import java.util.List;

import de.hybris.platform.core.model.media.MediaModel;

public interface AcerChemMediaDao {
	public MediaModel getMediaByPk(final String pk);

	
	public List<MediaModel> getMediasOfLimit(final List<String> mimes,final int limitCount); 

}
