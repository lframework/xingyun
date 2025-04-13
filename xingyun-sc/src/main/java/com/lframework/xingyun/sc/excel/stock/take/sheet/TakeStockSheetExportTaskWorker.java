package com.lframework.xingyun.sc.excel.stock.take.sheet;

import com.lframework.starter.web.resp.PageResult;
import com.lframework.starter.web.utils.ApplicationUtil;
import com.lframework.starter.web.utils.JsonUtil;
import com.lframework.xingyun.core.components.export.ExportTaskWorker;
import com.lframework.xingyun.sc.entity.TakeStockSheet;
import com.lframework.xingyun.sc.service.stock.take.TakeStockSheetService;
import com.lframework.xingyun.sc.vo.stock.take.sheet.QueryTakeStockSheetVo;

public class TakeStockSheetExportTaskWorker implements
    ExportTaskWorker<QueryTakeStockSheetVo, TakeStockSheet, TakeStockSheetExportModel> {

  @Override
  public QueryTakeStockSheetVo parseParams(String json) {
    return JsonUtil.parseObject(json, QueryTakeStockSheetVo.class);
  }

  @Override
  public PageResult<TakeStockSheet> getDataList(int pageIndex, int pageSize,
      QueryTakeStockSheetVo params) {

    TakeStockSheetService takeStockSheetService = ApplicationUtil.getBean(
        TakeStockSheetService.class);

    return takeStockSheetService.query(pageIndex, pageSize, params);
  }

  @Override
  public TakeStockSheetExportModel exportData(TakeStockSheet data) {
    return new TakeStockSheetExportModel(data);
  }

  @Override
  public Class<TakeStockSheetExportModel> getModelClass() {
    return TakeStockSheetExportModel.class;
  }
}
