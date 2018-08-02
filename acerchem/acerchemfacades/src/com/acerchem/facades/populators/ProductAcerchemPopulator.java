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

import com.acerchem.facades.product.data.VendorData;

import de.hybris.platform.commercefacades.product.converters.populator.AbstractProductPopulator;
import de.hybris.platform.commercefacades.product.data.ProductData;
import de.hybris.platform.core.model.product.ProductModel;
import de.hybris.platform.core.model.product.UnitModel;
import de.hybris.platform.servicelayer.dto.converter.ConversionException;

/**
 * Populate the product data with the product's summary description
 */
public class ProductAcerchemPopulator<SOURCE extends ProductModel, TARGET extends ProductData> extends AbstractProductPopulator<SOURCE, TARGET>
{
	@Override
	public void populate(final SOURCE productModel, final TARGET productData) throws ConversionException
	{
		productData.setChemicalInfo(safeToString(getProductAttribute(productModel, ProductModel.CHEMICALINFO)));
		productData.setUnitCalculateRato(safeToString(getProductAttribute(productModel, ProductModel.CHEMICALINFO)));
		
		productData.setPackageType(safeToString(getProductAttribute(productModel, ProductModel.PACKAGETYPE)));
		productData.setPackageWeight(safeToString(getProductAttribute(productModel, ProductModel.PACKAGEWEIGHT)));
		productData.setNetWeight(safeToString(getProductAttribute(productModel, ProductModel.NETWEIGHT)));
		productData.setGrossWeight(safeToString(getProductAttribute(productModel, ProductModel.GROSSWEIGHT)));
		
		productData.setCAS(safeToString(getProductAttribute(productModel, ProductModel.CAS)));
		
		productData.setSpecification(safeToString(getProductAttribute(productModel, ProductModel.SPECIFICATION)));
		
		productData.setOtherName(safeToString(getProductAttribute(productModel, ProductModel.OTHERNAME)));
		
		productData.setFormulaWeight(safeToString(getProductAttribute(productModel, ProductModel.FORMULAWEIGHT)));
		
//		frank gu
		productData.setSupplierInfo00(safeToString(getProductAttribute(productModel, ProductModel.ATTRIBUTEZERO)));
		productData.setSupplierInfo01(safeToString(getProductAttribute(productModel, productModel.DESCRIBEZERO)));
		productData.setSupplierInfo02(safeToString(getProductAttribute(productModel, productModel.ATTRIBUTEONE)));
		productData.setSupplierInfo03(safeToString(getProductAttribute(productModel, productModel.DESCRIBEONE)));
		productData.setSupplierInfo04(safeToString(getProductAttribute(productModel, productModel.ATTRIBUTETWO)));
		productData.setSupplierInfo05(safeToString(getProductAttribute(productModel, productModel.DESCRIBETWO)));
		productData.setSupplierInfo06(safeToString(getProductAttribute(productModel, productModel.ATTRIBUTETHREE)));
		productData.setSupplierInfo07(safeToString(getProductAttribute(productModel, productModel.DESCRIBETHREE)));
		productData.setSupplierInfo08(safeToString(getProductAttribute(productModel, productModel.ATTRIBUTEFOUR)));
		productData.setSupplierInfo09(safeToString(getProductAttribute(productModel, productModel.DESCRIBEFOUR)));
		productData.setSupplierInfo10(safeToString(getProductAttribute(productModel, productModel.ATTRIBUTEFIVE)));
		productData.setSupplierInfo11(safeToString(getProductAttribute(productModel, productModel.DESCRIBEFIVE)));
		productData.setSupplierInfo12(safeToString(getProductAttribute(productModel, productModel.ATTRIBUTESIX)));
		productData.setSupplierInfo13(safeToString(getProductAttribute(productModel, productModel.DESCRIBESIX)));
		productData.setSupplierInfo14(safeToString(getProductAttribute(productModel, productModel.ATTRIBUTESEVEN)));
		productData.setSupplierInfo15(safeToString(getProductAttribute(productModel, productModel.DESCRIBESEVEN)));
		productData.setSupplierInfo16(safeToString(getProductAttribute(productModel, productModel.ATTRIBUTEEIGHT)));
		productData.setSupplierInfo17(safeToString(getProductAttribute(productModel, productModel.DESCRIBEEIGHT)));
		productData.setSupplierInfo18(safeToString(getProductAttribute(productModel, productModel.ATTRIBUTENINE)));
		productData.setSupplierInfo19(safeToString(getProductAttribute(productModel, productModel.DESCRIBENINE)));
		
		
		VendorData data = new VendorData();
		if (productModel.getAcerChemVendor()!= null){
			data.setName(productModel.getAcerChemVendor().getName());
	        data.setCode(productModel.getAcerChemVendor().getCode());
	        
	        productData.setVendor(data);
	      }
		
	    
				
		
		UnitModel uModel = productModel.getUnit();
		if (uModel != null){
			productData.setUnitName(uModel.getName());
		}
		
	}
}
