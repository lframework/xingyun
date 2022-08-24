package com.lframework.xingyun.basedata.api.facade;

import com.lframework.starter.cloud.controller.DefaultClient;
import com.lframework.starter.cloud.resp.ApiInvokeResult;
import com.lframework.starter.cloud.resp.ApiInvokeResultBuilder;
import com.lframework.xingyun.basedata.biz.service.customer.ICustomerService;
import com.lframework.xingyun.basedata.facade.CustomerFeignClient;
import com.lframework.xingyun.basedata.facade.entity.Customer;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 客户管理
 *
 * @author zmj
 */
@Api(tags = "客户管理")
@Validated
@RestController
public class CustomerClient extends DefaultClient implements CustomerFeignClient {

  @Autowired
  private ICustomerService customerService;

  @ApiOperation("根据ID查询")
  @ApiImplicitParam(value = "ID", name = "id", paramType = "query", required = true)
  @Override
  public ApiInvokeResult<Customer> findById(@RequestParam String id) {
    return ApiInvokeResultBuilder.success(customerService.findById(id));
  }
}
