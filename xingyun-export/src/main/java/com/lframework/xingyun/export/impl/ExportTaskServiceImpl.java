package com.lframework.xingyun.export.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.lframework.starter.common.exceptions.impl.DefaultSysException;
import com.lframework.starter.web.impl.BaseMpServiceImpl;
import com.lframework.starter.web.utils.ApplicationUtil;
import com.lframework.xingyun.core.service.SecurityUploadRecordService;
import com.lframework.xingyun.export.dto.ExportTaskSummaryDto;
import com.lframework.xingyun.export.entity.ExportTask;
import com.lframework.xingyun.export.enums.ExportTaskStatus;
import com.lframework.xingyun.export.events.ExportTaskNotifyEvent;
import com.lframework.xingyun.export.mappers.ExportTaskMapper;
import com.lframework.xingyun.export.service.ExportTaskService;
import java.time.LocalDateTime;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
public class ExportTaskServiceImpl extends BaseMpServiceImpl<ExportTaskMapper, ExportTask>
    implements ExportTaskService {

  @Autowired
  private SecurityUploadRecordService securityUploadRecordService;


  @Transactional(rollbackFor = Exception.class)
  @Override
  public String create(ExportTask task) {
    this.save(task);

    ApplicationUtil.publishEvent(new ExportTaskNotifyEvent(this, task.getCreateById()));
    return task.getId();
  }

  @Transactional(rollbackFor = Exception.class)
  @Override
  public void setExporting(String id) {

    ExportTask task = this.getById(id);

    Wrapper<ExportTask> updateWrapper = Wrappers.lambdaUpdate(ExportTask.class)
        .eq(ExportTask::getId, id)
        .eq(ExportTask::getStatus, ExportTaskStatus.CREATED)
        .set(ExportTask::getStatus, ExportTaskStatus.EXPORTING);

    if (!this.update(updateWrapper)) {
      log.error("导出任务 ID {} 设置为 正在导出 失败", id);
      throw new DefaultSysException(
          "导出任务设置为“" + ExportTaskStatus.EXPORTING.getDesc() + "”失败");
    }

    ApplicationUtil.publishEvent(new ExportTaskNotifyEvent(this, task.getCreateById()));
  }

  @Transactional(rollbackFor = Exception.class)
  @Override
  public void setSuccess(String id, String uploadType, String objectName, String fileSize) {

    ExportTask task = this.getById(id);
    String recordId = securityUploadRecordService.create(uploadType, objectName);

    Wrapper<ExportTask> updateWrapper = Wrappers.lambdaUpdate(ExportTask.class)
        .eq(ExportTask::getId, id)
        .in(ExportTask::getStatus, ExportTaskStatus.CREATED, ExportTaskStatus.EXPORTING)
        .set(ExportTask::getRecordId, recordId)
        .set(ExportTask::getStatus, ExportTaskStatus.SUCCESS)
        .set(ExportTask::getFinishTime, LocalDateTime.now())
        .set(ExportTask::getFileSize, fileSize)
        .setSql("cur_count = total_count");
    if (!this.update(updateWrapper)) {
      log.error("导出任务 ID {} 设置为 导出成功 失败", id);
      throw new DefaultSysException(
          "导出任务设置为“" + ExportTaskStatus.SUCCESS.getDesc() + "”失败");
    }
    ApplicationUtil.publishEvent(new ExportTaskNotifyEvent(this, task.getCreateById()));
  }

  @Transactional(rollbackFor = Exception.class)
  @Override
  public void setFail(String id, String errorMsg) {
    ExportTask task = this.getById(id);
    Wrapper<ExportTask> updateWrapper = Wrappers.lambdaUpdate(ExportTask.class)
        .eq(ExportTask::getId, id)
        .set(ExportTask::getStatus, ExportTaskStatus.FAIL)
        .set(ExportTask::getErrorMsg, errorMsg)
        .set(ExportTask::getFinishTime, LocalDateTime.now());
    if (!this.update(updateWrapper)) {
      log.error("导出任务 ID {} 设置为 导出失败 失败", id);
      throw new DefaultSysException(
          "导出任务设置为“" + ExportTaskStatus.FAIL.getDesc() + "”失败");
    }
    ApplicationUtil.publishEvent(new ExportTaskNotifyEvent(this, task.getCreateById()));
  }

  @Transactional(rollbackFor = Exception.class)
  @Override
  public void setTotalCount(String id, Long totalCount) {
    Wrapper<ExportTask> updateWrapper = Wrappers.lambdaUpdate(ExportTask.class)
        .eq(ExportTask::getId, id)
        .eq(ExportTask::getStatus, ExportTaskStatus.EXPORTING)
        .set(ExportTask::getTotalCount, totalCount);

    if (!this.update(updateWrapper)) {
      log.error("导出任务 ID {} 设置 数据总条数 失败", id);
      throw new DefaultSysException(
          "导出任务设置数据总条数失败");
    }
  }

  @Transactional(rollbackFor = Exception.class)
  @Override
  public void addCurCount(String id, int addCount) {
    Wrapper<ExportTask> updateWrapper = Wrappers.lambdaUpdate(ExportTask.class)
        .eq(ExportTask::getId, id)
        .eq(ExportTask::getStatus, ExportTaskStatus.EXPORTING)
        .setSql("cur_count = cur_count + " + addCount);

    if (!this.update(updateWrapper)) {
      log.error("导出任务 ID {} 设置 当前导出条数 失败", id);
      throw new DefaultSysException(
          "导出任务设置当前导出条数失败");
    }
  }

  @Override
  public ExportTaskSummaryDto getSummaryByUserId(String userId) {
    return getBaseMapper().getSummaryByUserId(userId);
  }
}
