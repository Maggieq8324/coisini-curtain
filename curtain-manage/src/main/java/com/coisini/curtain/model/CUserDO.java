package com.coisini.curtain.model;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.util.Date;
import java.util.List;
import java.util.Map;


/**
 * @author TaleLin
 */
@TableName(value = "user", autoResultMap = true)
@Data
public class CUserDO {

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private String openid;

    private String nickname;

    private String email;

    @JsonIgnore
    private String password;

    private String mobile;

    @TableField(typeHandler = JacksonTypeHandler.class)
    private Map<String, Object> wxProfile;

    @JsonIgnore
    private Date createTime;

    @JsonIgnore
    private Date updateTime;

    @JsonIgnore
    @TableLogic
    private Date deleteTime;
}
