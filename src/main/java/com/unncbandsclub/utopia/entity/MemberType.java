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
@ApiModel(value="MemberType对象", description="")
public class MemberType implements Serializable {

    private static final long serialVersionUID=1L;

    @ApiModelProperty(value = "记录ID")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "记录创建时间")
    private Date createdTime;

    @ApiModelProperty(value = "记录更新时间")
    private Date updatedTime;

    @ApiModelProperty(value = "成员类型名称")
    private String name;

    @ApiModelProperty(value = "激活状态 1 or 0")
    private Boolean status;


}
