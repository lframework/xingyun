package com.lframework.xingyun.export.handlers;

import com.lframework.starter.common.exceptions.ClientException;
import com.lframework.starter.common.exceptions.impl.DefaultClientException;
import com.lframework.starter.common.utils.CollectionUtil;
import com.lframework.starter.common.utils.FileUtil;
import com.lframework.starter.common.utils.StringUtil;
import com.lframework.starter.web.components.excel.ExcelModel;
import com.lframework.starter.web.components.excel.ExcelMultipartWriterSheetBuilder;
import com.lframework.starter.web.components.security.AbstractUserDetails;
import com.lframework.starter.web.components.security.SecurityUtil;
import com.lframework.starter.web.components.upload.client.dto.UploadDto;
import com.lframework.starter.web.resp.PageResult;
import com.lframework.starter.web.utils.ApplicationUtil;
import com.lframework.starter.web.utils.ExcelUtil;
import com.lframework.starter.web.utils.UploadUtil;
import com.lframework.xingyun.core.components.export.ExportTaskWorker;
import com.lframework.xingyun.export.entity.ExportTask;
import com.lframework.xingyun.export.service.ExportTaskService;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ExportTaskHandler<P, D, M extends ExcelModel> {

  // 每页条数
  private static final int PER_PAGE_SIZE = 10000;

  // 上报处理条数的攒批数量
  private static final int COUNT_BATCH_SIZE = 100;

  private ExportTaskWorker<P, D, M> exportTaskWorker;

  private long timeout;

  private int curCount;

  private String taskId;

  public ExportTaskHandler(String taskId, ExportTaskWorker<P, D, M> exportTaskWorker,
      long timeout) {
    this.taskId = taskId;
    this.exportTaskWorker = exportTaskWorker;
    this.timeout = timeout;
  }

  public void execute(String json) {

    ExportTaskService exportTaskService = ApplicationUtil.getBean(ExportTaskService.class);

    ExportTask exportTask = exportTaskService.getById(taskId);

    String tmpLocation = ApplicationUtil.getEnv().getProperty("spring.servlet.multipart.location");
    tmpLocation = tmpLocation.endsWith(File.separator) ? tmpLocation : tmpLocation + File.separator;
    try {
      // 将导出任务状态置为正在导出
      exportTaskService.setExporting(taskId);

      P params = exportTaskWorker.parseParams(json);

      long beginTs = System.currentTimeMillis();
      int pageIndex = 1;
      PageResult<D> pageResult = exportTaskWorker.getDataList(pageIndex, PER_PAGE_SIZE, params);
      this.checkCurrentUser();
      long totalCount = pageResult.getTotalCount();
      // 上报总数据条数
      exportTaskService.setTotalCount(taskId, totalCount);

      File xlsxFile = FileUtil.newFile(tmpLocation + taskId + ".xlsx");
      ExcelMultipartWriterSheetBuilder builder = ExcelUtil.multipartExportXlsx(xlsxFile, "Sheet1",
          exportTaskWorker.getModelClass());
      try {
        do {
          long calcTs = System.currentTimeMillis() - beginTs;
          if (calcTs > timeout * 1000L) {
            throw new DefaultClientException(
                "导出任务已执行" + (calcTs / 1000) + "秒，已超出最大执行时间" + timeout
                    + "秒，无法导出。");
          }
          List<D> dataList = pageResult.getDatas();
          List<M> modelList = new ArrayList<>(dataList.size());
          for (D data : dataList) {
            M model = exportTaskWorker.exportData(data);
            modelList.add(model);
            // 上报处理条数
            this.reportCurCount();
          }

          builder.doWrite(modelList);

          pageResult = exportTaskWorker.getDataList(++pageIndex, PER_PAGE_SIZE, params);
          this.checkCurrentUser();
        } while (pageResult.isHasNext());
      } finally {
        builder.finish();
      }

      UploadDto uploadDto = UploadUtil.upload(xlsxFile,
          CollectionUtil.toList("export-task", exportTask.getCreateById()), true);

      // 将导出任务状态置为导出完成
      exportTaskService.setSuccess(taskId, uploadDto.getUploadType(), uploadDto.getObjectName(),
          FileUtil.readableFileSize(xlsxFile.length()));
    } catch (ClientException e) {
      this.handleError(e);
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      this.handleError(e);
    }
  }

  protected void reportCurCount() {
    this.curCount++;
    if (this.curCount >= COUNT_BATCH_SIZE) {
      ExportTaskService exportTaskService = ApplicationUtil.getBean(ExportTaskService.class);
      exportTaskService.addCurCount(taskId, this.curCount);
      this.curCount = 0;
    }
  }

  /**
   * 处理异常
   *
   * @param e
   */
  protected void handleError(Exception e) {
    ExportTaskService exportTaskService = ApplicationUtil.getBean(ExportTaskService.class);
    // 将导出任务状态置为失败
    exportTaskService.setFail(taskId,
        StringUtil.isBlank(e.getMessage()) ? e.toString() : e.getMessage());

    exportTaskWorker.handleError(e);
  }

  protected void checkCurrentUser() {
    // 必须一直检测session是否在线，否则可能会导致查出的数据超出了当前用户可访问的数据
    AbstractUserDetails currentUser = SecurityUtil.getCurrentUser();
    if (currentUser == null || SecurityUtil.getSessionByLoginId(currentUser.getLoginId()) == null) {
      throw new DefaultClientException("导出用户已退出登录，无法导出");
    }
  }
}
