package com.unncbandsclub.utopia.controller;


import com.unncbandsclub.utopia.annotation.UserLoginToken;
import com.unncbandsclub.utopia.service.BandService;
import com.unncbandsclub.utopia.utlis.ErrorCase;
import com.unncbandsclub.utopia.utlis.Result;
import com.unncbandsclub.utopia.vo.BandRegisterVo;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author Ruizhe Huang
 * @since 2022-05-22
 */
@RestController
@RequestMapping("/band")
public class BandController {


  @Resource
  BandService bandService;

  @PostMapping("/register")
  @UserLoginToken(accessInNeed = {3009})
  public Result registerBand(@RequestBody BandRegisterVo vo) {

    if (vo.getBandName() == null || vo.getBandName().isEmpty()) {
      return Result.error(ErrorCase.INVALID_BAND_NAME);
    }

    if (bandService.findByName(vo.getBandName()) != null) {
      return Result.error(ErrorCase.BAND_NAME_EXIST);
    }

    


    return Result.ok();
  }
}

