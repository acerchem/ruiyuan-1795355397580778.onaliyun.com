package com.acerchem.core.report.order.beans;

public class AcerchemProductBuyerBean {

	private String productCode;
	private String productName;
	private String buyer;
	private Long buyQuantity;
	private String month;// yyyyMM
	private String countryOfBuyer;
	private String areaOfBuyer;
	private String category;

	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(final String productCode) {
		this.productCode = productCode;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(final String productName) {
		this.productName = productName;
	}

	public String getBuyer() {
		return buyer;
	}

	public void setBuyer(final String buyer) {
		this.buyer = buyer;
	}

	public Long getBuyQuantity() {
		return buyQuantity;
	}

	public void setBuyQuantity(final Long buyQuantity) {
		this.buyQuantity = buyQuantity;
	}

	public String getMonth() {
		return month;
	}

	public void setMonth(final String month) {
		this.month = month;
	}

	public String getCountryOfBuyer() {
		return countryOfBuyer;
	}

	public void setCountryOfBuyer(final String countryOfBuyer) {
		this.countryOfBuyer = countryOfBuyer;
	}

	public String getAreaOfBuyer() {
		return areaOfBuyer;
	}

	public void setAreaOfBuyer(final String areaOfBuyer) {
		this.areaOfBuyer = areaOfBuyer;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(final String category) {
		this.category = category;
	}

}
