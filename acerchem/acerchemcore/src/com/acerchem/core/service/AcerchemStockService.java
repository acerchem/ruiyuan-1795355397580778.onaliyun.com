package com.acerchem.core.service;

import de.hybris.platform.core.model.order.AbstractOrderModel;
import de.hybris.platform.core.model.product.ProductModel;
import de.hybris.platform.ordersplitting.model.WarehouseModel;
import de.hybris.platform.stock.exception.InsufficientStockLevelException;


public interface AcerchemStockService {

	void calculateStock(ProductModel var1, WarehouseModel var2, int var3, String var4, boolean isUseFutureStock) throws InsufficientStockLevelException;

	void releaseStock(AbstractOrderModel abstractOrderModel);
}
