package com.lframework.xingyun.sc.api.facade;

import com.lframework.starter.cloud.controller.DefaultClient;
import com.lframework.starter.cloud.resp.ApiInvokeResult;
import com.lframework.starter.cloud.resp.ApiInvokeResultBuilder;
import com.lframework.xingyun.sc.biz.service.retail.IRetailOutSheetService;
import com.lframework.xingyun.sc.facade.RetailOutSheetFeignClient;
import com.lframework.xingyun.sc.facade.dto.purchase.receive.GetPaymentDateDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 零售出库单管理
 *
 * @author zmj
 */
@Api(tags = "零售出库单管理")
@Validated
@RestController
public class RetailOutSheetClient extends DefaultClient implements
    RetailOutSheetFeignClient {

  @Autowired
  private IRetailOutSheetService retailOutSheetService;

  @ApiOperation("根据会员ID查询默认付款日期")
  @ApiImplicitParam(value = "会员ID", name = "memberId", paramType = "query", required = true)
  @Override
  public ApiInvokeResult<GetPaymentDateDto> getPaymentDate(@RequestParam String memberId) {
    return ApiInvokeResultBuilder.success(retailOutSheetService.getPaymentDate(memberId));
  }
}
