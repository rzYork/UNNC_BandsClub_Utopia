package com.unncbandsclub.utopia.utlis;

import com.unncbandsclub.utopia.entity.Role;
import com.unncbandsclub.utopia.entity.User;
import com.unncbandsclub.utopia.pojo.LoginResult;
import com.unncbandsclub.utopia.service.UserService;
import com.unncbandsclub.utopia.vo.TokenVo;

import java.util.List;

public class TokenThreadUtils {
  private static ThreadLocal<TokenVo> threadLocal = new ThreadLocal<TokenVo>();

  public static void save(TokenVo v) {
    threadLocal.set(v);
  }

  public static TokenVo get() {
    return threadLocal.get();
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

  public static User getUser(UserService service) {
    String username = getUsername();
    if (username == null) return null;
    String password = getPassword();
    if (password == null) return null;
    LoginResult login = service.login(username, password);
    if (login == null || !login.isSuccess()) return null;
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

