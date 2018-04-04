package com.acerchem.facades.facades.impl;

import com.acerchem.facades.facades.AcerchemCheckoutFacade;
import com.acerchem.facades.facades.AcerchemOrderException;
import de.hybris.platform.commercefacades.user.data.CountryData;
import de.hybris.platform.core.model.c2l.CountryModel;
import de.hybris.platform.core.model.order.AbstractOrderEntryModel;
import de.hybris.platform.core.model.order.CartModel;
import de.hybris.platform.core.model.user.AddressModel;
import de.hybris.platform.order.CartService;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Required;

import javax.annotation.Resource;


public class DefaultAcerchemCheckoutFacade implements AcerchemCheckoutFacade {

    @Resource
    private CartService cartService;

    @Override
    public void validateCartAddress(CountryData countryData) throws AcerchemOrderException{
        //1.cartEntry's pos address match current address
        CartModel cartModel = cartService.getSessionCart();

        String countryIsoCode = countryData.getIsocode();
        if (CollectionUtils.isNotEmpty(cartModel.getEntries())){
            for (AbstractOrderEntryModel aoe : cartModel.getEntries()){
                AddressModel aoeAddress = aoe.getDeliveryPointOfService().getAddress();
                if (aoeAddress == null){
                    throw new AcerchemOrderException("当前提货点未配置地址.");
                }else{
                    CountryModel countryModel = aoeAddress.getCountry();
                    if (!countryIsoCode.equals(countryModel.getIsocode())){
                        throw new AcerchemOrderException("当前选择地址与购物车内配送地区不一致,请重新选择地址或重新添加商品.");
                    }
                }
            }
        }
    }
}

