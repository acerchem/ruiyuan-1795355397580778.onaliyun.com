package com.acerchem.facades.process.email.context;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Required;

import com.acerchem.facades.process.email.context.pojo.AcerChemEmailContextUtils;
import com.acerchem.facades.process.email.context.pojo.ContractEmailContextPoJo;
import com.acerchem.facades.process.email.context.pojo.CustomerContactAddressOfEmailData;
import com.acerchem.facades.process.email.context.pojo.ProductItemDataOfEmail;
import com.acerchem.facades.process.email.context.pojo.ProductTotalDataOfEmail;

import de.hybris.platform.acceleratorservices.model.cms2.pages.EmailPageModel;
import de.hybris.platform.acceleratorservices.process.email.context.AbstractEmailContext;
import de.hybris.platform.basecommerce.model.site.BaseSiteModel;
import de.hybris.platform.commercefacades.coupon.data.CouponData;
import de.hybris.platform.commercefacades.order.data.OrderData;
import de.hybris.platform.commercefacades.order.data.OrderEntryData;
import de.hybris.platform.commercefacades.product.data.PriceData;
import de.hybris.platform.commercefacades.product.data.ProductData;
import de.hybris.platform.commercefacades.storelocator.data.PointOfServiceData;
import de.hybris.platform.core.model.c2l.LanguageModel;
import de.hybris.platform.core.model.order.OrderModel;
import de.hybris.platform.core.model.user.AddressModel;
import de.hybris.platform.core.model.user.CustomerModel;
import de.hybris.platform.orderprocessing.model.OrderProcessModel;
import de.hybris.platform.servicelayer.dto.converter.Converter;

public class AcerChemContractEmailContext extends AbstractEmailContext<OrderProcessModel> {
	private Converter<OrderModel, OrderData> orderConverter;
	private OrderData orderData;
	private List<CouponData> giftCoupons;
	private String deliveryDate;

	private String customerAddress;
	private ContractEmailContextPoJo append;
	private String warehouse;
	private String paymentTerms;
	private CustomerModel customerModel;

	private String moneyToWords;

	private String contactUser;
	private String contactMobile;

	private String deliveryMode;

	private String toShip;

	@Override
	public void init(final OrderProcessModel orderProcessModel, final EmailPageModel emailPageModel) {
		super.init(orderProcessModel, emailPageModel);
		orderData = getOrderConverter().convert(orderProcessModel.getOrder());

		// orderData.getConsignments()
		// List<ConsignmentData>
		giftCoupons = orderData.getAppliedOrderPromotions().stream()
				.filter(x -> CollectionUtils.isNotEmpty(x.getGiveAwayCouponCodes()))
				.flatMap(p -> p.getGiveAwayCouponCodes().stream()).collect(Collectors.toList());

		deliveryDate = orderData.getPickUpdate();

		initCustomerAddress(orderProcessModel);
		initAppend();
		initPaymentTerms(orderProcessModel);

		customerModel = getCustomer(orderProcessModel);

		initDeliveryMode();

	}

