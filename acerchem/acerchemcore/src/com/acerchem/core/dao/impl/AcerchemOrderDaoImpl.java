package com.acerchem.core.dao.impl;

import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.Resource;
import com.acerchem.core.dao.AcerchemOrderDao;
import de.hybris.platform.servicelayer.search.SearchResult;
import de.hybris.platform.commercefacades.order.data.MonthlySalesAnalysis;
import de.hybris.platform.commercefacades.order.data.OrderDetailsReportData;
import de.hybris.platform.commercefacades.user.data.AddressData;
import de.hybris.platform.core.model.order.OrderEntryModel;
import de.hybris.platform.core.model.order.OrderModel;
import de.hybris.platform.core.model.user.AddressModel;
import de.hybris.platform.servicelayer.dto.converter.Converter;
import de.hybris.platform.servicelayer.search.FlexibleSearchQuery;
import de.hybris.platform.servicelayer.search.FlexibleSearchService;

public class AcerchemOrderDaoImpl implements AcerchemOrderDao{

	@Resource
	private FlexibleSearchService flexibleSearchService;
	@Resource
	private Converter<AddressModel, AddressData> addressConverter;
	
	@Override
	public List<OrderDetailsReportData> getOrderDetails(Integer month,String area,String countryCode,String userName,String orderCode) {
		
		final Map<String, Object> params = new HashMap<String, Object>();
		String SQL = "select {e.pk} from {OrderEntry as e"+
				" JOIN Order as o ON {e:order} = {o:pk}" +
				" JOIN Customer as u ON {u:pk} = {o:user}" +
				" JOIN Address as a ON {a:pk} = {o:deliveryAddress}" +
				" JOIN Country as c ON {c:pk} = {a:country}" +
				"} where 1=1 ";
		
		if(month>=1&&month<=12)
		{
			SQL += " AND datepart(mm,{o:creationtime}) =?month " ;
			params.put("month", month);
		}
		if(area!=null&&!area.equals(""))
		{
			SQL += " AND {u:area} =?area " ;
			params.put("area", area);
		}
		if(countryCode!=null&&!countryCode.equals(""))
		{
			SQL += " AND {c:isocode} =?isocode " ;
			params.put("isocode", countryCode);
		}
		if(userName!=null&&!userName.equals(""))
		{
			SQL += " AND {u:name} =?userName " ;
			params.put("userName", userName);
		}
		
		if(orderCode!=null&&!orderCode.equals(""))
		{
			SQL += " AND {o:code} =?orderCode ";
			params.put("orderCode", orderCode);
		}
		
		final StringBuilder builder = new StringBuilder(SQL);
		final FlexibleSearchQuery query = new FlexibleSearchQuery(builder.toString());
		query.addQueryParameters(params);
		
		final SearchResult<OrderEntryModel> result = flexibleSearchService.search(query);
		
		
		List<OrderDetailsReportData> orderDetails = new ArrayList<OrderDetailsReportData>();
		for(OrderEntryModel od:result.getResult())
		{
			AddressModel addressModel = null;
			if(od.getOrder().getDeliveryMode().getCode().equals("DELIVERY_MENTION"))
			{//自提
				for(AddressModel address:od.getOrder().getUser().getAddresses())
				{
					if(address.getContactAddress())
					{
						addressModel=address;
					}
				}
			}
			else
			{//送货
				addressModel=od.getOrder().getDeliveryAddress();
			}
			
			OrderDetailsReportData detail=new OrderDetailsReportData();
			detail.setCurrency(od.getOrder().getCurrency().getName());
			detail.setCountry(addressModel.getCountry().getName());
			detail.setArea(od.getOrder().getUser().getArea().toString());
			detail.setOrderCode(od.getOrder().getCode());
			detail.setOrderTime(od.getOrder().getCreationtime());
			detail.setProductName(od.getProduct().getName());
			detail.setProductQuantity(od.getQuantity());
			detail.setOrderAmount(od.getTotalPrice());
			detail.setUserUid(od.getOrder().getUser().getUid());
			detail.setDeliveryAddress(addressConverter.convert(addressModel));
			detail.setSalesman(od.getOrder().getPlacedBy().getName());
			detail.setSupplier(od.getProduct().getAcerChemVendor().getName());
			orderDetails.add(detail);
		}
		return orderDetails;
		
	}

