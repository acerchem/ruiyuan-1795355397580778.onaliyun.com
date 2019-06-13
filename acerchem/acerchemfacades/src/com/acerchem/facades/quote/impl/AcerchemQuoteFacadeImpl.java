package com.acerchem.facades.quote.impl;

import com.acerchem.facades.quote.AcerchemQuoteFacade;
import de.hybris.platform.commercefacades.order.impl.DefaultQuoteFacade;
import de.hybris.platform.commercefacades.quote.data.QuoteData;
import de.hybris.platform.commerceservices.enums.QuoteAction;
import de.hybris.platform.commerceservices.enums.QuoteUserType;
import de.hybris.platform.commerceservices.event.QuoteBuyerSubmitEvent;
import de.hybris.platform.commerceservices.order.strategies.QuoteActionValidationStrategy;
import de.hybris.platform.commerceservices.order.strategies.QuoteMetadataValidationStrategy;
import de.hybris.platform.commerceservices.order.strategies.QuoteUpdateExpirationTimeStrategy;
import de.hybris.platform.commerceservices.order.strategies.QuoteUpdateStateStrategy;
import de.hybris.platform.core.model.order.AbstractOrderEntryModel;
import de.hybris.platform.core.model.order.CartModel;
import de.hybris.platform.core.model.order.QuoteEntryModel;
import de.hybris.platform.core.model.order.QuoteModel;
import de.hybris.platform.core.model.user.CustomerModel;
import de.hybris.platform.core.model.user.UserModel;
import de.hybris.platform.servicelayer.event.EventService;
import org.apache.commons.collections4.CollectionUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;


public class AcerchemQuoteFacadeImpl extends DefaultQuoteFacade implements AcerchemQuoteFacade
{
	@Resource
	private QuoteActionValidationStrategy quoteActionValidationStrategy;

	@Resource
	private QuoteUpdateExpirationTimeStrategy quoteUpdateExpirationTimeStrategy;

	@Resource
	private QuoteMetadataValidationStrategy quoteMetadataValidationStrategy;

	@Resource
	private QuoteUpdateStateStrategy quoteUpdateStateStrategy;

	@Resource
	private EventService eventService;


	@Override
	public void directSendQuote()
	{
		final CartModel cartModel = getCartService().getSessionCart();
		final QuoteModel quoteModel = getCommerceQuoteService().createQuoteFromCart(cartModel,
				getQuoteUserIdentificationStrategy().getCurrentQuoteUser());
		final UserModel userModel = getQuoteUserIdentificationStrategy().getCurrentQuoteUser();
		getCommerceQuoteService().unassignQuote(quoteModel, userModel);

		quoteActionValidationStrategy.validate(QuoteAction.SUBMIT, quoteModel, userModel);
		quoteMetadataValidationStrategy.validate(QuoteAction.SUBMIT, quoteModel, userModel);
		quoteUpdateStateStrategy.updateQuoteState(QuoteAction.SUBMIT, quoteModel, userModel);
		assignQuoteToentrys(quoteModel);
		if(cartModel.getPickUpDate()!=null) quoteModel.setWaitDeliveriedDate(cartModel.getPickUpDate());
		getModelService().save(quoteModel);

		final QuoteBuyerSubmitEvent quoteBuyerSubmitEvent = new QuoteBuyerSubmitEvent(quoteModel, userModel,
				QuoteUserType.BUYER);
		eventService.publishEvent(quoteBuyerSubmitEvent);
	}

	protected void assignQuoteToentrys(QuoteModel quoteModel){
		if(CollectionUtils.isNotEmpty(quoteModel.getEntries()))
		{
			List<AbstractOrderEntryModel> entryModels = new ArrayList<>(quoteModel.getEntries());
			entryModels = entryModels.stream().filter(entry -> entry instanceof QuoteEntryModel)
					.map(entry -> {
						QuoteEntryModel quoteEntry = (QuoteEntryModel)entry;
						//quoteEntry.setCreationtime(new Date());
						quoteEntry.setUser(quoteModel.getUser());
						if(quoteModel.getUser() instanceof CustomerModel)
						{
							quoteEntry.setSalesman(((CustomerModel)quoteModel.getUser()).getEmployee());
						}else{
							quoteEntry.setSalesman(quoteModel.getUser());
						}
						return quoteEntry;
					}).collect(Collectors.toList());
			getModelService().saveAll(entryModels);
			quoteModel.setEntries(entryModels);
		}
	}
}
