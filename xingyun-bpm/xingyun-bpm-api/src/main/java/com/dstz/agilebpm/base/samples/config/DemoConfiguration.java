package com.dstz.agilebpm.base.samples.config;

import com.dstz.base.api.exception.BusinessMessage;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 只是用于演示环境，正式使用时可以删除
 */
@ConditionalOnProperty("bpm.demo.enabled")
@Configuration
public class DemoConfiguration implements WebMvcConfigurer {

  @Override
  public void addInterceptors(InterceptorRegistry registry) {
    registry.addInterceptor(new DemoInterceptor());
  }

  public static class DemoInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
        Object handler) throws Exception {
      String uri = request.getRequestURI();
      if (uri.contains("sys/sysDataSource/") || uri.contains("sys/sysDataSourceDef")) {
        throw new BusinessMessage("【演示环境】不允许操作数据源！");
      }
      return true;
    }
  }
}
