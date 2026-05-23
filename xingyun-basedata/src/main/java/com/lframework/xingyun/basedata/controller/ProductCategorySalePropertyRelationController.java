package com.lframework.xingyun.basedata.controller;

import com.lframework.starter.common.utils.CollectionUtil;
import com.lframework.starter.web.core.annotations.security.HasPermission;
import com.lframework.starter.web.core.components.resp.InvokeResult;
import com.lframework.starter.web.core.components.resp.InvokeResultBuilder;
import com.lframework.starter.web.core.controller.DefaultBaseController;
import com.lframework.xingyun.basedata.bo.product.category.saleproperty.ProductCategorySalePropertyRelationBo;
import com.lframework.xingyun.basedata.dto.product.category.saleproperty.ProductCategorySalePropertyRelationDto;
import com.lframework.xingyun.basedata.service.product.ProductCategorySalePropertyRelationService;
import com.lframework.xingyun.basedata.vo.product.category.saleproperty.BindProductCategorySalePropertyRelationVo;
import com.lframework.xingyun.basedata.vo.product.category.saleproperty.CreateProductCategorySalePropertyRelationVo;
import com.lframework.xingyun.basedata.vo.product.saleproperty.CreateProductSalePropertyDefinitionVo;
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
 * 商品分类销售属性配置管理
 *
 * @author zmj
 */
@Tag(name = "商品分类销售属性配置管理")
@Validated
@RestController
@RequestMapping("/basedata/product/category/sale/property")
public class ProductCategorySalePropertyRelationController extends DefaultBaseController {

  @Autowired
  private ProductCategorySalePropertyRelationService ProductCategorySalePropertyRelationService;

  /**
   * 商品分类销售属性配置列表
   */
  @Operation(summary = "商品分类销售属性配置列表")
  @Parameter(name = "categoryId", description = "分类ID", in = ParameterIn.QUERY, required = true)
  @HasPermission({"base-data:product:sale-property:query", "base-data:product:sale-property:add",
      "base-data:product:sale-property:modify"})
  @GetMapping("/query")
  public InvokeResult<List<ProductCategorySalePropertyRelationBo>> query(
      @NotBlank(message = "商品分类不能为空！") String categoryId) {

    List<ProductCategorySalePropertyRelationDto> datas = ProductCategorySalePropertyRelationService.getByCategoryId(
        categoryId);
    if (CollectionUtil.isEmpty(datas)) {
      return InvokeResultBuilder.success(CollectionUtil.emptyList());
    }

    List<ProductCategorySalePropertyRelationBo> results = datas.stream()
        .map(ProductCategorySalePropertyRelationBo::new)
        .collect(Collectors.toList());

    return InvokeResultBuilder.success(results);
  }

  /**
   * 绑定已有销售属性
   */
  @Operation(summary = "绑定已有销售属性")
  @HasPermission({"base-data:product:sale-property:modify"})
  @PostMapping("/bind")
  public InvokeResult<Void> bind(@Valid @RequestBody BindProductCategorySalePropertyRelationVo vo) {

    ProductCategorySalePropertyRelationService.bindExistingProperties(vo.getCategoryId(),
        vo.getPropertyIds());

    return InvokeResultBuilder.success();
  }

  /**
   * 新增销售属性并绑定分类
   */
  @Operation(summary = "新增销售属性并绑定分类")
  @HasPermission({"base-data:product:sale-property:add"})
  @PostMapping
  public InvokeResult<Void> create(@Valid @RequestBody CreateProductCategorySalePropertyRelationVo vo) {

    CreateProductSalePropertyDefinitionVo createVo = new CreateProductSalePropertyDefinitionVo();
    BeanUtils.copyProperties(vo, createVo);

    ProductCategorySalePropertyRelationService.createPropertyAndBind(vo.getCategoryId(), createVo);

    return InvokeResultBuilder.success();
  }

  /**
   * 删除商品分类销售属性配置
   */
  @Operation(summary = "删除商品分类销售属性配置")
  @HasPermission({"base-data:product:sale-property:modify"})
  @DeleteMapping
  public InvokeResult<Void> delete(
      @Parameter(description = "分类ID", required = true) @NotBlank(message = "商品分类不能为空！") String categoryId,
      @Parameter(description = "销售属性ID", required = true) @NotBlank(message = "销售属性不能为空！") String propertyId) {

    ProductCategorySalePropertyRelationService.removeByCategoryIdAndPropertyId(categoryId, propertyId);

    return InvokeResultBuilder.success();
  }
}
