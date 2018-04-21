package com.acerchem.core.job;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;

import com.acerchem.core.image.service.AcerChemImageFailedRecoredService;
import com.acerchem.core.image.service.AcerChemImageUploadLogService;
import com.acerchem.core.model.ImageFailedRecordModel;
import com.acerchem.core.model.ImageUploadedLogModel;
import com.acerchem.core.web.aliyun.MediaFileManager;
import com.acerchem.core.web.aliyun.UploadFileDefault;
import com.aliyun.oss.OSSClient;
//import com.amazonaws.util.json.JSONObject;
//import com.amazonaws.util.json.JSONUtils;

import de.hybris.platform.core.Registry;
import de.hybris.platform.core.Tenant;
import de.hybris.platform.cronjob.enums.CronJobResult;
import de.hybris.platform.cronjob.enums.CronJobStatus;
import de.hybris.platform.cronjob.model.CronJobModel;
import de.hybris.platform.servicelayer.config.ConfigurationService;
import de.hybris.platform.servicelayer.cronjob.AbstractJobPerformable;
import de.hybris.platform.servicelayer.cronjob.PerformResult;
import de.hybris.platform.util.MediaUtil;

public class AliyunFilesJobPerformable extends AbstractJobPerformable<CronJobModel> {

	@Resource
	private ConfigurationService configurationService;
	@Resource
	private AcerChemImageFailedRecoredService acerChemImageFailedRecoredService;

	@Resource
	private AcerChemImageUploadLogService acerChemImageUploadLogService;

	private static final Integer MAX_COUNT = 20;

	@Override
	public PerformResult perform(CronJobModel cronJob) {
		// TODO Auto-generated method stub
		System.out.println("****cronJob  Scheduling***");
		try {
			List<ImageFailedRecordModel> list = acerChemImageFailedRecoredService.getAllImageFailedRecord();

			// List<MediaModel> list =
			// acerChemImageFailedRecoredService.getMediaWithImageFailedRecord();
			if (list.size() > 0) {

				// 初始化aliyun参数
				String lsEndpoint = configurationService.getConfiguration().getString("aliyun.endpoint");
				String lsAccessKeyId = configurationService.getConfiguration().getString("aliyun.accessKeyId");
				String lsAccessKeySecret = configurationService.getConfiguration().getString("aliyun.accessKeySecret");
				String lsBucketName = configurationService.getConfiguration().getString("aliyun.bucketName");
				String lsDomain = configurationService.getConfiguration().getString("aliyun.domain");
				MediaFileManager.initializeParameters(lsEndpoint, lsAccessKeyId, lsAccessKeySecret, lsBucketName);
				UploadFileDefault.initializeParameters(lsEndpoint, lsAccessKeyId, lsAccessKeySecret, lsBucketName);

				OSSClient client = UploadFileDefault.openClient();
				int iCount = 0;
				Tenant currentTenant = Registry.getCurrentTenant();
				if (currentTenant == null) {
					Registry.activateMasterTenant();
				}
				try {

					for (ImageFailedRecordModel model : list) {
						String ls = model.getLocation();
						String mediaPK = model.getMediaPK();

						if( iCount == MAX_COUNT.intValue()){
							break;
						}
						// System.out.println(ls);
						String action = model.getActionType().toString();
						System.out.println("***" + action + "***");
						String key = model.getAliyunUrl();
						if (StringUtils.isNotBlank(key)) {
							// 处理后来增加的字段没有的值
							if (StringUtils.isBlank(mediaPK)) {
								mediaPK = key.substring(key.lastIndexOf("/") + 1);
								mediaPK = mediaPK.substring(0, mediaPK.indexOf("."));
							}

							if ("ADD".equals(action) ) {
								File mainDataDir = MediaUtil.getLocalStorageDataDir();

								if (StringUtils.isNotBlank(ls)) {
									File file = MediaUtil.composeOrGetParent(mainDataDir, ls);
									if (file.exists()) {
										System.out.println(file.getAbsolutePath());

										// InputStream input =
										// MediaManager.getInstance().getMediaAsStream(new
										// ModelMediaSource(media));
										System.out.println("*****start upload to aliyun*****");

										boolean success = (UploadFileDefault.uploadFile(file, key, client));
										if (success) {

											modelService.remove(model);

											iCount++;
											System.out.println("*****uploading count (" + iCount + ") *****");
											// sync to save imageUploadedLog
											String aliyunUrl = lsDomain + "/" + key;
											saveUploadLog(mediaPK, aliyunUrl, ls);
										}
									}
								}
							} else if ("DEL".equals(action)) {

								if (MediaFileManager.deleteFile(key)) {

									modelService.remove(model);

									// delete log

//									ImageUploadedLogModel iulModel = acerChemImageUploadLogService
//											.getImageUploadedLog(mediaPK);
//									if (iulModel != null) {
//										modelService.remove(iulModel);
//									}

								}
							}
						}

					}
				} catch (IOException e) {
					e.printStackTrace();
				} finally {
					System.out.println("*****upload finished*****");
					UploadFileDefault.closeClient(client);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return new PerformResult(CronJobResult.SUCCESS, CronJobStatus.FINISHED);
	}

	private void saveUploadLog(final String mediaPK, final String aliyunUrl, final String localPath) {
		ImageUploadedLogModel iulModel = acerChemImageUploadLogService.getImageUploadedLog(mediaPK);

		System.out.println("****upload end>>>>synsave to server start*****");
		// save aliyunUrl to ImageUploadedLog
		if (iulModel == null) {
			iulModel = modelService.create(ImageUploadedLogModel.class);
		}
		iulModel.setAliyunUrl(aliyunUrl);
		iulModel.setImagePK(mediaPK);
		iulModel.setLocation(localPath);

		modelService.save(iulModel);
		System.out.println("****synsave to server end*****");
	}

}
