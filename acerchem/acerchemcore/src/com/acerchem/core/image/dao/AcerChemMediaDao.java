package com.acerchem.core.image.dao;

import java.util.List;

import de.hybris.platform.core.model.media.MediaModel;

/**
 * 
 * @author jayson.wang
 * added by 2018.3.04
 * modified by 2018.4.19
 */
public interface AcerChemMediaDao {
	public MediaModel getMediaByPk(final String pk);
	
	public List<MediaModel> getMediasOfLimit(final List<String> mimes,final int limitCount); 
}
