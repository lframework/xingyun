package com.lframework.xingyun.sc.api.facade;

import com.lframework.starter.cloud.controller.DefaultClient;
import com.lframework.starter.cloud.resp.ApiInvokeResult;
import com.lframework.starter.cloud.resp.ApiInvokeResultBuilder;
import com.lframework.xingyun.core.enums.SettleStatus;
import com.lframework.xingyun.sc.biz.service.sale.ISaleOutSheetService;
import com.lframework.xingyun.sc.biz.service.sale.ISaleOutSheetTccService;
import com.lframework.xingyun.sc.facade.SaleOutSheetFeignClient;
import com.lframework.xingyun.sc.facade.dto.purchase.receive.GetPaymentDateDto;
import com.lframework.xingyun.sc.facade.entity.SaleOutSheet;
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
 * 销售出库单管理
 *
 * @author zmj
 */
@Api(tags = "销售出库单管理")
@Validated
@RestController
public class SaleOutSheetClient extends DefaultClient implements
    SaleOutSheetFeignClient {

  @Autowired
  private ISaleOutSheetService saleOutSheetService;

  @Autowired
  private ISaleOutSheetTccService saleOutSheetTccService;

  @ApiOperation("根据ID查询")
  @ApiImplicitParam(value = "ID", name = "id", paramType = "query", required = true)
  @Override
  public ApiInvokeResult<SaleOutSheet> getById(@RequestParam String id) {
    return ApiInvokeResultBuilder.success(saleOutSheetService.getById(id));
  }

  @ApiOperation("查询已审核列表")
  @ApiImplicitParams({
      @ApiImplicitParam(value = "客户ID", name = "customerId", paramType = "query", required = true),
      @ApiImplicitParam(value = "起始时间", name = "startTime", paramType = "query"),
      @ApiImplicitParam(value = "截止时间", name = "endTime", paramType = "query"),
      @ApiImplicitParam(value = "结算状态", name = "settleStatus", paramType = "query", required = true)})
  @Override
  public ApiInvokeResult<List<SaleOutSheet>> getApprovedList(@RequestParam String customerId,
      @RequestParam(required = false) LocalDateTime startTime,
      @RequestParam(required = false) LocalDateTime endTime,
      @RequestParam SettleStatus settleStatus) {
    return ApiInvokeResultBuilder.success(
        saleOutSheetService.getApprovedList(customerId, startTime, endTime, settleStatus));
  }

  @ApiOperation("设置成未结算")
  @ApiImplicitParam(value = "ID", name = "id", paramType = "query", required = true)
  @Override
  public ApiInvokeResult<Integer> setUnSettle(@RequestParam String id) {
    return ApiInvokeResultBuilder.success(saleOutSheetTccService.setUnSettle(id));
  }

  @ApiOperation("设置成结算中")
  @ApiImplicitParam(value = "ID", name = "id", paramType = "query", required = true)
  @Override
  public ApiInvokeResult<Integer> setPartSettle(@RequestParam String id) {
    return ApiInvokeResultBuilder.success(saleOutSheetTccService.setPartSettle(id));
  }

  @ApiOperation("设置成已结算")
  @ApiImplicitParam(value = "ID", name = "id", paramType = "query", required = true)
  @Override
  public ApiInvokeResult<Integer> setSettled(@RequestParam String id) {
    return ApiInvokeResultBuilder.success(saleOutSheetTccService.setSettled(id));
  }

  @ApiOperation("根据客户ID查询默认付款日期")
  @ApiImplicitParam(value = "客户ID", name = "customerId", paramType = "query", required = true)
  @Override
  public ApiInvokeResult<GetPaymentDateDto> getPaymentDate(@RequestParam String customerId) {
    return ApiInvokeResultBuilder.success(saleOutSheetService.getPaymentDate(customerId));
  }
}
