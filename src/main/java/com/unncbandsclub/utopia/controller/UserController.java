package com.unncbandsclub.utopia.controller;


import com.unncbandsclub.utopia.annotation.PassToken;
import com.unncbandsclub.utopia.annotation.UserLoginToken;
import com.unncbandsclub.utopia.config.UtopiaSystemConfiguration;
import com.unncbandsclub.utopia.entity.Access;
import com.unncbandsclub.utopia.entity.Role;
import com.unncbandsclub.utopia.entity.User;
import com.unncbandsclub.utopia.pojo.RegisterResult;
import com.unncbandsclub.utopia.service.AccessService;
import com.unncbandsclub.utopia.utlis.*;
import com.unncbandsclub.utopia.vo.*;
import com.unncbandsclub.utopia.pojo.LoginResult;
import com.unncbandsclub.utopia.service.RoleService;
import com.unncbandsclub.utopia.service.UserService;
import io.swagger.annotations.Api;
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
@Api(tags ="用户")
@RequestMapping("/user")
public class UserController {
  @Resource
  UserService userService;

  @Resource
  RoleService roleService;

  @Resource
  AccessService accessService;

  @Resource
  UtopiaSystemConfiguration config;


  @PostMapping("/login")
  @PassToken
  public Result loginUser(@RequestBody UserLoginVo vo) {
    if (vo == null || vo.getUsername() == null || vo.getPassword() == null || vo.getUsername().isEmpty() || vo.getPassword().isEmpty()) {
      return Result.error(ErrorCase.NULL_OR_EMPTY_NECESSARY_PARAMETER);
    }
    LoginResult login = userService.login(vo.getUsername(), vo.getPassword());


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

    if (login.getUser().getStatus() == false) {
      return Result.error(ErrorCase.DISABLED_ACCOUNT);
    }

    //login success
    User user = login.getUser();
    HashMap<String, Object> data = new HashMap<>();

    List<Role> roleByUserId =
      roleService.findRoleByUserId(user.getId());
    List<Integer> roleIdList = roleByUserId.stream().map(Role::getId).collect(Collectors.toList());

    List<Access> accessByRole = accessService.findAccessByRole(roleByUserId);
    List<Integer> accessIdList = accessByRole.stream().map(Access::getId).collect(Collectors.toList());

    TokenVo tokenVo = new TokenVo();
    tokenVo.setPassword(user.getPassword());
    tokenVo.setUsername(user.getName());
    tokenVo.setLoginTime(new Date().getTime());
    tokenVo.setRoleList(roleIdList);
    tokenVo.setRoleList(accessIdList);
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

  @GetMapping("/info")
  @UserLoginToken(accessInNeed = {3020})
  public Result getIndividualInformation() {
    TokenVo vo = TokenThreadUtils.get();
    if (vo == null)
      return Result.error(ErrorCase.INVALID_TOKEN);
    User userByName = userService.findUserByName(vo.getUsername());
    return Result.ok(userByName);
  }

  @PatchMapping("/update-info")
  @UserLoginToken(accessInNeed = {3008})
  public Result updateInfo(@RequestBody InfoUpdateVo vo) {
    User loginUser
      = TokenThreadUtils.getUser();
    if (!RegularUtil.checkInfoUpdateVo(vo)) {
      return Result.error(ErrorCase.ILLEGAL_PARAMETER);
    }
    User user = userService.updateUser(loginUser, vo);
    if (user == null) return Result.error(ErrorCase.SYSTEM_ERROR);
    return Result.ok(user);
  }

}

