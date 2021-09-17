package com.coisini.curtain.core.money;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * @Description 银行家模式
 * @author coisini
 * @date Aug 22, 2021
 * @Version 1.0
 */
//@Component
public class HalfEvenRound implements IMoneyDiscount {

    @Override
    public BigDecimal discount(BigDecimal original, BigDecimal discount) {
        BigDecimal actualMoney = original.multiply(discount);

        /**
         * 四舍六入，银行家模式
         * 五前为偶舍去，五前为奇要进一
         */
        BigDecimal finalMoney = actualMoney.setScale(2, RoundingMode.HALF_EVEN);

        return finalMoney;
    }

}
