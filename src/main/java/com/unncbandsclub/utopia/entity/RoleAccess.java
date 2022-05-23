package com.unncbandsclub.utopia.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 角色权限表
 * </p>
 *
 * @author Ruizhe Huang
 * @since 2022-05-17
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="RoleAccess对象", description="角色权限表")
public class RoleAccess implements Serializable {

    private static final long serialVersionUID=1L;

      @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "角色id")
    private Integer roleId;

    @ApiModelProperty(value = "权限id")
    private Integer accessId;

    @ApiModelProperty(value = "插入时间")
    private Date createdTime;


}
