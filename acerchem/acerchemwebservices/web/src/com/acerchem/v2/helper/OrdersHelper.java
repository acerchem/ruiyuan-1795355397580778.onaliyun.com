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
package com.acerchem.v2.helper;

import de.hybris.platform.commercefacades.order.OrderFacade;
import de.hybris.platform.commercefacades.order.data.OrderHistoriesData;
import de.hybris.platform.commercefacades.order.data.OrderHistoryData;
import de.hybris.platform.commerceservices.customer.CustomerAccountService;
import de.hybris.platform.commerceservices.search.flexiblesearch.PagedFlexibleSearchService;
import de.hybris.platform.commerceservices.search.flexiblesearch.data.SortQueryData;
import de.hybris.platform.commerceservices.search.pagedata.PageableData;
import de.hybris.platform.commerceservices.search.pagedata.PaginationData;
import de.hybris.platform.commerceservices.search.pagedata.SearchPageData;
import de.hybris.platform.commerceservices.search.pagedata.SortData;
import de.hybris.platform.commercewebservicescommons.dto.order.OrderHistoryListWsDTO;
import de.hybris.platform.converters.Converters;
import de.hybris.platform.core.enums.OrderStatus;
import de.hybris.platform.core.model.order.OrderModel;
import de.hybris.platform.core.model.user.CustomerModel;
import de.hybris.platform.servicelayer.dto.converter.Converter;
import de.hybris.platform.servicelayer.search.FlexibleSearchQuery;
import de.hybris.platform.servicelayer.search.FlexibleSearchService;
import de.hybris.platform.servicelayer.search.SearchResult;
import de.hybris.platform.servicelayer.user.UserService;
import de.hybris.platform.store.BaseStoreModel;
import de.hybris.platform.store.services.BaseStoreService;

import com.acerchem.constants.YcommercewebservicesConstants;

import static de.hybris.platform.servicelayer.util.ServicesUtil.validateParameterNotNull;
import static de.hybris.platform.servicelayer.util.ServicesUtil.validateParameterNotNullStandardMessage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.annotation.Resource;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang.ArrayUtils;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;


@Component
public class OrdersHelper extends AbstractHelper
{
	@Resource(name = "orderFacade")
	private OrderFacade orderFacade;

	@Cacheable(value = "orderCache", key = "T(de.hybris.platform.commercewebservicescommons.cache.CommerceCacheKeyGenerator).generateKey(true,true,'DTO',#statuses,#currentPage,#pageSize,#sort,#fields)")
	public OrderHistoryListWsDTO searchOrderHistory(final String orderCode,final String statuses, final int currentPage, final int pageSize,
			final String sort, final String fields)
	{
		final OrderHistoriesData orderHistoriesData = searchOrderHistory(orderCode,statuses, currentPage, pageSize, sort);
		return getDataMapper().map(orderHistoriesData, OrderHistoryListWsDTO.class, fields);
	}

	@Cacheable(value = "orderCache", key = "T(de.hybris.platform.commercewebservicescommons.cache.CommerceCacheKeyGenerator).generateKey(true,true,'Data',#statuses,#currentPage,#pageSize,#sort)")
	public OrderHistoriesData searchOrderHistory(final String orderCode,final String statuses, final int currentPage, final int pageSize,
			final String sort)
	{
		final PageableData pageableData = createPageableData(currentPage, pageSize, sort);
		
		
		final SearchPageData<OrderHistoryData> searchPageData;
				
		if (statuses != null&&!statuses.equals(""))
		{
			final Set<OrderStatus> statusSet = extractOrderStatuses(statuses);
			searchPageData=getPagedOrderHistoryForStatuses(orderCode,pageableData,statusSet.toArray(new OrderStatus[statusSet.size()]));
		}
		else
		{
			searchPageData=getPagedOrderHistoryForStatuses(orderCode,pageableData);
		}
		return createOrderHistoriesData(searchPageData);
	}
	
	@Resource
	private UserService userService;
	@Resource
	private BaseStoreService baseStoreService;
	@Resource
	private CustomerAccountService customerAccountService;
	@Resource
	private Converter<OrderModel, OrderHistoryData> orderHistoryConverter;
	
	public SearchPageData<OrderHistoryData> getPagedOrderHistoryForStatuses(final String orderCode,final PageableData pageableData,
			final OrderStatus... statuses)
	{
		final CustomerModel currentCustomer = (CustomerModel) userService.getCurrentUser();
		final BaseStoreModel currentBaseStore = baseStoreService.getCurrentBaseStore();
		final SearchPageData<OrderModel> orderResults = getOrderList(orderCode,currentCustomer, currentBaseStore,
				statuses, pageableData);
		
		final SearchPageData<OrderHistoryData> result = new SearchPageData<OrderHistoryData>();
		result.setPagination(orderResults.getPagination());
		result.setSorts(orderResults.getSorts());
		result.setResults(Converters.convertAll(orderResults.getResults(), orderHistoryConverter));
		return result;
	}
	
	@Resource
	private PagedFlexibleSearchService pagedFlexibleSearchService;
	
