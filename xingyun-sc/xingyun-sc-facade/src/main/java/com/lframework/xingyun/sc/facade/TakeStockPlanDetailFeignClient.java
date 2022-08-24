package com.lframework.xingyun.sc.facade;

import com.lframework.starter.cloud.BaseFeignClient;
import com.lframework.starter.cloud.resp.ApiInvokeResult;
import com.lframework.xingyun.sc.facade.dto.stock.take.plan.GetTakeStockPlanDetailProductDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "xingyun-sc-api", contextId = "TakeStockPlanDetailFeignClient")
public interface TakeStockPlanDetailFeignClient extends BaseFeignClient {

  /**
   * 根据盘点任务ID、商品ID查询
   *
   * @param planId
   * @param productId
   * @return
   */
  @GetMapping("/facade/stock/take/plan/getByPlanIdAndProductId")
  ApiInvokeResult<GetTakeStockPlanDetailProductDto> getByPlanIdAndProductId(
      @RequestParam String planId,
      @RequestParam String productId);
}
