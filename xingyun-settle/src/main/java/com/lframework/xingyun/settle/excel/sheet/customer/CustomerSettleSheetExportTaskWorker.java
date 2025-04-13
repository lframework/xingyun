package com.lframework.xingyun.settle.excel.sheet.customer;

import com.lframework.starter.web.resp.PageResult;
import com.lframework.starter.web.utils.ApplicationUtil;
import com.lframework.starter.web.utils.JsonUtil;
import com.lframework.xingyun.core.components.export.ExportTaskWorker;
import com.lframework.xingyun.settle.entity.CustomerSettleSheet;
import com.lframework.xingyun.settle.service.CustomerSettleSheetService;
import com.lframework.xingyun.settle.vo.sheet.customer.QueryCustomerSettleSheetVo;

public class CustomerSettleSheetExportTaskWorker implements
    ExportTaskWorker<QueryCustomerSettleSheetVo, CustomerSettleSheet, CustomerSettleSheetExportModel> {

  @Override
  public QueryCustomerSettleSheetVo parseParams(String json) {
    return JsonUtil.parseObject(json, QueryCustomerSettleSheetVo.class);
  }

  @Override
  public PageResult<CustomerSettleSheet> getDataList(int pageIndex, int pageSize,
      QueryCustomerSettleSheetVo params) {

    CustomerSettleSheetService customerSettleSheetService = ApplicationUtil.getBean(
        CustomerSettleSheetService.class);

    return customerSettleSheetService.query(pageIndex, pageSize, params);
  }

  @Override
  public CustomerSettleSheetExportModel exportData(CustomerSettleSheet data) {
    return new CustomerSettleSheetExportModel(data);
  }

  @Override
  public Class<CustomerSettleSheetExportModel> getModelClass() {
    return CustomerSettleSheetExportModel.class;
  }
}
