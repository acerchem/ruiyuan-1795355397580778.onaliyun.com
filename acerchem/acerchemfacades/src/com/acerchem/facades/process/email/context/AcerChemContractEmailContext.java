package com.acerchem.facades.process.email.context;

import java.math.BigDecimal;
import java.text.DecimalFormat;
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
	private String wareHouse;
	private String paymentTerms;
	private CustomerModel customerModel;  


	@Override
	public void init(final OrderProcessModel orderProcessModel, final EmailPageModel emailPageModel) {
		super.init(orderProcessModel, emailPageModel);
		orderData = getOrderConverter().convert(orderProcessModel.getOrder());

		// orderData.getConsignments()
		// List<ConsignmentData>
		giftCoupons = orderData.getAppliedOrderPromotions().stream()
				.filter(x -> CollectionUtils.isNotEmpty(x.getGiveAwayCouponCodes()))
				.flatMap(p -> p.getGiveAwayCouponCodes().stream()).collect(Collectors.toList());

		deliveryDate = "4/12/2018";

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
		String tempCode = "";

		long quantity = 0;
		double totalAmount = 0;
		long subQuantity = 0;
		double subAmount = 0;

		if (CollectionUtils.isNotEmpty(orderEntries)) {
			for (OrderEntryData orderEntry : orderEntries) {

				ProductData product = orderEntry.getProduct();

				ProductItemDataOfEmail pie = new ProductItemDataOfEmail();

				if (StringUtils.isNotBlank(tempCode)) {
					if (!tempCode.equals(product.getCode())) {
						ProductItemDataOfEmail subtotalPie = new ProductItemDataOfEmail();

						subtotalPie.setProductName("Total");

						String formatDouble = new DecimalFormat("0.00").format(subAmount);
						subtotalPie.setQuantity(String.valueOf(subQuantity));
						subtotalPie.setAmount(formatDouble);
						subtotalPie.setTotal(true);

						list.add(subtotalPie);
						
						subQuantity = 0;
						subAmount = 0;
					}
				}

				tempCode = product.getCode();
				pie.setProductCode(tempCode);
				pie.setProductName(product.getName());
				pie.setUnitName(product.getUnitName());

				long longQuantity = orderEntry.getQuantity();
				pie.setQuantity(String.valueOf(longQuantity));

				PriceData priceData = orderEntry.getBasePrice();
				BigDecimal amount = new BigDecimal(0);
				if (priceData != null) {
					pie.setPrice(priceData.getFormattedValue());
					BigDecimal dPrice = priceData.getValue();
					amount = dPrice.multiply(new BigDecimal(longQuantity));
					pie.setAmount(amount.toString());
				} else {
					pie.setPrice("0.00");
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
				subQuantity += orderEntry.getQuantity();
				totalAmount += amount.doubleValue();
				subAmount += amount.doubleValue();
				list.add(pie);

				// warehouse
				if (StringUtils.isBlank(wareHouse)) {
					//String warehouseCode = orderEntry.getWarehouseCode();
					PointOfServiceData  pos = orderEntry.getDeliveryPointOfService();
					if (pos != null){
						wareHouse=pos.getAddress().getFormattedAddress();
						wareHouse = StringUtils.defaultString(wareHouse,"&nbsp;");
					}
					
					
				}

			}
		}

		pojo.setProductLists(list);

		// add total

		 ProductTotalDataOfEmail totalData = new ProductTotalDataOfEmail();
		totalData.setQuantity(String.valueOf(quantity));
		totalData.setAmountTotal(new DecimalFormat("0.00").format(totalAmount));
		// totalData.setNetWeight(String.valueOf(net));
		// totalData.setGrossWeight(String.valueOf(gross));
		 pojo.setTotalData(totalData);

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

	public String getWareHouse() {
		return wareHouse;
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

}
