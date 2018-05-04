package com.acerchem.core.web.aliyun;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.model.Bucket;
import com.aliyun.oss.model.CannedAccessControlList;
import com.aliyun.oss.model.CreateBucketRequest;
import com.aliyun.oss.model.ListBucketsRequest;
import com.aliyun.oss.model.OSSObject;
import com.aliyun.oss.model.OSSObjectSummary;
import com.aliyun.oss.model.ObjectAcl;
import com.aliyun.oss.model.ObjectListing;
import com.aliyun.oss.model.PutObjectRequest;

/**
 * 上传文件
 * 
 * @author jayson 2018年3月2日
 */
public class UploadFileDefault {
	private static String endpoint = "http://oss-us-east-1.aliyuncs.com";
	private static String accessKeyId = "LTAItFNuj9ju8BhI";
	private static String accessKeySecret = "GfyBU4iNfQftoUV20fHoYz2zNqJARy";
	private static String bucketName = "acerchem";
	private static String preffixKey = "application";
	private static String key = "<key>";

	// @Resource(name = "configurationService")
	// private ConfigurationService configurationService;

	// @PostConstruct
	// public void init() {
	// System.out.println("I'm init method using @PostConstrut....");
	// uploadFile = this;
	// uploadFile.configurationService = this.configurationService;
	//
	// initializeParameters();
	// }

	// 初始化配置数据
	public static void initializeParameters(String lsEndpoint, String lsAccessKeyId, String lsAccessKeySecret,
			String lsBucketName) {
		UploadFileDefault.endpoint = lsEndpoint;
		UploadFileDefault.accessKeyId = lsAccessKeyId;
		UploadFileDefault.accessKeySecret = lsAccessKeySecret;
		UploadFileDefault.bucketName = lsBucketName;

	}

	public static String getEndpoint() {
		return endpoint;
	}

	public static String getPreffixKey() {
		return preffixKey;
	}

	public static void setPreffixKey(String preffixKey) {
		UploadFileDefault.preffixKey = preffixKey;
	}

	public static String getKey() {
		return key;
	}

	public static void setKey(String key) {
		UploadFileDefault.key = key;
	}

	public static String getAccessKeyId() {
		return accessKeyId;
	}

	public static String getAccessKeySecret() {
		return accessKeySecret;
	}

	public static String getBucketName() {
		return bucketName;
	}

