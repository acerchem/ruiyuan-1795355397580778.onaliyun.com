package com.acerchem.core.service.impl;

import java.util.List;

import de.hybris.platform.ordersplitting.model.StockLevelModel;
import de.hybris.platform.store.BaseStoreModel;

public interface AcerchemFutureStockservice {
	/**
	 * 
	 * 说明：
	 * @param productPk
	 * @param baseStore
	 * @author 
	 * @return 
	 * @time：
	 */
	List<StockLevelModel> getStockLevelModel(String productPk, BaseStoreModel baseStore);
	
}



