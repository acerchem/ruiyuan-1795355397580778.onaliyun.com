package com.acerchem.facades.process.email.context.pojo;

import java.util.List;

public class ContractEmailContextPoJo {
	private ProductTotalDataOfEmail totalData;
	private List<ProductItemDataOfEmail> productLists;
	
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
	
	
	

}
