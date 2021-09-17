package com.coisini.curtain.model;

import com.coisini.curtain.core.annotation.TokenPassword;
import com.coisini.curtain.core.enumeration.LoginType;
import lombok.Getter;
import lombok.Setter;
import javax.validation.constraints.NotBlank;

/**
 * @Description Token DTO
 * @author coisini
 * @date Aug 18, 2021
 * @Version 1.0
 */
@Getter
@Setter
public class TokenGetModel {

    @NotBlank(message = "account不允许为空")
    private String account;

    @TokenPassword(max=30, message = "{token.password}")
    private String password;

    private LoginType type;

}
