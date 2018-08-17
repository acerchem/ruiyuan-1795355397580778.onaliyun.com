package com.acerchem.storefront.data;

import java.io.Serializable;

public class EmployeeMonthlySalesBean implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private String empName;
	private Double janAmount;
	private Double febAmount;
	private Double marAmount;
	private Double aprAmount;
	private Double mayAmount;
	private Double junAmount;
	private Double julAmount;
	private Double augAmount;
	private Double sepAmount;
	private Double octAmount;
	private Double novAmount;
	private Double decAmount;
	private Double subTotal;
	
	public EmployeeMonthlySalesBean(){
		
	}

	public String getEmpName() {
		return empName;
	}

	public void setEmpName(final String empName) {
		this.empName = empName;
	}

	public Double getJanAmount() {
		return janAmount;
	}

	public void setJanAmount(final Double janAmount) {
		this.janAmount = janAmount;
	}

	public Double getFebAmount() {
		return febAmount;
	}

	public void setFebAmount(final Double febAmount) {
		this.febAmount = febAmount;
	}

	public Double getMarAmount() {
		return marAmount;
	}

	public void setMarAmount(final Double marAmount) {
		this.marAmount = marAmount;
	}

	public Double getAprAmount() {
		return aprAmount;
	}

	public void setAprAmount(final Double aprAmount) {
		this.aprAmount = aprAmount;
	}

	public Double getMayAmount() {
		return mayAmount;
	}

	public void setMayAmount(final Double mayAmount) {
		this.mayAmount = mayAmount;
	}

	public Double getJunAmount() {
		return junAmount;
	}

	public void setJunAmount(final Double junAmount) {
		this.junAmount = junAmount;
	}

	public Double getJulAmount() {
		return julAmount;
	}

	public void setJulAmount(final Double julAmount) {
		this.julAmount = julAmount;
	}

	public Double getAugAmount() {
		return augAmount;
	}

	public void setAugAmount(final Double augAmount) {
		this.augAmount = augAmount;
	}

	public Double getSepAmount() {
		return sepAmount;
	}

	public void setSepAmount(final Double sepAmount) {
		this.sepAmount = sepAmount;
	}

	public Double getOctAmount() {
		return octAmount;
	}

	public void setOctAmount(final Double octAmount) {
		this.octAmount = octAmount;
	}

	public Double getNovAmount() {
		return novAmount;
	}

	public void setNovAmount(final Double novAmount) {
		this.novAmount = novAmount;
	}

	public Double getDecAmount() {
		return decAmount;
	}

	public void setDecAmount(final Double decAmount) {
		this.decAmount = decAmount;
	}

	public Double getSubTotal() {
		return subTotal;
	}

	public void setSubTotal(final Double subTotal) {
		this.subTotal = subTotal;
	}
	
	

}
