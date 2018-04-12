package com.acerchem.facades.process.email.context.pojo;

public class ProductItemDataOfEmail {
	private String productCode;
	private String productName;
	private String batchNo;
	private String quantity;
	private String netWeight;
	private String grossWeight; 
	private boolean isTotal;
	
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
	
	
	

}
