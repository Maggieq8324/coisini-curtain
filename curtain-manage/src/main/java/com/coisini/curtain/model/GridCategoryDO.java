package com.coisini.curtain.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.util.Date;

/**
 * @author generator@TaleLin
 * @since 2020-06-01
 */
@Data
@TableName("grid_category")
public class GridCategoryDO {

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private String title;

    private String img;

    private String name;

    private Integer categoryId;

    private Integer rootCategoryId;

    @JsonIgnore
    private Date createTime;

    @JsonIgnore
    private Date updateTime;

    @TableLogic
    @JsonIgnore
    private Date deleteTime;

}
