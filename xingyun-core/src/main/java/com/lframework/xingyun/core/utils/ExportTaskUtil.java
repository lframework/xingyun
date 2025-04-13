package com.lframework.xingyun.core.utils;

import com.lframework.starter.common.utils.DateUtil;
import com.lframework.starter.mq.core.producer.MqProducer;
import com.lframework.starter.web.components.security.IUserTokenResolver;
import com.lframework.starter.web.utils.ApplicationUtil;
import com.lframework.starter.web.utils.JsonUtil;
import com.lframework.xingyun.core.components.export.ExportTaskWorker;
import com.lframework.xingyun.core.dto.export.AddExportTaskDto;
import com.lframework.xingyun.core.queue.MqConstants;
import java.time.LocalDateTime;

public class ExportTaskUtil {

  public static void exportTask(String taskName,
      Class<? extends ExportTaskWorker<?, ?, ?>> clazz,
      Object params) {
    MqProducer mqProducer = ApplicationUtil.getBean(MqProducer.class);
    IUserTokenResolver userTokenResolver = ApplicationUtil.getBean(IUserTokenResolver.class);
    AddExportTaskDto dto = new AddExportTaskDto();
    dto.setName(taskName + "_" + DateUtil.formatDateTime(LocalDateTime.now(), "yyyyMMddHHmmss"));
    dto.setReqClassName(clazz.getName());
    dto.setReqParams(JsonUtil.toJsonString(params));
    dto.setToken(userTokenResolver.getToken());

    mqProducer.sendMessage(MqConstants.ADD_EXPORT_TASK, dto);
  }
}
