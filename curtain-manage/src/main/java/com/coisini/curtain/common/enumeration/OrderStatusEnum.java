package com.coisini.curtain.common.enumeration;

/**
 * @author colorful@TaleLin
 *
 * 订单状态枚举类
 */
public enum OrderStatusEnum {
    /**
     * 全部
     */
    ALL(0),
    /**
     * 待支付
     */
    UNPAID(1),
    /**
     * 已支付
     */
    PAID(2),
    /**
     * 已发货
     */
    DELIVERED(3),
    /**
     * 已完成
     */
    FINISHED(4),
    /**
     * 已取消
     */
    CANCELED(5);

    private int value;

    OrderStatusEnum(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
