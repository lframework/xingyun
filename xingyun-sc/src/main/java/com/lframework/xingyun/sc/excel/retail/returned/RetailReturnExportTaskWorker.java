package com.lframework.xingyun.sc.excel.retail.returned;

import com.lframework.starter.web.resp.PageResult;
import com.lframework.starter.web.utils.ApplicationUtil;
import com.lframework.starter.web.utils.JsonUtil;
import com.lframework.xingyun.core.components.export.ExportTaskWorker;
import com.lframework.xingyun.sc.entity.RetailReturn;
import com.lframework.xingyun.sc.service.retail.RetailReturnService;
import com.lframework.xingyun.sc.vo.retail.returned.QueryRetailReturnVo;

public class RetailReturnExportTaskWorker implements
    ExportTaskWorker<QueryRetailReturnVo, RetailReturn, RetailReturnExportModel> {

  @Override
  public QueryRetailReturnVo parseParams(String json) {
    return JsonUtil.parseObject(json, QueryRetailReturnVo.class);
  }

  @Override
  public PageResult<RetailReturn> getDataList(int pageIndex, int pageSize,
      QueryRetailReturnVo params) {

    RetailReturnService retailReturnService = ApplicationUtil.getBean(
        RetailReturnService.class);

    return retailReturnService.query(pageIndex, pageSize, params);
  }

  @Override
  public RetailReturnExportModel exportData(RetailReturn data) {
    return new RetailReturnExportModel(data);
  }

  @Override
  public Class<RetailReturnExportModel> getModelClass() {
    return RetailReturnExportModel.class;
  }
}
