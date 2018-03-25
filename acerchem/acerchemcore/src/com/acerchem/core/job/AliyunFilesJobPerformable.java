package com.acerchem.core.job;

import de.hybris.platform.cronjob.enums.CronJobResult;
import de.hybris.platform.cronjob.enums.CronJobStatus;
import de.hybris.platform.cronjob.model.CronJobModel;
import de.hybris.platform.servicelayer.cronjob.AbstractJobPerformable;
import de.hybris.platform.servicelayer.cronjob.PerformResult;

public class AliyunFilesJobPerformable extends AbstractJobPerformable<CronJobModel> {

	/* (non-Javadoc)
	 * @see de.hybris.platform.servicelayer.cronjob.AbstractJobPerformable#perform(de.hybris.platform.cronjob.model.CronJobModel)
	 */
	@Override
	public PerformResult perform(CronJobModel arg0) {
		// TODO Auto-generated method stub
		
		
		
		return new PerformResult(CronJobResult.SUCCESS, CronJobStatus.FINISHED);
	}
	
	private void continuesAliyunFileProcesser(){
		
	}

}
