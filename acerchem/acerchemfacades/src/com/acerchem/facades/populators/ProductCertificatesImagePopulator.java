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

import de.hybris.platform.commercefacades.product.converters.populator.AbstractProductPopulator;

import de.hybris.platform.commercefacades.product.data.ProductData;
import de.hybris.platform.core.model.media.MediaModel;
import de.hybris.platform.core.model.product.ProductModel;
import de.hybris.platform.servicelayer.dto.converter.ConversionException;
import de.hybris.platform.servicelayer.dto.converter.Converter;

import com.acerchem.facades.product.data.CertificatesData;


public class ProductCertificatesImagePopulator<SOURCE extends ProductModel, TARGET extends ProductData> 
extends AbstractProductPopulator<SOURCE, TARGET>
{
	
	private Converter<MediaModel, CertificatesData> certificatesConverter;
	

	
	/**
	 * @return the certificatesConverter
	 */
	public Converter<MediaModel, CertificatesData> getCertificatesConverter() {
		return certificatesConverter;
	}



	/**
	 * @param certificatesConverter the certificatesConverter to set
	 */
	public void setCertificatesConverter(Converter<MediaModel, CertificatesData> certificatesConverter) {
		this.certificatesConverter = certificatesConverter;
	}



	@Override
	public void populate(final SOURCE productModel, final TARGET productData) throws ConversionException
	{
		final MediaModel media = productModel.getCertificates();
		
		final CertificatesData imageData = getCertificatesConverter().convert(media);
		
		productData.setCertificates(imageData);
	}

	
}
