package com.acerchem.core.service.impl;

import java.util.List;

import de.hybris.platform.ordersplitting.model.StockLevelModel;

public interface AcerchemFutureStockservice {
	/**
	 * 
	 * 说明：
	 * @param stockLevelModel
	 * @author 
	 * @return 
	 * @time：
	 */
	List<StockLevelModel> getStockLevelModel(String product_pk);
	
}



