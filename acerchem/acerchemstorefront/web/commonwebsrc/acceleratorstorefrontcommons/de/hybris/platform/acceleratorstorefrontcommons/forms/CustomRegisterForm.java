package de.hybris.platform.acceleratorstorefrontcommons.forms;

/**
 * Alice: Form object for registration
 */
public class CustomRegisterForm
{
	private String email;
	private String pwd;
	private String checkPwd;
	private String name;
	private String currency;
	private String language;
	private String telephone;
	private String mobileNumber;
	private String contacts;
	private AddressForm shipAddress;
	private AddressForm contactAddress;

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

	public String getContacts()
	{
		return contacts;
	}

	public void setContacts(final String contacts)
	{
		this.contacts = contacts;
	}

	public String getTelephone()
	{
		return telephone;
	}

	public void setTelephone(final String telephone)
	{
		this.telephone = telephone;
	}

	public String getMobileNumber()
	{
		return mobileNumber;
	}

	public void setMobileNumber(final String mobileNumber)
	{
		this.mobileNumber = mobileNumber;
	}

}

