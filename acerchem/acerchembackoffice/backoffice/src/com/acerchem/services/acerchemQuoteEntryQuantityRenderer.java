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


public class acerchemQuoteEntryQuantityRenderer extends AbstractWidgetComponentRenderer<Component, Object, QuoteEntryModel>
{
	public void render(Component parent, Object configuration, QuoteEntryModel data, DataType dataType, WidgetInstanceManager widgetInstanceManager) {
		Div contactTypePanel = new Div();
		Long quantity = data.getQuantity();
		String quantityS = "";
		ProductModel product = data.getProduct();
		if(product!=null && quantity!=null){
			String netweight = product.getNetWeight();
			if(netweight!=null)
			{
				BigDecimal netweightBig = new BigDecimal(netweight);
				BigDecimal quantityBig = new BigDecimal(quantity);
				quantityBig = quantityBig.multiply(netweightBig).setScale(2, BigDecimal.ROUND_HALF_UP);
				quantityS = quantityBig.toString();
			}else{
				quantityS = quantity.toString();
			}
		}
		Label contactTypeLabel = new Label(quantityS);
		contactTypePanel.appendChild(contactTypeLabel);
		parent.appendChild(contactTypePanel);
		this.fireComponentRendered(parent, configuration, data);
	}
}