	public SearchPageData<OrderModel> getOrderList(final String orderCode,final CustomerModel customerModel, final BaseStoreModel store,
			final OrderStatus[] status, final PageableData pageableData)
	{
		validateParameterNotNull(pageableData, "PageableData must not be null");
		validateParameterNotNull(customerModel, "Customer must not be null");
		validateParameterNotNull(store, "Store must not be null");

		final Map<String, Object> queryParams = new HashMap<String, Object>();
		queryParams.put("customer", customerModel);
		queryParams.put("store", store);
		
		String sql="SELECT {" + OrderModel.PK + "}, {" + OrderModel.CREATIONTIME + "}, {" + OrderModel.CODE + "} FROM {" + OrderModel._TYPECODE + "} WHERE {" + OrderModel.USER
				+ "} = ?customer AND {" + OrderModel.VERSIONID + "} IS NULL AND {" + OrderModel.STORE + "} = ?store";
		
		if (orderCode != null&&!orderCode.equals(""))
		{
			queryParams.put("orderCode", "%" + orderCode + "%");
			sql+=" AND {" + OrderModel.CODE + "} like ?orderCode";
		}
		
		if(ArrayUtils.isNotEmpty(status))
		{
			queryParams.put("statusList", Arrays.asList(status));
			sql+=" AND {" + OrderModel.STATUS + "} IN (?statusList)";
		}
		
		
		if(pageableData.getSort().equals("byAmountAsc"))
		{
			sql+=" ORDER BY {" + OrderModel.TOTALPRICE + "} ASC, {" + OrderModel.PK + "} DESC";
		}
		else if(pageableData.getSort().equals("byAmountDesc"))
		{
			sql+=" ORDER BY {" + OrderModel.TOTALPRICE + "} DESC, {" + OrderModel.PK + "} DESC";
		}
		else
		{
			sql+=" ORDER BY {" + OrderModel.CREATIONTIME + "} DESC, {" + OrderModel.PK + "}";
		}
		
		final StringBuilder queryBuilder = new StringBuilder();
		queryBuilder.append(sql);
		return search(queryBuilder.toString(),queryParams, pageableData);
	}
	
	@Resource
	private FlexibleSearchService flexibleSearchService;
	
	public <T> SearchPageData<T> search(final String query,final Map<String, ?> queryParams, final PageableData pageableData)
	{
		validateParameterNotNullStandardMessage("pageableData", pageableData);
		Assert.isTrue(pageableData.getCurrentPage() >= 0, "pageableData current page must be zero or greater");
		

		validateParameterNotNullStandardMessage("query", query);

		final FlexibleSearchQuery searchQuery = new FlexibleSearchQuery(query);
		if (MapUtils.isNotEmpty(queryParams))
		{
			searchQuery.addQueryParameters(queryParams);
		}
			
		validateParameterNotNullStandardMessage("searchQuery", searchQuery);
		searchQuery.setNeedTotal(true);
		searchQuery.setStart(pageableData.getCurrentPage() * pageableData.getPageSize());
		searchQuery.setCount(pageableData.getPageSize());

		final SearchResult<T> searchResult = flexibleSearchService.search(searchQuery);
	
		final PaginationData paginationData = new PaginationData();
		paginationData.setPageSize(pageableData.getPageSize());
		paginationData.setSort(pageableData.getSort());
		paginationData.setTotalNumberOfResults(searchResult.getTotalCount());
		paginationData.setNumberOfPages((int) Math.ceil(((double) paginationData.getTotalNumberOfResults()) / paginationData.getPageSize()));
		paginationData.setCurrentPage(Math.max(0, Math.min(paginationData.getNumberOfPages(), pageableData.getCurrentPage())));

		final SearchPageData<T> result = new SearchPageData<T>();
		result.setResults(searchResult.getResult());
		result.setPagination(paginationData);
		
		final SearchPageData<T> searchPageData = result;

		searchPageData.getPagination().setSort(pageableData.getSort());
		
		final List<SortData> result1 = new ArrayList<SortData>();
		final SortData sortData = new SortData();
		sortData.setCode(pageableData.getSort());
		sortData.setSelected(true);
		result1.add(sortData);
		searchPageData.setSorts(result1);
		
		return searchPageData;
	}
	
	protected SortQueryData createSortQueryData(final String sortCode, final String query)
	{
		final SortQueryData result = new SortQueryData();
		result.setSortCode(sortCode);
		result.setQuery(query);
		return result;
	}
	
	protected Set<OrderStatus> extractOrderStatuses(final String statuses)
	{
		final String[] statusesStrings = statuses.split(YcommercewebservicesConstants.OPTIONS_SEPARATOR);

		final Set<OrderStatus> statusesEnum = new HashSet<>();
		for (final String status : statusesStrings)
		{
			statusesEnum.add(OrderStatus.valueOf(status));
		}
		return statusesEnum;
	}

	protected OrderHistoriesData createOrderHistoriesData(final SearchPageData<OrderHistoryData> result)
	{
		final OrderHistoriesData orderHistoriesData = new OrderHistoriesData();

		orderHistoriesData.setOrders(result.getResults());
		orderHistoriesData.setSorts(result.getSorts());
		orderHistoriesData.setPagination(result.getPagination());

		return orderHistoriesData;
	}
}
