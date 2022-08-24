package com.lframework.xingyun.sc.facade;

import com.lframework.starter.cloud.BaseFeignClient;
import com.lframework.starter.cloud.resp.ApiInvokeResult;
import com.lframework.xingyun.sc.facade.entity.ProductStock;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "xingyun-sc-api", contextId = "ProductStockFeignClient")
public interface ProductStockFeignClient extends BaseFeignClient {

  /**
   * 根据商品ID、仓库ID查询
   *
   * @param productId
   * @param scId
   * @return
   */
  @GetMapping("/facade/stock/product/getByProductIdAndScId")
  ApiInvokeResult<ProductStock> getByProductIdAndScId(@RequestParam String productId,
      @RequestParam String scId);
}
