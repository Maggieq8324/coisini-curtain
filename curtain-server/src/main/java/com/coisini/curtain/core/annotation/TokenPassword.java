package com.coisini.curtain.core.annotation;

import com.coisini.curtain.core.validators.TokenPasswordValidator;
import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * @Description Token Password 校验注解
 * @author coisini
 * @date Aug 17， 2021
 * @Version 1.0
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.FIELD})
@Constraint(validatedBy = TokenPasswordValidator.class )
public @interface TokenPassword {
    String message() default "字段不符合要求";

    int min() default 6;

    int max() default 32;

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
