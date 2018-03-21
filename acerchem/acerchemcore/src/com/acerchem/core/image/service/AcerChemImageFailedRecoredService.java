package com.acerchem.core.image.service;

import java.util.List;

import com.acerchem.core.model.ImageFailedRecordModel;

public interface AcerChemImageFailedRecoredService {
	public ImageFailedRecordModel getImageFailedRecordByFileAttr(final String fileName,final String actionType);
}
