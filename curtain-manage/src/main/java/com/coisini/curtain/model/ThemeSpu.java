package com.coisini.curtain.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @Description ThemeSpu
 * @author coisini
 * @date Sep 17, 2021
 * @Version 1.0
 */
@Data
@TableName("theme_spu")
public class ThemeSpu {

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private Integer themeId;

    private Integer spuId;

}
