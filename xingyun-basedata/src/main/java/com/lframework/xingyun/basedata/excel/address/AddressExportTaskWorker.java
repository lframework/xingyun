package com.lframework.xingyun.basedata.excel.address;

import com.lframework.starter.web.core.components.resp.PageResult;
import com.lframework.starter.web.core.utils.ApplicationUtil;
import com.lframework.starter.web.core.utils.JsonUtil;
import com.lframework.xingyun.basedata.entity.Address;
import com.lframework.xingyun.basedata.service.address.AddressService;
import com.lframework.xingyun.basedata.vo.address.QueryAddressVo;
import com.lframework.starter.mq.core.components.export.ExportTaskWorker;

public class AddressExportTaskWorker implements
    ExportTaskWorker<QueryAddressVo, Address, AddressExportModel> {

  @Override
  public QueryAddressVo parseParams(String json) {
    return JsonUtil.parseObject(json, QueryAddressVo.class);
  }

  @Override
  public PageResult<Address> getDataList(int pageIndex, int pageSize, QueryAddressVo params) {
    AddressService addressService = ApplicationUtil.getBean(AddressService.class);

    return addressService.query(pageIndex, pageSize, params);
  }

  @Override
  public AddressExportModel exportData(Address data) {
    return new AddressExportModel(data);
  }

  @Override
  public Class<AddressExportModel> getModelClass() {
    return AddressExportModel.class;
  }
}
