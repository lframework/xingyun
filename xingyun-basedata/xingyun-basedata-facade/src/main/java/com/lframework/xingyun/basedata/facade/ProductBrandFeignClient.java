package com.lframework.xingyun.basedata.facade;

import com.lframework.starter.cloud.BaseFeignClient;
import com.lframework.starter.cloud.resp.ApiInvokeResult;
import com.lframework.xingyun.basedata.facade.entity.ProductBrand;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "xingyun-basedata-api", contextId = "ProductBrandFeignClient")
public interface ProductBrandFeignClient extends BaseFeignClient {

  /**
   * 根据ID查询
   *
   * @param id
   * @return
   */
  @GetMapping("/facade/basedata/product/brand/findById")
  ApiInvokeResult<ProductBrand> findById(@RequestParam String id);
}
