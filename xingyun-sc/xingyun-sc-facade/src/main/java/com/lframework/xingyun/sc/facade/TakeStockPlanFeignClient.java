package com.lframework.xingyun.sc.facade;

import com.lframework.starter.cloud.BaseFeignClient;
import com.lframework.starter.cloud.resp.ApiInvokeResult;
import com.lframework.xingyun.sc.facade.entity.TakeStockPlan;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "xingyun-sc-api", contextId = "TakeStockPlanFeignClient")
public interface TakeStockPlanFeignClient extends BaseFeignClient {

  /**
   * 根据ID查询
   *
   * @return
   */
  @GetMapping("/facade/stock/take/plan/getById")
  ApiInvokeResult<TakeStockPlan> getById(@RequestParam String id);
}
