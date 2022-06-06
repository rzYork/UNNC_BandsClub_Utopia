package com.unncbandsclub.utopia.controller;


import com.unncbandsclub.utopia.annotation.PassToken;
import com.unncbandsclub.utopia.annotation.UserLoginToken;
import com.unncbandsclub.utopia.config.UtopiaSystemConfiguration;
import com.unncbandsclub.utopia.entity.Role;
import com.unncbandsclub.utopia.entity.User;
import com.unncbandsclub.utopia.pojo.RegisterResult;
import com.unncbandsclub.utopia.vo.*;
import com.unncbandsclub.utopia.utlis.ErrorCase;
import com.unncbandsclub.utopia.pojo.LoginResult;
import com.unncbandsclub.utopia.utlis.TokenUtil;
import com.unncbandsclub.utopia.utlis.Result;
import com.unncbandsclub.utopia.service.RoleService;
import com.unncbandsclub.utopia.service.UserService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 用户表 前端控制器
 * </p>
 *
 * @author Ruizhe Huang
 * @since 2022-05-17
 */
@RestController
@CrossOrigin
@RequestMapping("/user")
public class UserController {
  @Resource
  UserService userService;

  @Resource
  RoleService roleService;

  @Resource
  UtopiaSystemConfiguration config;


  @PostMapping("/login")
  @PassToken
  public Result loginUser(@RequestBody UserLoginVo vo) {
    if (vo == null || vo.getUsername() == null || vo.getPassword() == null || vo.getUsername().isEmpty() || vo.getPassword().isEmpty()) {
      return Result.error(ErrorCase.NULL_OR_EMPTY_NECESSARY_PARAMETER);
    }
    LoginResult login = userService.login(vo.getUsername(), vo.getPassword());

    if (login.getUser().getStatus() == false) {
      return Result.error(ErrorCase.DISABLED_ACCOUNT);
    }

    if (!login.isSuccess()) {
      LoginResult.LoginFailReason loginFailReason = login.getLoginFailReason();
      switch (loginFailReason) {
        case USERNAME_NOT_EXIST:
          return Result.error(ErrorCase.USER_NOT_EXIST);
        case WRONG_PASSWORD:
          return Result.error(ErrorCase.WRONG_PASSWORD);
        case EMAIL_NOT_EXIST:
          return Result.error(ErrorCase.EMAIL_NOT_EXIST);
        case ACCOUNT_INVALID:
          return Result.error(ErrorCase.INVALID_ACCOUNT);
      }
    }

    //login success
    User user = login.getUser();
    HashMap<String, Object> data = new HashMap<>();

    List<Role> roleByUserId =
      roleService.findRoleByUserId(user.getId());
    List<Integer> roleIdList = roleByUserId.stream().map(Role::getId).collect(Collectors.toList());

    TokenVo tokenVo = new TokenVo();
    tokenVo.setPassword(user.getPassword());
    tokenVo.setUsername(user.getName());
    tokenVo.setLoginTime(new Date().getTime());
    tokenVo.setRoleList(roleIdList);
    data.put("token", TokenUtil.createToken(tokenVo));

    return Result.ok(data);
  }


  @PostMapping("/register")
  @PassToken
  public Result registerUser(@RequestBody UserRegisterVo vo) {
    if (vo == null || vo.getUsername() == null || vo.getPassword() == null || vo.getUsername().isEmpty() || vo.getPassword().isEmpty()) {
      return Result.error(ErrorCase.NULL_OR_EMPTY_NECESSARY_PARAMETER);
    }
    String password = vo.getPassword();
    String username = vo.getUsername();
    RegisterResult regResult = userService.register(username, password, "CD_KEY");
    if (!regResult.isSuccess()) {
      switch (regResult.registerFailReason()) {

        case REGISTER_CLOSE:
          return Result.error(ErrorCase.SYSTEM_MAINTAIN);
        case USERNAME_EXISTS:
          return Result.error(ErrorCase.USER_EXIST);
        case ILLEGAL_USERNAME:
        case ILLEGAL_PASSWORD:
        case WEAK_PASSWORD:
          return Result.error(ErrorCase.ILLEGAL_PARAMETER);
        case INVALID_KEY:
          return Result.error(ErrorCase.INVALID_KEY);
      }
    }
    HashMap<String, Object> data = new HashMap<>();
    data.put("user", regResult.getUser());
    return Result.ok(data);

  }

  @PostMapping("/info")
  @UserLoginToken(accessInNeed = {3020})
  public Result getIndividualInformation(@RequestBody UserVo vo) {
    if (vo == null || vo.getUsername() == null || vo.getUsername().isEmpty()) {
      return Result.error(ErrorCase.NULL_OR_EMPTY_NECESSARY_PARAMETER);
    }
    User userByName = userService.findUserByName(vo.getUsername());
    if (userByName == null)
      return Result.error(ErrorCase.USER_NOT_EXIST);
    return Result.ok(userByName);
  }

}

