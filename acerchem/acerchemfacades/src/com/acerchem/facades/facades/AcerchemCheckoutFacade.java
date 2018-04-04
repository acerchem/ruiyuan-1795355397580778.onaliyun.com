package com.acerchem.facades.facades;

import de.hybris.platform.commercefacades.user.data.CountryData;

public interface AcerchemCheckoutFacade {

    void validateCartAddress(CountryData countryData) throws AcerchemOrderException;
}

