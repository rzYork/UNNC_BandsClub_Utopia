package com.unncbandsclub.utopia.utlis;

import com.alibaba.fastjson.JSONObject;
import com.unncbandsclub.utopia.annotation.PassToken;
import com.unncbandsclub.utopia.annotation.UserLoginToken;
import com.unncbandsclub.utopia.config.UtopiaSystemConfiguration;
import com.unncbandsclub.utopia.entity.Access;
import com.unncbandsclub.utopia.entity.User;
import com.unncbandsclub.utopia.pojo.LoginResult;
import com.unncbandsclub.utopia.service.AccessService;
import com.unncbandsclub.utopia.service.RoleService;
import com.unncbandsclub.utopia.vo.TokenVo;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.List;


@Component
@Slf4j
public class TokenInterceptor implements HandlerInterceptor {

  @Resource
  AccessService accessService;

  @Resource
  RoleService roleService;

  @Resource
  UtopiaSystemConfiguration config;


  /**
   * @param request
   * @param response
   * @param handler
   * @return 请求是否通过
   * @throws Exception
   */
  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
    //配置Token校验是否开启
    if (!config.getTokenVerifyOpen()) {
      return true;
    }
    // 如果不是映射到方法直接通过
    if (!(handler instanceof HandlerMethod)) {
      return true;
    }

    HandlerMethod handlerMethod = (HandlerMethod) handler;
    Method method = handlerMethod.getMethod();

    //跨域请求会首先发送一个options请求，直接返回正常状态并通过拦截器
    if (request.getMethod().equalsIgnoreCase("OPTIONS")) {
      response.setStatus(HttpServletResponse.SC_OK);
      log.info("走过了option请求");
      return true;
    }

    // 检查该方法上是否有 PassToken 的注解
    if (method.isAnnotationPresent(PassToken.class)) {
      PassToken passToken = method.getAnnotation(PassToken.class);
      if (passToken.required()) {
        log.info("Skipped Interceptor");
        return true;
      }
    }

    StringBuilder msgBuilder = new StringBuilder("token verify fail ");
    Claims claim;
    if (method.isAnnotationPresent(UserLoginToken.class)) {
      UserLoginToken annotation = method.getAnnotation(UserLoginToken.class);
      if (annotation.required()) {
        response.setCharacterEncoding("UTF-8");
        String token = request.getHeader("Authorization");
        log.info("parsing token :" + token);
        if (token != null) {
          boolean pass = (claim = TokenUtil.parseToken(token)) != null;
          TokenVo tokenVo = TokenUtil.parseToken(claim);

          if (tokenVo != null) {
            LoginResult loginResult = TokenUtil.verifyToken(tokenVo);

            pass = pass && loginResult.isSuccess();
            if (!loginResult.isSuccess()) {
              msgBuilder.append("|token login fail|");
            }

            boolean in = (annotation.whitelist().length == 0);
            if (!in) {
              for (int i = 0; i < annotation.whitelist().length; i++) {
                if (annotation.whitelist()[i].equals(tokenVo.getUsername())) {
                  in = true;
                  break;
                }
              }
            }
            if (!in) {
              log.info("Token White List Interceptor Not Pass! ");
              log.info(annotation.whitelist() + " Request User: " + tokenVo.getUsername());
            }
            pass = pass && in;

            boolean permitted = true;
            if (pass) {
              User u = loginResult.getUser();
              List<Access> access = accessService.findAccessByRole(roleService.findRoleByUserId(u.getId()));
              for (int accessId : annotation.accessInNeed()) {
                boolean find = false;
                for (Access thisAccess : access) {
                  find = find || (thisAccess.getId() == accessId);

                  if (find) break;
                }
                if (!find) {
                  msgBuilder.append("|PERMISSION " + accessId + " is in need|");
                }
                permitted = permitted && find;
              }

              pass = pass && permitted;
            }
            if (pass) {
              log.info("TOKEN 验证通过，TokenInterceptor拦截器放行");
              return true;
            }
          }
        }
      }
    }


    response.setContentType("application/json;charset=utf8");

    try {
      JSONObject jsonObject = new JSONObject();
      jsonObject.put("message", msgBuilder.toString());
      jsonObject.put("code", HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
      response.getWriter().append(jsonObject.toString());
      log.info("验证失败，未通过TokenInterceptor拦截器");
    } catch (
      Exception e) {
      log.error(e.getMessage());
      return false;
    }

    return false;
  }
}
