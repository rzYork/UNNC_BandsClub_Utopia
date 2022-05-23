package com.unncbandsclub.utopia.annotation;

import com.unncbandsclub.utopia.entity.Access;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface UserLoginToken {
    boolean required() default true;

    String[] whitelist() default {};  //限定用户名称

    int[] accessInNeed() default {};

}