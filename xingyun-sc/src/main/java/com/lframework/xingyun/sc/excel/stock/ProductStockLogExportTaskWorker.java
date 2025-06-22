package com.lframework.xingyun.sc.excel.stock;

import com.lframework.starter.web.core.components.resp.PageResult;
import com.lframework.starter.web.core.utils.ApplicationUtil;
import com.lframework.starter.web.core.utils.JsonUtil;
import com.lframework.starter.mq.core.components.export.ExportTaskWorker;
import com.lframework.xingyun.sc.entity.ProductStockLog;
import com.lframework.xingyun.sc.service.stock.ProductStockLogService;
import com.lframework.xingyun.sc.vo.stock.log.QueryProductStockLogVo;

public class ProductStockLogExportTaskWorker implements
    ExportTaskWorker<QueryProductStockLogVo, ProductStockLog, ProductStockLogExportModel> {

  @Override
  public QueryProductStockLogVo parseParams(String json) {
    return JsonUtil.parseObject(json, QueryProductStockLogVo.class);
  }

  @Override
  public PageResult<ProductStockLog> getDataList(int pageIndex, int pageSize,
      QueryProductStockLogVo params) {

    ProductStockLogService productStockLogService = ApplicationUtil.getBean(
        ProductStockLogService.class);

    return productStockLogService.query(pageIndex, pageSize, params);
  }

  @Override
  public ProductStockLogExportModel exportData(ProductStockLog data) {
    return new ProductStockLogExportModel(data);
  }

  @Override
  public Class<ProductStockLogExportModel> getModelClass() {
    return ProductStockLogExportModel.class;
  }
}
