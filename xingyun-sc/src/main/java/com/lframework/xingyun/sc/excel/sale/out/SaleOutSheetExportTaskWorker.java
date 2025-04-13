package com.lframework.xingyun.sc.excel.sale.out;

import com.lframework.starter.web.resp.PageResult;
import com.lframework.starter.web.utils.ApplicationUtil;
import com.lframework.starter.web.utils.JsonUtil;
import com.lframework.xingyun.core.components.export.ExportTaskWorker;
import com.lframework.xingyun.sc.entity.SaleOutSheet;
import com.lframework.xingyun.sc.service.sale.SaleOutSheetService;
import com.lframework.xingyun.sc.vo.sale.out.QuerySaleOutSheetVo;

public class SaleOutSheetExportTaskWorker implements
    ExportTaskWorker<QuerySaleOutSheetVo, SaleOutSheet, SaleOutSheetExportModel> {

  @Override
  public QuerySaleOutSheetVo parseParams(String json) {
    return JsonUtil.parseObject(json, QuerySaleOutSheetVo.class);
  }

  @Override
  public PageResult<SaleOutSheet> getDataList(int pageIndex, int pageSize,
      QuerySaleOutSheetVo params) {

    SaleOutSheetService saleOutSheetService = ApplicationUtil.getBean(
        SaleOutSheetService.class);

    return saleOutSheetService.query(pageIndex, pageSize, params);
  }

  @Override
  public SaleOutSheetExportModel exportData(SaleOutSheet data) {
    return new SaleOutSheetExportModel(data);
  }

  @Override
  public Class<SaleOutSheetExportModel> getModelClass() {
    return SaleOutSheetExportModel.class;
  }
}
