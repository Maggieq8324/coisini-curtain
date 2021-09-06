package com.coisini.curtain.service.impl;

import com.coisini.curtain.model.OrderMessageModel;

/**
 * @Description 订单取消接口
 * @author coisini
 * @date Aug 25, 2021
 * @Version 1.0
 */
public interface OrderCancelService {

    /**
     * 订单取消
     * @param orderMessageModel
     */
    void cancel(OrderMessageModel orderMessageModel);

}
