package com.yabushan.system.utils;

import com.amazonaws.services.s3.model.PutObjectResult;
import com.yabushan.system.service.IS3Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.Date;

/**
 * Excel图片S3文件上传工具类
 *
 * @author 姜森焱
 */
@Component
public class FileS3UploadUtils {
    @Autowired
    private  IS3Service is3Service;


    public static String bucketname;

    @Value("${s3.bucketname}")
    public void setBucketname(String bucketname) {
        FileS3UploadUtils.bucketname = bucketname;
    }

    /**
     * s3Excel导入图片通用上传请求
     * businessKey :属于那个业务
     */
    public  String uploadExcelImageFile(File file, String businessKey) {
        try {
            Date date = new Date();
            //拼接s3的文件key
            String key = businessKey + "_" + date.getTime();
            PutObjectResult putObjectResult = is3Service.putObject(bucketname, key, file);
            return key;
        } catch (Exception e) {
            //System.out.println(e.getMessage());
        }
        return null;
    }
}
