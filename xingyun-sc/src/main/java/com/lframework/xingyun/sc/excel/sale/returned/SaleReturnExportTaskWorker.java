package com.lframework.xingyun.sc.excel.sale.returned;

import com.lframework.starter.web.resp.PageResult;
import com.lframework.starter.web.utils.ApplicationUtil;
import com.lframework.starter.web.utils.JsonUtil;
import com.lframework.xingyun.core.components.export.ExportTaskWorker;
import com.lframework.xingyun.sc.entity.SaleReturn;
import com.lframework.xingyun.sc.service.sale.SaleReturnService;
import com.lframework.xingyun.sc.vo.sale.returned.QuerySaleReturnVo;

public class SaleReturnExportTaskWorker implements
    ExportTaskWorker<QuerySaleReturnVo, SaleReturn, SaleReturnExportModel> {

  @Override
  public QuerySaleReturnVo parseParams(String json) {
    return JsonUtil.parseObject(json, QuerySaleReturnVo.class);
  }

  @Override
  public PageResult<SaleReturn> getDataList(int pageIndex, int pageSize, QuerySaleReturnVo params) {

    SaleReturnService saleReturnService = ApplicationUtil.getBean(SaleReturnService.class);

    return saleReturnService.query(pageIndex, pageSize, params);
  }

  @Override
  public SaleReturnExportModel exportData(SaleReturn data) {
    return new SaleReturnExportModel(data);
  }

  @Override
  public Class<SaleReturnExportModel> getModelClass() {
    return SaleReturnExportModel.class;
  }
}
