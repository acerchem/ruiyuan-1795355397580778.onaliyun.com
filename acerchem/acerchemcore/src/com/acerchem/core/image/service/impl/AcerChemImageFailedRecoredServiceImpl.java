package com.acerchem.core.image.service.impl;

import java.util.List;

import javax.annotation.Resource;

import com.acerchem.core.image.dao.AcerChemImageFailedRecordDao;
import com.acerchem.core.image.service.AcerChemImageFailedRecoredService;
import com.acerchem.core.model.ImageFailedRecordModel;

import de.hybris.platform.core.model.media.MediaModel;

public class AcerChemImageFailedRecoredServiceImpl implements AcerChemImageFailedRecoredService {
	
	

	@Resource
	private AcerChemImageFailedRecordDao acerChemImageFailedRecordDao;

	@Override
	public ImageFailedRecordModel getImageFailedRecordByFileAttr(final String fileName, final String actionType) {
		// TODO Auto-generated method stub
		
		final List<ImageFailedRecordModel> list = acerChemImageFailedRecordDao.getImageFailedRecordByFileAttr(fileName, actionType);
		if(list.size()>0){
			return list.get(0);
		}
		return null;
	}

	@Override
	public List<ImageFailedRecordModel> getAllImageFailedRecord() {
		// TODO Auto-generated method stub
		return acerChemImageFailedRecordDao.getAllImageFailedRecord();
	}
	
	/* (non-Javadoc)
	 * @see com.acerchem.core.image.service.AcerChemImageFailedRecoredService#getMediaWithImageFailedRecord()
	 */
	@Override
	public List<MediaModel> getMediaWithImageFailedRecord() {
		// TODO Auto-generated method stub
		return acerChemImageFailedRecordDao.getMediaWithImageFailedRecord();
	}

	/* (non-Javadoc)
	 * @see com.acerchem.core.image.service.AcerChemImageFailedRecoredService#getLimitedImageFailedRecord(int)
	 */
	@Override
	public List<ImageFailedRecordModel> getLimitedImageFailedRecord(final int limit) {
		// TODO Auto-generated method stub
		return acerChemImageFailedRecordDao.getLimitedImageFailedRecord(limit);
	}
	
	
}
