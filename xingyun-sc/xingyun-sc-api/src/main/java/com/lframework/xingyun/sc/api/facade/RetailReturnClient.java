package com.lframework.xingyun.sc.api.facade;

import com.lframework.starter.cloud.controller.DefaultClient;
import com.lframework.starter.cloud.resp.ApiInvokeResult;
import com.lframework.starter.cloud.resp.ApiInvokeResultBuilder;
import com.lframework.xingyun.sc.biz.service.retail.IRetailReturnService;
import com.lframework.xingyun.sc.facade.RetailReturnFeignClient;
import com.lframework.xingyun.sc.facade.entity.RetailReturn;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 零售退单管理
 *
 * @author zmj
 */
@Api(tags = "零售退单管理")
@Validated
@RestController
public class RetailReturnClient extends DefaultClient implements
    RetailReturnFeignClient {

  @Autowired
  private IRetailReturnService retailReturnService;


  @ApiOperation("根据ID查询")
  @ApiImplicitParam(value = "ID", name = "id", paramType = "query", required = true)
  @Override
  public ApiInvokeResult<RetailReturn> getById(@RequestParam String id) {
    return ApiInvokeResultBuilder.success(retailReturnService.getById(id));
  }
}
