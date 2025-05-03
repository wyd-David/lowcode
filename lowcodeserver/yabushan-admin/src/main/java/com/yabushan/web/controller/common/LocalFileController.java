package com.yabushan.web.controller.common;

import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.yabushan.common.config.RuoYiConfig;
import com.yabushan.common.constant.Constants;
import com.yabushan.common.core.domain.AjaxResult;
import com.yabushan.common.utils.QRCodeGeneratorUtils;
import com.yabushan.common.utils.StringUtils;
import com.yabushan.common.utils.file.FileUploadUtils;
import com.yabushan.common.utils.file.FileUtils;
import com.yabushan.framework.config.ServerConfig;
import com.yabushan.system.domain.ImportFileInfo;
import com.yabushan.system.service.IImportFileInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
@Api(tags = "服务器文件上传下载接口")
@RequestMapping("/localFile")
@RestController
public class LocalFileController {

    private static final Logger log = LoggerFactory.getLogger(LocalFileController.class);
    @Autowired
    private ServerConfig serverConfig;

    @Autowired
    private IImportFileInfoService iImportFileInfoService;

    private static final String FILE_DELIMETER = ",";

    /**
     * 生成二维码
     *
     * @param content 二维码的跳转地址,内容
     */
    @GetMapping(value = "/common/activity-code")
    @ApiOperation("生成二维码IO流")
    public void getCode(String content, HttpServletResponse response) throws IOException, WriterException {
        // 设置响应流信息
        response.setContentType("image/jpg");
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);

