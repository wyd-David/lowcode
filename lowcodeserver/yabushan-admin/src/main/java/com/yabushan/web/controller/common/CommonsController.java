package com.yabushan.web.controller.common;


import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.s3.model.PutObjectResult;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.yabushan.common.config.RuoYiConfig;
import com.yabushan.common.constant.Constants;
import com.yabushan.common.core.domain.AjaxResult;
import com.yabushan.common.core.redis.RedisCache;
import com.yabushan.common.utils.QRCodeGeneratorUtils;
import com.yabushan.common.utils.StringUtils;
import com.yabushan.common.utils.file.FileUploadUtils;
import com.yabushan.common.utils.file.FileUtils;
import com.yabushan.framework.config.ServerConfig;
import com.yabushan.framework.web.service.TokenService;
import com.yabushan.system.domain.ImportFileInfo;
import com.yabushan.system.service.IImportFileInfoService;
import com.yabushan.system.service.IS3Service;
import com.yabushan.system.service.ISysConfigService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.coobird.thumbnailator.Thumbnails;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Base64Utils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * 通用请求处理
 *
 * @author ruoyi
 */
@RestController
@Api(tags = "文件上传下载接口")
public class CommonsController {
    private static final Logger log = LoggerFactory.getLogger(CommonController.class);

    @Autowired
    private ServerConfig serverConfig;
    @Autowired
    private ISysConfigService configService;
    @Autowired
    private IImportFileInfoService iImportFileInfoService;
    @Autowired
    private IS3Service is3Service;
    @Autowired
    private TokenService tokenService;

    @Autowired
    private RedisCache redisCache;

    @Value("${s3.bucketname}")
    public String bucketname;

    private static final String FILE_DELIMETER = ",";

