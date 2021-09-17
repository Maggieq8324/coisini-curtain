package com.coisini.curtain.model;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import java.io.Serializable;

/**
 * @Description UserIdentity
 * @author coisini
 * @date Sep 17, 2021
 * @Version 1.0
 */
@Setter
@Getter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("lin_user_identity")
public class UserIdentity extends BaseModel implements Serializable {

    private static final long serialVersionUID = 456555840105356178L;

    /**
     * 用户id
     */
    private Integer userId;

    /**
     * 认证类型，例如 username_password，用户名-密码认证
     */
    private String identityType;

    /**
     * 认证，例如 用户名
     */
    private String identifier;

    /**
     * 凭证，例如 密码
     */
    private String credential;
}
