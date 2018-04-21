package com.acerchem.core.event;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;

import com.acerchem.core.enums.ImageFailedActionType;
import com.acerchem.core.image.service.AcerChemImageFailedRecoredService;
import com.acerchem.core.image.service.AcerChemImageUploadLogService;
import com.acerchem.core.image.service.AcerChemMediaService;
import com.acerchem.core.model.ImageFailedRecordModel;
import com.acerchem.core.web.aliyun.MediaFileManager;

import de.hybris.platform.core.PK;
import de.hybris.platform.core.model.media.MediaModel;
import de.hybris.platform.enumeration.EnumerationService;
import de.hybris.platform.servicelayer.config.ConfigurationService;
import de.hybris.platform.servicelayer.event.events.AfterItemRemovalEvent;
import de.hybris.platform.servicelayer.event.impl.AbstractEventListener;
import de.hybris.platform.servicelayer.model.ModelService;
import de.hybris.platform.util.Config;

public class MediaFilesAfterDeletedListener extends AbstractEventListener<AfterItemRemovalEvent> {

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

	private final static String DOMAIN = Config.getString("aliyun.domain",
			"https://acerchem.oss-us-east-1.aliyuncs.com");
	private final String lsEndpoint = Config.getString("aliyun.endpoint", "http://oss-us-east-1.aliyuncs.com");
	private final String lsAccessKeyId = Config.getString("aliyun.accessKeyId", "LTAItFNuj9ju8BhI");
	private final String lsAccessKeySecret = Config.getString("aliyun.accessKeySecret",
			"GfyBU4iNfQftoUV20fHoYz2zNqJARy");
	private final String lsBucketName = Config.getString("aliyun.bucketName", "acerchem");

	@Override
	protected void onEvent(AfterItemRemovalEvent event) {
		// TODO Auto-generated method stub
		try {
			if (event != null) {
				// System.out.println("****after item removal event
				// active****");
				PK pk = (PK) event.getSource();
				// 指定是media
				if (30 == pk.getTypeCode()) {
					// ImageUploadedLogModel iulModel =
					// acerChemImageUploadLogService
					// .getImageUploadedLog(pk.getLongValueAsString());
					System.out.println("****domain=" + DOMAIN + "****");
					MediaModel media = acerChemMediaService.getMediaModelByPk(pk.getLongValueAsString());

					System.out.println("fromMediaofLocation=" + media.getLocation());
					// acerChemMediaService.getMediaModelByPk(pk.getLongValueAsString());
					if (media != null) {
						if (StringUtils.isNotBlank(media.getAliyunUrl())) {

							String key = media.getAliyunUrl().replace(DOMAIN + "/", "");

							// 初始化upload参数

							MediaFileManager.initializeParameters(lsEndpoint, lsAccessKeyId, lsAccessKeySecret,
									lsBucketName);

							System.out.println("*****delete aliyun file starting*****");
							// delete aliyun file
							boolean b = MediaFileManager.deleteFile(key);

							if (b) {
								// delete ImageUploadedLogModel
								media.setAliyunUrl(null);
								modelService.save(media);
							} else {
								deleteAliyunFileFailedProccess(media, key);
							}
							System.out.print("*****delete aliyun file finished*****");
						}

					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	// 网上删除aliyun文件失败处理
	private void deleteAliyunFileFailedProccess(final MediaModel media, final String key) {
		ImageFailedActionType actionType = enumerationService.getEnumerationValue(ImageFailedActionType.class, "DEL");

		String fileName = media.getAliyunUrl();
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
