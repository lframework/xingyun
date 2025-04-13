package com.lframework.xingyun.sc.excel.stock;

import com.lframework.starter.web.resp.PageResult;
import com.lframework.starter.web.utils.ApplicationUtil;
import com.lframework.starter.web.utils.JsonUtil;
import com.lframework.xingyun.core.components.export.ExportTaskWorker;
import com.lframework.xingyun.sc.entity.ProductStock;
import com.lframework.xingyun.sc.service.stock.ProductStockService;
import com.lframework.xingyun.sc.vo.stock.QueryProductStockVo;

public class ProductStockExportTaskWorker implements
    ExportTaskWorker<QueryProductStockVo, ProductStock, ProductStockExportModel> {

  @Override
  public QueryProductStockVo parseParams(String json) {
    return JsonUtil.parseObject(json, QueryProductStockVo.class);
  }

  @Override
  public PageResult<ProductStock> getDataList(int pageIndex, int pageSize,
      QueryProductStockVo params) {

    ProductStockService productStockService = ApplicationUtil.getBean(ProductStockService.class);

    return productStockService.query(pageIndex, pageSize, params);
  }

  @Override
  public ProductStockExportModel exportData(ProductStock data) {
    return new ProductStockExportModel(data);
  }

  @Override
  public Class<ProductStockExportModel> getModelClass() {
    return ProductStockExportModel.class;
  }
}
