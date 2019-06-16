package com.acerchem.actions.order.auditing;

import com.hybris.backoffice.widgets.notificationarea.event.NotificationUtils;
import com.hybris.cockpitng.actions.ActionContext;
import com.hybris.cockpitng.actions.ActionResult;
import com.hybris.cockpitng.actions.CockpitAction;
import com.hybris.cockpitng.engine.impl.AbstractComponentWidgetAdapterAware;
import de.hybris.platform.core.model.order.OrderModel;
import de.hybris.platform.processengine.BusinessProcessEvent;
import de.hybris.platform.processengine.BusinessProcessService;
import de.hybris.platform.servicelayer.model.ModelService;
import org.apache.log4j.Logger;

import javax.annotation.Resource;
import java.util.EnumSet;


public class OrderReceiptsCreditAction extends AbstractComponentWidgetAdapterAware implements CockpitAction<OrderModel, Object>
{
	private static final Logger LOG = Logger.getLogger(OrderReceiptsCreditAction.class);

	@Resource
	BusinessProcessService businessProcessService;

	@Resource
	private ModelService modelService;

	@Override
	public ActionResult<Object> perform(ActionContext<OrderModel> ctx) {
		OrderModel order = (OrderModel) ctx.getData();

		ActionResult actionResult = new ActionResult("success");
		actionResult.setStatusFlags(EnumSet.of(ActionResult.StatusFlag.OBJECT_MODIFIED));
		//waitForEmCreditReceiptsConfirm

		LOG.info("---------------------------------------"+order.getOrderProcess().iterator().next().getCode());
		final String eventID = new StringBuilder()//
				.append(order.getOrderProcess().iterator().next().getCode())//
				.append("_")//
				.append("waitForCreditReceiptsConfirmEvent")//
				.toString();
		final BusinessProcessEvent event = BusinessProcessEvent.builder(eventID)
				.withChoice("waitForEmCreditReceiptsConfirm").build();
		businessProcessService.triggerEvent(event);
		order.setEmployeeConfirmDelivery(true);
		this.modelService.save(order);
		LOG.info("--------------------end-------------------"+order.getEmployeeConfirmDelivery());
		return actionResult;
	}

	protected String getNotificationSource(ActionContext<?> actionContext) {
		return NotificationUtils.getWidgetNotificationSource(actionContext);
	}

	public boolean canPerform(ActionContext<OrderModel> ctx) {
		return true;
	}
}
