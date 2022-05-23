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
 * 用户表
 * </p>
 *
 * @author Ruizhe Huang
 * @since 2022-05-17
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="User对象", description="用户表")
public class User implements Serializable {

    private static final long serialVersionUID=1L;

      @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "用户唯一标识UUID")
    private String uuid;

    @ApiModelProperty(value = "登录姓名")
    private String name;

    @ApiModelProperty(value = "默认昵称")
    private String nickname;

    @ApiModelProperty(value = "登录密码")
    private String password;

    @ApiModelProperty(value = "邮箱")
    private String addressEmail;

    @ApiModelProperty(value = "微信联系方式")
    private String addressWechat;

    @ApiModelProperty(value = "QQ联系方式")
    private String addressQq;

    @ApiModelProperty(value = "头像URL")
    private String avatar;

    @ApiModelProperty(value = "最后一次更新时间")
    private Date updatedTime;

    @ApiModelProperty(value = "插入时间")
    private Date createdTime;

    @ApiModelProperty(value = "上次登录时间")
    private Date lastLoginTime;

    @ApiModelProperty(value = "是否是超级管理员 1表示是 0 表示不是")
    private Boolean isAdmin;

    @ApiModelProperty(value = "状态 1：有效 0：无效")
    private Boolean status;

    @ApiModelProperty(value="性别 0不予展示 1男 2女 30 41")
    private Integer sex;

    @ApiModelProperty(value="实名")
    private String realName;

    @ApiModelProperty(value="学生ID")
    private Integer studentId;




}
