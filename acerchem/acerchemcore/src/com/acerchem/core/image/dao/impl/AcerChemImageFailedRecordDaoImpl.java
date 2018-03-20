package com.acerchem.core.image.dao.impl;

import java.util.Arrays;
import java.util.List;

import javax.annotation.Resource;

import com.acerchem.core.image.dao.AcerChemImageFailedRecordDao;
import com.acerchem.core.model.ImageFailedRecordModel;

import de.hybris.platform.ordersplitting.model.StockLevelModel;
import de.hybris.platform.servicelayer.search.FlexibleSearchQuery;
import de.hybris.platform.servicelayer.search.FlexibleSearchService;
import de.hybris.platform.servicelayer.search.SearchResult;

public class AcerChemImageFailedRecordDaoImpl implements AcerChemImageFailedRecordDao {

	@Resource(name = "flexibleSearchService")
	private FlexibleSearchService flexibleSearchService;
	
	@Override
	public List<ImageFailedRecordModel> getImageFailedRecordByFileAttr(String fileName, String actionType) {
		// TODO Auto-generated method stub
		
		String GET_IMAGEFAILEDRECORD = "SELECT {pk} "
				+ " FROM {"+ImageFailedRecordModel._TYPECODE+"}"
				+ " WHERE {"+ImageFailedRecordModel.FILENAME+"} =?fileName and {"+ImageFailedRecordModel.ACTIONTYPE+"} = ?action";
		FlexibleSearchQuery query = new FlexibleSearchQuery(GET_IMAGEFAILEDRECORD);
		query.addQueryParameter("fileName", fileName);
		query.addQueryParameter("action",actionType);
		
		
		SearchResult<ImageFailedRecordModel> result = flexibleSearchService.search(query);
		return result.getResult();
		
		
	}

}
