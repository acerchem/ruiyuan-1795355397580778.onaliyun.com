package com.acerchem.core.dao.impl;

import java.util.List;

import de.hybris.platform.ordersplitting.model.StockLevelModel;
import de.hybris.platform.store.BaseStoreModel;

public interface AcerchemFutureStockDao {
	/**
	 * 
	 * 说明：查询远期库存
	 * @param
	 * @return
	 * @author 
	 * @time：
	 */
	List<StockLevelModel> findAcerChemFutureStock(String productPK, BaseStoreModel baseStore);


}




