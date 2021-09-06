package com.coisini.curtain.service;

import java.util.Map;

/**
 * @Description 微信支付接口
 * @author coisini
 * @date Aug 23, 2021
 * @Version 1.0
 */
public interface WxPaymentService {

    /**
     * 微信支付(预订单)
     * @param oid 订单ID
     * @return
     */
    Map<String, String> preOrder(Long oid);

}
