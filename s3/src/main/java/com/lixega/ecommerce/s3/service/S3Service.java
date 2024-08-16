package com.lixega.ecommerce.s3.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.PutObjectRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;


@Service
public class S3Service {

    @Autowired
    private AmazonS3 amazonS3;

    private final String bucketName = "my-bucket";

    public String uploadFile(String keyName, File file) {
        if (!amazonS3.doesBucketExistV2(bucketName)) {
            amazonS3.createBucket(bucketName);
        }
        amazonS3.putObject(new PutObjectRequest(bucketName, keyName, file));
        return amazonS3.getUrl(bucketName, keyName).toString(); // Return the S3 URL of the file
    }
}