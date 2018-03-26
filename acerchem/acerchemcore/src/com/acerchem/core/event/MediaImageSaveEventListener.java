package com.acerchem.core.event;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;

import com.acerchem.core.enums.ImageFailedActionType;
import com.acerchem.core.image.service.AcerChemImageFailedRecoredService;
import com.acerchem.core.image.service.AcerChemImageUploadLogService;
import com.acerchem.core.image.service.AcerChemMediaService;
import com.acerchem.core.model.ImageFailedRecordModel;
import com.acerchem.core.model.ImageUploadedLogModel;
import com.acerchem.core.web.aliyun.UploadFileDefault;

import de.hybris.platform.core.PK;
import de.hybris.platform.core.model.media.MediaModel;
import de.hybris.platform.enumeration.EnumerationService;
import de.hybris.platform.jalo.media.Media;
import de.hybris.platform.servicelayer.config.ConfigurationService;
import de.hybris.platform.servicelayer.exceptions.ModelLoadingException;
import de.hybris.platform.servicelayer.model.ModelService;
import de.hybris.platform.tx.AfterSaveEvent;
import de.hybris.platform.tx.AfterSaveListener;

public class MediaImageSaveEventListener implements AfterSaveListener {

	@Resource
	private ModelService modelService;
	@Resource(name = "configurationService")
	private ConfigurationService configurationService;
	@Resource
	private AcerChemImageUploadLogService acerChemImageUploadLogService;
	@Resource
	private AcerChemMediaService acerChemMediaService;

	@Resource
	private EnumerationService enumerationService;

	@Resource
	private AcerChemImageFailedRecoredService acerChemImageFailedRecoredService;

	@Override
	public void afterSave(Collection<AfterSaveEvent> collection) {
		// TODO Auto-generated method stub
		for (final AfterSaveEvent event : collection) {
			final int type = event.getType();
			if (AfterSaveEvent.UPDATE == type || AfterSaveEvent.CREATE == type) {
				final PK pk = event.getPk();
				// 30 is media
				if (30 == pk.getTypeCode()) {
					System.out.println("*****Media After save event is active!********");
					try {
						final MediaModel media = modelService.get(pk);
						if (media == null)
							return;
						// logic。。。。。。。。
						// when media is images
						String mediaPath = StringUtils.isNotBlank(media.getFolder().getPath())
								? media.getFolder().getPath() : "";
						String mediaType = media.getMime();
						// 采用全mimep匹配，否则会因为cronJob缘故，上传zip文件
						if (StringUtils.isNotBlank(mediaType)) {
							// mediaType = mediaType.substring(0,
							// mediaType.indexOf("/"));
						} else {
							mediaType = "";
						}

						final String ls = media.getLocation();
						if (mediaType.equals("image/jpeg") || mediaType.equals("image/png")
								|| mediaType.equals("image/gif") || mediaType.equals("image/bmp")) {

							String root = configurationService.getConfiguration().getString("aliyun.preffixImageKey");
							if (StringUtils.isNotBlank(ls)) {

								System.out.println("**********upload image to aliyun start*********");
								uploadFileSendProcessor(media, root);

							} else {
								System.out.println("Media's Location is null!");
							}
						}
						// 资质文件,暂定三种
						else if (mediaType.equals("application/pdf") || mediaType.equals("application/msexcel")
								|| mediaType.equals("application/msword")) {
							String root = configurationService.getConfiguration().getString("aliyun.preffixDocKey");
							if (StringUtils.isNotBlank(ls)) {
								System.out.println("**********upload doc to aliyun start*********");
								uploadFileSendProcessor(media, root);
							}

						}

					} catch (ModelLoadingException me) {
						// me.printStackTrace();
						System.out.print(
								"Preventing no resoure,After saved Event again,Occurring thread cross over? check it in future...");

					} catch (Exception e) {
						e.printStackTrace();
					}
				}

			} else if (AfterSaveEvent.REMOVE == type) {
				System.out.println("****After deleted saveEvent start******");
			}

		}

	}

	private void uploadFileSendProcessor(final MediaModel media, final String root) {

		try {
			// get localfile path
			String localPath = configurationService.getConfiguration().getString("upload.path");
			localPath += media.getLocation();

			System.out.println(localPath);
			File file = new File(localPath);

			// aliyun path
			SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
			String temp_ = df.format(new Date());

			String keySuffix = file.getName().substring(file.getName().lastIndexOf(".") + 1);

			// aliyun relation path>>> application/yyyymmdd/
			String key = root + "/" + temp_ + "/" + media.getPk().getLong().toString() + "." + keySuffix;
			// 初始化upload参数
			String lsEndpoint = configurationService.getConfiguration().getString("aliyun.endpoint");
			String lsAccessKeyId = configurationService.getConfiguration().getString("aliyun.accessKeyId");
			String lsAccessKeySecret = configurationService.getConfiguration().getString("aliyun.accessKeySecret");
			String lsBucketName = configurationService.getConfiguration().getString("aliyun.bucketName");

			UploadFileDefault.initializeParameters(lsEndpoint, lsAccessKeyId, lsAccessKeySecret, lsBucketName);
			// upload aliyun

			boolean uploadFlag = UploadFileDefault.uploadFile(file, key);

			// if (!acerChemImageUploadLogService.isExistByLocation(localPath))
			// {
			ImageUploadedLogModel iulModel = acerChemImageUploadLogService
					.getImageUploadedLog(media.getPk().getLong().toString());
			String aliyunUrl = configurationService.getConfiguration().getString("aliyun.domain") + "/" + key;
			if (uploadFlag) {
				System.out.println("****upload end>>>>synsave to server start*****");
				// save aliyunUrl to ImageUploadedLog
				if (iulModel == null) {
					iulModel = modelService.create(ImageUploadedLogModel.class);
				}
				iulModel.setAliyunUrl(aliyunUrl);
				iulModel.setImagePK(media.getPk().getLong().toString());
				iulModel.setLocation(localPath);

				modelService.save(iulModel);
				System.out.println("****synsave to server end*****");
			} else {
				uploadFailedProccess(media, key, localPath);
			}
			// }

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	// 上传失败处理--保存上传的文件路径和aliyun的key路径
	private void uploadFailedProccess(MediaModel media, String aliyunPath, String path) {

		ImageFailedActionType actionType = enumerationService.getEnumerationValue(ImageFailedActionType.class, "ADD");
		String fileName = media.getLocation();
		if (fileName == null)
			return;

		fileName = fileName.substring(fileName.lastIndexOf("/") + 1);

		String status = "0";
		ImageFailedRecordModel failedRecord = acerChemImageFailedRecoredService.getImageFailedRecordByFileAttr(fileName,
				"ADD");
		if (failedRecord != null) {
			int n = Integer.getInteger(failedRecord.getStatus());
			n++;
			status = String.valueOf(n);

			failedRecord.setAliyunUrl(aliyunPath);
			failedRecord.setLocation(path);
			failedRecord.setStatus(status);

		} else {
			failedRecord = modelService.create(ImageFailedRecordModel.class);
			failedRecord.setFileName(fileName);
			failedRecord.setActionType(actionType);
			failedRecord.setAliyunUrl(aliyunPath);
			failedRecord.setLocation(path);
			failedRecord.setStatus(status);

		}

		modelService.save(failedRecord);

	}
	
	//配置处理资质文件的Mime
	private boolean isIncluding(final String mimeType){
		
		
		return false;
	}

}
