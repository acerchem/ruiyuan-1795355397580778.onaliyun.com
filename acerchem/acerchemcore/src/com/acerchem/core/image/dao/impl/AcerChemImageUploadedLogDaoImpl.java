package com.acerchem.core.image.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;

import com.acerchem.core.image.dao.AcerChemImageUploadedLogDAO;
import com.acerchem.core.model.ImageUploadedLogModel;


import de.hybris.platform.servicelayer.model.ModelService;
import de.hybris.platform.servicelayer.search.FlexibleSearchQuery;
import de.hybris.platform.servicelayer.search.FlexibleSearchService;
import de.hybris.platform.servicelayer.search.SearchResult;

public class AcerChemImageUploadedLogDaoImpl implements AcerChemImageUploadedLogDAO {
	

	@Resource(name = "flexibleSearchService")
	private FlexibleSearchService flexibleSearchService;
	@Resource
	private ModelService modelService;

	@Override
	public void saveImageUploadedLog(ImageUploadedLogModel model) {
		// TODO Auto-generated method stub
		modelService.save(model);

	}

	@Override
	public ImageUploadedLogModel getImageUploadedLog(String pk) {
		// TODO Auto-generated method stub

		final String GET_IMAGEUPLOADLOG_MODEL = "select {pk} from {" + ImageUploadedLogModel._TYPECODE + "} where {"
				+ ImageUploadedLogModel.IMAGEPK + "} =?imagepk";
		

		final Map<String, Object> params = new HashMap<String, Object>();
		final StringBuilder builder = new StringBuilder(GET_IMAGEUPLOADLOG_MODEL);
		params.put("imagepk", pk);
		
		final FlexibleSearchQuery query = new FlexibleSearchQuery(builder.toString());
		query.addQueryParameters(params);

		// query.setResultClassList(Arrays.asList(ImageUploadedLogModel.class));
		SearchResult<ImageUploadedLogModel> result = flexibleSearchService.search(query);

		List<ImageUploadedLogModel> list = result.getResult();

		if (CollectionUtils.isNotEmpty(list)) {
			return list.get(0);
		}
		return null;
	}
	
	
	@Override
	public boolean isExistByLocation(final String location) {
		final String SQL = "select {pk} from {" + ImageUploadedLogModel._TYPECODE + "} where {"
				+ ImageUploadedLogModel.LOCATION + "} =?location";
		
		
		final Map<String, Object> params = new HashMap<String, Object>();
		final StringBuilder builder = new StringBuilder(SQL);
		params.put("location", location);
		
		final FlexibleSearchQuery query = new FlexibleSearchQuery(builder.toString());
		query.addQueryParameters(params);
		
		SearchResult<ImageUploadedLogModel> result = flexibleSearchService.search(query);
		
		if (result.getCount() > 0) {
			return true;
		}
		
		return false;
	}

}
