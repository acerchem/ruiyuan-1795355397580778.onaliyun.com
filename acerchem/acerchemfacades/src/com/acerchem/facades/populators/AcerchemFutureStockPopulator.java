package com.acerchem.facades.populators;

import com.acerchem.core.service.impl.AcerchemFutureStockservice;
import de.hybris.platform.commercefacades.product.data.StockData;
import de.hybris.platform.converters.Populator;
import de.hybris.platform.ordersplitting.model.StockLevelModel;
import de.hybris.platform.servicelayer.dto.converter.ConversionException;

public class AcerchemFutureStockPopulator implements Populator<StockLevelModel, StockData>
{
	private AcerchemFutureStockservice acerchemFutureStockservice;

	@Override
	public void populate(StockLevelModel source, StockData target) {

		target.setFutureStockLevel(Long.valueOf(source.getPreOrder()));
		target.setStockLevel(Long.valueOf(source.getAvailable()));

		target.setFutureReleaseDay(source.getPreOrderReleaseDay());
		target.setAvaReleaseDay(source.getAvaPreOrderReleaseDay());

		target.setWarehouseCode(source.getWarehouse().getCode());
	}

	public void setAcerchemFutureStockservice(AcerchemFutureStockservice acerchemFutureStockservice) {
		this.acerchemFutureStockservice = acerchemFutureStockservice;
	}
}
