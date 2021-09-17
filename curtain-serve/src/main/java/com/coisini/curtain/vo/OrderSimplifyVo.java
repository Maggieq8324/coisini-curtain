package com.coisini.curtain.vo;

import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @Description Order Simplify Vo
 * @author coisini
 * @date Aug 23, 2021
 * @Version 1.0
 */
@Getter
@Setter
public class OrderSimplifyVo {
    private Long id;
    private String orderNo;
    private BigDecimal totalPrice;
    private Long totalCount;
    private String snapImg;
    private String snapTitle;
    private BigDecimal finalTotalPrice;
    private Integer status;
    private Date expiredTime;
    private Date placedTime;
    /**
     * 倒计时时长
     */
    private Long period;
}
