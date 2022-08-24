package com.lframework.xingyun.sc.facade;

import com.lframework.starter.cloud.BaseFeignClient;
import com.lframework.starter.cloud.resp.ApiInvokeResult;
import com.lframework.xingyun.sc.facade.dto.purchase.receive.GetPaymentDateDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "xingyun-sc-api", contextId = "RetailOutSheetFeignClient")
public interface RetailOutSheetFeignClient extends BaseFeignClient {

  /**
   * 根据会员ID查询默认付款日期
   *
   * @param memberId
   */
  @GetMapping("/facade/retail/out/sheet/getPaymentDate")
  ApiInvokeResult<GetPaymentDateDto> getPaymentDate(@RequestParam String memberId);
}
