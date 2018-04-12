package com.acerchem.facades.process.email.context.pojo;

import java.util.List;

public class ReleaseNoteEmailContextPoJo {
	
	private String pickupAddress;
	private String releaseContactUser;
	private String releasePhone;
	private String email1;
	private String email2;
	
	private List<ProductItemDataOfEmail> productLists;
	private ProductTotalDataOfEmail totalData;
	//private List<>
	
	public String getPickupAddress() {
		return pickupAddress;
	}
	public void setPickupAddress(String pickupAddress) {
		this.pickupAddress = pickupAddress;
	}
	public String getReleaseContactUser() {
		return releaseContactUser;
	}
	public void setReleaseContactUser(String releaseContactUser) {
		this.releaseContactUser = releaseContactUser;
	}
	public String getReleasePhone() {
		return releasePhone;
	}
	public void setReleasePhone(String releasePhone) {
		this.releasePhone = releasePhone;
	}
	public String getEmail1() {
		return email1;
	}
	public void setEmail1(String email1) {
		this.email1 = email1;
	}
	public String getEmail2() {
		return email2;
	}
	public void setEmail2(String email2) {
		this.email2 = email2;
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
	
	
	

}
