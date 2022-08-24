package com.lframework.xingyun.sc.api.facade;

import com.lframework.starter.cloud.controller.DefaultClient;
import com.lframework.starter.cloud.resp.ApiInvokeResult;
import com.lframework.starter.cloud.resp.ApiInvokeResultBuilder;
import com.lframework.xingyun.sc.biz.service.stock.take.ITakeStockPlanService;
import com.lframework.xingyun.sc.facade.TakeStockPlanFeignClient;
import com.lframework.xingyun.sc.facade.entity.TakeStockPlan;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 盘点任务
 *
 * @author zmj
 */
@Api(tags = "盘点任务")
@Validated
@RestController
public class TakeStockPlanClient extends DefaultClient implements TakeStockPlanFeignClient {

  @Autowired
  private ITakeStockPlanService takeStockPlanService;

  @ApiOperation("根据ID查询")
  @ApiImplicitParam(value = "ID", name = "id", paramType = "query", required = true)
  @Override
  public ApiInvokeResult<TakeStockPlan> getById(@RequestParam String id) {
    return ApiInvokeResultBuilder.success(takeStockPlanService.getById(id));
  }
}
