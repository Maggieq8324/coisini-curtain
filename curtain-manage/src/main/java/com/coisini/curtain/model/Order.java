package com.coisini.curtain.model;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @Description Order
 * @author coisini
 * @date Sep 17, 2021
 * @Version 1.0
 */
@Data
@TableName(value = "sys_order", autoResultMap = true)
public class Order {

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private String orderNo;

    /**
     * user表外键
     */
    private Integer userId;

    private BigDecimal totalPrice;

    private Integer totalCount;

    private String snapImg;

    private String snapTitle;

    @TableField(typeHandler = JacksonTypeHandler.class)
    private List<Map<String, Object>> snapItems;

    @TableField(typeHandler = JacksonTypeHandler.class)
    private Map<String, String> snapAddress;

    private String prepayId;

    private BigDecimal finalTotalPrice;

    private Integer status;

    @JsonIgnore
    private Date createTime;

    @JsonIgnore
    @TableLogic
    private Date deleteTime;

    @JsonIgnore
    @TableField(update = "now()")
    private Date updateTime;

    private Date expiredTime;

    private Date placedTime;
}
