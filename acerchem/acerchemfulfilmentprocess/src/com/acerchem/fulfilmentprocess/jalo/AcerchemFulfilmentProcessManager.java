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
package com.acerchem.fulfilmentprocess.jalo;

import de.hybris.platform.jalo.JaloSession;
import de.hybris.platform.jalo.extension.ExtensionManager;
import com.acerchem.fulfilmentprocess.constants.AcerchemFulfilmentProcessConstants;

@SuppressWarnings("PMD")
public class AcerchemFulfilmentProcessManager extends GeneratedAcerchemFulfilmentProcessManager
{
	public static final AcerchemFulfilmentProcessManager getInstance()
	{
		ExtensionManager em = JaloSession.getCurrentSession().getExtensionManager();
		return (AcerchemFulfilmentProcessManager) em.getExtension(AcerchemFulfilmentProcessConstants.EXTENSIONNAME);
	}
	
}
