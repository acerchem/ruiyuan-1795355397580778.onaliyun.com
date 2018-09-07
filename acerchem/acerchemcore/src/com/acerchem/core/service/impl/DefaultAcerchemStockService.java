package com.acerchem.core.service.impl;

import static de.hybris.platform.servicelayer.util.ServicesUtil.validateParameterNotNull;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.acerchem.core.service.AcerchemStockService;

import de.hybris.platform.catalog.enums.ProductInfoStatus;
import de.hybris.platform.commercefacades.order.data.CartModificationData;
import de.hybris.platform.commerceservices.constants.CommerceServicesConstants;
import de.hybris.platform.commerceservices.enums.CustomerType;
import de.hybris.platform.commerceservices.order.CommerceCartCalculationStrategy;
import de.hybris.platform.commerceservices.order.CommerceCartModification;
import de.hybris.platform.commerceservices.order.CommerceCartModificationStatus;
import de.hybris.platform.commerceservices.order.CommerceCartService;
import de.hybris.platform.commerceservices.service.data.CommerceCartParameter;
import de.hybris.platform.commerceservices.stock.CommerceStockService;
import de.hybris.platform.commerceservices.strategies.CartValidationStrategy;
import de.hybris.platform.commerceservices.strategies.hooks.CartValidationHook;
import de.hybris.platform.converters.Converters;
import de.hybris.platform.core.model.ItemModel;
import de.hybris.platform.core.model.order.AbstractOrderEntryModel;
import de.hybris.platform.core.model.order.AbstractOrderModel;
import de.hybris.platform.core.model.order.CartEntryModel;
import de.hybris.platform.core.model.order.CartModel;
import de.hybris.platform.core.model.product.ProductModel;
import de.hybris.platform.core.model.user.CustomerModel;
import de.hybris.platform.order.CartService;
import de.hybris.platform.order.model.AbstractOrderEntryProductInfoModel;
import de.hybris.platform.ordersplitting.model.StockLevelModel;
import de.hybris.platform.ordersplitting.model.WarehouseModel;
import de.hybris.platform.servicelayer.config.ConfigurationService;
import de.hybris.platform.servicelayer.dto.converter.Converter;
import de.hybris.platform.servicelayer.model.ModelService;
import de.hybris.platform.servicelayer.search.FlexibleSearchQuery;
import de.hybris.platform.servicelayer.search.FlexibleSearchService;
import de.hybris.platform.servicelayer.search.SearchResult;
import de.hybris.platform.servicelayer.user.UserService;
import de.hybris.platform.stock.exception.InsufficientStockLevelException;
import de.hybris.platform.stock.impl.DefaultStockService;
import de.hybris.platform.store.services.BaseStoreService;

public class DefaultAcerchemStockService extends DefaultStockService implements AcerchemStockService {

    @Override
    public void calculateStock(ProductModel product, WarehouseModel warehouse, int quantity, String comment, boolean isUseFutureStock) throws InsufficientStockLevelException {
        if (isUseFutureStock){
            if(quantity <= 0) {
                throw new IllegalArgumentException("amount must be greater than zero.");
            } else {
                StockLevelModel currentStockLevel = super.getStockLevel(product,warehouse);
                int preOrder = currentStockLevel.getPreOrder();
                if(preOrder == 0 || preOrder < quantity) {
                    throw new InsufficientStockLevelException("insufficient available amount for stock level [" + currentStockLevel.getPk() + "]");
                } else {
                    int newPreOrder = preOrder - quantity;
                    currentStockLevel.setPreOrder(newPreOrder);
                    getModelService().save(currentStockLevel);
                }
            }
        }else {
            super.reserve(product,warehouse,quantity,comment);
        }
    }

