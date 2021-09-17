package com.coisini.curtain.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import java.util.HashMap;
import java.util.Map;

/**
 * @Description 异常Code码配置类
 * @author coisini
 * @date Aug 10, 2021
 * @Version 1.0
 */
@ConfigurationProperties(prefix = "coisini")
@PropertySource(value = "classpath:config/exception-code.properties")
@Component
public class ExceptionCodeConfiguration {

    private Map<Integer,String> codes = new HashMap<>();

    public Map<Integer, String> getCodes() {
        return codes;
    }

    public void setCodes(Map<Integer, String> codes) {
        this.codes = codes;
    }

    public String getMessage(int code) {
        String message = codes.get(code);
        return message;
    }

}
