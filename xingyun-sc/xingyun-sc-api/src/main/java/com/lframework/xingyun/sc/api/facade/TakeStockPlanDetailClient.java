package com.lframework.xingyun.sc.api.facade;

import com.lframework.starter.cloud.controller.DefaultClient;
import com.lframework.starter.cloud.resp.ApiInvokeResult;
import com.lframework.starter.cloud.resp.ApiInvokeResultBuilder;
import com.lframework.xingyun.sc.biz.service.stock.take.ITakeStockPlanDetailService;
import com.lframework.xingyun.sc.facade.TakeStockPlanDetailFeignClient;
import com.lframework.xingyun.sc.facade.dto.stock.take.plan.GetTakeStockPlanDetailProductDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 盘点任务明细
 *
 * @author zmj
 */
@Api(tags = "盘点任务明细")
@Validated
@RestController
public class TakeStockPlanDetailClient extends DefaultClient implements
    TakeStockPlanDetailFeignClient {

  @Autowired
  private ITakeStockPlanDetailService takeStockPlanDetailService;

  @ApiOperation("根据盘点任务ID、商品ID查询")
  @ApiImplicitParams({
      @ApiImplicitParam(value = "盘点任务ID", name = "planId", paramType = "query", required = true),
      @ApiImplicitParam(value = "商品ID", name = "productId", paramType = "query", required = true)})
  @Override
  public ApiInvokeResult<GetTakeStockPlanDetailProductDto> getByPlanIdAndProductId(
      @RequestParam String planId, @RequestParam String productId) {
    return ApiInvokeResultBuilder.success(
        takeStockPlanDetailService.getByPlanIdAndProductId(planId, productId));
  }
}
