package com.coisini.curtain.core.money;

import java.math.BigDecimal;

/**
 * @Description 折扣价 接口
 * @author coisini
 * @date Aug 22, 2021
 * @Version 1.0
 */
public interface IMoneyDiscount {

    /**
     * 折扣价计算
     * @param original 原价
     * @param discount 折扣
     * @return
     */
    BigDecimal discount(BigDecimal original, BigDecimal discount);

}
