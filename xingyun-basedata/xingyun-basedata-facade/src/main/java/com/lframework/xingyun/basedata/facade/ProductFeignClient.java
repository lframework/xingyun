package com.lframework.xingyun.basedata.facade;

import com.lframework.starter.cloud.BaseFeignClient;
import com.lframework.starter.cloud.resp.ApiInvokeResult;
import com.lframework.xingyun.basedata.facade.dto.product.info.ProductDto;
import com.lframework.xingyun.basedata.facade.dto.product.info.PurchaseProductDto;
import com.lframework.xingyun.basedata.facade.dto.product.info.RetailProductDto;
import com.lframework.xingyun.basedata.facade.dto.product.info.SaleProductDto;
import com.lframework.xingyun.basedata.facade.entity.Product;
import com.lframework.xingyun.basedata.facade.vo.product.info.QueryProductVo;
import java.util.List;
import javax.validation.Valid;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "xingyun-basedata-api", contextId = "ProductFeignClient")
public interface ProductFeignClient extends BaseFeignClient {

  /**
   * 根据ID查询
   *
   * @param id
   * @return
   */
  @GetMapping("/facade/basedata/product/findById")
  ApiInvokeResult<ProductDto> findById(@RequestParam String id);


  /**
   * 根据ID查询采购商品
   *
   * @param id
   * @return
   */
  @GetMapping("/facade/basedata/product/getPurchaseById")
  ApiInvokeResult<PurchaseProductDto> getPurchaseById(@RequestParam String id);

  /**
   * 根据ID查询销售商品
   *
   * @param id
   * @return
   */
  @GetMapping("/facade/basedata/product/getSaleById")
  ApiInvokeResult<SaleProductDto> getSaleById(@RequestParam String id);

  /**
   * 根据ID查询零售商品
   *
   * @param id
   * @return
   */
  @GetMapping("/facade/basedata/product/getRetailById")
  ApiInvokeResult<RetailProductDto> getRetailById(@RequestParam String id);

  /**
   * 根据编号查询
   *
   * @param code
   * @return
   */
  @GetMapping("/facade/basedata/product/getByCode")
  ApiInvokeResult<Product> getByCode(@RequestParam String code);

  /**
   * 查询列表
   *
   * @param vo
   * @return
   */
  @GetMapping("/facade/basedata/product/query")
  ApiInvokeResult<List<ProductDto>> query(@Valid @RequestBody QueryProductVo vo);

  /**
   * 查询商品品种数
   *
   * @param vo
   * @return
   */
  @GetMapping("/facade/basedata/product/queryCount")
  ApiInvokeResult<Integer> queryCount(@Valid @RequestBody QueryProductVo vo);

  /**
   * 根据类目ID查询
   *
   * @param categoryIds
   * @return
   */
  @GetMapping("/facade/basedata/product/getByCategoryIds")
  ApiInvokeResult<List<ProductDto>> getByCategoryIds(@RequestBody List<String> categoryIds);

  /**
   * 根据品牌ID查询
   *
   * @param brandIds
   * @return
   */
  @GetMapping("/facade/basedata/product/getByBrandIds")
  ApiInvokeResult<List<ProductDto>> getByBrandIds(@RequestBody List<String> brandIds);

}
