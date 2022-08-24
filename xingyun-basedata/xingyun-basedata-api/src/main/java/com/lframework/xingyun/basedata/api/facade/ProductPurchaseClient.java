package com.lframework.xingyun.basedata.api.facade;

import com.lframework.starter.cloud.controller.DefaultClient;
import com.lframework.starter.cloud.resp.ApiInvokeResult;
import com.lframework.starter.cloud.resp.ApiInvokeResultBuilder;
import com.lframework.xingyun.basedata.biz.service.product.IProductPurchaseService;
import com.lframework.xingyun.basedata.facade.ProductPurchaseFeignClient;
import com.lframework.xingyun.basedata.facade.entity.ProductPurchase;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 商品采购价管理
 *
 * @author zmj
 */
@Api(tags = "商品采购价管理")
@Validated
@RestController
public class ProductPurchaseClient extends DefaultClient implements
    ProductPurchaseFeignClient {

  @Autowired
  private IProductPurchaseService productPurchaseService;

  @ApiOperation("根据ID查询")
  @ApiImplicitParam(value = "商品ID", name = "id", paramType = "query", required = true)
  @Override
  public ApiInvokeResult<ProductPurchase> getById(@RequestParam String id) {
    return ApiInvokeResultBuilder.success(productPurchaseService.getById(id));
  }
}
