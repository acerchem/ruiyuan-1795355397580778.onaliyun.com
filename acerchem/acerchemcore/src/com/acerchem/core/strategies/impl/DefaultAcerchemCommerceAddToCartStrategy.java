package com.acerchem.core.strategies.impl;

import com.acerchem.core.strategies.AcerchemCommerceAddToCartStrategy;
import de.hybris.platform.basecommerce.enums.InStockStatus;
import de.hybris.platform.commerceservices.order.CommerceCartModification;
import de.hybris.platform.commerceservices.order.CommerceCartModificationException;
import de.hybris.platform.commerceservices.order.CommerceCartModificationStatus;
import de.hybris.platform.commerceservices.order.impl.DefaultCommerceAddToCartStrategy;
import de.hybris.platform.commerceservices.service.data.CommerceCartParameter;
import de.hybris.platform.core.model.order.AbstractOrderEntryModel;
import de.hybris.platform.core.model.order.AbstractOrderModel;
import de.hybris.platform.core.model.order.CartEntryModel;
import de.hybris.platform.core.model.order.CartModel;
import de.hybris.platform.core.model.product.ProductModel;
import de.hybris.platform.ordersplitting.WarehouseService;
import de.hybris.platform.ordersplitting.model.StockLevelModel;
import de.hybris.platform.ordersplitting.model.WarehouseModel;
import de.hybris.platform.servicelayer.util.ServicesUtil;
import de.hybris.platform.store.BaseStoreModel;
import de.hybris.platform.storelocator.model.PointOfServiceModel;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Required;

import javax.annotation.Nonnull;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Created by Jacob.Ji on 2018/3/23.
 */
public class DefaultAcerchemCommerceAddToCartStrategy extends DefaultCommerceAddToCartStrategy implements AcerchemCommerceAddToCartStrategy {

    private WarehouseService warehouseService;


    @Override
    public CommerceCartModification addToCart(CommerceCartParameter parameter) throws CommerceCartModificationException {
        final CommerceCartModification modification = doAddToCart(parameter);
        getCommerceCartCalculationStrategy().calculateCart(parameter);
        afterAddToCart(parameter, modification);
        // Here the entry is fully populated, so we can search for a similar one and merge.
        mergeEntry(modification, parameter);
        return modification;
    }

    @Override
    protected CommerceCartModification doAddToCart(CommerceCartParameter parameter) throws CommerceCartModificationException {
        CommerceCartModification modification;

        final CartModel cartModel = parameter.getCart();
        final ProductModel productModel = parameter.getProduct();
        final long quantityToAdd = parameter.getQuantity();

        PointOfServiceModel deliveryPointOfService = parameter.getPointOfService();

        final boolean isUseFutureStock = parameter.getIsUseFutureStock();

        this.beforeAddToCart(parameter);
        validateAddToCart(parameter);

          if (isProductForCode(parameter).booleanValue())
        {
            // So now work out what the maximum allowed to be added is (note that this may be negative!)
            final long actualAllowedQuantityChange = this.getAllowedCartAdjustmentForProduct(cartModel, productModel, quantityToAdd,
                    deliveryPointOfService, isUseFutureStock);
            final Integer maxOrderQuantity = productModel.getMaxOrderQuantity();
            final long cartLevel = checkCartLevel(productModel, cartModel, deliveryPointOfService);
            final long cartLevelAfterQuantityChange = actualAllowedQuantityChange + cartLevel;

            if (actualAllowedQuantityChange > 0)
            {
                // We are allowed to add items to the cart
                final CartEntryModel entryModel = addCartEntry(parameter, actualAllowedQuantityChange);
                entryModel.setIsUseFutureStock(parameter.getIsUseFutureStock());
                entryModel.setAvailableDate(parameter.getAvailableDate());
                getModelService().save(entryModel);

                final String statusCode = getStatusCodeAllowedQuantityChange(actualAllowedQuantityChange, maxOrderQuantity,
                        quantityToAdd, cartLevelAfterQuantityChange);

                modification = createAddToCartResp(parameter, statusCode, entryModel, actualAllowedQuantityChange);
            }
            else
            {
                // Not allowed to add any quantity, or maybe even asked to reduce the quantity
                // Do nothing!
                final String status = getStatusCodeForNotAllowedQuantityChange(maxOrderQuantity, maxOrderQuantity);

                modification = createAddToCartResp(parameter, status, createEmptyCartEntry(parameter), 0);

            }
        }
        else
        {
            modification = createAddToCartResp(parameter, CommerceCartModificationStatus.UNAVAILABLE,
                    createEmptyCartEntry(parameter), 0);
        }

        return modification;
    }

