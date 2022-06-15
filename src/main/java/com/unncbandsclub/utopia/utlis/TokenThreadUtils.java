package com.unncbandsclub.utopia.utlis;

import com.unncbandsclub.utopia.entity.Role;
import com.unncbandsclub.utopia.entity.User;
import com.unncbandsclub.utopia.pojo.LoginResult;
import com.unncbandsclub.utopia.service.UserService;
import com.unncbandsclub.utopia.vo.TokenVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.List;

@Component
@Slf4j
public class TokenThreadUtils {
  private static ThreadLocal<TokenVo> threadLocal = new ThreadLocal<TokenVo>();

  public static void save(TokenVo v) {
    threadLocal.set(v);
  }

  public static TokenVo get() {
    return threadLocal.get();
  }


  @Resource
  UserService service;

  private static UserService userService;

  @PostConstruct
  public void init() {
    TokenThreadUtils.userService = this.service;
  }


  public static String getUsername() {
    if (threadLocal.get() == null) {
      return null;
    }
    return threadLocal.get().getUsername();
  }

  public static String getPassword() {
    if (threadLocal.get() == null) {
      return null;
    }
    return threadLocal.get().getPassword();
  }

  public static Long getLoginTime() {
    if (threadLocal.get() == null) {
      return null;
    }
    return threadLocal.get().getLoginTime();
  }

  public static List<Integer> getRoleList() {
    if (threadLocal.get() == null) {
      return null;
    }
    return threadLocal.get().getRoleList();
  }

  public static User getUser() {
    if (userService == null) {
      log.debug("Null pointer of User service");
      return null;
    }
    String username = getUsername();
    if (username == null) {
      log.debug("Null pointer of user name");
      return null;
    }
    String password = getPassword();
    if (password == null) {
      log.debug("Null pointer of password");
      return null;
    }
    LoginResult login = userService.login(username, password);
    if (login == null || !login.isSuccess()) {
      log.debug("Login failed");
      return null;
    }
    return login.getUser();
  }

  public static List<Integer> getAccessList() {
    if (threadLocal.get() == null) {
      return null;
    }
    return threadLocal.get().getAccessList();
  }


  public static void remove() {
    threadLocal.remove();
  }


}

