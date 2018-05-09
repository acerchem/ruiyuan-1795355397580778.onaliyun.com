package com.acerchem.facades.facades.impl;

import java.math.BigDecimal;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.acerchem.core.model.CountryTrayFareConfModel;
import com.acerchem.core.service.AcerchemTrayService;
import com.acerchem.facades.facades.AcerchemOrderException;
import com.acerchem.facades.facades.AcerchemTrayFacade;

import de.hybris.platform.commercefacades.order.data.CartData;
import de.hybris.platform.commercefacades.order.data.OrderEntryData;
import de.hybris.platform.commercefacades.product.data.ProductData;
import de.hybris.platform.commercefacades.user.data.AddressData;
import de.hybris.platform.core.model.c2l.CountryModel;
import de.hybris.platform.core.model.c2l.RegionModel;
import de.hybris.platform.order.CartService;
import de.hybris.platform.servicelayer.i18n.CommonI18NService;
import de.hybris.platform.servicelayer.model.ModelService;


@Service("acerchemTrayFacade")
public class DefaultAcerchemTrayFacade implements AcerchemTrayFacade {

    private static final Logger LOG = Logger.getLogger(DefaultAcerchemTrayFacade.class);

    private final String errorCode="1";

    @Resource
    private AcerchemTrayService acerchemTrayService;

    @Resource
    private CommonI18NService commonI18NService;

    @Resource
    private CartService cartService;
    
    @Resource
    private ModelService modelService;

    @Override
    public double getBasePriceByCountryAndTray(String countryIsoCode,String regionIsoCode, int trayAmount){
        double price = 0.0;
        CountryModel countryModel = commonI18NService.getCountry(countryIsoCode);
        RegionModel regionModel =  commonI18NService.getRegion(countryModel, regionIsoCode);
        CountryTrayFareConfModel countryTrayFareConf =  acerchemTrayService.getPriceByCountryAndTray(regionModel, trayAmount);
        if (countryTrayFareConf!=null){
            price = countryTrayFareConf.getPrice();
        }
         return price;
    }

    @Override
    public double getTotalPriceForCart(CartData cartData,AddressData addressData) throws AcerchemOrderException {
        double totalTrayPrice = 0.0d;
        RegionModel regionModel = null;
        CountryTrayFareConfModel countryTrayFareConf = null;
        //托盘数量
        BigDecimal totalTrayAmount = BigDecimal.ZERO;
        if (cartData!=null){

            for (OrderEntryData aoe : cartData.getEntries()){

                ProductData productModel = aoe.getProduct();
                //先获取托盘比例，在计算数量
                String unitCalculateRato = productModel.getUnitCalculateRato();
                if (ObjectUtils.isEmpty(unitCalculateRato)){
                   LOG.error("当前商品未配置托盘比例,产品编号："+productModel.getCode());
                   throw new AcerchemOrderException(errorCode,"当前商品未配置托盘比例,产品编号："+productModel.getCode()+",请联系系统售后人员.");
                }
                Long quantity = (aoe.getQuantity())*(Long.parseLong(aoe.getProduct().getNetWeight()));

                //托盘数量
                BigDecimal entryTrayAmount = BigDecimal.valueOf(quantity).divide(new BigDecimal(unitCalculateRato),BigDecimal.ROUND_HALF_UP,BigDecimal.ROUND_DOWN);

                totalTrayAmount =totalTrayAmount.add(entryTrayAmount);
            }
            
            CountryModel countryModel = commonI18NService.getCountry(addressData.getCountry().getIsocode());
            if(addressData.getRegion() != null){
            	regionModel =  commonI18NService.getRegion(countryModel, addressData.getRegion().getIsocode());
            }
        }
        if(regionModel != null){
        	countryTrayFareConf = acerchemTrayService.getPriceByCountryAndTray(regionModel, (int) Math.ceil(totalTrayAmount.doubleValue()));
        }
        if (countryTrayFareConf!=null){
            totalTrayPrice = countryTrayFareConf.getPrice();
        }
        return totalTrayPrice;
    }

}

