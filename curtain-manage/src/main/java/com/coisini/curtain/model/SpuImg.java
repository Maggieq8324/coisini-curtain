package com.coisini.curtain.model;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @Description SpuImg
 * @author coisini
 * @date Sep 17, 2021
 * @Version 1.0
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("spu_img")
public class SpuImg extends BaseModel {


    private String img;

    private Integer spuId;


}
