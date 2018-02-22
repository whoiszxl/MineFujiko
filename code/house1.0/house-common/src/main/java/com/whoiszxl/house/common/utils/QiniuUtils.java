package com.whoiszxl.house.common.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Map;

import org.apache.http.HttpException;
import org.apache.tomcat.util.http.fileupload.disk.DiskFileItem;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.google.common.collect.Maps;
import com.google.gson.Gson;
import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;

/**
 * 七牛云上传工具类
 * @author Administrator
 *
 */
@PropertySource("classpath:/application.properties")
public class QiniuUtils {

	@Value("${qiniu.accessKey}")
	private String accessKey = "JH0tS3sIln_xKbD730vrgH1rdpYqOj6E529EtNpb";
	
	@Value("${qiniu.secretKey}")
	private String secretKey = "9soYmzCGjn11QxeqQVWtdMCV2FMvb0OY1jhOesgo";
	
	@Value("${qiniu.bucket}")
	private String bucket = "zxlvoid";
	
	public Map<String, String> uploadToQiniu(MultipartFile file) {
		
		System.out.println("passssssssss:::"+secretKey);
		
		//构造一个带指定Zone对象的配置类
		Configuration cfg = new Configuration(Zone.zone2());
		//...其他参数参考类注释
		UploadManager uploadManager = new UploadManager(cfg);
		//...生成上传凭证，然后准备上传
		//默认不指定key的情况下，以文件内容的hash值作为文件名
		String key = null;
		try {
			
			FileInputStream fis = new FileInputStream(multipartToFile(file));

		    Auth auth = Auth.create(accessKey, secretKey);
		    String upToken = auth.uploadToken(bucket);
		    try {
		        Response response = uploadManager.put(fis,key,upToken,null, null);
		        //解析上传成功的结果
		        DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
		        
		        Map<String, String> result = Maps.newHashMap();
		        result.put("key", putRet.key);
		        result.put("value", putRet.hash);
		        return result;
		    } catch (QiniuException ex) {
		        Response r = ex.response;
		        System.err.println(r.toString());
		        try {
		            System.err.println(r.bodyString());
		        } catch (QiniuException ex2) {
		            //ignore
		        }
		    }
		} catch (UnsupportedEncodingException ex) {
		    //ignore
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
		
	}
	
	
	private File multipartToFile(MultipartFile multfile) throws IOException {  
		File f = null;
		try {
		    f=File.createTempFile("tmp", null);
		    multfile.transferTo(f);
		    f.deleteOnExit();
		} catch (IOException e) {
		    e.printStackTrace();
		}
		return f;
    }  
}
