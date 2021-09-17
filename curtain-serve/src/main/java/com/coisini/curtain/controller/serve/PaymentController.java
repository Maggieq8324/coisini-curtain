package com.coisini.curtain.controller.serve;

import com.coisini.curtain.core.annotation.ScopeLevel;
import com.coisini.curtain.lib.WxNotify;
import com.coisini.curtain.service.WxPaymentNotifyService;
import com.coisini.curtain.service.WxPaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.Positive;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

/**
 * @Description 微信支付控制器
 * @author coisini
 * @date Aug 23, 2021
 * @Version 1.0
 */
@RestController
@RequestMapping("payment")
@Validated
public class PaymentController {

    @Autowired
    private WxPaymentService wxPaymentService;

    @Autowired
    private WxPaymentNotifyService wxPaymentNotifyService;

    /**
     * 微信支付(预订单)
     * @param oid 订单ID
     * @return
     */
    @PostMapping("/pay/order/{id}")
    @ScopeLevel
    public Map<String,String> preWxOrder(@PathVariable(name = "id") @Positive Long oid) {
        return wxPaymentService.preOrder(oid);
    }

    /**
     * 微信支付回调
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/wx/notify")
    public String payCallback(HttpServletRequest request, HttpServletResponse response) {
        InputStream o;
        try {
            o = request.getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
            return WxNotify.fail();
        }

        String xml = WxNotify.readNotify(o);

        try{
            wxPaymentNotifyService.processPayNotify(xml);
        } catch (Exception e){
            return WxNotify.fail();
        }

        return WxNotify.success();
    }

}
