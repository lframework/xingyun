package com.lframework.xingyun.api;

import com.lframework.starter.web.core.annotations.locker.EnableLock;
import com.lframework.starter.web.core.annotations.locker.LockType;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.mybatis.spring.annotation.MapperScan;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@EnableLock(type = LockType.REDIS)
@EnableDiscoveryClient
@EnableFeignClients(basePackages = "com.lframework.xingyun")
@ServletComponentScan(basePackages = "com.lframework.xingyun")
@SpringBootApplication(scanBasePackages = "com.lframework.xingyun")
@MapperScan("com.lframework.xingyun.**.mappers")
public class XingYunCloudApiApplication {

  public static void main(String[] args) {
    SpringApplication.run(XingYunCloudApiApplication.class, args);
  }

  @Configuration
  public static class OpenApiConfiguration {

    @Bean("xingyunCloudApi")
    public GroupedOpenApi xingyunCloudApi() {
      return GroupedOpenApi.builder()
          .group("星云ERP")
          .packagesToScan(
              "com.lframework.xingyun.api",
              "com.lframework.xingyun.basedata",
              "com.lframework.xingyun.chart",
              "com.lframework.xingyun.comp",
              "com.lframework.xingyun.sc",
              "com.lframework.xingyun.settle")
          .build();
    }

    @Bean
    public OpenAPI openAPI() {
      return new OpenAPI().info(new Info()
          .title("星云ERP接口文档")
          .description("# 星云ERP接口文档")
          .version("v1.0.0"));
    }
  }
}
