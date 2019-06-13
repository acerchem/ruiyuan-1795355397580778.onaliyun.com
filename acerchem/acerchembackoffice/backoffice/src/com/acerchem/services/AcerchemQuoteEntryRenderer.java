package com.acerchem.services;

import com.hybris.cockpitng.dataaccess.facades.type.DataType;
import com.hybris.cockpitng.engine.WidgetInstanceManager;
import com.hybris.cockpitng.widgets.common.AbstractWidgetComponentRenderer;
import de.hybris.platform.core.model.order.QuoteEntryModel;
import de.hybris.platform.core.model.product.ProductModel;
import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Component;
import org.zkoss.zul.Div;
import org.zkoss.zul.Label;


public class AcerchemQuoteEntryRenderer extends AbstractWidgetComponentRenderer<Component, Object, QuoteEntryModel>
{
	public void render(Component parent, Object configuration, QuoteEntryModel data, DataType dataType, WidgetInstanceManager widgetInstanceManager) {
		Div contactTypePanel = new Div();

		if(data.getOrder().getDeliveryMode()!=null){
			if(data.getOrder().getDeliveryMode().getCode().equals("DELIVERY_GROSS")){
				Label contactTypeLabel = new Label("DDP");
				contactTypePanel.appendChild(contactTypeLabel);
			}else{

				Label contactTypeLabel = new Label("FCA");
				contactTypePanel.appendChild(contactTypeLabel);
			}
		}
		parent.appendChild(contactTypePanel);
		this.fireComponentRendered(parent, configuration, data);
	}
}
