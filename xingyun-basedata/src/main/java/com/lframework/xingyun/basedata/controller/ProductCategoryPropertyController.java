package com.lframework.xingyun.basedata.controller;

import com.lframework.starter.common.utils.CollectionUtil;
import com.lframework.starter.web.core.annotations.security.HasPermission;
import com.lframework.starter.web.core.components.resp.InvokeResult;
import com.lframework.starter.web.core.components.resp.InvokeResultBuilder;
import com.lframework.starter.web.core.controller.DefaultBaseController;
import com.lframework.xingyun.basedata.bo.product.category.property.ProductCategoryPropertyBo;
import com.lframework.xingyun.basedata.dto.product.category.property.ProductCategoryPropertyDto;
import com.lframework.xingyun.basedata.enums.PropertyType;
import com.lframework.xingyun.basedata.service.product.ProductCategoryPropertyService;
import com.lframework.xingyun.basedata.vo.product.category.property.BindProductCategoryPropertyVo;
import com.lframework.xingyun.basedata.vo.product.category.property.CreateProductCategoryPropertyVo;
import com.lframework.xingyun.basedata.vo.product.property.CreateProductPropertyVo;
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
public class ProductCategoryPropertyController extends DefaultBaseController {

  @Autowired
  private ProductCategoryPropertyService productCategoryPropertyService;

  /**
   * 商品分类属性配置列表
   */
  @Operation(summary = "商品分类属性配置列表")
  @Parameter(name = "categoryId", description = "分类ID", in = ParameterIn.QUERY, required = true)
  @HasPermission({"base-data:product:property:query", "base-data:product:property:add",
      "base-data:product:property:modify"})
  @GetMapping("/query")
  public InvokeResult<List<ProductCategoryPropertyBo>> query(
      @NotBlank(message = "商品分类不能为空！") String categoryId) {

    List<ProductCategoryPropertyDto> datas = productCategoryPropertyService.getByCategoryId(
        categoryId);
    if (CollectionUtil.isEmpty(datas)) {
      return InvokeResultBuilder.success(CollectionUtil.emptyList());
    }

    List<ProductCategoryPropertyBo> results = datas.stream().map(ProductCategoryPropertyBo::new)
        .collect(Collectors.toList());

    return InvokeResultBuilder.success(results);
  }

  /**
   * 绑定已有分类属性
   */
  @Operation(summary = "绑定已有分类属性")
  @HasPermission({"base-data:product:property:modify"})
  @PostMapping("/bind")
  public InvokeResult<Void> bind(@Valid @RequestBody BindProductCategoryPropertyVo vo) {

    productCategoryPropertyService.bindExistingProperties(vo.getCategoryId(), vo.getPropertyIds());

    return InvokeResultBuilder.success();
  }

  /**
   * 新增分类属性并绑定分类
   */
  @Operation(summary = "新增分类属性并绑定分类")
  @HasPermission({"base-data:product:property:add"})
  @PostMapping
  public InvokeResult<Void> create(@Valid @RequestBody CreateProductCategoryPropertyVo vo) {

    CreateProductPropertyVo createVo = new CreateProductPropertyVo();
    BeanUtils.copyProperties(vo, createVo);
    createVo.setPropertyType(PropertyType.APPOINT.getCode());

    productCategoryPropertyService.createPropertyAndBind(vo.getCategoryId(), createVo);

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

    productCategoryPropertyService.removeByCategoryIdAndPropertyId(categoryId, propertyId);

    return InvokeResultBuilder.success();
  }
}
