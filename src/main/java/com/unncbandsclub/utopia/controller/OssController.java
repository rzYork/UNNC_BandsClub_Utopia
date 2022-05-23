package com.unncbandsclub.utopia.controller;

import com.unncbandsclub.utopia.service.OssService;
import com.unncbandsclub.utopia.utlis.ErrorCase;
import com.unncbandsclub.utopia.utlis.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@RestController
@Api(tags = "上传")
@RequestMapping("/ossController")
@Slf4j
public class OssController {
    @Resource
    private OssService ossService;

    @PostMapping("/upload")
    @ResponseBody
    @ApiOperation("上传")
    public Result upload(@RequestParam("file") MultipartFile file, HttpServletRequest req) {
        log.info("upload file with multipartFile object");
        log.info("size:" + file.getSize());
        log.info("type: " + file.getContentType());
        log.info("name: " + file.getName());
        String upload = ossService.upload(file);
        if (upload == null || upload.isEmpty()) {
            return Result.error(ErrorCase.UPLOAD_FILE_FAIL);
        }
        return Result.ok(upload);
    }
}