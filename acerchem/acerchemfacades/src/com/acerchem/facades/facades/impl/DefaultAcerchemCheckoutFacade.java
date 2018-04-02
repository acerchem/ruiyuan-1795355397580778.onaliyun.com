package com.acerchem.facades.facades.impl;

import com.acerchem.facades.facades.AcerchemCheckoutFacade;
import com.acerchem.facades.facades.AcerchemOrderException;
import com.acerchem.facades.facades.AcerchemStockFacade;
import com.acerchem.facades.product.data.StockDataList;
import de.hybris.platform.commercefacades.product.data.StockData;
import de.hybris.platform.commercefacades.user.data.CountryData;
import de.hybris.platform.core.model.order.AbstractOrderEntryModel;
import de.hybris.platform.core.model.order.CartEntryModel;
import de.hybris.platform.core.model.order.CartModel;
import de.hybris.platform.core.model.product.ProductModel;
import de.hybris.platform.core.model.user.AddressModel;
import de.hybris.platform.order.CartService;
import de.hybris.platform.ordersplitting.model.StockLevelModel;
import de.hybris.platform.product.ProductService;
import de.hybris.platform.servicelayer.dto.converter.Converter;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Required;

import java.util.List;


public class DefaultAcerchemCheckoutFacade implements AcerchemCheckoutFacade {

    private CartService cartService;

    @Override
    public void validateCartAddress(CountryData countryData) throws AcerchemOrderException{
        //1.cartEntry's pos address match current address
        CartModel cartModel = cartService.getSessionCart();

        if (CollectionUtils.isNotEmpty(cartModel.getEntries())){
            for (AbstractOrderEntryModel aoe : cartModel.getEntries()){
                AddressModel aoeAddress = aoe.getDeliveryPointOfService().getAddress();

                if (aoe.getDeliveryPointOfService().getAddress() == null){
                    throw new AcerchemOrderException("当前提货点未配置地址");
                }else{

                }

            }
        }

    }


}

