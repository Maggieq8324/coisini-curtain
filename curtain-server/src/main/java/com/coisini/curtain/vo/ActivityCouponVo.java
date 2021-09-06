package com.coisini.curtain.vo;

import com.coisini.curtain.entity.Activity;
import lombok.Getter;
import lombok.Setter;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Description Activity Coupon Vo
 * @author coisini
 * @date Aug 20, 2021
 * @Version 1.0
 */
@Getter
@Setter
public class ActivityCouponVo extends ActivityPureVo {

    private List<CouponPureVo> coupons;

    public ActivityCouponVo(Activity activity) {
        super(activity);
        coupons = activity.getCouponList()
                          .stream()
                          .map(CouponPureVo::new)
                          .collect(Collectors.toList());
    }
}
