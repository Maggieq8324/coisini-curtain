package com.coisini.curtain.core.validators;

import com.coisini.curtain.core.annotation.TokenPassword;
import org.apache.commons.lang3.StringUtils;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * @Description Token Password 校验关联类
 * @author coisini
 * @date Aug 17, 2021
 * @Version 1.0
 */
public class TokenPasswordValidator implements ConstraintValidator<TokenPassword, String> {

    private Integer min;
    private Integer max;

    @Override
    public void initialize(TokenPassword constraintAnnotation) {
        this.min = constraintAnnotation.min();
        this.max = constraintAnnotation.max();
    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        if(StringUtils.isEmpty(s)){
            return true;
        }

        return s.length() >= this.min && s.length() <= this.max;
    }
}
