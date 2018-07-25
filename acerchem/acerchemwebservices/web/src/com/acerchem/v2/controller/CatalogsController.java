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
package com.acerchem.v2.controller;

import de.hybris.platform.category.CategoryService;
import de.hybris.platform.commercefacades.catalog.CatalogFacade;
import de.hybris.platform.commercefacades.catalog.CatalogOption;
import de.hybris.platform.commercefacades.catalog.PageOption;
import de.hybris.platform.commercefacades.catalog.data.CatalogData;
import de.hybris.platform.commercefacades.catalog.data.CatalogVersionData;
import de.hybris.platform.commercefacades.catalog.data.CatalogsData;
import de.hybris.platform.commercefacades.catalog.data.CategoryHierarchyData;
import de.hybris.platform.commercefacades.product.data.CategoryData;
import de.hybris.platform.commercefacades.product.data.ProductData;
import de.hybris.platform.commercefacades.search.ProductSearchFacade;
import de.hybris.platform.commercefacades.search.data.SearchQueryData;
import de.hybris.platform.commercefacades.search.data.SearchStateData;
import de.hybris.platform.commerceservices.search.facetdata.ProductCategorySearchPageData;
import de.hybris.platform.commerceservices.search.pagedata.PaginationData;
import de.hybris.platform.commercewebservicescommons.dto.catalog.CatalogListWsDTO;
import de.hybris.platform.commercewebservicescommons.dto.catalog.CatalogVersionWsDTO;
import de.hybris.platform.commercewebservicescommons.dto.catalog.CatalogWsDTO;
import de.hybris.platform.commercewebservicescommons.dto.catalog.CategoryHierarchyWsDTO;
import de.hybris.platform.servicelayer.dto.converter.ConversionException;
import de.hybris.platform.webservicescommons.mapping.DataMapper;
import de.hybris.platform.webservicescommons.mapping.FieldSetBuilder;
import de.hybris.platform.webservicescommons.mapping.impl.FieldSetBuilderContext;
import de.hybris.platform.commerceservices.search.pagedata.PageableData;
import java.io.UnsupportedEncodingException;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


/**
 *
 * @pathparam catalogId Catalog identifier
 * @pathparam catalogVersionId Catalog version identifier
 * @pathparam categoryId Category identifier
 */
@Controller
@RequestMapping(value = "/{baseSiteId}/catalogs")
public class CatalogsController extends BaseController
{
	private static final Set<CatalogOption> OPTIONS;
	
	static
	{
		OPTIONS = getOptions();
	}

	@Resource(name = "cwsCatalogFacade")
	private CatalogFacade catalogFacade;
	@Resource(name = "fieldSetBuilder")
	private FieldSetBuilder fieldSetBuilder;

	/**
	 * Returns all catalogs with versions defined for the base store.
	 *
	 * @queryparam fields Response configuration (list of fields, which should be returned in response)
	 * @return All catalogs defined for the base store.
	 */
	@RequestMapping(method = RequestMethod.GET)
	@ResponseBody
	public CatalogListWsDTO getCatalogs(@RequestParam(defaultValue = DEFAULT_FIELD_SET) final String fields)
	{
		final List<CatalogData> catalogDataList = catalogFacade.getAllProductCatalogsForCurrentSite(OPTIONS);
		final CatalogsData catalogsData = new CatalogsData();
		catalogsData.setCatalogs(catalogDataList);

		final FieldSetBuilderContext context = new FieldSetBuilderContext();
		context.setRecurrencyLevel(countRecurrecyLevel(catalogDataList));
		final Set<String> fieldSet = fieldSetBuilder.createFieldSet(CatalogListWsDTO.class, DataMapper.FIELD_PREFIX, fields,
				context);

		return getDataMapper().map(catalogsData, CatalogListWsDTO.class, fieldSet);
	}


	/**
	 * Returns a information about a catalog based on its ID, along with versions defined for the current base store.
	 *
	 * @queryparam fields Response configuration (list of fields, which should be returned in response)
	 * @return Catalog structure
	 */
	@RequestMapping(value = "/{catalogId}", method = RequestMethod.GET)
	@ResponseBody
	public CatalogWsDTO getCatalog(@PathVariable final String catalogId,
			@RequestParam(defaultValue = DEFAULT_FIELD_SET) final String fields)
	{
		final CatalogData catalogData = catalogFacade.getProductCatalogForCurrentSite(catalogId, OPTIONS);

		final FieldSetBuilderContext context = new FieldSetBuilderContext();
		context.setRecurrencyLevel(countRecurrencyForCatalogData(catalogData));
		final Set<String> fieldSet = fieldSetBuilder.createFieldSet(CatalogWsDTO.class, DataMapper.FIELD_PREFIX, fields, context);

		return getDataMapper().map(catalogData, CatalogWsDTO.class, fieldSet);
	}

	/**
	 * Returns information about catalog version that exists for the current base store.
	 *
	 * @queryparam fields Response configuration (list of fields, which should be returned in response)
	 * @return Information about catalog version
	 */
	@RequestMapping(value = "/{catalogId}/{catalogVersionId}", method = RequestMethod.GET)
	@ResponseBody
	public CatalogVersionWsDTO getCatalogVersion(@PathVariable final String catalogId, @PathVariable final String catalogVersionId,
			@RequestParam(defaultValue = DEFAULT_FIELD_SET) final String fields)
	{
		final CatalogVersionData catalogVersionData = catalogFacade.getProductCatalogVersionForTheCurrentSite(catalogId,
				catalogVersionId, OPTIONS);

		final FieldSetBuilderContext context = new FieldSetBuilderContext();
		context.setRecurrencyLevel(countRecurrencyForCatalogVersionData(catalogVersionData));
		final Set<String> fieldSet = fieldSetBuilder.createFieldSet(CatalogVersionWsDTO.class, DataMapper.FIELD_PREFIX, fields,
				context);

		return getDataMapper().map(catalogVersionData, CatalogVersionWsDTO.class, fieldSet);
	}

