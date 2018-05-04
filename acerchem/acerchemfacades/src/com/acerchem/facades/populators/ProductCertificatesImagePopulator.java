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

import com.acerchem.facades.product.data.CertificatessData;

import java.util.Collection;
import java.util.List;
import java.util.ArrayList;;



public class ProductCertificatesImagePopulator<SOURCE extends ProductModel, TARGET extends ProductData> 
extends AbstractProductPopulator<SOURCE, TARGET>
{
	
	private Converter<MediaModel, CertificatessData> certificatesConverter;
	

	
	/**
	 * @return the certificatesConverter
	 */
	public Converter<MediaModel, CertificatessData> getCertificatesConverter() {
		return certificatesConverter;
	}



	/**
	 * @param certificatesConverter the certificatesConverter to set
	 */
	public void setCertificatesConverter(Converter<MediaModel, CertificatessData> certificatesConverter) {
		this.certificatesConverter = certificatesConverter;
	}



	@Override
	public void populate(final SOURCE productModel, final TARGET productData) throws ConversionException
	{
		//final MediaModel media = productModel.getCertificates();
		
		final Collection<MediaModel> medias = productModel.getCertificatess();
		
		List<CertificatessData>  datas = new ArrayList<CertificatessData>();
		
		if (medias != null){
			
			for(MediaModel media:medias){
				
				final CertificatessData data = getCertificatesConverter().convert(media);
				
				datas.add(data);

			}
			
			productData.setCertificatess(datas);
			
		}
		
		
		
	
	}

	
}
