package com.acerchem.storefront.controllers.pages;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import de.hybris.platform.acceleratorstorefrontcommons.controllers.pages.AbstractPageController;
import de.hybris.platform.commercefacades.order.data.OrderData;
import de.hybris.platform.commercefacades.order.data.OrderEntryData;
import de.hybris.platform.commercefacades.product.data.ProductData;
import de.hybris.platform.commerceservices.customer.CustomerAccountService;
import de.hybris.platform.core.model.order.OrderModel;
import de.hybris.platform.core.model.user.CustomerModel;
import de.hybris.platform.orderprocessing.model.OrderProcessModel;
import de.hybris.platform.processengine.BusinessProcessService;
import de.hybris.platform.servicelayer.dto.converter.Converter;
import de.hybris.platform.servicelayer.model.ModelService;
import de.hybris.platform.servicelayer.user.UserService;
import de.hybris.platform.store.BaseStoreModel;
import de.hybris.platform.store.services.BaseStoreService;

@Controller
@RequestMapping(value = "/jhtest")
public class MyJhTestContrillor extends AbstractPageController {

	@Resource
	private CustomerAccountService customerAccountService;
	@Resource
	private BaseStoreService baseStoreService;
	@Resource
	private BusinessProcessService businessProcessService;
	@Resource
	private ModelService modelService;
	@Resource
	private UserService userService;
	@Resource
	private Converter<OrderModel, OrderData> orderConverter;
	private OrderData orderData;

	// @Resource
	// private AcerChemOrderDao acerChemOrderDao;
	// @Resource
	// private ModelService modelService;
	// @Resource
	// private BusinessProcessService businessProcessService;

	@RequestMapping(value = "/apply/{code}")
	public String toApply(@PathVariable("code") final String formalcode, final Model model) {
		System.out.println("****" + formalcode);
		// OrderModel orderModel =
		// acerChemOrderDao.getOrderModelByCode("jh1234567").get(0);
		//
		//
		// // final OrderModel orderModel =
		// orderPlacedEvent.getProcess().getOrder();
		// final OrderProcessModel orderProcessModel = (OrderProcessModel)
		// businessProcessService.createProcess(
		// "orderConfirmationEmailProcess-" + orderModel.getCode() + "-" +
		// System.currentTimeMillis(),
		// "orderConfirmationEmailProcess");
		// orderProcessModel.setOrder(orderModel);
		// modelService.save(orderProcessModel);
		// businessProcessService.startProcess(orderProcessModel);
		//
		// System.out.println("****"+ orderModel.getCode());

		model.addAttribute("code", formalcode);
		// testOrderProcess();
		testOrderData();
		model.addAttribute("showdata", "call proccess finished!");

		return "pages/product/mytest-jh";
	}

	private void testOrderProcess() {
		final BaseStoreModel baseStoreModel = baseStoreService.getCurrentBaseStore();
		final OrderModel orderModel = customerAccountService
				.getOrderForCode((CustomerModel) userService.getCurrentUser(), "jh1234567", baseStoreModel);

		final OrderProcessModel orderProcessModel = (OrderProcessModel) businessProcessService.createProcess(
				"orderConfirmationEmailProcess-" + orderModel.getCode() + "-" + System.currentTimeMillis(),
				"orderConfirmationEmailProcess");
		orderProcessModel.setOrder(orderModel);
		modelService.save(orderProcessModel);
		businessProcessService.startProcess(orderProcessModel);
	}

	private void testOrderData() {
		final BaseStoreModel baseStoreModel = baseStoreService.getCurrentBaseStore();
		final OrderModel orderModel = customerAccountService
				.getOrderForCode((CustomerModel) userService.getCurrentUser(), "jh1234567", baseStoreModel);

		orderData = orderConverter.convert(orderModel);

		List<OrderEntryData> orderEntries = orderData.getEntries();
		String tempCode = "";

		long quantity = 0;
		double totalAmount = 0;
		long subQuantity = 0;
		double subAmount = 0;

		if (CollectionUtils.isNotEmpty(orderEntries)) {
			for (OrderEntryData orderEntry : orderEntries) {

				ProductData product = orderEntry.getProduct();

				// ProductItemDataOfEmail pie = new ProductItemDataOfEmail();

				System.out.println("code=" + product.getCode());

				System.out.println("name=" + product.getName());
				System.out.println("quantity=" + orderEntry.getQuantity());
				System.out.println("unitname=" + product.getUnitName());

			}
		}
		// show
		// String currency = orderData.get

	}
}