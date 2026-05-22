package com.lframework.xingyun.basedata.controller;

import com.lframework.starter.common.exceptions.impl.DefaultClientException;
import com.lframework.starter.common.utils.CollectionUtil;
import com.lframework.starter.web.core.annotations.security.HasPermission;
import com.lframework.starter.web.core.components.resp.InvokeResult;
import com.lframework.starter.web.core.components.resp.InvokeResultBuilder;
import com.lframework.starter.web.core.components.resp.PageResult;
import com.lframework.starter.web.core.controller.DefaultBaseController;
import com.lframework.starter.web.core.utils.PageResultUtil;
import com.lframework.xingyun.basedata.bo.product.category.RelatedProductCategoryBo;
import com.lframework.xingyun.basedata.bo.product.saleproperty.GetProductSalePropertyBo;
import com.lframework.xingyun.basedata.bo.product.saleproperty.ProductSalePropertyModelorBo;
import com.lframework.xingyun.basedata.bo.product.saleproperty.QueryProductSalePropertyBo;
import com.lframework.xingyun.basedata.dto.product.category.PropertyCategoryCountDto;
import com.lframework.xingyun.basedata.dto.product.saleproperty.ProductSalePropertyModelorDto;
import com.lframework.xingyun.basedata.entity.ProductCategory;
import com.lframework.xingyun.basedata.entity.ProductSaleProperty;
import com.lframework.xingyun.basedata.service.product.ProductCategorySalePropertyService;
import com.lframework.xingyun.basedata.service.product.ProductSalePropertyService;
import com.lframework.xingyun.basedata.vo.product.saleproperty.CreateProductSalePropertyVo;
import com.lframework.xingyun.basedata.vo.product.saleproperty.QueryProductSalePropertyVo;
import com.lframework.xingyun.basedata.vo.product.saleproperty.UpdateProductSalePropertyVo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 商品销售属性管理
 *
 * @author zmj
 */
@Tag(name = "商品销售属性管理")
@Validated
@RestController
@RequestMapping("/basedata/product/sale/property")
public class ProductSalePropertyController extends DefaultBaseController {

  @Autowired
  private ProductSalePropertyService productSalePropertyService;

  @Autowired
  private ProductCategorySalePropertyService productCategorySalePropertyService;

  /**
   * 商品销售属性列表
   */
  @Operation(summary = "商品销售属性列表")
  @HasPermission({"base-data:product:sale-property:query",
      "base-data:product:sale-property:add", "base-data:product:sale-property:modify"})
  @GetMapping("/query")
  public InvokeResult<PageResult<QueryProductSalePropertyBo>> query(
      @Valid QueryProductSalePropertyVo vo) {

    PageResult<ProductSaleProperty> pageResult = productSalePropertyService.query(
        getPageIndex(vo), getPageSize(vo), vo);

    List<ProductSaleProperty> datas = pageResult.getDatas();
    List<QueryProductSalePropertyBo> results = null;

    if (!CollectionUtil.isEmpty(datas)) {
      results = datas.stream().map(QueryProductSalePropertyBo::new).collect(Collectors.toList());
      List<String> propertyIds = results.stream().map(QueryProductSalePropertyBo::getId).collect(
          Collectors.toList());
      Map<String, Integer> categoryCountMap = productCategorySalePropertyService.countCategoriesByPropertyIds(
              propertyIds).stream()
          .collect(Collectors.toMap(PropertyCategoryCountDto::getPropertyId,
              PropertyCategoryCountDto::getCategoryCount));
      results.forEach(t -> t.setCategoryCount(categoryCountMap.getOrDefault(t.getId(), 0)));
    }

    return InvokeResultBuilder.success(PageResultUtil.rebuild(pageResult, results));
  }

  /**
   * 查询商品销售属性
   */
  @Operation(summary = "查询商品销售属性")
  @Parameter(name = "id", description = "ID", in = ParameterIn.QUERY, required = true)
  @HasPermission({"base-data:product:sale-property:query",
      "base-data:product:sale-property:add", "base-data:product:sale-property:modify"})
  @GetMapping
  public InvokeResult<GetProductSalePropertyBo> get(
      @NotBlank(message = "ID不能为空！") String id) {

    ProductSaleProperty data = productSalePropertyService.findById(id);
    if (data == null) {
      throw new DefaultClientException("商品销售属性不存在！");
    }

    GetProductSalePropertyBo result = new GetProductSalePropertyBo(data);

    return InvokeResultBuilder.success(result);
  }

  /**
   * 删除商品销售属性
   */
  @Operation(summary = "删除商品销售属性")
  @HasPermission({"base-data:product:sale-property:delete"})
  @DeleteMapping
  public InvokeResult<Void> deleteById(
      @Parameter(description = "ID", required = true) @NotEmpty(message = "商品销售属性ID不能为空！") String id) {

    productSalePropertyService.deleteById(id);

    productSalePropertyService.cleanCacheByKey(id);

    return InvokeResultBuilder.success();
  }

  /**
   * 新增商品销售属性
   */
  @Operation(summary = "新增商品销售属性")
  @HasPermission({"base-data:product:sale-property:add"})
  @PostMapping
  public InvokeResult<Void> create(@Valid @RequestBody CreateProductSalePropertyVo vo) {

    productSalePropertyService.create(vo);

    return InvokeResultBuilder.success();
  }

  /**
   * 修改商品销售属性
   */
  @Operation(summary = "修改商品销售属性")
  @HasPermission({"base-data:product:sale-property:modify"})
  @PutMapping
  public InvokeResult<Void> update(@Valid @RequestBody UpdateProductSalePropertyVo vo) {

    productSalePropertyService.update(vo);

    productSalePropertyService.cleanCacheByKey(vo.getId());

    return InvokeResultBuilder.success();
  }

  /**
   * 查询已关联商品分类
   */
  @Operation(summary = "查询已关联商品分类")
  @Parameter(name = "id", description = "销售属性ID", in = ParameterIn.QUERY, required = true)
  @HasPermission({"base-data:product:category:query"})
  @GetMapping("/categories")
  public InvokeResult<List<RelatedProductCategoryBo>> getCategories(
      @NotBlank(message = "销售属性ID不能为空！") String id) {

    List<ProductCategory> datas = productCategorySalePropertyService.getCategoriesByPropertyId(id);
    List<RelatedProductCategoryBo> results = CollectionUtil.emptyList();
    if (!CollectionUtil.isEmpty(datas)) {
      results = datas.stream().map(RelatedProductCategoryBo::new).collect(Collectors.toList());
    }

    return InvokeResultBuilder.success(results);
  }

  /**
   * 商品销售属性模型
   */
  @Operation(summary = "商品销售属性模型")
  @GetMapping("/modelor")
  public InvokeResult<List<ProductSalePropertyModelorBo>> getModelor() {

    List<ProductSalePropertyModelorDto> datas = productSalePropertyService.getModelor();
    List<ProductSalePropertyModelorBo> results = CollectionUtil.emptyList();
    if (!CollectionUtil.isEmpty(datas)) {
      results = datas.stream().map(ProductSalePropertyModelorBo::new).collect(Collectors.toList());
    }

    return InvokeResultBuilder.success(results);
  }
}
