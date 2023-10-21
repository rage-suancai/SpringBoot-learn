package com.springboot.CommonFrameworks.InterfaceDocument.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfiguration {

    @Bean
    public OpenAPI springDocOpenAPI() {

        return new OpenAPI().info(new Info()
                .title("图书管理系统 - 在线API接口文档")
                .description("这是一个图书管理系统的后端API文档，欢迎前端人员查阅")
                .version("2.0")
                .license(new License().name("我的Github个人主页")
                        .url("https://github.com/rage-suancai")));

    }

}
