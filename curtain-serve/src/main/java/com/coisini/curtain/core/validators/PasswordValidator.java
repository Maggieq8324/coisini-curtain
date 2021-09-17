package com.coisini.curtain.core.validators;

import com.coisini.curtain.core.annotation.PasswordEquals;
import com.coisini.curtain.evt.ValidEvt;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * @Description 自定义校验注解关联类
 *      ConstraintValidator的第一个参数：注解的类型
 *      ConstraintValidator的第二个参数：自定义注解修饰的目标的类型
 * @author coisini
 * @date Aug 10, 2021
 * @Version 1.0
 */
public class PasswordValidator implements ConstraintValidator<PasswordEquals, ValidEvt> {

    private int min;
    private int max;

    /**
     * 初始化获取注解参数
     * @param constraintAnnotation
     */
    @Override
    public void initialize(PasswordEquals constraintAnnotation) {
        this.min = constraintAnnotation.min();
        this.max = constraintAnnotation.max();
    }

    /**
     * 校验参数
     * @param value
     * @param context
     * @return
     */
    @Override
    public boolean isValid(ValidEvt value, ConstraintValidatorContext context) {
        String password1 = value.getPassword1();
        String password2 = value.getPassword2();

        return password1.equals(password2) && this.validLength(password1, password2);
    }

    /**
     * 校验密码长度
     * @param password1
     * @param password2
     * @return
     */
    private boolean validLength(String password1, String password2) {
        return password1.length() > min && password1.length() < max
                && password2.length() > min && password2.length() < max;
    }

}
