package com.lframework.xingyun.sc.api.facade;

import com.lframework.starter.cloud.controller.DefaultClient;
import com.lframework.starter.cloud.resp.ApiInvokeResult;
import com.lframework.starter.cloud.resp.ApiInvokeResultBuilder;
import com.lframework.xingyun.sc.biz.service.stock.IProductStockService;
import com.lframework.xingyun.sc.facade.ProductStockFeignClient;
import com.lframework.xingyun.sc.facade.entity.ProductStock;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 商品库存
 *
 * @author zmj
 */
@Api(tags = "商品库存")
@Validated
@RestController
public class ProductStockClient extends DefaultClient implements
    ProductStockFeignClient {

  @Autowired
  private IProductStockService productStockService;

  @ApiOperation("根据商品ID、仓库ID查询")
  @ApiImplicitParams({
      @ApiImplicitParam(value = "商品ID", name = "productId", paramType = "query", required = true),
      @ApiImplicitParam(value = "仓库ID", name = "scId", paramType = "query", required = true)
  })
  @Override
  public ApiInvokeResult<ProductStock> getByProductIdAndScId(@RequestParam String productId,
      @RequestParam String scId) {
    return ApiInvokeResultBuilder.success(
        productStockService.getByProductIdAndScId(productId, scId));
  }
}
