package com.lframework.xingyun.settle.excel.pre;

import com.lframework.starter.web.resp.PageResult;
import com.lframework.starter.web.utils.ApplicationUtil;
import com.lframework.starter.web.utils.JsonUtil;
import com.lframework.xingyun.core.components.export.ExportTaskWorker;
import com.lframework.xingyun.settle.entity.SettlePreSheet;
import com.lframework.xingyun.settle.service.SettlePreSheetService;
import com.lframework.xingyun.settle.vo.pre.QuerySettlePreSheetVo;

public class SettlePreSheetExportTaskWorker implements
    ExportTaskWorker<QuerySettlePreSheetVo, SettlePreSheet, SettlePreSheetExportModel> {

  @Override
  public QuerySettlePreSheetVo parseParams(String json) {
    return JsonUtil.parseObject(json, QuerySettlePreSheetVo.class);
  }

  @Override
  public PageResult<SettlePreSheet> getDataList(int pageIndex, int pageSize,
      QuerySettlePreSheetVo params) {

    SettlePreSheetService SettlePreSheetService = ApplicationUtil.getBean(
        SettlePreSheetService.class);

    return SettlePreSheetService.query(pageIndex, pageSize, params);
  }

  @Override
  public SettlePreSheetExportModel exportData(SettlePreSheet data) {
    return new SettlePreSheetExportModel(data);
  }

  @Override
  public Class<SettlePreSheetExportModel> getModelClass() {
    return SettlePreSheetExportModel.class;
  }
}