	public static OSSClient openClient() {
		OSSClient ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);
		return ossClient;
	}
	/**
	 * 上传流文件
	 * 
	 * @param input
	 * @throws IOException
	 */
	public static boolean uploadFile(InputStream input, String key,OSSClient  ossClient) throws IOException {

		//OSSClient ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);

		try {
			// 确立bucket
			CreateBucketExist(ossClient, bucketName);
			// 上传
			ossClient.putObject(bucketName, key, input);
		} catch (OSSException oe) {
			
//			ossClient.shutdown();
			return false;
		} catch (ClientException ce) {
//			ossClient.shutdown();
			return false;

//		} finally {
//			/*
//			 * Do not forget to shut down the client finally to release all
//			 * allocated resources.
//			 */
//			ossClient.shutdown();
		}

		return true;
	}

	// 上传本地文件
	public static boolean uploadFile(File local, String key,OSSClient client) throws IOException {

		//OSSClient ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);
		//OSSClient Client = ossClient;
		try {
			// 确立bucket
			CreateBucketExist(client, bucketName);
			// 上传
			client.putObject(bucketName, key, local);

		} catch (OSSException oe) {
//			ossClient.shutdown();
			return false;
		} catch (ClientException ce) {
//			ossClient.shutdown();
			return false;

//		} finally {
//			/*
//			 * Do not forget to shut down the client finally to release all
//			 * allocated resources.
//			 */
//			ossClient.shutdown();
		}

		return true;
	}

	public static void closeClient(OSSClient client){
		client.shutdown();
	}
	
	private static void CreateBucketExist(OSSClient ossClient, String buketName) {

		/*
		 * Determine whether the bucket exists
		 */
		if (!ossClient.doesBucketExist(bucketName)) {
			/*
			 * Create a new OSS bucket
			 */
			System.out.println("Creating bucket " + bucketName + "\n");
			ossClient.createBucket(bucketName);
			CreateBucketRequest createBucketRequest = new CreateBucketRequest(bucketName);
			createBucketRequest.setCannedACL(CannedAccessControlList.PublicRead);
			ossClient.createBucket(createBucketRequest);
		}

	}

	// sample
	public static void upload() throws IOException {
		/*
		 * Constructs a client instance with your account for accessing OSS
		 */
		OSSClient ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);

		System.out.println("Getting Started with OSS SDK for Java\n");

		try {

			/*
			 * Determine whether the bucket exists
			 */
			if (!ossClient.doesBucketExist(bucketName)) {
				/*
				 * Create a new OSS bucket
				 */
				System.out.println("Creating bucket " + bucketName + "\n");
				ossClient.createBucket(bucketName);
				CreateBucketRequest createBucketRequest = new CreateBucketRequest(bucketName);
				createBucketRequest.setCannedACL(CannedAccessControlList.PublicRead);
				ossClient.createBucket(createBucketRequest);
			}

			/*
			 * List the buckets in your account
			 */
			System.out.println("Listing buckets");

			ListBucketsRequest listBucketsRequest = new ListBucketsRequest();
			listBucketsRequest.setMaxKeys(500);

			for (Bucket bucket : ossClient.listBuckets()) {
				System.out.println(" - " + bucket.getName());
			}
			System.out.println();

			/*
			 * Upload an object to your bucket
			 */
			System.out.println("Uploading a new object to OSS from a file\n");
			ossClient.putObject(new PutObjectRequest(bucketName, key, createSampleFile()));

			/*
			 * Determine whether an object residents in your bucket
			 */
			boolean exists = ossClient.doesObjectExist(bucketName, key);
			System.out.println("Does object " + bucketName + " exist? " + exists + "\n");

			ossClient.setObjectAcl(bucketName, key, CannedAccessControlList.PublicRead);
			ossClient.setObjectAcl(bucketName, key, CannedAccessControlList.Default);

			ObjectAcl objectAcl = ossClient.getObjectAcl(bucketName, key);
			System.out.println("ACL:" + objectAcl.getPermission().toString());

			/*
			 * Download an object from your bucket
			 */
			System.out.println("Downloading an object");
			OSSObject object = ossClient.getObject(bucketName, key);
			System.out.println("Content-Type: " + object.getObjectMetadata().getContentType());
			displayTextInputStream(object.getObjectContent());

			/*
			 * List objects in your bucket by prefix
			 */
			System.out.println("Listing objects");
			ObjectListing objectListing = ossClient.listObjects(bucketName, "My");
			for (OSSObjectSummary objectSummary : objectListing.getObjectSummaries()) {
				System.out.println(" - " + objectSummary.getKey() + "  " + "(size = " + objectSummary.getSize() + ")");
			}
			System.out.println();

			/*
			 * Delete an object
			 */
			System.out.println("Deleting an object\n");
			ossClient.deleteObject(bucketName, key);

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
			ossClient.shutdown();
		}

	}

	private static File createSampleFile() throws IOException {
		File file = File.createTempFile("oss-java-sdk-", ".txt");
		file.deleteOnExit();

		Writer writer = new OutputStreamWriter(new FileOutputStream(file));
		writer.write("abcdefghijklmnopqrstuvwxyz\n");
		writer.write("0123456789011234567890\n");
		writer.close();

		return file;
	}

	private static void displayTextInputStream(InputStream input) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(input));
		while (true) {
			String line = reader.readLine();
			if (line == null)
				break;

			System.out.println("    " + line);
		}
		System.out.println();

		reader.close();
	}

	public static void main(String[] args) {

		SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
		String temp_ = df.format(new Date());
		System.out.println(temp_);

		
		initializeParameters("https://oss-us-east-1.aliyuncs.com","LTAIuleTEtEf4JnB","7WFdsSmV878UcAMUIlRfWF3RzJed2M","acerchem-r");
		
		String key = preffixKey + "/" + temp_ + "/" + UUID.randomUUID().toString().replaceAll("-", "");
		// 59ddcd8cN50a50637.jpg
		// 5a01a250N56caf7f6.jpg
		File file = new File("E:/company/image/59ddcd8cN50a50637.jpg");

		String suffixKey = file.getName().substring(file.getName().lastIndexOf(".") + 1);
		System.out.println(file.getAbsolutePath());
		// 计算下上传文件时间
		long startTime = System.currentTimeMillis();
		OSSClient client = UploadFileDefault.openClient();
		try {
			uploadFile(file, key + suffixKey,client);
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally{
			UploadFileDefault.closeClient(client);
		}

		long endTime = System.currentTimeMillis();
		float excTime = (float) (endTime - startTime) / 1000;
		System.out.println("执行时间：" + excTime + "s");
	}
}
