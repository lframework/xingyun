package com.lframework.xingyun.settle.excel.check.customer;

import com.lframework.starter.web.resp.PageResult;
import com.lframework.starter.web.utils.ApplicationUtil;
import com.lframework.starter.web.utils.JsonUtil;
import com.lframework.xingyun.core.components.export.ExportTaskWorker;
import com.lframework.xingyun.settle.entity.CustomerSettleCheckSheet;
import com.lframework.xingyun.settle.service.CustomerSettleCheckSheetService;
import com.lframework.xingyun.settle.vo.check.customer.QueryCustomerSettleCheckSheetVo;

public class CustomerSettleCheckSheetExportTaskWorker implements
    ExportTaskWorker<QueryCustomerSettleCheckSheetVo, CustomerSettleCheckSheet, CustomerSettleCheckSheetExportModel> {

  @Override
  public QueryCustomerSettleCheckSheetVo parseParams(String json) {
    return JsonUtil.parseObject(json, QueryCustomerSettleCheckSheetVo.class);
  }

  @Override
  public PageResult<CustomerSettleCheckSheet> getDataList(int pageIndex, int pageSize,
      QueryCustomerSettleCheckSheetVo params) {

    CustomerSettleCheckSheetService customerSettleCheckSheetService = ApplicationUtil.getBean(
        CustomerSettleCheckSheetService.class);

    return customerSettleCheckSheetService.query(pageIndex, pageSize, params);
  }

  @Override
  public CustomerSettleCheckSheetExportModel exportData(CustomerSettleCheckSheet data) {
    return new CustomerSettleCheckSheetExportModel(data);
  }

  @Override
  public Class<CustomerSettleCheckSheetExportModel> getModelClass() {
    return CustomerSettleCheckSheetExportModel.class;
  }
}
