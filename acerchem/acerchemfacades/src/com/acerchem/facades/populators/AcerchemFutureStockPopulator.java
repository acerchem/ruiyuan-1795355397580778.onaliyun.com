package com.acerchem.facades.populators;

import java.util.ArrayList;
import java.util.List;

import com.acerchem.core.service.impl.AcerchemFutureStockservice;
import com.acerchem.facades.product.data.FutureStockLevelData;

import de.hybris.platform.commercefacades.product.converters.populator.StockPopulator;
import de.hybris.platform.commercefacades.product.data.StockData;
import de.hybris.platform.core.model.product.ProductModel;
import de.hybris.platform.ordersplitting.model.StockLevelModel;
import de.hybris.platform.servicelayer.dto.converter.ConversionException;

public class AcerchemFutureStockPopulator<SOURCE extends StockLevelModel, TARGET extends StockData> extends StockPopulator<ProductModel , StockData> 
{
	

	private AcerchemFutureStockservice acerchemFutureStockservice;
	
	
	@Override
	public void populate(ProductModel source, StockData stockData) throws ConversionException {
		
		try {
			
			
			ArrayList<FutureStockLevelData> list = new ArrayList<FutureStockLevelData>();
			
			List<StockLevelModel> stocklevels = acerchemFutureStockservice.getStockLevelModel(source.getCode());
			for (StockLevelModel stockLevelModel2 : stocklevels) {
				//远期库存数量
				Integer  preOrder = stockLevelModel2.getPreOrder();
				//远期库存时间
				Integer preOrderReleaseDay = stockLevelModel2.getPreOrderReleaseDay();
				
				
				if (null!=preOrderReleaseDay&&null!=preOrder) {
					
					//添加到data
					FutureStockLevelData futureStockLevelData = new FutureStockLevelData();
					futureStockLevelData.setFutureStockLevel(preOrder);
					futureStockLevelData.setReleaseDay(preOrderReleaseDay);
					
					list.add(futureStockLevelData);
				}
				
			}
			stockData.setFutureStock(list);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * @return the acerchemFutureStockservice
	 */
	public AcerchemFutureStockservice getAcerchemFutureStockservice() {
		return acerchemFutureStockservice;
	}

	/**
	 * @param acerchemFutureStockservice the acerchemFutureStockservice to set
	 */
	public void setAcerchemFutureStockservice(AcerchemFutureStockservice acerchemFutureStockservice) {
		this.acerchemFutureStockservice = acerchemFutureStockservice;
	}
	
	

}
