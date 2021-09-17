package com.coisini.curtain.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import java.util.Date;

/**
 * @Description Banner
 * @author coisini
 * @date Sep 17, 2021
 * @Version 1.0
 */
@Getter
@Setter
@TableName("banner")
public class Banner {

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private String name;

    private String title;

    private String description;

    private String img;

    @JsonIgnore
    private Date createTime;

    @JsonIgnore
    private Date updateTime;

    @JsonIgnore
    @TableLogic
    private Date deleteTime;
}
