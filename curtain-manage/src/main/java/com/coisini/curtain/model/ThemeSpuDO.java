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
@TableName("theme_spu")
public class ThemeSpuDO {

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private Integer themeId;

    private Integer spuId;

}
