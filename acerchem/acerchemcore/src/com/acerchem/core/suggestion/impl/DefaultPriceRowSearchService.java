package com.acerchem.core.suggestion.impl;

import de.hybris.platform.europe1.model.PriceRowModel;
import de.hybris.platform.servicelayer.model.ModelService;
import com.acerchem.core.suggestion.PriceRowSearchService;
import com.acerchem.core.suggestion.dao.PriceRowSearchDao;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

/**
 * Alice
 */
public class DefaultPriceRowSearchService implements PriceRowSearchService
{
	private PriceRowSearchDao priceRowSearchDao;
	@Resource(name = "modelService")
	private ModelService modelService;

	@Override
	public void getReferencesForProductInPriceRow(final PriceRowModel newPriceRow)
	{
		List<PriceRowModel> priceRows=priceRowSearchDao.findPriceRowBySameProductToPriceRow(newPriceRow);
		Calendar c = Calendar.getInstance(); 
		if(newPriceRow.getStartTime()!=null)
    	{
			c.setTime(newPriceRow.getStartTime()); 
    	}
		else
		{
			c.setTime(new Date()); 
		}
	    c.set(Calendar.DATE,c.get(Calendar.DATE)-1); 
	    Date date=c.getTime();
	    
	    for(PriceRowModel pr:priceRows)
	    {
	    	Boolean falg=false;
	    	if(pr.getStartTime()==null)
	    	{
	    		pr.setStartTime(date);
	    		falg=true;
	    	}
	    	if(pr.getEndTime()==null||pr.getEndTime().getTime()>date.getTime())
	    	{
	    		pr.setEndTime(date);
	    		falg=true;
	    	}
	    	if(falg)
	    		modelService.save(pr);
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
