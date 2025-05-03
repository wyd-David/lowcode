package com.yabushan.system.service.impl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.ArrayList;
import java.util.Random;

import com.yabushan.common.core.domain.AjaxResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.yabushan.common.utils.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.yabushan.system.mapper.SysFilesMapper;
import com.yabushan.system.domain.SysFiles;
import com.yabushan.system.service.ISysFilesService;
import org.springframework.web.multipart.MultipartFile;

/**
 * 文件 Service业务层处理
 *
 * @author yabushan
 * @date 2022-05-16
 */
@Service
public class SysFilesServiceImpl implements ISysFilesService {
    private static final Logger log = LoggerFactory.getLogger(SysFilesServiceImpl.class);

    @Value("${yabushan.minio-bucket}")
    private String minioBucket;

    @Autowired(required = false)
    private SysFilesMapper sysFilesMapper;

    @Override
    public AjaxResult uploadFile(MultipartFile file) {
        // 封装文件对象
        SysFiles sysFiles = new SysFiles();
        String fileName = file.getOriginalFilename();
        sysFiles.setFileName(fileName);

        // todo 文件上传未完成


        // minioUtil.uploadFile(uploadfile.getInputStream(), bucket, objectName+"/"+uploadfile.getOriginalFilename());

        return null;
    }

    /**
     * @return 返回【当前年/当前月/当前日/时间戳_四位随机字符】
     */
    private String getDateDirAndRandomFileName() {
        StringBuilder sb = new StringBuilder();
        LocalDateTime dateTime = LocalDateTime.now(ZoneOffset.of("+8"));
        sb.append(dateTime.getYear());
        sb.append("/");
        sb.append(dateTime.getMonthValue());
        sb.append("/");
        sb.append(dateTime.getDayOfMonth());
        sb.append("/");
        sb.append(dateTime.toInstant(ZoneOffset.of("+8")).toEpochMilli());
        sb.append("_");
        String str = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        Random random = new Random();
        for (int i = 0; i < 5; i++) {
            int number = random.nextInt(62);
            sb.append(str.charAt(number));
        }
        return sb.toString();
    }

    public int insertFile(SysFiles file){
        return sysFilesMapper.insertSysFiles(file);
    }

    public SysFiles selectSysFilesById(String file){
        return sysFilesMapper.selectSysFilesById(file);
    }
    public List<SysFiles> selectSysFileListByIds(String[] fileIds){
        return sysFilesMapper.selectSysFileListByIds(fileIds);
    }

}
