package com.unncbandsclub.utopia.controller;

import com.unncbandsclub.utopia.annotation.UserLoginToken;
import com.unncbandsclub.utopia.entity.Role;
import com.unncbandsclub.utopia.entity.User;
import com.unncbandsclub.utopia.vo.UserVo;
import com.unncbandsclub.utopia.service.RoleService;
import com.unncbandsclub.utopia.service.UserService;
import com.unncbandsclub.utopia.utlis.ErrorCase;
import com.unncbandsclub.utopia.utlis.Result;
import com.unncbandsclub.utopia.utlis.UDataMapBuilder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/role")
public class RoleController {

    @Resource
    UserService userService;

    @Resource
    RoleService roleService;

    @UserLoginToken
    @PostMapping("/get/username")
    public Result findUserRoleByUserId(@RequestBody UserVo vo) {
        User u = userService.findUserByName(vo.getUsername());
        if (u == null) {
            return Result.error(ErrorCase.USER_NOT_EXIST);
        }

        List<Role> role = roleService.findRoleByUserId(u.getId());
        return Result.ok(UDataMapBuilder.build(role.toArray()));
    }
}
