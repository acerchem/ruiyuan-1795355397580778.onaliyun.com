package com.acerchem.core.service.impl;

import java.util.List;


import org.springframework.stereotype.Component;

import com.acerchem.core.dao.impl.AcerchemFutureStockDao;

import de.hybris.platform.ordersplitting.model.StockLevelModel;
import de.hybris.platform.servicelayer.model.ModelService;

@Component
public class DefaultAcerchemFutureStockservice implements AcerchemFutureStockservice {
	
	private AcerchemFutureStockDao acerchemFutureStockDao;

	private ModelService modelService;
	
	
	
	public List<StockLevelModel> getStockLevelModel(String product_pk){
		
		List<StockLevelModel> stocklevel = acerchemFutureStockDao.findAcerChemFutureStock(product_pk);
		
		return stocklevel;
		
	}
	
	
	
	
	/**
	 * @return the modelService
	 */
	public ModelService getModelService() {
		return modelService;
	}

	/**
	 * @param modelService the modelService to set
	 */
	public void setModelService(ModelService modelService) {
		this.modelService = modelService;
	}


	/**
	 * @return the acerchemFutureStockDao
	 */
	public AcerchemFutureStockDao getAcerchemFutureStockDao() {
		return acerchemFutureStockDao;
	}



	/**
	 * @param acerchemFutureStockDao the acerchemFutureStockDao to set
	 */
	public void setAcerchemFutureStockDao(AcerchemFutureStockDao acerchemFutureStockDao) {
		this.acerchemFutureStockDao = acerchemFutureStockDao;
	}
	
	
}


