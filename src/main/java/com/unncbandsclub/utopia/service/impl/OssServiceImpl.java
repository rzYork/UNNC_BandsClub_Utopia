package com.unncbandsclub.utopia.service.impl;

import com.aliyun.oss.OSSClient;
import com.unncbandsclub.utopia.service.OssService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

@Service
@Slf4j
public class OssServiceImpl implements OssService {

    @Value("${aliyun.oss.maxSize}")
    private int maxSize;

    @Value("${aliyun.oss.bucketName}")
    private String bucketName;

    @Value("${aliyun.oss.dir.prefix}")
    private String dirPrefix;

    @Resource
    private OSSClient ossClient;

    @Override
    public String upload(MultipartFile file) {
        try {
            return upload(file.getInputStream(), file.getOriginalFilename());
        } catch (IOException e) {
            log.error(e.getMessage());
        }
        return null;
    }

    @Override
    public String upload(InputStream inputStream, String name) {
        String objectName = getFileName(name);
        log.info(bucketName+" -[]- "+name+ " -[]- "+objectName);
        ossClient.putObject(bucketName, objectName, inputStream);
        return formatPath(objectName);
    }

    private String getFileName(String url) {
        String[] split = url.split("\\.");
        return dirPrefix + new SimpleDateFormat("yyyyMMdd").format(new Date()) + UUID.randomUUID().toString().replaceAll("-","") + "."+split[split.length - 1];
    }

    private String formatPath(String objectName) {
        return "https://" + bucketName + "." + ossClient.getEndpoint().getHost() + "/" + objectName;
    }
}