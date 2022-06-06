package com.unncbandsclub.utopia.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.unncbandsclub.utopia.entity.Band;
import com.unncbandsclub.utopia.mapper.BandMapper;
import com.unncbandsclub.utopia.service.BandService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author Ruizhe Huang
 * @since 2022-05-22
 */
@Service
public class BandServiceImpl extends ServiceImpl<BandMapper, Band> implements BandService {
  @Resource
  BandMapper bandMapper;

  @Override
  public Band findById(Integer id) {
    return bandMapper.selectById(id);
  }

  @Override
  public Band findByName(String name) {
    return bandMapper.selectOne(new QueryWrapper<Band>().eq("band_name", name));
  }

  @Override
  public List<Band> findAll() {
    return bandMapper.selectList(null);
  }

  @Override
  public boolean deleteByName(String name) {
    return bandMapper.delete(new QueryWrapper<Band>().eq("band_name", name)) == 1;
  }

  @Override
  public Band registerBand(String name) {
    Band b = new Band();
    b.setBandName(name);
    int insert = bandMapper.insert(b);
    if (insert != 1)
      return null;
    return findByName(name);

  }

  @Override
  public Band registerBand(String name, String introduction) {
    Band b = new Band();
    b.setBandName(name);
    b.setIntroduction(introduction);
    int insert = bandMapper.insert(b);
    if (insert != -1)
      return null;
    return findByName(name);
  }


  @Deprecated
  @Override
  public Band registerBand(Band band) {
    int insert = bandMapper.insert(band);
    if (insert == -1)
      return null;
    return band;
  }
}
