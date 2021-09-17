package com.coisini.curtain.core.money;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * @Description 向上取整
 * @author coisini
 * @date Aug 22, 2021
 * @Version 1.0
 */
//@Component
public class HalfUpRound implements IMoneyDiscount{

    @Override
    public BigDecimal discount(BigDecimal original, BigDecimal discount) {

        BigDecimal actualMoney = original.multiply(discount);

        /**
         * 四舍五入
         */
        BigDecimal finalMoney = actualMoney.setScale(2, RoundingMode.HALF_UP);

        return finalMoney;
    }

}
