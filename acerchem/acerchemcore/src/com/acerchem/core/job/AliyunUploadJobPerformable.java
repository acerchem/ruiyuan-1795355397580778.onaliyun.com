package com.acerchem.core.job;

import org.apache.log4j.Logger;

import de.hybris.platform.cronjob.model.CronJobModel;
import de.hybris.platform.servicelayer.cronjob.AbstractJobPerformable;
import de.hybris.platform.servicelayer.cronjob.PerformResult;

public class AliyunUploadJobPerformable extends AbstractJobPerformable<CronJobModel> {
	private static final Logger LOG = Logger.getLogger(AliyunUploadJobPerformable.class);

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.hybris.platform.servicelayer.cronjob.AbstractJobPerformable#perform(de
	 * .hybris.platform.cronjob.model.CronJobModel)
	 */
	@Override
	public PerformResult perform(CronJobModel cronJob) {
		// TODO Auto-generated method stub
		return null;
	}

}
