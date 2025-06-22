package com.lframework.xingyun.sc.excel.stock.transfer;

import com.lframework.starter.web.core.components.resp.PageResult;
import com.lframework.starter.web.core.utils.ApplicationUtil;
import com.lframework.starter.web.core.utils.JsonUtil;
import com.lframework.starter.mq.core.components.export.ExportTaskWorker;
import com.lframework.xingyun.sc.entity.ScTransferOrder;
import com.lframework.xingyun.sc.service.stock.transfer.ScTransferOrderService;
import com.lframework.xingyun.sc.vo.stock.transfer.QueryScTransferOrderVo;

public class ScTransferOrderExportTaskWorker implements
    ExportTaskWorker<QueryScTransferOrderVo, ScTransferOrder, ScTransferOrderExportModel> {

  @Override
  public QueryScTransferOrderVo parseParams(String json) {
    return JsonUtil.parseObject(json, QueryScTransferOrderVo.class);
  }

  @Override
  public PageResult<ScTransferOrder> getDataList(int pageIndex, int pageSize,
      QueryScTransferOrderVo params) {

    ScTransferOrderService scTransferOrderService = ApplicationUtil.getBean(
        ScTransferOrderService.class);

    return scTransferOrderService.query(pageIndex, pageSize, params);
  }

  @Override
  public ScTransferOrderExportModel exportData(ScTransferOrder data) {
    return new ScTransferOrderExportModel(data);
  }

  @Override
  public Class<ScTransferOrderExportModel> getModelClass() {
    return ScTransferOrderExportModel.class;
  }
}
