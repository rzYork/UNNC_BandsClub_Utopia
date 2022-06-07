package com.unncbandsclub.utopia.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.unncbandsclub.utopia.entity.Role;
import com.unncbandsclub.utopia.entity.UserRole;
import com.unncbandsclub.utopia.mapper.RoleMapper;
import com.unncbandsclub.utopia.mapper.UserRoleMapper;
import com.unncbandsclub.utopia.service.RoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 角色表 服务实现类
 * </p>
 *
 * @author Ruizhe Huang
 * @since 2022-05-17
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {
    @Resource
    RoleMapper roleMapper;
    @Resource
    UserRoleMapper userRoleMapper;

    @Override
    public List<Role> findRoleByUserId(Integer uid) {
        if(uid==null)
            return new ArrayList<>();
        List<Integer> roleIdList=
                userRoleMapper.selectList(new QueryWrapper<UserRole>().in("uid",uid))
                        .stream()
                        .map(UserRole::getRoleId)
                        .collect(Collectors.toList());
        List<Role> roles= roleIdList.isEmpty()?new ArrayList<>():roleMapper.selectList(new QueryWrapper<Role>().in("id", roleIdList));
        return roles;
    }

    @Override
    public Role findRoleById(Integer rid) {
        return roleMapper.selectById(rid);
    }

    @Override
    public List<Role> findRoleById(List<Integer> roleIds) {
        return roleIds.isEmpty()?new ArrayList<>():roleMapper.selectList(new QueryWrapper<Role>().in("id",roleIds));
    }

    @Override
    public boolean updatePlayerRoleList(Integer uid, List<Integer> newRoleIds) {
        List<Role> uRoleList = findRoleByUserId(uid);
        Iterator<Role> iterator = uRoleList.iterator();
        while(iterator.hasNext()){
            Role next = iterator.next();
            if(!newRoleIds.contains(next.getId())){
                removeUserRole(uid,next.getId());
            }else{
                newRoleIds.remove(next.getId());
            }
        }
        for (Integer newRoleId : newRoleIds) {
            addUserRole(uid,newRoleId);
        }
        return true;
    }



    @Override
    public boolean removeUserRole(Integer uid, Integer rid) {
        return userRoleMapper.delete(new QueryWrapper<UserRole>().eq("uid",uid).eq("role_id",rid))>0;
    }

    @Override
    public boolean addUserRole(Integer uid, Integer rid) {
        if(findUserRole(uid,rid)!=null)
            return false;
        UserRole userRole = new UserRole();
        userRole.setRoleId(rid);
        userRole.setUid(uid);
        userRoleMapper.insert(userRole);
        return true;
    }

    @Override
    public UserRole findUserRole(Integer uid, Integer rid) {
        return userRoleMapper.selectOne(new QueryWrapper<UserRole>().eq("uid",uid).eq("role_id",rid));
    }
}
