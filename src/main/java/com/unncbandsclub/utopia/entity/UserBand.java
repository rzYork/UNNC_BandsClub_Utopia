package com.unncbandsclub.utopia.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author Ruizhe Huang
 * @since 2022-05-22
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="UserBand对象", description="")
public class UserBand implements Serializable {

    private static final long serialVersionUID=1L;

    @ApiModelProperty(value = "记录ID")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "用户id")
    private Integer uid;

    @ApiModelProperty(value = "乐队ID")
    private Integer bid;

    @ApiModelProperty(value = "记录创建时间 (成员进入乐队时间戳)")
    private Date createdTime;

    @ApiModelProperty(value = "记录更新时间")
    private Date updatedTime;

    @ApiModelProperty(value = "是否在役成员")
    private Boolean isServing;

    @ApiModelProperty(value = "上次离队时间戳")
    private Date quittedTime;

    @ApiModelProperty(value = "成员类型")
    private Integer memberType;


}
