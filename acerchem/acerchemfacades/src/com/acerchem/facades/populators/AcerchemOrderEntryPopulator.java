package com.acerchem.facades.populators;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.hybris.platform.comments.model.CommentModel;
import de.hybris.platform.commercefacades.comment.data.CommentData;
import de.hybris.platform.commercefacades.order.data.ConfigurationInfoData;
import de.hybris.platform.commercefacades.order.data.ConsignmentEntryData;
import de.hybris.platform.commercefacades.order.data.DeliveryModeData;
import de.hybris.platform.commercefacades.order.data.OrderEntryData;
import de.hybris.platform.commercefacades.product.PriceDataFactory;
import de.hybris.platform.commercefacades.product.data.PriceData;
import de.hybris.platform.commercefacades.product.data.PriceDataType;
import de.hybris.platform.commercefacades.product.data.ProductData;
import de.hybris.platform.commercefacades.storelocator.data.PointOfServiceData;
import de.hybris.platform.commerceservices.strategies.ModifiableChecker;
import de.hybris.platform.converters.Populator;
import de.hybris.platform.core.model.order.AbstractOrderEntryModel;
import de.hybris.platform.core.model.order.OrderEntryModel;
import de.hybris.platform.core.model.order.delivery.DeliveryModeModel;
import de.hybris.platform.core.model.product.ProductModel;
import de.hybris.platform.order.EntryGroupService;
import de.hybris.platform.order.model.AbstractOrderEntryProductInfoModel;
import de.hybris.platform.ordersplitting.model.ConsignmentEntryModel;
import de.hybris.platform.servicelayer.dto.converter.Converter;
import de.hybris.platform.storelocator.model.PointOfServiceModel;

public class AcerchemOrderEntryPopulator implements Populator<OrderEntryModel, OrderEntryData> {
	private static final Logger LOG = LoggerFactory.getLogger(AcerchemOrderEntryPopulator.class);
	@Resource
	private Converter<ConsignmentEntryModel, ConsignmentEntryData> consignmentEntryConverter;
	@Resource
	private Converter<AbstractOrderEntryModel,OrderEntryData> orderEntryConverter;
	@Resource
	private Converter<ProductModel, ProductData> orderEntryProductConverter;//productConverter;
	@Resource
	private Converter<DeliveryModeModel, DeliveryModeData> deliveryModeConverter;
	@Resource
	private PriceDataFactory priceDataFactory;
	@Resource
	private ModifiableChecker<AbstractOrderEntryModel> commerceOrderEntryModifiableChecker ;//entryOrderChecker;
	@Resource
	private Converter<PointOfServiceModel, PointOfServiceData> pointOfServiceConverter;
	@Resource
	private Converter<AbstractOrderEntryProductInfoModel, List<ConfigurationInfoData>> orderEntryProductInfoConverter ;//productConfigurationConverter;
	@Resource
	private EntryGroupService entryGroupService;
	@Resource
	private Converter<CommentModel, CommentData> orderCommentConverter;

	@Override
	public void populate(OrderEntryModel source, OrderEntryData target) {
		
		
		
		//add consignmentEntries
		if(CollectionUtils.isNotEmpty(source.getConsignmentEntries())){	
			
			//
			
			List<ConsignmentEntryModel> entries = new ArrayList<ConsignmentEntryModel>(source.getConsignmentEntries());
			//List<ConsignmentEntryData> dataEntris = getConsignmentEntryConverter().convertAll(entries);
			
			List<ConsignmentEntryData> dataEntris = new ArrayList<ConsignmentEntryData>();
			
			
			for(ConsignmentEntryModel model:entries){
				ConsignmentEntryData data = new ConsignmentEntryData();
				
				data.setBatchNum(model.getBatchNum());
				data.setQuantity(model.getQuantity());
				data.setShippedQuantity(model.getShippedQuantity());
				
				//data.setOrderEntry(orderEntryConverter.convert(model.getOrderEntry()));
				AbstractOrderEntryModel orderEntry =model.getOrderEntry();
				OrderEntryData orderEntryData = new OrderEntryData();
				
				addCommon(orderEntry, orderEntryData);
				addProduct(orderEntry, orderEntryData);
				addTotals(orderEntry, orderEntryData);
				addDeliveryMode(orderEntry, orderEntryData);
				addConfigurations(orderEntry, orderEntryData);
				addEntryGroups(orderEntry, orderEntryData);
				addComments(orderEntry, orderEntryData);
				
				data.setOrderEntry(orderEntryData);
				
				dataEntris.add(data);
				
			}
			
			
			target.setConsignmentEntries(dataEntris);			
			
		}
		
	}

