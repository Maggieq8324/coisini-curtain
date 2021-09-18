package com.coisini.curtain.vo;

import lombok.Data;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @Description Order Simplify Vo
 * @author coisini
 * @date Sep 17, 2021
 * @Version 1.0
 */
@Data
public class OrderSimplifyVo {

    private Integer id;

    private String orderNo;

    private Integer userId;

    private BigDecimal totalPrice;

    private Integer totalCount;

    private String snapImg;

    private String snapTitle;

    private String prepayId;

    private BigDecimal finalTotalPrice;

    private Integer status;

    private Boolean expired;

    private Date createTime;

}
