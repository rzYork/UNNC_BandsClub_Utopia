package com.unncbandsclub.utopia.service.impl;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.PutObjectResult;
import com.unncbandsclub.utopia.service.OssService;
import com.unncbandsclub.utopia.utlis.RegularUtil;
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

  /**
   * 上传最大文件限制2Gbs
   */
  @Value("${aliyun.oss.maxSize}")
  private int maxSize;


  @Value("${aliyun.oss.bucketName}")
  private String bucketName;

  /**
   * 上传根目录前缀
   */
  @Value("${aliyun.oss.dir.prefix}")
  private String dirPrefix;

  @Resource
  private OSSClient ossClient;

  @Override
  public String upload(MultipartFile file) {
    try {
      long sizeKb = file.getSize() / 1024;
      if(sizeKb*1024>maxSize){
        return null;
      }
      return upload(file.getInputStream(), file.getOriginalFilename());
    } catch (IOException e) {
      log.error(e.getMessage());
    }
    return null;
  }

  @Override
  public String upload(InputStream inputStream, String name) {

    String objectName = getObjectName("", name);
    PutObjectResult putObjectResult = ossClient.putObject(bucketName, objectName, inputStream);
    log.info("PutObjectResult: "+putObjectResult.toString());
    return formatPath(objectName);
  }

  /**
   * 获取存储对象路径与名称  以日期+uuid作为存储文件名
   *
   * @param url
   * @return
   */
  private String getObjectName(String dirPath, String url) {
    if (dirPath.startsWith("/")) {
      dirPath.replaceFirst("/", ""); //规范目录格式
    }

    return dirPrefix + new SimpleDateFormat("yyyyMMddHHmmss").
      format(new Date()) + UUID.randomUUID().toString().replaceAll("-", "") //去除UUID中中划线分割，因为作为文件名不合法
      + "."
      + RegularUtil.extractFileSuffix(url); //使用源文件后缀
  }

  private String formatPath(String objectName) {
    return "https://" + bucketName + "." + ossClient.getEndpoint().getHost() + "/" + objectName;
  }
}
