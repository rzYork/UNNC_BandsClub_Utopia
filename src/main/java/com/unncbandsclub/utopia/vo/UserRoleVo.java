package com.unncbandsclub.utopia.vo;

import lombok.Data;

import java.util.List;



@Data
public class UserRoleVo {
    private String username;
    private List<Integer> newRoleList;
}
