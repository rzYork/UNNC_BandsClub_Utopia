package com.unncbandsclub.utopia.pojo;

import com.unncbandsclub.utopia.entity.User;

public class LoginResult {
    public enum LoginFailReason{        USERNAME_NOT_EXIST,WRONG_PASSWORD,EMAIL_NOT_EXIST,ACCOUNT_INVALID;
    }
    private boolean success;
    private User user;
    private LoginFailReason loginFailReason;

    public boolean isSuccess() {
        return success;
    }

    public LoginFailReason getLoginFailReason() {
        return loginFailReason;
    }

    private LoginResult(){

    }

    public User getUser() {
        return user;
    }

    public static LoginResult success(User user){
        LoginResult loginResult = new LoginResult();
        loginResult.success=true;
        loginResult.user=user;
        return loginResult;
    }

    public static LoginResult fail(LoginFailReason reason){
        LoginResult loginResult=new LoginResult();
        loginResult.success=false;
        loginResult.loginFailReason=reason;
        return loginResult;
    }
}
