package com.coisini.curtain.core.money;

import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * @Description 向上取整
 * @author coisini
 * @date Aug 21, 2021
 * @Version 1.0
 */
@Component
public class UpRound implements IMoneyDiscount {

    @Override
    public BigDecimal discount(BigDecimal original, BigDecimal discount) {
        BigDecimal actualMoney = original.multiply(discount);

        /**
         * 向上取整
         */
        BigDecimal finalMoney = actualMoney.setScale(2, RoundingMode.UP);

        return finalMoney;
    }

}
