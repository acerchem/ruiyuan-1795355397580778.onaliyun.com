package com.acerchem.core.image.service.impl;

import java.util.List;

import javax.annotation.Resource;

import com.acerchem.core.image.dao.AcerChemMediaDao;
import com.acerchem.core.image.service.AcerChemMediaService;

import de.hybris.platform.core.model.media.MediaModel;

public class AcerChemMediaServiceImpl implements AcerChemMediaService {

	@Resource
	private AcerChemMediaDao acerChemMediaDao;
	@Override
	public MediaModel getMediaModelByPk(String pk) {
		// TODO Auto-generated method stub
		return acerChemMediaDao.getMediaByPk(pk);
	}
	/* (non-Javadoc)
	 * @see com.acerchem.core.image.service.AcerChemMediaService#getMediasOfLimit(java.lang.String, int)
	 */
	@Override
	public List<MediaModel> getMediasOfLimit(String mime, int limitCount) {
		// TODO Auto-generated method stub
		return acerChemMediaDao.getMediasOfLimit(mime, limitCount);
	}

	
	
}
