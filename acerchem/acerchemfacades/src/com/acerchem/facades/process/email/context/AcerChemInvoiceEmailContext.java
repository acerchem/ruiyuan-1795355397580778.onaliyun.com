package com.acerchem.facades.process.email.context;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

	// total vat 20%
	private String vatAmount;
	private String vatTotal;
	private String vatMoneyToWords;

	private String customerCompany;

	private String deliveryCode;

	private String upperCasePackageWeightArray;
	private String classificationPackageTypeQuantityArray;

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

		setCustomerCompany(customerModel.getCompanyName());

		final String total = orderData.getTotalPrice().getValue().toString();
		moneyToWords = AcerChemEmailContextUtils.getMoneyOfWord(total, "$");

		// initContactInfo();
		initVatTotal();

		this.deliveryCode = orderData.getDeliveryMode() == null ? "" : orderData.getDeliveryMode().getCode();
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

	private void setAppend(final InvoiceEmailContextPoJo append) {
		this.append = append;
	}

	private void initCustomerAddress(final OrderProcessModel orderProcessModel) {

		String address = "";
		final CustomerModel customer = getCustomer(orderProcessModel);
		CustomerContactAddressOfEmailData addressData = new CustomerContactAddressOfEmailData();
		if (customer != null) {
			final Collection<AddressModel> addrs = customer.getAddresses();
			address = AcerChemEmailContextUtils.getCustomerContactAddress(addrs);
			addressData = AcerChemEmailContextUtils.getCustomerContactAddressData(addrs);
		}

		setCustomerAddressData(addressData);
		setCustomerAddress(address);
		this.contactMobile = addressData.getContactPhone();
		this.contactUser = addressData.getContactUser();
	}

	public String getCustomerAddress() {
		return this.customerAddress;
	}

	private void initAppend() {
		final InvoiceEmailContextPoJo initAppend = new InvoiceEmailContextPoJo();
		// set append value

		// add totaldata and list
		final List<ProductItemDataOfEmail> list = new ArrayList<ProductItemDataOfEmail>();
		final List<ConsignmentData> consignments = orderData.getConsignments();
		String tempCode = "";

		long quantity = 0;
		long net = 0;
		long gross = 0;
		long itemQuantity = 0;
		long itemNet = 0;
		long itemGross = 0;

		// String packageWeight = ""; // 当前认为包裹重量一致
		// 处理不同的packageWeight
		List<String> packageWeightList = new ArrayList<String>();
		final Map<String, Long> subPackageTypeAmount = new HashMap<String, Long>();
		if (CollectionUtils.isNotEmpty(consignments)) {
			for (final ConsignmentData consignment : consignments) {

				final List<ConsignmentEntryData> entryLists = consignment.getEntries();

				if (entryLists != null) {
					for (final ConsignmentEntryData consignEntry : entryLists) {

						final ProductData product = consignEntry.getOrderEntry().getProduct();

						final ProductItemDataOfEmail pie = new ProductItemDataOfEmail();

						if (StringUtils.isNotBlank(tempCode)) {
							if (!tempCode.equals(product.getCode())) {
								final ProductItemDataOfEmail totalPie = new ProductItemDataOfEmail();

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
						pie.setBatchNo(StringUtils.defaultString(consignEntry.getBatchNum(), " "));
						String tempNet = StringUtils.defaultString(product.getNetWeight(), "0");
						String tempGross = StringUtils.defaultString(product.getGrossWeight(), "0");
						if (!StringUtils.isNumeric(tempNet)) {
							tempNet = "0";
						} else {
							tempNet = String.valueOf(Long.valueOf(tempNet) * consignEntry.getQuantity());
						}
						if (!StringUtils.isNumeric(tempGross)) {
							tempGross = "0";
						} else {
							tempGross = String.valueOf(Long.valueOf(tempGross) * consignEntry.getQuantity());
						}
						pie.setNetWeight(tempNet);
						pie.setGrossWeight(tempGross);
						pie.setTotal(false);

						final String punit = StringUtils.defaultString(product.getUnitName(), "kg");
						final String pack = StringUtils.defaultString(product.getPackageWeight()).trim() + punit + "/"
								+ StringUtils.defaultString(product.getPackageType()).trim();

						quantity += consignEntry.getQuantity();
						net += Long.parseLong(tempNet);
						gross += Long.parseLong(tempGross);
						itemQuantity += consignEntry.getQuantity();
						itemNet += Long.parseLong(tempNet);
						itemGross += Long.parseLong(tempGross);
						list.add(pie);
						packageWeightList.add(pack);

						// add warehouse
						if (StringUtils.isBlank(warehouse)) {
							final OrderEntryData entryData = consignEntry.getOrderEntry();

							if (entryData != null) {
								final PointOfServiceData pos = entryData.getDeliveryPointOfService();
								if (pos != null) {
									setWarehouse(StringUtils.defaultString(pos.getName(), "&nbsp;"));
								}
							}
						}
						// sub packageWeight total quantity
						if (subPackageTypeAmount
								.get(StringUtils.defaultString(product.getPackageType(), "none")) == null) {
							subPackageTypeAmount.put(StringUtils.defaultString(product.getPackageType(), "none"),
									consignEntry.getQuantity());
						} else {
							long tempLongValue = subPackageTypeAmount
									.get(StringUtils.defaultString(product.getPackageType(), "none")).longValue();
							tempLongValue += consignEntry.getQuantity().longValue();
							subPackageTypeAmount.put(StringUtils.defaultString(product.getPackageType(), "none"),
									tempLongValue);
						}

					}

				}
				System.out.println("");
			}
		}

		initAppend.setProductLists(list);

		final ProductTotalDataOfEmail totalData = new ProductTotalDataOfEmail();
		totalData.setQuantity(String.valueOf(quantity));
		totalData.setNetWeight(String.valueOf(net));
		totalData.setGrossWeight(String.valueOf(gross));

		totalData.setPackingCount(String.valueOf(quantity));
		// 处理packageweightList组合
		if (packageWeightList.size() > 0) {
			packageWeightList = AcerChemEmailContextUtils.removeDuplicate(packageWeightList);
			final String packArrayStr = packageWeightList.toString().replace("[", "").replace("]", "");
			totalData.setPackingWeight(StringUtils.defaultString(packArrayStr, ""));
			setUpperCasePackageWeightArray(StringUtils.upperCase(packArrayStr));
		} else {
			totalData.setPackingWeight("  ");
		}

		totalData.setShippingMarks("N/M");
		totalData.setPoNo(" ");

		initAppend.setTotalData(totalData);

		// add classificationTotalPackage amount
		if (subPackageTypeAmount.size() > 0) {
			String _packageWeightTotal = "";
			for (final Map.Entry<String, Long> pwEntry : subPackageTypeAmount.entrySet()) {
				if (!pwEntry.getKey().equals("none")) {
					_packageWeightTotal += pwEntry.getValue().longValue() + pwEntry.getKey() + "s,";
				}
			}
			//去掉最后一个","
			final int pos = _packageWeightTotal.lastIndexOf(",");
			if (pos > 0){
				_packageWeightTotal = _packageWeightTotal.substring(0,pos);
			}
			setClassificationPackageTypeQuantityArray(_packageWeightTotal);
		}

		// add default
		initAppend.setAcerChemCompanyNo("07460051");
		initAppend.setAcerChemTaxNo("GB111785918");

		initAppend.setTaxNo("PL8722076235");

		final String taxInfo = "V.A.T Transferred article 138 Council Directive 2006/112/EC \n\r";
		final StringBuilder sb = new StringBuilder(taxInfo);
		sb.append(
				"Neele-Vat Warehousing BV, Bierbrouwerstraat 2, 3194 AP Hoogvliet Rotterdam, The Netherlands, NL804421390B02 acting as limited fiscal \n\r");
		sb.append("representative: ACERCHEM UK LTD.");

		initAppend.setTaxInfo(sb.toString());

		setAppend(initAppend);

		// 处理warehouse数据，这个数据在proforma时，因没有consignment，而取不到
		if (StringUtils.isBlank(warehouse)) {
			final List<OrderEntryData> orderEntries = orderData.getEntries();
			if (CollectionUtils.isNotEmpty(orderEntries)) {
				for (final OrderEntryData orderEntry : orderEntries) {
					final PointOfServiceData pos = orderEntry.getDeliveryPointOfService();
					if (pos != null) {
						setWarehouse(StringUtils.defaultString(pos.getName(), "&nbsp;"));
						break;
					}
				}
			}
		}

	}

	public String getCurrency() {
		return this.currency;
	}

	public void initPaymentTerms(final OrderProcessModel orderProcessModel) {

		final String paymentMode = orderData.getPaymentMode();
		final String terms = AcerChemEmailContextUtils.getPaymementTerms(orderProcessModel, paymentMode);
		setPaymentTerms(terms);
	}

	public void initWarehouse() {

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
	public void setPaymentTerms(final String paymentTerms) {
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
	 * @param warehouse
	 *            the warehouse to set
	 */
	public void setWarehouse(final String warehouse) {
		this.warehouse = warehouse;
	}

	public String getMoneyToWords() {
		return moneyToWords;
	}

	public void setMoneyToWords(final String moneyToWords) {
		this.moneyToWords = moneyToWords;
	}

	public CustomerContactAddressOfEmailData getCustomerAddressData() {
		return customerAddressData;
	}

	public void setCustomerAddressData(final CustomerContactAddressOfEmailData customerAddressData) {
		this.customerAddressData = customerAddressData;
	}

	public void setCustomerAddress(final String customerAddress) {
		this.customerAddress = customerAddress;
	}

	public String getContactUser() {
		return contactUser;
	}

	public void setContactUser(final String contactUser) {
		this.contactUser = contactUser;
	}

	public String getContactMobile() {
		return contactMobile;
	}

	public void setContactMobile(final String contactMobile) {
		this.contactMobile = contactMobile;
	}

	public String getVatAmount() {
		return vatAmount;
	}

	public void setVatAmount(final String vatAmount) {
		this.vatAmount = vatAmount;
	}

	public String getVatTotal() {
		return vatTotal;
	}

	public void setVatTotal(final String vatTotal) {
		this.vatTotal = vatTotal;
	}

	public String getVatMoneyToWords() {
		return vatMoneyToWords;
	}

	public void setVatMoneyToWords(final String vatMoneyToWords) {
		this.vatMoneyToWords = vatMoneyToWords;
	}

	// vat 20%
	public void initVatTotal() {

		final DecimalFormat df = new DecimalFormat(",##0.00");

		final double dTotal = orderData.getTotalPrice().getValue().doubleValue();

		final double dVat = dTotal * 0.2;
		final String vat_ = df.format(dVat);
		setVatAmount(vat_);

		final double dVatTotal = dTotal + dVat;
		final String vatTotal_ = df.format(dVatTotal);
		setVatTotal(vatTotal_);
		final DecimalFormat df1 = new DecimalFormat("0.00");

		final String formatTotal = df1.format(dVatTotal);
		final String vatMoney_ = AcerChemEmailContextUtils.getMoneyOfWord(formatTotal, "$");

		setVatMoneyToWords(vatMoney_);
	}

	public String getCustomerCompany() {
		return customerCompany;
	}

	public void setCustomerCompany(final String customerCompany) {
		this.customerCompany = customerCompany;
	}

	public String getInvoiceDate() {
		String dateStr = "";

		if (getDeliveryCode().equals("DELIVERY_GROSS")) {// 配送 DELIVERY_GROSS
			dateStr = orderData.getWaitDeliveiedDate();
		} else {// 自提 DELIVERY_MENTION
			dateStr = orderData.getPickUpdate();
		}

		return dateStr;
	}

	// 处理到货地址
	public String getShipto() {
		String shipto = "";

		if (getDeliveryCode().equals("DELIVERY_GROSS")) {// 配送 DELIVERY_GROSS
			shipto = orderData.getDeliveryAddress().getFormattedAddress();
		} else {// 自提 DELIVERY_MENTION
			shipto = "&nbsp;&nbsp;&nbsp;&nbsp;";
		}

		return shipto;
	}

	public String getDeliveryCode() {
		return deliveryCode;
	}

	public void setDeliveryCode(final String deliveryCode) {
		this.deliveryCode = deliveryCode;
	}

	public String getUpperCasePackageWeightArray() {
		return upperCasePackageWeightArray;
	}

	public void setUpperCasePackageWeightArray(final String upperCasePackageWeightArray) {
		this.upperCasePackageWeightArray = upperCasePackageWeightArray;
	}

	

	public String getClassificationPackageTypeQuantityArray() {
		return classificationPackageTypeQuantityArray;
	}

	public void setClassificationPackageTypeQuantityArray(final String classificationPackageTypeQuantityArray) {
		this.classificationPackageTypeQuantityArray = classificationPackageTypeQuantityArray;
	}

}
