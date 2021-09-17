package com.coisini.curtain.model;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @author generator@TaleLin
 * @since 2020-05-27
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true) // 链式赋值
@TableName("spu_detail_img")
public class SpuDetailImgDO extends BaseModel {


    private String img;

    private Integer spuId;

//    @TableField(value = "`index`")
    private Integer sort;


}
