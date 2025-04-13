package com.lframework.xingyun.sc.excel.purchase;

import com.lframework.starter.web.resp.PageResult;
import com.lframework.starter.web.utils.ApplicationUtil;
import com.lframework.starter.web.utils.JsonUtil;
import com.lframework.xingyun.core.components.export.ExportTaskWorker;
import com.lframework.xingyun.sc.entity.PurchaseOrder;
import com.lframework.xingyun.sc.service.purchase.PurchaseOrderService;
import com.lframework.xingyun.sc.vo.purchase.QueryPurchaseOrderVo;

public class PurchaseOrderExportTaskWorker implements
    ExportTaskWorker<QueryPurchaseOrderVo, PurchaseOrder, PurchaseOrderExportModel> {

  @Override
  public QueryPurchaseOrderVo parseParams(String json) {
    return JsonUtil.parseObject(json, QueryPurchaseOrderVo.class);
  }

  @Override
  public PageResult<PurchaseOrder> getDataList(int pageIndex, int pageSize,
      QueryPurchaseOrderVo params) {

    PurchaseOrderService purchaseOrderService = ApplicationUtil.getBean(
        PurchaseOrderService.class);

    return purchaseOrderService.query(pageIndex, pageSize, params);
  }

  @Override
  public PurchaseOrderExportModel exportData(PurchaseOrder data) {
    return new PurchaseOrderExportModel(data);
  }

  @Override
  public Class<PurchaseOrderExportModel> getModelClass() {
    return PurchaseOrderExportModel.class;
  }
}
