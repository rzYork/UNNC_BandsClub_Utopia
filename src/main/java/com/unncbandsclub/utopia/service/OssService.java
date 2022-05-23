package com.unncbandsclub.utopia.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;

public interface OssService
{
    String upload(MultipartFile file);

    String upload(InputStream inputStream, String name);
}
