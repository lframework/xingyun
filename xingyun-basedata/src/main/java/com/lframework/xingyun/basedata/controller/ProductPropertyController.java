package com.lframework.xingyun.basedata.controller;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.lframework.starter.common.exceptions.impl.DefaultClientException;
import com.lframework.starter.common.utils.CollectionUtil;
import com.lframework.starter.common.utils.ThreadUtil;
import com.lframework.starter.web.core.annotations.security.HasPermission;
import com.lframework.starter.web.core.controller.DefaultBaseController;
import com.lframework.starter.web.core.components.resp.InvokeResult;
import com.lframework.starter.web.core.components.resp.InvokeResultBuilder;
import com.lframework.starter.web.core.components.resp.PageResult;
import com.lframework.starter.web.core.components.threads.DefaultRunnable;
import com.lframework.starter.web.core.utils.PageResultUtil;
import com.lframework.xingyun.basedata.bo.product.category.RelatedProductCategoryBo;
import com.lframework.xingyun.basedata.bo.product.property.GetProductPropertyBo;
import com.lframework.xingyun.basedata.bo.product.property.ProductPropertyModelorBo;
import com.lframework.xingyun.basedata.bo.product.property.QueryProductPropertyBo;
import com.lframework.xingyun.basedata.dto.product.category.PropertyCategoryCountDto;
import com.lframework.xingyun.basedata.dto.product.property.ProductPropertyModelorDto;
import com.lframework.xingyun.basedata.entity.ProductCategory;
import com.lframework.xingyun.basedata.entity.ProductProperty;
import com.lframework.xingyun.basedata.entity.ProductPropertyRelation;
import com.lframework.xingyun.basedata.service.product.ProductCategoryPropertyService;
import com.lframework.xingyun.basedata.service.product.ProductPropertyRelationService;
import com.lframework.xingyun.basedata.service.product.ProductPropertyService;
import com.lframework.xingyun.basedata.vo.product.property.CreateProductPropertyVo;
import com.lframework.xingyun.basedata.vo.product.property.QueryProductPropertyVo;
import com.lframework.xingyun.basedata.vo.product.property.UpdateProductPropertyVo;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.Operation;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
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
 * 商品分类属性管理
 *
 * @author zmj
 */
@Tag(name = "商品分类属性管理")
@Validated
@RestController
@RequestMapping("/basedata/product/property")
public class ProductPropertyController extends DefaultBaseController {

  @Autowired
  private ProductPropertyService productPropertyService;

  @Autowired
  private ProductPropertyRelationService productPropertyRelationService;

  @Autowired
  private ProductCategoryPropertyService productCategoryPropertyService;

  /**
   * 商品分类属性列表
   */
  @Operation(summary = "商品分类属性列表")
  @HasPermission({"base-data:product:property:query", "base-data:product:property:add",
      "base-data:product:property:modify"})
  @GetMapping("/query")
  public InvokeResult<PageResult<QueryProductPropertyBo>> query(@Valid QueryProductPropertyVo vo) {

    PageResult<ProductProperty> pageResult = productPropertyService.query(getPageIndex(vo),
        getPageSize(vo), vo);

    List<ProductProperty> datas = pageResult.getDatas();
    List<QueryProductPropertyBo> results = null;

    if (!CollectionUtil.isEmpty(datas)) {

      results = datas.stream().map(QueryProductPropertyBo::new).collect(Collectors.toList());
      List<String> propertyIds = results.stream().map(QueryProductPropertyBo::getId).collect(
          Collectors.toList());
      Map<String, Integer> categoryCountMap = productCategoryPropertyService.countCategoriesByPropertyIds(
              propertyIds).stream()
          .collect(Collectors.toMap(PropertyCategoryCountDto::getPropertyId,
              PropertyCategoryCountDto::getCategoryCount));
      results.forEach(t -> t.setCategoryCount(categoryCountMap.getOrDefault(t.getId(), 0)));
    }

    return InvokeResultBuilder.success(PageResultUtil.rebuild(pageResult, results));
  }

