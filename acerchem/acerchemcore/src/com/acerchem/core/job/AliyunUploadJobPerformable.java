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

import com.acerchem.core.image.service.AcerChemImageFailedRecoredService;
import com.acerchem.core.image.service.AcerChemImageUploadLogService;
import com.acerchem.core.image.service.AcerChemMediaService;
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

		try {

			// image
			List<String> imageParams = new ArrayList<String>();
			imageParams.add("image/jpeg");
			imageParams.add("image/png");
			imageParams.add("image/gif");
			imageParams.add("image/bmp");

			// 每次MAX_IMAGE个图片文件数据处理
			List<MediaModel> medias = acerChemMediaService.getMediasOfLimit(imageParams, MAX_IMAGE);

			if (CollectionUtils.isNotEmpty(medias)) {

				for (MediaModel media : medias) {
					uploadFileSendProcessor(media, IMAGEROOT);
				}

			}

			// doc
			if (SYN_UPLOAD_DOC) {
				List<String> docParams = new ArrayList<String>();
				docParams.add("application/pdf");
				docParams.add("application/msexcel");
				docParams.add("application/msword");

				// 每次MAX_DOC个资质文件数据处理
				List<MediaModel> docMedias = acerChemMediaService.getMediasOfLimit(docParams, MAX_DOC);

				if (CollectionUtils.isNotEmpty(docMedias)) {

					for (MediaModel docmedia : docMedias) {
						uploadFileSendProcessor(docmedia, DOCROOT);
					}

				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return new PerformResult(CronJobResult.SUCCESS, CronJobStatus.FINISHED);
	}

	// 上传阿里云处理器
	private void uploadFileSendProcessor(final MediaModel media, final String root) {

		try {
			// 初始化upload参数
			UploadFileDefault.initializeParameters(lsEndpoint, lsAccessKeyId, lsAccessKeySecret, lsBucketName);

			OSSClient client = UploadFileDefault.openClient();
			try {
				// get localfile path
				String localPath = media.getLocation();

				System.out.println(localPath);
				// get parent fileDir
				File mainDataDir = MediaUtil.getLocalStorageDataDir();
				// get local file
				File file = MediaUtil.composeOrGetParent(mainDataDir, localPath);
				System.out.println(file.getAbsolutePath());
				if (file.exists()) {

					// aliyun path
					SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
					String temp_ = df.format(new Date());
					String keySuffix = localPath.substring(localPath.lastIndexOf(".") + 1);

					// aliyun relation path>>> application/yyyymmdd/
					String key = root + "/" + temp_ + "/" + media.getPk().getLong().toString() + "." + keySuffix;

					// upload aliyun
					boolean uploadFlag = UploadFileDefault.uploadFile(file, key, client);

					// synchronize to save media with aliyunUrl
					String aliyunUrl = DOMAIN + "/" + key;
					if (uploadFlag) {
						System.out.println("****upload end>>>>synsave to Media start*****");

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

						System.out.println("****synsave to Media end*****");
						// } else {
						// uploadFailedProccess(media, key);
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				UploadFileDefault.closeClient(client);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	// 上传失败处理--保存上传的文件路径和aliyun的key路径
	// private void uploadFailedProccess(MediaModel media, String aliyunPath) {
	// try {
	//
	// ImageFailedActionType actionType =
	// enumerationService.getEnumerationValue(ImageFailedActionType.class,
	// "ADD");
	// // String fileName = media.getLocation();
	// // if (fileName == null)
	// // return;
	//
	// String fileName = aliyunPath.substring(aliyunPath.lastIndexOf("/") + 1);
	//
	// String status = "0";
	// ImageFailedRecordModel failedRecord = acerChemImageFailedRecoredService
	// .getImageFailedRecordByFileAttr(fileName, "ADD");
	// if (failedRecord != null) {
	// int n = Integer.getInteger(failedRecord.getStatus());
	// n++;
	// status = String.valueOf(n);
	//
	// failedRecord.setAliyunUrl(aliyunPath);
	// failedRecord.setLocation(media.getLocation());
	// failedRecord.setStatus(status);
	// failedRecord.setMediaData(media);
	// failedRecord.setMediaPK(media.getPk().getLongValueAsString());
	//
	// } else {
	// failedRecord = modelService.create(ImageFailedRecordModel.class);
	// failedRecord.setFileName(fileName);
	// failedRecord.setActionType(actionType);
	// failedRecord.setAliyunUrl(aliyunPath);
	// failedRecord.setLocation(media.getLocation());
	// failedRecord.setStatus(status);
	// failedRecord.setMediaData(media);
	// failedRecord.setMediaPK(media.getPk().getLongValueAsString());
	//
	// }
	//
	// modelService.save(failedRecord);
	// } catch (Exception e) {
	// e.printStackTrace();
	// }
	//
	// }
}
