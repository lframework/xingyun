package com.lframework.xingyun.settle.excel.fee;

import com.lframework.starter.web.core.components.resp.PageResult;
import com.lframework.starter.web.core.utils.ApplicationUtil;
import com.lframework.starter.web.core.utils.JsonUtil;
import com.lframework.starter.mq.core.components.export.ExportTaskWorker;
import com.lframework.xingyun.settle.entity.SettleFeeSheet;
import com.lframework.xingyun.settle.service.SettleFeeSheetService;
import com.lframework.xingyun.settle.vo.fee.QuerySettleFeeSheetVo;

public class SettleFeeSheetExportTaskWorker implements
    ExportTaskWorker<QuerySettleFeeSheetVo, SettleFeeSheet, SettleFeeSheetExportModel> {

  @Override
  public QuerySettleFeeSheetVo parseParams(String json) {
    return JsonUtil.parseObject(json, QuerySettleFeeSheetVo.class);
  }

  @Override
  public PageResult<SettleFeeSheet> getDataList(int pageIndex, int pageSize,
      QuerySettleFeeSheetVo params) {

    SettleFeeSheetService SettleFeeSheetService = ApplicationUtil.getBean(
        SettleFeeSheetService.class);

    return SettleFeeSheetService.query(pageIndex, pageSize, params);
  }

  @Override
  public SettleFeeSheetExportModel exportData(SettleFeeSheet data) {
    return new SettleFeeSheetExportModel(data);
  }

  @Override
  public Class<SettleFeeSheetExportModel> getModelClass() {
    return SettleFeeSheetExportModel.class;
  }
}
