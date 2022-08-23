package com.lframework.xingyun.api;

import com.github.xiaoymin.knife4j.spring.extension.OpenApiExtensionResolver;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

/**
 * 底层的Bean的包全部在com.lframework.starter下 此处单独配置两个包名而不是直接配一个总的包名的原因： 1、可以更好分辨哪些包是属于底层，哪些包属于当前业务层
 * 2、有部分需求：将底层预设的Bean排除，这样更配包名更清晰，能快速排除底层包
 * 注意：底层包名规则为com.lframework.starter.xxx，其中：xxx代表web、mybatis等，例如：web-starter的包为com.lframework.starter.web
 */
@ServletComponentScan(basePackages = {"com.lframework.starter", "com.lframework.xingyun"})
@SpringBootApplication(scanBasePackages = {"com.lframework.starter", "com.lframework.xingyun"})
@MapperScan({"com.lframework.starter.**.mappers", "com.lframework.xingyun.**.mappers"})
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
          .select()
          .apis(RequestHandlerSelectors.basePackage("com.lframework.xingyun"))
          .paths(PathSelectors.any())
          .build().extensions(openApiExtensionResolver.buildSettingExtensions());
      return docket;
    }

    // 可以修改内容 但是不要删除这个Bean
    @Bean
    public ApiInfo apiInfo() {

      return new ApiInfoBuilder().title("星云ERP接口文档").description("# 星云ERP接口文档")
          .contact("lframework@163.com")
          .build();
    }
  }
}
