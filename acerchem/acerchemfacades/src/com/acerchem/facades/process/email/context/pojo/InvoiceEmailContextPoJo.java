package com.acerchem.facades.process.email.context.pojo;

import java.util.List;

public class InvoiceEmailContextPoJo {

	private String taxNo;
	private String taxInfo; 
	private String paymentTerms;
	private String deliveryTerms;
	private String acerChemTaxNo;
	private String acerChemCompanyNo;
	private ProductTotalDataOfEmail totalData;
	private List<ProductItemDataOfEmail> productLists;
	

	public String getTaxNo() {
		return taxNo;
	}

	public void setTaxNo(String taxNo) {
		this.taxNo = taxNo;
	}

	public String getTaxInfo() {
		return taxInfo;
	}

	public void setTaxInfo(String taxInfo) {
		this.taxInfo = taxInfo;
	}

	public String getPaymentTerms() {
		return paymentTerms;
	}

	public void setPaymentTerms(String paymentTerms) {
		this.paymentTerms = paymentTerms;
	}

	public String getDeliveryTerms() {
		return deliveryTerms;
	}

	public void setDeliveryTerms(String deliveryTerms) {
		this.deliveryTerms = deliveryTerms;
	}

	public String getAcerChemTaxNo() {
		return acerChemTaxNo;
	}

	public void setAcerChemTaxNo(String acerChemTaxNo) {
		this.acerChemTaxNo = acerChemTaxNo;
	}

	public String getAcerChemCompanyNo() {
		return acerChemCompanyNo;
	}

	public void setAcerChemCompanyNo(String acerChemCompanyNo) {
		this.acerChemCompanyNo = acerChemCompanyNo;
	}

	public ProductTotalDataOfEmail getTotalData() {
		return totalData;
	}

	public void setTotalData(ProductTotalDataOfEmail totalData) {
		this.totalData = totalData;
	}

	public List<ProductItemDataOfEmail> getProductLists() {
		return productLists;
	}

	public void setProductLists(List<ProductItemDataOfEmail> productLists) {
		this.productLists = productLists;
	}
	
	
}
