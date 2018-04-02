package com.acerchem.facades.facades.impl;

import de.hybris.platform.commercefacades.customer.impl.DefaultCustomerFacade;
import de.hybris.platform.commercefacades.user.data.CustomerData;
import de.hybris.platform.commerceservices.search.pagedata.PageableData;
import de.hybris.platform.commerceservices.search.pagedata.SearchPageData;
import de.hybris.platform.core.model.product.ProductModel;
import de.hybris.platform.core.model.user.CustomerModel;
import de.hybris.platform.ordersplitting.model.WarehouseModel;
import de.hybris.platform.product.ProductService;
import de.hybris.platform.servicelayer.dto.converter.Converter;
import de.hybris.platform.store.BaseStoreModel;
import de.hybris.platform.store.services.BaseStoreService;
import de.hybris.platform.storelocator.model.PointOfServiceModel;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.util.ObjectUtils;

import com.acerchem.core.service.AcerchemCustomerService;
import com.acerchem.facades.facades.AcerchemCustomerFacade;
import com.acerchem.facades.product.data.CountryToWarehouseData;


/**
 * Created by Jacob.Ji on 2018/3/20.
 */
public class DefaultAcerchemCustomerFacade extends DefaultCustomerFacade implements AcerchemCustomerFacade
{

	@Resource
	private Converter<CustomerModel, CustomerData> acerchemCustomerConverter;
	@Resource
	private Converter<WarehouseModel, CountryToWarehouseData> countryToWarehouseConverter;
	@Resource
	private AcerchemCustomerService acerchemCustomerService;
	@Resource
	private BaseStoreService baseStoreService;
	@Resource
	private ProductService productService;

	@Override
	public CustomerData getCurrentCustomer()
	{
		final CustomerModel customerModel = (CustomerModel) getCurrentUser();
		final CustomerData customerData = acerchemCustomerConverter.convert(customerModel);
		return customerData;
	}

	@Override
	public SearchPageData<CountryToWarehouseData> getAllPointOfServices(final PageableData pageableData)
	{

		final BaseStoreModel currentBaseStore = baseStoreService.getCurrentBaseStore();
		final SearchPageData<PointOfServiceModel> result = acerchemCustomerService.getAllPos(currentBaseStore, pageableData);

		final List<CountryToWarehouseData> dataList = null;
		if (!ObjectUtils.isEmpty(result) && result.getResults() != null)
		{
			//需求变动
			//            dataList = countryToWarehouseConverter.convertAll(result.getResults());
		}

		final SearchPageData<CountryToWarehouseData> searchPageData = new SearchPageData<>();

		searchPageData.setResults(dataList);

		return searchPageData;
	}

	@Override
	public List<CountryToWarehouseData> getAllWarehouses(final String productCode)
	{
		final BaseStoreModel baseStoreModel = baseStoreService.getCurrentBaseStore();

		List<CountryToWarehouseData> dataList = null;
		final ProductModel productModel = productService.getProductForCode(productCode);
		if (CollectionUtils.isNotEmpty(productModel.getStockLevels()))
		{
			final List<WarehouseModel> warehouseModelList = productModel
					.getStockLevels()
					.stream()
					.filter(stockLevelModel -> stockLevelModel.getWarehouse() != null)
					.flatMap(stockLevelModel -> Stream.of(stockLevelModel.getWarehouse()))
					.filter(
							warehouseModel -> warehouseModel.getBaseStores() != null
									&& warehouseModel.getBaseStores().contains(baseStoreModel)).collect(Collectors.toList());
			if (warehouseModelList != null)
			{
				dataList = countryToWarehouseConverter.convertAll(warehouseModelList);
			}
		}

		return dataList;
	}

}
