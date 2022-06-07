package com.unncbandsclub.utopia.controller;


import com.unncbandsclub.utopia.config.UtopiaSystemConfiguration;
import com.unncbandsclub.utopia.entity.Avatar;
import com.unncbandsclub.utopia.service.AvatarService;
import com.unncbandsclub.utopia.utlis.ErrorCase;
import com.unncbandsclub.utopia.utlis.Result;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

@RestController
@RequestMapping("/avatar")
@Api(tags = "头像")
@Slf4j
public class AvatarController {
  @Resource
  AvatarService avatarService;

  @Resource
  UtopiaSystemConfiguration config;


  @GetMapping("/list-all")
  public Result listAllAvatar() {
    HashMap<String, Object> map = new HashMap<>();
    for (Avatar avatar : avatarService.findAll()) {
      map.put(String.valueOf(avatar.getId()), avatar.getUrl());
    }
    return Result.ok(map);
  }

  @GetMapping("/get")
  public Result getAvatar(@RequestParam("id") Integer id) {

    Avatar avatarById = avatarService.findAvatarById(id);
    if (avatarById == null) {
      return Result.error(ErrorCase.ID_NOT_EXISTS);
    }
    return Result.ok(avatarById);
  }

  @GetMapping("/get-random-default")
  public Result getRandomDefault() {
    List<Integer> defaultAvatarIds = config.getDefaultAvatarIds();
    Avatar avatar = avatarService.findAvatarById(defaultAvatarIds.get(new Random().nextInt(defaultAvatarIds.size())));
    if(avatar==null) {
      log.error("默认头像链接失效");
      return Result.error(ErrorCase.SYSTEM_ERROR);
    }
    return Result.ok(avatar);
  }

  @PostMapping("/upload")
  public Result uploadAvatar(@RequestParam("avatar") MultipartFile avatar, HttpServletRequest req) {
    //TODO
    if (!avatar.getContentType().split("/")[0].equals("image")) {
      return Result.error(ErrorCase.ILLEGAL_FILE_CONTENT_TYPE);
    }
    long sizeKb = avatar.getSize() / 1024;
    if(sizeKb/1024>2){
      return Result.error(ErrorCase.TOO_LARGE_FILE);
    }

    String url = avatarService.uploadAvatar(avatar);
    return Result.ok(url);
  }
}
