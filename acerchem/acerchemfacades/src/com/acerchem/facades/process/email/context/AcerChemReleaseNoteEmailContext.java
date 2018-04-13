package com.acerchem.facades.process.email.context;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Required;

import com.acerchem.facades.process.email.context.pojo.AcerChemEmailContextUtils;
import com.acerchem.facades.process.email.context.pojo.InvoiceEmailContextPoJo;
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
	private ReleaseNoteEmailContextPoJo append;

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
		ReleaseNoteEmailContextPoJo pojo = new ReleaseNoteEmailContextPoJo();
		// todo ...

		// add list
		List<ProductItemDataOfEmail> list = new ArrayList<ProductItemDataOfEmail>();
		List<ConsignmentData> consignments = orderData.getConsignments();
		String tempName = "";

		long quantity = 0;
		long net = 0;
		long gross = 0;
		long itemQuantity = 0;
		long itemNet = 0;
		long itemGross = 0;

		if (CollectionUtils.isNotEmpty(consignments)) {
			for (ConsignmentData consignment : consignments) {

				List<ConsignmentEntryData> entryLists = consignment.getEntries();

				if (entryLists != null) {
					for (ConsignmentEntryData consignEntry : entryLists) {

						ProductData product = consignEntry.getOrderEntry().getProduct();

						ProductItemDataOfEmail pie = new ProductItemDataOfEmail();

						if (StringUtils.isNotBlank(tempName)) {
							if (!tempName.equals(product.getName())) {
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

						tempName = product.getName();
						pie.setProductCode(product.getCode());
						pie.setProductName(product.getName());
						pie.setQuantity(consignEntry.getQuantity().toString());
						pie.setBatchNo("DY0661700095");
						pie.setNetWeight("1000");
						pie.setGrossWeight("1120");
						pie.setTotal(false);

						quantity += consignEntry.getQuantity();
						net += 1000;
						gross += 1120;
						itemQuantity += consignEntry.getQuantity();
						itemNet += 1000;
						itemGross += 1120;
						list.add(pie);

					}

				}
				System.out.println("");
			}
		}

		pojo.setProductLists(list);

		// add total

		ProductTotalDataOfEmail totalData = new ProductTotalDataOfEmail();
		totalData.setQuantity(String.valueOf(quantity));
		totalData.setNetWeight(String.valueOf(net));
		totalData.setGrossWeight(String.valueOf(gross));
		pojo.setTotalData(totalData);
		
		
		setAppend(pojo);

	}

	public ReleaseNoteEmailContextPoJo getAppend() {
		return append;
	}

	public void setAppend(ReleaseNoteEmailContextPoJo append) {
		this.append = append;
	}

}
