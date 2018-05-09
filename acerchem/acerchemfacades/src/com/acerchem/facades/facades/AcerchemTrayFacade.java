package com.acerchem.facades.facades;

import de.hybris.platform.commercefacades.order.data.CartData;
import de.hybris.platform.commercefacades.user.data.AddressData;

/**
 * Created by Jacob.Ji on 2018/4/8.
 */
public interface AcerchemTrayFacade {

    /**
     * 根据国家和托盘数量获取价格
     * @param countryIsoCode
     * @param trayAmount
     * @return
     */
    double getBasePriceByCountryAndTray(String countryIsoCode,String regionIsoCode, int trayAmount);

    /**
     * 计算购物车内所有商品的托盘总价
     * @param
     * @return
     */
    double getTotalPriceForCart(CartData cartData,AddressData addressData) throws AcerchemOrderException;

}
