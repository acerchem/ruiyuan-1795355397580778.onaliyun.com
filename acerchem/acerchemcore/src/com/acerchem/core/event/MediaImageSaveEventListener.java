package com.acerchem.core.event;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;

import com.acerchem.core.image.service.AcerChemImageUploadLogService;
import com.acerchem.core.image.service.AcerChemMediaService;
import com.acerchem.core.model.ImageUploadedLogModel;
import com.acerchem.core.web.aliyun.UploadFileDefault;

import de.hybris.platform.core.PK;
import de.hybris.platform.core.model.media.MediaModel;
import de.hybris.platform.jalo.media.Media;
import de.hybris.platform.servicelayer.config.ConfigurationService;
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

	@Override
	public void afterSave(Collection<AfterSaveEvent> collection) {
		// TODO Auto-generated method stub
		for (final AfterSaveEvent event : collection) {
			final int type = event.getType();
			if (AfterSaveEvent.UPDATE == type) {
				final PK pk = event.getPk();
				// 30 is media
				if (30 == pk.getTypeCode()) {
					System.out.println("*****Media After save event is active!********");
					final MediaModel media = modelService.get(pk);
					if (media == null)
						return;
					// logic。。。。。。。。
					// when media is images
					String mediaPath = StringUtils.isNotBlank(media.getFolder().getPath()) ? media.getFolder().getPath()
							: "";
					String mediaType = media.getMime();
					mediaType = mediaType.substring(0, mediaType.indexOf("/"));
					if (mediaPath.equals("images") && mediaType.equals("image")) {
						final String ls = media.getLocation();

						//处理hmc发生两次图
						String _location = ls.substring(ls.indexOf("/")+1);
						
						if (StringUtils.isNotBlank(ls) && (_location.indexOf("/")>0)) {

							System.out.println("**********upload image to aliyun start*********");
							uploadImageSendProcessor(media);

						} else {
							System.out.println("Media's Location is null!");
						}
					}
				}
			}
		}

	}

	private void uploadImageSendProcessor(MediaModel media) {

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

			//aliyun relation path>>> application/yyyymmdd/
			String key = configurationService.getConfiguration().getString("aliyun.preffixKey") + "/" + temp_ + "/"
					+ media.getPk().getLong().toString() + "." + keySuffix;
			//初始化upload参数
			String lsEndpoint = configurationService.getConfiguration().getString("aliyun.endpoint");
			String lsAccessKeyId = configurationService.getConfiguration().getString("aliyun.accessKeyId");
			String lsAccessKeySecret = configurationService.getConfiguration().getString("aliyun.accessKeySecret");
			String lsBucketName = configurationService.getConfiguration().getString("aliyun.bucketName");
			
			UploadFileDefault.initializeParameters(lsEndpoint, lsAccessKeyId, lsAccessKeySecret, lsBucketName);
			// upload aliyun
			boolean uploadFlag = UploadFileDefault.uploadFile(file, key);

			if (uploadFlag) {
				System.out.println("****upload end>>>>synsave to server start*****");
				// save aliyunUrl to ImageUploadedLog
				String aliyunUrl = configurationService.getConfiguration().getString("aliyun.domain") + "/" + key;

				ImageUploadedLogModel iulModel = acerChemImageUploadLogService
						.getImageUploadedLog(media.getPk().getLong().toString());
				if (iulModel == null) {
					iulModel = modelService.create(ImageUploadedLogModel.class);
				}
				iulModel.setAliyunUrl(aliyunUrl);
				iulModel.setImagePK(media.getPk().getLong().toString());

				modelService.save(iulModel);
				System.out.println("****synsave to server end*****");
			} else {
				uploadFailedProccess();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	// 上传失败处理
	private void uploadFailedProccess() {
		
		
		
	}

}
