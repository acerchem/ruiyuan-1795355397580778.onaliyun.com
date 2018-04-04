package com.acerchem.core.service.impl;

import com.acerchem.core.service.AcerchemStockService;
import de.hybris.platform.core.model.product.ProductModel;
import de.hybris.platform.ordersplitting.model.StockLevelModel;
import de.hybris.platform.ordersplitting.model.WarehouseModel;
import de.hybris.platform.stock.exception.InsufficientStockLevelException;
import de.hybris.platform.stock.impl.DefaultStockService;

public class DefaultAcerchemStockService extends DefaultStockService implements AcerchemStockService {

    @Override
    public void calculateFutureStock(ProductModel product, WarehouseModel warehouse, int quantity, String comment, boolean isUseFutureStock) throws InsufficientStockLevelException {
        if (isUseFutureStock){
            if(quantity <= 0) {
                throw new IllegalArgumentException("amount must be greater than zero.");
            } else {
                StockLevelModel currentStockLevel = super.getStockLevel(product,warehouse);
                int preOrder = currentStockLevel.getPreOrder();
                if(preOrder == 0 || preOrder < quantity) {
                    throw new InsufficientStockLevelException("insufficient available amount for stock level [" + currentStockLevel.getPk() + "]");
                } else {
                    int newPreOrder = preOrder - quantity;
                    currentStockLevel.setPreOrder(newPreOrder);
                    getModelService().save(currentStockLevel);
                }
            }
        }else {
            super.reserve(product,warehouse,quantity,comment);
        }
    }
}
