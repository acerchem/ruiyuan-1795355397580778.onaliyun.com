package com.acerchem.facades.facades.impl;

import java.math.BigDecimal;

import javax.annotation.Resource;

import de.hybris.platform.core.model.order.CartModel;
import de.hybris.platform.core.model.user.AddressModel;
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
        int totalTrayAmount = 0;
        if (cartData != null && addressData != null){

            for (OrderEntryData aoe : cartData.getEntries()){

                ProductData productModel = aoe.getProduct();
                //先获取托盘比例，在计算数量
                String unitCalculateRato = productModel.getUnitCalculateRato();
                if (ObjectUtils.isEmpty(unitCalculateRato)){
                   LOG.error("当前商品未配置托盘比例,产品编号："+productModel.getCode());
                   throw new AcerchemOrderException(errorCode,"当前商品未配置托盘比例,产品编号："+productModel.getCode()+",请联系系统售后人员.");
                }
//                Long quantity = (aoe.getQuantity())*(Long.parseLong(aoe.getProduct().getNetWeight()));
                Long quantity = aoe.getQuantity();

                //托盘数量
                int entryTrayAmount = BigDecimal.valueOf(quantity).divide(new BigDecimal(unitCalculateRato),0,BigDecimal.ROUND_UP).intValue();
                totalTrayAmount += entryTrayAmount;
                LOG.debug( productModel.getCode() + " quantity:"+quantity.intValue() + ",unitCalculateRato:"+unitCalculateRato + ",entryTrayAmount:"+entryTrayAmount + "         totalTrayAmount:"+totalTrayAmount);
//                if(totalTrayAmount.intValue() > 20){
//                	totalTrayAmount = new BigDecimal(20);
//                }
            }
            if(addressData.getCountry() != null) {
                CountryModel countryModel = commonI18NService.getCountry(addressData.getCountry().getIsocode());
                if (addressData.getRegion() != null) {
                    regionModel = commonI18NService.getRegion(countryModel, addressData.getRegion().getIsocode());
                }
            }
        }
        LOG.debug("totalTrayAmount:"+totalTrayAmount);
        if(regionModel != null){
        	countryTrayFareConf = acerchemTrayService.getPriceByCountryAndTray(regionModel,totalTrayAmount);
            if (countryTrayFareConf!=null){
                totalTrayPrice = countryTrayFareConf.getPrice();

                totalTrayPrice = new BigDecimal(totalTrayPrice).multiply(new BigDecimal(commonI18NService.getCurrentCurrency().getConversion())).doubleValue();
//            }else{cartData.getDeliveryMode().getCode()DELIVERY_GROSS
//                LOG.error("地区未配置托盘价格，regionModel.getIsocode()="+regionModel.getIsocode());
//                throw new AcerchemOrderException(errorCode,"Do not get the delivery cost information，please contact with I4U.");
            }
        }
        return totalTrayPrice;
    }

    @Override
    public int getDeliveryDaysForCart(CartModel cartModel) {

        RegionModel regionModel = null;
        CountryTrayFareConfModel countryTrayFareConf = null;
        AddressModel addressModel = null;
        if(cartModel != null){
            addressModel = cartModel.getDeliveryAddress();
        }
        if(addressModel != null && cartModel.getDeliveryAddress() != null && cartModel.getDeliveryAddress().getCountry() != null){
            CountryModel countryModel = commonI18NService.getCountry(cartModel.getDeliveryAddress().getCountry().getIsocode());
            if(addressModel.getRegion() != null){
                regionModel =  commonI18NService.getRegion(countryModel, addressModel.getRegion().getIsocode());
            }
        }
        if(regionModel != null){
            countryTrayFareConf = acerchemTrayService.getPriceByCountryAndTray(regionModel, -1);
        }
        if (countryTrayFareConf!=null){
            return countryTrayFareConf.getDeliveriedDay();
        }
        return 0;
    }

}

