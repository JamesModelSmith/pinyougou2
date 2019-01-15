package com.pinyougou.pinyougouparent.common;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.web.MultipartAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
@EnableScheduling
@MapperScan(value = "com.pinyougou.pinyougouparent.pojo")
@EnableTransactionManagement
@EnableAutoConfiguration(exclude = { MultipartAutoConfiguration.class })
@EnableAsync

public class CispHistoryApplication {

    // 显示声明CommonsMultipartResolver为mutipartResolver
    @Bean(name = "multipartResolver")
    public CommonsMultipartResolver multipartResolver() {
        CommonsMultipartResolver resolver = new CommonsMultipartResolver();
        resolver.setDefaultEncoding("UTF-8");
        resolver.setResolveLazily(true);// resolveLazily属性启用是为了推迟文件解析，以在在UploadAction中捕获文件大小异常
        resolver.setMaxInMemorySize(40960);
        resolver.setMaxUploadSize(5 * 1024 * 1024 * 1024);// 上传文件大小 5G
        return resolver;
    }
}
