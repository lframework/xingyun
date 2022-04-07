package com.dstz.agilebpm.base.samples;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author wacxhs
 * @date 2018-07-11
 */
@ComponentScan("com.dstz.*")
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class BpmApplication {

  public static void main(String[] args) {
    SpringApplication.run(BpmApplication.class, args);
  }
}
