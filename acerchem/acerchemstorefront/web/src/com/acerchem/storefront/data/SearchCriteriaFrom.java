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
	private String vendorCode;
	private String productName;
	private String productCode;
	private String productCharacteristic;
	private String warehouseCode;
	private String employeeName;
	private String deliveryModeCode;

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

	public String getVendorCode() {
		return vendorCode;
	}

	public void setVendorCode(String vendorCode) {
		this.vendorCode = vendorCode;
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

	public String getProductCharacteristic() {
		return productCharacteristic;
	}

	public void setProductCharacteristic(String productCharacteristic) {
		this.productCharacteristic = productCharacteristic;
	}

	public String getWarehouseCode() {
		return warehouseCode;
	}

	public void setWarehouseCode(String warehouseCode) {
		this.warehouseCode = warehouseCode;
	}

	public String getEmployeeName() {
		return employeeName;
	}

	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}

	public String getDeliveryModeCode() {
		return deliveryModeCode;
	}

	public void setDeliveryModeCode(String deliveryModeCode) {
		this.deliveryModeCode = deliveryModeCode;
	}
}




