package com.yabushan.system.service;

import com.amazonaws.services.s3.model.*;

import java.io.File;
import java.net.URL;
import java.util.Date;
import java.util.List;

public interface IS3Service {

    /**
     * 列举这个用户所 有 bucket
     * @return List<Bucket>
     */
    public List<Bucket> listBuckets();

    /**
     * 创建 bucket
     * @param bucketName
     * @return
     */
    public Bucket createBucket(String bucketName);

    /**
     * 查询 bucket 是否已存在
     * @param bucketName
     * @return
     */
    public boolean doesBucketExist(String bucketName);

    /**
     * 删除 bucket
     * @param bucketName
     */
    public void deleteBucket(String bucketName);

    /**
     * 获取 bucket 名字
     * @return String
     */
    public  String getName() ;

    /**
     * 获取 bucket 创建时间
     * @return Date
     */
    public Date getCreationDate();

    /**
     * 列举某个bucket的所有对象
     * @param bucketName
     * @return
     */
    public ObjectListing listObjects(String bucketName);

    /**
     * 列举某个bucket的带前缀对象
     * @param bucketName
     * @param prefix
     * @return
     */
    public  ObjectListing  listObjects(String bucketName, String prefix);

    /**
     * 获取某个对象的详细信息
     * @return List<S3ObjectSummary>
     */
    List<S3ObjectSummary>  getObjectSummaries();

    /**
     * 获取对象的名字
     * @return
     */
    String getKey();

    /**
     * 获取对象的大小
     * @return
     */
    long getSize();

    /**
     * 上传对象
     * @param bucketName
     * @param key
     * @param file
     * @return
     */
    PutObjectResult putObject(String bucketName, String key, File file);

    /**
     * 下载对象
     * @param bucketName
     * @param key
     * @return
     */
    S3Object getObject(String bucketName, String key);

    /**
     * 下载对象
     * @param getObjectRequest
     * @param destinationFile
     * @return
     */
    ObjectMetadata getObject(GetObjectRequest  getObjectRequest, File destinationFile);

    /**
     * 删除对象
     * @param bucketName
     * @param key
     */
    void  deleteObject(String bucketName, String key);

    /**
     * 查询对象是否存在
     * @param bucketName
     * @param objectName
     * @return
     */
    Boolean  doesObjectExist(String bucketName, String objectName);

    /**
     * 生成外链
     * @param bucketName
     * @param key
     * @param expiration
     * @return
     */
    URL generatePresignedUrl(String bucketName, String key, Date expiration);

    /**
     * 初始化分片上传
     * @param initiateMultipartUploadRequest
     * @return
     */
    InitiateMultipartUploadResult initiateMultipartUpload(InitiateMultipartUploadRequest initiateMultipartUploadRequest);

    /**
     * 完成分片上传
     * @param completeMultipartUploadRequest
     * @return
     */
    CompleteMultipartUploadResult  completeMultipartUpload(CompleteMultipartUploadRequest completeMultipartUploadRequest);

    /**
     * 分片上传
     * @param uploadPartRequest
     * @return
     */
    UploadPartResult  uploadPart(UploadPartRequest uploadPartRequest);


}
