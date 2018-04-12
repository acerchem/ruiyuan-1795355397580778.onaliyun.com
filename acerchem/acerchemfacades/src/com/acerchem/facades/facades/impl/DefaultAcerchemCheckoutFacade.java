package com.acerchem.facades.facades.impl;

import com.acerchem.core.service.AcerchemDeliveryService;
import com.acerchem.facades.facades.AcerchemCheckoutFacade;
import com.acerchem.facades.facades.AcerchemOrderException;
import com.acerchem.facades.facades.AcerchemTrayFacade;
import de.hybris.platform.commercefacades.order.data.DeliveryModeData;
import de.hybris.platform.commercefacades.order.data.ZoneDeliveryModeData;
import de.hybris.platform.commercefacades.order.impl.DefaultCheckoutFacade;
import de.hybris.platform.commercefacades.product.data.PriceDataType;
import de.hybris.platform.commercefacades.user.data.CountryData;
import de.hybris.platform.commerceservices.service.data.CommerceCheckoutParameter;
import de.hybris.platform.core.model.c2l.CountryModel;
import de.hybris.platform.core.model.order.AbstractOrderEntryModel;
import de.hybris.platform.core.model.order.CartModel;
import de.hybris.platform.core.model.order.delivery.DeliveryModeModel;
import de.hybris.platform.deliveryzone.model.ZoneDeliveryModeModel;
import de.hybris.platform.order.CartService;
import de.hybris.platform.servicelayer.config.ConfigurationService;
import de.hybris.platform.storelocator.model.PointOfServiceModel;
import de.hybris.platform.util.PriceValue;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.logging.Log;
import org.apache.log4j.Logger;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

import static de.hybris.platform.servicelayer.util.ServicesUtil.validateParameterNotNullStandardMessage;


public class DefaultAcerchemCheckoutFacade extends DefaultCheckoutFacade implements AcerchemCheckoutFacade {

    private static final Logger LOG = Logger.getLogger(DefaultAcerchemCheckoutFacade.class);

    //自提
    private final String DELIVERY_MENTION = "DELIVERY_MENTION";
    //送货
    private final String DELIVERY_GROSS = "DELIVERY_GROSS";
    private final String DELIVERY_MENTION_FEE ="delivery.mention.storage.fee";
    private final String ORDER_OPERATION_FEE ="order.operation.fee";
    private final String ORDER_STANDARD_CRITICAL_FEE ="order.standard.critical.price";
    //默认存储费
    private final String defaultStorageFee = "0";
    private final String defaultOrderOperationFee = "100";
    private final String defaultOrderStandardFee = "10000";



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

                double orderTotalPrice  = cartModel.getTotalPrice();

                String orderStandardFee = configurationService.getConfiguration().getString(ORDER_STANDARD_CRITICAL_FEE,defaultOrderStandardFee);

                BigDecimal operationFee = BigDecimal.ZERO;
                if (orderTotalPrice <= Double.valueOf(orderStandardFee)){
                    String orderOperationFee = configurationService.getConfiguration().getString(ORDER_OPERATION_FEE,defaultOrderOperationFee);
                    operationFee = operationFee.add(BigDecimal.valueOf(Double.valueOf(orderOperationFee)));
                }

                PriceValue deliveryCost = null;
                //自提运费和存储费用改造
                if (DELIVERY_MENTION.equals(deliveryModeModel.getCode())){
                    //自提费
                    String deliveryMetionPrice = configurationService.getConfiguration().getString(DELIVERY_MENTION_FEE,defaultStorageFee);

                    //操作费+自提费
                    BigDecimal fee = operationFee.add(BigDecimal.valueOf(Double.valueOf(deliveryMetionPrice)));

                    deliveryCost = new PriceValue(cartModel.getCurrency().getIsocode(), fee.doubleValue(), true);

                }else if (DELIVERY_GROSS.equals(deliveryModeModel.getCode())){
                    BigDecimal fee = BigDecimal.valueOf(0.0d);
                    try {
                        //托盘运输费
                        fee =  BigDecimal.valueOf(acerchemTrayFacade.getTotalPriceForCart());
                        //操作费+托盘运输费
                        fee = operationFee.add(fee);
                    } catch (AcerchemOrderException e) {
                        e.printStackTrace();
                    }
                    deliveryCost = new PriceValue(cartModel.getCurrency().getIsocode(), fee.doubleValue(), true);
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

    @Override
    public boolean hasShippingItems()
    {   
        return true;
    }

    @Override
    public boolean setDeliveryMode(final String deliveryModeCode)
    {
        validateParameterNotNullStandardMessage("deliveryModeCode", deliveryModeCode);

        final CartModel cartModel = getCart();
        if (cartModel != null)
        {
            final DeliveryModeModel deliveryModeModel = getDeliveryService().getDeliveryModeForCode(deliveryModeCode);
            if (deliveryModeModel != null)
            {
                final CommerceCheckoutParameter parameter = createCommerceCheckoutParameter(cartModel, true);
                parameter.setDeliveryMode(deliveryModeModel);
                return getCommerceCheckoutService().setDeliveryMode(parameter);
            }
        }
        return false;
    }
}

