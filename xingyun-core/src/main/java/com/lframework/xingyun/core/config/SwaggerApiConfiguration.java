package com.lframework.xingyun.core.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.service.ApiInfo;

@Configuration
public class SwaggerApiConfiguration {

  // 可以修改内容 但是不要删除这个Bean
  @Bean
  public ApiInfo apiInfo() {

    return new ApiInfoBuilder().title("星云ERP接口文档").description("# 星云ERP接口文档")
        .contact("lframework@163.com")
        .build();
  }
}
