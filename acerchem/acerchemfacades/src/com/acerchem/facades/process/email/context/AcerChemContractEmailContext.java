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
		CustomerModel customer = getCustomer(orderProcessModel);
		if (customer != null) {
			Collection<AddressModel> addrs = customer.getAddresses();
			address = AcerChemEmailContextUtils.getCustomerContactAddress(addrs);
		}

		this.customerAddress = address;

	}

	public String getCustomerAddress() {
		return this.customerAddress;
	}

	// initialize append data
	public void initAppend() {
		ContractEmailContextPoJo pojo = new ContractEmailContextPoJo();
		// todo ...

		// add list
		List<ProductItemDataOfEmail> list = new ArrayList<ProductItemDataOfEmail>();
		List<OrderEntryData> orderEntries = orderData.getEntries();

		long quantity = 0;
		if (CollectionUtils.isNotEmpty(orderEntries)) {
			for (OrderEntryData orderEntry : orderEntries) {

				ProductData product = orderEntry.getProduct();

				ProductItemDataOfEmail pie = new ProductItemDataOfEmail();

				pie.setProductCode(product.getCode());
				pie.setProductName(product.getName());
				pie.setUnitName(product.getUnitName());

				long longQuantity = orderEntry.getQuantity();
				pie.setQuantity(String.valueOf(longQuantity));

				PriceData basePrice = orderEntry.getBaseRealPrice();
				if (basePrice != null) {
					pie.setPrice(basePrice.getFormattedValue());
				} else {
					pie.setPrice("0.00");
				}

				PriceData amountPrice = orderEntry.getTotalRealPrice();
				if (amountPrice != null) {
					pie.setAmount(amountPrice.getFormattedValue());
				} else {
					pie.setAmount("0.00");
				}

				// pie.setPackageWeight(priceData);
				if (StringUtils.isNotBlank(product.getPackageWeight())) {
					pie.setPackageWeight(product.getPackageWeight() + "/" + product.getPackageType());
				} else {
					pie.setPackageWeight("");
				}

				pie.setTotal(false);

				quantity += orderEntry.getQuantity();

				list.add(pie);

				// warehouse
				if (StringUtils.isBlank(warehouse)) {
					// String warehouseCode = orderEntry.getWarehouseCode();
					PointOfServiceData pos = orderEntry.getDeliveryPointOfService();
					if (pos != null) {

						setWarehouse(StringUtils.defaultString(pos.getAddress().getFormattedAddress(), "&nbsp;"));

					}

				}

			}
		}

		pojo.setProductLists(list);

		// add total

		String totalPriceStr = "0.00";
		ProductTotalDataOfEmail totalData = new ProductTotalDataOfEmail();
		totalData.setQuantity(String.valueOf(quantity));

		PriceData totalPrice = orderData.getTotalPrice();
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

		String paymentMode = orderData.getPaymentMode();
		String terms = AcerChemEmailContextUtils.getPaymementTerms(orderProcessModel, paymentMode);
		this.setPaymentTerms(terms);
	}

	public ContractEmailContextPoJo getAppend() {
		return append;
	}

	public void setAppend(ContractEmailContextPoJo append) {
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
	 * @param warehouse
	 *            the warehouse to set
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

}
