package com.unncbandsclub.utopia.service.impl;

import com.unncbandsclub.utopia.entity.MemberType;
import com.unncbandsclub.utopia.mapper.MemberTypeMapper;
import com.unncbandsclub.utopia.service.MemberTypeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Ruizhe Huang
 * @since 2022-05-22
 */
@Service
public class MemberTypeServiceImpl extends ServiceImpl<MemberTypeMapper, MemberType> implements MemberTypeService {

  @Resource
  MemberTypeMapper memberTypeMapper;
    @Override
    public List<MemberType> findAll() {
        return memberTypeMapper.selectList(null);
    }
}
