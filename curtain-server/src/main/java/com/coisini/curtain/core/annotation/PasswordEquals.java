package com.coisini.curtain.core.annotation;

import com.coisini.curtain.core.validators.PasswordValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * @Description 自定义参数校验注解
 *      @Documented 注解中的注释加入文档
 *      @Retention 注解保留阶段 RetentionPolicy.RUNTIME 运行阶段
 *      @Target 作用目标,注解的使用范围 TYPE:用于描述类、接口(包括注解类型) 或enum声明
 *      @Constraint 将注解和注解关联类关联到一起
 * @author coisini
 * @date Aug 10, 2021
 * @Version 1.0
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@Constraint(validatedBy = PasswordValidator.class)
public @interface PasswordEquals {

    int min() default 4;

    int max() default 6;

    String message() default "passwords are not equal";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
