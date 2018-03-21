package com.acerchem.core.image.service.impl;

import javax.annotation.Resource;

import com.acerchem.core.image.dao.AcerChemImageUploadedLogDAO;
import com.acerchem.core.image.service.AcerChemImageUploadLogService;
import com.acerchem.core.model.ImageUploadedLogModel;

public class AcerChemImageUploadLogServiceImpl implements AcerChemImageUploadLogService {

	@Resource
	private AcerChemImageUploadedLogDAO acerChemImageUploadedLogDao; 
										
	
	@Override
	public ImageUploadedLogModel getImageUploadedLog(final String pk) {
		// TODO Auto-generated method stub
		return acerChemImageUploadedLogDao.getImageUploadedLog(pk);
		
	}

}
