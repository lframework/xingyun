package com.lframework.xingyun.basedata.api.facade;

import com.lframework.starter.cloud.controller.DefaultClient;
import com.lframework.starter.cloud.resp.ApiInvokeResult;
import com.lframework.starter.cloud.resp.ApiInvokeResultBuilder;
import com.lframework.xingyun.basedata.biz.service.product.IProductSalePropItemRelationService;
import com.lframework.xingyun.basedata.facade.ProductSalePropItemRelationFeignClient;
import com.lframework.xingyun.basedata.facade.dto.product.saleprop.item.SalePropItemByProductDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 销售属性关系
 *
 * @author zmj
 */
@Api(tags = "销售属性关系")
@Validated
@RestController
public class ProductSalePropItemRelationClient extends DefaultClient implements
    ProductSalePropItemRelationFeignClient {

  @Autowired
  private IProductSalePropItemRelationService productSalePropItemRelationService;

  @ApiOperation("根据商品ID查询")
  @ApiImplicitParam(value = "商品ID", name = "productId", paramType = "query", required = true)
  @Override
  public ApiInvokeResult<SalePropItemByProductDto> getByProductId(@RequestParam String productId) {
    return ApiInvokeResultBuilder.success(
        productSalePropItemRelationService.getByProductId(productId));
  }
}
