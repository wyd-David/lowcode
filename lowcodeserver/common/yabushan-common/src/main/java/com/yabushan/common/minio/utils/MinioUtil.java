package com.yabushan.common.minio.utils;

import com.alibaba.fastjson.JSONObject;
import com.yabushan.common.minio.domain.Fileinfo;
import io.minio.*;
import io.minio.errors.*;
import io.minio.messages.Bucket;
import io.minio.messages.Item;
import org.apache.ibatis.type.ZonedDateTimeTypeHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Component
public class MinioUtil {
	@Autowired
	private MinioClient minioClient;

	/**
	 * 创建一个桶
	 */
	public void createBucket(String bucket) throws Exception {
		boolean found = minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucket).build());
		if (!found) {
			minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucket).build());
		}
	}

	/**
	 * 上传一个文件
	 */
	public void uploadFile(InputStream stream, String bucket, String objectName) throws Exception {
		minioClient.putObject(PutObjectArgs.builder().bucket(bucket).object(objectName)
				.stream(stream, -1, 10485760).build());
	}

	/**
	 * 列出所有的桶
	 */
	public List<String> listBuckets() throws Exception {
		List<Bucket> list = minioClient.listBuckets();
		List<String> names = new ArrayList<>();
		list.forEach(b -> {
			names.add(b.name());
		});
		return names;
	}

	/**
	 * 列出一个桶中的所有文件和目录
	 */
	public List<Fileinfo> listFiles(String bucket) throws Exception {
		Iterable<Result<Item>> results = minioClient.listObjects(
				ListObjectsArgs.builder().bucket(bucket).recursive(true).build());

		List<Fileinfo> infos = new ArrayList<>();
		results.forEach(r -> {
			Fileinfo info = new Fileinfo();
			try {
				Item item = r.get();
				info.setFilename(item.objectName());
				info.setDirectory(item.isDir());
				infos.add(info);
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
		return infos;
	}

	/**
	 * 下载一个文件
	 */
	public InputStream download(String bucket, String objectName) throws Exception {
		InputStream stream = minioClient.getObject(
				GetObjectArgs.builder().bucket(bucket).object(objectName).build());
		return stream;
	}

	/**
	 * 删除一个桶
	 */
	public void deleteBucket(String bucket) throws Exception {
		minioClient.removeBucket(RemoveBucketArgs.builder().bucket(bucket).build());
	}

	/**
	 * 删除一个对象
	 */
	public void deleteObject(String bucket, String objectName) throws Exception {
		minioClient.removeObject(RemoveObjectArgs.builder().bucket(bucket).object(objectName).build());
	}


	/**
	 * 获取存储桶策略
	 *
	 * @param bucketName 存储桶名称
	 * @return json
	 */
	private JSONObject getBucketPolicy(String bucketName)
			throws IOException, InvalidKeyException, InvalidResponseException, BucketPolicyTooLargeException, NoSuchAlgorithmException, ServerException, InternalException, XmlParserException, ServerException, InsufficientDataException, ErrorResponseException {
		String bucketPolicy = minioClient.getBucketPolicy(GetBucketPolicyArgs.builder().bucket(bucketName).build());
		return JSONObject.parseObject(bucketPolicy);
	}

	/**
	 * 根据bucketName获取信息
	 *
	 * @param bucketName bucket名称
	 */
	public  List<Bucket> getBucket(String bucketName)
			throws IOException, InvalidKeyException, InvalidResponseException, InsufficientDataException, NoSuchAlgorithmException, ServerException, InternalException, XmlParserException, ErrorResponseException {
		List<Bucket> buckets = minioClient.listBuckets();
		return buckets;
	}


	/**
	 * 判断文件夹是否存在
	 *
	 * @param bucketName 存储桶
	 * @param objectName 文件夹名称（去掉/）
	 * @return true：存在
	 */
	public  boolean doesFolderExist(String bucketName, String objectName) {
		boolean exist = false;
		try {
			Iterable<Result<Item>> results = minioClient.listObjects(
					ListObjectsArgs.builder().bucket(bucketName).prefix(objectName).recursive(false).build());
			for (Result<Item> result : results) {
				Item item = result.get();
				if (item.isDir() && objectName.equals(item.objectName())) {
					exist = true;
				}
			}
		} catch (Exception e) {
			exist = false;
		}
		return exist;
	}


	/**
	 * 根据文件前置查询文件
	 *
	 * @param bucketName bucket名称
	 * @param prefix 前缀
	 * @param recursive 是否递归查询
	 * @return MinioItem 列表
	 */
	public  List<Item> getAllObjectsByPrefix(String bucketName, String prefix,
												   boolean recursive)
			throws ErrorResponseException, InsufficientDataException, InternalException, InvalidKeyException, InvalidResponseException,
			IOException, NoSuchAlgorithmException, ServerException, XmlParserException {
		List<Item> list = new ArrayList<>();
		Iterable<Result<Item>> objectsIterator = minioClient.listObjects(
				ListObjectsArgs.builder().bucket(bucketName).prefix(prefix).recursive(recursive).build());
		if (objectsIterator != null) {
			for (Result<Item> o : objectsIterator) {
				Item item = o.get();
				list.add(item);
			}
		}
		return list;
	}

	/**
	 * 获取文件流
	 *
	 * @param bucketName bucket名称
	 * @param objectName 文件名称
	 * @return 二进制流
	 */
	public  InputStream getObject(String bucketName, String objectName)
			throws IOException, InvalidKeyException, InvalidResponseException, InsufficientDataException, NoSuchAlgorithmException, ServerException, InternalException, XmlParserException, ErrorResponseException {
		return minioClient
				.getObject(GetObjectArgs.builder().bucket(bucketName).object(objectName).build());
	}


	/**
	 * 断点下载
	 *
	 * @param bucketName bucket名称
	 * @param objectName 文件名称
	 * @param offset 起始字节的位置
	 * @param length 要读取的长度
	 * @return 流
	 */
	public InputStream getObject(String bucketName, String objectName, long offset, long length)
			throws IOException, InvalidKeyException, InvalidResponseException, InsufficientDataException, NoSuchAlgorithmException, ServerException, InternalException, XmlParserException, ErrorResponseException {
		return minioClient.getObject(
				GetObjectArgs.builder().bucket(bucketName).object(objectName).offset(offset).length(length)
						.build());
	}


	/**
	 * 获取路径下文件列表
	 *
	 * @param bucketName bucket名称
	 * @param prefix 文件名称
	 * @param recursive 是否递归查找，如果是false,就模拟文件夹结构查找
	 * @return 二进制流
	 */
	public  Iterable<Result<Item>> listObjects(String bucketName, String prefix,
													 boolean recursive) {
		return minioClient.listObjects(
				ListObjectsArgs.builder().bucket(bucketName).prefix(prefix).recursive(recursive).build());
	}



	/**
	 * 获取文件外链
	 *
	 * @param bucketName bucket名称
	 * @param objectName 文件名称
	 * @param expires 过期时间 <=7 秒级
	 * @return url
	 */
	public  String getPresignedObjectUrl(String bucketName, String objectName,
											   Integer expires)
			throws IOException, InvalidKeyException, InvalidResponseException, InsufficientDataException, ServerException, InternalException, NoSuchAlgorithmException, XmlParserException, ErrorResponseException {
		Map<String, String> stringStringMap = presignedGetObject(bucketName, expires);
		return stringStringMap.toString();
	}

	/**
	 * 给presigned URL设置策略
	 *
	 * @param bucketName 存储桶
	 * @param expires 过期策略
	 * @return map
	 */
	public Map<String, String> presignedGetObject(String bucketName,
												  Integer expires)
			throws IOException, ServerException, InsufficientDataException, ErrorResponseException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {

		ZonedDateTime zonedDateTime = ZonedDateTime.now().plusDays(expires);
		PostPolicy policy = new PostPolicy(bucketName,zonedDateTime);
		return minioClient.getPresignedPostFormData(policy);
	}


	/**
	 * 将URLDecoder编码转成UTF8
	 *
	 * @param str
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static String getUtf8ByURLDecoder(String str) throws UnsupportedEncodingException {
		String url = str.replaceAll("%(?![0-9a-fA-F]{2})", "%25");
		return URLDecoder.decode(url, "UTF-8");
	}



}
