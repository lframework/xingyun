package com.lframework.xingyun.sc.excel.stock.take.pre;

import com.lframework.starter.web.core.components.resp.PageResult;
import com.lframework.starter.web.core.utils.ApplicationUtil;
import com.lframework.starter.web.core.utils.JsonUtil;
import com.lframework.starter.mq.core.components.export.ExportTaskWorker;
import com.lframework.xingyun.sc.entity.PreTakeStockSheet;
import com.lframework.xingyun.sc.service.stock.take.PreTakeStockSheetService;
import com.lframework.xingyun.sc.vo.stock.take.pre.QueryPreTakeStockSheetVo;

public class PreTakeStockSheetExportTaskWorker implements
    ExportTaskWorker<QueryPreTakeStockSheetVo, PreTakeStockSheet, PreTakeStockSheetExportModel> {

  @Override
  public QueryPreTakeStockSheetVo parseParams(String json) {
    return JsonUtil.parseObject(json, QueryPreTakeStockSheetVo.class);
  }

  @Override
  public PageResult<PreTakeStockSheet> getDataList(int pageIndex, int pageSize,
      QueryPreTakeStockSheetVo params) {

    PreTakeStockSheetService preTakeStockSheetService = ApplicationUtil.getBean(
        PreTakeStockSheetService.class);

    return preTakeStockSheetService.query(pageIndex, pageSize, params);
  }

  @Override
  public PreTakeStockSheetExportModel exportData(PreTakeStockSheet data) {
    return new PreTakeStockSheetExportModel(data);
  }

  @Override
  public Class<PreTakeStockSheetExportModel> getModelClass() {
    return PreTakeStockSheetExportModel.class;
  }
}
