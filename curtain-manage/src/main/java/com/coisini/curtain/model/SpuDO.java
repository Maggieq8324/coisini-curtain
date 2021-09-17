package com.coisini.curtain.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * @author generator@TaleLin
 * @since 2020-05-23
 */
@Data
@TableName("spu")
public class SpuDO {

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @JsonIgnore
    private Date createTime;

    @JsonIgnore
    private Date updateTime;

    @TableLogic
    @JsonIgnore
    private Date deleteTime;

    private String title;

    private String subtitle;

    private Integer categoryId;

    private Integer rootCategoryId;

    private Integer online;

    /**
     * 文本型价格，有时候SPU需要展示的是一个范围，或者自定义平均价格
     */
    private String price;

    /**
     * 某种规格可以直接附加单品图片
     */
    private Integer sketchSpecId;

    /**
     * 默认选中的sku
     */
    private Integer defaultSkuId;

    private String img;

    private String discountPrice;

    private String description;

    private String tags;

    private Integer isTest;

    private String spuThemeImg;

    private String forThemeImg;


}
