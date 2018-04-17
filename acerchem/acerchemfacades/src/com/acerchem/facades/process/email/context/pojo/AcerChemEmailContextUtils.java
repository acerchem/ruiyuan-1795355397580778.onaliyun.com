package com.acerchem.facades.process.email.context.pojo;

import java.util.Collection;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import de.hybris.platform.core.model.c2l.CountryModel;
import de.hybris.platform.core.model.user.AddressModel;

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
		return "";
	}
	
}
