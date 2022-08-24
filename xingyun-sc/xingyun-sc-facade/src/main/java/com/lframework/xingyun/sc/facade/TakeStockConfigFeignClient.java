package com.lframework.xingyun.sc.facade;

import com.lframework.starter.cloud.BaseFeignClient;
import com.lframework.starter.cloud.resp.ApiInvokeResult;
import com.lframework.xingyun.sc.facade.entity.TakeStockConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(value = "xingyun-sc-api", contextId = "TakeStockConfigFeignClient")
public interface TakeStockConfigFeignClient extends BaseFeignClient {

  /**
   * 查询详情
   *
   * @return
   */
  @GetMapping("/facade/stock/take/config/get")
  ApiInvokeResult<TakeStockConfig> get();
}
