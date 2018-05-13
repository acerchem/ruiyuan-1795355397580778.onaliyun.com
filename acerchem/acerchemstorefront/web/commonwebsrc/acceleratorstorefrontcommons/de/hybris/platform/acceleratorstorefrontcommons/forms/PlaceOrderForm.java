/*
 * [y] hybris Platform
 *
 * Copyright (c) 2017 SAP SE or an SAP affiliate company.  All rights reserved.
 *
 * This software is the confidential and proprietary information of SAP
 * ("Confidential Information"). You shall not disclose such Confidential
 * Information and shall use it only in accordance with the terms of the
 * license agreement you entered into with SAP.
 */
package de.hybris.platform.acceleratorstorefrontcommons.forms;


public class PlaceOrderForm
{
	private String securityCode;
	private boolean termsCheck;

	private String paymentCode;

	/**
	 * @return the paymentCode
	 */
	public String getPaymentCode()
	{
		return paymentCode;
	}

	/**
	 * @param paymentCode
	 *           the paymentCode to set
	 */
	public void setPaymentCode(final String paymentCode)
	{
		this.paymentCode = paymentCode;
	}

	public String getSecurityCode()
	{
		return securityCode;
	}

	public void setSecurityCode(final String securityCode)
	{
		this.securityCode = securityCode;
	}

	public boolean isTermsCheck()
	{
		return termsCheck;
	}

	public void setTermsCheck(final boolean termsCheck)
	{
		this.termsCheck = termsCheck;
	}

}
