package com.unncbandsclub.utopia.service;

import com.unncbandsclub.utopia.entity.Band;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Ruizhe Huang
 * @since 2022-05-22
 */
public interface BandService extends IService<Band> {
  Band findById(Integer id);
  Band findByName(String name);
  List<Band> findAll();

  boolean deleteByName(String name);


  /**
   * 根据乐队名注册新的乐队 默认激活状态且为在校状态
   * @param name 乐队名
   * @return 虚新的Band Object 若名称重复，则返回null
   */
  Band registerBand(String name);
  Band registerBand(String name,String introduction);
  Band registerBand(Band band);
}
