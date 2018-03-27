package com.acerchem.core.service.impl;

import java.util.List;

import javax.annotation.Resource;

import com.acerchem.core.dao.AcerChemProductDao;
import com.acerchem.core.service.AcerChemProductService;

import de.hybris.platform.core.model.product.ProductModel;

public class AcerChemProductServiceImpl implements AcerChemProductService {

	@Resource
	private AcerChemProductDao acerChemProductDao;
	@Override
	public List<ProductModel> getProductByVendorName(String vendorName) {
		// TODO Auto-generated method stub
		return acerChemProductDao.getProductByVendorName(vendorName);
	}

	@Override
	public List<ProductModel> getProductByVendorCode(String vendorCode) {
		// TODO Auto-generated method stub
		return acerChemProductDao.getProductByVendorCode(vendorCode);
	}

	@Override
	public boolean isExistProductWithVendor(String productCode, String vendorCode) {
		// TODO Auto-generated method stub
		return acerChemProductDao.isExistProductWithVendor(productCode, vendorCode);
	}

}
