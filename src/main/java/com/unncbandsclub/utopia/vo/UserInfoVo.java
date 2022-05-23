package com.unncbandsclub.utopia.vo;


import lombok.Data;

@Data
public class UserInfoVo {
    private String username;
    private String nickname;
    private String addressEmail;
    private String addressWechat;
    private String addressQQ;
    private Integer sex;
    private String realName;
    private Integer studentId;
}
