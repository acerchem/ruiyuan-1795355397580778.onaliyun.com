package com.acerchem.storefront.data;

public class VendorAnalysisForm {

	private String startDate;
	private String endDate;
	private String vendor;
	private boolean isVendorFlag;
	private String vendorName;
	
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(final String startDate) {
		this.startDate = startDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(final String endDate) {
		this.endDate = endDate;
	}
	public String getVendor() {
		return vendor;
	}
	public void setVendor(final String vendor) {
		this.vendor = vendor;
	}
	public boolean isVendorFlag() {
		return isVendorFlag;
	}
	public void setVendorFlag(final boolean isVendorFlag) {
		this.isVendorFlag = isVendorFlag;
	}
	public String getVendorName() {
		return vendorName;
	}
	public void setVendorName(final String vendorName) {
		this.vendorName = vendorName;
	}
	
	
	
}
