package com.acerchem.facades.process.email.context;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Required;

import com.acerchem.facades.process.email.context.pojo.InvoiceEmailContextPoJo;
import com.acerchem.facades.process.email.context.pojo.ReleaseNoteEmailContextPoJo;

import de.hybris.platform.acceleratorservices.model.cms2.pages.EmailPageModel;
import de.hybris.platform.acceleratorservices.process.email.context.AbstractEmailContext;
import de.hybris.platform.basecommerce.model.site.BaseSiteModel;
import de.hybris.platform.commercefacades.coupon.data.CouponData;
import de.hybris.platform.commercefacades.order.data.OrderData;
import de.hybris.platform.core.model.c2l.LanguageModel;
import de.hybris.platform.core.model.order.OrderModel;
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
			String street = customer.getDefaultPaymentAddress().getStreetname();
			String country = customer.getDefaultPaymentAddress().getCountry().getName();
			address = StringUtils.isNotBlank(street) ? street : "" + " ";
			address += StringUtils.isNotBlank(country) ? country : "";
		}

		this.customerAddress = address;

	}

	public String getCustomerAddress() {
		return this.customerAddress;
	}
	
	
	//initialize append data
	public void initAppend(){
		ReleaseNoteEmailContextPoJo pojo = new ReleaseNoteEmailContextPoJo();
		// todo ...
		
		
		
		setAppend(pojo);
		
	}

	public ReleaseNoteEmailContextPoJo getAppend() {
		return append;
	}

	public void setAppend(ReleaseNoteEmailContextPoJo append) {
		this.append = append;
	}

}
