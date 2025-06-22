package com.lframework.xingyun.settle.excel.item.out;

import com.lframework.starter.web.core.components.resp.PageResult;
import com.lframework.starter.web.core.utils.ApplicationUtil;
import com.lframework.starter.web.core.utils.JsonUtil;
import com.lframework.starter.mq.core.components.export.ExportTaskWorker;
import com.lframework.xingyun.settle.entity.SettleOutItem;
import com.lframework.xingyun.settle.service.SettleOutItemService;
import com.lframework.xingyun.settle.vo.item.out.QuerySettleOutItemVo;

public class SettleOutItemExportTaskWorker implements
    ExportTaskWorker<QuerySettleOutItemVo, SettleOutItem, SettleOutItemExportModel> {

  @Override
  public QuerySettleOutItemVo parseParams(String json) {
    return JsonUtil.parseObject(json, QuerySettleOutItemVo.class);
  }

  @Override
  public PageResult<SettleOutItem> getDataList(int pageOutdex, int pageSize,
      QuerySettleOutItemVo params) {

    SettleOutItemService settleOutItemService = ApplicationUtil.getBean(SettleOutItemService.class);

    return settleOutItemService.query(pageOutdex, pageSize, params);
  }

  @Override
  public SettleOutItemExportModel exportData(SettleOutItem data) {
    return new SettleOutItemExportModel(data);
  }

  @Override
  public Class<SettleOutItemExportModel> getModelClass() {
    return SettleOutItemExportModel.class;
  }
}
