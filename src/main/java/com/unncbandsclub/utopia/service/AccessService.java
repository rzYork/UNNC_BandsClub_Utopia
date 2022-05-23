package com.unncbandsclub.utopia.service;

import com.unncbandsclub.utopia.entity.Access;
import com.baomidou.mybatisplus.extension.service.IService;
import com.unncbandsclub.utopia.entity.Role;

import java.util.List;

/**
 * <p>
 * 权限详情表 服务类
 * </p>
 *
 * @author Ruizhe Huang
 * @since 2022-05-17
 */
public interface AccessService extends IService<Access> {
    List<Access> findAccessByRole(List<Role> role);

    List<Access> findAll();

    Access findById(Integer accessId);
}
