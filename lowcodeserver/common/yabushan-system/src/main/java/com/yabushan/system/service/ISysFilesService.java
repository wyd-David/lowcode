package com.yabushan.system.service;

import java.util.List;

import com.yabushan.common.core.domain.AjaxResult;
import com.yabushan.system.domain.SysFiles;
import org.springframework.web.multipart.MultipartFile;

/**
 * 文件 Service接口
 *
 * @author yabushan
 * @date 2022-05-16
 */
public interface ISysFilesService
{

    /**
     * 文件上传：包括文件上传到 minio 文件服务器、向表中添加文件信息
     */
    AjaxResult uploadFile(MultipartFile file);


    int insertFile(SysFiles file);

    SysFiles selectSysFilesById(String file);

    List<SysFiles> selectSysFileListByIds(String[] fileIds);

}
