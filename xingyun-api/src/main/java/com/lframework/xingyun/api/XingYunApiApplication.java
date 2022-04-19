package com.lframework.xingyun.api;

import com.github.xiaoymin.knife4j.spring.extension.OpenApiExtensionResolver;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@SpringBootApplication(scanBasePackages = "com.lframework")
@MapperScan("com.lframework.**.mappers")
public class XingYunApiApplication {

  public static void main(String[] args) {

    SpringApplication.run(XingYunApiApplication.class, args);
  }

  /**
   * Swagger 自定义配置信息 请自行修改
   */
  @Configuration
  public static class SwaggerApiConfiguration {

    @Bean(value = "defaultApi")
    public Docket defaultApi(OpenApiExtensionResolver openApiExtensionResolver) {
      Docket docket = new Docket(DocumentationType.SWAGGER_2).apiInfo(apiInfo()).groupName("星云ERP")
          .select().apis(RequestHandlerSelectors.basePackage("com.lframework.xingyun"))
          .paths(PathSelectors.any()).build()
          .extensions(openApiExtensionResolver.buildSettingExtensions());
      return docket;
    }

    // 可以修改内容 但是不要删除这个Bean
    @Bean
    public ApiInfo apiInfo() {
      return new ApiInfoBuilder().title("星云ERP接口文档").description("# 星云ERP接口文档")
          .contact("lframework@163.com").build();
    }
  }
}
