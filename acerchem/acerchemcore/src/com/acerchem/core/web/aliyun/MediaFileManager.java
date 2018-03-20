package com.acerchem.core.web.aliyun;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.model.DeleteObjectsRequest;
import com.aliyun.oss.model.DeleteObjectsResult;
import com.aliyun.oss.model.GetObjectRequest;

import de.hybris.platform.servicelayer.config.ConfigurationService;

/**
 * 上传阿里云的文件管理器
 * 
 * @author jayson
 *
 * @Date 2018.3.2
 */
public class MediaFileManager {

	@Resource(name = "configurationService")
	private ConfigurationService configurationService;

	private static String endpoint = "http://oss-us-east-1.aliyuncs.com";
	private static String accessKeyId = "LTAItFNuj9ju8BhI";
	private static String accessKeySecret = "GfyBU4iNfQftoUV20fHoYz2zNqJARy";
	private static String bucketName = "acerchem";

	// 初始化配置数据
	public static void initializeParameters(String lsEndpoint, String lsAccessKeyId, String lsAccessKeySecret,
			String lsBucketName) {
		MediaFileManager.endpoint = lsEndpoint;
		MediaFileManager.accessKeyId = lsAccessKeyId;
		MediaFileManager.accessKeySecret = lsAccessKeySecret;
		MediaFileManager.bucketName = lsBucketName;

	}

	// 缩放
	public static void resizeMedia(String key, String resizeKey, String w_size, String h_size) throws IOException {
		OSSClient ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);

		try {

			// String style = "image/resize,m_fixed,w_100,h_100";
			String size_w = "w_" + w_size;
			String size_h = "h_" + h_size;
			String style = "image/resize,m_fixed," + size_w + "," + size_h;

			// GetObjectRequest request = new GetObjectRequest(bucketName, key);

			GetObjectRequest request = new GetObjectRequest(bucketName, key);

			request.setProcess(style);

			// ossClient.getObject(request, new File("example-resize.jpg"));

			ossClient.getObject(request, new File(resizeKey));

		} catch (OSSException oe) {
			System.out.println("Caught an OSSException, which means your request made it to OSS, "
					+ "but was rejected with an error response for some reason.");
			System.out.println("Error Message: " + oe.getErrorCode());
			System.out.println("Error Code:       " + oe.getErrorCode());
			System.out.println("Request ID:      " + oe.getRequestId());
			System.out.println("Host ID:           " + oe.getHostId());
		} catch (ClientException ce) {
			System.out.println("Caught an ClientException, which means the client encountered "
					+ "a serious internal problem while trying to communicate with OSS, "
					+ "such as not being able to access the network.");
			System.out.println("Error Message: " + ce.getMessage());
		} catch (Throwable e) {
			e.printStackTrace();
		} finally {
			ossClient.shutdown();
		}
	}

	// 删除文件,采用简单模式，返回删除失败的文件列表
	public static List<String> deleteMediaFiles(List<String> keys) throws IOException {

		OSSClient client = new OSSClient(endpoint, accessKeyId, accessKeySecret);

		List<String> deletedObjects = new ArrayList<String>();
		try {
			System.out.println("\nDeleting all objects:");
			DeleteObjectsResult deleteObjectsResult = client
					.deleteObjects(new DeleteObjectsRequest(bucketName).withKeys(keys).withQuiet(true));
			deletedObjects = deleteObjectsResult.getDeletedObjects();
		} catch (OSSException oe) {
			System.out.println("Caught an OSSException, which means your request made it to OSS, "
					+ "but was rejected with an error response for some reason.");
			System.out.println("Error Message: " + oe.getErrorCode());
			System.out.println("Error Code:       " + oe.getErrorCode());
			System.out.println("Request ID:      " + oe.getRequestId());
			System.out.println("Host ID:           " + oe.getHostId());
		} catch (ClientException ce) {
			System.out.println("Caught an ClientException, which means the client encountered "
					+ "a serious internal problem while trying to communicate with OSS, "
					+ "such as not being able to access the network.");
			System.out.println("Error Message: " + ce.getMessage());
		} finally {
			/*
			 * Do not forget to shut down the client finally to release all
			 * allocated resources.
			 */
			client.shutdown();
		}

		return deletedObjects;
	}

	public static void main(String[] args) {
		/*
		 * List<String> keys = new ArrayList<String>();
		 * keys.add("application/20180313/6bd0795e9b004f59aa85cbf0db6e4859jpg");
		 * keys.add("application/20180313/f2");
		 * keys.add("application/20180313/f3"); try { List<String> results =
		 * deleteMediaFiles(keys);
		 * 
		 * if (results.size() > 0) { for(String s : results){
		 * System.out.println(s); } } }catch(Exception e){ e.printStackTrace();
		 * }
		 */

		System.out.println("eee:" );

	}

}
