package com.coisini.curtain.model;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.util.Date;

/**
 * @author generator@TaleLin
 * @since 2020-05-27
 */
@Data
@TableName("category")
public class CategoryDO {

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private String name;

    private String description;

    private Integer isRoot;

    private Integer parentId;

    private String img;

//    @TableField(value = "`index`" )
    private Integer sort;

    private Integer online;

    private Integer level;

    @JsonIgnore
    private Date createTime;

    @JsonIgnore
    private Date updateTime;

    @TableLogic
    @JsonIgnore
    private Date deleteTime;


}
