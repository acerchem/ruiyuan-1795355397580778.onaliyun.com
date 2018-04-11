package com.acerchem.facades.process.email.context;

import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Component;

import de.hybris.platform.acceleratorservices.model.cms2.pages.EmailPageModel;
import de.hybris.platform.acceleratorservices.process.email.context.AbstractEmailContext;
import de.hybris.platform.acceleratorservices.urlencoder.UrlEncoderService;
import de.hybris.platform.acceleratorservices.urlresolver.SiteBaseUrlResolutionService;
import de.hybris.platform.basecommerce.model.site.BaseSiteModel;
import de.hybris.platform.commercefacades.coupon.data.CouponData;
import de.hybris.platform.commercefacades.order.data.OrderData;
import de.hybris.platform.commerceservices.customer.CustomerEmailResolutionService;
import de.hybris.platform.core.model.c2l.LanguageModel;
import de.hybris.platform.core.model.order.OrderModel;
import de.hybris.platform.core.model.user.CustomerModel;
import de.hybris.platform.orderprocessing.model.OrderProcessModel;
import de.hybris.platform.servicelayer.config.ConfigurationService;
import de.hybris.platform.servicelayer.dto.converter.Converter;


//@Inherited
@Component("myOrderNotificationEmailContext")
public class MyOrderNotificationEmailContext extends AbstractEmailContext<OrderProcessModel> {
	
	@Resource
	private Converter<OrderModel, OrderData> orderConverter;
	private OrderData orderData;
	private List<CouponData> giftCoupons;
	
//	@Resource
//	private CustomerEmailResolutionService customerEmailResolutionService;
//	@Resource
//	private ConfigurationService configurationService;
//	@Resource
//	private UrlEncoderService urlEncoderService;
//	@Resource
//	private SiteBaseUrlResolutionService siteBaseUrlResolutionService;
	
	/* (non-Javadoc)
	 * @see de.hybris.platform.acceleratorservices.process.email.context.AbstractEmailContext#init(de.hybris.platform.processengine.model.BusinessProcessModel, de.hybris.platform.acceleratorservices.model.cms2.pages.EmailPageModel)
	 */
	@Override
	public void init(OrderProcessModel orderProcessModel, EmailPageModel emailPageModel) {
		// TODO Auto-generated method stub
		super.init(orderProcessModel, emailPageModel);
		
		orderData = getOrderConverter().convert(orderProcessModel.getOrder());

		System.out.println("hi");
		String temp = orderData.getCode();
		if ( temp != null){
			System.out.println("****"+ temp);
		}
		giftCoupons = orderData.getAppliedOrderPromotions().stream()
				.filter(x -> CollectionUtils.isNotEmpty(x.getGiveAwayCouponCodes())).flatMap(p -> p.getGiveAwayCouponCodes().stream())
				.collect(Collectors.toList());
	}
	

	/* (non-Javadoc)
	 * @see de.hybris.platform.acceleratorservices.process.email.context.AbstractEmailContext#getSite(de.hybris.platform.processengine.model.BusinessProcessModel)
	 */
	@Override
	protected BaseSiteModel getSite(OrderProcessModel businessProcessModel) {
		// TODO Auto-generated method stub
		return businessProcessModel.getOrder().getSite();
	}

	/* (non-Javadoc)
	 * @see de.hybris.platform.acceleratorservices.process.email.context.AbstractEmailContext#getCustomer(de.hybris.platform.processengine.model.BusinessProcessModel)
	 */
	@Override
	protected CustomerModel getCustomer(OrderProcessModel businessProcessModel) {
		// TODO Auto-generated method stub
		return (CustomerModel)businessProcessModel.getOrder().getUser();
	}

	/* (non-Javadoc)
	 * @see de.hybris.platform.acceleratorservices.process.email.context.AbstractEmailContext#getEmailLanguage(de.hybris.platform.processengine.model.BusinessProcessModel)
	 */
	@Override
	protected LanguageModel getEmailLanguage(OrderProcessModel businessProcessModel) {
		// TODO Auto-generated method stub
		return businessProcessModel.getOrder().getLanguage();
	}


	/**
	 * @return the orderConverter
	 */
	public Converter<OrderModel, OrderData> getOrderConverter() {
		return orderConverter;
	}


	/**
	 * @param orderConverter the orderConverter to set
	 */
	public void setOrderConverter(Converter<OrderModel, OrderData> orderConverter) {
		this.orderConverter = orderConverter;
	}


	/**
	 * @return the orderData
	 */
	public OrderData getOrder() {
		return orderData;
	}


	/**
	 * @return the giftCoupons
	 */
	public List<CouponData> getCoupons() {
		return giftCoupons;
	}


	/* (non-Javadoc)
	 * @see de.hybris.platform.acceleratorservices.process.email.context.AbstractEmailContext#setCustomerEmailResolutionService(de.hybris.platform.commerceservices.customer.CustomerEmailResolutionService)
	 */
	@Override
	public void setCustomerEmailResolutionService(CustomerEmailResolutionService customerEmailResolutionService) {
		// TODO Auto-generated method stub
		super.setCustomerEmailResolutionService(customerEmailResolutionService);
	}


	/* (non-Javadoc)
	 * @see de.hybris.platform.acceleratorservices.process.email.context.AbstractEmailContext#setConfigurationService(de.hybris.platform.servicelayer.config.ConfigurationService)
	 */
	@Override
	public void setConfigurationService(ConfigurationService configurationService) {
		// TODO Auto-generated method stub
		super.setConfigurationService(configurationService);
	}


	/* (non-Javadoc)
	 * @see de.hybris.platform.acceleratorservices.document.context.AbstractHybrisVelocityContext#setSiteBaseUrlResolutionService(de.hybris.platform.acceleratorservices.urlresolver.SiteBaseUrlResolutionService)
	 */
	@Override
	public void setSiteBaseUrlResolutionService(SiteBaseUrlResolutionService siteBaseUrlResolutionService) {
		// TODO Auto-generated method stub
		super.setSiteBaseUrlResolutionService(siteBaseUrlResolutionService);
	}


	/* (non-Javadoc)
	 * @see de.hybris.platform.acceleratorservices.document.context.AbstractHybrisVelocityContext#setUrlEncoderService(de.hybris.platform.acceleratorservices.urlencoder.UrlEncoderService)
	 */
	@Override
	public void setUrlEncoderService(UrlEncoderService urlEncoderService) {
		// TODO Auto-generated method stub
		super.setUrlEncoderService(urlEncoderService);
	}


	
	
	

}
