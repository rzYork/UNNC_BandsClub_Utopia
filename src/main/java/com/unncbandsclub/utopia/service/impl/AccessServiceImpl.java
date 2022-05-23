package com.unncbandsclub.utopia.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.unncbandsclub.utopia.entity.Access;
import com.unncbandsclub.utopia.entity.Role;
import com.unncbandsclub.utopia.entity.RoleAccess;
import com.unncbandsclub.utopia.mapper.AccessMapper;
import com.unncbandsclub.utopia.mapper.RoleAccessMapper;
import com.unncbandsclub.utopia.service.AccessService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 权限详情表 服务实现类
 * </p>
 *
 * @author Ruizhe Huang
 * @since 2022-05-17
 */
@Service
public class AccessServiceImpl extends ServiceImpl<AccessMapper, Access> implements AccessService {

    @Resource
    AccessMapper accessMapper;
    @Resource
    RoleAccessMapper roleAccessMapper;

    @Override
    public List<Access> findAccessByRole(List<Role> role) {
        List<Integer> roleIds = role.stream().map(Role::getId).collect(Collectors.toList());
        List<RoleAccess> roleAccess = roleIds.isEmpty()?new ArrayList<>():roleAccessMapper.selectList(new QueryWrapper<RoleAccess>().in("role_id", roleIds));
        List<Integer> accessIds = roleAccess.stream().map(RoleAccess::getAccessId).collect(Collectors.toList());
        List<Access> accessList = accessIds.isEmpty()?new ArrayList<>():accessMapper.selectList(new QueryWrapper<Access>().in("id", accessIds));
        return accessList;
    }

    @Override
    public List<Access> findAll() {
        return accessMapper.selectList(null);
    }


    @Override
    public Access findById(Integer accessId) {
        return accessMapper.selectById(accessId);
    }
}
