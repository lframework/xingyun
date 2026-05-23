package com.lframework.xingyun.basedata.controller;

import com.lframework.starter.common.utils.CollectionUtil;
import com.lframework.starter.web.core.annotations.security.HasPermission;
import com.lframework.starter.web.core.components.resp.InvokeResult;
import com.lframework.starter.web.core.components.resp.InvokeResultBuilder;
import com.lframework.starter.web.core.controller.DefaultBaseController;
import com.lframework.xingyun.basedata.bo.product.category.property.ProductCategoryPropertyRelationBo;
import com.lframework.xingyun.basedata.dto.product.category.property.ProductCategoryPropertyRelationDto;
import com.lframework.xingyun.basedata.enums.PropertyType;
import com.lframework.xingyun.basedata.service.product.ProductCategoryPropertyRelationService;
import com.lframework.xingyun.basedata.vo.product.category.property.BindProductCategoryPropertyRelationVo;
import com.lframework.xingyun.basedata.vo.product.category.property.CreateProductCategoryPropertyRelationVo;
import com.lframework.xingyun.basedata.vo.product.property.CreateProductCategoryPropertyDefinitionVo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 商品分类属性配置管理
 *
 * @author zmj
 */
@Tag(name = "商品分类属性配置管理")
@Validated
@RestController
@RequestMapping("/basedata/product/category/property")
public class ProductCategoryPropertyRelationController extends DefaultBaseController {

  @Autowired
  private ProductCategoryPropertyRelationService ProductCategoryPropertyRelationService;

  /**
   * 商品分类属性配置列表
   */
  @Operation(summary = "商品分类属性配置列表")
  @Parameter(name = "categoryId", description = "分类ID", in = ParameterIn.QUERY, required = true)
  @HasPermission({"base-data:product:property:query", "base-data:product:property:add",
      "base-data:product:property:modify"})
  @GetMapping("/query")
  public InvokeResult<List<ProductCategoryPropertyRelationBo>> query(
      @NotBlank(message = "商品分类不能为空！") String categoryId) {

    List<ProductCategoryPropertyRelationDto> datas = ProductCategoryPropertyRelationService.getByCategoryId(
        categoryId);
    if (CollectionUtil.isEmpty(datas)) {
      return InvokeResultBuilder.success(CollectionUtil.emptyList());
    }

    List<ProductCategoryPropertyRelationBo> results = datas.stream().map(ProductCategoryPropertyRelationBo::new)
        .collect(Collectors.toList());

    return InvokeResultBuilder.success(results);
  }

  /**
   * 绑定已有分类属性
   */
  @Operation(summary = "绑定已有分类属性")
  @HasPermission({"base-data:product:property:modify"})
  @PostMapping("/bind")
  public InvokeResult<Void> bind(@Valid @RequestBody BindProductCategoryPropertyRelationVo vo) {

    ProductCategoryPropertyRelationService.bindExistingProperties(vo.getCategoryId(), vo.getPropertyIds());

    return InvokeResultBuilder.success();
  }

  /**
   * 新增分类属性并绑定分类
   */
  @Operation(summary = "新增分类属性并绑定分类")
  @HasPermission({"base-data:product:property:add"})
  @PostMapping
  public InvokeResult<Void> create(@Valid @RequestBody CreateProductCategoryPropertyRelationVo vo) {

    CreateProductCategoryPropertyDefinitionVo createVo = new CreateProductCategoryPropertyDefinitionVo();
    BeanUtils.copyProperties(vo, createVo);
    createVo.setPropertyType(PropertyType.APPOINT.getCode());

    ProductCategoryPropertyRelationService.createPropertyAndBind(vo.getCategoryId(), createVo);

    return InvokeResultBuilder.success();
  }

  /**
   * 删除商品分类属性配置
   */
  @Operation(summary = "删除商品分类属性配置")
  @HasPermission({"base-data:product:property:modify"})
  @DeleteMapping
  public InvokeResult<Void> delete(
      @Parameter(description = "分类ID", required = true) @NotBlank(message = "商品分类不能为空！") String categoryId,
      @Parameter(description = "分类属性ID", required = true) @NotBlank(message = "分类属性不能为空！") String propertyId) {

    ProductCategoryPropertyRelationService.removeByCategoryIdAndPropertyId(categoryId, propertyId);

    return InvokeResultBuilder.success();
  }
}
