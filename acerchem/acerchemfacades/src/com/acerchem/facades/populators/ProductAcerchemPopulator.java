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

import java.math.BigDecimal;
import javax.annotation.Resource;

import com.acerchem.facades.product.data.VendorData;

import de.hybris.platform.commercefacades.product.converters.populator.AbstractProductPopulator;
import de.hybris.platform.commercefacades.product.data.PriceData;
import de.hybris.platform.commercefacades.product.data.ProductData;
import de.hybris.platform.core.model.product.ProductModel;
import de.hybris.platform.core.model.product.UnitModel;
import de.hybris.platform.core.model.user.UserModel;
import de.hybris.platform.servicelayer.dto.converter.ConversionException;
import de.hybris.platform.servicelayer.user.UserService;

/**
 * Populate the product data with the product's summary description
 */
public class ProductAcerchemPopulator<SOURCE extends ProductModel, TARGET extends ProductData> extends AbstractProductPopulator<SOURCE, TARGET>
{
    @Resource
	private UserService userService;
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
		final UserModel user = userService.getCurrentUser();
		final boolean isAnonymousUser = userService.isAnonymousUser(user);
		if(!isAnonymousUser)
		{
			productData.setSupplierInfo00(safeToString(getProductAttribute(productModel, ProductModel.ATTRIBUTEZERO)));
			productData.setSupplierInfo01(safeToString(getProductAttribute(productModel, ProductModel.DESCRIBEZERO)));
			productData.setSupplierInfo02(safeToString(getProductAttribute(productModel, ProductModel.ATTRIBUTEONE)));
			productData.setSupplierInfo03(safeToString(getProductAttribute(productModel, ProductModel.DESCRIBEONE)));
			productData.setSupplierInfo04(safeToString(getProductAttribute(productModel, ProductModel.ATTRIBUTETWO)));
			productData.setSupplierInfo05(safeToString(getProductAttribute(productModel, ProductModel.DESCRIBETWO)));
			productData.setSupplierInfo06(safeToString(getProductAttribute(productModel, ProductModel.ATTRIBUTETHREE)));
			productData.setSupplierInfo07(safeToString(getProductAttribute(productModel, ProductModel.DESCRIBETHREE)));
			productData.setSupplierInfo08(safeToString(getProductAttribute(productModel, ProductModel.ATTRIBUTEFOUR)));
			productData.setSupplierInfo09(safeToString(getProductAttribute(productModel, ProductModel.DESCRIBEFOUR)));
			productData.setSupplierInfo10(safeToString(getProductAttribute(productModel, ProductModel.ATTRIBUTEFIVE)));
			productData.setSupplierInfo11(safeToString(getProductAttribute(productModel, ProductModel.DESCRIBEFIVE)));
			productData.setSupplierInfo12(safeToString(getProductAttribute(productModel, ProductModel.ATTRIBUTESIX)));
			productData.setSupplierInfo13(safeToString(getProductAttribute(productModel, ProductModel.DESCRIBESIX)));
			productData.setSupplierInfo14(safeToString(getProductAttribute(productModel, ProductModel.ATTRIBUTESEVEN)));
			productData.setSupplierInfo15(safeToString(getProductAttribute(productModel, ProductModel.DESCRIBESEVEN)));
			productData.setSupplierInfo16(safeToString(getProductAttribute(productModel, ProductModel.ATTRIBUTEEIGHT)));
			productData.setSupplierInfo17(safeToString(getProductAttribute(productModel, ProductModel.DESCRIBEEIGHT)));
			productData.setSupplierInfo18(safeToString(getProductAttribute(productModel, ProductModel.ATTRIBUTENINE)));
			productData.setSupplierInfo19(safeToString(getProductAttribute(productModel, ProductModel.DESCRIBENINE)));
		}
		
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
