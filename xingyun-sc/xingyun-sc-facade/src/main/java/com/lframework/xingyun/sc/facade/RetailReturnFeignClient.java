package com.lframework.xingyun.sc.facade;

import com.lframework.starter.cloud.BaseFeignClient;
import com.lframework.starter.cloud.resp.ApiInvokeResult;
import com.lframework.xingyun.sc.facade.entity.RetailReturn;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "xingyun-sc-api", contextId = "RetailReturnFeignClient")
public interface RetailReturnFeignClient extends BaseFeignClient {

  /**
   * 根据ID查询
   *
   * @param id
   * @return
   */
  @GetMapping("/facade/retail/return/getById")
  ApiInvokeResult<RetailReturn> getById(@RequestParam String id);
}
