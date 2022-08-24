package com.lframework.xingyun.basedata.api;

import com.alibaba.cloud.seata.feign.SeataFeignClientAutoConfiguration;
import com.github.xiaoymin.knife4j.spring.extension.OpenApiExtensionResolver;
import io.seata.spring.annotation.datasource.EnableAutoDataSourceProxy;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

/**
 * 底层的Bean的包全部在com.lframework.starter下 此处单独配置两个包名而不是直接配一个总的包名的原因： 1、可以更好分辨哪些包是属于底层，哪些包属于当前业务层
 * 2、有部分需求：将底层预设的Bean排除，这样更配包名更清晰，能快速排除底层包 注意：底层包名规则为com.lframework.starter.xxx，其中：xxx代表web、mybatis等，例如：web-starter的包为com.lframework.starter.web
 */
@EnableAutoDataSourceProxy
@EnableDiscoveryClient
@EnableFeignClients(basePackages = {"com.lframework.starter", "com.lframework.xingyun"})
@ServletComponentScan(basePackages = {"com.lframework.starter", "com.lframework.xingyun"})
@SpringBootApplication(scanBasePackages = {"com.lframework.starter",
    "com.lframework.xingyun"}, exclude = {
    SeataFeignClientAutoConfiguration.class})
@MapperScan({"com.lframework.starter.**.mappers", "com.lframework.xingyun.**.mappers"})
public class BaseDataApplication {

  public static void main(String[] args) {

    SpringApplication.run(BaseDataApplication.class, args);
  }

  /**
   * Swagger 自定义配置信息 请自行修改
   */
  @Configuration
  public static class SwaggerCustomConfiguration {

    @Bean(value = "defaultApi")
    public Docket defaultApi(ApiInfo apiInfo, OpenApiExtensionResolver openApiExtensionResolver) {

      Docket docket = new Docket(DocumentationType.SWAGGER_2).apiInfo(apiInfo).groupName("基础信息服务")
          .select()
          .apis(RequestHandlerSelectors.basePackage("com.lframework.xingyun.basedata"))
          .paths(PathSelectors.any())
          .build().extensions(openApiExtensionResolver.buildSettingExtensions());
      return docket;
    }
  }
}
