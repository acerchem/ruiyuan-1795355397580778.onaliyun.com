package com.acerchem.facades.process.email.context.pojo;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import com.acerchem.core.model.CustomerCreditAccountModel;
import com.acerchem.core.model.PhoneNumberModel;
import com.acerchem.core.model.UserPhoneNumberModel;

import de.hybris.platform.core.model.c2l.CountryModel;
import de.hybris.platform.core.model.c2l.RegionModel;
import de.hybris.platform.core.model.user.AddressModel;
import de.hybris.platform.core.model.user.CustomerModel;
import de.hybris.platform.orderprocessing.model.OrderProcessModel;

//根据地址集合获得客户联系地址
public class AcerChemEmailContextUtils {

	//联系地址
	public static String getCustomerContactAddress(Collection<AddressModel> collect) {
		return getCustomerContactAddress(collect,"CONTACT");
	}
	public static String getCustomerContactAddress(Collection<AddressModel> collect,String AddressType) {
		if (CollectionUtils.isNotEmpty(collect)) {
			for (AddressModel model : collect) {
				if (getAddressType(AddressType,model)) {
					String street = StringUtils.defaultString(model.getStreetname(), "");
					String streetNum = StringUtils.defaultString(model.getStreetnumber(), "");
					String town = StringUtils.defaultString(model.getTown(), "");
					String country = "";
					CountryModel countryModel = model.getCountry();
					if (countryModel != null) {
						country = StringUtils.defaultString(countryModel.getName(), "");
					}
					String regin = "";
					RegionModel reginModel = model.getRegion();
					if (reginModel != null) {
						regin = reginModel.getName();
					}

					StringBuilder sb = new StringBuilder(town);
					sb.append("  ");
					sb.append(regin).append(" ");
					sb.append(country);
					return sb.toString();

				}
			}

		}
		return "&nbsp;";
	}

	//联系地址对象
	public static CustomerContactAddressOfEmailData getCustomerContactAddressData(Collection<AddressModel> collect) {
		return getCustomerContactAddressData(collect,"CONTACT");
	}
	
	
	//SHIPPING/UNLOADING/BILLING/CONTACT
	public static CustomerContactAddressOfEmailData getCustomerContactAddressData(Collection<AddressModel> collect,String AddressType) {

		CustomerContactAddressOfEmailData data = new CustomerContactAddressOfEmailData();
		if (CollectionUtils.isNotEmpty(collect)) {
			for (AddressModel model : collect) {
				if (getAddressType(AddressType,model)) {
					String street = StringUtils.defaultString(model.getStreetname(), "");
					String streetNum = StringUtils.defaultString(model.getStreetnumber(), "");
					String town = StringUtils.defaultString(model.getTown(), "");
					String country = "";
					CountryModel countryModel = model.getCountry();
					if (countryModel != null) {
						country = StringUtils.defaultString(countryModel.getName(), "");
					}
					data.setCountry(country);
					data.setStreet(street);
					data.setStreetNum(streetNum);
					data.setTown(town);
					String regin = "";
					RegionModel reginModel = model.getRegion();
					if (reginModel != null) {
						regin = reginModel.getName();
					}
					
					data.setRegion(regin);
					//add contact info
					data.setContactPhone(model.getPhone1());
					data.setContactUser(model.getLastname());
					
					return data;

				}
			}

		}
		return data;
	}
	// type = SHIPPING/UNLOADING/BILLING/CONTACT
	private static boolean getAddressType(String type,AddressModel model){
		if (model !=null){
			if (type.equals("SHIPPING")){
				return model.getShippingAddress();
			}else if (type.equals("UNLOADING")){
				return model.getUnloadingAddress();
			}else if (type.equals("BILLING")){
				return model.getBillingAddress();
			}else if (type.equals("CONTACT")){
				return model.getContactAddress();
			}
		}
		return false;
	}
	
