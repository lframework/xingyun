package com.lframework.xingyun.basedata.api.facade;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.lframework.starter.cloud.controller.DefaultClient;
import com.lframework.starter.cloud.resp.ApiInvokeResult;
import com.lframework.starter.cloud.resp.ApiInvokeResultBuilder;
import com.lframework.xingyun.basedata.biz.service.product.IProductService;
import com.lframework.xingyun.basedata.facade.ProductFeignClient;
import com.lframework.xingyun.basedata.facade.dto.product.info.ProductDto;
import com.lframework.xingyun.basedata.facade.dto.product.info.PurchaseProductDto;
import com.lframework.xingyun.basedata.facade.dto.product.info.RetailProductDto;
import com.lframework.xingyun.basedata.facade.dto.product.info.SaleProductDto;
import com.lframework.xingyun.basedata.facade.entity.Product;
import com.lframework.xingyun.basedata.facade.vo.product.info.QueryProductVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 商品管理
 *
 * @author zmj
 */
@Api(tags = "商品管理")
@Validated
@RestController
public class ProductClient extends DefaultClient implements ProductFeignClient {

  @Autowired
  private IProductService productService;

  @ApiOperation("根据ID查询")
  @ApiImplicitParam(value = "ID", name = "id", paramType = "query", required = true)
  @Override
  public ApiInvokeResult<ProductDto> findById(@RequestParam String id) {
    return ApiInvokeResultBuilder.success(productService.findById(id));
  }

  @ApiOperation("根据ID查询采购商品")
  @ApiImplicitParam(value = "ID", name = "id", paramType = "query", required = true)
  @Override
  public ApiInvokeResult<PurchaseProductDto> getPurchaseById(@RequestParam String id) {
    return ApiInvokeResultBuilder.success(productService.getPurchaseById(id));
  }

  @ApiOperation("根据ID查询销售商品")
  @ApiImplicitParam(value = "ID", name = "id", paramType = "query", required = true)
  @Override
  public ApiInvokeResult<SaleProductDto> getSaleById(@RequestParam String id) {
    return ApiInvokeResultBuilder.success(productService.getSaleById(id));
  }

  @ApiOperation("根据ID查询零售商品")
  @ApiImplicitParam(value = "ID", name = "id", paramType = "query", required = true)
  @Override
  public ApiInvokeResult<RetailProductDto> getRetailById(@RequestParam String id) {
    return ApiInvokeResultBuilder.success(productService.getRetailById(id));
  }

  @ApiOperation("根据编号查询")
  @ApiImplicitParam(value = "商品编号", name = "code", paramType = "query", required = true)
  @Override
  public ApiInvokeResult<Product> getByCode(@RequestParam String code) {
    Wrapper<Product> queryWrapper = Wrappers.lambdaQuery(Product.class)
        .eq(Product::getCode, code);
    Product product = productService.getOne(queryWrapper);

    return ApiInvokeResultBuilder.success(product);
  }

  @ApiOperation("查询列表")
  @Override
  public ApiInvokeResult<List<ProductDto>> query(@Valid @RequestBody QueryProductVo vo) {
    return ApiInvokeResultBuilder.success(productService.query(vo));
  }

  @ApiOperation("查询商品品种数")
  @Override
  public ApiInvokeResult<Integer> queryCount(@Valid @RequestBody QueryProductVo vo) {
    return ApiInvokeResultBuilder.success(productService.queryCount(vo));
  }

  @ApiOperation("根据类目ID查询")
  @Override
  public ApiInvokeResult<List<ProductDto>> getByCategoryIds(
      @ApiParam(value = "类目ID", required = true) @RequestBody List<String> categoryIds) {
    return ApiInvokeResultBuilder.success(productService.getByCategoryIds(categoryIds));
  }

  @ApiOperation("根据品牌ID查询")
  @Override
  public ApiInvokeResult<List<ProductDto>> getByBrandIds(
      @ApiParam(value = "品牌ID", required = true) @RequestBody List<String> brandIds) {
    return ApiInvokeResultBuilder.success(productService.getByBrandIds(brandIds));
  }
}
