package com.acerchem.core.util;

import com.acerchem.core.dao.AcerchemBuPriceDao;
import com.acerchem.core.enums.BUType;
import com.acerchem.core.model.BUPriceAdditionalConfModel;
import de.hybris.platform.catalog.jalo.CatalogAwareEurope1PriceFactory;
import de.hybris.platform.core.model.user.CustomerModel;
import de.hybris.platform.core.model.user.UserModel;
import de.hybris.platform.europe1.channel.strategies.RetrieveChannelStrategy;
import de.hybris.platform.europe1.constants.Europe1Tools;
import de.hybris.platform.europe1.enums.PriceRowChannel;
import de.hybris.platform.europe1.jalo.PriceRow;
import de.hybris.platform.europe1.model.PriceRowModel;
import de.hybris.platform.jalo.SessionContext;
import de.hybris.platform.jalo.c2l.Currency;
import de.hybris.platform.jalo.enumeration.EnumerationValue;
import de.hybris.platform.jalo.order.AbstractOrder;
import de.hybris.platform.jalo.order.AbstractOrderEntry;
import de.hybris.platform.jalo.order.price.JaloPriceFactoryException;
import de.hybris.platform.jalo.order.price.PriceInformation;
import de.hybris.platform.jalo.product.Product;
import de.hybris.platform.jalo.product.Unit;
import de.hybris.platform.jalo.user.User;
import de.hybris.platform.servicelayer.model.ModelService;
import de.hybris.platform.servicelayer.user.UserService;
import de.hybris.platform.util.PriceValue;
import de.hybris.platform.util.localization.Localization;
import org.apache.commons.lang.StringUtils;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;


public class AcerchemCatalogAwareEurope1PriceFactory extends CatalogAwareEurope1PriceFactory
{
	private RetrieveChannelStrategy retrieveChannelStrategy;

	@Resource
	public ModelService modelService;

	@Resource
	public AcerchemBuPriceDao acerchemBuPriceDao;

	@Resource
	private UserService userService;

	public void setRetrieveChannelStrategy(RetrieveChannelStrategy retrieveChannelStrategy) {
		this.retrieveChannelStrategy = retrieveChannelStrategy;
		super.setRetrieveChannelStrategy(retrieveChannelStrategy);
	}

	public PriceValue getBasePrice(AbstractOrderEntry entry) throws JaloPriceFactoryException {
		SessionContext ctx = this.getSession().getSessionContext();
		AbstractOrder order = entry.getOrder(ctx);
		Currency currency = null;
		EnumerationValue productGroup = null;
		User user = null;
		EnumerationValue userGroup = null;
		Unit unit = null;
		long quantity = 0L;
		boolean net = false;
		Date date = null;
		Product product = entry.getProduct();
		boolean giveAwayMode = entry.isGiveAway(ctx);
		boolean entryIsRejected = entry.isRejected(ctx);
		PriceRow row;
		if (giveAwayMode && entryIsRejected) {
			row = null;
		} else {
			row = this.matchPriceRowForPrice(ctx, product, productGroup = this.getPPG(ctx, product), user = order.getUser(), userGroup = this.getUPG(ctx, user), quantity = entry.getQuantity(ctx), unit = entry.getUnit(ctx), currency = order.getCurrency(ctx), date = order.getDate(ctx), net = order.isNet(), giveAwayMode);
		}

		if (row != null) {
			Currency rowCurr = row.getCurrency();
			double price;
			if (currency.equals(rowCurr)) {
				price = row.getPriceAsPrimitive() / (double)row.getUnitFactorAsPrimitive();
			} else {
				price = rowCurr.convert(currency, row.getPriceAsPrimitive() / (double)row.getUnitFactorAsPrimitive());
			}

			Unit priceUnit = row.getUnit();
			Unit entryUnit = entry.getUnit();

			UserModel userModel = modelService.get(user);
			BUType buType = BUType.UK;
			if(userModel!=null && userModel instanceof CustomerModel){
				if(((CustomerModel) userModel).getBuType()!=null)
				{
					buType = ((CustomerModel) userModel).getBuType();
				}
			}

			PriceRowModel priceRowModel = null;
			if(row.getPK()!=null){
				priceRowModel = modelService.get(row.getPK());
			}
			if(priceRowModel!=null && BUType.UK.getCode().equals(buType.getCode())){
				price = Double.valueOf(priceRowModel.getUKCostPrice());
			}
			if(priceRowModel!=null && BUType.US.getCode().equals(buType.getCode())){
				price = Double.valueOf(priceRowModel.getUSCostPrice());
			}
			if(priceRowModel!=null && BUType.EU.getCode().equals(buType.getCode())){
				price = Double.valueOf(priceRowModel.getEUCostPrice());
			}
			if(priceRowModel!=null && BUType.AP.getCode().equals(buType.getCode())){
				price = Double.valueOf(priceRowModel.getAPCostPrice());
			}

			price = addBuAdditionalPrice(price, buType);

			double convertedPrice = priceUnit.convertExact(entryUnit, price);
			return new PriceValue(currency.getIsoCode(), convertedPrice, row.isNetAsPrimitive());
		} else if (giveAwayMode) {
			return new PriceValue(order.getCurrency(ctx).getIsoCode(), 0.0D, order.isNet());
		} else {
			String msg = Localization.getLocalizedString("exception.europe1pricefactory.getbaseprice.jalopricefactoryexception1", new Object[]{product, productGroup, user, userGroup, Long.toString(quantity), unit, currency, date, Boolean.toString(net)});
			throw new JaloPriceFactoryException(msg, 0);
		}
	}

