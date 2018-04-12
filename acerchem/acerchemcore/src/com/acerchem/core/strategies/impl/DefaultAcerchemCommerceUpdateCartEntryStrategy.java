package com.acerchem.core.strategies.impl;

import com.acerchem.core.strategies.AcerchemCommerceUpdateCartEntryStrategy;
import de.hybris.platform.basecommerce.enums.InStockStatus;
import de.hybris.platform.commerceservices.order.CommerceCartModification;
import de.hybris.platform.commerceservices.order.CommerceCartModificationException;
import de.hybris.platform.commerceservices.order.CommerceCartModificationStatus;
import de.hybris.platform.commerceservices.order.impl.DefaultCommerceUpdateCartEntryStrategy;
import de.hybris.platform.commerceservices.service.data.CommerceCartParameter;
import de.hybris.platform.core.model.order.AbstractOrderEntryModel;
import de.hybris.platform.core.model.order.CartModel;
import de.hybris.platform.ordersplitting.model.StockLevelModel;
import de.hybris.platform.storelocator.model.PointOfServiceModel;

import static de.hybris.platform.servicelayer.util.ServicesUtil.validateParameterNotNull;

/**
 * Created by Jacob.Ji on 2018/3/23.
 */
public class DefaultAcerchemCommerceUpdateCartEntryStrategy extends DefaultCommerceUpdateCartEntryStrategy implements AcerchemCommerceUpdateCartEntryStrategy {

    @Override
    public CommerceCartModification updatePointOfServiceForCartEntry(final CommerceCartParameter parameters)
            throws CommerceCartModificationException
    {
        final CartModel cartModel = parameters.getCart();
        final PointOfServiceModel pointOfServiceModel = parameters.getPointOfService();
        validateParameterNotNull(cartModel, "Cart model cannot be null");
        validateParameterNotNull(pointOfServiceModel, "PointOfService Model cannot be null");

        final AbstractOrderEntryModel entryToUpdate = getEntryForNumber(cartModel, (int) parameters.getEntryNumber());

        if (entryToUpdate == null)
        {
            throw new CommerceCartModificationException("Unknown entry number");
        }

        if (!isOrderEntryUpdatable(entryToUpdate))
        {
            throw new CommerceCartModificationException("Entry is not updatable");
        }

        final AbstractOrderEntryModel mergeTarget = getEntryMergeStrategy().getEntryToMerge(cartModel.getEntries(), entryToUpdate);
        if (mergeTarget != null)
        {
            return mergeEntries(entryToUpdate, mergeTarget, cartModel);
        }
        else
        {
            final CommerceCartModification modification = new CommerceCartModification();
            final boolean isUseFutureStock = parameters.getIsUseFutureStock();
            long stockLevel = getAvailableStockLevel(entryToUpdate.getProduct(), parameters.getPointOfService());
            if (isUseFutureStock){
                for (StockLevelModel stockLevelModel : entryToUpdate.getProduct().getStockLevels())
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
            }
            if (stockLevel < entryToUpdate.getQuantity().longValue())
            {
                entryToUpdate.setQuantity(Long.valueOf(stockLevel));
                modification.setQuantity(stockLevel);
                modification.setStatusCode(CommerceCartModificationStatus.LOW_STOCK);
            }
            else
            {
                modification.setStatusCode(CommerceCartModificationStatus.SUCCESS);
            }
            entryToUpdate.setDeliveryPointOfService(pointOfServiceModel);
            getModelService().save(entryToUpdate);
            getModelService().refresh(cartModel);
            getCommerceCartCalculationStrategy().calculateCart(cartModel);
            getModelService().refresh(entryToUpdate);
            modification.setEntry(entryToUpdate);
            return modification;
        }
    }

}
