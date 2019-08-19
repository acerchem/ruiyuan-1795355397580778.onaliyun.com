package com.acerchem.storefront.data;

public class ProductSalesForm {

	private String month;
	private String categoryCode;
	private String area;
	private String countryCode;
	private String productName;
	private String productCode;
	private String employeeName;
	public String getMonth() {
		return month;
	}
	public void setMonth(final String month) {
		this.month = month;
	}
	public String getCategoryCode() {
		return categoryCode;
	}
	public void setCategoryCode(final String categoryCode) {
		this.categoryCode = categoryCode;
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
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getProductCode() {
		return productCode;
	}
	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}
	public String getEmployeeName() {
		return employeeName;
	}
	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}
}
