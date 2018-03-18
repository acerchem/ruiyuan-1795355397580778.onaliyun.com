package com.acerchem.core.image.dao.impl;

import java.util.Arrays;
import java.util.List;

import javax.annotation.Resource;

import org.fest.util.Collections;

import com.acerchem.core.image.dao.AcerChemMediaDao;
import com.acerchem.core.model.ImageFailedRecordModel;

import de.hybris.platform.core.model.media.MediaModel;
import de.hybris.platform.servicelayer.search.FlexibleSearchQuery;
import de.hybris.platform.servicelayer.search.FlexibleSearchService;
import de.hybris.platform.servicelayer.search.SearchResult;

public class AcerChemMediaDaoImpl implements AcerChemMediaDao {

	@Resource(name = "flexibleSearchService")
	private FlexibleSearchService flexibleSearchService;
	
	@Override
	public MediaModel getMediaByPk(String pk) {
		// TODO Auto-generated method stub
		
		final String GET_MEDIA_MODEL = "select pk from {" + MediaModel._TYPECODE + "} where {" + MediaModel.PK + "} =?pk";
		
		FlexibleSearchQuery query = new FlexibleSearchQuery(GET_MEDIA_MODEL);
		query.addQueryParameter("pk", pk);
		
		query.setResultClassList(Arrays.asList(MediaModel.class));
		SearchResult<MediaModel> result = flexibleSearchService.search(query);
		List<MediaModel> list = result.getResult();
		if(!Collections.isEmpty(list)){
			return list.get(0);
		}
		
		return null;
	}

}
