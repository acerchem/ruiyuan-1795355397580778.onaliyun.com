package com.acerchem.services;

import com.hybris.cockpitng.core.config.impl.jaxb.editorarea.AbstractTab;
import com.hybris.cockpitng.dataaccess.facades.type.DataType;
import com.hybris.cockpitng.engine.WidgetInstanceManager;
import de.hybris.platform.core.model.order.AbstractOrderModel;
import de.hybris.platform.jalo.JaloSession;
import de.hybris.platform.jalo.order.AbstractOrder;
import de.hybris.platform.promotions.PromotionsService;
import de.hybris.platform.promotions.backoffice.editors.PromotionsTabEditorAreaRenderer;
import de.hybris.platform.promotions.result.PromotionOrderResults;
import org.springframework.beans.factory.annotation.Required;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;


//PromotionsTabEditorAreaRenderer
public class CustomerPromotionsTabEditorAreaRenderer extends PromotionsTabEditorAreaRenderer
{
	private PromotionsService promotionsService;

	private static final String ZUL_FILE = "cng/customerPromotionsTab.zul";

	protected Component renderTab(Component parent, AbstractTab configuration, AbstractOrderModel abstractOrder, DataType dataType, WidgetInstanceManager widgetInstanceManager) {
		Map args = new HashMap();
		PromotionOrderResults promotionOrderResults;
		if(abstractOrder != null){
			promotionOrderResults = getPromotionsService().getPromotionResults(abstractOrder);
		}else{
			promotionOrderResults = new PromotionOrderResults(
					JaloSession.getCurrentSession().getSessionContext(), (AbstractOrder)null, Collections.emptyList(), 0.0D);
		}
		args.put("promotionResults", promotionOrderResults);
		return Executions.getCurrent().createComponents(ZUL_FILE, parent, args);
	}

	public PromotionsService getPromotionsService() {
		return this.promotionsService;
	}

	@Required
	public void setPromotionsService(PromotionsService promotionsService) {
		this.promotionsService = promotionsService;
	}
}
