package com.acerchem.core.image.dao;

import java.util.List;

import com.acerchem.core.model.ImageFailedRecordModel;

import de.hybris.platform.core.model.media.MediaModel;

public interface AcerChemImageFailedRecordDao {

	public List<ImageFailedRecordModel> getImageFailedRecordByFileAttr(final String fileName,final String actionType);
	public List<ImageFailedRecordModel> getAllImageFailedRecord();
	public List<MediaModel> getMediaWithImageFailedRecord();
}
