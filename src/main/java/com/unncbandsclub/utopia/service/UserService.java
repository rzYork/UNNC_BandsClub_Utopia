package com.unncbandsclub.utopia.service;

import com.unncbandsclub.utopia.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;
import com.unncbandsclub.utopia.pojo.LoginResult;
import com.unncbandsclub.utopia.pojo.RegisterResult;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 用户表 服务类
 * </p>
 *
 * @author Ruizhe Huang
 * @since 2022-05-17
 */
@Service
public interface UserService extends IService<User> {

    RegisterResult register(String username, String password,String key);

    RegisterResult register(String username,String password);

    LoginResult login(String username, String password);

    User findUserByName(String username);

    List<User> findAll();

    boolean updateUser(User u);





}
