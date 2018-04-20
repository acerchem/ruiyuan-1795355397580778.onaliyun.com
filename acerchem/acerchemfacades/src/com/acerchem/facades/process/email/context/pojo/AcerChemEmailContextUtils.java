package com.acerchem.facades.process.email.context.pojo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import com.acerchem.core.model.CustomerCreditAccountModel;

import de.hybris.platform.core.model.c2l.CountryModel;
import de.hybris.platform.core.model.user.AddressModel;
import de.hybris.platform.core.model.user.CustomerModel;
import de.hybris.platform.mobileservices.model.text.PhoneNumberModel;
import de.hybris.platform.mobileservices.model.text.UserPhoneNumberModel;
import de.hybris.platform.orderprocessing.model.OrderProcessModel;

//根据地址集合获得客户联系地址
public class AcerChemEmailContextUtils {

	public static String getCustomerContactAddress(Collection<AddressModel> collect){
		if (CollectionUtils.isNotEmpty(collect)){
			for(AddressModel model :collect){
				if(model.getContactAddress()) {
					String street = StringUtils.defaultString(model.getStreetname(),"");
					String streetNum = StringUtils.defaultString(model.getStreetnumber(),"");
					String town = StringUtils.defaultString(model.getTown(),"");
					String country = "";
					CountryModel countryModel = model.getCountry();
					if (countryModel != null){
						country = StringUtils.defaultString(countryModel.getName(),"");
					}
					StringBuilder sb = new StringBuilder(street);
					sb.append("  ");
					sb.append(streetNum).append(" ");
					sb.append(town).append("  ");
					sb.append(country);
					return sb.toString();
					
				} 
			}
			
		}
		return "&nbsp;";
	}
	
	//获得信用期
	public static String getPaymementTerms(final OrderProcessModel orderProcessModel,final String paymentMode){
		
		String terms = "&nbsp; ";
		if (StringUtils.isNotBlank(paymentMode)) {
			if (!paymentMode.equalsIgnoreCase("prepay")) {
				CustomerModel customerModel = (CustomerModel) orderProcessModel.getOrder().getUser();
				terms = " T/T {DAYCOUNT} DAYS AFTER SHIPPING DOCUMENTS";
				if (customerModel != null) {
					CustomerCreditAccountModel customerCreditAccount = customerModel.getCreditAccount();
					if (customerCreditAccount != null) {
						int dayCount = customerCreditAccount.getBillingInterval();

						terms = terms.replace("{DAYCOUNT}", String.valueOf(dayCount));
					}
				} else {
					terms = terms.replace("{DAYCOUNT}", "0");
				}
			}
		}
		return terms;
	}
	
	//获得电话号码
	public static List<String> getPhoneNumbers(final Collection<UserPhoneNumberModel> coll){
		List<String> list = new ArrayList<String>();
		if (CollectionUtils.isNotEmpty(coll)){
			for(UserPhoneNumberModel userPhone:coll){
				PhoneNumberModel pnm = userPhone.getPhoneNumber();
				if (pnm != null){
					String phone = pnm.getFormat().getCode();
					if (StringUtils.isNotBlank(phone)) {
						list.add(phone);
					}
				}
				
			}
		}
		
		return list;
	}
}
