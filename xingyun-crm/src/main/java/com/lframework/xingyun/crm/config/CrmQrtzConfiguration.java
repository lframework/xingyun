package com.lframework.xingyun.crm.config;

import com.lframework.xingyun.crm.job.MemberAutoDropLevelJob;
import org.quartz.CronScheduleBuilder;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CrmQrtzConfiguration {

  @Bean
  public JobDetail memberAutoDropLevelJob() {
    return JobBuilder.newJob(MemberAutoDropLevelJob.class).withIdentity("MemberAutoDropLevelJob")
        .storeDurably().build();
  }

  @Bean
  public Trigger memberAutoDropLevelTrigger() {
    CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule("0 0 0 * * ?");

    return TriggerBuilder.newTrigger().forJob(memberAutoDropLevelJob())
        .withIdentity("MemberAutoDropLevelTrigger").withSchedule(scheduleBuilder).build();
  }
}
