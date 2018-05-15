package com.acerchem.facades.process.email.context;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Required;

import com.acerchem.facades.process.email.context.pojo.CustomerContactAddressOfEmailData;
import com.acerchem.facades.process.email.context.pojo.DeliveryNoteEmailContextPoJo;
import com.acerchem.facades.process.email.context.pojo.ProductItemDataOfEmail;
import com.acerchem.facades.process.email.context.pojo.ProductTotalDataOfEmail;

import de.hybris.platform.acceleratorservices.model.cms2.pages.EmailPageModel;
import de.hybris.platform.acceleratorservices.process.email.context.AbstractEmailContext;
import de.hybris.platform.basecommerce.model.site.BaseSiteModel;
import de.hybris.platform.commercefacades.coupon.data.CouponData;
import de.hybris.platform.commercefacades.order.data.ConsignmentData;
import de.hybris.platform.commercefacades.order.data.ConsignmentEntryData;
import de.hybris.platform.commercefacades.order.data.OrderData;
import de.hybris.platform.commercefacades.product.data.ProductData;
import de.hybris.platform.commercefacades.user.data.AddressData;
import de.hybris.platform.core.model.c2l.LanguageModel;
import de.hybris.platform.core.model.order.OrderModel;
import de.hybris.platform.core.model.user.CustomerModel;
import de.hybris.platform.orderprocessing.model.OrderProcessModel;
import de.hybris.platform.servicelayer.dto.converter.Converter;
import de.hybris.platform.servicelayer.user.ContactInfoService;

public class AcerChemDeliveryNoteEmailContext extends AbstractEmailContext<OrderProcessModel> {
	private Converter<OrderModel, OrderData> orderConverter;
	private OrderData orderData;
	private List<CouponData> giftCoupons;

	private String customerAddress;
	private CustomerContactAddressOfEmailData customerAddressData;
	private DeliveryNoteEmailContextPoJo append;
	private CustomerModel customerModel;  
	
	private String contactUser;
	private String contactMobile;
	
	@Resource
	private ContactInfoService contactInfoService;

	@Override
	public void init(final OrderProcessModel orderProcessModel, final EmailPageModel emailPageModel) {
		super.init(orderProcessModel, emailPageModel);
		orderData = getOrderConverter().convert(orderProcessModel.getOrder());

		//orderData.getWaitDeliveiedDate();
		// orderData.getConsignments()
		// List<ConsignmentData>
		giftCoupons = orderData.getAppliedOrderPromotions().stream()
				.filter(x -> CollectionUtils.isNotEmpty(x.getGiveAwayCouponCodes()))
				.flatMap(p -> p.getGiveAwayCouponCodes().stream()).collect(Collectors.toList());

		initCustomerAddress(orderProcessModel);
		initAppend();
		customerModel = getCustomer(orderProcessModel);
		//initContactInfo();
	}

	@Override
	protected BaseSiteModel getSite(final OrderProcessModel orderProcessModel) {
		return orderProcessModel.getOrder().getSite();
	}

	@Override
	protected CustomerModel getCustomer(final OrderProcessModel orderProcessModel) {
		return (CustomerModel) orderProcessModel.getOrder().getUser();
	}

	protected Converter<OrderModel, OrderData> getOrderConverter() {
		return orderConverter;
	}

	@Required
	public void setOrderConverter(final Converter<OrderModel, OrderData> orderConverter) {
		this.orderConverter = orderConverter;
	}

	public OrderData getOrder() {
		return orderData;
	}

	@Override
	protected LanguageModel getEmailLanguage(final OrderProcessModel orderProcessModel) {
		return orderProcessModel.getOrder().getLanguage();
	}

	public List<CouponData> getCoupons() {
		return giftCoupons;
	}

	private void initCustomerAddress(final OrderProcessModel orderProcessModel) {

		final String address = "";
		final CustomerContactAddressOfEmailData addressData = new CustomerContactAddressOfEmailData();
//		CustomerModel customer = getCustomer(orderProcessModel);
//		if (customer != null) {
//			Collection<AddressModel> addrs = customer.getAddresses();
//			address = AcerChemEmailContextUtils.getCustomerContactAddress(addrs,"SHIPPING");
//			addressData = AcerChemEmailContextUtils.getCustomerContactAddressData(addrs,"SHIPPING");
//			
//		}

		final AddressData  orderAddressData = orderData.getDeliveryAddress();
		
		if(orderAddressData != null){
			addressData.setCountry(orderAddressData.getCountry().getName());
			addressData.setRegion(orderAddressData.getRegion().getName());
			addressData.setTown(orderAddressData.getTown());
			
			addressData.setContactPhone(orderAddressData.getPhone());
			addressData.setContactUser(orderAddressData.getLastName());
		}
		
		this.customerAddress = address;
		this.customerAddressData = addressData;

		this.contactMobile = addressData.getContactPhone();
		this.contactUser = addressData.getContactUser();
	}

	public String getCustomerAddress() {
		return this.customerAddress;
	}

