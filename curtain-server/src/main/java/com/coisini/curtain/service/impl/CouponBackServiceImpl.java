package com.coisini.curtain.service.impl;

import com.coisini.curtain.model.OrderMessageModel;
import com.coisini.curtain.core.enumeration.OrderStatus;
import com.coisini.curtain.exception.http.ServerErrorException;
import com.coisini.curtain.entity.Order;
import com.coisini.curtain.repository.OrderRepository;
import com.coisini.curtain.repository.UserCouponRepository;
import com.coisini.curtain.service.CouponBackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;

/**
 * @Description 优惠劵返还实现类
 * @author coisini
 * @date Aug 25, 2021
 * @Version 1.0
 */
@Service
public class CouponBackServiceImpl implements CouponBackService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private UserCouponRepository userCouponRepository;

    /**
     * 优惠劵归还
     * @param orderMessageModel
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void returnBack(OrderMessageModel orderMessageModel) {
        Long couponId = orderMessageModel.getCouponId();
        Long uid = orderMessageModel.getUserId();
        Long orderId = orderMessageModel.getOrderId();

        if (couponId == -1) {
            return;
        }

        Optional<Order> optional = orderRepository.findFirstByUserIdAndId(uid, orderId);
        Order order = optional.orElseThrow(() ->new ServerErrorException(9999));

        if (order.getStatusEnum().equals(OrderStatus.UNPAID)
                || order.getStatusEnum().equals(OrderStatus.CANCELED)) {
            userCouponRepository.returnBack(couponId, uid);
        }
    }
}
