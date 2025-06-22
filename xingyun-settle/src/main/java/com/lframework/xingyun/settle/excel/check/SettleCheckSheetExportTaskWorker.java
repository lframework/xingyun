package com.lframework.xingyun.settle.excel.check;

import com.lframework.starter.web.core.components.resp.PageResult;
import com.lframework.starter.web.core.utils.ApplicationUtil;
import com.lframework.starter.web.core.utils.JsonUtil;
import com.lframework.starter.mq.core.components.export.ExportTaskWorker;
import com.lframework.xingyun.settle.entity.SettleCheckSheet;
import com.lframework.xingyun.settle.service.SettleCheckSheetService;
import com.lframework.xingyun.settle.vo.check.QuerySettleCheckSheetVo;

public class SettleCheckSheetExportTaskWorker implements
    ExportTaskWorker<QuerySettleCheckSheetVo, SettleCheckSheet, SettleCheckSheetExportModel> {

  @Override
  public QuerySettleCheckSheetVo parseParams(String json) {
    return JsonUtil.parseObject(json, QuerySettleCheckSheetVo.class);
  }

  @Override
  public PageResult<SettleCheckSheet> getDataList(int pageIndex, int pageSize,
      QuerySettleCheckSheetVo params) {

    SettleCheckSheetService SettleCheckSheetService = ApplicationUtil.getBean(
        SettleCheckSheetService.class);

    return SettleCheckSheetService.query(pageIndex, pageSize, params);
  }

  @Override
  public SettleCheckSheetExportModel exportData(SettleCheckSheet data) {
    return new SettleCheckSheetExportModel(data);
  }

  @Override
  public Class<SettleCheckSheetExportModel> getModelClass() {
    return SettleCheckSheetExportModel.class;
  }
}
