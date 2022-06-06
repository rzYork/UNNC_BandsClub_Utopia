package com.unncbandsclub.utopia.service;

import com.unncbandsclub.utopia.entity.MemberType;
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
public interface MemberTypeService extends IService<MemberType> {

    List<MemberType> findAll();
}
