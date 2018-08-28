package com.acerchem.core.dao.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;

import com.acerchem.core.dao.AcerchemDocMessageDao;
import com.acerchem.core.model.AcerchemDocMessageModel;

import de.hybris.platform.servicelayer.search.FlexibleSearchQuery;
import de.hybris.platform.servicelayer.search.FlexibleSearchService;
import de.hybris.platform.servicelayer.search.SearchResult;

public class AcerchemDocMessageDaoImpl implements AcerchemDocMessageDao {

	@Resource
	private FlexibleSearchService flexibleSearchService;
	
	@Override
	public List<AcerchemDocMessageModel> getDocMessageList(final String acerchemCode) {
		final String articelCode= StringUtils.defaultString(acerchemCode);
		final String SQL = "select {pk} from {AcerchemDocMessage} where {articleCode}=?code";
		final FlexibleSearchQuery query = new FlexibleSearchQuery(SQL);
		query.addQueryParameter("code", articelCode);
		
		final SearchResult<AcerchemDocMessageModel> result = flexibleSearchService.search(query);

		
		return result.getResult();
	}

	@Override
	public List<AcerchemDocMessageModel> getDocMessageAllList() {
		final String SQL = "select {pk} from {AcerchemDocMessage} order by {creationtime} DESC";
		final FlexibleSearchQuery query = new FlexibleSearchQuery(SQL);
		
		final SearchResult<AcerchemDocMessageModel> result = flexibleSearchService.search(query);

		
		return result.getResult();
		
	}

}
