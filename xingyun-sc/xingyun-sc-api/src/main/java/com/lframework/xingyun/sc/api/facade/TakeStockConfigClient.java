package com.lframework.xingyun.sc.api.facade;

import com.lframework.starter.cloud.controller.DefaultClient;
import com.lframework.starter.cloud.resp.ApiInvokeResult;
import com.lframework.starter.cloud.resp.ApiInvokeResultBuilder;
import com.lframework.xingyun.sc.biz.service.stock.take.ITakeStockConfigService;
import com.lframework.xingyun.sc.facade.TakeStockConfigFeignClient;
import com.lframework.xingyun.sc.facade.entity.TakeStockConfig;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RestController;

/**
 * 盘点参数
 *
 * @author zmj
 */
@Api(tags = "盘点参数")
@Validated
@RestController
public class TakeStockConfigClient extends DefaultClient implements
    TakeStockConfigFeignClient {

  @Autowired
  private ITakeStockConfigService takeStockConfigService;

  @ApiOperation("查询详情")
  @Override
  public ApiInvokeResult<TakeStockConfig> get() {
    return ApiInvokeResultBuilder.success(takeStockConfigService.get());
  }
}
