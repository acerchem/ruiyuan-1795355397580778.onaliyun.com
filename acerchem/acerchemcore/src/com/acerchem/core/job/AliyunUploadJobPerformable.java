package com.acerchem.core.job;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;

import com.acerchem.core.enums.ImageFailedActionType;
import com.acerchem.core.image.service.AcerChemImageFailedRecoredService;
import com.acerchem.core.image.service.AcerChemImageUploadLogService;
import com.acerchem.core.image.service.AcerChemMediaService;
import com.acerchem.core.model.ImageFailedRecordModel;
import com.acerchem.core.model.ImageUploadedLogModel;
import com.acerchem.core.web.aliyun.UploadFileDefault;
import com.aliyun.oss.OSSClient;

import de.hybris.platform.core.model.media.MediaModel;
import de.hybris.platform.cronjob.enums.CronJobResult;
import de.hybris.platform.cronjob.enums.CronJobStatus;
import de.hybris.platform.cronjob.model.CronJobModel;
import de.hybris.platform.enumeration.EnumerationService;
import de.hybris.platform.servicelayer.cronjob.AbstractJobPerformable;
import de.hybris.platform.servicelayer.cronjob.PerformResult;
import de.hybris.platform.util.Config;
import de.hybris.platform.util.MediaUtil;

public class AliyunUploadJobPerformable extends AbstractJobPerformable<CronJobModel> {
	private static final Logger LOG = Logger.getLogger(AliyunUploadJobPerformable.class);

	@Resource
	private AcerChemMediaService acerChemMediaService;
	@Resource
	private EnumerationService enumerationService;
	@Resource
	private AcerChemImageFailedRecoredService acerChemImageFailedRecoredService;

	@Resource
	private AcerChemImageUploadLogService acerChemImageUploadLogService;

	private final static boolean IS_NOUPLOAD = Config.getBoolean("aliyun.isNoUpload", false);
	private final static String IMAGEROOT = Config.getString("aliyun.preffixImageKey", "image");
	private final static String DOCROOT = Config.getString("aliyun.preffixDocKey", "doc");
	private final static int MAX_IMAGE = Config.getInt("aliyun.maxOfImage", 10);
	private final static int MAX_DOC = Config.getInt("aliyun.maxOfDoc", 10);
	private final static boolean SYN_UPLOAD_DOC = Config.getBoolean("aliyun.isSynUploadDOC", false);
	private final static String DOMAIN = Config.getString("aliyun.domain",
			"https://acerchem.oss-us-east-1.aliyuncs.com");

	private final String lsEndpoint = Config.getString("aliyun.endpoint", "http://oss-us-east-1.aliyuncs.com");
	private final String lsAccessKeyId = Config.getString("aliyun.accessKeyId", "LTAItFNuj9ju8BhI");
	private final String lsAccessKeySecret = Config.getString("aliyun.accessKeySecret",
			"GfyBU4iNfQftoUV20fHoYz2zNqJARy");
	private final String lsBucketName = Config.getString("aliyun.bucketName", "acerchem");

	@Override
	public PerformResult perform(final CronJobModel cronJob) {
		// TODO Auto-generated method stub
		try {
			if (!IS_NOUPLOAD) {
				final List<String> imageParams = new ArrayList<String>();
				imageParams.add("image/jpeg");
				imageParams.add("image/png");
				imageParams.add("image/gif");
				imageParams.add("image/bmp");

				// 每次MAX_IMAGE个图片文件数据处理
				final List<MediaModel> medias = acerChemMediaService.getMediasOfLimit(imageParams, MAX_IMAGE, "Online",
						"acerchem");

				if (CollectionUtils.isNotEmpty(medias)) {

					for (final MediaModel media : medias) {
						LOG.info("****>>>version=" + media.getCatalogVersion().getVersion());
						uploadFileSendProcessor(media, IMAGEROOT);
					}

				}

				// doc
				if (SYN_UPLOAD_DOC) {
					final List<String> docParams = new ArrayList<String>();
					docParams.add("application/pdf");
					docParams.add("application/msexcel");
					docParams.add("application/msword");

					// 每次MAX_DOC个资质文件数据处理
					final List<MediaModel> docMedias = acerChemMediaService.getMediasOfLimit(docParams, MAX_DOC, "Online",
							"acerchem");

					if (CollectionUtils.isNotEmpty(docMedias)) {

						for (final MediaModel docmedia : docMedias) {
							uploadFileSendProcessor(docmedia, DOCROOT);
						}

					}
				}
			}

		} catch (final Exception e) {
			e.printStackTrace();
		}

		return new PerformResult(CronJobResult.SUCCESS, CronJobStatus.FINISHED);
	}

