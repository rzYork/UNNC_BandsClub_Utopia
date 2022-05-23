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
 * 权限详情表
 * </p>
 *
 * @author Ruizhe Huang
 * @since 2022-05-17
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="Access对象", description="权限详情表")
public class Access implements Serializable {

    private static final long serialVersionUID=1L;

      @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "权限名称")
    private String name;

    @ApiModelProperty(value = "状态 1：有效 0：无效")
    private Boolean status;

    @ApiModelProperty(value = "最后一次更新时间")
    private Date updatedTime;

    @ApiModelProperty(value = "插入时间")
    private Date createdTime;


}
