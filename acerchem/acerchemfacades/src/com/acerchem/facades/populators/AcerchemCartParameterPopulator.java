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

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import javax.annotation.Resource;

import de.hybris.platform.basecommerce.enums.InStockStatus;
import de.hybris.platform.commercefacades.order.data.AddToCartParams;
import de.hybris.platform.commerceservices.service.data.CommerceCartParameter;
import de.hybris.platform.converters.Populator;
import de.hybris.platform.core.model.product.ProductModel;
import de.hybris.platform.ordersplitting.model.StockLevelModel;
import de.hybris.platform.servicelayer.dto.converter.ConversionException;
import de.hybris.platform.servicelayer.search.FlexibleSearchQuery;
import de.hybris.platform.servicelayer.search.FlexibleSearchService;
import de.hybris.platform.servicelayer.search.SearchResult;

public class AcerchemCartParameterPopulator implements Populator<AddToCartParams, CommerceCartParameter>
{
	@Resource
    private FlexibleSearchService flexibleSearchService;
	
	@SuppressWarnings("unchecked")
	@Override
	public void populate(final AddToCartParams source, final CommerceCartParameter target) throws ConversionException
	{
		target.setIsUseFutureStock(source.getIsUseFutureStock());
		target.setAvailableDate(source.getAvailableDate());
		
		if (target.getProduct() != null)
		{
			final String SQL = "SELECT {s.PK} FROM {StockLevel as s JOIN Warehouse as w ON {s.warehouse}={w.pk} } "
					+ "WHERE {s.productCode} = \'"+source.getProductCode()+"\' AND {w.code} = \'"+source.getStoreId()+"\'";
			final FlexibleSearchQuery query = new FlexibleSearchQuery(SQL);
			query.setNeedTotal(false);
			query.setCount(1);
	        final SearchResult<StockLevelModel> result = flexibleSearchService.search(query);
	        Set<StockLevelModel> StockLevelSet = new HashSet<StockLevelModel>();
	        StockLevelSet.addAll(result.getResult());
	        
	        final ProductModel product = target.getProduct();
			product.setStockLevels(StockLevelSet);
			target.setProduct(product);
		}
	}
	
}
