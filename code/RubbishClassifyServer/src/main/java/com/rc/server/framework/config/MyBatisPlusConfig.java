package com.rc.server.framework.config;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @ClassName MyBatisPlusConfig
 * @Description 配置分页插件
 * @Author
 * @Date 2020/5/8 14:24
 * @Version
 **/

@Configuration
public class MyBatisPlusConfig {

    /**
     * 分页插件
     *
     * @return
     */
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        return new PaginationInterceptor();
    }

}
