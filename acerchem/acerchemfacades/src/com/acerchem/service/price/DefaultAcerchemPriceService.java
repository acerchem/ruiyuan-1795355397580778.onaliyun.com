package com.acerchem.service.price;



import com.acerchem.core.model.UserLevelModel;
import de.hybris.platform.commercefacades.order.data.OrderData;
import de.hybris.platform.commercefacades.product.PriceDataFactory;
import de.hybris.platform.commercefacades.product.data.PriceData;
import de.hybris.platform.core.model.user.UserModel;
import de.hybris.platform.product.impl.DefaultPriceService;
import de.hybris.platform.servicelayer.user.UserService;
import org.apache.log4j.Logger;
import org.jvnet.hk2.annotations.Service;
import org.springframework.beans.factory.annotation.Required;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;

@Service
public class DefaultAcerchemPriceService extends DefaultPriceService{

    @Resource
    private UserService userService;

    private PriceDataFactory priceDataFactory;

    private final static Logger LOG = Logger.getLogger( DefaultAcerchemPriceService.class );



    protected PriceDataFactory getPriceDataFactory()
    {
        return priceDataFactory;
    }

    @Required
    public void setPriceDataFactory(final PriceDataFactory priceDataFactory)
    {
        this.priceDataFactory = priceDataFactory;
    }

    /**
     * 通过商品价格计算折扣价格
     * @param priceData
     */
    public PriceData creatpPromotionPrice(PriceData priceData){

        if( null!=priceData ){

            final UserModel user = userService.getCurrentUser();
            UserLevelModel userLevel = user.getUserLevel();

            if(userLevel!=null){

                Double discount = userLevel.getDiscount();
                if(discount!=null&&userLevel!=null){

                    final PriceData promotionPrice = getPriceDataFactory().create( priceData.getPriceType(), priceData.getValue().multiply(BigDecimal.valueOf(discount)), priceData.getCurrencyIso());
                    LOG.info("discount="+discount+"||basePrice="+priceData.getValue()+"||promotionPrice="+promotionPrice.getValue());

                    return promotionPrice;
                }
            }
        }
        return null;
    }



}
