package com.acerchem.storefront.data;

import java.io.Serializable;
import java.math.BigDecimal;

import de.hybris.platform.acceleratorstorefrontcommons.forms.AddressForm;

/**
 * Alice: Form object for registration
 */
public class CustomRegisterForm implements Serializable
{
	private String email;
	private String pwd;
	private String checkPwd;
	private String name;
	private String currency;
	private String language;
	private String telephone;
	//private String mobileNumber;
	//private String contacts;
	private AddressForm shipAddress;
	private AddressForm contactAddress;
	
	private String companyType;
	private String establishedIn;
	private BigDecimal revenue;
	private Integer employeesNo;
	private BigDecimal limitCreditAmount;
	private String vatNo;
	private boolean haveFinancialReport;
	private boolean provideTradeReference;
	private String companyName;
	
	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	
	public String getCompanyType() {
		return companyType;
	}

	public void setCompanyType(String companyType) {
		this.companyType = companyType;
	}

	public String getEstablishedIn() {
		return establishedIn;
	}

	public void setEstablishedIn(String establishedIn) {
		this.establishedIn = establishedIn;
	}

	public BigDecimal getRevenue() {
		return revenue;
	}

	public void setRevenue(BigDecimal revenue) {
		this.revenue = revenue;
	}

	public Integer getEmployeesNo() {
		return employeesNo;
	}

	public void setEmployeesNo(Integer employeesNo) {
		this.employeesNo = employeesNo;
	}

	public BigDecimal getLimitCreditAmount() {
		return limitCreditAmount;
	}

	public void setLimitCreditAmount(BigDecimal limitCreditAmount) {
		this.limitCreditAmount = limitCreditAmount;
	}

	public String getVatNo() {
		return vatNo;
	}

	public void setVatNo(String vatNo) {
		this.vatNo = vatNo;
	}


	public boolean getHaveFinancialReport() {
		return haveFinancialReport;
	}

	public void setHaveFinancialReport(boolean haveFinancialReport) {
		this.haveFinancialReport = haveFinancialReport;
	}
	
	public boolean getProvideTradeReference() {
		return provideTradeReference;
	}

	public void setProvideTradeReference(boolean provideTradeReference) {
		this.provideTradeReference = provideTradeReference;
	}
	
	public AddressForm getShipAddress()
	{
		return shipAddress;
	}

	public void setShipAddress(final AddressForm shipAddress)
	{
		this.shipAddress = shipAddress;
	}

	public AddressForm getContactAddress()
	{
		return contactAddress;
	}

	public void setContactAddress(final AddressForm contactAddress)
	{
		this.contactAddress = contactAddress;
	}

	public String getEmail()
	{
		return email;
	}

	public void setEmail(final String email)
	{
		this.email = email;
	}

	public String getPwd()
	{
		return pwd;
	}

	public void setPwd(final String pwd)
	{
		this.pwd = pwd;
	}

	public String getCheckPwd()
	{
		return checkPwd;
	}

	public void setCheckPwd(final String checkPwd)
	{
		this.checkPwd = checkPwd;
	}

	public String getName()
	{
		return name;
	}

	public void setName(final String name)
	{
		this.name = name;
	}

	public String getCurrency()
	{
		return currency;
	}

	public void setCurrency(final String currency)
	{
		this.currency = currency;
	}

	public String getLanguage()
	{
		return language;
	}

	public void setLanguage(final String language)
	{
		this.language = language;
	}

	/*public String getContacts()
	{
		return contacts;
	}

	public void setContacts(final String contacts)
	{
		this.contacts = contacts;
	}*/

	public String getTelephone()
	{
		return telephone;
	}

	public void setTelephone(final String telephone)
	{
		this.telephone = telephone;
	}

	/*public String getMobileNumber()
	{
		return mobileNumber;
	}

	public void setMobileNumber(final String mobileNumber)
	{
		this.mobileNumber = mobileNumber;
	}*/

}




