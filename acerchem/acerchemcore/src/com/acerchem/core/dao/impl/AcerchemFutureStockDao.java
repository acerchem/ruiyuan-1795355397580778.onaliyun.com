package com.acerchem.core.dao.impl;

import java.util.List;

import de.hybris.platform.ordersplitting.model.StockLevelModel;

public interface AcerchemFutureStockDao {
	/**
	 * 
	 * 说明：查询远期库存
	 * @param stockLevel
	 * @return
	 * @author 
	 * @time：
	 */
	List<StockLevelModel> findAcerChemFutureStock(String product_pk);


}




