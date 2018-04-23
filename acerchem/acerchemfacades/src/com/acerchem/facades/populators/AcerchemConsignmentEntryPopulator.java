package com.acerchem.facades.populators;

import org.apache.commons.lang.StringUtils;

import de.hybris.platform.commercefacades.order.converters.populator.ConsignmentEntryPopulator;
import de.hybris.platform.commercefacades.order.data.ConsignmentEntryData;
import de.hybris.platform.ordersplitting.model.ConsignmentEntryModel;

/**
 * 
 * @author Jayson.wang 2018年4月23日
 */
public class AcerchemConsignmentEntryPopulator extends ConsignmentEntryPopulator {

	@Override
	public void populate(ConsignmentEntryModel source, ConsignmentEntryData target) {
		super.populate(source, target);
		
		if(StringUtils.isNotBlank(source.getBatchNum())){
			target.setBatchNum(source.getBatchNum());
		}
	}

}
