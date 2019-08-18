package com.acerchem.core.event;

import javax.annotation.Resource;

import com.acerchem.core.enums.ImageFailedActionType;
import com.acerchem.core.image.service.AcerChemImageFailedRecoredService;
import com.acerchem.core.image.service.AcerChemImageUploadLogService;
import com.acerchem.core.image.service.AcerChemMediaService;
import com.acerchem.core.model.ImageFailedRecordModel;
import com.acerchem.core.model.ImageUploadedLogModel;
import com.acerchem.core.web.aliyun.MediaFileManager;

import de.hybris.platform.core.PK;
import de.hybris.platform.enumeration.EnumerationService;
import de.hybris.platform.servicelayer.config.ConfigurationService;
import de.hybris.platform.servicelayer.event.events.AfterItemRemovalEvent;
import de.hybris.platform.servicelayer.event.impl.AbstractEventListener;
import de.hybris.platform.servicelayer.model.ModelService;
import org.apache.log4j.Logger;

public class MyImageAfterDeletedListener extends AbstractEventListener<AfterItemRemovalEvent> {


	private static final Logger LOG = Logger.getLogger(MyImageAfterDeletedListener.class);

	@Resource(name = "configurationService")
	private ConfigurationService configurationService;
	@Resource
	private ModelService modelService;
	@Resource
	private EnumerationService enumerationService;
	@Resource
	private AcerChemMediaService acerChemMediaService;

	@Resource
	private AcerChemImageUploadLogService acerChemImageUploadLogService;
	@Resource
	private AcerChemImageFailedRecoredService acerChemImageFailedRecoredService;

	@Override
	protected void onEvent(AfterItemRemovalEvent event) {
		// TODO Auto-generated method stub
		try {
			if (event != null) {
				System.out.println("****after item removal event active****");
				PK pk = (PK) event.getSource();
				// 指定是media
				if (30 == pk.getTypeCode()) {
					ImageUploadedLogModel iulModel = acerChemImageUploadLogService
							.getImageUploadedLog(pk.getLongValueAsString());

					if (iulModel != null) {
						String domain = configurationService.getConfiguration().getString("aliyun.domain");
						String key = iulModel.getAliyunUrl().replace(domain+"/", "");

						// 初始化upload参数
						String lsEndpoint = configurationService.getConfiguration().getString("aliyun.endpoint");
						String lsAccessKeyId = configurationService.getConfiguration().getString("aliyun.accessKeyId");
						String lsAccessKeySecret = configurationService.getConfiguration()
								.getString("aliyun.accessKeySecret");
						String lsBucketName = configurationService.getConfiguration().getString("aliyun.bucketName");

						MediaFileManager.initializeParameters(lsEndpoint, lsAccessKeyId, lsAccessKeySecret,
								lsBucketName);

						System.out.print("*****delete aliyun file starting*****");
						// delete aliyun file
						boolean b = MediaFileManager.deleteFile(key);
						
						if (b) {
							// delete ImageUploadedLogModel
							modelService.remove(iulModel);
						} else {
							deleteAliyunFileFailedProccess(iulModel, key);
						}
						System.out.println("*****delete aliyun file finished*****");
					}

				}
			}
		} catch (Exception e) {
//			e.printStackTrace();
			LOG.error(e.getMessage(),e);
		}

	}

	// 网上删除aliyun文件失败处理
	private void deleteAliyunFileFailedProccess(final ImageUploadedLogModel iulModel, final String key) {
		ImageFailedActionType actionType = enumerationService.getEnumerationValue(ImageFailedActionType.class, "DEL");

		String fileName = iulModel.getAliyunUrl();
		if (fileName == null)
			return;

		fileName = fileName.substring(fileName.lastIndexOf("/") + 1);

		String status = "0";
		ImageFailedRecordModel failedRecord = acerChemImageFailedRecoredService.getImageFailedRecordByFileAttr(fileName,
				"DEL");
		if (failedRecord != null) {
			int n = Integer.getInteger(failedRecord.getStatus());
			n++;
			status = String.valueOf(n);

			failedRecord.setAliyunUrl(key);
			failedRecord.setStatus(status);

		} else {
			failedRecord = modelService.create(ImageFailedRecordModel.class);
			failedRecord.setFileName(fileName);
			failedRecord.setActionType(actionType);
			failedRecord.setAliyunUrl(key);
			failedRecord.setStatus(status);

		}

		modelService.save(failedRecord);

	}

}
