package com.unncbandsclub.utopia.controller;


import com.unncbandsclub.utopia.annotation.PassToken;
import com.unncbandsclub.utopia.service.MemberTypeService;
import com.unncbandsclub.utopia.utlis.Result;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Ruizhe Huang
 * @since 2022-05-22
 */
@RestController
@Api(tags="成员类型")
@RequestMapping("/member-type")
public class MemberTypeController {
  @Resource
  MemberTypeService memberTypeService;

  @GetMapping
  @PassToken
  public Result listALlMemberType(){
    return Result.ok(memberTypeService.findAll());
  }
}

