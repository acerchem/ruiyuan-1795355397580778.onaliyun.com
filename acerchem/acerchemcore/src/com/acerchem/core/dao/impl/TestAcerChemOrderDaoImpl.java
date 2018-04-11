package com.acerchem.core.dao.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.acerchem.core.dao.AcerChemOrderDao;

import de.hybris.platform.core.model.order.OrderModel;
import de.hybris.platform.core.model.product.ProductModel;
import de.hybris.platform.servicelayer.search.FlexibleSearchQuery;
import de.hybris.platform.servicelayer.search.FlexibleSearchService;
import de.hybris.platform.servicelayer.search.SearchResult;

@Component("acerChemOrderDao")
public class TestAcerChemOrderDaoImpl implements AcerChemOrderDao {

	@Resource
	private FlexibleSearchService flexibleSearchService;
	
	@Override
	public List<OrderModel> getOrderModelByCode(String code) {
		// TODO Auto-generated method stub
		
		String SQL = "select {PK} from {Order} where {code} = ?code";
		final FlexibleSearchQuery fsq = new FlexibleSearchQuery(SQL);
		fsq.addQueryParameter("code", code);
		
		final SearchResult<OrderModel> result = flexibleSearchService.search(fsq);
		return result.getResult();
	}

}
