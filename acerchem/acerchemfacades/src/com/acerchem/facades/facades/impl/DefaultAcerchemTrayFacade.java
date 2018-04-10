package com.acerchem.facades.facades.impl;

import com.acerchem.core.model.CountryTrayFareConfModel;
import com.acerchem.core.service.AcerchemTrayService;
import com.acerchem.facades.facades.AcerchemOrderException;
import com.acerchem.facades.facades.AcerchemTrayFacade;
import de.hybris.platform.core.model.c2l.CountryModel;
import de.hybris.platform.core.model.order.AbstractOrderEntryModel;
import de.hybris.platform.core.model.order.CartModel;
import de.hybris.platform.core.model.product.ProductModel;
import de.hybris.platform.order.CartService;
import de.hybris.platform.servicelayer.i18n.CommonI18NService;
import de.hybris.platform.servicelayer.i18n.I18NService;
import de.hybris.platform.servicelayer.session.SessionService;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.math.BigDecimal;


@Service("acerchemTrayFacade")
public class DefaultAcerchemTrayFacade implements AcerchemTrayFacade {

    @Resource
    private AcerchemTrayService acerchemTrayService;

    @Resource
    private CommonI18NService commonI18NService;

    @Resource
    private CartService cartService;

    @Override
    public double getBasePriceByCountryAndTray(String countryIsoCode, int trayAmount){
        double price = 0.0;
        CountryModel countryModel = commonI18NService.getCountry(countryIsoCode);
        CountryTrayFareConfModel countryTrayFareConf =  acerchemTrayService.getPriceByCountryAndTray(countryModel,trayAmount);
        if (countryTrayFareConf!=null){
            price = countryTrayFareConf.getPrice();
        }
         return price;
    }

    @Override
    public double getTotalPriceForCart() throws AcerchemOrderException {
        double totalTrayPrice = 0.0d;
        CountryModel countryModel = null;
        //托盘数量
        BigDecimal totalTrayAmount = BigDecimal.ZERO;
        if (cartService.hasSessionCart()){
            CartModel cartModel = cartService.getSessionCart();

            for (AbstractOrderEntryModel aoe : cartModel.getEntries()){

                if (aoe.getDeliveryPointOfService().getAddress()!=null) {
                   countryModel = aoe.getDeliveryPointOfService().getAddress().getCountry();
                }
                ProductModel productModel = aoe.getProduct();
                //先获取托盘比例，在计算数量
                String unitCalculateRato = productModel.getUnitCalculateRato();
                if (ObjectUtils.isEmpty(unitCalculateRato)){
                    throw new AcerchemOrderException("当前商品未配置托盘比例,产品编号："+productModel.getCode());
                }
                Long quantity = aoe.getQuantity();

                //托盘数量
                BigDecimal entryTrayAmount = BigDecimal.valueOf(quantity).divide(new BigDecimal(unitCalculateRato),BigDecimal.ROUND_HALF_UP,BigDecimal.ROUND_DOWN);

                totalTrayAmount.add(entryTrayAmount);
            }
        }

        CountryTrayFareConfModel countryTrayFareConf = acerchemTrayService.getPriceByCountryAndTray(countryModel, (int) Math.ceil(totalTrayAmount.doubleValue()));
        if (countryTrayFareConf!=null){
            totalTrayPrice = countryTrayFareConf.getPrice();
        }
        return totalTrayPrice;
    }

}

