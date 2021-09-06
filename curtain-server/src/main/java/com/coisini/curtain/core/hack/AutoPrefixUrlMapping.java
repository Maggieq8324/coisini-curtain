package com.coisini.curtain.core.hack;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;
import java.lang.reflect.Method;
import java.util.Objects;

/**
 * @Description 自动补全路由前缀处理类
 *      RequestMappingHandlerMapping 负责处理标注了@RequestMapping的控制器
 * @author coisini
 * @date Aug 10, 2021
 * @Version 1.0
 */
public class AutoPrefixUrlMapping extends RequestMappingHandlerMapping {

    /**
     * 读取基础包配置
     */
    @Value("${coisini.api-package}")
    private String bathApiPackagePath;

    /**
     * 重写方法路由获取
     * @param method
     * @param handlerType
     * @return
     */
    @Override
    protected RequestMappingInfo getMappingForMethod(Method method, Class<?> handlerType) {
        RequestMappingInfo mappingInfo = super.getMappingForMethod(method, handlerType);
        if (Objects.nonNull(mappingInfo)) {
            String prefix = this.getPrefix(handlerType);
            /**
             * RequestMappingInfo.paths(prefix).build() 根据前缀生成mappingInfo
             * combine(mappingInfo) 拼接原来的mappingInfo
             */
            return RequestMappingInfo.paths(prefix).build().combine(mappingInfo);
        }

        return mappingInfo;
    }

    /**
     * 获取方法路由前缀
     * @param handleType
     * @return
     */
    private String getPrefix(Class<?> handleType) {
        String packageName = handleType.getPackage().getName();
        String dotPath = packageName.replace(this.bathApiPackagePath, "").replace(".","/");
        return dotPath;
    }

}
