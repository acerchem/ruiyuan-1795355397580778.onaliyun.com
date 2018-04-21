package com.acerchem.core.image.dao.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;

import com.acerchem.core.image.dao.AcerChemMediaDao;

import de.hybris.platform.catalog.model.CatalogModel;
import de.hybris.platform.catalog.model.CatalogVersionModel;
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
	public List<MediaModel> getMediasOfLimit(final List<String> mimes, final int limitCount,final String version,final String catalogId) {
		// TODO Auto-generated method stub
		
//		final String SQL="select {pk} from {" + MediaModel._TYPECODE + "} where {" + MediaModel.ALIYUNURL + "} IS NULL and {"
//				 + MediaModel.MIME + "} in (?mimes)";
//		'%Online%'
//		'%acerchem%'
		
		final String fSQL="select {a.pk} from {" + MediaModel._TYPECODE + " AS a" 
		                              +" JOIN CatalogVersion AS c ON {a."+ MediaModel.CATALOGVERSION 
		                              + "} = {c."+CatalogVersionModel.PK + "}"
		                              +" JOIN Catalog AS l ON {c." + CatalogVersionModel.CATALOG 
		                              + "} = {l." + CatalogModel.PK + "}" 
		                              + " } where { a." + MediaModel.ALIYUNURL + "} IS NULL and { a."
				 + MediaModel.MIME + "} in (?mimes) and { c." + CatalogVersionModel.VERSION + "} like ?version" 
				 + " and {l." + CatalogModel.ID + "} like ?catalogId";
		

		final FlexibleSearchQuery query = new FlexibleSearchQuery(fSQL);
		query.addQueryParameter("mimes", mimes);
		query.addQueryParameter("version", "%" + version + "%");
		query.addQueryParameter("catalogId",  "%" + catalogId + "%");
		query.setNeedTotal(false);
		query.setCount(limitCount);
		
		final SearchResult<MediaModel> result = flexibleSearchService.search(query);
		
		//MediaModel mm = new MediaModel();
		
		return result.getResult();
	}
	
	


}
