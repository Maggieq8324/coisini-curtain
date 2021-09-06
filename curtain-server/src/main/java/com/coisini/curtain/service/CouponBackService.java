package com.coisini.curtain.service;

import com.coisini.curtain.model.OrderMessageModel;

/**
 * @Description 优惠劵返还接口
 * @author coisini
 * @date Aug 25, 2021
 * @Version 1.0
 */
public interface CouponBackService {

    /**
     * 优惠劵归还
     * @param orderMessageModel
     */
    void returnBack(OrderMessageModel orderMessageModel);

}
