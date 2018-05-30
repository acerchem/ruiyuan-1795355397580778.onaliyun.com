package com.acerchem.facades.populators;

import de.hybris.platform.commercefacades.storelocator.converters.populator.PointOfServicePopulator;
import de.hybris.platform.commercefacades.storelocator.data.PointOfServiceData;
import de.hybris.platform.ordersplitting.model.WarehouseModel;
import de.hybris.platform.storelocator.model.PointOfServiceModel;

/**
 * 
 * @author Jayson.wang 2018年5月30日
 */
public class AcerchemPointOfServicePopulator extends PointOfServicePopulator {

	@Override
	public void populate(final PointOfServiceModel source, final PointOfServiceData target) {
		super.populate(source, target);
		if (source.getWarehouses() != null && source.getWarehouses().size() > 0) {
			for (final WarehouseModel warehouse : source.getWarehouses()) {
				if (warehouse.getDefault()) {
					if (warehouse.getRegion() != null) {
						target.setDefaultWarehouseRegionName(warehouse.getRegion().getName());
					}
				}
			}
		}
	}

}
