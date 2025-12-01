package com.example.yaoshan.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Swagger 3.0配置类
 */
@Configuration
public class SwaggerConfig {

    /**
     * 配置OpenAPI信息
     */
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("养生项目API文档")
                        .version("1.0.0")
                        .description("养生项目的API文档，包含用户管理、产品管理、订单管理、社区管理、AI养生咨询等模块")
                        .license(new License().name("Apache 2.0").url("http://springdoc.org")));
    }
}