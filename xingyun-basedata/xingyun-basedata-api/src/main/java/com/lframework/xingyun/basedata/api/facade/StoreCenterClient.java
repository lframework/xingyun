package com.lframework.xingyun.basedata.api.facade;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.lframework.starter.cloud.controller.DefaultClient;
import com.lframework.starter.cloud.resp.ApiInvokeResult;
import com.lframework.starter.cloud.resp.ApiInvokeResultBuilder;
import com.lframework.xingyun.basedata.biz.service.storecenter.IStoreCenterService;
import com.lframework.xingyun.basedata.facade.StoreCenterFeignClient;
import com.lframework.xingyun.basedata.facade.entity.StoreCenter;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 仓库管理
 *
 * @author zmj
 */
@Api(tags = "仓库管理")
@Validated
@RestController
public class StoreCenterClient extends DefaultClient implements StoreCenterFeignClient {

  @Autowired
  private IStoreCenterService storeCenterService;

  @ApiOperation("根据ID查询")
  @ApiImplicitParam(value = "ID", name = "id", paramType = "query", required = true)
  @Override
  public ApiInvokeResult<StoreCenter> findById(@RequestParam String id) {

    return ApiInvokeResultBuilder.success(storeCenterService.findById(id));
  }

  @ApiOperation("根据编号查询")
  @ApiImplicitParam(value = "编号", name = "code", paramType = "query", required = true)
  @Override
  public ApiInvokeResult<StoreCenter> getByCode(@RequestParam String code) {
    Wrapper<StoreCenter> queryWrapper = Wrappers.lambdaQuery(StoreCenter.class)
        .eq(StoreCenter::getCode, code);
    StoreCenter sc = storeCenterService.getOne(queryWrapper);
    return ApiInvokeResultBuilder.success(sc);
  }
}
