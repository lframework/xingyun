package com.lframework.xingyun.sc.excel.stock.adjust;

import com.lframework.starter.web.core.components.resp.PageResult;
import com.lframework.starter.web.core.utils.ApplicationUtil;
import com.lframework.starter.web.core.utils.JsonUtil;
import com.lframework.starter.mq.core.components.export.ExportTaskWorker;
import com.lframework.xingyun.sc.entity.StockAdjustSheet;
import com.lframework.xingyun.sc.service.stock.adjust.StockAdjustSheetService;
import com.lframework.xingyun.sc.vo.stock.adjust.stock.QueryStockAdjustSheetVo;

public class StockAdjustSheetExportTaskWorker implements
    ExportTaskWorker<QueryStockAdjustSheetVo, StockAdjustSheet, StockAdjustSheetExportModel> {

  @Override
  public QueryStockAdjustSheetVo parseParams(String json) {
    return JsonUtil.parseObject(json, QueryStockAdjustSheetVo.class);
  }

  @Override
  public PageResult<StockAdjustSheet> getDataList(int pageIndex, int pageSize,
      QueryStockAdjustSheetVo params) {

    StockAdjustSheetService stockAdjustSheetService = ApplicationUtil.getBean(
        StockAdjustSheetService.class);

    return stockAdjustSheetService.query(pageIndex, pageSize, params);
  }

  @Override
  public StockAdjustSheetExportModel exportData(StockAdjustSheet data) {
    return new StockAdjustSheetExportModel(data);
  }

  @Override
  public Class<StockAdjustSheetExportModel> getModelClass() {
    return StockAdjustSheetExportModel.class;
  }
}
