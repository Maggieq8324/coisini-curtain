package com.coisini.curtain.evt;

import com.coisini.curtain.core.annotation.PasswordEquals;
import lombok.Builder;
import lombok.Getter;

/**
 * @Description 自定义注解校验类
 * @author coisini
 * @date Aug 10, 2021
 * @Version 1.0
 */
@Getter
@Builder
@PasswordEquals(min = 1, message = "Incorrect password length or passwords are not equal")
public class ValidEvt {

    private String password1;
    private String password2;

}
