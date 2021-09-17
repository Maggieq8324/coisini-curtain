package com.coisini.curtain.common.file;

import com.coisini.curtain.module.file.Uploader;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

/**
 * 文件上传配置类
 *
 * @author Juzi@TaleLin
 */
@Configuration
public class UploaderConfiguration {
    /**
     * @return 本地文件上传实现类
     */
    @Bean
    @Order
    @ConditionalOnMissingBean
    public Uploader uploader(){
        return new LocalUploader();
    }
}
