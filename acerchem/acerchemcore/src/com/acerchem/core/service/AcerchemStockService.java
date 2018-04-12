package com.acerchem.core.service;

import de.hybris.platform.core.model.product.ProductModel;
import de.hybris.platform.ordersplitting.model.WarehouseModel;
import de.hybris.platform.stock.exception.InsufficientStockLevelException;


public interface AcerchemStockService {

	void calculateFutureStock(ProductModel var1, WarehouseModel var2, int var3, String var4, boolean isUseFutureStock) throws InsufficientStockLevelException;
}
