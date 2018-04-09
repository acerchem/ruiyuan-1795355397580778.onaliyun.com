package com.acerchem.facades.facades.impl;

import com.acerchem.core.service.AcerchemCustomerService;
import com.acerchem.facades.facades.AcerchemCustomerFacade;
import com.acerchem.facades.product.data.StoreOfProductData;
import de.hybris.platform.commercefacades.customer.impl.DefaultCustomerFacade;
import de.hybris.platform.commercefacades.user.data.CountryData;
import de.hybris.platform.commercefacades.user.data.CustomerData;
import de.hybris.platform.core.model.c2l.CountryModel;
import de.hybris.platform.core.model.product.ProductModel;
import de.hybris.platform.core.model.user.CustomerModel;
import de.hybris.platform.ordersplitting.model.StockLevelModel;
import de.hybris.platform.ordersplitting.model.WarehouseModel;
import de.hybris.platform.product.ProductService;
import de.hybris.platform.servicelayer.dto.converter.Converter;
import de.hybris.platform.stock.StockService;
import de.hybris.platform.store.services.BaseStoreService;
import de.hybris.platform.storelocator.model.PointOfServiceModel;
import de.hybris.platform.storelocator.pos.PointOfServiceService;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.*;


/**
 * Created by Jacob.Ji on 2018/3/20.
 */
public class DefaultAcerchemCustomerFacade extends DefaultCustomerFacade implements AcerchemCustomerFacade
{

	@Resource
	private Converter<CustomerModel, CustomerData> acerchemCustomerConverter;
	@Resource
	private AcerchemCustomerService acerchemCustomerService;
	@Resource
	private BaseStoreService baseStoreService;
	@Resource
	private ProductService productService;
	@Resource
	private PointOfServiceService pointOfServiceService;
	@Resource
	private StockService stockService;
	@Resource
	private Converter<CountryModel, CountryData> countryConverter;

	@Override
	public CustomerData getCurrentCustomer()
	{
		final CustomerModel customerModel = (CustomerModel) getCurrentUser();
		final CustomerData customerData = acerchemCustomerConverter.convert(customerModel);
		return customerData;
	}

	@Override
	public List<StoreOfProductData> getAllPos(String productCode)
	{
		List<StoreOfProductData> dataList = new ArrayList<>();
		final ProductModel productModel = productService.getProductForCode(productCode);

		Collection<StockLevelModel> stockLevels = stockService.getAllStockLevels(productModel);

		for (StockLevelModel stockLevelModel : stockLevels){
			WarehouseModel warehouseModel = stockLevelModel.getWarehouse();
			for (PointOfServiceModel pos : warehouseModel.getPointsOfService()){
				StoreOfProductData storeOfProductData = new StoreOfProductData();

				storeOfProductData.setStoreId(pos.getName());
				storeOfProductData.setStoreName(pos.getDisplayName());
				//近期库存天数和远期库存天数
				storeOfProductData.setAvaReleaseDay(stockLevelModel.getAvaPreOrderReleaseDay());

				int num = stockLevelModel.getPreOrderReleaseDay()!=null?stockLevelModel.getPreOrderReleaseDay():0;
				Calendar ca = Calendar.getInstance();
				ca.add(Calendar.DATE, num);// num为增加的天数
				storeOfProductData.setFutureAvailableDate(ca.getTime());
				//库存
				storeOfProductData.setInventory(stockLevelModel.getAvailable());
				storeOfProductData.setFutureInventory(stockLevelModel.getPreOrder());
//				storeOfProductData.setIsUseFutureStock(Boolean.FALSE);
				//配送范围
				if (pos.getZone()!=null&&pos.getZone().getCountries()!=null){
					storeOfProductData.setCountryDataList(countryConverter.convertAll(pos.getZone().getCountries()));
				}
				dataList.add(storeOfProductData);
			}
		}
		return dataList;
	}

}
