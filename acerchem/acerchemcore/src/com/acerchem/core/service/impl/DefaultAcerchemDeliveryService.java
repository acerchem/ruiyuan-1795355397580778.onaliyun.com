package com.acerchem.core.service.impl;

import com.acerchem.core.service.AcerchemDeliveryService;
import de.hybris.platform.commerceservices.delivery.impl.DefaultDeliveryService;
import de.hybris.platform.core.model.order.AbstractOrderModel;
import de.hybris.platform.core.model.order.delivery.DeliveryModeModel;
import org.mockito.cglib.core.CollectionUtils;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import static de.hybris.platform.servicelayer.util.ServicesUtil.validateParameterNotNull;

/**
 * Created by Jacob.Ji on 2018/4/8.
 */

public class DefaultAcerchemDeliveryService extends DefaultDeliveryService implements AcerchemDeliveryService {

    @Override
    public Collection<DeliveryModeModel> getSupportedDeliveryModeListForOrder()
    {
        final Collection<DeliveryModeModel> deliveryModes = getDeliveryModeDao().findAllDeliveryModes();

        return deliveryModes;
    }
}
