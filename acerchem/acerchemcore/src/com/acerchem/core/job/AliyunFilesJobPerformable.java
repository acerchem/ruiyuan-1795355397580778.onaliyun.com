package com.acerchem.core.job;

import java.io.File;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;

import com.acerchem.core.image.service.AcerChemImageFailedRecoredService;
import com.acerchem.core.image.service.AcerChemImageUploadLogService;
import com.acerchem.core.model.ImageFailedRecordModel;
import com.acerchem.core.model.ImageUploadedLogModel;
import com.acerchem.core.web.aliyun.MediaFileManager;
import com.acerchem.core.web.aliyun.UploadFileDefault;

import de.hybris.platform.cronjob.enums.CronJobResult;
import de.hybris.platform.cronjob.enums.CronJobStatus;
import de.hybris.platform.cronjob.model.CronJobModel;
import de.hybris.platform.servicelayer.config.ConfigurationService;
import de.hybris.platform.servicelayer.cronjob.AbstractJobPerformable;
import de.hybris.platform.servicelayer.cronjob.PerformResult;

public class AliyunFilesJobPerformable extends AbstractJobPerformable<CronJobModel> {

	@Resource
	private ConfigurationService configurationService;
	@Resource
	private AcerChemImageFailedRecoredService acerChemImageFailedRecoredService;
	
	@Resource
	private AcerChemImageUploadLogService acerChemImageUploadLogService;

	@Override
	public PerformResult perform(CronJobModel arg0) {
		// TODO Auto-generated method stub

		List<ImageFailedRecordModel> list = acerChemImageFailedRecoredService.getAllImageFailedRecord();

		if (CollectionUtils.isNotEmpty(list)) {

			// 初始化aliyun参数
			String lsEndpoint = configurationService.getConfiguration().getString("aliyun.endpoint");
			String lsAccessKeyId = configurationService.getConfiguration().getString("aliyun.accessKeyId");
			String lsAccessKeySecret = configurationService.getConfiguration().getString("aliyun.accessKeySecret");
			String lsBucketName = configurationService.getConfiguration().getString("aliyun.bucketName");
			MediaFileManager.initializeParameters(lsEndpoint, lsAccessKeyId, lsAccessKeySecret, lsBucketName);
			UploadFileDefault.initializeParameters(lsEndpoint, lsAccessKeyId, lsAccessKeySecret, lsBucketName);
			try {

				for (ImageFailedRecordModel model : list) {
					String action = model.getActionType().toString();
					String key = model.getAliyunUrl();
					if ("ADD".equals(action)) {
						
						String location = model.getLocation();
						File file = new File(location);

						if (UploadFileDefault.uploadFile(file, key)) {

							modelService.remove(model);
						}
					} else if ("DEL".equals(action)) {

						if(MediaFileManager.deleteFile(key)){
							
							modelService.remove(model);
							
							//delete log
							String pk = key.substring(key.lastIndexOf("/")+1);
							pk = pk.substring(0,pk.indexOf("."));
							ImageUploadedLogModel iulModel = acerChemImageUploadLogService.getImageUploadedLog(pk);
							if (iulModel != null){
								modelService.remove(iulModel);
							}
							
						}
					}

				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return new PerformResult(CronJobResult.SUCCESS, CronJobStatus.FINISHED);
	}

}
