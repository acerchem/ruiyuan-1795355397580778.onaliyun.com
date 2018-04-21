package com.acerchem.core.image.dao.impl;

import java.util.List;

import javax.annotation.Resource;

import com.acerchem.core.image.dao.AcerChemImageFailedRecordDao;
import com.acerchem.core.model.ImageFailedRecordModel;

import de.hybris.platform.core.model.media.MediaModel;
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

	@Override
	public List<ImageFailedRecordModel> getAllImageFailedRecord() {
		String GET_IMAGEFAILEDRECORD = "SELECT {pk} "
				+ " FROM {"+ImageFailedRecordModel._TYPECODE+"}";
		final FlexibleSearchQuery query = new FlexibleSearchQuery(GET_IMAGEFAILEDRECORD );
		
		final SearchResult<ImageFailedRecordModel> result = flexibleSearchService.search(query);
		
		return result.getResult();
	}

	

	/* (non-Javadoc)
	 * @see com.acerchem.core.image.dao.AcerChemImageFailedRecordDao#getMediaWithImageFailedRecord()
	 */
	@Override
	public List<MediaModel> getMediaWithImageFailedRecord() {
		String SQL = "select {m.pk} from {" + MediaModel._TYPECODE + " as m JOIN " + ImageFailedRecordModel._TYPECODE + 
				" as i ON { i." +  ImageFailedRecordModel.MEDIADATA + "} = {m." + MediaModel.PK + "} } ";
		
		final FlexibleSearchQuery query = new FlexibleSearchQuery(SQL );
		final SearchResult<MediaModel> result = flexibleSearchService.search(query);
		
		return result.getResult();
	}

	/* (non-Javadoc)
	 * @see com.acerchem.core.image.dao.AcerChemImageFailedRecordDao#getLimitedImageFailedRecord(int)
	 */
	@Override
	public List<ImageFailedRecordModel> getLimitedImageFailedRecord(int limit) {
		String GET_IMAGEFAILEDRECORD = "SELECT {pk} "
				+ " FROM {"+ImageFailedRecordModel._TYPECODE+"}";
		final FlexibleSearchQuery query = new FlexibleSearchQuery(GET_IMAGEFAILEDRECORD );
		query.setNeedTotal(false);
		query.setCount(limit);
		
		final SearchResult<ImageFailedRecordModel> result = flexibleSearchService.search(query);
		
		return result.getResult();
		
	}

	
}
