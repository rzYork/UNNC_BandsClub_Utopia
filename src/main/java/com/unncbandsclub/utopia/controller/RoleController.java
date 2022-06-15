package com.unncbandsclub.utopia.controller;

import com.unncbandsclub.utopia.annotation.UserLoginToken;
import com.unncbandsclub.utopia.entity.Role;
import com.unncbandsclub.utopia.entity.User;
import com.unncbandsclub.utopia.service.RoleService;
import com.unncbandsclub.utopia.service.UserService;
import com.unncbandsclub.utopia.utlis.ErrorCase;
import com.unncbandsclub.utopia.utlis.Result;
import com.unncbandsclub.utopia.utlis.TokenThreadUtils;
import com.unncbandsclub.utopia.utlis.UDataMapBuilder;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@Api(tags="角色")
@RequestMapping("/role")
public class RoleController {

    @Resource
    UserService userService;

    @Resource
    RoleService roleService;

    @UserLoginToken
    @PostMapping("/get/username")
    public Result findUserRoleByUserId() {
      User loginUser = TokenThreadUtils.getUser();
      if(loginUser==null){
        return Result.error(ErrorCase.INVALID_TOKEN);
      }


        List<Role> role = roleService.findRoleByUserId(loginUser.getId());
        return Result.ok(role.toArray());
    }
}
