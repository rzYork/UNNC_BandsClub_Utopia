package com.unncbandsclub.utopia.controller;

import com.unncbandsclub.utopia.annotation.UserLoginToken;
import com.unncbandsclub.utopia.entity.Role;
import com.unncbandsclub.utopia.entity.User;
import com.unncbandsclub.utopia.service.AccessService;
import com.unncbandsclub.utopia.service.RoleService;
import com.unncbandsclub.utopia.service.UserService;
import com.unncbandsclub.utopia.utlis.ErrorCase;
import com.unncbandsclub.utopia.utlis.RegularUtil;
import com.unncbandsclub.utopia.utlis.Result;
import com.unncbandsclub.utopia.utlis.UDataMapBuilder;
import com.unncbandsclub.utopia.vo.UserInfoVo;
import com.unncbandsclub.utopia.vo.UserRoleVo;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/manage")
public class ManageController {

  @Resource
  UserService userService;

  @Resource
  AccessService accessService;

  @Resource
  RoleService roleService;

  @GetMapping("/list-all-user")
  @UserLoginToken(accessInNeed = {3004})
  public Result getUserInfo() {
    return Result.ok(UDataMapBuilder.build(userService.findAll().toArray()));
  }

  @GetMapping("/disable")
  @UserLoginToken(accessInNeed = {3002})
  public Result disableAccount(@RequestParam("username") String username) {
    if (username == null || username.isEmpty()) {
      return Result.error(ErrorCase.NULL_OR_EMPTY_NECESSARY_PARAMETER);
    }
    User u = userService.findUserByName(username);
    if (u == null)
      return Result.error(ErrorCase.USER_NOT_EXIST);
    if (!u.getStatus())
      return Result.error(ErrorCase.REPEAT_OPERATION);
    u.setStatus(false);
    boolean b = userService.updateUser(u);
    if (b)
      return Result.ok();
    else
      return Result.error(ErrorCase.MODIFY_USER_FAIL);
  }

  @GetMapping("/enable")
  @UserLoginToken(accessInNeed = {3002})
  public Result enableAccount(@RequestParam("username") String username) {
    if (username == null || username.isEmpty()) {
      return Result.error(ErrorCase.NULL_OR_EMPTY_NECESSARY_PARAMETER);
    }
    User u = userService.findUserByName(username);
    if (u == null)
      return Result.error(ErrorCase.USER_NOT_EXIST);
    if (u.getStatus())
      return Result.error(ErrorCase.REPEAT_OPERATION);
    u.setStatus(true);
    boolean b = userService.updateUser(u);
    if (b)
      return Result.ok();
    else
      return Result.error(ErrorCase.MODIFY_USER_FAIL);
  }


  @PostMapping("/update-user-info")
  @UserLoginToken(accessInNeed = {3003})
  public Result updateUserInfo(@RequestBody UserInfoVo vo) {

    if (vo==null||vo.getUsername() == null || vo.getUsername().isEmpty()) {
      return Result.error(ErrorCase.NULL_OR_EMPTY_NECESSARY_PARAMETER);
    }
    //TODO  2022年6月1日 18点30分 必要参数判空
    User u = userService.findUserByName(vo.getUsername());
    if (vo.getAddressEmail() != null) {
      if (!RegularUtil.checkMail(vo.getAddressEmail())) {
        return Result.error(ErrorCase.ILLEGAL_MAIL);
      }
      u.setAddressEmail(vo.getAddressEmail());
    }
    if (vo.getAddressQQ() != null) {
      if (!RegularUtil.checkQQ(vo.getAddressQQ())) {
        return Result.error(ErrorCase.ILLEGAL_QQ);
      }
      u.setAddressQq(vo.getAddressQQ());
    }
    if (vo.getNickname() != null) {
      if (!RegularUtil.checkNickName(vo.getNickname())) {
        return Result.error(ErrorCase.ILLEGAL_NICKNAME);
      }
      u.setNickname(vo.getNickname());
    }
    if (vo.getAddressWechat() != null) {
      if (!RegularUtil.checkWechat(vo.getAddressWechat())) {
        return Result.error(ErrorCase.ILLEGAL_WECHAT);
      }
      u.setAddressWechat(vo.getAddressWechat());
    }
    if (vo.getRealName() != null) {
      if (!RegularUtil.checkRealName(vo.getRealName())) {
        return Result.error(ErrorCase.ILLEGAL_REAL_NAME);
      }
      u.setRealName(vo.getRealName());
    }
    if (vo.getSex() != null) {
      if (!RegularUtil.checkSex(vo.getSex())) {
        return Result.error(ErrorCase.ILLEGAL_SEX_OPTION);
      }
      u.setSex(vo.getSex());
    }
    if (vo.getStudentId() != null) {
      if (!RegularUtil.checkStudentId(vo.getStudentId())) {
        return Result.error(ErrorCase.ILLEGAL_STUDENT_ID);
      }
      u.setStudentId(vo.getStudentId());
    }
    userService.updateUser(u);
    return Result.ok(u);
  }

  @PostMapping("/update-user-role")
  @UserLoginToken(accessInNeed = {3005})
  public Result updateUserRole(@RequestBody UserRoleVo vo) {
    if(vo==null||vo.getUsername()==null||vo.getUsername().isEmpty())
    {
      return Result.error(ErrorCase.NULL_OR_EMPTY_NECESSARY_PARAMETER);
    }
    User u = userService.findUserByName(vo.getUsername());
    if (u == null)
      return Result.error(ErrorCase.USER_NOT_EXIST);

    List<Integer> roleIdList = vo.getNewRoleList();
    roleIdList = roleIdList.stream().distinct().collect(Collectors.toList());
    List<Role> roleList = roleService.findRoleById(roleIdList);
    if (roleList.size() < roleIdList.size()) {
      return Result.error(ErrorCase.ROLE_ID_NOT_EXIST);
    }

    roleService.updatePlayerRoleList(u.getId(), roleIdList);
    return Result.ok(roleService.findRoleByUserId(u.getId()));
  }

}
