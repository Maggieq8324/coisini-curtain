package com.coisini.curtain.common.aop;

import cn.hutool.core.util.StrUtil;
import com.coisini.curtain.common.configuration.CodeMessageConfiguration;
import com.coisini.curtain.vo.UnifyResponseVO;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * 处理返回结果为 UnifyResponseVO 的视图函数
 * 默认的返回均是英文，在此处通过code替换成中文
 *
 * @author pedro@TaleLin
 * @author colorful@TaleLin
 */
@Aspect
@Component
@Slf4j
public class ResultAspect {


    @Pointcut("execution(public * com.coisini.curtain.controller..*.*(..))")
    public void handlePlaceholder() {
    }

    @AfterReturning(returning = "ret", pointcut = "handlePlaceholder()")
    public void doAfterReturning(Object ret) throws Throwable {
        if (ret instanceof UnifyResponseVO) {
            UnifyResponseVO result = (UnifyResponseVO) ret;
            int code = result.getCode();
            String message = CodeMessageConfiguration.getMessage(code);
            if (StrUtil.isNotBlank(message) && StrUtil.isBlank((CharSequence) result.getMessage())) {
                result.setMessage(message);
            }
        }
    }
}
