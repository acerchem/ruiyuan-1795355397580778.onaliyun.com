package com.acerchem.core.dao;

import java.util.List;

import de.hybris.platform.core.model.user.UserModel;
import de.hybris.platform.ordersplitting.model.VendorModel;
import de.hybris.platform.ordersplitting.model.WarehouseModel;

public interface AcerchemProductWarehouseDao {
	

	List<WarehouseModel> getProductWarehouseByProductCode(String productCode);
	
}
