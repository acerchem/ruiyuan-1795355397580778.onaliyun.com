package com.acerchem.facades.process.email.context.pojo;

public class ProductItemDataOfEmail {
	private String productCode;
	private String productName;
	private String batchNo;
	private String quantity;
	private String netWeight;
	private String grossWeight; 
	private boolean isTotal;
	
	private String weightUnit;
	private String price; //单价
	private String amount;//单种产品的价格小计 =price*quantity
	private String packageWeight;//单种产品的包裹重量
	
	public String getProductCode() {
		return productCode;
	}
	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getBatchNo() {
		return batchNo;
	}
	public void setBatchNo(String batchNo) {
		this.batchNo = batchNo;
	}
	public String getQuantity() {
		return quantity;
	}
	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}
	public String getNetWeight() {
		return netWeight;
	}
	public void setNetWeight(String netWeight) {
		this.netWeight = netWeight;
	}
	public String getGrossWeight() {
		return grossWeight;
	}
	public void setGrossWeight(String grossWeight) {
		this.grossWeight = grossWeight;
	}
	public boolean isTotal() {
		return isTotal;
	}
	public void setTotal(boolean isTotal) {
		this.isTotal = isTotal;
	}
	/**
	 * @return the weightUnit
	 */
	public String getWeightUnit() {
		return weightUnit;
	}
	/**
	 * @param weightUnit the weightUnit to set
	 */
	public void setWeightUnit(String weightUnit) {
		this.weightUnit = weightUnit;
	}
	/**
	 * @return the price
	 */
	public String getPrice() {
		return price;
	}
	/**
	 * @param price the price to set
	 */
	public void setPrice(String price) {
		this.price = price;
	}
	/**
	 * @return the amount
	 */
	public String getAmount() {
		return amount;
	}
	/**
	 * @param amount the amount to set
	 */
	public void setAmount(String amount) {
		this.amount = amount;
	}
	/**
	 * @return the packageWeight
	 */
	public String getPackageWeight() {
		return packageWeight;
	}
	/**
	 * @param packageWeight the packageWeight to set
	 */
	public void setPackageWeight(String packageWeight) {
		this.packageWeight = packageWeight;
	}
	
	
	

}
