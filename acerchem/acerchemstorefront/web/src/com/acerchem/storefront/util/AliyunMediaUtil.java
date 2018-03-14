package com.acerchem.storefront.util;

import java.net.URLEncoder;

//import java.io.BufferedReader;
//import java.io.File;
//import java.io.FileOutputStream;
//import java.io.IOException;
//import java.io.InputStream;
//import java.io.InputStreamReader;
//import java.io.OutputStreamWriter;
//import java.io.Writer;
//import java.net.URLEncoder;
//
//import com.aliyun.oss.ClientException;
//import com.aliyun.oss.OSS;
////import com.aliyun.oss.OSSClientBuilder;
//import com.aliyun.oss.OSSException;
//import com.aliyun.oss.model.Bucket;
//import com.aliyun.oss.model.CannedAccessControlList;
//import com.aliyun.oss.model.CreateBucketRequest;
//import com.aliyun.oss.model.ListBucketsRequest;
//import com.aliyun.oss.model.OSSObject;
//import com.aliyun.oss.model.OSSObjectSummary;
//import com.aliyun.oss.model.ObjectAcl;
//import com.aliyun.oss.model.ObjectListing;
//import com.aliyun.oss.model.PutObjectRequest;

public class AliyunMediaUtil {
	private static String endpoint = "<endpoint, http://oss-cn-hangzhou.aliyuncs.com>";
    private static String accessKeyId = "<accessKeyId>";
    private static String accessKeySecret = "<accessKeySecret>";
    private static String bucketName = "<bucketName>";
    private static String key = "<key>";

    /**
     * 上传到阿里云的图片文件
     */
	public static void uploadAliyunMedia(){
		//OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
		
		
	}
	
	
	
	public static void main(String[] args){
		try {
		  String urlString = URLEncoder.encode("无标题", "utf-8");  //输出%E6%97%A0%E6%A0%87%E9%A2%98.png
		                                                                //%E6%97%A0%E6%A0%87%E9%A2%98
		  System.out.println(urlString);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