	// initialize append data
	public void initAppend() {
		final DeliveryNoteEmailContextPoJo pojo = new DeliveryNoteEmailContextPoJo();
		// todo ...

		// add list
		final List<ProductItemDataOfEmail> list = new ArrayList<ProductItemDataOfEmail>();
		final List<ConsignmentData> consignments = orderData.getConsignments();
		
//		List<ConsignmentEntryData> consignmentEntries = orderData.getConsignments().stream()
//				.filter(x->CollectionUtils.isNotEmpty(x.getEntries()))
//				.flatMap(y->y.getEntries().stream()).collect(Collectors.toList());
		
		String tempName = "";

		long quantity = 0;
		long net = 0;
		long gross = 0;
		long itemQuantity = 0;
		long itemNet = 0;
		long itemGross = 0;

		int batchNum = 0;
		if (CollectionUtils.isNotEmpty(consignments)) {
			for (final ConsignmentData consignment : consignments) {

				final List<ConsignmentEntryData> entryLists = consignment.getEntries();

				if (entryLists != null) {
					for (final ConsignmentEntryData consignEntry : entryLists) {

						final ProductData product = consignEntry.getOrderEntry().getProduct();

						final ProductItemDataOfEmail pie = new ProductItemDataOfEmail();

						if (StringUtils.isNotBlank(tempName)) {
							if (!tempName.equals(product.getName())) {
								final ProductItemDataOfEmail totalPie = new ProductItemDataOfEmail();

								totalPie.setProductName("subTotal");
								totalPie.setGrossWeight(String.valueOf(itemGross));
								totalPie.setNetWeight(String.valueOf(itemNet));
								totalPie.setQuantity(String.valueOf(itemQuantity));
								totalPie.setTotal(true);

								list.add(totalPie);
								itemQuantity = 0;
								itemNet = 0;
								itemGross = 0;
								batchNum ++;
							}
						}

						tempName = product.getName();
						pie.setProductCode(product.getCode());
						pie.setProductName(product.getName());
						pie.setQuantity(consignEntry.getQuantity().toString());
						pie.setBatchNo(StringUtils.defaultString(consignEntry.getBatchNum()," "));
						String tempNet = StringUtils.defaultString(product.getNetWeight(),"0");
						String tempGross = StringUtils.defaultString(product.getGrossWeight(),"0");
						if(!StringUtils.isNumeric(tempNet)){
							tempNet = "0";
						}
						if(!StringUtils.isNumeric(tempGross)){
							tempGross = "0";
						}
						pie.setNetWeight(tempNet);
						pie.setGrossWeight(tempGross);
						pie.setTotal(false);

						quantity += consignEntry.getQuantity();
						net += Long.parseLong(tempNet);
						gross += Long.parseLong(tempGross);
						itemQuantity += consignEntry.getQuantity();
						itemNet += Long.parseLong(tempNet);
						itemGross += Long.parseLong(tempGross);
						list.add(pie);

					}

				}
				
			}
			//add last item project
			if (batchNum > 0){
				final ProductItemDataOfEmail lastPie = new ProductItemDataOfEmail();

				lastPie.setProductName("subTotal");
				lastPie.setGrossWeight(String.valueOf(itemGross));
				lastPie.setNetWeight(String.valueOf(itemNet));
				lastPie.setQuantity(String.valueOf(itemQuantity));
				lastPie.setTotal(true);

				list.add(lastPie);
			}
		}

		pojo.setProductLists(list);

		// add total

		final ProductTotalDataOfEmail totalData = new ProductTotalDataOfEmail();
		totalData.setQuantity(String.valueOf(quantity));
		totalData.setNetWeight(String.valueOf(net));
		totalData.setGrossWeight(String.valueOf(gross));
		pojo.setTotalData(totalData);

		setAppend(pojo);

	}

	public DeliveryNoteEmailContextPoJo getAppend() {
		return append;
	}

	public void setAppend(final DeliveryNoteEmailContextPoJo append) {
		this.append = append;
	}

	/**
	 * @return the customerModel
	 */
	public CustomerModel getCustomer() {
		return customerModel;
	}

	/**
	 * @return the contactUser
	 */
	public String getContactUser() {
		return contactUser;
	}

	/**
	 * @return the contactMobile
	 */
	public String getContactMobile() {
		return contactMobile;
	}
	
//	public void initContactInfo(){
//		CustomerModel customer = this.getCustomer();
//		//Collection<AbstractContactInfoModel> coll = customer.getContactInfos();
//		
//		AbstractContactInfoModel contactInfo =  contactInfoService.getMainContactInfo(customer);
//		if (contactInfo != null){
//			UserModel curUser = contactInfo.getUser();
//			if (curUser != null  ){
//				this.setContactUser(curUser.getName());
//				
//				List<String> phones = AcerChemEmailContextUtils.getPhoneNumbers(curUser.getPhoneNumbers());
//				if(phones.size() > 0){
//					this.setContactMobile(phones.get(0));
//				}
//			}
//		}
//		
//	}

	/**
	 * @param contactUser the contactUser to set
	 */
	public void setContactUser(final String contactUser) {
		this.contactUser = contactUser;
	}

	/**
	 * @param contactMobile the contactMobile to set
	 */
	public void setContactMobile(final String contactMobile) {
		this.contactMobile = contactMobile;
	}

	public CustomerContactAddressOfEmailData getCustomerAddressData() {
		return customerAddressData;
	}

	public void setCustomerAddressData(final CustomerContactAddressOfEmailData customerAddressData) {
		this.customerAddressData = customerAddressData;
	}

}
