package com.acerchem.fulfilmentprocess.jobs;

import com.acerchem.core.model.ProductThePriceModel;
import de.hybris.platform.core.model.product.ProductModel;
import de.hybris.platform.cronjob.enums.CronJobResult;
import de.hybris.platform.cronjob.enums.CronJobStatus;
import de.hybris.platform.cronjob.model.CronJobModel;
import de.hybris.platform.europe1.model.PriceRowModel;
import de.hybris.platform.servicelayer.cronjob.AbstractJobPerformable;
import de.hybris.platform.servicelayer.cronjob.PerformResult;
import de.hybris.platform.servicelayer.search.FlexibleSearchQuery;
import de.hybris.platform.servicelayer.search.SearchResult;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author liutao
 * @date 2019/08/21
 */
public class ProductThePriceStatisticalJob extends AbstractJobPerformable<CronJobModel> {


    private static final Logger LOG = Logger.getLogger(ProductThePriceStatisticalJob.class);
    @Override
    public PerformResult perform(CronJobModel cronJobModel) {
        Date now = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String format = sdf.format(now);
        Calendar cal = Calendar.getInstance();
        cal.setTime(now);
        // 设置一个星期的第一天，按中国的习惯一个星期的第一天是星期一
        cal.setFirstDayOfWeek(Calendar.MONDAY);
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH)+ 1;
        int week = cal.get(Calendar.WEEK_OF_MONTH);//获取到当前时间是第几周

        String query = "select {pr.pk} from {PriceRow as pr left join Product as pt on {pr.product} = {pt.pk} }  where " +
                "({pr.endTime} >= ?currentTime  and {pr.startTime} < ?currentTime ) or ({pr.startTime} is null and {pr.endTime} is null)";
        final FlexibleSearchQuery searchQuery = new FlexibleSearchQuery(query);
        searchQuery.addQueryParameter("currentTime", format);
        final SearchResult<PriceRowModel> search = flexibleSearchService.search(searchQuery);
        if (CollectionUtils.isNotEmpty(search.getResult())){
            List<ProductThePriceModel> productThePriceModels = new ArrayList<>();
            search.getResult().stream().forEach(e->{
                ProductThePriceModel model = new ProductThePriceModel();
                if (null != e.getProduct()){
                    model.setProductCode(e.getProduct().getCode());
                }else {
                    model.setProductCode("".equals(e.getProductId())?"":e.getProductId());
                }
                model.setProductName(StringUtils.isNotBlank(e.getProduct().getOtherName())?e.getProduct().getOtherName():"");
                model.setPrice(e.getPrice());
                model.setYear(year);
                model.setMonth(month);
                model.setWeeks(week);
                productThePriceModels.add(model);
            });
            this.modelService.saveAll(productThePriceModels);
        }
        return new PerformResult(CronJobResult.SUCCESS, CronJobStatus.FINISHED);
    }

}
