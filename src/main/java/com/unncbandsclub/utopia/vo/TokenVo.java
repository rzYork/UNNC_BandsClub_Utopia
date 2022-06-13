package com.unncbandsclub.utopia.vo;


import lombok.Data;

import java.util.List;

@Data
public class TokenVo {
    private String username;
    private long loginTime;
    private String password;
    private List<Integer> roleList;
    private List<Integer> accessList;
}
