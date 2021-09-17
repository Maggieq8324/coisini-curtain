package com.coisini.curtain.model;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @Description Log
 * @author coisini
 * @date Sep 17, 2021
 * @Version 1.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("sys_log")
@EqualsAndHashCode(callSuper = true)
public class Log extends BaseModel implements Serializable {

    private static final long serialVersionUID = -7471474245124682011L;

    private String message;

    private Integer userId;

    private String username;

    private Integer statusCode;

    private String method;

    private String path;

    private String permission;

}
