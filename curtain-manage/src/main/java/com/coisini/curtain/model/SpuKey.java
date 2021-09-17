package com.coisini.curtain.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @Description SpuKey
 * @author coisini
 * @date Sep 17, 2021
 * @Version 1.0
 */
@Data
@Accessors(chain = true)
@TableName("spu_key")
public class SpuKey {

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private Integer spuId;

    private Integer specKeyId;


}
