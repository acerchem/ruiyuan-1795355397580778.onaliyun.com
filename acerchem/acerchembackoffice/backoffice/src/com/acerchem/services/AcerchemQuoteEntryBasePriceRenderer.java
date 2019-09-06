package com.acerchem.services;

import com.hybris.cockpitng.dataaccess.facades.type.DataType;
import com.hybris.cockpitng.engine.WidgetInstanceManager;
import com.hybris.cockpitng.widgets.common.AbstractWidgetComponentRenderer;
import de.hybris.platform.core.model.order.QuoteEntryModel;
import de.hybris.platform.core.model.product.ProductModel;
import org.zkoss.zk.ui.Component;
import org.zkoss.zul.Div;
import org.zkoss.zul.Label;

import java.math.BigDecimal;


public class AcerchemQuoteEntryBasePriceRenderer extends AbstractWidgetComponentRenderer<Component, Object, QuoteEntryModel>
{
	public void render(Component parent, Object configuration, QuoteEntryModel data, DataType dataType, WidgetInstanceManager widgetInstanceManager) {
		Div contactTypePanel = new Div();
		Double basePrice = data.getBasePrice();
		ProductModel product = data.getProduct();
		if(product!=null && basePrice!=null){
			String netweight = product.getNetWeight();
			if(netweight!=null)
			{
				BigDecimal netweightBig = new BigDecimal(netweight);
				BigDecimal basePriceBig = new BigDecimal(basePrice);
				basePriceBig = basePriceBig.divide(netweightBig);
				basePriceBig = basePriceBig.setScale(2, BigDecimal.ROUND_HALF_UP);
				basePrice = basePriceBig.doubleValue();
			}
		}
		Label contactTypeLabel = new Label(basePrice!=null?basePrice.toString():"0");
		contactTypePanel.appendChild(contactTypeLabel);
		parent.appendChild(contactTypePanel);
		this.fireComponentRendered(parent, configuration, data);
	}
}
