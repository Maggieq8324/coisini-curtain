package com.coisini.curtain.common.enumeration;

/**
 * @author TaleLin
 */

public enum OnlineOrNotEnum {
    /**
     * 上线
     */
    ONLINE(1),
    /**
     * 非上线
     */
    NOT_ONLINE(0);

    private int value;

    OnlineOrNotEnum(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
