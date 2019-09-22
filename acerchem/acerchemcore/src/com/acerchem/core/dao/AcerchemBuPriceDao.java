package com.acerchem.core.dao;

import com.acerchem.core.model.BUPriceAdditionalConfModel;


public interface AcerchemBuPriceDao
{
	BUPriceAdditionalConfModel getBuPriceModelByCode(String code);
}
