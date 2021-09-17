package com.coisini.curtain.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @Description SkuSpec
 * @author coisini
 * @date Sep 17, 2021
 * @Version 1.0
 */
@Data
@TableName("sku_spec")
public class SkuSpec {

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private Integer spuId;

    private Integer skuId;

    private Integer keyId;

    private Integer valueId;


}
