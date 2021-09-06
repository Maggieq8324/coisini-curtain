package com.coisini.curtain.service;

/**
 * @Description 微信支付消息接口
 * @author coisini
 * @date Aug 23, 2021
 * @Version 1.0
 */
public interface WxPaymentNotifyService {

    /**
     * 微信支付回调消息处理
     * @param notify 消息
     */
    void processPayNotify(String notify);

}
