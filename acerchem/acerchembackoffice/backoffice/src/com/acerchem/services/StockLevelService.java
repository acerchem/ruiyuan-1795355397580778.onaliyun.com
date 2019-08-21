package com.acerchem.services;

import de.hybris.platform.core.model.order.OrderModel;

/**
 * @author Abel.li
 * @description
 * @contact abel0130@163.com
 * @date 2019-08-21
 */
public interface StockLevelService {

    void resetAmountByOrder(OrderModel order) throws Exception;

}