	protected void addEntryGroups(final AbstractOrderEntryModel source, final OrderEntryData target)
	{
		if (source.getEntryGroupNumbers() != null)
		{
			target.setEntryGroupNumbers(new ArrayList<>(source.getEntryGroupNumbers()));
		}
	}

	protected void addConfigurations(final AbstractOrderEntryModel source, final OrderEntryData target)
	{
		final List<ConfigurationInfoData> configurations
				= orderEntryProductInfoConverter.convertAll(source.getProductInfos()).stream()
				.flatMap(Collection::stream)
				.collect(Collectors.toList());
		target.setConfigurationInfos(configurations);
		if (configurations != null)
		{
			target.setStatusSummaryMap(configurations.stream()
					.peek(i -> {
						if (i.getStatus() == null)
						{
							LOG.warn("Missing status in configuration {}", i.getConfigurationLabel());
						}
					})
					.peek(config -> {
						if (config.getStatus() == null)
						{
							throw new IllegalStateException("Configuration info " + config.getConfigurationLabel()
									+ " has null status. Check populator of configuration type " + config.getConfiguratorType());
						}
					}).map(ConfigurationInfoData::getStatus)
					.collect(Collectors.toMap(Function.identity(), item -> 1, (first, second) -> first + second)));
		}
	}

	protected void addDeliveryMode(final AbstractOrderEntryModel orderEntry, final OrderEntryData entry)
	{
		if (orderEntry.getDeliveryMode() != null)
		{
			entry.setDeliveryMode(deliveryModeConverter.convert(orderEntry.getDeliveryMode()));
		}

		if (orderEntry.getDeliveryPointOfService() != null)
		{
			entry.setDeliveryPointOfService(pointOfServiceConverter.convert(orderEntry.getDeliveryPointOfService()));
		}
	}
	
	protected void addCommon(final AbstractOrderEntryModel orderEntry, final OrderEntryData entry)
	{
		entry.setEntryNumber(orderEntry.getEntryNumber());
		entry.setQuantity(orderEntry.getQuantity());
		adjustUpdateable(entry, orderEntry);
	}
	protected void adjustUpdateable(final OrderEntryData entry, final AbstractOrderEntryModel entryToUpdate)
	{
		entry.setUpdateable(commerceOrderEntryModifiableChecker.canModify(entryToUpdate));
	}

	protected void addProduct(final AbstractOrderEntryModel orderEntry, final OrderEntryData entry)
	{
		entry.setProduct(orderEntryProductConverter.convert(orderEntry.getProduct()));
	}

	protected void addTotals(final AbstractOrderEntryModel orderEntry, final OrderEntryData entry)
	{
		if (orderEntry.getBasePrice() != null)
		{
			entry.setBasePrice(createPrice(orderEntry, orderEntry.getBasePrice()));
		}
		if (orderEntry.getTotalPrice() != null)
		{
			entry.setTotalPrice(createPrice(orderEntry, orderEntry.getTotalPrice()));
		}
	}

	protected void addComments(final AbstractOrderEntryModel orderEntry, final OrderEntryData entry)
	{
		entry.setComments(orderCommentConverter.convertAll(orderEntry.getComments()));
	}

	protected PriceData createPrice(final AbstractOrderEntryModel orderEntry, final Double val)
	{
		return priceDataFactory.create(PriceDataType.BUY, BigDecimal.valueOf(val.doubleValue()),
				orderEntry.getOrder().getCurrency());
	}

	
	
}
