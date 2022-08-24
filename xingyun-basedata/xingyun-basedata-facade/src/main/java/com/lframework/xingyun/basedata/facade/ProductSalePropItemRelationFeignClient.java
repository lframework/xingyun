package com.lframework.xingyun.basedata.facade;

import com.lframework.starter.cloud.BaseFeignClient;
import com.lframework.starter.cloud.resp.ApiInvokeResult;
import com.lframework.xingyun.basedata.facade.dto.product.saleprop.item.SalePropItemByProductDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "xingyun-basedata-api", contextId = "ProductSalePropItemRelationFeignClient")
public interface ProductSalePropItemRelationFeignClient extends BaseFeignClient {

  /**
   * 根据商品ID查询
   *
   * @param productId
   * @return
   */
  @GetMapping("/facade/basedata/product/saleprop/item/relation/getByProductId")
  ApiInvokeResult<SalePropItemByProductDto> getByProductId(@RequestParam String productId);
}
