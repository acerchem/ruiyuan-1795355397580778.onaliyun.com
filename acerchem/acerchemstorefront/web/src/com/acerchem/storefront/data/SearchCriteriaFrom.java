package com.acerchem.storefront.data;

import java.io.Serializable;

/**
 * Alice: Form object for Reports
 */
@SuppressWarnings("serial")
public class SearchCriteriaFrom implements Serializable{

	private Integer pageNumber;
	private String month;
	private String area;
	private String countryCode;
	private String userName;   
	private String orderCode;
	private String cutomerCompanyName;
	
	public Integer getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(final Integer pageNumber) {
		this.pageNumber = pageNumber;
	}
	
	public String getMonth() {
		return month;
	}

	public void setMonth(final String month) {
		this.month = month;
	}
	
	public String getArea() {
		return area;
	}

	public void setArea(final String area) {
		this.area = area;
	}
	
	public String getCountryCode() {
		return countryCode;
	}

	public void setCountryCode(final String countryCode) {
		this.countryCode = countryCode;
	}
	
	public String getUserName() {
		return userName;
	}

	public void setUserName(final String userName) {
		this.userName = userName;
	}
	
	public String getOrderCode() {
		return orderCode;
	}

	public void setOrderCode(final String orderCode) {
		this.orderCode = orderCode;
	}

	public String getCutomerCompanyName() {
		return cutomerCompanyName;
	}

	public void setCutomerCompanyName(final String cutomerCompanyName) {
		this.cutomerCompanyName = cutomerCompanyName;
	}

}




