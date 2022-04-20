package com.dstz.agilebpm.base.samples.config;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;

/**
 * spring mvc 相关的配置
 *
 * @author jeff
 */
@Configuration
public class WebMvcConfigurers {

  //自定义字符串转换器
  @Bean
  public StringHttpMessageConverter stringHttpMessageConverter() {

    StringHttpMessageConverter converter = new StringHttpMessageConverter(StandardCharsets.UTF_8);
    return converter;
  }

  //自定义fastjson转换器
  @Bean
  public HttpMessageConverters fastJsonHttpMessageConverters() {
    //1.需要定义一个convert转换消息的对象;
    FastJsonHttpMessageConverter fastJsonHttpMessageConverter = new FastJsonHttpMessageConverter();

    //2:添加fastJson的配置信息;
    SerializerFeature[] serializerList = {SerializerFeature.WriteDateUseDateFormat};

    //3处理中文乱码问题
    List<MediaType> fastMediaTypes = new ArrayList<>();
    fastMediaTypes.add(MediaType.TEXT_HTML);
    fastMediaTypes.add(MediaType.APPLICATION_JSON_UTF8);

    //4.在convert中添加配置信息.
    fastJsonHttpMessageConverter.setSupportedMediaTypes(fastMediaTypes);
    fastJsonHttpMessageConverter.setFeatures(serializerList);

    HttpMessageConverter<?> converter = fastJsonHttpMessageConverter;
    return new HttpMessageConverters(converter);

  }


}
