package com.acerchem.facades.process.email.context.pojo;

import java.util.List;

public class ContractEmailContextPoJo {
	private ProductTotalDataOfEmail totalData;
	private List<ProductItemDataOfEmail> productLists;
	private String paymentTerms;
	private String warehouse;
	
	/**
	 * @return the totalData
	 */
	public ProductTotalDataOfEmail getTotalData() {
		return totalData;
	}
	/**
	 * @param totalData the totalData to set
	 */
	public void setTotalData(ProductTotalDataOfEmail totalData) {
		this.totalData = totalData;
	}
	/**
	 * @return the productLists
	 */
	public List<ProductItemDataOfEmail> getProductLists() {
		return productLists;
	}
	/**
	 * @param productLists the productLists to set
	 */
	public void setProductLists(List<ProductItemDataOfEmail> productLists) {
		this.productLists = productLists;
	}
	/**
	 * @return the paymentTerms
	 */
	public String getPaymentTerms() {
		return paymentTerms;
	}
	/**
	 * @param paymentTerms the paymentTerms to set
	 */
	public void setPaymentTerms(String paymentTerms) {
		this.paymentTerms = paymentTerms;
	}
	/**
	 * @return the warehouse
	 */
	public String getWarehouse() {
		return warehouse;
	}
	/**
	 * @param warehouse the warehouse to set
	 */
	public void setWarehouse(String warehouse) {
		this.warehouse = warehouse;
	}
	
	
	

}
