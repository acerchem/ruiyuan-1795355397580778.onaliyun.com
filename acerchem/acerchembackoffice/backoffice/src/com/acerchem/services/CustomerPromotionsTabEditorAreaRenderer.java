package com.acerchem.services;

import com.hybris.cockpitng.core.config.impl.jaxb.editorarea.AbstractTab;
import com.hybris.cockpitng.dataaccess.facades.type.DataType;
import com.hybris.cockpitng.engine.WidgetInstanceManager;

import de.hybris.platform.core.model.order.AbstractOrderEntryModel;
import de.hybris.platform.core.model.order.AbstractOrderModel;
import de.hybris.platform.promotions.backoffice.editors.PromotionsTabEditorAreaRenderer;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


//PromotionsTabEditorAreaRenderer
public class CustomerPromotionsTabEditorAreaRenderer extends PromotionsTabEditorAreaRenderer
{
	private static final String ZUL_FILE = "cng/customerPromotionsTab.zul";

	protected Component renderTab(Component parent, AbstractTab configuration, AbstractOrderModel abstractOrder, DataType dataType, WidgetInstanceManager widgetInstanceManager) {
		Map args = new HashMap();
		List<AbstractOrderEntryModel> orderEntry = new ArrayList<AbstractOrderEntryModel>();
		if(abstractOrder != null){
			orderEntry = abstractOrder.getEntries();
		}
		args.put("orderEntry", orderEntry);
		return Executions.getCurrent().createComponents(ZUL_FILE, parent, args);
	}

}
