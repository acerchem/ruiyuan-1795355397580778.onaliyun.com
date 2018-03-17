package com.acerchem.core.web.aliyun;

import java.io.IOException;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.model.CompleteMultipartUploadResult;
import com.aliyun.oss.model.UploadFileRequest;
import com.aliyun.oss.model.UploadFileResult;

import de.hybris.platform.servicelayer.config.ConfigurationService;

/**
 * 断点续传文件
 * @author jayson
 * 2018年3月2日
 */
public class UploadFile {

	private static String endpoint = "http://oss-us-east-1.aliyuncs.com";
    private static String accessKeyId = "LTAItFNuj9ju8BhI";
    private static String accessKeySecret = "GfyBU4iNfQftoUV20fHoYz2zNqJARy";
    private static String bucketName = "acerchem";
    private static String preffixKey = "application";
    
   // private static String key = "<downloadKey>";
   // private static String uploadFile = "<uploadFile>";
    
    private static UploadFile uploadFile;
    
    @Resource(name = "configurationService")
	private  ConfigurationService configurationService;
    @PostConstruct
	 public void init() {  
        System.out.println("I'm  init  method  using  @PostConstrut....");    
        uploadFile = this;  
        uploadFile.configurationService = this.configurationService;  
        
        initializeParameters();
  }  
    
    //初始化配置数据
   	private void initializeParameters(){
   		
   	}
    
    public static void uploadContinue(String downloadKey,String uploadFile)  throws IOException{
    	OSSClient ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);
        
        try {
            UploadFileRequest uploadFileRequest = new UploadFileRequest(bucketName, downloadKey);
            // 待上传的本地文件
            uploadFileRequest.setUploadFile(uploadFile);
            // 设置并发下载数，默认1
            uploadFileRequest.setTaskNum(5);
            // 设置分片大小，默认100KB
            uploadFileRequest.setPartSize(1024 * 1024 * 1);
            // 开启断点续传，默认关闭
            uploadFileRequest.setEnableCheckpoint(true);
            
            UploadFileResult uploadResult = ossClient.uploadFile(uploadFileRequest);
            
            CompleteMultipartUploadResult multipartUploadResult = 
                    uploadResult.getMultipartUploadResult();
            System.out.println(multipartUploadResult.getETag());
            
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
}
