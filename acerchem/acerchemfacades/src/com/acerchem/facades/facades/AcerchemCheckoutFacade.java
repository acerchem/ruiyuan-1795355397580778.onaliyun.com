package com.acerchem.facades.facades;

import de.hybris.platform.commercefacades.order.data.CardTypeData;
import de.hybris.platform.commercefacades.order.data.DeliveryModeData;
import de.hybris.platform.commercefacades.user.data.CountryData;
import de.hybris.platform.core.model.order.delivery.DeliveryModeModel;

import java.util.Collection;
import java.util.List;

public interface AcerchemCheckoutFacade {

    void validateCartAddress(CountryData countryData) throws AcerchemOrderException;

    List<? extends DeliveryModeData> getSupportedDeliveryModes();

    boolean setDeliveryMode(final String deliveryModeCode);

    List<CardTypeData> getSupportedCardTypes(String selectedDeliveryModeCode);

}

