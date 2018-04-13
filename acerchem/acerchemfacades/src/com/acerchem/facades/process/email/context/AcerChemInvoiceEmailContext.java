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

import de.hybris.platform.acceleratorservices.model.cms2.pages.EmailPageModel;
import de.hybris.platform.acceleratorservices.process.email.context.AbstractEmailContext;
import de.hybris.platform.basecommerce.model.site.BaseSiteModel;
import de.hybris.platform.commercefacades.coupon.data.CouponData;
import de.hybris.platform.commercefacades.order.data.ConsignmentData;
import de.hybris.platform.commercefacades.order.data.ConsignmentEntryData;
import de.hybris.platform.commercefacades.order.data.OrderData;
import de.hybris.platform.commercefacades.order.data.OrderEntryData;
import de.hybris.platform.commercefacades.product.data.ProductData;
import de.hybris.platform.core.model.c2l.CountryModel;
import de.hybris.platform.core.model.c2l.LanguageModel;
import de.hybris.platform.core.model.order.OrderModel;
import de.hybris.platform.core.model.user.AddressModel;
import de.hybris.platform.core.model.user.CustomerModel;
import de.hybris.platform.orderprocessing.model.OrderProcessModel;
import de.hybris.platform.servicelayer.dto.converter.Converter;

/**
 * Velocity context for a Invoice notification email.
 */
public class AcerChemInvoiceEmailContext extends AbstractEmailContext<OrderProcessModel> {

	private Converter<OrderModel, OrderData> orderConverter;
	private OrderData orderData;
	private List<CouponData> giftCoupons;
	
	private InvoiceEmailContextPoJo append;
	private String customerAddress;

	private String currency;
	
	
	@Override
	public void init(final OrderProcessModel orderProcessModel, final EmailPageModel emailPageModel) {
		super.init(orderProcessModel, emailPageModel);
		orderData = getOrderConverter().convert(orderProcessModel.getOrder());

		// orderData.getConsignments()
		// List<ConsignmentData>
	
		giftCoupons = orderData.getAppliedOrderPromotions().stream()
				.filter(x -> CollectionUtils.isNotEmpty(x.getGiveAwayCouponCodes()))
				.flatMap(p -> p.getGiveAwayCouponCodes().stream()).collect(Collectors.toList());
		
		currency = "USD";
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

	public InvoiceEmailContextPoJo getAppend() {
		return append;
	}

	private void setAppend(InvoiceEmailContextPoJo append) {
		this.append = append;
	}
	
	private void initCustomerAddress(final OrderProcessModel orderProcessModel){
		
		String address = "";
		CustomerModel customer = getCustomer(orderProcessModel);
		if (customer != null) {
			Collection<AddressModel> addrs = customer.getAddresses();
			address = AcerChemEmailContextUtils.getCustomerContactAddress(addrs);
		}

		this.customerAddress = address;
	}
	
	public String getCustomerAddress(){
		return this.customerAddress;
	}
	private void initAppend(){
		InvoiceEmailContextPoJo initAppend = new InvoiceEmailContextPoJo();
		//set append value
		
		//add totaldata and list
		List<ProductItemDataOfEmail> list = new ArrayList<ProductItemDataOfEmail>(); 
		List<ConsignmentData> consignments = orderData.getConsignments();
		String tempName = "";
		
		long quantity = 0;
		long  net = 0;
		long gross = 0;		
		long itemQuantity = 0;
		long  itemNet = 0;
		long itemGross = 0;		
		
		if(CollectionUtils.isNotEmpty(consignments)){
			for(ConsignmentData  consignment : consignments){
				
				List<ConsignmentEntryData> entryLists =  consignment.getEntries();
				
				if(entryLists != null){
					for(ConsignmentEntryData consignEntry : entryLists){
						
						ProductData product = consignEntry.getOrderEntry().getProduct();
						
						ProductItemDataOfEmail pie = new ProductItemDataOfEmail();
						
						if(StringUtils.isNotBlank(tempName)) {
							if(!tempName.equals(product.getName())){
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
		
		initAppend.setProductLists(list);
		
		ProductTotalDataOfEmail totalData = new ProductTotalDataOfEmail();
		totalData.setQuantity(String.valueOf(quantity));
		totalData.setNetWeight(String.valueOf(net));
		totalData.setGrossWeight(String.valueOf(gross));
		
		totalData.setPackingCount("200");
		totalData.setPackingWeight("25");
		totalData.setShippingMarks("N/M");
		totalData.setPoNo(" nr ZZ-735/17/MAPSG & nr ZZ-750/17/MAPSG");
		
		initAppend.setTotalData(totalData);
		
		//add default
		initAppend.setAcerChemCompanyNo("07460051");
		initAppend.setAcerChemTaxNo("GB111785918");
		initAppend.setTaxNo("PL8722076235");
		
		String taxInfo = "V.A.T Transferred article 138 Council Directive 2006/112/EC \n\r";
		StringBuilder sb =  new StringBuilder(taxInfo);
		sb.append("Neele-Vat Warehousing BV, Bierbrouwerstraat 2, 3194 AP Hoogvliet Rotterdam, The Netherlands, NL804421390B02 acting as limited fiscal \n\r");
		sb.append("representative: ACERCHEM UK LTD.");
		
		initAppend.setTaxInfo(sb.toString());
		
		setAppend(initAppend);
	}
	
	public String getCurrency(){
		return this.currency;
	}


	

}
