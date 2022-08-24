package com.lframework.xingyun.chart.api.config;

import com.lframework.xingyun.chart.facade.constants.MqConstants;
import javax.jms.Queue;
import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author zmj
 * @since 2022/8/25
 */
@Configuration
public class ChartMqConfiguration {

  @Bean
  public Queue orderChart() {
    return new ActiveMQQueue(MqConstants.QUEUE_ORDER_CHART);
  }
}
