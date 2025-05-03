package com.yabushan.common.minio.controller;


import com.yabushan.common.core.domain.AjaxResult;
import com.yabushan.common.minio.utils.MinioUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;

@Api(tags = "文件操作接口")
/**
 *
 * http://127.0.0.1:8080/swagger-ui.html#/%E6%96%87%E4%BB%B6%E6%93%8D%E4%BD%9C%E6%8E%A5%E5%8F%A3
 */
@RestController
@RequestMapping("/DeleteMinio")
public class FileController {

	@Autowired
	MinioUtil minioUtil;

	@ApiOperation("上传一个文件")
	@RequestMapping(value = "/uploadfile", method = RequestMethod.POST)
	@ResponseBody
	public AjaxResult fileupload(@RequestParam MultipartFile uploadfile, @RequestParam String bucket,
								 @RequestParam(required=false) String objectName) throws Exception {
		// minioUtil.createBucket(bucket);
		if (objectName != null) {
			minioUtil.uploadFile(uploadfile.getInputStream(), bucket, objectName+"/"+uploadfile.getOriginalFilename());
		} else {
			minioUtil.uploadFile(uploadfile.getInputStream(), bucket, uploadfile.getOriginalFilename());
		}
		return AjaxResult.success();
	}

	@ApiOperation("列出所有的桶")
	@RequestMapping(value = "/listBuckets", method = RequestMethod.GET)
	@ResponseBody
	public AjaxResult listBuckets() throws Exception {
		return AjaxResult.success(minioUtil.listBuckets());
	}

	@ApiOperation("递归列出一个桶中的所有文件和目录")
	@RequestMapping(value = "/listFiles", method = RequestMethod.GET)
	@ResponseBody
	public AjaxResult listFiles(@RequestParam String bucket) throws Exception {
		return AjaxResult.success("200", minioUtil.listFiles(bucket));
	}

	@ApiOperation("下载一个文件")
	@RequestMapping(value = "/downloadFile", method = RequestMethod.GET)
	@ResponseBody
	public void downloadFile(@RequestParam String bucket, @RequestParam String objectName,
			HttpServletResponse response) throws Exception {
		InputStream stream = minioUtil.download(bucket, objectName);
		ServletOutputStream output = response.getOutputStream();
		response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(objectName.substring(objectName.lastIndexOf("/") + 1), "UTF-8"));
        response.setContentType("application/octet-stream");
        response.setCharacterEncoding("UTF-8");
		IOUtils.copy(stream, output);
	}


	@ApiOperation("删除一个文件")
	@RequestMapping(value = "/deleteFile", method = RequestMethod.GET)
	@ResponseBody
	public AjaxResult deleteFile(@RequestParam String bucket, @RequestParam String objectName) throws Exception {
		minioUtil.deleteObject(bucket, objectName);
		return AjaxResult.success();
	}

	@ApiOperation("删除一个桶")
	@RequestMapping(value = "/deleteBucket", method = RequestMethod.GET)
	@ResponseBody
	public AjaxResult deleteBucket(@RequestParam String bucket) throws Exception {
		minioUtil.deleteBucket(bucket);
		return AjaxResult.success();
	}


	/**
	 * 图片回显前端
	 */
	@GetMapping(value = "/ImgShow")
	@ApiOperation("图片回显前端")
	public void getCode(String bucket, String objectName, HttpServletResponse response) throws IOException {
		BufferedInputStream bufferedInputStream = null;
		try {
			InputStream stream = minioUtil.download(bucket, objectName);
			ServletOutputStream output = response.getOutputStream();
			response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(objectName.substring(objectName.lastIndexOf("/") + 1), "UTF-8"));
			response.setContentType("application/octet-stream");
			response.setCharacterEncoding("UTF-8");
			bufferedInputStream = new BufferedInputStream(stream);
			writeBuffBytes(bufferedInputStream, output);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
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
	 * 输出指定流的byte数组
	 *
	 * @param os 输出流
	 * @return
	 */
	public static void writeBuffBytes(BufferedInputStream bufferedInputStream, OutputStream os) throws IOException {
		try {
			byte[] b = new byte[bufferedInputStream.available()];
			int length;
			while ((length = bufferedInputStream.read(b)) > 0) {
				os.write(b, 0, length);
			}

			os.flush();
		} catch (IOException e) {
			throw e;
		} finally {
			if(null != os){
				os.close();
			}
		}
	}




}
