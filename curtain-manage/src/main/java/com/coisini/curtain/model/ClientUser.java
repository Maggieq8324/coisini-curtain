package com.coisini.curtain.model;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import java.util.Date;
import java.util.Map;

/**
 * @Description ClientUser
 * @author coisini
 * @date Sep 17, 2021
 * @Version 1.0
 */
@TableName(value = "user", autoResultMap = true)
@Data
public class ClientUser {

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
