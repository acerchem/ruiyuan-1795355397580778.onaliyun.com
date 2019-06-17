package com.acerchem.customer.populator;

import de.hybris.platform.commerceservices.search.solrfacetsearch.data.SolrSearchRequest;
import de.hybris.platform.commerceservices.search.solrfacetsearch.data.SolrSearchResponse;
import de.hybris.platform.converters.Populator;
import de.hybris.platform.core.model.user.CustomerModel;
import de.hybris.platform.ordersplitting.model.WarehouseModel;
import de.hybris.platform.servicelayer.user.UserService;
import de.hybris.platform.solrfacetsearch.search.*;
import de.hybris.platform.solrfacetsearch.search.impl.SolrSearchResult;
import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;
import org.apache.solr.common.SolrException;
import org.springframework.beans.factory.annotation.Required;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author Abel.li
 * @description 覆写SolrSerachRequestResponsePopulator
 * @contact abel0130@163.com
 * @date 2019-06-11
 */
public class SolrSearchResponsePopulator<FACET_SEARCH_CONFIG_TYPE, INDEXED_TYPE_TYPE, INDEXED_PROPERTY_TYPE, INDEXED_TYPE_SORT_TYPE> implements
        Populator<SolrSearchRequest<FACET_SEARCH_CONFIG_TYPE, INDEXED_TYPE_TYPE, INDEXED_PROPERTY_TYPE, SearchQuery, INDEXED_TYPE_SORT_TYPE>, SolrSearchResponse<FACET_SEARCH_CONFIG_TYPE, INDEXED_TYPE_TYPE, INDEXED_PROPERTY_TYPE, SearchQuery, INDEXED_TYPE_SORT_TYPE, SearchResult>> {


    private static final Logger LOG = Logger.getLogger(SolrSearchResponsePopulator.class);

    private UserService userService;
    private FacetSearchService solrFacetSearchService;
    private SolrKeywordRedirectService solrKeywordRedirectService;

    public UserService getUserService() {
        return userService;
    }

    public void setUserService(UserService userService) {
        this.userService=userService;
    }

    protected FacetSearchService getSolrFacetSearchService()
    {
        return solrFacetSearchService;
    }

    @Required
    public void setSolrFacetSearchService(final FacetSearchService solrFacetSearchService)
    {
        this.solrFacetSearchService = solrFacetSearchService;
    }

    public SolrKeywordRedirectService getSolrKeywordRedirectService()
    {
        return solrKeywordRedirectService;
    }

    @Required
    public void setSolrKeywordRedirectService(final SolrKeywordRedirectService solrKeywordRedirectService)
    {
        this.solrKeywordRedirectService = solrKeywordRedirectService;
    }

    @Override
    public void populate(
            final SolrSearchRequest<FACET_SEARCH_CONFIG_TYPE, INDEXED_TYPE_TYPE, INDEXED_PROPERTY_TYPE, SearchQuery, INDEXED_TYPE_SORT_TYPE> source,
            final SolrSearchResponse<FACET_SEARCH_CONFIG_TYPE, INDEXED_TYPE_TYPE, INDEXED_PROPERTY_TYPE, SearchQuery, INDEXED_TYPE_SORT_TYPE, SearchResult> target)
    {
        try
        {
            target.setRequest(source);
            CustomerModel customerModel = (CustomerModel) userService.getCurrentUser();
            SearchQuery searchQuery=source.getSearchQuery();

            Set<WarehouseModel> warehouseModelSet = customerModel.getWarehouse();
            if (CollectionUtils.isNotEmpty(warehouseModelSet)) {
                Set<String> codeSet = warehouseModelSet.stream().map(WarehouseModel::getCode).collect(Collectors.toSet());
                searchQuery.addFilterQuery("warehouseCode_string_mv", SearchQuery.Operator.OR, codeSet);
                LOG.info("search paramallPromotions : " + searchQuery.getUserQuery() + ":" + codeSet.toString());
            } else {
                Set<String> codeSet = new HashSet<>();
                codeSet.add("nil");
                searchQuery.addFilterQuery("warehouseCode_string_mv", SearchQuery.Operator.AND, codeSet);
            }
            final SearchResult searchResult = getSolrFacetSearchService().search(searchQuery);
            if (searchResult instanceof SolrSearchResult)
            {
                getSolrKeywordRedirectService().attachKeywordRedirect((SolrSearchResult) searchResult);
            }
            target.setSearchResult(searchResult);
        }
        catch (final FacetSearchException | SolrException | NullPointerException ex)
        {
            LOG.error("Exception while executing SOLR search", ex);
        }
    }
}
