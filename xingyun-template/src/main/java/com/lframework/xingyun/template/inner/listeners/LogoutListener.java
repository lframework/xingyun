package com.lframework.xingyun.template.inner.listeners;

import com.lframework.xingyun.template.core.enums.DefaultOpLogType;
import com.lframework.xingyun.template.core.utils.OpLogUtil;
import com.lframework.xingyun.template.core.vo.CreateOpLogsVo;
import com.lframework.xingyun.template.inner.events.LogoutEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class LogoutListener implements ApplicationListener<LogoutEvent> {

  @Override
  public void onApplicationEvent(LogoutEvent event) {

    CreateOpLogsVo vo = new CreateOpLogsVo();
    vo.setName("退出登录");
    vo.setLogType(DefaultOpLogType.AUTH);
    vo.setCreateBy(event.getUser().getName());
    vo.setCreateById(event.getUser().getId());
    vo.setIp(event.getUser().getIp());

    OpLogUtil.addLog(vo);
  }
}
