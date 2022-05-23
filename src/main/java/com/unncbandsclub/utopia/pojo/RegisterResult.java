package com.unncbandsclub.utopia.pojo;

import com.unncbandsclub.utopia.entity.User;

public class RegisterResult{
    public enum RegisterFailReason{REGISTER_CLOSE,USERNAME_EXISTS,ILLEGAL_USERNAME,ILLEGAL_PASSWORD,WEAK_PASSWORD,INVALID_KEY}
    private boolean success;
    private User user;
    private RegisterFailReason registerFailReason;

    public boolean isSuccess() {
        return success;
    }

    public RegisterFailReason registerFailReason() {
        return registerFailReason;
    }

    private RegisterResult(){

    }

    public User getUser() {
        return user;
    }

    public static RegisterResult success(User user){
        RegisterResult r = new RegisterResult();
        r.success=true;
        r.user=user;
        return r;
    }

    public static RegisterResult fail(RegisterFailReason reason){
        RegisterResult r=new RegisterResult();
        r.success=false;
        r.registerFailReason=reason;
        return r;
    }
}
