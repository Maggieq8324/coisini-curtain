package com.coisini.curtain.service.impl;

import com.coisini.curtain.core.common.LocalUser;
import com.coisini.curtain.core.enumeration.CouponStatus;
import com.coisini.curtain.core.enumeration.OrderStatus;
import com.coisini.curtain.core.money.IMoneyDiscount;
import com.coisini.curtain.model.OrderModel;
import com.coisini.curtain.model.SkuInfoModel;
import com.coisini.curtain.exception.http.ForbiddenException;
import com.coisini.curtain.exception.http.NotFoundException;
import com.coisini.curtain.exception.http.ParameterException;
import com.coisini.curtain.logic.CouponChecker;
import com.coisini.curtain.logic.OrderChecker;
import com.coisini.curtain.entity.*;
import com.coisini.curtain.repository.CouponRepository;
import com.coisini.curtain.repository.OrderRepository;
import com.coisini.curtain.repository.SkuRepository;
import com.coisini.curtain.repository.UserCouponRepository;
import com.coisini.curtain.service.OrderService;
import com.coisini.curtain.service.SkuService;
import com.coisini.curtain.util.CommonUtil;
import com.coisini.curtain.util.OrderUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @Description Order 实现类
 * @author coisini
 * @date Aug 21, 2021
 * @Version 1.0
 */
@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private SkuService skuService;

    @Autowired
    private CouponRepository couponRepository;

    @Autowired
    private UserCouponRepository userCouponRepository;

    @Autowired
    private IMoneyDiscount iMoneyDiscount;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private SkuRepository skuRepository;

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Value("${coisini.order.max-sku-limit}")
    private int maxSkuLimit;

    @Value("${coisini.order.pay-time-limit}")
    private Integer payTimeLimit;

    @Override
    public OrderChecker isOk(Long uid, OrderModel orderModel) {
        if (orderModel.getFinalTotalPrice().compareTo(new BigDecimal("0")) <= 0) {
            throw new ParameterException(50011);
        }

        List<Long> skuIdList = orderModel.getSkuInfoList()
                                        .stream()
                                        .map(SkuInfoModel::getId)
                                        .collect(Collectors.toList());

        /**
         * 服务端Sku
         */
        List<Sku> skuList = skuService.getSkuListByIds(skuIdList);

        /**
         * 优惠劵校验获取
         */
        Long couponId = orderModel.getCouponId();
        CouponChecker couponChecker = null;
        if (Objects.nonNull(couponId)) {
            Coupon coupon = couponRepository.findById(couponId)
                                            .orElseThrow(() -> new NotFoundException(40004));
            UserCoupon userCoupon = userCouponRepository.findFirstByUserIdAndCouponIdAndStatus(uid, couponId, CouponStatus.AVAILABLE.getValue())
                                            .orElseThrow(() -> new NotFoundException(50006));

            couponChecker = new CouponChecker(coupon, iMoneyDiscount);
        }

        /**
         * 订单校验
         */
        OrderChecker orderChecker = new OrderChecker(orderModel, skuList, couponChecker, maxSkuLimit);
        orderChecker.isOk();
        return orderChecker;
    }

    /**
     * 下单
     * @param uid 用户ID
     * @param orderModel 订单参数
     * @param orderChecker 订单校验
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public Long placeOrder(Long uid, OrderModel orderModel, OrderChecker orderChecker) {

        Calendar now = Calendar.getInstance();
        Calendar now1 = (Calendar) now.clone();
        // 过期时间
        Date expiredTime = CommonUtil.addSomeSeconds(now, this.payTimeLimit).getTime();

        Order order = Order.builder()
                           .orderNo(OrderUtil.makeOrderNo())
                           .totalPrice(orderModel.getTotalPrice())
                           .finalTotalPrice(orderModel.getFinalTotalPrice())
                           .userId(uid)
                           .totalCount(orderChecker.getTotalCount().longValue())
                           .snapImg(orderChecker.getLeaderImg())
                           .snapTitle(orderChecker.getLeaderTitle())
                           .status(OrderStatus.UNPAID.value())
                           .expiredTime(expiredTime)
                           .placedTime(now1.getTime())
                           .build();

        order.setSnapAddress(orderModel.getAddress());
        order.setSnapItems(orderChecker.getOrderSkuList());

        orderRepository.save(order);

        // 库存扣减
        this.reduceStock(orderChecker);

        // 优惠劵核销
        Long couponId = -1L;
        if (Objects.nonNull(orderModel.getCouponId())) {
            // 默认写入优惠劵ID为 -1
            writeOffCoupon(orderModel.getCouponId(), order.getId(), uid);
            couponId = orderModel.getCouponId();
        }

        // 加入到延迟消息队列
        sendToRedis(order.getId(), uid, couponId);

        return order.getId();
    }

    /**
     * 保存到redis
     * @param oid 订单ID
     * @param uid 用户ID
     * @param couponId 优惠劵ID
     */
    private void sendToRedis(Long oid, Long uid, Long couponId) {
        String key = uid.toString() + "," + oid.toString() + "," + couponId.toString();

        try {
            redisTemplate.opsForValue().set(key, "1", payTimeLimit, TimeUnit.SECONDS);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 库存扣减
     * @param orderChecker
     */
    private void reduceStock(OrderChecker orderChecker) {
        List<OrderSku> orderSkuList = orderChecker.getOrderSkuList();

        for (OrderSku orderSku : orderSkuList) {
            int count = skuRepository.reduceStock(orderSku.getId(), orderSku.getCount().longValue());

            if (count != 1) {
                throw new ParameterException(50003);
            }
        }

    }

    /**
     * 优惠券核销
     * @param couponId
     * @param oid
     * @param uid
     */
    private void writeOffCoupon(Long couponId, Long oid, Long uid) {
        int result = this.userCouponRepository.writeOff(couponId, oid, uid);
        if (result != 1) {
            throw new ForbiddenException(40012);
        }
    }

    /**
     * 查询待支付订单
     * @param page
     * @param size
     * @return
     */
    @Override
    public Page<Order> getUnpaid(Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("createTime").descending());
        Long uid = LocalUser.getUser().getId();
        Date now = new Date();
        return orderRepository.findByExpiredTimeGreaterThanAndStatusAndUserId(now,
                                            OrderStatus.UNPAID.value(), uid, pageable);
    }

    /**
     * 根据状态获取订单
     * @param status
     * @param page
     * @param size
     * @return
     */
    @Override
    public Page<Order> getByStatus(Integer status, Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("createTime").descending());
        Long uid = LocalUser.getUser().getId();
        if (status == OrderStatus.All.value()) {
            return orderRepository.findByUserId(uid, pageable);
        }
        return orderRepository.findByUserIdAndStatus(uid, status, pageable);
    }

    /**
     * 获取订单详情
     * @param oid 订单ID
     * @return
     */
    @Override
    public Optional<Order> getOrderDetail(Long oid) {
        Long uid = LocalUser.getUser().getId();
        return orderRepository.findFirstByUserIdAndId(uid, oid);
    }

    /**
     * 更新支付ID
     * @param orderId 订单ID
     * @param prePayId 支付ID
     */
    @Override
    public void updateOrderPrepayId(Long orderId, String prePayId) {
        Optional<Order> order = orderRepository.findById(orderId);
        order.ifPresent(o -> {
            o.setPrepayId(prePayId);
            orderRepository.saveAndFlush(o);
        });
        order.orElseThrow(() -> new ParameterException(10007));
    }
}
