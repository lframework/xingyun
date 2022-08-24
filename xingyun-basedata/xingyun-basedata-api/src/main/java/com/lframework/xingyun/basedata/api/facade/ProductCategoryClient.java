package com.lframework.xingyun.basedata.api.facade;

import com.lframework.starter.cloud.controller.DefaultClient;
import com.lframework.starter.cloud.resp.ApiInvokeResult;
import com.lframework.starter.cloud.resp.ApiInvokeResultBuilder;
import com.lframework.starter.mybatis.service.system.IRecursionMappingService;
import com.lframework.xingyun.basedata.biz.service.product.IProductCategoryService;
import com.lframework.xingyun.basedata.facade.ProductCategoryFeignClient;
import com.lframework.xingyun.basedata.facade.entity.ProductCategory;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 类目管理
 *
 * @author zmj
 */
@Api(tags = "类目管理")
@Validated
@RestController
public class ProductCategoryClient extends DefaultClient implements
    ProductCategoryFeignClient {

  @Autowired
  private IProductCategoryService productCategoryService;

  @Autowired
  private IRecursionMappingService recursionMappingService;

  @ApiOperation("根据ID查询")
  @ApiImplicitParam(value = "ID", name = "id", paramType = "query", required = true)
  @Override
  public ApiInvokeResult<ProductCategory> findById(@RequestParam String id) {
    return ApiInvokeResultBuilder.success(productCategoryService.findById(id));
  }
}
