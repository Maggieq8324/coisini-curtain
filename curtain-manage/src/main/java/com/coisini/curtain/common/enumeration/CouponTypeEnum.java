package com.coisini.curtain.common.enumeration;

/**
 * @author colorful@TaleLin
 */
public enum CouponTypeEnum {
    /**
     * 满减券
     */
    FULL_MONEY_CUT(1),
    /**
     * 折扣券
     */
    DISCOUNT(2),
    /**
     * 无门槛券
     */
    ALL(3),
    /**
     * 满金额折扣券
     */
    FULL_MONEY_DISCOUNT(4);

    private int value;

    CouponTypeEnum(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
