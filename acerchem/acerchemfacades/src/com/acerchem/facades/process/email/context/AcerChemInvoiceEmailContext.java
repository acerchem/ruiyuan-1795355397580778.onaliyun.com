package com.acerchem.facades.process.email.context;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.velocity.tools.generic.NumberTool;
import org.springframework.beans.factory.annotation.Required;

import com.acerchem.facades.process.email.context.pojo.AcerChemEmailContextUtils;
import com.acerchem.facades.process.email.context.pojo.CustomerContactAddressOfEmailData;
import com.acerchem.facades.process.email.context.pojo.InvoiceEmailContextPoJo;
import com.acerchem.facades.process.email.context.pojo.ProductItemDataOfEmail;
import com.acerchem.facades.process.email.context.pojo.ProductTotalDataOfEmail;

import de.hybris.platform.acceleratorservices.model.cms2.pages.EmailPageModel;
import de.hybris.platform.acceleratorservices.process.email.context.AbstractEmailContext;
import de.hybris.platform.basecommerce.model.site.BaseSiteModel;
import de.hybris.platform.commercefacades.coupon.data.CouponData;
import de.hybris.platform.commercefacades.order.data.ConsignmentData;
import de.hybris.platform.commercefacades.order.data.ConsignmentEntryData;
import de.hybris.platform.commercefacades.order.data.OrderData;
import de.hybris.platform.commercefacades.order.data.OrderEntryData;
import de.hybris.platform.commercefacades.product.data.ProductData;
import de.hybris.platform.commercefacades.storelocator.data.PointOfServiceData;
import de.hybris.platform.core.model.c2l.LanguageModel;
import de.hybris.platform.core.model.order.OrderModel;
import de.hybris.platform.core.model.user.AddressModel;
import de.hybris.platform.core.model.user.CustomerModel;
import de.hybris.platform.orderprocessing.model.OrderProcessModel;
import de.hybris.platform.servicelayer.dto.converter.Converter;
import de.hybris.platform.servicelayer.user.ContactInfoService;

/**
 * Velocity context for a Invoice notification email.
 */
public class AcerChemInvoiceEmailContext extends AbstractEmailContext<OrderProcessModel> {
	public static final String NUMBER_TOOL = "numberTool";

	private Converter<OrderModel, OrderData> orderConverter;
	private OrderData orderData;
	private List<CouponData> giftCoupons;

	private InvoiceEmailContextPoJo append;
	private String customerAddress;

	private String currency;
	private String paymentTerms;
	
	private CustomerModel customerModel;  
	
	private String warehouse;
	
	private String moneyToWords;
	private CustomerContactAddressOfEmailData customerAddressData;

	private String contactUser;
	private String contactMobile;
	
	//total vat 20%
	private String vatAmount;
	private String vatTotal;
	private String vatMoneyToWords;
	
	private String customerCompany;
	
	@Resource
	private ContactInfoService contactInfoService;
	