	/**
	 * Returns information about category that exists in a catalog version available for the current base store.
	 *
	 * @queryparam currentPage The current result page requested.
	 * @queryparam pageSize The number of results returned per page.
	 * @queryparam fields Response configuration (list of fields, which should be returned in response)
	 * @return Information about category
	 */
	@RequestMapping(value = "/{catalogId}/{catalogVersionId}/categories/{categoryId}", method = RequestMethod.GET)
	@ResponseBody
	public CategoryHierarchyWsDTO getCategories(@PathVariable final String catalogId, @PathVariable final String catalogVersionId,
			@PathVariable final String categoryId, @RequestParam(defaultValue = "DEFAULT") final String fields)
	{
		final PageOption page = PageOption.createForPageNumberAndPageSize(0, 10);
		final CategoryHierarchyData categoryHierarchyData = catalogFacade.getCategoryById(catalogId, catalogVersionId, categoryId,
				page, OPTIONS);

		final FieldSetBuilderContext context = new FieldSetBuilderContext();
		context.setRecurrencyLevel(countRecurrencyForCategoryHierarchyData(1, categoryHierarchyData));
		final Set<String> fieldSet = fieldSetBuilder.createFieldSet(CategoryHierarchyWsDTO.class, DataMapper.FIELD_PREFIX, fields,
				context);

		return getDataMapper().map(categoryHierarchyData, CategoryHierarchyWsDTO.class, fieldSet);
	}
	
	@Resource
	private CategoryService categoryService;
	@Resource(name = "productSearchFacade")
	private ProductSearchFacade<ProductData> productSearchFacade;
	
	@RequestMapping(value = "/category/{categoryCode}", method = RequestMethod.GET)
	@ResponseBody
    public ProductCategorySearchPageData<SearchStateData, ProductData, CategoryData> category(@PathVariable("categoryCode") final String categoryCode, // NOSONAR
                           @RequestParam(value = "q", required = false) final String searchQuery,
                           @RequestParam(value = "page", defaultValue = "0") final int page,
                           @RequestParam(value = "sort", required = false) final String sortCode,
                           final HttpServletRequest request, final HttpServletResponse response) throws UnsupportedEncodingException {
        
		ProductCategorySearchPageData<SearchStateData, ProductData, CategoryData> searchPageData = null;
		try
		{
			if (searchQuery == null)
			{
				searchPageData = productSearchFacade.categorySearch(categoryCode);
			}
			else
			{
				SearchQueryData searchQueryData = new SearchQueryData();
				searchQueryData.setValue(searchQuery);
				
				final SearchStateData searchState = new SearchStateData();
				searchState.setQuery(searchQueryData);
				final PageableData pageableData = new PageableData();
				pageableData.setCurrentPage(page);
				pageableData.setSort(sortCode);
				pageableData.setPageSize(50);
				
				searchPageData = productSearchFacade.categorySearch(categoryCode, searchState, pageableData);
			}
		}
		catch (final ConversionException e) // NOSONAR
		{
			final PaginationData paginationData = new PaginationData();
			paginationData.setCurrentPage(0);
			paginationData.setNumberOfPages(0);
			paginationData.setPageSize(1);
			paginationData.setTotalNumberOfResults(0);
			
			searchPageData.setResults(Collections.<ProductData> emptyList());
			searchPageData.setPagination(paginationData);
			searchPageData.setCategoryCode(categoryCode);
		}
		return searchPageData;

	}

	protected static Set<CatalogOption> getOptions()
	{
		final Set<CatalogOption> opts = new HashSet<>();
		opts.add(CatalogOption.BASIC);
		opts.add(CatalogOption.CATEGORIES);
		opts.add(CatalogOption.SUBCATEGORIES);
		return opts;
	}

	protected int countRecurrecyLevel(final List<CatalogData> catalogDataList)
	{
		int recurrencyLevel = 1;
		int value;
		for (final CatalogData catalog : catalogDataList)
		{
			value = countRecurrencyForCatalogData(catalog);
			if (value > recurrencyLevel)
			{
				recurrencyLevel = value;
			}
		}
		return recurrencyLevel;
	}

	protected int countRecurrencyForCatalogData(final CatalogData catalog)
	{
		int retValue = 1;
		int value;
		for (final CatalogVersionData version : catalog.getCatalogVersions())
		{
			value = countRecurrencyForCatalogVersionData(version);
			if (value > retValue)
			{
				retValue = value;
			}
		}
		return retValue;
	}

	protected int countRecurrencyForCatalogVersionData(final CatalogVersionData catalogVersion)
	{
		int retValue = 1;
		int value;
		for (final CategoryHierarchyData hierarchy : catalogVersion.getCategoriesHierarchyData())
		{
			value = countRecurrencyForCategoryHierarchyData(1, hierarchy);
			if (value > retValue)
			{
				retValue = value;
			}
		}
		return retValue;
	}

	protected int countRecurrencyForCategoryHierarchyData(final int currentValue, final CategoryHierarchyData hierarchy)
	{
		int calculatedValue = currentValue + 1;
		int subcategoryRecurrencyValue;
		for (final CategoryHierarchyData subcategory : hierarchy.getSubcategories())
		{
			subcategoryRecurrencyValue = countRecurrencyForCategoryHierarchyData(calculatedValue, subcategory);
			if (subcategoryRecurrencyValue > calculatedValue)
			{
				calculatedValue = subcategoryRecurrencyValue;
			}
		}
		return calculatedValue;
	}
}
