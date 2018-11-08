package com.acerchem.core.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
	public List<AcerchemDocMessageModel> getDocMessageAllList(String searching) {
		//传入条件
		Map<String, Object> map=new HashMap<>();
		//其实就是字符串拼
		if (searching==null) {
			searching="";
		}
		map.put("searching","%"+searching+"%");
		final String SQL = "select {pk} from {AcerchemDocMessage} where {AcerchemDocMessage.Title} Like  ?searching order by {creationtime} DESC";	
		final FlexibleSearchQuery query = new FlexibleSearchQuery(SQL,map);		
		final SearchResult<AcerchemDocMessageModel> result = flexibleSearchService.search(query);		
		return result.getResult();
		
	}


}
