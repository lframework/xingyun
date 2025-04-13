package com.lframework.xingyun.sc.excel.retail.out;

import com.lframework.starter.web.resp.PageResult;
import com.lframework.starter.web.utils.ApplicationUtil;
import com.lframework.starter.web.utils.JsonUtil;
import com.lframework.xingyun.core.components.export.ExportTaskWorker;
import com.lframework.xingyun.sc.entity.RetailOutSheet;
import com.lframework.xingyun.sc.service.retail.RetailOutSheetService;
import com.lframework.xingyun.sc.vo.retail.out.QueryRetailOutSheetVo;

public class RetailOutSheetExportTaskWorker implements
    ExportTaskWorker<QueryRetailOutSheetVo, RetailOutSheet, RetailOutSheetExportModel> {

  @Override
  public QueryRetailOutSheetVo parseParams(String json) {
    return JsonUtil.parseObject(json, QueryRetailOutSheetVo.class);
  }

  @Override
  public PageResult<RetailOutSheet> getDataList(int pageIndex, int pageSize,
      QueryRetailOutSheetVo params) {

    RetailOutSheetService retailOutSheetService = ApplicationUtil.getBean(
        RetailOutSheetService.class);

    return retailOutSheetService.query(pageIndex, pageSize, params);
  }

  @Override
  public RetailOutSheetExportModel exportData(RetailOutSheet data) {
    return new RetailOutSheetExportModel(data);
  }

  @Override
  public Class<RetailOutSheetExportModel> getModelClass() {
    return RetailOutSheetExportModel.class;
  }
}
