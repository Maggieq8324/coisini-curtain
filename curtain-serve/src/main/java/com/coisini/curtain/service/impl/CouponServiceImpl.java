package com.coisini.curtain.service.impl;

import com.coisini.curtain.core.enumeration.CouponStatus;
import com.coisini.curtain.exception.http.NotFoundException;
import com.coisini.curtain.exception.http.ParameterException;
import com.coisini.curtain.entity.Activity;
import com.coisini.curtain.entity.Coupon;
import com.coisini.curtain.entity.UserCoupon;
import com.coisini.curtain.repository.ActivityRepository;
import com.coisini.curtain.repository.CouponRepository;
import com.coisini.curtain.repository.UserCouponRepository;
import com.coisini.curtain.service.CouponService;
import com.coisini.curtain.util.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @Description 优惠劵实现类
 * @author coisini
 * @date Aug 20, 2021
 * @Version 1.0
 */
@Service
public class CouponServiceImpl implements CouponService {

    @Autowired
    private CouponRepository couponRepository;

    @Autowired
    private ActivityRepository activityRepository;

    @Autowired
    private UserCouponRepository userCouponRepository;

    @Override
    public List<Coupon> getByCategory(Long cid) {
        Date now = new Date();
        return couponRepository.findByCategory(cid, now);
    }

    @Override
    public List<Coupon> getWholeStoreCoupons() {
        Date now = new Date();
        return couponRepository.findByWholeStore(true, now);
    }

    @Override
    public void collectOneCoupon(Long uid, Long couponId) {
        couponRepository.findById(couponId)
                .orElseThrow(() -> new NotFoundException(40003));

        /* 根据优惠劵查询当前活动 */
        Activity activity = activityRepository.findByCouponListId(couponId)
                                .orElseThrow(() -> new NotFoundException(40010));

        /* 活动是否有效 */
        Date now = new Date();
        Boolean isIn = CommonUtil.isInTimeLine(now, activity.getStartTime(), activity.getEndTime());

        if (!isIn) {
            throw new ParameterException(40005);
        }

        /* 优惠劵是否领取 */
        userCouponRepository.findFirstByUserIdAndCouponId(uid, couponId)
                            .ifPresent((uc) -> { throw new ParameterException(40006); });

        UserCoupon userCoupon = UserCoupon.builder()
                                          .userId(uid)
                                          .couponId(couponId)
                                          .status(CouponStatus.AVAILABLE.getValue())
                                          .createTime(now)
                                          .build();

        userCouponRepository.saveAndFlush(userCoupon);

    }

    @Override
    public List<Coupon> getMyAvailableCoupons(Long uid) {
        return couponRepository.findMyAvailable(uid, new Date());
    }

    @Override
    public List<Coupon> getMyUsedCoupons(Long uid) {
        return couponRepository.findMyUsed(uid, new Date());
    }

    @Override
    public List<Coupon> getMyExpiredCoupons(Long uid) {
        return couponRepository.findMyExpired(uid, new Date());
    }

}
