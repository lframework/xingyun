package com.lframework.xingyun.api;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching
@SpringBootApplication(scanBasePackages = "com.lframework")
@MapperScan("com.lframework.**.mappers")
public class XingYunApiApplication {

  public static void main(String[] args) {

    SpringApplication.run(XingYunApiApplication.class, args);
  }
}
