package com.lframework.xingyun.basedata.api.facade;

import com.lframework.starter.cloud.controller.DefaultClient;
import com.lframework.starter.cloud.resp.ApiInvokeResult;
import com.lframework.starter.cloud.resp.ApiInvokeResultBuilder;
import com.lframework.xingyun.basedata.biz.service.product.IProductBrandService;
import com.lframework.xingyun.basedata.facade.ProductBrandFeignClient;
import com.lframework.xingyun.basedata.facade.entity.ProductBrand;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 品牌管理
 *
 * @author zmj
 */
@Api(tags = "品牌管理")
@Validated
@RestController
public class ProductBrandClient extends DefaultClient implements
    ProductBrandFeignClient {

  @Autowired
  private IProductBrandService productBrandService;

  @ApiOperation("根据ID查询")
  @ApiImplicitParam(value = "ID", name = "id", paramType = "query", required = true)
  @Override
  public ApiInvokeResult<ProductBrand> findById(@RequestParam String id) {
    return ApiInvokeResultBuilder.success(productBrandService.findById(id));
  }
}
