package com.lframework.xingyun.settle.excel.fee.customer;

import com.lframework.starter.web.resp.PageResult;
import com.lframework.starter.web.utils.ApplicationUtil;
import com.lframework.starter.web.utils.JsonUtil;
import com.lframework.xingyun.core.components.export.ExportTaskWorker;
import com.lframework.xingyun.settle.entity.CustomerSettleFeeSheet;
import com.lframework.xingyun.settle.service.CustomerSettleFeeSheetService;
import com.lframework.xingyun.settle.vo.fee.customer.QueryCustomerSettleFeeSheetVo;

public class CustomerSettleFeeSheetExportTaskWorker implements
    ExportTaskWorker<QueryCustomerSettleFeeSheetVo, CustomerSettleFeeSheet, CustomerSettleFeeSheetExportModel> {

  @Override
  public QueryCustomerSettleFeeSheetVo parseParams(String json) {
    return JsonUtil.parseObject(json, QueryCustomerSettleFeeSheetVo.class);
  }

  @Override
  public PageResult<CustomerSettleFeeSheet> getDataList(int pageIndex, int pageSize,
      QueryCustomerSettleFeeSheetVo params) {

    CustomerSettleFeeSheetService customerSettleFeeSheetService = ApplicationUtil.getBean(
        CustomerSettleFeeSheetService.class);

    return customerSettleFeeSheetService.query(pageIndex, pageSize, params);
  }

  @Override
  public CustomerSettleFeeSheetExportModel exportData(CustomerSettleFeeSheet data) {
    return new CustomerSettleFeeSheetExportModel(data);
  }

  @Override
  public Class<CustomerSettleFeeSheetExportModel> getModelClass() {
    return CustomerSettleFeeSheetExportModel.class;
  }
}