    @Override
    public void releaseStock(AbstractOrderModel abstractOrderModel) {
        if(abstractOrderModel!=null){
            for (AbstractOrderEntryModel aoe : abstractOrderModel.getEntries()) {
                boolean isUseFutureStock = aoe.getIsUseFutureStock();
                ProductModel product = aoe.getProduct();
                int quantity = aoe.getQuantity().intValue();
                WarehouseModel warehouse = aoe.getDeliveryPointOfService().getWarehouses().get(0);
                if (isUseFutureStock) {
                    //远期库存
                    StockLevelModel currentStockLevel = super.getStockLevel(product,warehouse);
                    int preOrder = currentStockLevel.getPreOrder();
                    int newPreOrder = preOrder + quantity;
                    currentStockLevel.setPreOrder(newPreOrder);
                    getModelService().save(currentStockLevel);
                } else {
                    //近期库存
                    super.release(product,warehouse,quantity,null);
                }
            }
        }
    }

	
	
	private static final Logger LOGGER = Logger.getLogger(DefaultAcerchemStockService.class);
	@Resource
	private CartService cartService;
	@Resource
	private CommerceCartService commerceCartService;
	@Resource
	private CartValidationStrategy cartValidationStrategy;
	@Resource
	private CommerceCartCalculationStrategy commerceCartCalculationStrategy;
	@Resource
	private List<CartValidationHook> cartValidationHooks;
	@Resource
	private ConfigurationService configurationService;
	@Resource
	private ModelService modelService;
	@Resource
	private Converter<CommerceCartModification, CartModificationData> cartModificationConverter;
	@Resource
	private UserService userService;
	
	@Override
	public boolean validateCart(RedirectAttributes redirectModel) {
		//Validate the cart
		List<CartModificationData> modifications = new ArrayList<>();
		try
		{
			if (cartService.hasSessionCart())
			{
				final CommerceCartParameter parameter = new CommerceCartParameter();
				parameter.setEnableHooks(true);
				parameter.setCart(cartService.getSessionCart());
				final CartModel cartModel = parameter.getCart();
				validateParameterNotNull(cartModel, "Cart model cannot be null");
				
				final List<CommerceCartModification> modifications1 = new ArrayList<CommerceCartModification>();
				final boolean callHooks = cartValidationHooks != null
						&& (parameter.isEnableHooks() && configurationService.getConfiguration().getBoolean(
								CommerceServicesConstants.CARTVALIDATIONHOOK_ENABLED, true));
				if (callHooks)
				{
					if (cartValidationHooks != null)
					{
						cartValidationHooks.stream().forEach(hook -> hook.beforeValidateCart(parameter, modifications1));
					}
				}

				final ItemModel customer = cartModel.getUser();
				Boolean isGuestUserCart = customer instanceof CustomerModel && CustomerType.GUEST.equals(((CustomerModel) customer).getType());
				
				//clean cart
				if (cartModel.getPaymentInfo() != null)
				{
					if (!isGuestUserCart && !userService.getCurrentUser().equals(cartModel.getPaymentInfo().getUser()))
					{
						cartModel.setPaymentInfo(null);
						modelService.save(cartModel);
					}
				}
				if (cartModel.getDeliveryAddress() != null)
				{
					if (!isGuestUserCart && !userService.getCurrentUser().equals(cartModel.getDeliveryAddress().getOwner()))
					{
						cartModel.setDeliveryAddress(null);
						modelService.save(cartModel);
					}
				}

				if (cartModel != null && cartModel.getEntries() != null && !cartModel.getEntries().isEmpty())
				{
					for (final AbstractOrderEntryModel orderEntryModel : cartModel.getEntries())
					{
						modifications1.add(validateCartEntry(cartModel, (CartEntryModel) orderEntryModel));
					}
				}
				if (callHooks)
				{
					if (cartValidationHooks != null)
					{
						cartValidationHooks.stream().forEach(hook -> hook.afterValidateCart(parameter, modifications1));
					}
				}

				// We only care about modifications that weren't successful
				final List<CommerceCartModification> errorModifications = new ArrayList<CommerceCartModification>(modifications1.size());
				for (final CommerceCartModification modification : modifications1)
				{
					if (!CommerceCartModificationStatus.SUCCESS.equals(modification.getStatusCode()))
					{
						errorModifications.add(modification);
					}
				}
				
				validateParameterNotNull(parameter.getCart(), "Cart model cannot be null");
				commerceCartCalculationStrategy.calculateCart(parameter);
				modifications = Converters.convertAll(errorModifications, cartModificationConverter);
			}
			
		}
		catch (final Exception e)
		{
			LOGGER.error("Failed to validate cart", e);
		}
		if (!modifications.isEmpty())
		{
			redirectModel.addFlashAttribute("validationData", modifications);
			// Invalid cart. Bounce back to the cart page.
			return true;
		}
		return false;
	}
	
