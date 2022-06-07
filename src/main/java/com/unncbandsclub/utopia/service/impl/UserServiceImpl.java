package com.unncbandsclub.utopia.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.unncbandsclub.utopia.config.UtopiaSystemConfiguration;
import com.unncbandsclub.utopia.entity.User;
import com.unncbandsclub.utopia.mapper.UserMapper;
import com.unncbandsclub.utopia.pojo.LoginResult;
import com.unncbandsclub.utopia.pojo.RegisterResult;
import com.unncbandsclub.utopia.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.unncbandsclub.utopia.utlis.RegularUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.http11.filters.BufferedInputFilter;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.io.*;
import java.util.*;


/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author Ruizhe Huang
 * @since 2022-05-17
 */

@Service
@Slf4j
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {


  @Resource
  UserMapper userMapper;


  public static final List<String> nickNames = new ArrayList<>();

  @Resource
  UtopiaSystemConfiguration config;


  @PostConstruct
  private void initConfig() {
    nickNames.addAll(Arrays.asList(config.getNickNames()));
  }



  @Override
  public RegisterResult register(String username, String password, String key) {
    if (!config.getRegisterOpen()) {
      return RegisterResult.fail(RegisterResult.RegisterFailReason.REGISTER_CLOSE);
    }
    if (config.getOpenCdkey()) {
      log.info("Register With CDKEY: [" + key + "]");
      //TODO CD_KEY Check

    }
    User existUser = userMapper.selectOne(new QueryWrapper<User>().eq("name", username));
    if (existUser != null) {
      return RegisterResult.fail(RegisterResult.RegisterFailReason.USERNAME_EXISTS);
    }

    if (username == null || !RegularUtil.checkName(username)) {
      return RegisterResult.fail(RegisterResult.RegisterFailReason.ILLEGAL_USERNAME);
    }
    if (password.length() < 6) {
      return RegisterResult.fail(RegisterResult.RegisterFailReason.WEAK_PASSWORD);
    }

    if (password == null || !RegularUtil.checkPwd(password)) {
      return RegisterResult.fail(RegisterResult.RegisterFailReason.ILLEGAL_PASSWORD);
    }

    User user = new User();
    user.setName(username);
    user.setPassword(password);
    user.setAddressEmail("");
    user.setAddressWechat("");
    user.setAvatar("/default_avatar.png");
    user.setCreatedTime(new Date());
    user.setUpdatedTime(new Date());
    user.setIsAdmin(false);
    user.setStatus(true);
    user.setNickname(nickNames.get((int) (Math.random() * list().size())));

    userMapper.insert(user);
    return RegisterResult.success(user);
  }

  @Override
  public RegisterResult register(String username, String password) {
    return register(username, password, "");
  }

  @Override
  public LoginResult login(String username, String password) {

    QueryWrapper<User> qw = new QueryWrapper();
    User user = userMapper.selectOne(qw.eq("name", username));

    if (user == null) {
      return LoginResult.fail(LoginResult.LoginFailReason.USERNAME_NOT_EXIST);
    }

    if (!(password.equals(user.getPassword()))) {
      return LoginResult.fail(LoginResult.LoginFailReason.WRONG_PASSWORD);
    }

    return LoginResult.success(user);

  }

  @Override
  public User findUserByName(String username) {
    if (username == null || username.isEmpty()) {
      return null;
    }
    return userMapper.selectOne(new QueryWrapper<User>().eq("name", username));
  }

  @Override
  public List<User> findAll() {
    return userMapper.selectList(null);

  }

  @Override
  public boolean updateUser(User u) {
    if (u == null || u.getId() == null) return false;
    return userMapper.updateById(u) == 1;
  }
}
