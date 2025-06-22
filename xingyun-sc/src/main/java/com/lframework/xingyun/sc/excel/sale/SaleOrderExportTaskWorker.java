package com.lframework.xingyun.sc.excel.sale;

import com.lframework.starter.web.core.components.resp.PageResult;
import com.lframework.starter.web.core.utils.ApplicationUtil;
import com.lframework.starter.web.core.utils.JsonUtil;
import com.lframework.starter.mq.core.components.export.ExportTaskWorker;
import com.lframework.xingyun.sc.entity.SaleOrder;
import com.lframework.xingyun.sc.service.sale.SaleOrderService;
import com.lframework.xingyun.sc.vo.sale.QuerySaleOrderVo;

public class SaleOrderExportTaskWorker implements
    ExportTaskWorker<QuerySaleOrderVo, SaleOrder, SaleOrderExportModel> {

  @Override
  public QuerySaleOrderVo parseParams(String json) {
    return JsonUtil.parseObject(json, QuerySaleOrderVo.class);
  }

  @Override
  public PageResult<SaleOrder> getDataList(int pageIndex, int pageSize,
      QuerySaleOrderVo params) {

    SaleOrderService saleOrderService = ApplicationUtil.getBean(
        SaleOrderService.class);

    return saleOrderService.query(pageIndex, pageSize, params);
  }

  @Override
  public SaleOrderExportModel exportData(SaleOrder data) {
    return new SaleOrderExportModel(data);
  }

  @Override
  public Class<SaleOrderExportModel> getModelClass() {
    return SaleOrderExportModel.class;
  }
}
