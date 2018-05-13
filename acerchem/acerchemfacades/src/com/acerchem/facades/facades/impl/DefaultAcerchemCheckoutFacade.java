package com.acerchem.facades.facades.impl;

import com.acerchem.core.enums.CreditAccountStatusEnum;
import com.acerchem.core.model.CountryTrayFareConfModel;
import com.acerchem.core.model.CustomerCreditAccountModel;
import com.acerchem.core.service.AcerchemDeliveryService;
import com.acerchem.core.service.AcerchemTrayService;
import com.acerchem.facades.facades.AcerchemCheckoutFacade;
import com.acerchem.facades.facades.AcerchemOrderException;
import com.acerchem.facades.facades.AcerchemTrayFacade;
import com.acerchem.facades.product.data.PaymentModeData;
import com.acerchem.service.customercreditaccount.DefaultCustomerCreditAccountService;
import de.hybris.platform.commercefacades.order.data.*;
import de.hybris.platform.commercefacades.order.impl.DefaultCheckoutFacade;
import de.hybris.platform.commercefacades.product.PriceDataFactory;
import de.hybris.platform.commercefacades.product.data.PriceData;
import de.hybris.platform.commercefacades.product.data.PriceDataType;
import de.hybris.platform.commercefacades.product.data.ProductData;
import de.hybris.platform.commercefacades.user.data.AddressData;
import de.hybris.platform.commercefacades.user.data.CountryData;
import de.hybris.platform.commercefacades.user.data.RegionData;
import de.hybris.platform.commerceservices.service.data.CommerceCheckoutParameter;
import de.hybris.platform.core.model.c2l.CountryModel;
import de.hybris.platform.core.model.c2l.RegionModel;
import de.hybris.platform.core.model.order.AbstractOrderEntryModel;
import de.hybris.platform.core.model.order.AbstractOrderModel;
import de.hybris.platform.core.model.order.CartEntryModel;
import de.hybris.platform.core.model.order.CartModel;
import de.hybris.platform.core.model.order.OrderModel;
import de.hybris.platform.core.model.order.delivery.DeliveryModeModel;
import de.hybris.platform.core.model.order.payment.PaymentModeModel;
import de.hybris.platform.core.model.product.ProductModel;
import de.hybris.platform.core.model.user.AddressModel;
import de.hybris.platform.core.model.user.CustomerModel;
import de.hybris.platform.core.model.user.UserModel;
import de.hybris.platform.deliveryzone.model.ZoneDeliveryModeModel;
import de.hybris.platform.order.*;
import de.hybris.platform.order.exceptions.CalculationException;
import de.hybris.platform.ordersplitting.model.StockLevelModel;
import de.hybris.platform.servicelayer.config.ConfigurationService;
import de.hybris.platform.servicelayer.dto.converter.Converter;
import de.hybris.platform.stock.StockService;
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
import java.util.*;
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
    @Resource
    private Converter<AddressModel, AddressData> addressConverter;
    @Resource
    private PriceDataFactory priceDataFactory;
    @Resource
    private StockService stockService;
    @Resource
    private CalculationService calculationService;

    
    @Resource
	private AcerchemTrayService acerchemTrayService;
    
    @Resource
    private DefaultCustomerCreditAccountService defaultCustomerCreditAccountService;

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
        if (cartModel != null)
        {
            DeliveryModeData deliveryModeData = getDeliveryModeConverter().convert(deliveryModeModel);
           PriceValue deliveryCost = null;
            if (DELIVERY_MENTION.equals(deliveryModeModel.getCode())){
                deliveryCost = new PriceValue(cartModel.getCurrency().getIsocode(), 0.0d, true);
            }else if (DELIVERY_GROSS.equals(deliveryModeModel.getCode())){
                BigDecimal fee = BigDecimal.valueOf(0.0d);
                //add by taolq
                //fee =  BigDecimal.valueOf(acerchemTrayFacade.getTotalPriceForCart(cartModel));
                deliveryCost = new PriceValue(cartModel.getCurrency().getIsocode(), fee.doubleValue(), true);
            }

            if (deliveryCost != null)
            {
                deliveryModeData.setDeliveryCost(getPriceDataFactory().create(PriceDataType.BUY,
                        BigDecimal.valueOf(deliveryCost.getValue()), deliveryCost.getCurrencyIso()));
            }
          return deliveryModeData;
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

                boolean flag = getCommerceCheckoutService().setDeliveryMode(parameter);
                if (deliveryModeCode.equals("DELIVERY_MENTION")){
                    AddressModel addressModel = cartModel.getEntries().get(0).getDeliveryPointOfService().getAddress();
                    cartModel.setDeliveryAddress(addressModel);
                }else{
                    List<AddressModel> deliveryAddresses = getDeliveryService().getSupportedDeliveryAddressesForOrder(cartModel,true);
                    if (deliveryAddresses !=null && deliveryAddresses.size()>0){
                        cartModel.setDeliveryAddress(deliveryAddresses.get(0));
                    }
                }
                //促销那块会把操作费，存储费不加上，在此处计算总价格和单价
                recalculateCartTotalPrice(cartModel);

                return flag;

            }
        }
        return false;
    }

    private void recalculateCartTotalPrice(CartModel cartModel) {
        double total =cartModel.getTotalPrice();
        //促销那块会把运费加上，这边需要重新判断一下
        if(cartModel.getAllPromotionResults()!=null
                &&cartModel.getAllPromotionResults().size()>0){

        }else if (cartModel.getDeliveryCost()!=null){
            total = total +cartModel.getDeliveryCost().doubleValue();
        }
        if(cartModel.getOperateCost()!=null){
            total = total +cartModel.getOperateCost().doubleValue();
        }
        if (cartModel.getStorageCost()!=null){
            total = total +cartModel.getStorageCost().doubleValue();
        }
        cartModel.setTotalPrice(total);

        getModelService().save(cartModel);
    }

    @Override
    public List<CardTypeData> getSupportedCardTypes(String selectedDeliveryModeCode)
    {
        List<CardTypeData> cardTypeDataList = new ArrayList<>();
        if (selectedDeliveryModeCode!=null){
            DeliveryModeModel deliveryModeModel = deliveryModeService.getDeliveryModeForCode(selectedDeliveryModeCode);
           if (deliveryModeModel!=null&&deliveryModeModel.getSupportedPaymentModes()!=null){
               for (PaymentModeModel paymentModeModel : deliveryModeModel.getSupportedPaymentModes()){
                   CustomerCreditAccountModel customerCreditAccountModel = defaultCustomerCreditAccountService.getCustomerCreditAccount();
                   if (customerCreditAccountModel== null && paymentModeModel.getCode().equals("CreditPayment")) {
                      //信用账户为空就不存在data里
                   }else if (customerCreditAccountModel!=null&& CreditAccountStatusEnum.LOCKED.equals(customerCreditAccountModel.getStatus())){

                   }else{
                       CardTypeData cardTypeData = new CardTypeData();
                       cardTypeData.setCode(paymentModeModel.getCode());
                       cardTypeData.setName(paymentModeModel.getName());
                       cardTypeDataList.add(cardTypeData);
                   }
               }
           }
        }
        return cardTypeDataList;
    }


    @Override
    public void setPaymentDetail(final String paymentInfoId) throws AcerchemOrderException {
        validateParameterNotNullStandardMessage("paymentInfoId", paymentInfoId);

        if (StringUtils.isNotBlank(paymentInfoId))
        {
            PaymentModeModel paymentModeModel = paymentModeService.getPaymentModeForCode(paymentInfoId);
            final CartModel cartModel = getCartModel();
            
            final CartData cartData = getCheckoutCart();
            
            if(getDeliveryModes().getCode().equalsIgnoreCase("DELIVERY_GROSS")){
            	cartModel.setTotalPrice(cartData.getTotalPrice().getValue().doubleValue()+cartModel.getDeliveryCost());
            } else {
            	cartModel.setTotalPrice(cartData.getTotalPrice().getValue().doubleValue());
            }
            
            if (paymentModeModel.getCode().equals("CreditPayment")) {
                validateCustomerCredit(cartModel);
            }
            
            if (paymentModeModel != null)
            {
               cartModel.setPaymentMode(paymentModeModel);
               getModelService().save(cartModel);
              //  return true;
            }
           
        }
    }

    @Override
    public boolean savePickUpDateForOrder(String pickUpDate) {
        if (StringUtils.isNotBlank(pickUpDate))
        {
            final CartModel cartModel = getCart();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
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

    
	/*public DeliveryModeData getDeliveryMode()
	{
		return this.getDeliveryMode();
	} */
    @Override
	public DeliveryModeData getDeliveryModes()
	{
		return getDeliveryMode();
	}


    @Override
    public CartData getCheckoutCart()
    {
        final CartData cartData = getCartFacade().getSessionCart();
        final CartModel cartModel = getCartService().getSessionCart();
        
        double total =  0;
        if (cartData != null)
        {
            cartData.setDeliveryAddress(this.getDeliveryAddress());
                        
            cartData.setDeliveryMode(null);
            cartData.setPaymentModeData(getPaymentModeData());
            cartData.setPaymentInfo(getPaymentDetails());

            if (cartModel.getStorageCost()!=null){
            	
            	total +=cartModel.getStorageCost();
                cartData.setStorageCost(priceDataFactory.create(PriceDataType.BUY,
                        BigDecimal.valueOf(cartModel.getStorageCost().doubleValue()), cartModel.getCurrency().getIsocode()));
            }
            if (cartModel.getOperateCost()!=null){
            	total+=cartModel.getOperateCost();
                cartData.setOperateCost(priceDataFactory.create(PriceDataType.BUY,
                        BigDecimal.valueOf(cartModel.getOperateCost().doubleValue()), cartModel.getCurrency().getIsocode()));
            }
            if(cartModel.getPickUpDate()!=null){
        	  SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        	  cartData.setPickUpdate(sdf.format(cartModel.getPickUpDate()));
            }

            setOrderDeliveryDays(cartData, cartModel);
            
            double subTotal = 0;

            for (OrderEntryData orderEntryData: cartData.getEntries()){
              //  BigDecimal basePrice = orderEntryData.getTotalPrice().getValue().divide(BigDecimal.valueOf(orderEntryData.getQuantity()));
            	
            	BigDecimal basePrice = orderEntryData.getBasePrice().getValue();
                PriceData promotionBasePrice = priceDataFactory.create(PriceDataType.BUY,
                        BigDecimal.valueOf(basePrice.doubleValue()), cartModel.getCurrency().getIsocode());
                orderEntryData.setPromotionBasePrice(promotionBasePrice);
                
                
                String netWeight = orderEntryData.getProduct().getNetWeight();
            	
            	long items = orderEntryData.getQuantity();
            	
            	//BigDecimal basePrice = orderEntryData.getBasePrice().getValue();
            	long totalWeight = items* Long.valueOf(netWeight);
            	//Double totalPrice = Long.valueOf(basePrice)*totalWeight;
            	
            	Double totalPrice =basePrice.doubleValue()*totalWeight;
            	
            	subTotal += totalPrice;
            	
            	orderEntryData.setTotalPrice(createPrice(cartModel, totalPrice));
            }
            
            total += subTotal;
          
            cartData.setSubTotal(createPrice(cartModel, subTotal));
            cartData.setTotalPrice(createPrice(cartModel, total));
            
            
        }
        return cartData;
    }

    private void setOrderDeliveryDays(CartData cartData, CartModel cartModel) {
        List<Integer> deliveryDayList =new ArrayList<>();
        if (cartModel.getEntries()!=null && cartModel.getEntries().size()>0){
            boolean isUsefutureStock = cartModel.getEntries().get(0).getIsUseFutureStock();
            cartData.setIsUseFutureStock(isUsefutureStock);
            for (AbstractOrderEntryModel aoe: cartModel.getEntries()){
                if (isUsefutureStock){
                    StockLevelModel stockLevelModel = stockService.getStockLevel(aoe.getProduct(),aoe.getDeliveryPointOfService().getWarehouses().get(0));
                    deliveryDayList.add(stockLevelModel.getPreOrderReleaseDay());
                }else{
                    StockLevelModel stockLevelModel = stockService.getStockLevel(aoe.getProduct(),aoe.getDeliveryPointOfService().getWarehouses().get(0));
                    deliveryDayList.add(stockLevelModel.getAvaPreOrderReleaseDay());
                }
            }
            if (isUsefutureStock){
                cartData.setDeliveryDays(Collections.max(deliveryDayList));
            }else{
                cartData.setDeliveryDays(Collections.min(deliveryDayList));
            }
        }
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
	public void apportionmentCartPrice(OrderModel orderModel){
        if (!ObjectUtils.isEmpty(orderModel)){

            //总托盘比例
            BigDecimal totalUnitCalculateRato = BigDecimal.ZERO;
            for (AbstractOrderEntryModel aoe: orderModel.getEntries()){
                Double entryUnitCalculateRato = Double.valueOf(aoe.getProduct().getUnitCalculateRato());
                totalUnitCalculateRato = totalUnitCalculateRato.add(BigDecimal.valueOf(entryUnitCalculateRato));
            }
            //总附加费用
            BigDecimal totalAdditionalFee = BigDecimal.ZERO;
            if (orderModel.getDeliveryCost()!=null){
                totalAdditionalFee = BigDecimal.valueOf(orderModel.getDeliveryCost());
            }
            if (orderModel.getOperateCost()!=null){
                totalAdditionalFee = totalAdditionalFee.add(BigDecimal.valueOf(orderModel.getOperateCost()));
            }
            if (orderModel.getStorageCost()!=null){
                totalAdditionalFee = totalAdditionalFee.add(BigDecimal.valueOf(orderModel.getStorageCost()));
            }

            int size = orderModel.getEntries().size();
            //
            BigDecimal remainTotalDeliveryCost =totalAdditionalFee;

            for (int i =0 ; i<size ; i++){
                AbstractOrderEntryModel aoe = orderModel.getEntries().get(i);
                Double basePrice = getAbstractOrderEntryBasePrice(aoe);
                Double totalPrice = aoe.getTotalPrice();

                BigDecimal entryTotalAdditionalFee = BigDecimal.ZERO;
               if (i == size-1) {
                   entryTotalAdditionalFee = remainTotalDeliveryCost;
               }else {

                   Double entryUnitCalculateRato = Double.valueOf(aoe.getProduct().getUnitCalculateRato());
                   //计算比例
                   BigDecimal proportion = new BigDecimal(entryUnitCalculateRato).divide(totalUnitCalculateRato, BigDecimal.ROUND_CEILING, BigDecimal.ROUND_HALF_UP);
                   //计算entry total 运费
                   entryTotalAdditionalFee = proportion.multiply(totalAdditionalFee);

                   //剩余运费
                   remainTotalDeliveryCost = totalAdditionalFee.subtract(entryTotalAdditionalFee);

               }
               BigDecimal quantity = BigDecimal.valueOf(aoe.getQuantity());
                //附加费单价
               BigDecimal entryDeliveryBasePrice = entryTotalAdditionalFee.divide(quantity, BigDecimal.ROUND_HALF_UP, BigDecimal.ROUND_HALF_UP);
               Double baseRealPrice = BigDecimal.valueOf(basePrice).add(entryDeliveryBasePrice).doubleValue();
               aoe.setBaseRealPrice(baseRealPrice);
                //附加费行总价
               Double totalRealPrice = BigDecimal.valueOf(totalPrice).add(entryTotalAdditionalFee).doubleValue();
               aoe.setBasePrice(basePrice);
               aoe.setTotalRealPrice(totalRealPrice);

            }
            BigDecimal orderTotalPrice = totalAdditionalFee.add(BigDecimal.valueOf(orderModel.getTotalPrice()));
            orderModel.setTotalPrice(orderTotalPrice.doubleValue());
            getModelService().saveAll(orderModel.getEntries());
            getModelService().saveAll(orderModel);
        }
    }

    private Double getAbstractOrderEntryBasePrice (AbstractOrderEntryModel aoe){
        BigDecimal entryTotalPrice = BigDecimal.valueOf(aoe.getTotalPrice());
        BigDecimal quantity = BigDecimal.valueOf(aoe.getQuantity());
        BigDecimal baseRealPrice = entryTotalPrice.divide(quantity,BigDecimal.ROUND_CEILING,BigDecimal.ROUND_HALF_UP);
        return baseRealPrice.doubleValue();
    }

    @Override
    public List<? extends AddressData> getDeliveryAddresses() // NOSONAR
    {
        //自提时取pos的地址
        PointOfServiceModel pos = getCart().getEntries().get(0).getDeliveryPointOfService();
        List<AddressData> deliveryAddresses = null;

        if (pos !=null && pos.getAddress()!=null){
            deliveryAddresses = new ArrayList<>();
            AddressModel addressModel = pos.getAddress();
            AddressData addressData = addressConverter.convert(addressModel);
            deliveryAddresses.add(addressData);
        }

        return deliveryAddresses == null ? Collections.<AddressData> emptyList() : deliveryAddresses;
    }
    
    
  private List<? extends AddressModel> getWareHoseAddresses() // NOSONAR
    {
        //自提时取pos的地址
        PointOfServiceModel pos = getCart().getEntries().get(0).getDeliveryPointOfService();
        List<AddressModel> deliveryAddresses = null;

        if (pos !=null && pos.getAddress()!=null){
            deliveryAddresses = new ArrayList<>();
            AddressModel addressModel = pos.getAddress();
            deliveryAddresses.add(addressModel);
        }

        return deliveryAddresses == null ? Collections.<AddressModel> emptyList() : deliveryAddresses;
    }

    @Override
    public OrderData placeOrder() throws InvalidCartException {
        final CartModel cartModel = getCartModel();
        if (cartModel != null)
        {
            if (cartModel.getUser().equals(getCurrentUserForCheckout()) || getCheckoutCustomerStrategy().isAnonymousCheckout())
            {
                beforePlaceOrder(cartModel);
                final OrderModel orderModel = placeOrder(cartModel);
                apportionmentCartPrice(orderModel);
                afterPlaceOrder(cartModel, orderModel);
                if (orderModel != null)
                {
                    return getOrderConverter().convert(orderModel);
                }
            }
        }
        return null;
    }

    @Override
    protected AddressData getDeliveryAddress()
    {
        final CartModel cart = getCart();
        if (cart != null)
        {
            final AddressModel deliveryAddress = cart.getDeliveryAddress();
            if (deliveryAddress != null)
            {
                 return getAddressConverter().convert(deliveryAddress);
            }
        }
        return null;
    }

    private void validateCustomerCredit(CartModel cartModel) throws AcerchemOrderException {
        double totalPrice = cartModel.getTotalPrice();
        CustomerCreditAccountModel customerCreditAccountModel = defaultCustomerCreditAccountService.getCustomerCreditAccount();
        if (customerCreditAccountModel!=null && customerCreditAccountModel.getCreaditRemainedAmount()!=null){
            double creaditReaminAmount = customerCreditAccountModel.getCreaditRemainedAmount().doubleValue();
            if(totalPrice > creaditReaminAmount){
                throw new AcerchemOrderException("checkout.creditcard.limit");
            }
        }
    }

    @Override
    public boolean setDeliveryAddress(final AddressData addressData)
    {
        final CartModel cartModel = getCart();
        if (cartModel != null)
        {
            AddressModel addressModel = null;
            if (addressData != null)
            {
                addressModel = addressData.getId() == null ? createDeliveryAddressModel(addressData, cartModel)
                        : getDeliveryAddressModelForCode(addressData.getId());
            }

            cartModel.setDeliveryAddress(addressModel);
            getModelService().save(cartModel);
        }
        return false;
    }
    
    
    public PriceData createPrice(final CartModel cardModel, final Double val)
   	{
   		return getPriceDataFactory().create(PriceDataType.BUY, BigDecimal.valueOf(val.doubleValue()),
   				cardModel.getCurrency());
   	}


	@Override
	public CartModel getCartModel() {
		 final CartModel cartModel = getCartService().getSessionCart();
		 
		 
	            double deliveryCost=0;
	            
	            final CartData cartData = getCartFacade().getSessionCart();
	            
	            if (this.getDeliveryAddress() != null){
	            	 try {
						 deliveryCost=acerchemTrayFacade.getTotalPriceForCart(cartData, this.getDeliveryAddress());
					} catch (AcerchemOrderException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
	            	 
	            	
	            }
	            cartModel.setDeliveryCost(deliveryCost);
	            
	      if(cartModel.getDeliveryMode() != null && cartModel.getDeliveryMode().getCode().equalsIgnoreCase("DELIVERY_MENTION")){
	    	  cartModel.setDeliveryAddress(getWareHoseAddresses().get(0));
	      }
	            
		 
		 return cartModel;
	}
	
	
	public  Integer getTotalPriceForCart(CartData data){
		
		final CartModel cartModel = getCartService().getSessionCart();
	 	RegionData regionData = null;
	 	
	 	RegionModel regionModel = null;
		CountryTrayFareConfModel countryTrayFareConf  = null;
		//��������
		BigDecimal totalTrayAmount = BigDecimal.ZERO;
		if (data!=null){

			for (OrderEntryData aoe : data.getEntries()){

				if (aoe.getDeliveryPointOfService().getAddress()!=null) {
					regionData =data.getDeliveryAddress().getRegion();
				}
				ProductData productData = aoe.getProduct();
				//�Ȼ�ȡ���̱������ڼ�������
				String unitCalculateRato = productData.getUnitCalculateRato();
				if (ObjectUtils.isEmpty(unitCalculateRato)){
					
				}
				Long quantity = (aoe.getQuantity())*(Long.parseLong(aoe.getProduct().getNetWeight()));

				//��������
				BigDecimal entryTrayAmount = BigDecimal.valueOf(quantity).divide(new BigDecimal(unitCalculateRato),BigDecimal.ROUND_HALF_UP,BigDecimal.ROUND_DOWN);

				totalTrayAmount =totalTrayAmount.add(entryTrayAmount);
			}
		}
		if(regionData != null){
			regionModel =cartModel.getDeliveryAddress().getRegion();
			countryTrayFareConf = acerchemTrayService.getPriceByCountryAndTray(regionModel, (int) Math.ceil(totalTrayAmount.doubleValue()));
		}
		if(countryTrayFareConf != null){
			return countryTrayFareConf.getDeliveriedDay();
		}
		return 0;
	}


	
}