        OutputStream stream = response.getOutputStream();
        //获取一个二维码图片
        BitMatrix bitMatrix = QRCodeGeneratorUtils.generateQRCodeImageIO(content);
        //以流的形式输出到前端
        MatrixToImageWriter.writeToStream(bitMatrix, "jpg", stream);
    }

    /**
     * 本地资源通用下载
     */
    @GetMapping("/common/download/resource")
    @ApiOperation(value = "本地资源通用下载")
    public void commonresourceDownload(String name, HttpServletRequest request, HttpServletResponse response) throws Exception {
        // 本地资源路径
        String localPath = RuoYiConfig.getProfile();
        // 数据库资源地址
        String downloadPath = localPath + StringUtils.substringAfter(name, Constants.RESOURCE_PREFIX);
        // 下载名称
        String downloadName = StringUtils.substringAfterLast(downloadPath, "/");
        response.setCharacterEncoding("utf-8");
        response.setContentType("multipart/form-data");
        response.setHeader("Content-Disposition",
                "attachment;fileName=" + FileUtils.setFileDownloadHeader(request, downloadName));
        FileUtils.writeBytes(downloadPath, response.getOutputStream());
    }


    /**
     * 通用本地下载请求
     *
     * @param fileName 文件名称
     * @param delete 是否删除
     */
    @GetMapping("/local/download")
    @ApiOperation(value = "本地资源通用下载,同时是否删除服务器文件")
    public void fileDownload(String fileName, Boolean delete, HttpServletResponse response, HttpServletRequest request)
    {
        try
        {
            if (!FileUtils.checkAllowDownload(fileName))
            {
                throw new Exception(StringUtils.format("文件名称({})非法，不允许下载。 ", fileName));
            }
            String realFileName = System.currentTimeMillis() + fileName.substring(fileName.indexOf("_") + 1);
            String filePath = RuoYiConfig.getDownloadPath() + fileName;

            response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
            FileUtils.setAttachmentResponseHeader(response, realFileName);
            FileUtils.writeBytes(filePath, response.getOutputStream());
            if (delete)
            {
                FileUtils.deleteFile(filePath);
            }
        }
        catch (Exception e)
        {
            log.error("下载文件失败", e);
        }
    }

    /**
     * 通用本地上传请求（单个）
     */
    @PostMapping("/local/upload")
    @ResponseBody
    @ApiOperation(value = "单个文件上传")
    public AjaxResult uploadFile(MultipartFile file) throws Exception
    {
        try
        {
            // 上传文件路径
            String filePath = RuoYiConfig.getUploadPath();
            // 上传并返回新文件名称
            String fileName = FileUploadUtils.upload(filePath, file);
            String url = serverConfig.getUrl() + fileName;
            AjaxResult ajax = AjaxResult.success();
            ajax.put("url", url);
            ajax.put("fileName", fileName);
            ajax.put("newFileName", FileUtils.getName(fileName));
            ajax.put("originalFilename", file.getOriginalFilename());
            ajax.put("name", file.getOriginalFilename());

            //插入文件记录
            ImportFileInfo importFileInfo = new ImportFileInfo();
            importFileInfo.setBusinessKey(url);
            importFileInfo.setFileName( fileName);
            //插入压缩原图Key
            importFileInfo.setFileKey(null);
//                    //插入的文件Key（暂时不需要原图）
//                    importFileInfo.setCompressFileKey(keynew);
            importFileInfo.setFileSize(String.valueOf(file.getSize()));
            importFileInfo.setApplyId("local");
            iImportFileInfoService.insertImportFileInfo(importFileInfo);
            return ajax;
        }
        catch (Exception e)
        {
            return AjaxResult.error(e.getMessage());
        }
    }

    /**
     * 通用本地上传请求（多个）
     */
    @PostMapping("/local/uploads")
    @ResponseBody
    @ApiOperation(value = "多个文件上传")
    public AjaxResult uploadFiles(List<MultipartFile> files) throws Exception
    {
        try
        {
            // 上传文件路径
            String filePath = RuoYiConfig.getUploadPath();
            List<String> urls = new ArrayList<String>();
            List<String> fileNames = new ArrayList<String>();
            List<String> newFileNames = new ArrayList<String>();
            List<String> originalFilenames = new ArrayList<String>();
            for (MultipartFile file : files)
            {
                // 上传并返回新文件名称
                String fileName = FileUploadUtils.upload(filePath, file);
                String url = serverConfig.getUrl() + fileName;
                urls.add(url);
                fileNames.add(fileName);
                newFileNames.add(FileUtils.getName(fileName));
                originalFilenames.add(file.getOriginalFilename());

                //插入文件记录
                ImportFileInfo importFileInfo = new ImportFileInfo();
                importFileInfo.setBusinessKey(StringUtils.join(urls, FILE_DELIMETER));
                importFileInfo.setFileName( StringUtils.join(fileNames, FILE_DELIMETER));
                //插入压缩原图Key
                importFileInfo.setFileKey(null);
//                    //插入的文件Key（暂时不需要原图）
//                    importFileInfo.setCompressFileKey(keynew);
                importFileInfo.setFileSize(String.valueOf(file.getSize()));
                importFileInfo.setApplyId("local");
                iImportFileInfoService.insertImportFileInfo(importFileInfo);
                
                
            }
            AjaxResult ajax = AjaxResult.success();
            ajax.put("urls", StringUtils.join(urls, FILE_DELIMETER));
            ajax.put("fileNames", StringUtils.join(fileNames, FILE_DELIMETER));
            ajax.put("newFileNames", StringUtils.join(newFileNames, FILE_DELIMETER));
            ajax.put("originalFilenames", StringUtils.join(originalFilenames, FILE_DELIMETER));
            return ajax;
        }
        catch (Exception e)
        {
            return AjaxResult.error(e.getMessage());
        }
    }

    /**
     * 本地资源通用下载
     */
    @GetMapping("/local/download/resource")
    @ApiOperation(value = "本地资源通用下载")
    public void resourceDownload(String resource, HttpServletRequest request, HttpServletResponse response)
            throws Exception
    {
        try
        {
            if (!FileUtils.checkAllowDownload(resource))
            {
                throw new Exception(StringUtils.format("资源文件({})非法，不允许下载。 ", resource));
            }
            // 本地资源路径
            String localPath = RuoYiConfig.getProfile();
            // 数据库资源地址
            String downloadPath = localPath + StringUtils.substringAfter(resource, Constants.RESOURCE_PREFIX);
            // 下载名称
            String downloadName = StringUtils.substringAfterLast(downloadPath, "/");
            response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
            FileUtils.setAttachmentResponseHeader(response, downloadName);
            FileUtils.writeBytes(downloadPath, response.getOutputStream());
        }
        catch (Exception e)
        {
            log.error("下载文件失败", e);
        }
    }

}