    /**
     * 通用下载请求
     *
     * @param fileName 文件名称
     */
    @GetMapping("common/download")
    @ApiOperation(value = "通用下载请求")
    public void fileS3Download(String fileName, String key, Boolean delete, HttpServletResponse response, HttpServletRequest request) {
        //判断是否存在filekey,存在走s3
        if (StringUtils.isNotNull(key) && !key.equals("")) {
            BufferedInputStream in_stream = null;
            try {
                S3Object s3Object = is3Service.getObject(bucketname, key);

                if (!FileUtils.isValidFilename(fileName)) {
                    throw new Exception(StringUtils.format("文件名称({})非法，不允许下载。 ", fileName));
                }
                in_stream = new BufferedInputStream(s3Object.getObjectContent());
                response.setCharacterEncoding("utf-8");
                response.setContentType(s3Object.getObjectMetadata().getContentType());
                response.setHeader("Content-Disposition",
                        "attachment;fileName=" + fileName);
                FileUtils.writeBuffBytes(in_stream, response.getOutputStream());
            } catch (Exception e) {
                log.error("下载文件失败", e);
            } finally {
                if (in_stream != null) {
                    try {
                        in_stream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        } else {
            try {
                if (!FileUtils.isValidFilename(fileName)) {
                    throw new Exception(StringUtils.format("文件名称({})非法，不允许下载。 ", fileName));
                }
//                String realFileName = System.currentTimeMillis() + fileName.substring(fileName.indexOf("_") + 1);
                String filePath = RuoYiConfig.getDownloadPath() + fileName;
                response.setCharacterEncoding("utf-8");
                response.setContentType("multipart/form-data");
                response.setHeader("Content-Disposition",
                        "attachment;fileName=" + FileUtils.setFileDownloadHeader(request, fileName));
                FileUtils.writeBytes(filePath, response.getOutputStream());
                if (delete) {
                    FileUtils.deleteFile(filePath);
                }
            } catch (Exception e) {
                log.error("下载文件失败", e);
            }
        }
    }

    @PostMapping("/common/testS3ooooo")
    @ApiOperation(value = "S3测试删除数据")
    public AjaxResult testS3ooooo() {
        ObjectListing objectListing = is3Service.listObjects(bucketname);
        List<S3ObjectSummary> objectSummaries = objectListing.getObjectSummaries();
        for (int i = 0; i < objectSummaries.size(); i++) {
            if (objectSummaries.get(i).getKey().contains("Card_")) {//删除一卡通的
                System.out.println(objectSummaries.get(i).getKey());
                is3Service.deleteObject(bucketname, objectSummaries.get(i).getKey());
            }
            if (objectSummaries.get(i).getKey().contains(".xlsx")) {//删除等于这个后缀的key
                System.out.println(objectSummaries.get(i).getKey());
                is3Service.deleteObject(bucketname, objectSummaries.get(i).getKey());
            }
            if (objectSummaries.get(i).getKey().contains(".txt")) {//删除等于这个后缀的key
                System.out.println(objectSummaries.get(i).getKey());
                is3Service.deleteObject(bucketname, objectSummaries.get(i).getKey());
            }
            if (objectSummaries.get(i).getKey().contains(".gif")) {//删除等于这个后缀的key
                System.out.println(objectSummaries.get(i).getKey());
                is3Service.deleteObject(bucketname, objectSummaries.get(i).getKey());
            }
            if (objectSummaries.get(i).getKey().contains(".gif")) {//删除等于这个后缀的key
                System.out.println(objectSummaries.get(i).getKey());
                is3Service.deleteObject(bucketname, objectSummaries.get(i).getKey());
            }
//            if(objectSummaries.get(i).getKey().contains("mat_")){//删除物资图片
//                System.out.println(objectSummaries.get(i).getKey());
//                is3Service.deleteObject(bucketname,objectSummaries.get(i).getKey());
//            }
            if (objectSummaries.get(i).getKey().contains("SECURITY_")) {//删除安全服务图片
                System.out.println(objectSummaries.get(i).getKey());
                is3Service.deleteObject(bucketname, objectSummaries.get(i).getKey());
            }
            if (objectSummaries.get(i).getKey().contains("repair_")) {//删除报修服务
                System.out.println(objectSummaries.get(i).getKey());
                is3Service.deleteObject(bucketname, objectSummaries.get(i).getKey());
            }
            if (objectSummaries.get(i).getKey().contains("Info_")) {//删除信息发布服务
                System.out.println(objectSummaries.get(i).getKey());
                is3Service.deleteObject(bucketname, objectSummaries.get(i).getKey());
            }
            if (objectSummaries.get(i).getKey().contains("overTime_")) {//删除加班服务
                System.out.println(objectSummaries.get(i).getKey());
                is3Service.deleteObject(bucketname, objectSummaries.get(i).getKey());
            }
        }
        return AjaxResult.success("成功");
    }

    /**
     * 单个文件通用上传请求
     * businessKey :属于那个业务
     */
    @Transactional
    @PostMapping("/common/upload")
    @ApiOperation(value = "通用上传请求")
    public AjaxResult uploadFile(MultipartFile file, String businessKey) {
        AjaxResult ajax = AjaxResult.success();
        if (StringUtils.isNotNull(businessKey) && !businessKey.equals("")) {
            try {
                Date date = new Date();
                //拼接s3的文件key
                String key = businessKey + "_" + date.getTime() + new Random().nextInt(99999);
                //MultipartFile转IO FILE
                File fileio = multipartFileToFile(file);
                //压缩图暂存
                File yasuofileio = new File(file.getOriginalFilename());
                //判断是否是图片，进行压缩
                if (file.getContentType().startsWith("image/")) {
                    //  String sys_image_ys = configService.selectConfigByKey("sys_image_ys");
                    // if(sys_image_ys != null && sys_image_ys.equals("1")){
                    //上传原来的图片
                    PutObjectResult putObjectResult = is3Service.putObject(bucketname, key+"_HD", fileio);
                    //这段代码是压缩图片的代码
                    Thumbnails.of(fileio)
                            .scale(0.3f) //图片大小（长宽）压缩比例 从0-1，1表示原图
                            .outputQuality(0.3f) //图片质量压缩比例 从0-1，越接近1质量越好
                            .toFile(yasuofileio);
                    // }
                }
//                //压缩图片后的新文件Key
//                String keynew = businessKey + "_" + date.getTime() + new Random().nextInt(99999);
//                    //上传压缩后的图片
                //如果S3桶不存在则创建桶
                if(!is3Service.doesBucketExist(bucketname)){
                    is3Service.createBucket(bucketname);
                }
                PutObjectResult putObjectResultCompress = is3Service.putObject(bucketname, key, yasuofileio);
                //删除原来的图片
                if(null!=fileio){
                    if (!fileio.delete()) {
                        log.info("删除成功！");
                    }
                }
                //删除压缩图片
                if(null!=yasuofileio){
                    if (!yasuofileio.delete()) {
                        log.info("删除成功！");
                        // file delete failed; take appropriate action
                    }
                }
                //插入文件记录
                ImportFileInfo importFileInfo = new ImportFileInfo();
                importFileInfo.setBusinessKey(businessKey);
                importFileInfo.setFileName(file.getOriginalFilename());
                //插入压缩原图Key
                importFileInfo.setFileKey(key);
//                    //插入的文件Key（暂时不需要原图）
//                    importFileInfo.setCompressFileKey(keynew);
                importFileInfo.setFileSize(String.valueOf(file.getSize()));
                importFileInfo.setApplyId("公共文件上传");
                iImportFileInfoService.insertImportFileInfo(importFileInfo);
                log.info(file.getOriginalFilename());
                //返回给前端的是压缩后的图片key
                ajax.put("fileName", file.getOriginalFilename());
                ajax.put("fileKey", key);
                ajax.put("fileSize", file.getSize());

                return ajax;
            } catch (Exception e) {
                log.error(e.getMessage());
                throw new RuntimeException("公共上传文件失败！事务回滚！");
            }
        } else {
            return AjaxResult.error("上传失败");
        }
    }

    /**
     * 单个图片文件通用上传请求(图片压缩)
     * businessKey :属于那个业务
     */
    @Transactional
    @PostMapping("/common/upload/Img")
    @ApiOperation(value = "通用上传请求")
    public AjaxResult uploadFileImg(MultipartFile file, String businessKey) {
        AjaxResult ajax = AjaxResult.success();
        if (StringUtils.isNotNull(businessKey) && !businessKey.equals("")) {
            try {
                Date date = new Date();
                //拼接s3的文件key
                String key = businessKey + "_" + date.getTime() + new Random().nextInt(99999);

                File fileio = multipartFileToFile(file);
                //MultipartFile转IO FILE
                if (file.getContentType().startsWith("image/")) {
                    //上传原来的图片
                    PutObjectResult putObjectResult = is3Service.putObject(bucketname, key, fileio);
                    File yasuofileio = new File(file.getOriginalFilename());
                    Thumbnails.of(fileio)
                            .scale(0.3f) //图片大小（长宽）压缩比例 从0-1，1表示原图
                            .outputQuality(0.3f) //图片质量压缩比例 从0-1，越接近1质量越好
                            .toFile(yasuofileio);
                    //压缩图片后的新文件Key
                    String keynew = businessKey + "_" + date.getTime() + new Random().nextInt(99999);
//                    //上传压缩后的图片
                    PutObjectResult putObjectResultCompress = is3Service.putObject(bucketname, keynew, yasuofileio);
                    //删除原来的图片
                    if(null!=fileio) {
                        if (!fileio.delete()) {
                            log.info("删除成功！");
                            // file delete failed; take appropriate action
                        }
                    }
                    //删除压缩图片
                    if(null!=yasuofileio){
                        if (!yasuofileio.delete()) {
                            log.info("删除成功！");
                            // file delete failed; take appropriate action
                        }
                    }
                    //插入文件记录
                    ImportFileInfo importFileInfo = new ImportFileInfo();
                    importFileInfo.setBusinessKey(businessKey);
                    importFileInfo.setFileName(file.getOriginalFilename());
                    //插入原图Key
                    importFileInfo.setFileKey(key);
                    //插入压缩的文件Key
                    importFileInfo.setCompressFileKey(keynew);
                    importFileInfo.setFileSize(String.valueOf(file.getSize()));
                    importFileInfo.setApplyId("公共文件上传");
                    iImportFileInfoService.insertImportFileInfo(importFileInfo);
                    log.info(file.getOriginalFilename());
                    //返回给前端的是压缩后的图片key
                    ajax.put("fileName", file.getOriginalFilename());
                    ajax.put("fileKey", keynew);
                    ajax.put("fileSize", file.getSize());
                } else {
                    return AjaxResult.error("上传不是图片格式，请换图片格式！");
                }
                return ajax;
            } catch (Exception e) {
                log.error(e.getMessage());
                throw new RuntimeException("公共上传文件失败！事务回滚！");
            }
        } else {
            return AjaxResult.error("上传失败");
        }
    }

    /**
     * MultipartFile 转 File
     *
     * @param file
     * @throws Exception
     */
    public static File multipartFileToFile(MultipartFile file) throws Exception {

        File toFile = null;
        if (file == null || file.equals("") || file.getSize() <= 0) {
            file = null;
        } else {
            InputStream ins = null;
            ins = file.getInputStream();
            toFile = new File(file.getOriginalFilename());
            inputStreamToFile(ins, toFile);
            ins.close();
        }
        return toFile;
    }

    //获取流文件
    private static void inputStreamToFile(InputStream ins, File file) throws Exception {
        OutputStream os = null;
        try {
            //OutputStream os = new FileOutputStream(file);
            os = new FileOutputStream(file);
            int bytesRead = 0;
            byte[] buffer = new byte[8192];
            while ((bytesRead = ins.read(buffer, 0, 8192)) != -1) {
                os.write(buffer, 0, bytesRead);
            }
            //os.close();
            ins.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (os != null) {
                os.close();
            }
        }
    }

    /**
     * 多个文件通用上传请求
     * businessKey :属于那个业务
     */
    @PostMapping("/common/uploadList")
    @ApiOperation(value = "通用上传请求")
    public AjaxResult uploadFile(MultipartFile file[], String businessKey) {
        AjaxResult ajax = AjaxResult.success();
        if (StringUtils.isNull(businessKey) || businessKey.equals("")) {
            return AjaxResult.error("请传入业务简称");
        }
        try {

            //存放返回前端的文件集合
            List<Map<String, Object>> map = new ArrayList<>();
            //遍历文件集合来上传到s3
            for (int i = 0; i < file.length; i++) {
                //存放当前遍历文件对象
                Map<String, Object> remap = new HashMap<>();

                MultipartFile fileinfo = file[i];
                Date date = new Date();
                //拼接s3的文件key
                String key = businessKey + "_" + date.getTime();
                //MultipartFile转IO FILE
                File fileio = multipartFileToFile(fileinfo);
                //进行s3文件上传
                PutObjectResult putObjectResult = is3Service.putObject(bucketname, key, fileio);
                remap.put("fileName", fileinfo.getOriginalFilename());
                remap.put("fileKey", key);
                remap.put("fileSize", fileinfo.getSize());
                map.add(remap);
            }
            ajax.put("fileList", map);
            return ajax;
        } catch (Exception e) {
            log.error(e.getMessage());
            return AjaxResult.error(e.getMessage());
        }

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
     * 根据压缩过后的文件Key回显给前端进行图片放大
     */
    @GetMapping("/common/enlargeFileImg")
    @ApiOperation(value = "图片放大")
    public void enlargeFileImg(String filekey, HttpServletResponse response) {
        BufferedInputStream inputStream = null;
        ImportFileInfo importFileInfo = new ImportFileInfo();
        importFileInfo.setCompressFileKey(filekey);
        try {
            //查找库里面的表信息
            List<ImportFileInfo> importFileInfos = iImportFileInfoService.selectImportFileInfoList(importFileInfo);
            if (importFileInfos != null && importFileInfos.size() > 0) {
                //取值S3
                S3Object s3Object = is3Service.getObject(bucketname, importFileInfos.get(0).getFileKey());
                inputStream = new BufferedInputStream(s3Object.getObjectContent());
                // 设置响应流信息
                response.setContentType(s3Object.getObjectMetadata().getContentType());
                response.setCharacterEncoding("utf-8");
                response.setHeader("Pragma", "no-cache");
                response.setHeader("Cache-Control", "no-cache");
                response.setDateHeader("Expires", 0);
                FileUtils.writeBuffBytes(inputStream, response.getOutputStream());
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
    }

    /**
     * 图片回显前端
     */
    @GetMapping(value = "/gfxz/s3Image-Echo")
    @ApiOperation("图片回显前端")
    public void getCode(String fileName, String fileKey, HttpServletResponse response, String zipFileName) throws IOException {
        BufferedInputStream in_stream = null;
        BufferedInputStream inputStream = null;
        BufferedInputStream bufferedInputStream = null;
        try {
            S3Object s3Object = null;
            //从redis获取文件流
            redisCache.deleteObject(fileKey);
            Object tt1 = redisCache.getCacheObject(fileKey);
            //判断是否为空从redis
            if (tt1 == null) {
                //取值S3
                s3Object = is3Service.getObject(bucketname, fileKey);
                inputStream = new BufferedInputStream(s3Object.getObjectContent());
                bufferedInputStream = new BufferedInputStream(s3Object.getObjectContent());
                //赋值给redis
                byte[] tt = toByteArray(bufferedInputStream);
                String ttStr = Base64Utils.encodeToString(tt);
                redisCache.setCacheObject(fileKey, ttStr + ",-," + s3Object.getObjectMetadata().getContentType(), 2, TimeUnit.DAYS);
                //拿取redis信息
                Object fileInfo = redisCache.getCacheObject(fileKey);
                String[] split = fileInfo.toString().split(",-,", -1);
                byte[] ttnew = Base64Utils.decodeFromString(split[0]);
                InputStream sbs = new ByteArrayInputStream(ttnew);
                in_stream = new BufferedInputStream(sbs);
                // 设置响应流信息
                response.setContentType(split[1]);
                response.setCharacterEncoding("utf-8");
                response.setHeader("Pragma", "no-cache");
                response.setHeader("Cache-Control", "no-cache");
                response.setDateHeader("Expires", 0);
                ImportFileInfo importFileInfo = new ImportFileInfo();
                importFileInfo.setFileKey(fileKey);
                //查找库里面的表信息
                List<ImportFileInfo> importFileInfos = iImportFileInfoService.selectImportFileInfoList(importFileInfo);
                if (importFileInfos != null && importFileInfos.size() > 0 && StringUtils.isNotEmpty(fileName)) {
                    //如果传入文件名称，自动获取文件后缀名
//                MimeTypes allTypes = MimeTypes.getDefaultMimeTypes();
//                MimeType mimeType = allTypes.forName(objectMetadata.getContentType());
//                String extension = mimeType.getExtension();
                    response.addHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(importFileInfos.get(0).getFileName()));
                }
                FileUtils.writeBuffBytes(in_stream, response.getOutputStream());
            } else {//如果redis存在值的情况
                //取出加密后的文件key值
                String[] split = tt1.toString().split(",-,", -1);
                byte[] ttnew = Base64Utils.decodeFromString(split[0]);
                InputStream sbs = new ByteArrayInputStream(ttnew);
                in_stream = new BufferedInputStream(sbs);
                // 设置响应流信息
                response.setContentType(split[1]);
                response.setCharacterEncoding("utf-8");
                response.setHeader("Pragma", "no-cache");
                response.setHeader("Cache-Control", "no-cache");
                response.setDateHeader("Expires", 0);
                ImportFileInfo importFileInfo = new ImportFileInfo();
                importFileInfo.setFileKey(fileKey);
                //查找文件
                List<ImportFileInfo> importFileInfos = iImportFileInfoService.selectImportFileInfoList(importFileInfo);
                if (importFileInfos != null && importFileInfos.size() > 0 && StringUtils.isNotEmpty(fileName)) {
                    //如果传入文件名称，自动获取文件后缀名
//                MimeTypes allTypes = MimeTypes.getDefaultMimeTypes();
//                MimeType mimeType = allTypes.forName(objectMetadata.getContentType());
//                String extension = mimeType.getExtension();
                    response.addHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(importFileInfos.get(0).getFileName()));
                }
                FileUtils.writeBuffBytes(in_stream, response.getOutputStream());
            }

        } catch (Exception e) {
            log.error(e.getMessage());
        } finally {
            if (in_stream != null) {
                try {
                    in_stream.close();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
            if (inputStream != null) {
                try {
                    inputStream.close();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
            if (bufferedInputStream != null) {
                try {
                    bufferedInputStream.close();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * input流转byte[]数组
     *
     * @param input
     * @return
     * @throws IOException
     */
    public static byte[] toByteArray(InputStream input) throws IOException {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024 * 4];
        int n = 0;
        while (-1 != (n = input.read(buffer))) {
            output.write(buffer, 0, n);
        }
        return output.toByteArray();
    }

    /**
     * 多文件进行压缩
     */
    @GetMapping(value = "/gfxz/s3compressZip")
    @ApiOperation("多文件进行压缩")
    public void s3compressZip(String fileKey, String[] fileKeys, HttpServletResponse response, String zipFileName) {
        BufferedInputStream in_stream = null;
        FileOutputStream fous = null;
        ZipOutputStream zipOut = null;
        File file = null;
        /**
         * 创建一个临时压缩文件， 我们会把文件流全部注入到这个文件中 这里的文件你可以自定义是.rar还是.zip
         */
        file = new File(RuoYiConfig.getDownloadPath() + zipFileName);
        if (!file.exists()) {
            try {
                if (file.createNewFile()) log.info("创建成功");
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        response.reset();
        // response.getWriter()
        // 创建文件输出流
        try {
            fous = new FileOutputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        /**
         * 打包的方法我们会用到ZipOutputStream这样一个输出流, 所以这里我们把输出流转换一下
         */
        zipOut = new ZipOutputStream(fous);
        zipOut.setMethod(ZipOutputStream.DEFLATED);
        /**
         * 判断传入的集合
         */
        String[] filelist = null;
        if (StringUtils.isNotEmpty(fileKey)) {
            filelist = fileKey.split(",", -1);
        } else {
            filelist = fileKeys;
        }
        //循环文件key
        for (int i = 0; i < filelist.length; i++) {
            //S3下载
            S3Object s3Object = is3Service.getObject(bucketname, filelist[i]);
            ImportFileInfo importFileInfo = new ImportFileInfo();
            importFileInfo.setFileKey(filelist[i]);
            //查找文件列表
            List<ImportFileInfo> importFileInfos = iImportFileInfoService.selectImportFileInfoList(importFileInfo);
            in_stream = new BufferedInputStream(s3Object.getObjectContent(), 512);
            // org.apache.tools.zip.ZipEntry
            ZipEntry entry = new ZipEntry(importFileInfos.get(0).getFileName());
            try {
                zipOut.putNextEntry(entry);
            } catch (IOException e) {
                e.printStackTrace();
            }
            //把单个文件写入zip文件当中
            FileUtils.zipFile(in_stream, zipOut);
            try {
                if (in_stream != null) {

                    in_stream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (zipOut != null) {
            try {
                if (zipOut != null) {
                    zipOut.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        //传入到前端进行回显下载
        try{
            FileUtils.downloadZip(file, response);
        }catch (Exception e){
            log.error("前端进行回显下载失败！"+e);
        }
    }



    /**
     * 通用本地下载请求
     *
     * @param fileName 文件名称
     * @param delete 是否删除
     */
    @GetMapping("/local/download")
    public void fileDownload(String fileName, Boolean delete, HttpServletResponse response, HttpServletRequest request)
    {
        try
        {
            if (!FileUtils.checkAllowDownload(fileName))
            {
                throw new Exception(StringUtils.format("文件名称({})非法，不允许下载。 ", fileName));
            }
            String realFileName = System.currentTimeMillis() + fileName.substring(fileName.indexOf("_") + 1);
            String filePath = RuoYiConfig.getUploadPath() + fileName;

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
