package com.lframework.xingyun.sc.excel.logistics;

import com.lframework.starter.web.core.components.resp.PageResult;
import com.lframework.starter.web.core.utils.ApplicationUtil;
import com.lframework.starter.web.core.utils.JsonUtil;
import com.lframework.starter.mq.core.components.export.ExportTaskWorker;
import com.lframework.xingyun.sc.entity.LogisticsSheet;
import com.lframework.xingyun.sc.service.logistics.LogisticsSheetService;
import com.lframework.xingyun.sc.vo.logistics.QueryLogisticsSheetVo;

public class LogisticsSheetExportTaskWorker implements
    ExportTaskWorker<QueryLogisticsSheetVo, LogisticsSheet, LogisticsSheetExportModel> {

  @Override
  public QueryLogisticsSheetVo parseParams(String json) {
    return JsonUtil.parseObject(json, QueryLogisticsSheetVo.class);
  }

  @Override
  public PageResult<LogisticsSheet> getDataList(int pageIndex, int pageSize,
      QueryLogisticsSheetVo params) {

    LogisticsSheetService logisticsSheetService = ApplicationUtil.getBean(
        LogisticsSheetService.class);

    return logisticsSheetService.query(pageIndex, pageSize, params);
  }

  @Override
  public LogisticsSheetExportModel exportData(LogisticsSheet data) {
    return new LogisticsSheetExportModel(data);
  }

  @Override
  public Class<LogisticsSheetExportModel> getModelClass() {
    return LogisticsSheetExportModel.class;
  }
}
