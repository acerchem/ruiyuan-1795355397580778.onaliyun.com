package com.acerchem.core.image.dao;

import java.util.List;

import com.acerchem.core.model.ImageFailedRecordModel;

public interface AcerChemImageFailedRecordDao {

	public List<ImageFailedRecordModel> getImageFailedRecordByFileAttr(final String fileName,final String actionType);
}