	// 上传阿里云处理器
	private void uploadFileSendProcessor(final MediaModel media, final String root) {

		try {
			// 初始化upload参数
			UploadFileDefault.initializeParameters(lsEndpoint, lsAccessKeyId, lsAccessKeySecret, lsBucketName);

			final OSSClient client = UploadFileDefault.openClient();
			try {
				// get localfile path
				final String localPath = media.getLocation();

				LOG.info("****>>>RelativePath=" + localPath);
				// get parent fileDir
				final File mainDataDir = MediaUtil.getLocalStorageDataDir();
				// get local file
				final File file = MediaUtil.composeOrGetParent(mainDataDir, localPath);
				LOG.info("****>>>AbsolutePath=" + file.getAbsolutePath());

				if (file.exists()) {

					// aliyun path
					final SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
					final String temp_ = df.format(new Date());
					//final String keySuffix = localPath.substring(localPath.lastIndexOf(".") + 1);

					final String filename = file.getName();
					// aliyun relation path>>> application/yyyymmdd/
					//final String key = root + "/" + temp_ + "/" + media.getPk().getLong().toString() + "." + keySuffix;
					final String key = root + "/" + temp_ + "/" + filename;
					// upload aliyun
					final boolean uploadFlag = UploadFileDefault.uploadFile(file, key, client);

					// synchronize to save media with aliyunUrl
					final String aliyunUrl = DOMAIN + "/" + key;
					if (uploadFlag) {

						LOG.info("****upload end>>>>synsave to Media start*****");

						media.setAliyunUrl(aliyunUrl);
						modelService.save(media);

						// add upload image log
						ImageUploadedLogModel iulModel = acerChemImageUploadLogService
								.getImageUploadedLog(media.getPk().getLongValueAsString());
						if (iulModel == null) {
							iulModel = modelService.create(ImageUploadedLogModel.class);
						}
						iulModel.setAliyunUrl(aliyunUrl);
						iulModel.setImagePK(media.getPk().getLong().toString());
						iulModel.setLocation(localPath);

						modelService.save(iulModel);

						LOG.info("****synsave to Media end*****");

					} else {
						// 用来保证即使失败，原始media数据跳过这些失败的，继续新的数据上传
						// 而这些失败数据，放在上传失败记录处理！
						media.setAliyunUrl("UploadAliyunFailed");
						modelService.save(media);
						uploadFailedProccess(media, key);
						LOG.info("****>>>Upload Aliyun Failed!*****");
					}
				} else {

					media.setAliyunUrl("ImagesOfFilesNoExist");
					modelService.save(media);
					LOG.info("****>>>Files can't find!*****");

				}
			} catch (final IOException e) {
				e.printStackTrace();
			} finally {
				UploadFileDefault.closeClient(client);
			}
		} catch (final Exception e) {
			e.printStackTrace();
		}

	}

	// 上传失败处理--保存上传的文件路径和aliyun的key路径
	private void uploadFailedProccess(final MediaModel media, final String aliyunPath) {
		try {

			final ImageFailedActionType actionType = enumerationService.getEnumerationValue(ImageFailedActionType.class,
					"ADD");
			// String fileName = media.getLocation();
			// if (fileName == null)
			// return;

			final String fileName = aliyunPath.substring(aliyunPath.lastIndexOf("/") + 1);

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
		} catch (final Exception e) {
			e.printStackTrace();
		}

	}

}
