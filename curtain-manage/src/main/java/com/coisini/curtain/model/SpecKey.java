package com.coisini.curtain.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.util.Date;

/**
 * @Description SpecKey
 * @author coisini
 * @date Sep 17, 2021
 * @Version 1.0
 */
@Data
@TableName("spec_key")
public class SpecKey {

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @JsonIgnore
    private Date createTime;

    @JsonIgnore
    private Date updateTime;

    @TableLogic
    @JsonIgnore
    private Date deleteTime;

    private String name;

    private String unit;

    private Integer standard;

    private String description;


}