	@SuppressWarnings("deprecation")
	@Override
	public List<MonthlySalesAnalysis> getMonthlySalesAnalysis(Integer year, String area) {
		
		final Map<String, Object> params = new HashMap<String, Object>();
		String SQL = "select {o.pk} from {Order as o JOIN Customer as u ON {u:pk} = {o:user}} where 1=1 ";
		
		if(year>0)
		{
			SQL += " AND datepart(yyyy,{o:creationtime}) =?year " ;
			params.put("year", year);
		}
		if(area!=null&&!area.equals(""))
		{
			SQL += " AND {u:area} =?area " ;
			params.put("area", area);
		}
		SQL += " Order by {u:area} " ;
		
		final StringBuilder builder = new StringBuilder(SQL);
		final FlexibleSearchQuery query = new FlexibleSearchQuery(builder.toString());
		query.addQueryParameters(params);
		query.setResultClassList(Arrays.asList(OrderModel.class,Integer.class));
		final SearchResult<OrderModel> result = flexibleSearchService.search(query);
		
		
		Map<String,Map<Integer,Double>> countryMap=new HashMap<String,Map<Integer,Double>>();
		for(OrderModel oo:result.getResult())
		{
			Map<Integer,Double> MonthAmount=new HashMap<Integer,Double>();
			Double Amount=Double.valueOf(0);
			if(countryMap.get(oo.getDeliveryAddress().getCountry().getName())!=null)
			{
				Amount=countryMap.get(oo.getDeliveryAddress().getCountry().getName()).get(oo.getCreationtime().getMonth());
				MonthAmount=countryMap.get(oo.getDeliveryAddress().getCountry().getName());
			}
			
			if(oo.getCurrency().getIsocode().equals("USD"))
			{
				Amount+=oo.getTotalPrice()/oo.getCurrency().getConversion();
			}
			else
			{
				Amount+=oo.getTotalPrice();
			}
			MonthAmount.put(oo.getCreationtime().getMonth(),Amount);
			countryMap.put(oo.getDeliveryAddress().getCountry().getName(), MonthAmount);
		}
		
		List<MonthlySalesAnalysis> orderDetails = new ArrayList<MonthlySalesAnalysis>();
		for(String country : countryMap.keySet()){
			if(countryMap.get(country)!=null)
			{
				Map<Integer,Double> MonthMap = countryMap.get(country);
				MonthlySalesAnalysis detail=new MonthlySalesAnalysis();
				detail.setCountry(country);
				detail.setJanuaryAmount(MonthMap.get(1)!=null?MonthMap.get(1):0);
				detail.setFebruaryAmount(MonthMap.get(2)!=null?MonthMap.get(2):0);
				detail.setMarchAmount(MonthMap.get(3)!=null?MonthMap.get(3):0);
				detail.setAprllAmount(MonthMap.get(4)!=null?MonthMap.get(4):0);
				detail.setMayAmount(MonthMap.get(5)!=null?MonthMap.get(5):0);
				detail.setJuneAmount(MonthMap.get(6)!=null?MonthMap.get(6):0);
				detail.setJulyAmount(MonthMap.get(7)!=null?MonthMap.get(7):0);
				detail.setAugustAmount(MonthMap.get(8)!=null?MonthMap.get(8):0);
				detail.setSeptemberAmount(MonthMap.get(9)!=null?MonthMap.get(9):0);
				detail.setOctoberAmount(MonthMap.get(10)!=null?MonthMap.get(10):0);
				detail.setNovemberAmount(MonthMap.get(11)!=null?MonthMap.get(11):0);
				detail.setDecemberAmount(MonthMap.get(12)!=null?MonthMap.get(12):0);
				orderDetails.add(detail);
			}
		}
		return orderDetails;
	}
	
}
