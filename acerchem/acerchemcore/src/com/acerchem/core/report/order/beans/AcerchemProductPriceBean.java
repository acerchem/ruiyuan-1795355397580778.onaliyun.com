package com.acerchem.core.report.order.beans;

import java.util.Date;

public class AcerchemProductPriceBean {
	private String productCode;
	private String productName;
	private Long saleQuantity;
	private Double baseRealPrice;
	private Date orderPlaceTime;
	private int weeknum;

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

	public Long getSaleQuantity() {
		return saleQuantity;
	}

	public void setSaleQuantity(final Long saleQuantity) {
		this.saleQuantity = saleQuantity;
	}

	public Double getBaseRealPrice() {
		return baseRealPrice;
	}

	public void setBaseRealPrice(final Double baseRealPrice) {
		this.baseRealPrice = baseRealPrice;
	}

	public Date getOrderPlaceTime() {
		return orderPlaceTime;
	}

	public void setOrderPlaceTime(final Date orderPlaceTime) {
		this.orderPlaceTime = orderPlaceTime;
	}

	public int getWeeknum() {
		return weeknum;
	}

	public void setWeeknum(final int weeknum) {
		this.weeknum = weeknum;
	}

	

}