	// 获得信用期
	public static String getPaymementTerms(final OrderProcessModel orderProcessModel, final String paymentMode) {

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

	// 获得电话号码
	public static List<String> getPhoneNumbers(final Collection<UserPhoneNumberModel> coll) {
		List<String> list = new ArrayList<String>();
		if (CollectionUtils.isNotEmpty(coll)) {
			for (UserPhoneNumberModel userPhone : coll) {
				PhoneNumberModel pnm = userPhone.getPhoneNumber();
				if (pnm != null) {
					String phone = pnm.getFormat().getCode();
					if (StringUtils.isNotBlank(phone)) {
						list.add(phone);
					}
				}

			}
		}

		return list;
	}

	// number translate into words
	private static String[] smallNumbers = new String[] { "ZERO", "ONE", "TWO", "THREE", "FOUR", "FIVE", "SIX", "SEVEN",
			"EIGHT", "NINE", "TEN", "ELEVEN", "TWELVE", "THIRTEEN", "FOURTEEN", "FIFTEEN", "SIXTEEN", "SEVENTEEN",
			"EIGHTEEN", "NINETEEN" };
	private static String[] tensNumbers = new String[] { "", "", "TWENTY", "THIRTY", "FORTY", "FIFTY", "SIXTY",
			"SEVENTY", "EIGHTY", "NINETY" };
	private static String[] scaleNumers = new String[] { "", "THOUSAND", "MILLION", "BILLION" };
	private static String end = "ONLY";

	// email 所用
	public static String getMoneyOfWord(String money, String prefixWord) {
		String param = money;

		if (isNumber(param)) {
			// 只允许两位小数
			double f = Double.parseDouble(money);
			BigDecimal b = new BigDecimal(f);
			f = b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
			param = String.valueOf(f);
			if (param.lastIndexOf(".") > 0) {
				int pos = param.lastIndexOf(".");
				String pureDecimal = param.substring(pos + 1);

				if (StringUtils.equals("0", pureDecimal)) {
					param = param.substring(0, pos);
				}
			}
		} else {
			param = "0";
		}

		String prefix = StringUtils.containsIgnoreCase(prefixWord, "$") ? "say us dollors" : prefixWord;
		return StringUtils.upperCase(prefix + " " + getMoneyOfWord(param));
	}

	// 转换钱数为英文
	public static String getMoneyOfWord(String money) {
		double dMoney = Double.parseDouble(money);
		String[] arrMoney = money.split("\\.");
		// 小数点前
		int decimals1 = 0;
		// 小数点后
		int decimals2 = 0;
		// 纯小数
		if (dMoney < 1) {
			decimals1 = 0;
		} else {
			decimals1 = Integer.parseInt(arrMoney[0]);
		}

		if (arrMoney.length > 1) {
			decimals2 = Integer.parseInt(arrMoney[1]);
		}
		// 初始化显示英文为ZERO
		String combined1 = smallNumbers[0];
		String combined2 = smallNumbers[0];

		if (decimals1 != 0) {
			int[] digitGroups = new int[] { 0, 0, 0, 0 };
			//// 将金额拆分成4段，每段放3位数，即：XXX,XXX,XXX,XXX。最大仅支持到Billion，
			for (int i = 0; i < 4; i++) {
				digitGroups[i] = decimals1 % 1000;
				decimals1 = decimals1 / 1000;
			}

			String[] groupText = new String[] { "", "", "", "" };
			// 处理每段的金额转英文，百位+十位+个位
			for (int i = 0; i < 4; i++) {
				int hundreds = digitGroups[i] / 100;
				int tensUnits = digitGroups[i] % 100;
				// 百位
				if (hundreds != 0) {
					groupText[i] = groupText[i] + smallNumbers[hundreds] + " HUNDRED";
					if (tensUnits != 0) {
						groupText[i] = groupText[i] + " AND ";
					}
				}

				// 十位和个位
				int tens = tensUnits / 10;
				int units = tensUnits % 10;
				if (tens >= 2) {// 十位大于等于20
					groupText[i] = groupText[i] + tensNumbers[tens];
					if (units != 0) {
						groupText[i] = groupText[i] + " " + smallNumbers[units];
					}
				} else if(tens != 0 || units !=0) {// 十位和个位，小于20的情况
					// if (units != 0)
				     groupText[i] = groupText[i] + smallNumbers[tensUnits];

				}
			}
			// 金额的个十百位赋值到combined
			combined1 = groupText[0];
			// 将金额排除个十百位以外，余下的3段英文数字，加上千位分隔符英文单词，Thousand/Million/Billion
			for (int i = 1; i < 4; i++) {
				if (digitGroups[i] != 0) {
					String prefix = groupText[i] + " " + scaleNumers[i]; // A:组合Thousand
																			// 和Billion
					if (combined1.length() != 0) { // 如果金额的百位+十位+个位非0,则在后面加上空格
						prefix = prefix + " ";
					}
					combined1 = prefix + combined1; // 再连接 A+B

				}
			}
		}

		if (decimals2 != 0) {
			// 十位和个位
			int tens = decimals2 / 10;
			int units = decimals2 % 10;

			if (decimals2 >= 20) {
				combined2 = "CENTS " + tensNumbers[tens];
				if (units != 0) {
					combined2 = combined2 + " " + smallNumbers[units];
				}
			} else if (decimals2 > 1) {// 19到2之间
				combined2 = "CENTS " + smallNumbers[decimals2];
			} else {
				combined2 = "CENT " + smallNumbers[decimals2];
			}
		}

		if (!combined1.equals("ZERO")) {
			if (!combined2.equals("ZERO")) {
				return combined1 + " " + combined2 + " " + end;
			} else {
				return combined1 + " " + end;
			}
		} else if (!combined2.equals("ZERO")) {
			return combined2 + " " + end;
		} else {
			return "ZERO";
		}
	}

	public static boolean isNumber(String number) {
		if (number == null || "".equals(number))
			return false;
		int index = number.indexOf(".");
		if (index < 0) {
			return StringUtils.isNumeric(number);
		} else {
			String num1 = number.substring(0, index);
			String num2 = number.substring(index + 1);

			return StringUtils.isNumeric(num1) && StringUtils.isNumeric(num2);
		}
	}

	public static void main(String[] args) {
		//String s = "207.998678";
		String s = "18000.0";
		s = AcerChemEmailContextUtils.getMoneyOfWord(s, "$");

		System.out.println(s);
		// String s = new StringBuilder("aaaa").append("
		// \0").append("bbbbbb").toString();
		// System.out.println(s);
		// String x[] = s.split(" ");
		// System.out.println(x.length);
		// for(String y:x){
		// System.out.println(y);
		// }
		// DecimalFormat df = new DecimalFormat();
		// df.applyPattern(",##0.00");// 将格式应用于格式化器
		// BigDecimal d = new BigDecimal(12312.2345);
		// BigDecimal d1 = new BigDecimal(12312312.2);
		// String s = df.format(d);
		// System.out.println(s);
		// String s1 = df.format(d1);
		// System.out.println(s1);

	}

}
