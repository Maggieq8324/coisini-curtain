package com.coisini.curtain.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * @Description Theme
 * @author coisini
 * @date Sep 17, 2021
 * @Version 1.0
 */
@Data
@TableName("theme")
public class Theme {

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

    private String description;

    private String name;

    private String tplName;

    private String entranceImg;

    private String extend;

    private String internalTopImg;

    private String titleImg;

    private Integer online;


}
