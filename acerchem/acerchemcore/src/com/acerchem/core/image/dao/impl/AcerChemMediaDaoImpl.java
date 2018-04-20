package com.acerchem.core.image.dao.impl;

import java.util.Arrays;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
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
		
		final String GET_MEDIA_MODEL = "select {pk} from {" + MediaModel._TYPECODE + "} where {" + MediaModel.PK + "} =?pk";
		
		FlexibleSearchQuery query = new FlexibleSearchQuery(GET_MEDIA_MODEL);
		query.addQueryParameter("pk", pk);
		
		//query.setResultClassList(Arrays.asList(MediaModel.class));
		SearchResult<MediaModel> result = flexibleSearchService.search(query);
		List<MediaModel> list = result.getResult();
		if(CollectionUtils.isNotEmpty(list)){
			return list.get(0);
		}
		
		return null;
	}

	/* (non-Javadoc)
	 * @see com.acerchem.core.image.dao.AcerChemMediaDao#getMediaOfLimit(java.lang.String, int)
	 */
	@Override
	public List<MediaModel> getMediasOfLimit(String mime, int limitCount) {
		// TODO Auto-generated method stub
		
		final String SQL="select {pk} from {" + MediaModel._TYPECODE + "} where {" + MediaModel.MIME + "} like?mime ";
		//MediaModel mm = new MediaModel();
		
		return null;
	}
	
	

}
