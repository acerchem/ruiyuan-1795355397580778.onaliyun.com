package com.acerchem.core.image.dao;

import com.acerchem.core.model.ImageUploadedLogModel;

public interface AcerChemImageUploadedLogDAO {

	public void saveImageUploadedLog(final ImageUploadedLogModel model);
	public ImageUploadedLogModel getImageUploadedLog(final String pk);
}
