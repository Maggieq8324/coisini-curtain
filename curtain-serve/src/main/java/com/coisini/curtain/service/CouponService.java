package com.coisini.curtain.service;

import com.coisini.curtain.entity.Coupon;
import java.util.List;

/**
 * @Description 优惠劵接口
 * @author coisini
 * @date Aug 20, 2021
 * @Version 1.0
 */
public interface CouponService {

    /**
     * 根据二级分类ID查询优惠劵
     * @param cid 二级分类ID
     * @return
     */
    List<Coupon> getByCategory(Long cid);

    /**
     * 查询全场劵
     * @return
     */
    List<Coupon> getWholeStoreCoupons();

    /**
     * 优惠劵领取
     * @param uid 用户ID
     * @param couponId 优惠劵ID
     */
    void collectOneCoupon(Long uid, Long couponId);

    /**
     * 获取可使用的优惠劵
     * @param uid
     * @return
     */
    List<Coupon> getMyAvailableCoupons(Long uid);

    /**
     * 获取已使用的优惠劵
     * @param uid
     * @return
     */
    List<Coupon> getMyUsedCoupons(Long uid);

    /**
     * 获取已过期的优惠劵
     * @param uid
     * @return
     */
    List<Coupon> getMyExpiredCoupons(Long uid);

}
