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
import de.hybris.platform.core.model.user.UserModel;
import de.hybris.platform.ordersplitting.model.StockLevelModel;
import de.hybris.platform.ordersplitting.model.WarehouseModel;
import de.hybris.platform.product.ProductService;
import de.hybris.platform.servicelayer.dto.converter.Converter;
import de.hybris.platform.stock.StockService;
import de.hybris.platform.store.services.BaseStoreService;
import de.hybris.platform.storelocator.model.PointOfServiceModel;
import de.hybris.platform.storelocator.pos.PointOfServiceService;
import javax.annotation.Resource;
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
		
		Integer maxInventory=0;
		for (StockLevelModel stockLevelModel : stockLevels){
			WarehouseModel warehouseModel = stockLevelModel.getWarehouse();
			for (PointOfServiceModel pos : warehouseModel.getPointsOfService()){
				String country = "";
				StoreOfProductData storeOfProductData = new StoreOfProductData();

				storeOfProductData.setStoreId(pos.getName());
				storeOfProductData.setStoreName(pos.getDisplayName());

				storeOfProductData.setAvaReleaseDay(stockLevelModel.getAvaPreOrderReleaseDay());
				int num = stockLevelModel.getPreOrderReleaseDay()!=null?stockLevelModel.getPreOrderReleaseDay():0;
				storeOfProductData.setFutureAvailableDate(num);

				int actualAmount = stockLevelModel.getAvailable() - stockLevelModel.getReserved();
				storeOfProductData.setInventory(actualAmount);

				storeOfProductData.setFutureInventory(stockLevelModel.getPreOrder());
//				storeOfProductData.setIsUseFutureStock(Boolean.FALSE);

				if (pos.getAddress()!=null&&pos.getAddress().getCountry()!=null){
					CountryModel addressCountryModel = pos.getAddress().getCountry();
					CountryData addressCountryData = countryConverter.convert(addressCountryModel);
					List<CountryData> addressCountryDataList = new ArrayList<>();
					addressCountryDataList.add(addressCountryData);
					storeOfProductData.setCountryDataList(addressCountryDataList);
					country = country + addressCountryModel.getName()+"  ";
					storeOfProductData.setCountryListString(country);
				}
				
				if(actualAmount>maxInventory)
				{
					maxInventory=actualAmount;
					dataList.add(0,storeOfProductData);
				}
				else
				{
					dataList.add(storeOfProductData);
				}
			}
		}
		return dataList;
	}

	@Override
	public boolean isAnonymousUser() {
		UserModel userModel = getUserService().getCurrentUser();
		return getUserService().isAnonymousUser(userModel);
	}
}