	@Resource
	private CommerceStockService commerceStockService;
	@Resource
	private BaseStoreService baseStoreService;
	@Resource
    private FlexibleSearchService flexibleSearchService;
	
	protected CommerceCartModification validateCartEntry(final CartModel cartModel, final CartEntryModel cartEntryModel)
	{
		final ProductModel product = cartEntryModel.getProduct();
		
		final String SQL = "SELECT {s.PK} FROM {StockLevel as s JOIN Warehouse as w ON {s.warehouse}={w.pk} } "
				+ "WHERE {s.productCode} = \'"+product.getCode()+"\' AND {w.code} = \'"+cartEntryModel.getDeliveryPointOfService().getWarehouses().get(0).getCode()+"\'";
		final FlexibleSearchQuery query = new FlexibleSearchQuery(SQL);
		query.setNeedTotal(false);
		query.setCount(1);
        final SearchResult<StockLevelModel> result = flexibleSearchService.search(query);
        
        
		
		// Overall availability of this product
		Long stockLevel=0L;//库存数量
		if(result.getResult()!=null)
		{
			for (StockLevelModel stockLevelModel : result.getResult())
	        {
				if(cartEntryModel.getIsUseFutureStock())
				{//远期订单
					stockLevel = (long) stockLevelModel.getPreOrder();
				}
				else
				{//现货订单
					stockLevel = (long) stockLevelModel.getAvailable();
				}
	        }
		}

		// Overall stock quantity in the cart
		long cartLevel = 0;
		for (final CartEntryModel entryModel : cartService.getEntriesForProduct(cartModel, product))
		{
			cartLevel += entryModel.getQuantity() != null ? entryModel.getQuantity().longValue() : 0;
		}

		// Stock quantity for this cartEntry
		final long cartEntryLevel = cartEntryModel.getQuantity().longValue();

		// New stock quantity for this cartEntry
		final long newOrderEntryLevel;

		Long stockLevelForProductInBaseStore = null;

		// this product is not available at the given point of service.
		if (stockLevel.longValue() <= 0 && cartEntryModel.getDeliveryPointOfService() != null)
		{
			//库存数量（待修改）
			stockLevelForProductInBaseStore = commerceStockService.getStockLevelForProductAndBaseStore(
					cartEntryModel.getProduct(), baseStoreService.getCurrentBaseStore());

			if (stockLevelForProductInBaseStore != null)
			{
				newOrderEntryLevel = Math.min(cartEntryLevel, stockLevelForProductInBaseStore.longValue());
			}
			else
			{
				newOrderEntryLevel = Math.min(cartEntryLevel, cartLevel);
			}
		}
		else
		{
			// if stock is available.. get either requested quantity if its lower than available stock or maximum stock.
			newOrderEntryLevel = Math.min(cartEntryLevel, stockLevel.longValue());
		}

		// this product is not available at the given point of service.
		if (stockLevelForProductInBaseStore != null && stockLevelForProductInBaseStore.longValue() != 0)
		{
			final CommerceCartModification modification = new CommerceCartModification();
			modification.setStatusCode(CommerceCartModificationStatus.MOVED_FROM_POS_TO_STORE);
			
			CartEntryModel existingEntryForProduct = null;
				for (final CartEntryModel entryModel : cartService.getEntriesForProduct(cartModel, product))
				{
					if (entryModel.getDeliveryPointOfService() == null)
					{
						existingEntryForProduct = entryModel;
					}
				}
			if (existingEntryForProduct != null)
			{
				modelService.remove(cartEntryModel);
				final long quantityAdded = stockLevelForProductInBaseStore.longValue() >= cartLevel ? newOrderEntryLevel : cartLevel
						- stockLevelForProductInBaseStore.longValue();
				modification.setQuantityAdded(quantityAdded);
				final long updatedQuantity = (stockLevelForProductInBaseStore.longValue() <= cartLevel ? stockLevelForProductInBaseStore
						.longValue() : cartLevel);
				modification.setQuantity(updatedQuantity);
				existingEntryForProduct.setQuantity(Long.valueOf(updatedQuantity));
				modelService.save(existingEntryForProduct);
				modification.setEntry(existingEntryForProduct);
			}
			else
			{
				modification.setQuantityAdded(newOrderEntryLevel);
				modification.setQuantity(cartEntryLevel);
				cartEntryModel.setDeliveryPointOfService(null);
				modification.setEntry(cartEntryModel);
				modelService.save(cartEntryModel);
			}

			modelService.refresh(cartModel);

			return modification;
		}
		else if ((stockLevel != null && stockLevel.longValue() <= 0) || newOrderEntryLevel < 0)
		{
			// If no stock is available or the cart is full for this product, remove the entry from the cart
			final CommerceCartModification modification = new CommerceCartModification();
			modification.setStatusCode(CommerceCartModificationStatus.NO_STOCK);
			modification.setQuantityAdded(0);//New quantity for this entry
			modification.setQuantity(cartEntryLevel);//Old quantity for this entry
			final CartEntryModel entry = new CartEntryModel()
			{
				@Override
				public Double getBasePrice()
				{
					return null;
				}

				@Override
				public Double getTotalPrice()
				{
					return null;
				}
			};
			entry.setProduct(cartEntryModel.getProduct());
			modification.setEntry(entry);
			modelService.remove(cartEntryModel);
			modelService.refresh(cartModel);

			return modification;
		}
		else if (cartEntryLevel != newOrderEntryLevel)
		{
			// If the orderLevel has changed for this cartEntry, then recalculate the quantity
			final CommerceCartModification modification = new CommerceCartModification();
			modification.setStatusCode(CommerceCartModificationStatus.LOW_STOCK);
			modification.setQuantityAdded(newOrderEntryLevel);
			modification.setQuantity(cartEntryLevel);
			modification.setEntry(cartEntryModel);
			cartEntryModel.setQuantity(Long.valueOf(newOrderEntryLevel));
			modelService.save(cartEntryModel);
			modelService.refresh(cartModel);

			return modification;
		}
		else if (cartEntryModel.getProductInfos().stream()
				.filter(item -> item != null)
				.map(AbstractOrderEntryProductInfoModel::getProductInfoStatus)
				.filter(item -> ProductInfoStatus.ERROR.equals(item))
				.findAny()
				.isPresent())
		{
			final CommerceCartModification modification = new CommerceCartModification();
			modification.setStatusCode(CommerceCartModificationStatus.CONFIGURATION_ERROR);
			modification.setQuantityAdded(cartEntryLevel);
			modification.setQuantity(cartEntryLevel);
			modification.setEntry(cartEntryModel);

			return modification;
		}
		else
		{
			final CommerceCartModification modification = new CommerceCartModification();
			modification.setStatusCode(CommerceCartModificationStatus.SUCCESS);
			modification.setQuantityAdded(cartEntryLevel);
			modification.setQuantity(cartEntryLevel);
			modification.setEntry(cartEntryModel);

			return modification;
		}
	}
}
