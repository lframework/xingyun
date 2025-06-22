package com.lframework.xingyun.sc.excel.purchase.returned;

import com.lframework.starter.web.core.components.resp.PageResult;
import com.lframework.starter.web.core.utils.ApplicationUtil;
import com.lframework.starter.web.core.utils.JsonUtil;
import com.lframework.starter.mq.core.components.export.ExportTaskWorker;
import com.lframework.xingyun.sc.entity.PurchaseReturn;
import com.lframework.xingyun.sc.service.purchase.PurchaseReturnService;
import com.lframework.xingyun.sc.vo.purchase.returned.QueryPurchaseReturnVo;

public class PurchaseReturnExportTaskWorker implements
    ExportTaskWorker<QueryPurchaseReturnVo, PurchaseReturn, PurchaseReturnExportModel> {

  @Override
  public QueryPurchaseReturnVo parseParams(String json) {
    return JsonUtil.parseObject(json, QueryPurchaseReturnVo.class);
  }

  @Override
  public PageResult<PurchaseReturn> getDataList(int pageIndex, int pageSize,
      QueryPurchaseReturnVo params) {

    PurchaseReturnService purchaseReturnService = ApplicationUtil.getBean(
        PurchaseReturnService.class);

    return purchaseReturnService.query(pageIndex, pageSize, params);
  }

  @Override
  public PurchaseReturnExportModel exportData(PurchaseReturn data) {
    return new PurchaseReturnExportModel(data);
  }

  @Override
  public Class<PurchaseReturnExportModel> getModelClass() {
    return PurchaseReturnExportModel.class;
  }
}
