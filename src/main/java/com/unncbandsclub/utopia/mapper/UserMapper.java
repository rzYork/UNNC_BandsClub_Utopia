package com.unncbandsclub.utopia.mapper;

import com.unncbandsclub.utopia.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 用户表 Mapper 接口
 * </p>
 *
 * @author Ruizhe Huang
 * @since 2022-05-17
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {

}
