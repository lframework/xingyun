package com.lframework.xingyun.settle.excel.sheet;

import com.lframework.starter.web.core.components.resp.PageResult;
import com.lframework.starter.web.core.utils.ApplicationUtil;
import com.lframework.starter.web.core.utils.JsonUtil;
import com.lframework.starter.mq.core.components.export.ExportTaskWorker;
import com.lframework.xingyun.settle.entity.SettleSheet;
import com.lframework.xingyun.settle.service.SettleSheetService;
import com.lframework.xingyun.settle.vo.sheet.QuerySettleSheetVo;

public class SettleSheetExportTaskWorker implements
    ExportTaskWorker<QuerySettleSheetVo, SettleSheet, SettleSheetExportModel> {

  @Override
  public QuerySettleSheetVo parseParams(String json) {
    return JsonUtil.parseObject(json, QuerySettleSheetVo.class);
  }

  @Override
  public PageResult<SettleSheet> getDataList(int pageIndex, int pageSize,
      QuerySettleSheetVo params) {

    SettleSheetService SettleSheetService = ApplicationUtil.getBean(
        SettleSheetService.class);

    return SettleSheetService.query(pageIndex, pageSize, params);
  }

  @Override
  public SettleSheetExportModel exportData(SettleSheet data) {
    return new SettleSheetExportModel(data);
  }

  @Override
  public Class<SettleSheetExportModel> getModelClass() {
    return SettleSheetExportModel.class;
  }
}
