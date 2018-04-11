package com.acerchem.storefront.controllers.pages;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.acerchem.core.dao.AcerChemOrderDao;

import de.hybris.platform.acceleratorstorefrontcommons.controllers.pages.AbstractPageController;
import de.hybris.platform.core.model.order.OrderModel;
import de.hybris.platform.orderprocessing.model.OrderProcessModel;
import de.hybris.platform.processengine.BusinessProcessService;
import de.hybris.platform.servicelayer.model.ModelService;

@Controller
@RequestMapping(value = "/jhtest")
public class MyJhTestContrillor extends AbstractPageController {

	@Resource
	private AcerChemOrderDao acerChemOrderDao; 
	@Resource
	private ModelService modelService;
	@Resource
	private BusinessProcessService businessProcessService;
	
	@RequestMapping(value = "/apply/{code}")
	public String toApply(@PathVariable("code") final String formalcode, final Model model)
	{
		System.out.println("****" + formalcode);
		OrderModel orderModel = acerChemOrderDao.getOrderModelByCode("jh1234567").get(0);
		
		
		// final OrderModel orderModel = orderPlacedEvent.getProcess().getOrder();
		  final OrderProcessModel orderProcessModel = (OrderProcessModel) businessProcessService.createProcess(
		    "orderConfirmationEmailProcess-" + orderModel.getCode() + "-" + System.currentTimeMillis(),
		    "orderConfirmationEmailProcess");
		  orderProcessModel.setOrder(orderModel);
		  modelService.save(orderProcessModel);
		  businessProcessService.startProcess(orderProcessModel);
		
		System.out.println("****"+ orderModel.getCode());
		return "pages/product/mytest-jh";
	}
}
