package com.coisini.curtain.service.impl;

import com.alibaba.fastjson.JSON;
import com.coisini.curtain.core.common.LocalUser;
import com.coisini.curtain.exception.http.ForbiddenException;
import com.coisini.curtain.exception.http.NotFoundException;
import com.coisini.curtain.exception.http.ParameterException;
import com.coisini.curtain.exception.http.ServerErrorException;
import com.coisini.curtain.entity.Order;
import com.coisini.curtain.repository.OrderRepository;
import com.coisini.curtain.service.OrderService;
import com.coisini.curtain.service.WxPaymentService;
import com.coisini.curtain.util.CommonUtil;
import com.coisini.curtain.util.HttpRequestProxy;
import com.github.wxpay.sdk.MyWxPayConfig;
import com.github.wxpay.sdk.WXPay;
import com.github.wxpay.sdk.WXPayConstants;
import com.github.wxpay.sdk.WXPayUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * @Description 微信支付实现类
 * @author coisini
 * @date Aug 23, 2021
 * @Version 1.0
 */
@Service
@Slf4j
public class WxPaymentServiceImpl implements WxPaymentService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderService orderService;

    /**
     * 微信支付配置类
     */
    private static MyWxPayConfig myWxPayConfig = new MyWxPayConfig();

    @Value("${coisini.order.pay-callback-host}")
    private String payCallbackHost;

    @Value("${coisini.order.pay-callback-path}")
    private String payCallbackPath;

    /**
     * 微信支付(预订单)
     * @param oid 订单ID
     * @return
     */
    @Override
    public Map<String, String> preOrder(Long oid) {
        /**
         * 获取订单
         */
        Long uid = LocalUser.getUser().getId();
        Optional<Order> optionalOrder = orderRepository.findFirstByUserIdAndId(uid, oid);

        Order order = optionalOrder.orElseThrow(() -> new NotFoundException(50009));

        /**
         * 订单是否取消校验
         */
        if (order.needCancel()) {
            throw new ForbiddenException(50010);
        }

        /**
         * 微信统一下单
         */
        WXPay wxPay = assembleWxPayConfig();

        Map<String, String> params = this.makePreOrderParams(order.getFinalTotalPrice(), order.getOrderNo());
        Map<String, String> wxOrder;
        try {
            log.info("微信统一下单unifiedOrder入参：【" + JSON.toJSONString(params) + "】");
            wxOrder = wxPay.unifiedOrder(params);
            log.info("微信统一下单unifiedOrder出参：【" + JSON.toJSONString(wxOrder) + "】");
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServerErrorException(9999);
        }

        /**
         * 更新支付ID
         */
        if (this.unifiedOrderSuccess(wxOrder)) {
            orderService.updateOrderPrepayId(order.getId(), wxOrder.get("prepay_id"));
        }

        /**
         * 签名计算
         */
        return makePaySignature(wxOrder);
    }

    /**
     * 微信下单接口参数配置
     * @param serverFinalPrice 订单价格
     * @param orderNo 订单号
     * @return
     */
    private Map<String, String> makePreOrderParams(BigDecimal serverFinalPrice, String orderNo) {
        Map<String, String> param = new HashMap<>();

        // 微信支付回调
        String payCallbackUrl = this.payCallbackHost + this.payCallbackPath;

        // 商品标题
        param.put("body", "coisini");
        // 订单号
        param.put("out_trade_no", orderNo);
        param.put("device_info", "coisini");
        param.put("fee_type", "CNY");
        param.put("trade_type", "JSAPI");

        param.put("total_fee", CommonUtil.yuanToCentsPlainString(serverFinalPrice));
        param.put("openid", LocalUser.getUser().getOpenid());
        // 用户IP获取
        param.put("spbill_create_ip", HttpRequestProxy.getRemoteRealIp());

        // 微信回调接口
        param.put("notify_url", payCallbackUrl);
        return param;
    }

    /**
     * WXPay 获取
     * @return
     */
    public WXPay assembleWxPayConfig() {
        WXPay wxPay = null;
        try {
            wxPay = new WXPay(WxPaymentServiceImpl.myWxPayConfig);
        } catch (Exception e) {
            throw new ServerErrorException(9999);
        }

        return wxPay;
    }

    /**
     * 微信支付(预订单) 返回值判断
     * @param wxOrder 预订单返回值
     * @return
     */
    private boolean unifiedOrderSuccess(Map<String, String> wxOrder) {
        if (!"SUCCESS".equals(wxOrder.get("return_code"))
                || !"SUCCESS".equals(wxOrder.get("result_code"))) {
            throw new ParameterException(10007);
        }
        return true;
    }

    /**
     * 微信支付(预订单) 签名计算
     * @param wxOrder
     * @return
     */
    private Map<String, String> makePaySignature(Map<String, String> wxOrder) {
        Map<String, String> wxPayMap = new HashMap<>();

        String packages = "prepay_id=" + wxOrder.get("prepay_id");

        wxPayMap.put("appId", WxPaymentServiceImpl.myWxPayConfig.getAppID());
        wxPayMap.put("timeStamp", CommonUtil.timestamp10());
        wxPayMap.put("nonceStr", RandomStringUtils.randomAlphanumeric(32));
        wxPayMap.put("package", packages);
        wxPayMap.put("signType", "HMAC-SHA256");

        String sign;
        try {
            sign = WXPayUtil.generateSignature(wxPayMap, WxPaymentServiceImpl.myWxPayConfig.getKey(),
                                                    WXPayConstants.SignType.HMACSHA256);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServerErrorException(9999);
        }

        Map<String, String> miniPayParams = new HashMap<>();

        miniPayParams.put("paySign", sign);
        miniPayParams.putAll(wxPayMap);
        miniPayParams.remove("appId");
        return miniPayParams;
    }

}
