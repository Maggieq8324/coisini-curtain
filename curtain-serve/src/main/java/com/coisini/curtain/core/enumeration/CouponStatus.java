package com.coisini.curtain.core.enumeration;

import java.util.stream.Stream;

/**
 * @Description 优惠劵
 * @author coisini
 * @date Aug 20, 2021
 * @Version 1.0
 */
public enum CouponStatus {

    AVAILABLE(1, "可以使用,未过期"),
    USED(2, "已使用"),
    EXPIRED(3, "未使用，已过期");

    private Integer value;

    public Integer getValue() {
        return this.value;
    }

    CouponStatus(Integer value, String description) {
        this.value = value;
    }

    public static CouponStatus toType(int value) {
        return Stream.of(CouponStatus.values())
                     .filter(item -> item.value == value)
                     .findAny()
                     .orElse(null);
    }

}
