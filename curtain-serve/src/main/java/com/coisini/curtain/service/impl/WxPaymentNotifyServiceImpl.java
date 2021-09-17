package com.coisini.curtain.service.impl;

import com.coisini.curtain.core.enumeration.OrderStatus;
import com.coisini.curtain.exception.http.ServerErrorException;
import com.coisini.curtain.entity.Order;
import com.coisini.curtain.repository.OrderRepository;
import com.coisini.curtain.service.WxPaymentNotifyService;
import com.github.wxpay.sdk.WXPay;
import com.github.wxpay.sdk.WXPayUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Objects;
import java.util.Optional;

/**
 * @Description 微信支付消息实现类
 * @author coisini
 * @date Aug 23, 2021
 * @Version 1.0
 */
@Service
public class WxPaymentNotifyServiceImpl implements WxPaymentNotifyService {

    @Autowired
    private WxPaymentServiceImpl wxPaymentServiceImpl;

    @Autowired
    private OrderRepository orderRepository;

    /**
     * 微信支付回调消息处理
     * @param notify 消息
     */
    @Override
    public void processPayNotify(String notify) {
        Map<String, String> dataMap;
        try {
            dataMap = WXPayUtil.xmlToMap(notify);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServerErrorException(9999);
        }

        /**
         * 验证签名是否有效
         */
        WXPay wxPay = wxPaymentServiceImpl.assembleWxPayConfig();
        boolean valid;
        try {
            valid = wxPay.isResponseSignatureValid(dataMap);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServerErrorException(9999);

        }
        if (!valid) {
            throw new ServerErrorException(9999);
        }

        String returnCode = dataMap.get("return_code");
        // 订单号
        String orderNo = dataMap.get("out_trade_no");
        String resultCode = dataMap.get("result_code");

        if (!"SUCCESS".equals(returnCode)) {
            throw new ServerErrorException(9999);
        }
        if (!"SUCCESS".equals(resultCode)) {
            throw new ServerErrorException(9999);
        }
        if (Objects.isNull(orderNo)) {
            throw new ServerErrorException(9999);
        }

        /**
         * 回调订单处理
         */
        this.deal(orderNo);
    }

    /**
     * 微信支付回调订单处理
     * @param orderNo
     */
    private void deal(String orderNo) {
        Optional<Order> orderOptional = orderRepository.findFirstByOrderNo(orderNo);
        Order order = orderOptional.orElseThrow(() -> new ServerErrorException(9999));

        int res = -1;
        if (order.getStatus().equals(OrderStatus.UNPAID.value())
                || order.getStatus().equals(OrderStatus.CANCELED.value())) {
            res = orderRepository.updateStatusByOrderNo(orderNo, OrderStatus.PAID.value());
        }
        if (res != 1) {
            throw new ServerErrorException(9999);
        }
    }

}
