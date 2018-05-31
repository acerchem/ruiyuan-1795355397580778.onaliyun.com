package com.acerchem.facades.process.email.context;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Required;

import com.acerchem.facades.process.email.context.pojo.AcerChemEmailContextUtils;
import com.acerchem.facades.process.email.context.pojo.CustomerContactAddressOfEmailData;
import com.acerchem.facades.process.email.context.pojo.ProductItemDataOfEmail;
import com.acerchem.facades.process.email.context.pojo.ProductTotalDataOfEmail;
import com.acerchem.facades.process.email.context.pojo.ReleaseNoteEmailContextPoJo;

import de.hybris.platform.acceleratorservices.model.cms2.pages.EmailPageModel;
import de.hybris.platform.acceleratorservices.process.email.context.AbstractEmailContext;
import de.hybris.platform.basecommerce.model.site.BaseSiteModel;
import de.hybris.platform.commercefacades.coupon.data.CouponData;
import de.hybris.platform.commercefacades.order.data.ConsignmentData;
import de.hybris.platform.commercefacades.order.data.ConsignmentEntryData;
import de.hybris.platform.commercefacades.order.data.OrderData;
import de.hybris.platform.commercefacades.product.data.ProductData;
import de.hybris.platform.core.model.c2l.LanguageModel;
import de.hybris.platform.core.model.order.OrderModel;
import de.hybris.platform.core.model.user.AddressModel;
import de.hybris.platform.core.model.user.CustomerModel;
import de.hybris.platform.orderprocessing.model.OrderProcessModel;
import de.hybris.platform.servicelayer.dto.converter.Converter;

public class AcerChemReleaseNoteEmailContext extends AbstractEmailContext<OrderProcessModel> {
	private Converter<OrderModel, OrderData> orderConverter;
	private OrderData orderData;
	private List<CouponData> giftCoupons;

	private String customerAddress;
	private CustomerContactAddressOfEmailData customerAddressData;
	private ReleaseNoteEmailContextPoJo append;

	private CustomerModel customerModel;
	private String pickupDate;

	private String contactUser = "";
	private String contactMobile = "";

	@Override
	public void init(final OrderProcessModel orderProcessModel, final EmailPageModel emailPageModel) {
		super.init(orderProcessModel, emailPageModel);
		orderData = getOrderConverter().convert(orderProcessModel.getOrder());

		// orderData.getConsignments()
		// List<ConsignmentData>
		giftCoupons = orderData.getAppliedOrderPromotions().stream()
				.filter(x -> CollectionUtils.isNotEmpty(x.getGiveAwayCouponCodes()))
				.flatMap(p -> p.getGiveAwayCouponCodes().stream()).collect(Collectors.toList());

		initCustomerAddress(orderProcessModel);
		initAppend();
		customerModel = getCustomer(orderProcessModel);

		pickupDate = orderData.getPickUpdate();
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

		String address = "";
		final CustomerModel customer = getCustomer(orderProcessModel);
		if (customer != null) {
			final Collection<AddressModel> addrs = customer.getAddresses();
			address = AcerChemEmailContextUtils.getCustomerContactAddress(addrs);
			setCustomerAddressData(AcerChemEmailContextUtils.getCustomerContactAddressData(addrs));
			if (this.customerAddressData != null) {
				setContactMobile(this.customerAddressData.getContactPhone());
				setContactUser(this.customerAddressData.getContactUser());
			}
		}

		this.customerAddress = address;

	}

	public String getCustomerAddress() {
		return this.customerAddress;
	}

	// initialize append data
	public void initAppend() {
		final ReleaseNoteEmailContextPoJo pojo = new ReleaseNoteEmailContextPoJo();
		// todo ...

		// add list
		final List<ProductItemDataOfEmail> list = new ArrayList<ProductItemDataOfEmail>();
		final List<ConsignmentData> consignments = orderData.getConsignments();
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

								batchNum++;
							}
						}

						tempName = product.getName();
						pie.setProductCode(product.getCode());
						pie.setProductName(product.getName());
						pie.setQuantity(consignEntry.getQuantity().toString());
						pie.setBatchNo(StringUtils.defaultString(consignEntry.getBatchNum(), " "));
						String tempNet = StringUtils.defaultString(product.getNetWeight(), "0");
						String tempGross = StringUtils.defaultString(product.getGrossWeight(), "0");
						if (!AcerChemEmailContextUtils.isNumber(tempNet)) {
							tempNet = "0";
						}else{
							tempNet = String.valueOf(Long.valueOf(Double.valueOf(tempNet).longValue()) * consignEntry.getQuantity());
						}
						if (!AcerChemEmailContextUtils.isNumber(tempGross)) {
							tempGross = "0";
						}else{
							tempGross = String.valueOf(Long.valueOf(Double.valueOf(tempGross).longValue()) * consignEntry.getQuantity());
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
			// add last item project
			if (batchNum > 0) {
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

	public ReleaseNoteEmailContextPoJo getAppend() {
		return append;
	}

	public void setAppend(final ReleaseNoteEmailContextPoJo append) {
		this.append = append;
	}

	/**
	 * @return the customerModel
	 */
	public CustomerModel getCustomer() {
		return customerModel;
	}

	public String getPickupDate() {
		return pickupDate;
	}

	public void setPickupDate(final String pickupDate) {
		this.pickupDate = pickupDate;
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

	public void setCustomerAddress(final String customerAddress) {
		this.customerAddress = customerAddress;
	}

	public CustomerContactAddressOfEmailData getCustomerAddressData() {
		return customerAddressData;
	}

	public void setCustomerAddressData(final CustomerContactAddressOfEmailData customerAddressData) {
		this.customerAddressData = customerAddressData;
	}

}
