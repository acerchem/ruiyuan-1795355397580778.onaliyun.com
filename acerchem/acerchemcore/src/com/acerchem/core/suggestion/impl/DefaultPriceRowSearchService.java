package com.acerchem.core.suggestion.impl;

import de.hybris.platform.europe1.model.PriceRowModel;
import de.hybris.platform.servicelayer.model.ModelService;
import com.acerchem.core.suggestion.PriceRowSearchService;
import com.acerchem.core.suggestion.dao.PriceRowSearchDao;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Alice
 */
public class DefaultPriceRowSearchService implements PriceRowSearchService
{
	private PriceRowSearchDao priceRowSearchDao;
	private ModelService modelService;

	@Override
	public void getReferencesForProductInPriceRow(final PriceRowModel newPriceRow)
	{
		List<PriceRowModel> priceRows=priceRowSearchDao.findPriceRowBySameProductToPriceRow(newPriceRow);
		
		if(newPriceRow.getStartTime()!=null)
    	{
    		Calendar c = Calendar.getInstance(); 
		    c.setTime(newPriceRow.getStartTime()); 
		    c.set(Calendar.DATE,c.get(Calendar.DATE)-1); 
		    Date date=c.getTime();
		    
		    for(PriceRowModel pr:priceRows)
		    {
		    	if(pr.getPk()!=newPriceRow.getPk()&&pr.getCurrency()==newPriceRow.getCurrency())
		    	{
			    	if(pr.getStartTime()==null)
			    	{
			    		pr.setStartTime(date);
			    	}
			    	if(pr.getEndTime()==null||pr.getEndTime().getTime()>date.getTime())
			    	{
			    		pr.setEndTime(date);
			    	}
		    		getModelService().save(pr);
		    	}
		    }
    	}
    	else
    	{
    		Calendar c = Calendar.getInstance(); 
		    c.setTime(new Date()); 
		    c.set(Calendar.DATE,c.get(Calendar.DATE)); 
		    Date date=c.getTime();
		    
    		for(PriceRowModel pr:priceRows)
		    {
		    	if(pr.getPk()!=newPriceRow.getPk()&&pr.getCurrency()==newPriceRow.getCurrency())
		    	{
		    		pr.setStartTime(date);
		    		pr.setEndTime(date);
		    		getModelService().save(pr);
		    	}
		    }
    	}
	}
	
	protected PriceRowSearchDao getPriceRowSearchDao()
	{
		return priceRowSearchDao;
	}

	public void setPriceRowSearchDao(final PriceRowSearchDao priceRowSearchDao)
	{
		this.priceRowSearchDao = priceRowSearchDao;
	}
	
	public ModelService getModelService() {
		return modelService;
	}
	public void setModelService(ModelService modelService) {
		this.modelService = modelService;
	}
}
