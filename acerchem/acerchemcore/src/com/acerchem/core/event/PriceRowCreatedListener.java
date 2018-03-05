package com.acerchem.core.event;

import javax.annotation.Resource;
import org.springframework.stereotype.Component;
import com.acerchem.core.suggestion.PriceRowSearchService;
import de.hybris.platform.catalog.CatalogVersionService;
import de.hybris.platform.core.GenericCondition;
import de.hybris.platform.core.GenericQuery;
import de.hybris.platform.core.GenericSearchField;
import de.hybris.platform.core.Operator;
import de.hybris.platform.europe1.model.PriceRowModel;
import de.hybris.platform.genericsearch.GenericSearchService;
import de.hybris.platform.servicelayer.event.events.AfterItemCreationEvent;
import de.hybris.platform.servicelayer.event.impl.AbstractEventListener;
import de.hybris.platform.servicelayer.search.SearchResult;
import de.hybris.platform.servicelayer.session.SessionService;

/**
 * @author Alice
 */
@Component
public class PriceRowCreatedListener extends AbstractEventListener<AfterItemCreationEvent>
{
	@Resource
	GenericSearchService genericSearchService;
	@Resource
	PriceRowSearchService priceRowSearchService;//flexibleSearchService;
	
	@Resource
	private SessionService sessionService;
	@Resource
	private CatalogVersionService catalogVersionService;
 /*
  * (non-Javadoc)
  * 
  * @see
  * de.hybris.platform.servicelayer.event.impl.AbstractEventListener#onEvent(de.hybris.platform.servicelayer.event
  * .events.AbstractEvent)
  */
 @Override
 protected void onEvent(final AfterItemCreationEvent event)
 {
	 try
	 {
		  if(PriceRowModel._TYPECODE.equals(event.getTypeCode()))
		  {
			  	sessionService.getCurrentSession();
			  	catalogVersionService.setSessionCatalogVersions(catalogVersionService.getAllCatalogVersions());
			  	
			   final GenericQuery genericQuery = new GenericQuery(PriceRowModel._TYPECODE);
			   genericQuery.addCondition(GenericCondition.createConditionForValueComparison(new GenericSearchField(PriceRowModel.PK),Operator.EQUAL, event.getSource()));
			   final SearchResult<PriceRowModel> searchResult = genericSearchService.<PriceRowModel> search(genericQuery);
			   if (searchResult.getCount() > 0)
			   {
				    final PriceRowModel newPriceRow = searchResult.getResult().get(0);
				    if (newPriceRow.getProduct()!=null)
					{
				    	priceRowSearchService.getReferencesForProductInPriceRow(newPriceRow);
					}
			   }
		  }
	   }
	   catch(Exception e)
	   {
		   System.out.println(e.toString());
	   }
} 
}
