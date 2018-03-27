package com.acerchem.core.service.impl;

import java.util.List;

import javax.annotation.Resource;

import com.acerchem.core.dao.AcerChemVendorDao;
import com.acerchem.core.service.AcerChemVendorService;

import de.hybris.platform.ordersplitting.model.VendorModel;

public class AcerChemVendorServiceImpl implements AcerChemVendorService {

	@Resource
	private AcerChemVendorDao acerChemVendorDao;
	@Override
	public List<VendorModel> getVendorsByProductName(String productName) {
		// TODO Auto-generated method stub
		return acerChemVendorDao.getVendorsByProductName(productName);
	}

	@Override
	public VendorModel getVendorByProductCode(String productCode) {
		// TODO Auto-generated method stub
		return acerChemVendorDao.getVendorByProductCode(productCode);
	}

}
