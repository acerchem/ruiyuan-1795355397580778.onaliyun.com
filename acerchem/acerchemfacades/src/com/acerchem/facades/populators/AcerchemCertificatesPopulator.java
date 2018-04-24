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
package com.acerchem.facades.populators;

import de.hybris.platform.converters.Populator;
import de.hybris.platform.core.model.media.MediaModel;

import org.springframework.util.Assert;

import com.acerchem.facades.product.data.CertificatesData;


/**
 * Converter implementation for {@link de.hybris.platform.core.model.media.MediaModel} as source and {@link de.hybris.platform.commercefacades.product.data.ImageData} as target type.
 */
public class AcerchemCertificatesPopulator implements Populator<MediaModel, CertificatesData>
{

	@Override
	public void populate(final MediaModel source, final CertificatesData target)
	{
		/*Assert.notNull(source, "Parameter source cannot be null.");
		Assert.notNull(target, "Parameter target cannot be null.");*/

		if (source != null){
			
			target.setUrl(source.getURL());
			target.setAltText(source.getAltText());
		}
		
	/*	if (source.getMediaFormat() != null)
		{
			target.setFormat(source.getMediaFormat().getQualifier());
		}*/
	}
}
