package com.lframework.xingyun.basedata.api.facade;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.lframework.starter.cloud.controller.DefaultClient;
import com.lframework.starter.cloud.resp.ApiInvokeResult;
import com.lframework.starter.cloud.resp.ApiInvokeResultBuilder;
import com.lframework.xingyun.basedata.biz.service.supplier.ISupplierService;
import com.lframework.xingyun.basedata.facade.SupplierFeignClient;
import com.lframework.xingyun.basedata.facade.entity.Supplier;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 供应商管理
 *
 * @author zmj
 */
@Api(tags = "供应商管理")
@Validated
@RestController
public class SupplierClient extends DefaultClient implements SupplierFeignClient {

  @Autowired
  private ISupplierService supplierService;

  @ApiOperation("根据ID查询")
  @ApiImplicitParam(value = "ID", name = "id", paramType = "query", required = true)
  @Override
  public ApiInvokeResult<Supplier> findById(@RequestParam String id) {

    return ApiInvokeResultBuilder.success(supplierService.findById(id));
  }

  @ApiOperation("根据编号查询")
  @ApiImplicitParam(value = "编号", name = "code", paramType = "query", required = true)
  @Override
  public ApiInvokeResult<Supplier> getByCode(@RequestParam String code) {
    Wrapper<Supplier> queryWrapper = Wrappers.lambdaQuery(Supplier.class)
        .eq(Supplier::getCode, code);
    Supplier supplier = supplierService.getOne(queryWrapper);

    return ApiInvokeResultBuilder.success(supplier);
  }
}
