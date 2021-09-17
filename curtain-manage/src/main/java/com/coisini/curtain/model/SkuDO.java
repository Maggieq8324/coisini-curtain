package com.coisini.curtain.model;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

/**
 * @author generator@TaleLin
 * @since 2020-05-23
 */
@Data
@TableName(value = "sku", autoResultMap = true)
public class SkuDO {

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @JsonIgnore
    private Date createTime;

    @JsonIgnore
    private Date updateTime;

    @TableLogic
    @JsonIgnore
    private Date deleteTime;

    private BigDecimal price;

    private BigDecimal discountPrice;

    private Integer online;

    private String img;

    private String title;

    private Integer spuId;

    @TableField(typeHandler = JacksonTypeHandler.class)
    private List<SpecKeyValueDO> specs;

    private String code;

    private Integer stock;

    private Integer categoryId;

    private Integer rootCategoryId;

    private String test;


}
