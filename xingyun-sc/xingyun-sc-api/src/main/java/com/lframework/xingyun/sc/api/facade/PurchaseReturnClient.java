package com.lframework.xingyun.sc.api.facade;

import com.lframework.starter.cloud.controller.DefaultClient;
import com.lframework.starter.cloud.resp.ApiInvokeResult;
import com.lframework.starter.cloud.resp.ApiInvokeResultBuilder;
import com.lframework.xingyun.core.enums.SettleStatus;
import com.lframework.xingyun.sc.biz.service.purchase.IPurchaseReturnService;
import com.lframework.xingyun.sc.biz.service.purchase.IPurchaseReturnTccService;
import com.lframework.xingyun.sc.facade.PurchaseReturnFeignClient;
import com.lframework.xingyun.sc.facade.entity.PurchaseReturn;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 采购退单管理
 *
 * @author zmj
 */
@Api(tags = "采购退单管理")
@Validated
@RestController
public class PurchaseReturnClient extends DefaultClient implements
    PurchaseReturnFeignClient {

  @Autowired
  private IPurchaseReturnService purchaseReturnService;

  @Autowired
  private IPurchaseReturnTccService purchaseReturnTccService;

  @ApiOperation("根据ID查询")
  @ApiImplicitParam(value = "ID", name = "id", paramType = "query", required = true)
  @Override
  public ApiInvokeResult<PurchaseReturn> getById(@RequestParam String id) {
    return ApiInvokeResultBuilder.success(purchaseReturnService.getById(id));
  }

  @ApiOperation("查询已审核列表")
  @ApiImplicitParams({
      @ApiImplicitParam(value = "供应商ID", name = "supplierId", paramType = "query", required = true),
      @ApiImplicitParam(value = "起始时间", name = "startTime", paramType = "query"),
      @ApiImplicitParam(value = "截止时间", name = "endTime", paramType = "query"),
      @ApiImplicitParam(value = "结算状态", name = "settleStatus", paramType = "query", required = true)})
  @Override
  public ApiInvokeResult<List<PurchaseReturn>> getApprovedList(@RequestParam String supplierId,
      @RequestParam(required = false) LocalDateTime startTime,
      @RequestParam(required = false) LocalDateTime endTime,
      @RequestParam SettleStatus settleStatus) {
    return ApiInvokeResultBuilder.success(
        purchaseReturnService.getApprovedList(supplierId, startTime, endTime, settleStatus));
  }

  @ApiOperation("设置成未结算")
  @ApiImplicitParam(value = "ID", name = "id", paramType = "query", required = true)
  @Override
  public ApiInvokeResult<Integer> setUnSettle(@RequestParam String id) {
    return ApiInvokeResultBuilder.success(purchaseReturnTccService.setUnSettle(id));
  }

  @ApiOperation("设置成结算中")
  @ApiImplicitParam(value = "ID", name = "id", paramType = "query", required = true)
  @Override
  public ApiInvokeResult<Integer> setPartSettle(@RequestParam String id) {
    return ApiInvokeResultBuilder.success(purchaseReturnTccService.setPartSettle(id));
  }

  @ApiOperation("设置成已结算")
  @ApiImplicitParam(value = "ID", name = "id", paramType = "query", required = true)
  @Override
  public ApiInvokeResult<Integer> setSettled(@RequestParam String id) {
    return ApiInvokeResultBuilder.success(purchaseReturnTccService.setSettled(id));
  }
}
