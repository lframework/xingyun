package com.lframework.xingyun.settle.excel.item.in;

import com.lframework.starter.web.resp.PageResult;
import com.lframework.starter.web.utils.ApplicationUtil;
import com.lframework.starter.web.utils.JsonUtil;
import com.lframework.xingyun.core.components.export.ExportTaskWorker;
import com.lframework.xingyun.settle.entity.SettleInItem;
import com.lframework.xingyun.settle.service.SettleInItemService;
import com.lframework.xingyun.settle.vo.item.in.QuerySettleInItemVo;

public class SettleInItemExportTaskWorker implements
    ExportTaskWorker<QuerySettleInItemVo, SettleInItem, SettleInItemExportModel> {

  @Override
  public QuerySettleInItemVo parseParams(String json) {
    return JsonUtil.parseObject(json, QuerySettleInItemVo.class);
  }

  @Override
  public PageResult<SettleInItem> getDataList(int pageIndex, int pageSize,
      QuerySettleInItemVo params) {

    SettleInItemService settleInItemService = ApplicationUtil.getBean(SettleInItemService.class);

    return settleInItemService.query(pageIndex, pageSize, params);
  }

  @Override
  public SettleInItemExportModel exportData(SettleInItem data) {
    return new SettleInItemExportModel(data);
  }

  @Override
  public Class<SettleInItemExportModel> getModelClass() {
    return SettleInItemExportModel.class;
  }
}
