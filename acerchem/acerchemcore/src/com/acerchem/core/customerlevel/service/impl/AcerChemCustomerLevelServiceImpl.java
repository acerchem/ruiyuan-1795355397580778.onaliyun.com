package com.acerchem.core.customerlevel.service.impl;

import java.util.List;

import javax.annotation.Resource;

import com.acerchem.core.customerlevel.dao.AcerChemCustomerLevelDao;
import com.acerchem.core.customerlevel.service.AcerChemCustomerLevelService;
import com.acerchem.core.model.CustomerLevelModel;

public class AcerChemCustomerLevelServiceImpl implements AcerChemCustomerLevelService {

	@Resource
	private AcerChemCustomerLevelDao acerChemCustomerLevelDao;
	@Override
	public List<CustomerLevelModel> getALLCustomerLevel() {
		// TODO Auto-generated method stub
		return acerChemCustomerLevelDao.getALLCustomerLevel();
	}

}
