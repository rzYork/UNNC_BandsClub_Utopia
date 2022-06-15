package com.unncbandsclub.utopia.controller;


import com.unncbandsclub.utopia.annotation.UserLoginToken;
import com.unncbandsclub.utopia.entity.Access;
import com.unncbandsclub.utopia.entity.Role;
import com.unncbandsclub.utopia.entity.User;
import com.unncbandsclub.utopia.service.AccessService;
import com.unncbandsclub.utopia.service.RoleService;
import com.unncbandsclub.utopia.service.UserService;
import com.unncbandsclub.utopia.utlis.ErrorCase;
import com.unncbandsclub.utopia.utlis.Result;
import com.unncbandsclub.utopia.utlis.TokenThreadUtils;
import com.unncbandsclub.utopia.utlis.UDataMapBuilder;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 权限详情表 前端控制器
 * </p>
 *
 * @author Ruizhe Huang
 * @since 2022-05-17
 */
@RestController
@Slf4j
@RequestMapping("/access")
@Api(tags="权限")
public class AccessController {

  @Resource
  private UserService userService;
  @Resource
  private AccessService accessService;

  @Resource
  private RoleService roleService;

  @GetMapping("/get/username")
  @UserLoginToken()
  public Result getAccessListByUserName() {
    User loginUser = TokenThreadUtils.getUser();
    if (loginUser == null) {
      return Result.error(ErrorCase.INVALID_TOKEN);
    }
    List<Access> enabledAccess = new ArrayList<>();

    List<Role> roleByUserId = roleService.findRoleByUserId(loginUser.getId());
    List<Access> accessByRole = accessService.findAccessByRole(roleByUserId);
    enabledAccess.addAll(accessByRole.stream().filter(e -> e.getStatus()).collect(Collectors.toList()));

    return Result.ok(UDataMapBuilder.build(enabledAccess));
  }


}