	@Override
	public void init(final OrderProcessModel orderProcessModel, final EmailPageModel emailPageModel) {
		super.init(orderProcessModel, emailPageModel);
		put(NUMBER_TOOL, new NumberTool());
		orderData = getOrderConverter().convert(orderProcessModel.getOrder());

		// orderData.getConsignments()
		// List<ConsignmentData>

		giftCoupons = orderData.getAppliedOrderPromotions().stream()
				.filter(x -> CollectionUtils.isNotEmpty(x.getGiveAwayCouponCodes()))
				.flatMap(p -> p.getGiveAwayCouponCodes().stream()).collect(Collectors.toList());

		currency = StringUtils.defaultString(orderData.getCurrency(), "");
		initCustomerAddress(orderProcessModel);
		initAppend();

		initPaymentTerms(orderProcessModel);
		
		customerModel = getCustomer(orderProcessModel);
		
		this.setCustomerCompany(customerModel.getCompanyName());
		
		String total = orderData.getTotalPrice().getValue().toString();
		moneyToWords = AcerChemEmailContextUtils.getMoneyOfWord(total,"$");
			
		//initContactInfo();
		initVatTotal();
		
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

	public InvoiceEmailContextPoJo getAppend() {
		return append;
	}

	private void setAppend(InvoiceEmailContextPoJo append) {
		this.append = append;
	}

	private void initCustomerAddress(final OrderProcessModel orderProcessModel) {

		String address = "";
		CustomerModel customer = getCustomer(orderProcessModel);
		CustomerContactAddressOfEmailData addressData = new CustomerContactAddressOfEmailData();
		if (customer != null) {
			Collection<AddressModel> addrs = customer.getAddresses();
			address = AcerChemEmailContextUtils.getCustomerContactAddress(addrs);
			addressData = AcerChemEmailContextUtils.getCustomerContactAddressData(addrs);
		}

		setCustomerAddressData(addressData);
		setCustomerAddress(address);
		this.contactMobile = addressData.getContactPhone();
		this.contactUser = addressData.getContactUser();
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

	public String getCustomerAddress() {
		return this.customerAddress;
	}

	private void initAppend() {
		InvoiceEmailContextPoJo initAppend = new InvoiceEmailContextPoJo();
		// set append value

		// add totaldata and list
		List<ProductItemDataOfEmail> list = new ArrayList<ProductItemDataOfEmail>();
		List<ConsignmentData> consignments = orderData.getConsignments();
		String tempCode = "";

		long quantity = 0;
		long net = 0;
		long gross = 0;
		long itemQuantity = 0;
		long itemNet = 0;
		long itemGross = 0;

		String packageWeight = ""; //当前认为包裹重量一致
		if (CollectionUtils.isNotEmpty(consignments)) {
			for (ConsignmentData consignment : consignments) {

				List<ConsignmentEntryData> entryLists = consignment.getEntries();

				if (entryLists != null) {
					for (ConsignmentEntryData consignEntry : entryLists) {

						ProductData product = consignEntry.getOrderEntry().getProduct();

						ProductItemDataOfEmail pie = new ProductItemDataOfEmail();

						if (StringUtils.isNotBlank(tempCode)) {
							if (!tempCode.equals(product.getCode())) {
								ProductItemDataOfEmail totalPie = new ProductItemDataOfEmail();

								totalPie.setProductName("Total");
								totalPie.setGrossWeight(String.valueOf(itemGross));
								totalPie.setNetWeight(String.valueOf(itemNet));
								totalPie.setQuantity(String.valueOf(itemQuantity));
								totalPie.setTotal(true);

								list.add(totalPie);
								itemQuantity = 0;
								itemNet = 0;
								itemGross = 0;
							}
						}

						tempCode = product.getCode();
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

						packageWeight = product.getPackageWeight();
						
						quantity += consignEntry.getQuantity();
						net += Long.parseLong(tempNet);
						gross += Long.parseLong(tempGross);
						itemQuantity += consignEntry.getQuantity();
						itemNet += Long.parseLong(tempNet);
						itemGross += Long.parseLong(tempGross);
						list.add(pie);
						
						
						//add warehouse
						if (StringUtils.isBlank(warehouse)){
							OrderEntryData entryData = consignEntry.getOrderEntry();
							
							if ( entryData != null){
								PointOfServiceData pos = entryData.getDeliveryPointOfService();
								if (pos != null){
									setWarehouse(StringUtils.defaultString(pos.getName(),"&nbsp;"));
								}
							}
						}

					}

				}
				System.out.println("");
			}
		}

		initAppend.setProductLists(list);

		ProductTotalDataOfEmail totalData = new ProductTotalDataOfEmail();
		totalData.setQuantity(String.valueOf(quantity));
		totalData.setNetWeight(String.valueOf(net));
		totalData.setGrossWeight(String.valueOf(gross));

		totalData.setPackingCount(String.valueOf(quantity));
		totalData.setPackingWeight(StringUtils.defaultString(packageWeight,""));
		totalData.setShippingMarks("N/M");
		totalData.setPoNo(" ");

		initAppend.setTotalData(totalData);

		// add default
		initAppend.setAcerChemCompanyNo("07460051");
		initAppend.setAcerChemTaxNo("GB111785918");

		initAppend.setTaxNo("PL8722076235");

		String taxInfo = "V.A.T Transferred article 138 Council Directive 2006/112/EC \n\r";
		StringBuilder sb = new StringBuilder(taxInfo);
		sb.append(
				"Neele-Vat Warehousing BV, Bierbrouwerstraat 2, 3194 AP Hoogvliet Rotterdam, The Netherlands, NL804421390B02 acting as limited fiscal \n\r");
		sb.append("representative: ACERCHEM UK LTD.");

		initAppend.setTaxInfo(sb.toString());

		setAppend(initAppend);

	}

	public String getCurrency() {
		return this.currency;
	}

	public void initPaymentTerms(final OrderProcessModel orderProcessModel) {

		String paymentMode = orderData.getPaymentMode();
		String terms = AcerChemEmailContextUtils.getPaymementTerms(orderProcessModel, paymentMode);
		this.setPaymentTerms(terms);
	}

	public void initWarehouse(){
		
	}
	/**
	 * @return the paymentTerms
	 */
	public String getPaymentTerms() {
		return paymentTerms;
	}

	/**
	 * @param paymentTerms
	 *            the paymentTerms to set
	 */
	public void setPaymentTerms(String paymentTerms) {
		this.paymentTerms = paymentTerms;
	}

	/**
	 * @return the customerModel
	 */
	public CustomerModel getCustomer() {
		return customerModel;
	}

	/**
	 * @return the warehouse
	 */
	public String getWarehouse() {
		return warehouse;
	}

	/**
	 * @param warehouse the warehouse to set
	 */
	public void setWarehouse(String warehouse) {
		this.warehouse = warehouse;
	}

	public String getMoneyToWords() {
		return moneyToWords;
	}

	public void setMoneyToWords(String moneyToWords) {
		this.moneyToWords = moneyToWords;
	}

	public CustomerContactAddressOfEmailData getCustomerAddressData() {
		return customerAddressData;
	}

	public void setCustomerAddressData(CustomerContactAddressOfEmailData customerAddressData) {
		this.customerAddressData = customerAddressData;
	}

	public void setCustomerAddress(String customerAddress) {
		this.customerAddress = customerAddress;
	}

	public String getContactUser() {
		return contactUser;
	}

	public void setContactUser(String contactUser) {
		this.contactUser = contactUser;
	}

	public String getContactMobile() {
		return contactMobile;
	}

	public void setContactMobile(String contactMobile) {
		this.contactMobile = contactMobile;
	}

	public String getVatAmount() {
		return vatAmount;
	}

	public void setVatAmount(String vatAmount) {
		this.vatAmount = vatAmount;
	}

	public String getVatTotal() {
		return vatTotal;
	}

	public void setVatTotal(String vatTotal) {
		this.vatTotal = vatTotal;
	}

	public String getVatMoneyToWords() {
		return vatMoneyToWords;
	}

	public void setVatMoneyToWords(String vatMoneyToWords) {
		this.vatMoneyToWords = vatMoneyToWords;
	}

	//vat 20%
	public void initVatTotal(){
		
		DecimalFormat df   = new DecimalFormat(",##0.00");	
		
		double dTotal =orderData.getTotalPrice().getValue().doubleValue();
		
	
		double dVat = dTotal*0.2;
		String vat_ = df.format(dVat);
		this.setVatAmount(vat_);
		
		
		double  dVatTotal  =  dTotal + dVat;
		String vatTotal_ = df.format(dVatTotal);
		this.setVatTotal(vatTotal_);
		DecimalFormat df1   = new DecimalFormat("0.00");
		
		String formatTotal = df1.format(dVatTotal);
		String vatMoney_ = AcerChemEmailContextUtils.getMoneyOfWord(formatTotal,"$");
		
		this.setVatMoneyToWords(vatMoney_);
	}

	public String getCustomerCompany() {
		return customerCompany;
	}

	public void setCustomerCompany(String customerCompany) {
		this.customerCompany = customerCompany;
	}
}
