package com.lframework.xingyun.export.listeners.app;

import com.lframework.starter.web.dto.WsPushData;
import com.lframework.starter.websocket.components.WsDataPusher;
import com.lframework.starter.websocket.events.UserConnectEvent;
import com.lframework.xingyun.export.events.ExportTaskNotifyEvent;
import com.lframework.xingyun.export.service.ExportTaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
public class ExportTaskNotifyListener implements ApplicationListener<UserConnectEvent> {

  @Autowired
  private WsDataPusher wsDataPusher;

  @Autowired
  private ExportTaskService exportTaskService;

  @TransactionalEventListener
  public void execute(ExportTaskNotifyEvent event) {
    this.notify(event.getCreateById());
  }

  @Override
  public void onApplicationEvent(UserConnectEvent event) {
    this.notify(event.getUser().getId());
  }

  private void notify(String userId) {
    // 发送广播
    WsPushData pushData = new WsPushData();
    pushData.setBizType("exportTask");
    pushData.setDataObj(exportTaskService.getSummaryByUserId(userId));
    pushData.setIncludeUserId(userId);

    wsDataPusher.push(pushData);
  }
}
