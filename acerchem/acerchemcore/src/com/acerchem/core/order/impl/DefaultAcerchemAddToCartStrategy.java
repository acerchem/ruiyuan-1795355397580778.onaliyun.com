package com.acerchem.core.order.impl;

import de.hybris.platform.commerceservices.order.CommerceCartModification;
import de.hybris.platform.commerceservices.order.CommerceCartModificationException;
import de.hybris.platform.commerceservices.order.CommerceCartModificationStatus;
import de.hybris.platform.commerceservices.order.impl.DefaultCommerceAddToCartStrategy;
import de.hybris.platform.commerceservices.service.data.CommerceCartParameter;
import de.hybris.platform.core.model.order.CartEntryModel;
import de.hybris.platform.core.model.order.CartModel;
import de.hybris.platform.core.model.product.ProductModel;
import de.hybris.platform.storelocator.model.PointOfServiceModel;

public class DefaultAcerchemAddToCartStrategy extends  DefaultCommerceAddToCartStrategy{
	
	
	protected CommerceCartModification doAddToCart(final CommerceCartParameter parameter) throws CommerceCartModificationException
	{
		CommerceCartModification modification;

		final CartModel cartModel = parameter.getCart();
		final ProductModel productModel = parameter.getProduct();
		final long quantityToAdd = parameter.getQuantity();
		final PointOfServiceModel deliveryPointOfService = parameter.getPointOfService();

		this.beforeAddToCart(parameter);
		validateAddToCart(parameter);

		if (isProductForCode(parameter).booleanValue())
		{
			// So now work out what the maximum allowed to be added is (note that this may be negative!)
			final long actualAllowedQuantityChange = getAllowedCartAdjustmentForProduct(cartModel, productModel, quantityToAdd,
					deliveryPointOfService);
			//final Integer maxOrderQuantity = productModel.getMaxOrderQuantity();

			final Integer minOrderQuantity = productModel.getMinOrderQuantity();
			final long cartLevel = checkCartLevel(productModel, cartModel, deliveryPointOfService);
			final long cartLevelAfterQuantityChange = actualAllowedQuantityChange + cartLevel;

			long diffQuantity = 0;
			//---add by Randy
			// 判断是否添加购物车的数量大于最小购买量
			if (isMinOrderQuantitySet(minOrderQuantity))
			{
				diffQuantity = cartLevelAfterQuantityChange - minOrderQuantity.longValue();

			}

			if (actualAllowedQuantityChange > 0)
			{
				// We are allowed to add items to the cart
				final CartEntryModel entryModel = addCartEntry(parameter, actualAllowedQuantityChange);
				getModelService().save(entryModel);

				final String statusCode = getStatusCodeDiffQuantityChange(diffQuantity);

				modification = createAddToCartResp(parameter, statusCode, entryModel, actualAllowedQuantityChange);
			}
			else
			{
				// Not allowed to add any quantity, or maybe even asked to reduce the quantity
				// Do nothing!
				//final String status = getStatusCodeForNotAllowedQuantityChange(maxOrderQuantity, maxOrderQuantity);

				modification = createAddToCartResp(parameter, CommerceCartModificationStatus.NO_STOCK, createEmptyCartEntry(parameter), 0);

			}
		}
		else
		{
			modification = createAddToCartResp(parameter, CommerceCartModificationStatus.UNAVAILABLE,
					createEmptyCartEntry(parameter), 0);
		}

		return modification;
	}
	
	protected String getStatusCodeDiffQuantityChange(final long diffQuantity)
	{
		// Are we able to add the quantity we requested?
		if (diffQuantity < 0)
		{
			return CommerceCartModificationStatus.MAX_ORDER_QUANTITY_EXCEEDED;
		}
		else
		{
			return CommerceCartModificationStatus.SUCCESS;
		}

	}
	
	
	protected boolean isMinOrderQuantitySet(final Integer minOrderQuantity)
	{
		return minOrderQuantity != null;
	}

}
