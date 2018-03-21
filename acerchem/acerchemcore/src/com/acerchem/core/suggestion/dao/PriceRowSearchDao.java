package com.acerchem.core.suggestion.dao;

import de.hybris.platform.europe1.model.PriceRowModel;
import de.hybris.platform.servicelayer.internal.dao.Dao;
import java.util.List;

public interface PriceRowSearchDao extends Dao
{
	/**
	 *Alice 根据priceRow查询同一个产品下的其他priceRow
	 */
	List<PriceRowModel> findPriceRowBySameProductToPriceRow(PriceRowModel priceRow);
}
