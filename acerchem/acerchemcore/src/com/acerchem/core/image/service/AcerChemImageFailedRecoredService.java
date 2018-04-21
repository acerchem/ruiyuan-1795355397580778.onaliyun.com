package com.acerchem.core.image.service;

import java.util.List;

import com.acerchem.core.model.ImageFailedRecordModel;

import de.hybris.platform.core.model.media.MediaModel;

public interface AcerChemImageFailedRecoredService {
	public ImageFailedRecordModel getImageFailedRecordByFileAttr(final String fileName,final String actionType);
	public List<ImageFailedRecordModel> getAllImageFailedRecord();
	public List<MediaModel> getMediaWithImageFailedRecord();
	
	public List<ImageFailedRecordModel> getLimitedImageFailedRecord(final int limit);
}
