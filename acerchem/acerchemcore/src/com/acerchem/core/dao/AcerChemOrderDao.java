package com.acerchem.core.dao;

import java.util.List;

import de.hybris.platform.core.model.order.OrderModel;

public interface AcerChemOrderDao {
	
	public List<OrderModel> getOrderModelByCode(final String code);

}
