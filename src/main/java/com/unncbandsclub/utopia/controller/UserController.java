package com.unncbandsclub.utopia.controller;


import com.unncbandsclub.utopia.annotation.PassToken;
import com.unncbandsclub.utopia.annotation.UserLoginToken;
import com.unncbandsclub.utopia.entity.Role;
import com.unncbandsclub.utopia.entity.User;
import com.unncbandsclub.utopia.pojo.RegisterResult;
import com.unncbandsclub.utopia.utlis.UDataMapBuilder;
import com.unncbandsclub.utopia.vo.TokenVo;
import com.unncbandsclub.utopia.utlis.ErrorCase;
import com.unncbandsclub.utopia.pojo.LoginResult;
import com.unncbandsclub.utopia.vo.RegisterVo;
import com.unncbandsclub.utopia.utlis.TokenUtil;
import com.unncbandsclub.utopia.utlis.Result;
import com.unncbandsclub.utopia.vo.LoginVo;
import com.unncbandsclub.utopia.service.RoleService;
import com.unncbandsclub.utopia.service.UserService;
import com.unncbandsclub.utopia.vo.UserVo;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

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


    @PostMapping("/login/username")
    @PassToken
    public Result loginUser(@RequestBody LoginVo vo) {
        LoginResult login = userService.login(vo.getUsername(), vo.getPassword());

        if(login.getUser().getStatus()==false){
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


    @PostMapping("/register/username")
    @PassToken
    public Result registerUser(@RequestBody RegisterVo vo) {
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




}

