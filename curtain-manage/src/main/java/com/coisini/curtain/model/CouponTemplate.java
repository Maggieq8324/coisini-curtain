package com.coisini.curtain.model;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @Description CouponTemplate
 * @author coisini
 * @date Sep 17, 2021
 * @Version 1.0
 */
@Data
@TableName("coupon_template")
public class CouponTemplate {

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private String title;

    private String description;

    private BigDecimal fullMoney;

    private BigDecimal minus;

    /**
     * 国内多是打折，国外多是百分比 off
     */
    private BigDecimal discount;

    /**
     * 1. 满减券 2.折扣券 3.无门槛券 4.满金额折扣券
     */
    private Integer type;

    @JsonIgnore
    private Date createTime;

    @JsonIgnore
    @TableField(update = "now()")
    private Date updateTime;

    @TableLogic
    @JsonIgnore
    private Date deleteTime;

}
