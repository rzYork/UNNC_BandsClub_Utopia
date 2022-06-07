package com.unncbandsclub.utopia.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.unncbandsclub.utopia.entity.Avatar;
import com.unncbandsclub.utopia.mapper.AvatarMapper;
import com.unncbandsclub.utopia.service.AvatarService;
import com.unncbandsclub.utopia.service.OssService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class AvatarServiceImpl implements AvatarService {

  @Resource
  AvatarMapper mapper;

  @Resource
  OssService ossService;

  @Override
  public List<Avatar> findAll() {
    return mapper.selectList(null);
  }

  @Override
  public List<Avatar> findAvatarById(List<Integer> ids) {
    return mapper.selectList(new QueryWrapper<Avatar>().in("id", ids));
  }

  @Override
  public Avatar findAvatarById(Integer id) {
    return mapper.selectById(id);
  }

  @Override
  public String uploadAvatar(MultipartFile file) {
    String uploadUrl = ossService.upload(file);
    Avatar a = new Avatar();
    a.setUrl(uploadUrl);
    int insert = mapper.insert(a);
    if (insert != 1)
      return "";
    return uploadUrl;
  }
}
