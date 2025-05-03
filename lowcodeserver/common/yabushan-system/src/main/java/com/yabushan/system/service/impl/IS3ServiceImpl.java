package com.yabushan.system.service.impl;

import com.amazonaws.services.s3.model.*;
import com.yabushan.common.config.S3Config;
import com.yabushan.system.service.IS3Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.net.URL;
import java.util.Date;
import java.util.List;
@Service
public class IS3ServiceImpl implements IS3Service {

    @Autowired
    private S3Config s3Config;
    @Override
    public List<Bucket> listBuckets() {
        return s3Config.amazonS3().listBuckets();
    }

    @Override
    public Bucket createBucket(String bucketName) {
        return s3Config.amazonS3().createBucket(bucketName);
    }

    @Override
    public boolean doesBucketExist(String bucketName) {
        return s3Config.amazonS3().doesBucketExistV2(bucketName);
    }

    @Override
    public void deleteBucket(String bucketName) {
        s3Config.amazonS3().deleteBucket(bucketName);

    }

    @Override
    public String getName() {
        return s3Config.amazonS3().getRegionName();
    }

    @Override
    public Date getCreationDate() {
        return null;
    }

    @Override
    public ObjectListing listObjects(String bucketName) {
        return s3Config.amazonS3().listObjects(bucketName);
    }

    @Override
    public ObjectListing listObjects(String bucketName, String prefix) {
        return null;
    }

    @Override
    public List<S3ObjectSummary> getObjectSummaries() {
        return null;
    }

    @Override
    public String getKey() {
        return null;
    }

    @Override
    public long getSize() {
        return 0;
    }

    @Override
    public PutObjectResult putObject(String bucketName, String key, File file) {
        return  s3Config.amazonS3().putObject(bucketName,key,file);
    }

    @Override
    public S3Object getObject(String bucketName, String key) {
        return  s3Config.amazonS3().getObject(bucketName,key);
    }

    @Override
    public ObjectMetadata getObject(GetObjectRequest getObjectRequest, File destinationFile) {
        return null;
    }

    @Override
    public void deleteObject(String bucketName, String key) {
        s3Config.amazonS3().deleteObject(bucketName,key);
    }

    @Override
    public Boolean doesObjectExist(String bucketName, String objectName) {
        return null;
    }

    @Override
    public URL generatePresignedUrl(String bucketName, String key, Date expiration) {
        return null;
    }

    @Override
    public InitiateMultipartUploadResult initiateMultipartUpload(InitiateMultipartUploadRequest initiateMultipartUploadRequest) {
        return null;
    }

    @Override
    public CompleteMultipartUploadResult completeMultipartUpload(CompleteMultipartUploadRequest completeMultipartUploadRequest) {
        return null;
    }

    @Override
    public UploadPartResult uploadPart(UploadPartRequest uploadPartRequest) {
        return null;
    }
}