  /**
   * 查询商品分类属性
   */
  @Operation(summary = "查询商品分类属性")
  @Parameter(name = "id", description = "ID", in = ParameterIn.QUERY, required = true)
  @HasPermission({"base-data:product:property:query", "base-data:product:property:add",
      "base-data:product:property:modify"})
  @GetMapping
  public InvokeResult<GetProductPropertyBo> get(@NotBlank(message = "ID不能为空！") String id) {

    ProductProperty data = productPropertyService.findById(id);
    if (data == null) {
      throw new DefaultClientException("商品分类属性不存在！");
    }

    GetProductPropertyBo result = new GetProductPropertyBo(data);

    return InvokeResultBuilder.success(result);
  }

  /**
   * 根据ID删除
   */
  @Operation(summary = "删除商品分类属性")
  @HasPermission({"base-data:product:property:delete"})
  @DeleteMapping
  public InvokeResult<Void> deleteById(
      @Parameter(description = "ID", required = true) @NotEmpty(message = "商品分类属性ID不能为空！") String id) {

    productPropertyService.deleteById(id);

    productPropertyService.cleanCacheByKey(id);

    return InvokeResultBuilder.success();
  }

  /**
   * 新增商品分类属性
   */
  @Operation(summary = "新增商品分类属性")
  @HasPermission({"base-data:product:property:add"})
  @PostMapping
  public InvokeResult<Void> create(@Valid @RequestBody CreateProductPropertyVo vo) {

    productPropertyService.create(vo);

    return InvokeResultBuilder.success();
  }

  /**
   * 修改商品分类属性
   */
  @Operation(summary = "修改商品分类属性")
  @HasPermission({"base-data:product:property:modify"})
  @PutMapping
  public InvokeResult<Void> update(@Valid @RequestBody UpdateProductPropertyVo vo) {

    productPropertyService.update(vo);

    productPropertyService.cleanCacheByKey(vo.getId());

    ThreadUtil.execAsync(new DefaultRunnable((() -> {
      Wrapper<ProductPropertyRelation> relationWrapper = Wrappers.lambdaQuery(
              ProductPropertyRelation.class).select(ProductPropertyRelation::getProductId)
          .eq(ProductPropertyRelation::getPropertyId, vo.getId())
          .groupBy(ProductPropertyRelation::getProductId);
      List<ProductPropertyRelation> relations = productPropertyRelationService.list(
          relationWrapper);

      productPropertyRelationService.cleanCacheByKeys(
          relations.stream().map(ProductPropertyRelation::getProductId).collect(
              Collectors.toList()));
    })));

    return InvokeResultBuilder.success();
  }

  /**
   * 查询已关联商品分类
   */
  @Operation(summary = "查询已关联商品分类")
  @Parameter(name = "id", description = "分类属性ID", in = ParameterIn.QUERY, required = true)
  @HasPermission({"base-data:product:category:query"})
  @GetMapping("/categories")
  public InvokeResult<List<RelatedProductCategoryBo>> getCategories(
      @NotBlank(message = "分类属性ID不能为空！") String id) {

    List<ProductCategory> datas = productCategoryPropertyService.getCategoriesByPropertyId(id);
    List<RelatedProductCategoryBo> results = CollectionUtil.emptyList();
    if (!CollectionUtil.isEmpty(datas)) {
      results = datas.stream().map(RelatedProductCategoryBo::new).collect(Collectors.toList());
    }

    return InvokeResultBuilder.success(results);
  }

  /**
   * 分类属性模型
   */
  @Operation(summary = "分类属性模型")
  @Parameter(name = "categoryId", description = "分类ID", in = ParameterIn.QUERY, required = true)
  @GetMapping("/modelor/category")
  public InvokeResult<List<ProductPropertyModelorBo>> getModelorByCategory(
      @NotBlank(message = "分类ID不能为空！") String categoryId) {

    List<ProductPropertyModelorDto> datas = productPropertyService.getModelorByCategoryId(
        categoryId);

    List<ProductPropertyModelorBo> results = CollectionUtil.emptyList();
    if (!CollectionUtil.isEmpty(datas)) {
      results = datas.stream().map(ProductPropertyModelorBo::new).collect(Collectors.toList());
    }

    return InvokeResultBuilder.success(results);
  }
}
