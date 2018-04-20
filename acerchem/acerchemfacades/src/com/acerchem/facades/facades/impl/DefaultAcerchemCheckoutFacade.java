package com.acerchem.facades.facades.impl;

import com.acerchem.core.service.AcerchemDeliveryService;
import com.acerchem.facades.facades.AcerchemCheckoutFacade;
import com.acerchem.facades.facades.AcerchemOrderException;
import com.acerchem.facades.facades.AcerchemTrayFacade;
import com.acerchem.facades.product.data.PaymentModeData;
import de.hybris.platform.commercefacades.order.data.CardTypeData;
import de.hybris.platform.commercefacades.order.data.CartData;
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
import de.hybris.platform.core.model.order.payment.PaymentModeModel;
import de.hybris.platform.deliveryzone.model.ZoneDeliveryModeModel;
import de.hybris.platform.order.CartService;
import de.hybris.platform.order.DeliveryModeService;
import de.hybris.platform.order.PaymentModeService;
import de.hybris.platform.servicelayer.config.ConfigurationService;
import de.hybris.platform.storelocator.model.PointOfServiceModel;
import de.hybris.platform.util.PriceValue;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static de.hybris.platform.servicelayer.util.ServicesUtil.validateParameterNotNullStandardMessage;


public class DefaultAcerchemCheckoutFacade extends DefaultCheckoutFacade implements AcerchemCheckoutFacade {

    private static final Logger LOG = Logger.getLogger(DefaultAcerchemCheckoutFacade.class);

    //鑷彁
    private final String DELIVERY_MENTION = "DELIVERY_MENTION";
    //閫佽揣
    private final String DELIVERY_GROSS = "DELIVERY_GROSS";
    private final String DELIVERY_MENTION_FEE ="delivery.mention.storage.fee";
    private final String ORDER_OPERATION_FEE ="order.operation.fee";
    private final String ORDER_STANDARD_CRITICAL_FEE ="order.standard.critical.price";
    //榛樿瀛樺偍璐�
    private final String defaultStorageFee = "30";
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
    @Resource
    private DeliveryModeService deliveryModeService;
    @Resource
    private PaymentModeService paymentModeService;

    @Override
    public void validateCartAddress(CountryData countryData) throws AcerchemOrderException{
        //1.cartEntry's pos address match current address
        CartModel cartModel = cartService.getSessionCart();

        String countryIsoCode = countryData.getIsocode();
        if (CollectionUtils.isNotEmpty(cartModel.getEntries())){
            for (AbstractOrderEntryModel aoe : cartModel.getEntries()){
                PointOfServiceModel pos = aoe.getDeliveryPointOfService();
                if (pos == null){
                    throw new AcerchemOrderException("current pos Address is null");
                }
                if (pos.getDeliveryZone()!=null&&pos.getDeliveryZone().getCountries()!=null) {
                    Set<CountryModel> countrys = pos.getDeliveryZone().getCountries();
                    boolean isContains = countrys.stream().filter(countryModel -> countryIsoCode.equals(countryModel.getIsocode())).collect(Collectors.toList()).size()>0;
                    if (!isContains){
                        throw new AcerchemOrderException("The current address is not within the scope of delivery.");
                    }
                }
            }
        }
    }


    public List<? extends DeliveryModeData> getAllDeliveryModes() throws AcerchemOrderException {
        final List<DeliveryModeData> result = new ArrayList<DeliveryModeData>();
        final CartModel cartModel = getCart();
        if (cartModel != null)
        {
            for (final DeliveryModeModel deliveryModeModel : acerchemDeliveryService.getSupportedDeliveryModeListForOrder())
            {
                result.add(this.convertDeliveyMode(deliveryModeModel));
            }
        }
        return result;
    }

