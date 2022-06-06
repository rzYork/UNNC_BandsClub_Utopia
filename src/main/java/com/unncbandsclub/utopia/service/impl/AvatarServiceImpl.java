package com.unncbandsclub.utopia.service.impl;

import com.unncbandsclub.utopia.entity.Avatar;
import com.unncbandsclub.utopia.mapper.AvatarMapper;
import com.unncbandsclub.utopia.service.AvatarService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class AvatarServiceImpl implements AvatarService {

  @Resource
  AvatarMapper mapper;

  @Override
  public List<Avatar> findAll() {
    return mapper.selectList(null);
  }
}
