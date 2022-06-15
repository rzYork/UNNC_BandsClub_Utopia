package com.unncbandsclub.utopia.controller;


import com.unncbandsclub.utopia.annotation.UserLoginToken;
import com.unncbandsclub.utopia.entity.Band;
import com.unncbandsclub.utopia.service.BandService;
import com.unncbandsclub.utopia.utlis.ErrorCase;
import com.unncbandsclub.utopia.utlis.Result;
import com.unncbandsclub.utopia.vo.BandRegisterVo;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.*;

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
@Api(tags="乐队管理")
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
    Band band = new Band();
    band.setBandName(vo.getBandName());
    band.setIntroduction(vo.getIntroduction());
    band.setIs_in_school(true);

    Band result = bandService.registerBand(band);
    if (result == null) return Result.error(ErrorCase.SYSTEM_ERROR);
    return Result.ok(result);
  }

  @GetMapping("/findAll")
  @UserLoginToken(accessInNeed = {3011})
  public Result findALl(){
    return Result.ok(bandService.findAll());
  }
}

