package com.coisini.curtain.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @author generator@TaleLin
 * @since 2020-06-03
 */
@Data
@TableName("sku_spec")
public class SkuSpecDO {

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private Integer spuId;

    private Integer skuId;

    private Integer keyId;

    private Integer valueId;


}
