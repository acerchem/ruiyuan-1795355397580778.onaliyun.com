package com.acerchem.core.promotions.jalo;

import de.hybris.platform.jalo.SessionContext;
import de.hybris.platform.jalo.c2l.Currency;
import de.hybris.platform.jalo.order.AbstractOrder;
import de.hybris.platform.jalo.order.AbstractOrderEntry;
import de.hybris.platform.promotions.jalo.ProductPercentageDiscountPromotion;
import de.hybris.platform.promotions.jalo.PromotionOrderEntryAdjustAction;
import de.hybris.platform.promotions.jalo.PromotionOrderEntryConsumed;
import de.hybris.platform.promotions.jalo.PromotionResult;
import de.hybris.platform.promotions.jalo.PromotionsManager;
import de.hybris.platform.promotions.result.PromotionEvaluationContext;
import de.hybris.platform.promotions.result.PromotionOrderEntry;
import de.hybris.platform.promotions.result.PromotionOrderView;
import de.hybris.platform.promotions.util.Helper;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.rmi.dgc.VMID;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.log4j.Logger;

import com.acerchem.core.jalo.PromotionThresholdDiscount;

public class ProductThresholdPercentageDiscountPromotion extends GeneratedProductThresholdPercentageDiscountPromotion
{
	 private static final Logger LOG = Logger.getLogger(ProductPercentageDiscountPromotion.class);
	    
	 @Override
	 public List<PromotionResult> evaluate(SessionContext ctx, PromotionEvaluationContext promoContext)
	    {
	      List<PromotionResult> results = new ArrayList();
	      
	  
	      PromotionsManager.RestrictionSetResult rsr = findEligibleProductsInBasket(ctx, promoContext);
	      
	      if ((rsr.isAllowedToContinue()) && (!rsr.getAllowedProducts().isEmpty()))
	      {
	        PromotionOrderView view = promoContext.createView(ctx, this, rsr.getAllowedProducts());
	        PromotionsManager promotionsManager = PromotionsManager.getInstance();
	        
	  
	        while (view.getTotalQuantity(ctx) > 0L)
	        {
	        	promoContext.startLoggingConsumed(this);
	  
	          PromotionOrderEntry entry = view.peek(ctx);
	          BigDecimal quantityToDiscount = BigDecimal.valueOf(entry.getQuantity(ctx));
	          BigDecimal quantityOfOrderEntry = BigDecimal.valueOf(entry.getBaseOrderEntry().getQuantity(ctx).longValue());
	          
	          PromotionThresholdDiscount thresholdDiscount= getProperThreshold(entry,ctx);
	          if(thresholdDiscount==null){
	         	 //没有合适的折扣区间
	        	 promoContext.finishLoggingAndGetConsumed(this, true);
	         	 continue;
	          }
	         	 
	          BigDecimal percentageDiscount = new BigDecimal(
	         		 thresholdDiscount.getPercentageDiscount().toString())
	            .divide(new BigDecimal("100.0"));
	          
	  
	          BigDecimal originalUnitPrice = new BigDecimal(entry.getBasePrice(ctx).toString());
	          BigDecimal originalEntryPrice = originalUnitPrice.multiply(quantityToDiscount);
	          
	          Currency currency = promoContext.getOrder().getCurrency(ctx);
	          
	  
	          BigDecimal adjustedEntryPrice = Helper.roundCurrencyValue(ctx, currency, 
	            originalEntryPrice.subtract(originalEntryPrice.multiply(percentageDiscount)));
	          
	  
	          BigDecimal adjustedUnitPrice = Helper.roundCurrencyValue(ctx, currency, 
	            adjustedEntryPrice.equals(BigDecimal.ZERO) ? BigDecimal.ZERO : 
	            adjustedEntryPrice.divide(quantityToDiscount, RoundingMode.HALF_EVEN));
	          
	  
	          BigDecimal fiddleAmount = adjustedEntryPrice.subtract(adjustedUnitPrice.multiply(quantityToDiscount));
	          
	  
	          if (fiddleAmount.compareTo(BigDecimal.ZERO) == 0)
	          {
	            for (PromotionOrderEntryConsumed poec : view.consume(ctx, quantityToDiscount.longValue()))
	            {
	              poec.setAdjustedUnitPrice(ctx, adjustedUnitPrice.doubleValue());
	            }
	            
	  
	          }
	          else
	          {
	  
	            for (PromotionOrderEntryConsumed poec : view.consume(ctx, quantityToDiscount.longValue() - 1L))
	            {
	              poec.setAdjustedUnitPrice(ctx, adjustedUnitPrice.doubleValue());
	            }
	            
	  
	            for (PromotionOrderEntryConsumed poec : view.consume(ctx, 1L))
	            {
	              poec.setAdjustedUnitPrice(ctx, 
	                Helper.roundCurrencyValue(ctx, currency, adjustedUnitPrice.add(fiddleAmount)).doubleValue());
	            }
	          }
	          
	          PromotionResult result = promotionsManager.createPromotionResult(ctx, this, promoContext.getOrder(), 1.0F);
	          result.setConsumedEntries(ctx, promoContext.finishLoggingAndGetConsumed(this, true));
	          BigDecimal adjustment = Helper.roundCurrencyValue(ctx, currency, 
	            adjustedEntryPrice.subtract(originalEntryPrice));
	          PromotionOrderEntryAdjustAction poeac = createPromotionOrderEntryAdjustAction(promotionsManager,ctx, 
	            entry.getBaseOrderEntry(), quantityOfOrderEntry.longValue(), adjustment.doubleValue(),thresholdDiscount);
	          result.addAction(ctx, poeac);
	          
	          results.add(result);
	        }
	        
	  
	        return results;
	      }
	      
	      return results;
	    }
	    
