package com.acerchem.core.event;

import java.io.File;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;

import com.acerchem.core.enums.ImageFailedActionType;
import com.acerchem.core.image.service.AcerChemImageFailedRecoredService;
import com.acerchem.core.image.service.AcerChemImageUploadLogService;
import com.acerchem.core.image.service.AcerChemMediaService;
import com.acerchem.core.model.ImageFailedRecordModel;
import com.acerchem.core.model.ImageUploadedLogModel;
import com.acerchem.core.web.aliyun.UploadFileDefault;
import com.aliyun.oss.OSSClient;
import com.google.common.base.Preconditions;


import de.hybris.platform.core.PK;
import de.hybris.platform.core.Registry;
import de.hybris.platform.core.model.media.MediaModel;
import de.hybris.platform.enumeration.EnumerationService;
import de.hybris.platform.jalo.media.Media;
import de.hybris.platform.jalo.media.MediaManager;
import de.hybris.platform.servicelayer.config.ConfigurationService;
import de.hybris.platform.servicelayer.exceptions.ModelLoadingException;
import de.hybris.platform.servicelayer.media.impl.ModelMediaSource;
import de.hybris.platform.servicelayer.model.ModelService;
import de.hybris.platform.tx.AfterSaveEvent;
import de.hybris.platform.tx.AfterSaveListener;
import de.hybris.platform.util.MediaUtil;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Supplier;

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

		final String imageRoot = configurationService.getConfiguration().getString("aliyun.preffixImageKey");
		final String docRoot = configurationService.getConfiguration().getString("aliyun.preffixDocKey");
		final String asyncFlag = configurationService.getConfiguration().getString("aliyun.async");
		// get dir of key
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
		String temp_ = df.format(new Date());

		for (final AfterSaveEvent event : collection) {
			final int type = event.getType();
			if (AfterSaveEvent.CREATE == type) {
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

						final String localPath = media.getLocation();
						if (mediaType.equals("image/jpeg") || mediaType.equals("image/png")
								|| mediaType.equals("image/gif") || mediaType.equals("image/bmp")) {

							if (StringUtils.isNotBlank(localPath)) {

								if (asyncFlag.equals("yes")) {
									// 准备上传到阿里云的key
									System.out.println("******save to imageFailedRecord!********");
									String keySuffix = localPath.substring(localPath.lastIndexOf(".") + 1);
									String key = imageRoot + "/" + temp_ + "/" + media.getPk().getLongValue() + "."
											+ keySuffix;
									uploadFailedProccess(media, key);

//								
								} else if (asyncFlag.equals("no")) {
									System.out.println("**********upload image to aliyun start*********");
									// System.out.println(media.getRemovable());

									uploadFileSendProcessor(media, imageRoot);
								}

							} else {
								System.out.println("Media's Location is null!");
							}
						}
						// 资质文件,暂定三种
						else if (mediaType.equals("application/pdf") || mediaType.equals("application/msexcel")
								|| mediaType.equals("application/msword")) {

							if (StringUtils.isNotBlank(localPath)) {
								System.out.println("**********upload doc to aliyun start*********");
								uploadFileSendProcessor(media, docRoot);
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
				// System.out.println("****After deleted saveEvent
				// start******");
			}

		}

	}

	private void uploadFileSendProcessor(final MediaModel media, final String root) {

		// 初始化upload参数
		String lsEndpoint = configurationService.getConfiguration().getString("aliyun.endpoint");
		String lsAccessKeyId = configurationService.getConfiguration().getString("aliyun.accessKeyId");
		String lsAccessKeySecret = configurationService.getConfiguration().getString("aliyun.accessKeySecret");
		String lsBucketName = configurationService.getConfiguration().getString("aliyun.bucketName");

		UploadFileDefault.initializeParameters(lsEndpoint, lsAccessKeyId, lsAccessKeySecret, lsBucketName);

		OSSClient client = UploadFileDefault.openClient();
		try {
			// get localfile path
			// String localPath =
			// configurationService.getConfiguration().getString("upload.path");
			String localPath = media.getLocation();

			System.out.println(localPath);
			// String mainDataDir =
			// get parent fileDir
			File mainDataDir = MediaUtil.getLocalStorageDataDir();
			// get local file
			File file = MediaUtil.composeOrGetParent(mainDataDir, localPath);

			System.out.println(file.getAbsolutePath());

			// Preconditions.checkState(!modelService.isNew(media), "media must
			// be persisted to do binary operations");
			// ServicesUtil.validateParameterNotNull(media, "Argument media
			// cannot be null");
			// boolean b = mediaService.hasData(media);
			// if (b) {
			// Registry.activateMasterTenant();
			// System.out.println("*****NB*****");
			// InputStream input =
			// MediaManager.getInstance().getMediaAsStream(new
			// ModelMediaSource(media));

			// File file = new File(localPath);

			// aliyun path
			SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
			String temp_ = df.format(new Date());

			String keySuffix = localPath.substring(localPath.lastIndexOf(".") + 1);

			// aliyun relation path>>> application/yyyymmdd/
			String key = root + "/" + temp_ + "/" + media.getPk().getLong().toString() + "." + keySuffix;

			// upload aliyun

			boolean uploadFlag = UploadFileDefault.uploadFile(file, key, client);

			// if
			// (!acerChemImageUploadLogService.isExistByLocation(localPath))
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
				uploadFailedProccess(media, key);
			}
			// }
			// }

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			UploadFileDefault.closeClient(client);
		}

	}

	// 上传失败处理--保存上传的文件路径和aliyun的key路径
	private void uploadFailedProccess(MediaModel media, String aliyunPath) {
		try {

			ImageFailedActionType actionType = enumerationService.getEnumerationValue(ImageFailedActionType.class,
					"ADD");
			// String fileName = media.getLocation();
			// if (fileName == null)
			// return;

			String fileName = aliyunPath.substring(aliyunPath.lastIndexOf("/") + 1);

			String status = "0";
			ImageFailedRecordModel failedRecord = acerChemImageFailedRecoredService
					.getImageFailedRecordByFileAttr(fileName, "ADD");
			if (failedRecord != null) {
				int n = Integer.getInteger(failedRecord.getStatus());
				n++;
				status = String.valueOf(n);

				failedRecord.setAliyunUrl(aliyunPath);
				failedRecord.setLocation(media.getLocation());
				failedRecord.setStatus(status);
				failedRecord.setMediaData(media);
				failedRecord.setMediaPK(media.getPk().getLongValueAsString());

			} else {
				failedRecord = modelService.create(ImageFailedRecordModel.class);
				failedRecord.setFileName(fileName);
				failedRecord.setActionType(actionType);
				failedRecord.setAliyunUrl(aliyunPath);
				failedRecord.setLocation(media.getLocation());
				failedRecord.setStatus(status);
				failedRecord.setMediaData(media);
				failedRecord.setMediaPK(media.getPk().getLongValueAsString());

			}

			modelService.save(failedRecord);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	// 配置处理资质文件的Mime
	private boolean isIncluding(final String mimeType) {

		return false;
	}

}
