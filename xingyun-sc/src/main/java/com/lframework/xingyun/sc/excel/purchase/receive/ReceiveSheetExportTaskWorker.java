package com.lframework.xingyun.sc.excel.purchase.receive;

import com.lframework.starter.web.core.components.resp.PageResult;
import com.lframework.starter.web.core.utils.ApplicationUtil;
import com.lframework.starter.web.core.utils.JsonUtil;
import com.lframework.starter.mq.core.components.export.ExportTaskWorker;
import com.lframework.xingyun.sc.entity.ReceiveSheet;
import com.lframework.xingyun.sc.service.purchase.ReceiveSheetService;
import com.lframework.xingyun.sc.vo.purchase.receive.QueryReceiveSheetVo;

public class ReceiveSheetExportTaskWorker implements
    ExportTaskWorker<QueryReceiveSheetVo, ReceiveSheet, ReceiveSheetExportModel> {

  @Override
  public QueryReceiveSheetVo parseParams(String json) {
    return JsonUtil.parseObject(json, QueryReceiveSheetVo.class);
  }

  @Override
  public PageResult<ReceiveSheet> getDataList(int pageIndex, int pageSize,
      QueryReceiveSheetVo params) {

    ReceiveSheetService receiveSheetService = ApplicationUtil.getBean(
        ReceiveSheetService.class);

    return receiveSheetService.query(pageIndex, pageSize, params);
  }

  @Override
  public ReceiveSheetExportModel exportData(ReceiveSheet data) {
    return new ReceiveSheetExportModel(data);
  }

  @Override
  public Class<ReceiveSheetExportModel> getModelClass() {
    return ReceiveSheetExportModel.class;
  }
}
