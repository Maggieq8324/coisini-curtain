package com.coisini.curtain.core.enumeration;

/**
 * @Description 登录方式
 * @author coisini
 * @date Aug 17, 2021
 * @Version 1.0
 */
public enum LoginType {
    USER_WX(0, "微信登录"),
    USER_EMAIL(1, "邮箱登录");

    private Integer value;

    LoginType(Integer value, String description) {
        this.value = value;
    }
}
