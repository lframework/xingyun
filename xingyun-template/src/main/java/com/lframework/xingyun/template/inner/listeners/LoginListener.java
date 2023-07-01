package com.lframework.xingyun.template.inner.listeners;

import com.lframework.starter.web.common.security.AbstractUserDetails;
import com.lframework.starter.web.common.security.SecurityUtil;
import com.lframework.xingyun.template.core.enums.DefaultOpLogType;
import com.lframework.xingyun.template.core.utils.OpLogUtil;
import com.lframework.xingyun.template.core.vo.CreateOpLogsVo;
import com.lframework.xingyun.template.inner.events.LoginEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class LoginListener implements ApplicationListener<LoginEvent> {

  @Override
  public void onApplicationEvent(LoginEvent loginEvent) {

    AbstractUserDetails currentUser = SecurityUtil.getCurrentUser();
    CreateOpLogsVo vo = new CreateOpLogsVo();
    vo.setName("用户登录");
    vo.setLogType(DefaultOpLogType.AUTH);
    vo.setCreateBy(currentUser.getName());
    vo.setCreateById(currentUser.getId());
    vo.setIp(currentUser.getIp());

    OpLogUtil.addLog(vo);
  }
}
