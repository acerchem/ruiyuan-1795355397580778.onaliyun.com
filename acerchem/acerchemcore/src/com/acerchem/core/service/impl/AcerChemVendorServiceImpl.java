package com.acerchem.core.service.impl;

import java.util.List;

import javax.annotation.Resource;

import com.acerchem.core.dao.AcerChemVendorDao;
import com.acerchem.core.service.AcerChemVendorService;

import de.hybris.platform.core.model.user.UserModel;
import de.hybris.platform.ordersplitting.model.VendorModel;

public class AcerChemVendorServiceImpl implements AcerChemVendorService {

	@Resource
	private AcerChemVendorDao acerChemVendorDao;
	@Override
	public List<VendorModel> getVendorsByProductName(final String productName) {
		// TODO Auto-generated method stub
		return acerChemVendorDao.getVendorsByProductName(productName);
	}

	@Override
	public VendorModel getVendorByProductCode(final String productCode) {
		// TODO Auto-generated method stub
		return acerChemVendorDao.getVendorByProductCode(productCode);
	}

	@Override
	public List<VendorModel> getAllVendors() {
		// TODO Auto-generated method stub
		return acerChemVendorDao.getAllVendors();
	}

	@Override
	public UserModel getEmployeeByVendorCode(final String vendorCode) {
		// TODO Auto-generated method stub
		return acerChemVendorDao.getEmployeeByVendorCode(vendorCode);
	}
	
	

}
