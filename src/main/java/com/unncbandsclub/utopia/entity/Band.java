package com.unncbandsclub.utopia.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="Band对象",description="bands详情表")
public class Band {

  private static final long serialVersionUID=1L;

  @TableId(value = "id", type = IdType.AUTO)
  private Integer id;

  @ApiModelProperty(value = "乐队名称")
  private String bandName;

  @ApiModelProperty(value = "状态 1：有效 0：无效")
  private Boolean status;

  @ApiModelProperty(value = "最后一次更新时间")
  private Date updatedTime;

  @ApiModelProperty(value = "插入时间")
  private Date createdTime;

  @ApiModelProperty(value="是否在校状态")
  private Boolean is_in_school;

  @ApiModelProperty(value="介绍")
  private String introduction;

}
