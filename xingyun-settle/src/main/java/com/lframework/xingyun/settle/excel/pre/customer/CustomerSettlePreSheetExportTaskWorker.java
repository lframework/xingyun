package com.lframework.xingyun.settle.excel.pre.customer;

import com.lframework.starter.web.core.components.resp.PageResult;
import com.lframework.starter.web.core.utils.ApplicationUtil;
import com.lframework.starter.web.core.utils.JsonUtil;
import com.lframework.starter.mq.core.components.export.ExportTaskWorker;
import com.lframework.xingyun.settle.entity.CustomerSettlePreSheet;
import com.lframework.xingyun.settle.service.CustomerSettlePreSheetService;
import com.lframework.xingyun.settle.vo.pre.customer.QueryCustomerSettlePreSheetVo;

public class CustomerSettlePreSheetExportTaskWorker implements
    ExportTaskWorker<QueryCustomerSettlePreSheetVo, CustomerSettlePreSheet, CustomerSettlePreSheetExportModel> {

  @Override
  public QueryCustomerSettlePreSheetVo parseParams(String json) {
    return JsonUtil.parseObject(json, QueryCustomerSettlePreSheetVo.class);
  }

  @Override
  public PageResult<CustomerSettlePreSheet> getDataList(int pageIndex, int pageSize,
      QueryCustomerSettlePreSheetVo params) {

    CustomerSettlePreSheetService customerSettlePreSheetService = ApplicationUtil.getBean(
        CustomerSettlePreSheetService.class);

    return customerSettlePreSheetService.query(pageIndex, pageSize, params);
  }

  @Override
  public CustomerSettlePreSheetExportModel exportData(CustomerSettlePreSheet data) {
    return new CustomerSettlePreSheetExportModel(data);
  }

  @Override
  public Class<CustomerSettlePreSheetExportModel> getModelClass() {
    return CustomerSettlePreSheetExportModel.class;
  }
}
