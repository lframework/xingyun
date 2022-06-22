package com.lframework.xingyun.crm.job;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.lframework.common.utils.DateUtil;
import com.lframework.starter.web.components.qrtz.QrtzJob;
import com.lframework.starter.web.utils.ApplicationUtil;
import com.lframework.starter.web.utils.CronUtil;
import com.lframework.xingyun.crm.entity.CrmMember;
import com.lframework.xingyun.crm.entity.MemberLevelConfig;
import com.lframework.xingyun.crm.service.member.ICrmMemberService;
import com.lframework.xingyun.crm.service.member.IMemberLevelConfigService;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.quartz.JobExecutionContext;

@Slf4j
public class MemberAutoDropLevelJob extends QrtzJob {

  @Override
  protected void onExecute(JobExecutionContext context) {

    IMemberLevelConfigService memberLevelConfigService = ApplicationUtil.getBean(
        IMemberLevelConfigService.class);
    MemberLevelConfig config = memberLevelConfigService.get();
    if (!config.getIsDownGrade()) {
      // 不自动降级，停止
      log.info("会员不自动降级，停止");
      return;
    }

    if (config.getDownGradeExp() <= 0) {
      log.info("自动降级经验值<=0，停止");
      return;
    }

    boolean flag = false;
    LocalDateTime now = DateUtil.toLocalDateTime(LocalDate.now());

    switch (config.getDownGradeCycle()) {
      case DAY: {
        flag = CronUtil.match("* * * * * ?", now);
        break;
      }
      case WEEK: {
        flag = CronUtil.match("* * * ? * 1", now);
        break;
      }
      case YEAR: {
        flag = CronUtil.match("* * * 1 1 ?", now);
        break;
      }
      case MONTH: {
        flag = CronUtil.match("* * * 1 * ?", now);
        break;
      }
      case QUARTER: {
        flag = CronUtil.match("* * * 1 1,4,7,10 ?", now);
        break;
      }
      case HALF_YEAR: {
        flag = CronUtil.match("* * * 1 1,7 ?", now);
        break;
      }
    }

    if (!flag) {
      log.info("当前时间无需执行降级任务，停止");
      return;
    }

    ICrmMemberService crmMemberService = ApplicationUtil.getBean(ICrmMemberService.class);
    List<CrmMember> members = crmMemberService.list();

    for (CrmMember member : members) {
      if (member.getLastDropDate() != null) {
        if (member.getLastDropDate().compareTo(now.toLocalDate()) >= 0) {
          continue;
        }
      }
      crmMemberService.dropLevel(member.getId(), config.getDownGradeExp());

      Wrapper<CrmMember> updateWrapper = Wrappers.lambdaUpdate(CrmMember.class)
          .set(CrmMember::getLastDropDate, now.toLocalDate()).eq(CrmMember::getId, member.getId());
      crmMemberService.update(updateWrapper);
    }
  }
}
