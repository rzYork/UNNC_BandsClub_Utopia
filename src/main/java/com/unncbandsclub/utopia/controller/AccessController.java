package com.unncbandsclub.utopia.controller;


import com.unncbandsclub.utopia.annotation.UserLoginToken;
import com.unncbandsclub.utopia.entity.Access;
import com.unncbandsclub.utopia.entity.Role;
import com.unncbandsclub.utopia.entity.User;
import com.unncbandsclub.utopia.vo.UserVo;
import com.unncbandsclub.utopia.service.AccessService;
import com.unncbandsclub.utopia.service.RoleService;
import com.unncbandsclub.utopia.service.UserService;
import com.unncbandsclub.utopia.utlis.ErrorCase;
import com.unncbandsclub.utopia.utlis.Result;
import com.unncbandsclub.utopia.utlis.UDataMapBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

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
public class AccessController {

    @Resource
    private UserService userService;
    @Resource
    private AccessService accessService;

    @Resource
    private RoleService roleService;

    @PostMapping("/get/username")
    @UserLoginToken
    public Result getAccessListByUserName(@RequestBody UserVo vo) {
        if(vo==null||vo.getUsername()==null||vo.getUsername().isEmpty()){
          return Result.error(ErrorCase.NULL_OR_EMPTY_NECESSARY_PARAMETER);
        }
        List<Access> enabledAccess = new ArrayList<>();
        User u = userService.findUserByName(vo.getUsername());
        if (u == null)
            return Result.error(ErrorCase.USER_NOT_EXIST);

        List<Role> roleByUserId = roleService.findRoleByUserId(u.getId());
        List<Access> accessByRole = accessService.findAccessByRole(roleByUserId);
        enabledAccess.addAll(accessByRole.stream().filter(e -> e.getStatus()).collect(Collectors.toList()));

        return Result.ok(UDataMapBuilder.build(new Object[][]{{"access", enabledAccess}}));
    }




}