	    protected static String makeActionGUID()
	    {
	      return "Action[" + new VMID().toString() + "]";
	    }
	    
	    protected PromotionOrderEntryAdjustAction createPromotionOrderEntryAdjustAction(PromotionsManager promotionsManager,
	   		 SessionContext ctx, AbstractOrderEntry entry, long quantity, double adjustment,PromotionThresholdDiscount thresholdDiscount)
	    {
	      Map parameters = new HashMap();
	      parameters.put("guid", makeActionGUID());
	      parameters.put("amount", Double.valueOf(adjustment));
	      parameters.put("orderEntryProduct", entry.getProduct(ctx));
	      parameters.put("orderEntryNumber", entry.getEntryNumber());
	      parameters.put("orderEntryQuantity", Long.valueOf(quantity));
	      parameters.put("thresholdDiscount", thresholdDiscount);
	      return promotionsManager.createPromotionOrderEntryAdjustAction(ctx, parameters);
	    }
	 
	    protected PromotionThresholdDiscount getProperThreshold(PromotionOrderEntry entry,SessionContext ctx){
	   	 List<PromotionThresholdDiscount> list=  getThresholdDiscounts(ctx);
	   	 if(list!=null){
	   		for(PromotionThresholdDiscount thresholdDiscount:list){
	   			int min=thresholdDiscount.getMinQuantity()!=null?thresholdDiscount.getMinQuantity().intValue():0;
	   			int max=thresholdDiscount.getMaxQuantity()!=null?thresholdDiscount.getMaxQuantity().intValue():Integer.MAX_VALUE;
	   			long quantity=entry.getQuantity(ctx);
	   			if(quantity>min && quantity<=max && thresholdDiscount.getPercentageDiscount()!=null){
	   				return thresholdDiscount;
	   			}
	   		}
	   	 }
	   	 return null;
	    }
	 
	    @Override
	    public String getResultDescription(SessionContext ctx, PromotionResult promotionResult, Locale locale)
	    {
	      AbstractOrder order = promotionResult.getOrder(ctx);
	      if (order != null)
	      {
	        Currency orderCurrency = order.getCurrency(ctx);
	        
	        if (promotionResult.getFired(ctx))
	        {
	          
	          Iterator itr=promotionResult.getActions(ctx).iterator();
	          if(itr.hasNext()){
	         	 try{
	         	     PromotionOrderEntryAdjustAction action=(PromotionOrderEntryAdjustAction)itr.next();
	         	     Double totalDiscount = Double.valueOf(action.getValue(ctx));
	         	     PromotionThresholdDiscount thresholdDiscount=(PromotionThresholdDiscount)action.getProperty(ctx, "thresholdDiscount");
		          
	         	     Double percentageDiscount =thresholdDiscount.getPercentageDiscount();
		              Object[] args = { percentageDiscount, totalDiscount, 
		                         Helper.formatCurrencyAmount(ctx, locale, orderCurrency, totalDiscount.doubleValue()) };
		              return formatMessage(getMessageFired(ctx), args, locale);
	             
	             }catch(Exception e){
	         	   LOG.error(e);
	             }	         
	        }
	       }
	      }  
	      return "";
	    }
	    
//	    @Override
//	    protected void buildDataUniqueKey(SessionContext ctx, StringBuilder builder)
//	    {
//	      super.buildDataUniqueKey(ctx, builder);
//	      builder.append(getThresholdDiscounts(ctx)).append('|');
//	    }
	
}
