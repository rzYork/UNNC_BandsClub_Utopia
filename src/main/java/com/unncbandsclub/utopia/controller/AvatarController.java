package com.unncbandsclub.utopia.controller;


import com.unncbandsclub.utopia.entity.Avatar;
import com.unncbandsclub.utopia.service.AvatarService;
import com.unncbandsclub.utopia.utlis.Result;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

@RestController
@RequestMapping("/avatar")
@Api(tags = "头像")
public class AvatarController {
  @Resource
  AvatarService avatarService;

  @GetMapping("/list-default")
  public Result listDefaultAvatars() {

    //TODO
    return Result.ok("developing...");
  }


  @GetMapping("/list-all")
  public Result listAllAvatar(){
    HashMap<String,Object> map=new HashMap<>();
    for (Avatar avatar : avatarService.findAll()) {
      map.put(String.valueOf(avatar.getId()),avatar.getUrl());
    }
    return Result.ok(map);
  }
  @GetMapping("/get")
  public Result getAvatar(@RequestParam Integer id) {

    //TODO
    return Result.ok("developing...");
  }

  @PostMapping("/upload")
  public Result uploadAvatar(@RequestParam("avatar") MultipartFile avatar, HttpServletRequest req) {
    //TODO
    return Result.ok("developing...");
  }
}
