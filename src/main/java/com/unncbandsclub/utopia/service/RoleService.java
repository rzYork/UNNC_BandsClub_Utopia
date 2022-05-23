package com.unncbandsclub.utopia.service;

import com.sun.istack.NotNull;
import com.unncbandsclub.utopia.entity.Role;
import com.baomidou.mybatisplus.extension.service.IService;
import com.unncbandsclub.utopia.entity.UserRole;

import java.util.List;

/**
 * <p>
 * 角色表 服务类
 * </p>
 *
 * @author Ruizhe Huang
 * @since 2022-05-17
 */
public interface RoleService extends IService<Role> {
    List<Role> findRoleByUserId(@NotNull Integer uid);

    Role findRoleById(Integer rid);
    List<Role> findRoleById(List<Integer> roleIds);

    boolean updatePlayerRoleList(Integer uid,List<Integer> newRoleIds);
    boolean removeUserRole(Integer uid, Integer rid);
    boolean addUserRole(Integer uid, Integer rid);
    UserRole findUserRole(Integer uid,Integer rid);


}
