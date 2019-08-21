package com.acerchem.services.impl;

import com.acerchem.services.StockLevelService;
import de.hybris.platform.core.enums.OrderStatus;
import de.hybris.platform.core.model.order.AbstractOrderEntryModel;
import de.hybris.platform.core.model.order.OrderModel;
import de.hybris.platform.ordersplitting.model.StockLevelModel;
import de.hybris.platform.ordersplitting.model.WarehouseModel;
import de.hybris.platform.servicelayer.model.ModelService;
import org.apache.commons.collections.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Abel.li
 * @description
 * @contact abel0130@163.com
 * @date 2019-08-21
 */
public class DefaultAcerchemStockLevelService implements StockLevelService {

    @Resource
    private ModelService modelService;

    @Override
    public void resetAmountByOrder(OrderModel order) throws Exception {
        if (OrderStatus.UNDELIVERED.getCode().equals(order.getStatus().getCode())) {
            List<AbstractOrderEntryModel> entryModels = order.getEntries();
            StockLevelModel stockLevel = null;
            int totalQuantity = 0, stockQuantity = 0;
            if (CollectionUtils.isNotEmpty(entryModels)) {
                for (AbstractOrderEntryModel orderEntryModel : entryModels) {

                    List<WarehouseModel> warehouseModels = orderEntryModel.getDeliveryPointOfService().getWarehouses();
                    for (WarehouseModel warehouseModel : warehouseModels) {
                        for (StockLevelModel stockLevelModel : warehouseModel.getStockLevels()) {
                            int available=stockLevelModel.getAvailable();
                            if (stockLevelModel.getWarehouse().getCode().equalsIgnoreCase(warehouseModel.getCode())
                                    && stockLevelModel.getProductCode().equalsIgnoreCase(orderEntryModel.getProduct().getCode())
                                    && available - stockLevelModel.getReserved() > 0){
                                stockQuantity += available - stockLevelModel.getReserved();
                                stockLevel = stockLevelModel;
                            }
                        }
                    }
                    totalQuantity += orderEntryModel.getQuantity();
                }
                if (totalQuantity > stockQuantity) {
                    throw new Exception("text.savedcart.report.status.lowstock");
                }
            }

            if (stockLevel != null) {
                stockLevel.setAvailable(stockLevel.getAvailable() - totalQuantity < 1 ? 0 : stockLevel.getAvailable() - totalQuantity);
                stockLevel.setReserved(stockLevel.getReserved() - totalQuantity < 1 ? 0 : stockLevel.getReserved() - totalQuantity);
                modelService.save(stockLevel);
            }
        }
    }
}
