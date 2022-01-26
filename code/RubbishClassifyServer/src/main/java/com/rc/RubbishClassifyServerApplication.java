package com.rc;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@MapperScan("com.rc.**.mapper")
@SpringBootApplication
public class RubbishClassifyServerApplication   extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(RubbishClassifyServerApplication.class, args);
    }

    // 后续项目部署到linux的时候发现访问404,加上下述配置后方能正常访问
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(RubbishClassifyServerApplication.class);
    }

}
