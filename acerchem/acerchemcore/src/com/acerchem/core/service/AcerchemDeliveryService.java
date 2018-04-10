package com.acerchem.core.service;

import de.hybris.platform.core.model.order.delivery.DeliveryModeModel;

import java.util.Collection;

/**
 * Created by Jacob.Ji on 2018/4/8.
 */
public interface AcerchemDeliveryService {

    Collection<DeliveryModeModel> getSupportedDeliveryModeListForOrder();
}