    protected DeliveryModeData convertDeliveyMode(final DeliveryModeModel deliveryModeModel) throws AcerchemOrderException{

        final CartModel cartModel = getCart();
        if (deliveryModeModel instanceof ZoneDeliveryModeModel)
        {
            final ZoneDeliveryModeModel zoneDeliveryModeModel = (ZoneDeliveryModeModel) deliveryModeModel;
            if (cartModel != null)
            {
                final ZoneDeliveryModeData zoneDeliveryModeData = getZoneDeliveryModeConverter().convert(zoneDeliveryModeModel);

                PriceValue deliveryCost  = getDeliveryService().getDeliveryCostForDeliveryModeAndAbstractOrder(
                        deliveryModeModel, cartModel);;
                if (deliveryCost != null)
                {
                    zoneDeliveryModeData.setDeliveryCost(getPriceDataFactory().create(PriceDataType.BUY,
                            BigDecimal.valueOf(deliveryCost.getValue()), deliveryCost.getCurrencyIso()));
                }
                return zoneDeliveryModeData;
            }
        }else{
            if (cartModel != null)
            {
                DeliveryModeData deliveryModeData = getDeliveryModeConverter().convert(deliveryModeModel);
                double orderTotalPrice  = cartModel.getTotalPrice();
                String orderStandardFee = configurationService.getConfiguration().getString(ORDER_STANDARD_CRITICAL_FEE,defaultOrderStandardFee);
                BigDecimal operationFee = BigDecimal.ZERO;
                if (orderTotalPrice <= Double.valueOf(orderStandardFee)){
                    String orderOperationFee = configurationService.getConfiguration().getString(ORDER_OPERATION_FEE,defaultOrderOperationFee);
                    operationFee = operationFee.add(BigDecimal.valueOf(Double.valueOf(orderOperationFee)));
                }

                PriceValue deliveryCost = null;
                if (DELIVERY_MENTION.equals(deliveryModeModel.getCode())){
                    String deliveryMetionPrice = configurationService.getConfiguration().getString(DELIVERY_MENTION_FEE,defaultStorageFee);
                    BigDecimal fee = operationFee.add(BigDecimal.valueOf(Double.valueOf(deliveryMetionPrice)));

                    deliveryCost = new PriceValue(cartModel.getCurrency().getIsocode(), fee.doubleValue(), true);

                }else if (DELIVERY_GROSS.equals(deliveryModeModel.getCode())){
                    BigDecimal fee = BigDecimal.valueOf(0.0d);
                    fee =  BigDecimal.valueOf(acerchemTrayFacade.getTotalPriceForCart());
                    fee = operationFee.add(fee);
                    deliveryCost = new PriceValue(cartModel.getCurrency().getIsocode(), fee.doubleValue(), true);
                }

                if (deliveryCost != null)
                {
                    deliveryModeData.setDeliveryCost(getPriceDataFactory().create(PriceDataType.BUY,
                            BigDecimal.valueOf(deliveryCost.getValue()), deliveryCost.getCurrencyIso()));
                }
                return deliveryModeData;
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

    @Override
    public List<CardTypeData> getSupportedCardTypes(String selectedDeliveryModeCode)
    {
        List<CardTypeData> cardTypeDataList = new ArrayList<>();
        if (selectedDeliveryModeCode!=null){
            DeliveryModeModel deliveryModeModel = deliveryModeService.getDeliveryModeForCode(selectedDeliveryModeCode);
           if (deliveryModeModel!=null&&deliveryModeModel.getSupportedPaymentModes()!=null){
               for (PaymentModeModel paymentModeModel : deliveryModeModel.getSupportedPaymentModes()){
                   CardTypeData cardTypeData = new CardTypeData();
                   cardTypeData.setCode(paymentModeModel.getCode());
                   cardTypeData.setName(paymentModeModel.getName());
                   cardTypeDataList.add(cardTypeData);
               }
           }
        }
        return cardTypeDataList;
    }

    @Override
    public boolean setPaymentDetails(final String paymentInfoId)
    {
        validateParameterNotNullStandardMessage("paymentInfoId", paymentInfoId);

        if (StringUtils.isNotBlank(paymentInfoId))
        {
            PaymentModeModel paymentModeModel = paymentModeService.getPaymentModeForCode(paymentInfoId);
            final CartModel cartModel = getCart();
            if (paymentModeModel != null)
            {
               cartModel.setPaymentMode(paymentModeModel);
               getModelService().save(cartModel);
               return true;
            }
        }
        return false;
    }

    @Override
    public boolean savePickUpDateForOrder(String pickUpDate) {
        if (StringUtils.isNotBlank(pickUpDate))
        {
            final CartModel cartModel = getCart();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-DD");
            try {
                Date date=  sdf.parse(pickUpDate);
                cartModel.setPickUpDate(date);
                getModelService().save(cartModel);
                return true;
            } catch (ParseException e) {
                LOG.error("parse time error : " +e.getMessage());
            }
        }
        return false;
    }


    @Override
    public CartData getCheckoutCart()
    {
        final CartData cartData = getCartFacade().getSessionCart();
        if (cartData != null)
        {
            cartData.setDeliveryAddress(getDeliveryAddress());
            cartData.setDeliveryMode(getDeliveryMode());
            cartData.setPaymentModeData(getPaymentModeData());
            cartData.setPaymentInfo(getPaymentDetails());
        }
        return cartData;
    }

	@Override
	public PaymentModeData getPaymentModeData() {
		final CartModel cartModel = getCart();
		PaymentModeData paymentModeData = new PaymentModeData();
		PaymentModeModel paymentModeModel = cartModel.getPaymentMode();
		if(paymentModeModel!=null){
			paymentModeData .setCode(paymentModeModel.getCode());
			paymentModeData .setName(paymentModeModel.getName());
		}
		
		return paymentModeData;
	}

	//分摊价格
	public void ApportionmentCartPrice(CartModel cartModel){
        if (!ObjectUtils.isEmpty(cartModel)){

        }
    }
    
    
}

