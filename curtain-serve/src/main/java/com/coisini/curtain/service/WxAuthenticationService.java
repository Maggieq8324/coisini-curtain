package com.coisini.curtain.service;

/**
 * @Description 微信认证接口
 * @author coisini
 * @date Aug 18, 2021
 * @Version 1.0
 */
public interface WxAuthenticationService {

    /**
     * 小程序code码登录
     * @param code
     * @return
     */
    String code2session(String code);
}
