package com.coisini.curtain.service.impl;

import com.coisini.curtain.model.OrderMessageModel;
import com.coisini.curtain.exception.http.ServerErrorException;
import com.coisini.curtain.entity.Order;
import com.coisini.curtain.repository.OrderRepository;
import com.coisini.curtain.repository.SkuRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * @Description 订单取消实现类
 * @author coisini
 * @date Aug 25, 2021
 * @Version 1.0
 */
@Service
public class OrderCancelServiceImpl implements OrderCancelService{

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private SkuRepository skuRepository;

    /**
     * 订单取消
     * @param messageBo
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void cancel(OrderMessageModel orderMessageModel) {
        if (orderMessageModel.getOrderId() <= 0) {
            throw new ServerErrorException(9999);
        }

        cancel(orderMessageModel.getOrderId());
    }

    /**
     * 订单取消
     * @param oid 订单ID
     */
    private void cancel(Long oid) {
        Optional<Order> orderOptional = orderRepository.findById(oid);

        Order order = orderOptional.orElseThrow(() -> new ServerErrorException(9999));

        // 订单取消
        int count = orderRepository.cancelOrder(oid);

        if (count != 1) {
            return;
        }

        order.getSnapItems().forEach(item -> {
            // 库存归还
            skuRepository.recoverStock(item.getId(), item.getCount().longValue());
        });

    }
}
