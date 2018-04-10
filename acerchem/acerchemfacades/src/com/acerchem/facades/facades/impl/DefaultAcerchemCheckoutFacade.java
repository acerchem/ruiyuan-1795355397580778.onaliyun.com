package com.acerchem.facades.facades.impl;

import com.acerchem.core.service.AcerchemDeliveryService;
import com.acerchem.core.service.AcerchemTrayService;
import com.acerchem.facades.facades.AcerchemCheckoutFacade;
import com.acerchem.facades.facades.AcerchemOrderException;
import com.acerchem.facades.facades.AcerchemTrayFacade;
import de.hybris.platform.commercefacades.order.data.DeliveryModeData;
import de.hybris.platform.commercefacades.order.data.ZoneDeliveryModeData;
import de.hybris.platform.commercefacades.order.impl.DefaultCheckoutFacade;
import de.hybris.platform.commercefacades.product.data.PriceDataType;
import de.hybris.platform.commercefacades.user.data.CountryData;
import de.hybris.platform.core.model.c2l.CountryModel;
import de.hybris.platform.core.model.order.AbstractOrderEntryModel;
import de.hybris.platform.core.model.order.CartModel;
import de.hybris.platform.core.model.order.delivery.DeliveryModeModel;
import de.hybris.platform.core.model.user.AddressModel;
import de.hybris.platform.deliveryzone.model.ZoneDeliveryModeModel;
import de.hybris.platform.order.CartService;
import de.hybris.platform.servicelayer.config.ConfigurationService;
import de.hybris.platform.storelocator.model.PointOfServiceModel;
import de.hybris.platform.util.PriceValue;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Required;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


public class DefaultAcerchemCheckoutFacade extends DefaultCheckoutFacade implements AcerchemCheckoutFacade {

    //自提
    private final String DELIVERY_MENTION = "DELIVERY_MENTION";
    //送货
    private final String DELIVERY_GROSS = "DELIVERY_GROSS";

    private final String DELIVERY_MENTION_FEE ="delivery.mention.storage.fee";

    @Resource
    private CartService cartService;
    @Resource
    private AcerchemDeliveryService acerchemDeliveryService;
    @Resource
    private ConfigurationService configurationService;
    @Resource
    private AcerchemTrayFacade acerchemTrayFacade;

    @Override
    public void validateCartAddress(CountryData countryData) throws AcerchemOrderException{
        //1.cartEntry's pos address match current address
        CartModel cartModel = cartService.getSessionCart();

        String countryIsoCode = countryData.getIsocode();
        if (CollectionUtils.isNotEmpty(cartModel.getEntries())){
            for (AbstractOrderEntryModel aoe : cartModel.getEntries()){
                PointOfServiceModel pos = aoe.getDeliveryPointOfService();
                if (pos == null){
                    throw new AcerchemOrderException("当前提货点为空.");
                }
                if (pos.getDeliveryZone()!=null&&pos.getDeliveryZone().getCountries()!=null) {
                    Set<CountryModel> countrys = pos.getDeliveryZone().getCountries();
                    boolean isContains = countrys.stream().filter(countryModel -> countryIsoCode.equals(countryModel.getIsocode())).collect(Collectors.toList()).size()>0;
                    if (!isContains){
                        throw new AcerchemOrderException("当前地址不在配送范围内.");
                    }
                }
            }
        }
    }


    public List<? extends DeliveryModeData> getSupportedDeliveryModes() {
        final List<DeliveryModeData> result = new ArrayList<DeliveryModeData>();
        final CartModel cartModel = getCart();
        if (cartModel != null)
        {
            for (final DeliveryModeModel deliveryModeModel : acerchemDeliveryService.getSupportedDeliveryModeListForOrder())
            {
                result.add(this.convert(deliveryModeModel));
            }
        }
        return result;
    }

    protected DeliveryModeData convert(final DeliveryModeModel deliveryModeModel){
        if (deliveryModeModel instanceof ZoneDeliveryModeModel)
        {
            final ZoneDeliveryModeModel zoneDeliveryModeModel = (ZoneDeliveryModeModel) deliveryModeModel;
            final CartModel cartModel = getCart();
            if (cartModel != null)
            {
                final ZoneDeliveryModeData zoneDeliveryModeData = getZoneDeliveryModeConverter().convert(zoneDeliveryModeModel);

                PriceValue deliveryCost = null;
                //自提运费和存储费用改造
                if (DELIVERY_MENTION.equals(deliveryModeModel.getCode())){

                    String deliveryMetionPrice = configurationService.getConfiguration().getString(DELIVERY_MENTION_FEE);

                    double fee = deliveryMetionPrice!=null?Integer.valueOf(deliveryMetionPrice):0.0d;

                    deliveryCost = new PriceValue(cartModel.getCurrency().getIsocode(), fee, true);

                }else if (DELIVERY_GROSS.equals(deliveryModeModel.getCode())){
                    double fee = 0;
                    try {
                        fee = acerchemTrayFacade.getTotalPriceForCart();
                    } catch (AcerchemOrderException e) {
                        e.printStackTrace();
                    }

                    deliveryCost = new PriceValue(cartModel.getCurrency().getIsocode(), fee, true);

                }else{
                    deliveryCost = getDeliveryService().getDeliveryCostForDeliveryModeAndAbstractOrder(
                            deliveryModeModel, cartModel);
                }

                if (deliveryCost != null)
                {
                    zoneDeliveryModeData.setDeliveryCost(getPriceDataFactory().create(PriceDataType.BUY,
                            BigDecimal.valueOf(deliveryCost.getValue()), deliveryCost.getCurrencyIso()));
                }
                return zoneDeliveryModeData;
            }
            return null;
        }
        return getDeliveryModeConverter().convert(deliveryModeModel);
    }
}