    /**
     * Work out the allowed quantity adjustment for a product in the cart given a requested quantity change.
     *
     * @param cartModel
     *           the cart
     * @param productModel
     *           the product in the cart
     * @param quantityToAdd
     *           the amount to increase the quantity of the product in the cart, may be negative if the request is to
     *           reduce the quantity
     * @param pointOfServiceModel
     *           the PointOfService to check stock at, can be null
     * @return the allowed adjustment
     */
    protected long getAllowedCartAdjustmentForProduct(final CartModel cartModel, final ProductModel productModel,
                                                      final long quantityToAdd, final PointOfServiceModel pointOfServiceModel,
                                                      final boolean isUseFuturnStock)
    {
        final long cartLevel = checkCartLevel(productModel, cartModel, pointOfServiceModel);

        long stockLevel = 0L;
        if (isUseFuturnStock){
            for (StockLevelModel stockLevelModel : productModel.getStockLevels())
            {
                // If any stock level is flagged as FORCEINSTOCK then return null to indicate in stock
                if (InStockStatus.FORCEINSTOCK.equals(stockLevelModel.getInStockStatus()))
                {

                }
                // If any stock level is flagged as FORCEOUTOFSTOCK then we skip over it
                if (!InStockStatus.FORCEOUTOFSTOCK.equals(stockLevelModel.getInStockStatus()))
                {
                    final long availableToSellQuantity = stockLevelModel.getPreOrder();
                    if (availableToSellQuantity > 0 || !stockLevelModel.isTreatNegativeAsZero())
                    {
                        stockLevel += availableToSellQuantity;
                    }
                }
            }
        }else {
            stockLevel = getAvailableStockLevel(productModel, pointOfServiceModel);
        }

        // How many will we have in our cart if we add quantity
        final long newTotalQuantity = cartLevel + quantityToAdd;

        // Now limit that to the total available in stock
        final long newTotalQuantityAfterStockLimit = Math.min(newTotalQuantity, stockLevel);

        // So now work out what the maximum allowed to be added is (note that
        // this may be negative!)
        final Integer maxOrderQuantity = productModel.getMaxOrderQuantity();

        if (isMaxOrderQuantitySet(maxOrderQuantity))
        {
            final long newTotalQuantityAfterProductMaxOrder = Math
                    .min(newTotalQuantityAfterStockLimit, maxOrderQuantity.longValue());
            return newTotalQuantityAfterProductMaxOrder - cartLevel;
        }
        return newTotalQuantityAfterStockLimit - cartLevel;
    }


    @Override
    protected void mergeEntry(@Nonnull final CommerceCartModification modification, @Nonnull final CommerceCartParameter parameter)
            throws CommerceCartModificationException
    {
        ServicesUtil.validateParameterNotNullStandardMessage("modification", modification);
        if (modification.getEntry() == null || Objects.equals(modification.getEntry().getQuantity(), Long.valueOf(0L)))
        {
            // nothing to merge
            return;
        }
        ServicesUtil.validateParameterNotNullStandardMessage("parameter", parameter);
        if (parameter.isCreateNewEntry())
        {
            return;
        }
        final AbstractOrderModel cart = modification.getEntry().getOrder();
        if (cart == null)
        {
            // The entry is not in cart (most likely it's a stub)
            return;
        }
        final AbstractOrderEntryModel mergeTarget = getEntryMergeStrategy().getEntryToMerge(cart.getEntries(),
                modification.getEntry());
        if (mergeTarget == null)
        {
            if (parameter.getEntryNumber() != CommerceCartParameter.DEFAULT_ENTRY_NUMBER)
            {
                throw new CommerceCartModificationException("The new entry can not be merged into the entry #"
                        + parameter.getEntryNumber() + ". Give a correct value or " + CommerceCartParameter.DEFAULT_ENTRY_NUMBER
                        + " to accept any suitable entry.");
            }
        }
        else
        {
            // Merge the original entry into the merge target and remove the original entry.
            final Map<Integer, Long> entryQuantities = new HashMap<>(2);
            entryQuantities.put(mergeTarget.getEntryNumber(),
                    Long.valueOf(modification.getEntry().getQuantity().longValue() + mergeTarget.getQuantity().longValue()));
            entryQuantities.put(modification.getEntry().getEntryNumber(), Long.valueOf(0L));
            getCartService().updateQuantities(parameter.getCart(), entryQuantities);
            modification.setEntry(mergeTarget);
        }

    }

    @Required
    public void setWarehouseService(WarehouseService warehouseService) {
        this.warehouseService = warehouseService;
    }

}