	protected List getPriceInformations(SessionContext ctx, Product product, EnumerationValue productGroup, User user, EnumerationValue userGroup, Currency curr, boolean net, Date date, Collection taxValues) throws
			JaloPriceFactoryException
	{
		Collection<PriceRow> priceRows = this.filterPriceRows(this.matchPriceRowsForInfo(ctx, product, productGroup, user, userGroup, curr, date, net));
		List<PriceInformation> priceInfos = new ArrayList(priceRows.size());
		Collection theTaxValues = taxValues;
		List<PriceInformation> defaultPriceInfos = new ArrayList(priceRows.size());
		PriceRowChannel channel = this.retrieveChannelStrategy.getChannel(ctx);
		Iterator var16 = priceRows.iterator();
		UserModel userModel = modelService.get(user);
		BUType buType = BUType.UK;
		if(userModel!=null && userModel instanceof CustomerModel){
			if(((CustomerModel) userModel).getBuType()!=null)
			{
				buType = ((CustomerModel) userModel).getBuType();
			}
		}

		while(true) {
			while(var16.hasNext()) {
				PriceRow row = (PriceRow)var16.next();
				PriceInformation pInfo = Europe1Tools.createPriceInformation(row, curr);
				PriceRowModel priceRowModel = null;
				if(row.getPK()!=null){
					priceRowModel = modelService.get(row.getPK());
				}
				if(priceRowModel!=null && BUType.UK.getCode().equals(buType.getCode())){
					Double finalPrice = addBuAdditionalPrice(Double.valueOf(priceRowModel.getUKCostPrice()), buType);
					pInfo = new PriceInformation(pInfo.getQualifiers(), new PriceValue(pInfo.getPriceValue().getCurrencyIso(), finalPrice, row.isNetAsPrimitive()));
				}
				if(priceRowModel!=null && BUType.US.getCode().equals(buType.getCode())){
					Double finalPrice = addBuAdditionalPrice(Double.valueOf(priceRowModel.getUSCostPrice()), buType);
					pInfo = new PriceInformation(pInfo.getQualifiers(), new PriceValue(pInfo.getPriceValue().getCurrencyIso(), finalPrice, row.isNetAsPrimitive()));
				}
				if(priceRowModel!=null && BUType.EU.getCode().equals(buType.getCode())){
					Double finalPrice = addBuAdditionalPrice(Double.valueOf(priceRowModel.getEUCostPrice()), buType);
					pInfo = new PriceInformation(pInfo.getQualifiers(), new PriceValue(pInfo.getPriceValue().getCurrencyIso(), finalPrice, row.isNetAsPrimitive()));
				}
				if(priceRowModel!=null && BUType.AP.getCode().equals(buType.getCode())){
					Double finalPrice = addBuAdditionalPrice(Double.valueOf(priceRowModel.getAPCostPrice()), buType);
					pInfo = new PriceInformation(pInfo.getQualifiers(), new PriceValue(pInfo.getPriceValue().getCurrencyIso(), finalPrice, row.isNetAsPrimitive()));
				}

				if (pInfo.getPriceValue().isNet() != net) {
					if (theTaxValues == null) {
						theTaxValues = Europe1Tools.getTaxValues(this.getTaxInformations(product, this.getPTG(ctx, product), user, this.getUTG(ctx, user), date));
					}

					pInfo = new PriceInformation(pInfo.getQualifiers(), pInfo.getPriceValue().getOtherPrice(theTaxValues));
				}

				if (row.getChannel() == null) {
					defaultPriceInfos.add(pInfo);
				}

				if (channel == null && row.getChannel() == null) {
					priceInfos.add(pInfo);
				} else if (channel != null && row.getChannel() != null && row.getChannel().getCode().equalsIgnoreCase(channel.getCode())) {
					priceInfos.add(pInfo);
				}
			}

			if (priceInfos.size() == 0) {
				return defaultPriceInfos;
			}

			return priceInfos;
		}
	}

	protected Double addBuAdditionalPrice(Double price, BUType buType){
		BUPriceAdditionalConfModel buPriceAdditionalConfModel = acerchemBuPriceDao.getBuPriceModelByCode(buType.getCode());
		if(buPriceAdditionalConfModel!=null){
			if(StringUtils.isNotBlank(buPriceAdditionalConfModel.getPrice())){
				BigDecimal buAdditionalPrice = new BigDecimal(buPriceAdditionalConfModel.getPrice());
				buAdditionalPrice = buAdditionalPrice.setScale(2, BigDecimal.ROUND_HALF_UP);
				BigDecimal defaultPrice = new BigDecimal(price);
				defaultPrice = defaultPrice.add(buAdditionalPrice);
				price = defaultPrice.doubleValue();
			}
		}
		return price;
	}
}