	// 统一对模式处理为英文
	private void initDeliveryMode() {
		final String deliveryCode = orderData.getDeliveryMode() == null ? "" : orderData.getDeliveryMode().getCode();
		if (deliveryCode.equals("DELIVERY_GROSS")) {
			setDeliveryMode("DDP");
		} else if (deliveryCode.equals("DELIVERY_MENTION")) {
			setDeliveryMode("FCA");
		} else {
			setDeliveryMode("DDP"); // 默认DDP
		}
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

	/**
	 * @return the deliveryDate
	 */
	public String getDeliveryDate() {
		return deliveryDate;
	}

	private void initCustomerAddress(final OrderProcessModel orderProcessModel) {

		String address = "";
		final CustomerModel customer = getCustomer(orderProcessModel);
		if (customer != null) {
			final Collection<AddressModel> addrs = customer.getAddresses();
			address = AcerChemEmailContextUtils.getCustomerContactAddress(addrs);

			// 增加contact 电话
			final CustomerContactAddressOfEmailData objCustomAddress = AcerChemEmailContextUtils
					.getCustomerContactAddressData(addrs);

			setContactMobile(objCustomAddress.getContactPhone());
			setContactUser(objCustomAddress.getContactUser());
		}

		this.customerAddress = address;

	}

	public String getCustomerAddress() {
		return this.customerAddress;
	}

	// initialize append data
	public void initAppend() {
		final ContractEmailContextPoJo pojo = new ContractEmailContextPoJo();
		// todo ...

		// add list
		final List<ProductItemDataOfEmail> list = new ArrayList<ProductItemDataOfEmail>();
		final List<OrderEntryData> orderEntries = orderData.getEntries();

		long quantity = 0;
		long sumTotalWeight = 0;
		if (CollectionUtils.isNotEmpty(orderEntries)) {
			for (final OrderEntryData orderEntry : orderEntries) {

				final ProductData product = orderEntry.getProduct();

				final ProductItemDataOfEmail pie = new ProductItemDataOfEmail();

				pie.setProductCode(product.getCode());
				pie.setProductName(product.getName());
				pie.setUnitName(product.getUnitName());

				final long longQuantity = orderEntry.getQuantity();
				pie.setQuantity(String.valueOf(longQuantity));

				final PriceData basePrice = orderEntry.getBaseRealPrice();
				if (basePrice != null) {
					pie.setPrice(basePrice.getFormattedValue());
				} else {
					pie.setPrice("0.00");
				}

				final PriceData amountPrice = orderEntry.getTotalRealPrice();
				if (amountPrice != null) {
					pie.setAmount(amountPrice.getFormattedValue());
				} else {
					pie.setAmount("0.00");
				}

				// pie.setPackageWeight( quantity + packageType)
				final String punit = StringUtils.defaultString(product.getUnitName(), "kg");
				pie.setPackageWeight(StringUtils.defaultString(product.getPackageWeight()).trim() + punit + "/"
						+ StringUtils.defaultString(product.getPackageType()));
				// if (StringUtils.isNotBlank(product.getPackageWeight())) {
				// //计算包裹重量,圆整为整数
				// double entryPackageWeight=0;
				// if
				// (AcerChemEmailContextUtils.isNumber(product.getPackageWeight())){
				// final double perPackageWeight =
				// Double.valueOf(product.getPackageWeight());
				// entryPackageWeight = perPackageWeight * longQuantity;
				// }
				//
				// pie.setPackageWeight(Math.round(entryPackageWeight) + "/" +
				// product.getPackageType());
				// } else {
				// pie.setPackageWeight("");
				// }

				pie.setTotal(false);

				// 增加单位总重量
				final long pWeight = orderEntry.getTotalWeight() == null ? 0 : orderEntry.getTotalWeight();
				pie.setTotalWeight(String.valueOf(pWeight));

				quantity += orderEntry.getQuantity();
				sumTotalWeight += pWeight;
				list.add(pie);

				// warehouse-》改为所在城市
				if (StringUtils.isBlank(warehouse)) {
					// String warehouseCode = orderEntry.getWarehouseCode();
					final PointOfServiceData pos = orderEntry.getDeliveryPointOfService();
					if (pos != null) {

						setWarehouse(StringUtils.defaultString(pos.getDefaultWarehouseRegionName(), "&nbsp;"));

					}

				}

			}
		}

		pojo.setProductLists(list);

		// add total

		String totalPriceStr = "0.00";
		final ProductTotalDataOfEmail totalData = new ProductTotalDataOfEmail();
		// totalData.setQuantity(String.valueOf(quantity));
		totalData.setQuantity(String.valueOf(sumTotalWeight));
		final PriceData totalPrice = orderData.getTotalPrice();
		if (totalPrice != null) {
			totalData.setAmountTotal(totalPrice.getFormattedValue());
			totalPriceStr = totalPrice.getValue().toString();
		}
		pojo.setTotalData(totalData);

		// add moneyToWords

		// String totalPrice = orderData.getTotalPrice()
		this.moneyToWords = AcerChemEmailContextUtils.getMoneyOfWord(String.valueOf(totalPriceStr), "$");

		// add paymentTerms

		setAppend(pojo);

	}

	public void initPaymentTerms(final OrderProcessModel orderProcessModel) {

		final String paymentMode = orderData.getPaymentMode();
		final String terms = AcerChemEmailContextUtils.getPaymementTerms(orderProcessModel, paymentMode);
		setPaymentTerms(terms);
	}

	public ContractEmailContextPoJo getAppend() {
		return append;
	}

	public void setAppend(final ContractEmailContextPoJo append) {
		this.append = append;
	}

	public String getWarehouse() {
		return warehouse;
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

	public String getContactMobile() {
		return contactMobile;
	}

	public void setContactMobile(final String contactMobile) {
		this.contactMobile = contactMobile;
	}

	public String getContactUser() {
		return contactUser;
	}

	public void setContactUser(final String contactUser) {
		this.contactUser = contactUser;
	}

	public String getDeliveryMode() {
		return deliveryMode;
	}

	public void setDeliveryMode(final String deliveryMode) {
		this.deliveryMode = deliveryMode;
	}

	public String getToShip() {
		String name = "";
		if (orderData.getDeliveryAddress() != null && orderData.getDeliveryAddress().getCountry() != null) {
			name = orderData.getDeliveryAddress().getCountry().getName();
		}
		return name;
	}

	public void setToShip(final String toShip) {
		this.toShip = toShip;
	}

}
