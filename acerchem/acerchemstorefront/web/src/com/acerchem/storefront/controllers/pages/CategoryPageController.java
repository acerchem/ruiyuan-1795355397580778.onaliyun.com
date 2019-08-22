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
package com.acerchem.storefront.controllers.pages;


import de.hybris.platform.acceleratorstorefrontcommons.controllers.pages.AbstractCategoryPageController;
import de.hybris.platform.commercefacades.product.data.CategoryData;
import de.hybris.platform.commercefacades.product.data.ProductData;
import de.hybris.platform.commercefacades.search.data.SearchStateData;
import de.hybris.platform.commerceservices.search.facetdata.FacetData;
import de.hybris.platform.commerceservices.search.facetdata.FacetRefinement;
import de.hybris.platform.commerceservices.search.facetdata.ProductCategorySearchPageData;
import de.hybris.platform.core.model.product.ProductModel;
import de.hybris.platform.servicelayer.dto.converter.Converter;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.util.Collections;
import java.util.List;


/**
 * Controller for a category page
 */
@Controller
@RequestMapping(value = "/**/c")
public class CategoryPageController extends AbstractCategoryPageController {
	
	@Resource
	private Converter<ProductModel, ProductData> productConverter;
	
    @RequestMapping(value = CATEGORY_CODE_PATH_VARIABLE_PATTERN, method = RequestMethod.GET)
    public String category(@PathVariable("categoryCode") final String categoryCode, // NOSONAR
                           @RequestParam(value = "q", required = false) final String searchQuery,
                           @RequestParam(value = "page", defaultValue = "0") final int page,
                           @RequestParam(value = "show", defaultValue = "Page") final ShowMode showMode,
                           @RequestParam(value = "sort", required = false) final String sortCode, final Model model,
                           final HttpServletRequest request, final HttpServletResponse response) throws UnsupportedEncodingException {

        String result = performSearchAndGetResultsPage(categoryCode, searchQuery, page, showMode, sortCode, model, request, response);
        ProductCategorySearchPageData<SearchStateData, ProductData, CategoryData> pageData = (ProductCategorySearchPageData<SearchStateData, ProductData, CategoryData>) model.asMap().get("searchPageData");
        List<FacetData<SearchStateData>> facetDataList = pageData.getFacets();
        if (CollectionUtils.isNotEmpty(facetDataList)) {
            for (FacetData<SearchStateData> facetData : facetDataList) {
                if (CollectionUtils.isNotEmpty(facetData.getTopValues())) {
                    Collections.sort(facetData.getTopValues(), (v0, v1)-> v0.getCode().compareToIgnoreCase(v1.getCode()));
                }
                if (CollectionUtils.isNotEmpty(facetData.getValues())) {
                    Collections.sort(facetData.getValues(), (v0, v1)-> v0.getCode().compareToIgnoreCase(v1.getCode()));
                }
            }
        }

        model.addAttribute("searchPageData", pageData);
        return result;

//    	final CategoryModel category = getCommerceCategoryService().getCategoryForCode(categoryCode);
//    	List<ProductData> products=new ArrayList<>();
//    	products.addAll(Converters.convertAll(category.getProducts(), productConverter));
//    	if(category.getAllSubcategories()!=null)
//    	{
//    		for(CategoryModel Subcategory:category.getAllSubcategories())
//        	{
//        		products.addAll(Converters.convertAll(Subcategory.getProducts(), productConverter));
//        	}
//    	}
//    	
//		final String redirection = checkRequestUrl(request, response, getCategoryModelUrlResolver().resolve(category));
//		if (StringUtils.isNotEmpty(redirection))
//		{
//			return redirection;
//		}
//
//		final CategoryPageModel categoryPage = getCategoryPage(category);
//
//		final CategorySearchEvaluator categorySearch = new CategorySearchEvaluator(categoryCode, searchQuery, page, showMode,
//				sortCode, categoryPage);
//
//		ProductCategorySearchPageData<SearchStateData, ProductData, CategoryData> searchPageData = null;
//		try
//		{
//			categorySearch.doSearch();
//			searchPageData = categorySearch.getSearchPageData();
//		}
//		catch (final ConversionException e) // NOSONAR
//		{
//			searchPageData = createEmptySearchResult(categoryCode);
//		}
//		catch (final Exception e) // NOSONAR
//		{
//			searchPageData = new ProductCategorySearchPageData<>();
//    		searchPageData.setResults(products);
//			searchPageData.setPagination(createEmptyPagination());
//			searchPageData.setCategoryCode(categoryCode);
//		}
//
//		final boolean showCategoriesOnly = categorySearch.isShowCategoriesOnly();
//
//		storeCmsPageInModel(model, categorySearch.getCategoryPage());
//		storeContinueUrl(request);
//
//		populateModel(model, searchPageData, showMode);
//		model.addAttribute(WebConstants.BREADCRUMBS_KEY, getSearchBreadcrumbBuilder().getBreadcrumbs(categoryCode, searchPageData));
//		model.addAttribute("showCategoriesOnly", Boolean.valueOf(showCategoriesOnly));
//		model.addAttribute("categoryName", category.getName());
//		model.addAttribute("pageType", PageType.CATEGORY.name());
//		model.addAttribute("userLocation", getCustomerLocationService().getUserLocation());
//
//		updatePageTitle(category, model);
//
//		final RequestContextData requestContextData = getRequestContextData(request);
//		requestContextData.setCategory(category);
//		requestContextData.setSearch(searchPageData);
//
//		if (searchQuery != null)
//		{
//			model.addAttribute(ThirdPartyConstants.SeoRobots.META_ROBOTS, ThirdPartyConstants.SeoRobots.NOINDEX_FOLLOW);
//		}
//
//		final String metaKeywords = MetaSanitizerUtil.sanitizeKeywords(category.getKeywords());
//		final String metaDescription = MetaSanitizerUtil.sanitizeDescription(category.getDescription());
//		setUpMetaData(model, metaKeywords, metaDescription);
//
//		return getViewPage(categorySearch.getCategoryPage());
    }

    @ResponseBody
    @RequestMapping(value = CATEGORY_CODE_PATH_VARIABLE_PATTERN + "/facets", method = RequestMethod.GET)
    public FacetRefinement<SearchStateData> getFacets(@PathVariable("categoryCode") final String categoryCode,
                                                      @RequestParam(value = "q", required = false) final String searchQuery,
                                                      @RequestParam(value = "page", defaultValue = "0") final int page,
                                                      @RequestParam(value = "show", defaultValue = "Page") final ShowMode showMode,
                                                      @RequestParam(value = "sort", required = false) final String sortCode) throws UnsupportedEncodingException {
        return performSearchAndGetFacets(categoryCode, searchQuery, page, showMode, sortCode);
    }

    @ResponseBody
    @RequestMapping(value = CATEGORY_CODE_PATH_VARIABLE_PATTERN + "/results", method = RequestMethod.GET)
    public SearchResultsData<ProductData> getResults(@PathVariable("categoryCode") final String categoryCode,
                                                     @RequestParam(value = "q", required = false) final String searchQuery,
                                                     @RequestParam(value = "page", defaultValue = "0") final int page,
                                                     @RequestParam(value = "show", defaultValue = "Page") final ShowMode showMode,
                                                     @RequestParam(value = "sort", required = false) final String sortCode) throws UnsupportedEncodingException {
        return performSearchAndGetResultsData(categoryCode, searchQuery, page, showMode, sortCode);
    }
}
